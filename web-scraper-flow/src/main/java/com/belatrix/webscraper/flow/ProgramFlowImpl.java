package com.belatrix.webscraper.flow;

import com.belatrix.webscraper.api.flow.ProgramFlow;
import com.belatrix.webscraper.api.input.UrlListLoader;
import com.belatrix.webscraper.api.output.PatternTokenOutputExtractor;
import com.belatrix.webscraper.api.pattern.PatternLoader;
import com.belatrix.webscraper.api.processor.OutputTokenProcessor;
import com.belatrix.webscraper.api.url.UrlContentExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CyclicBarrier;
import java.util.regex.Pattern;

/**
 * @see com.belatrix.webscraper.api.flow.ProgramFlow
 * @author Victor Ortiz
 */
public class ProgramFlowImpl implements ProgramFlow {
    private static final Logger logger = LoggerFactory.getLogger( ProgramFlowImpl.class );

    @Inject
    private UrlListLoader urlListLoader;

    @Inject
    private PatternLoader patternLoader;

    @Inject
    private UrlContentExtractor urlContentExtractor;

    @Inject
    private OutputTokenProcessor outputTokenProcessor;

    @Inject
    private PatternTokenOutputExtractor patternTokenOutputExtractor;

    private final String outputPrefix;
    private final String patternPath;

    public ProgramFlowImpl( String outputPrefix, String patternPath ) {
        Objects.requireNonNull( outputPrefix, "The output directory path cannot be null" );
        Objects.requireNonNull( patternPath, "The pattern location path cannot be null" );

        this.outputPrefix = outputPrefix;
        this.patternPath = patternPath;
    }

    @Override
    public void scraperFlow( String urlPath ) throws IOException {
        logger.info( "Loading URLs from '{}'", urlPath );
        Set<URI> urls = urlListLoader.loadURLsFromTextFile( urlPath );

        logger.info( "Loading patterns from '{}'", patternPath );
        Set<Pattern> patterns = patternLoader.loadPatternsFromDirectory( patternPath );

        Set<Output> outputs = new CopyOnWriteArraySet<>();
        CyclicBarrier barrier = new CyclicBarrier( urls.size() + 1 );

        if ( urls.isEmpty() ) {
            logger.error( "No URLs found, processing will be skipped" );
            return;
        }

        if ( patterns.isEmpty() ) {
            logger.error( "No patterns found, URL processing will be skipped" );
            return;
        }

        for ( URI url : urls )
            new Thread( () -> handleURLProcessing( url, patterns, outputs, barrier ), url.getHost() ).start();

        barrierWaitForTasks( barrier );

        for ( Output output : outputs ) {
            String path = String.format( "%s%s.txt", outputPrefix, output.url.getHost() );

            try {
                logger.info( "Extracting URL '{}' tokens to path '{}'", output.url, path );
                patternTokenOutputExtractor.createTokenExtractorOutputFile( output.tokens, path );
            } catch ( IOException e ) {
                logger.error( e.getLocalizedMessage() );
            }
        }

        logger.info( "The extraction task has been completed" );
    }

    private void handleURLProcessing( URI url, Set<Pattern> patterns, Set<Output> outputs, CyclicBarrier barrier ) {
        threadedURLProcessing( url, patterns, outputs );
        barrierWaitForTasks( barrier );
    }

    private void threadedURLProcessing( URI url, Set<Pattern> patterns, Set<Output> outputs ) {
        logger.info( "Extracting content of the URL '{}'", url );
        StringBuilder buffer = urlContentExtractor.extractURLTextContent( url );
        Set<String> tokens = new CopyOnWriteArraySet<>();
        CyclicBarrier urlBarrier = new CyclicBarrier( patterns.size() );

        for ( Pattern pattern : patterns )
            new Thread( () -> threadedPatternProcessing( urlBarrier, tokens, pattern, buffer ) ).start();

        outputs.add( new Output( url, tokens ) );
    }

    private void threadedPatternProcessing( CyclicBarrier urlBarrier, Set<String> tokens, Pattern pattern, StringBuilder buffer ) {
        tokens.addAll( outputTokenProcessor.getMatchingTokensInBuffer( pattern, buffer ) );
        barrierWaitForTasks( urlBarrier );
    }

    private void barrierWaitForTasks( CyclicBarrier cyclicBarrier ) {
        try {
            cyclicBarrier.await();
        } catch ( BrokenBarrierException e ) {
            logger.error( e.getLocalizedMessage() );
        } catch ( InterruptedException e ) {
            logger.error( "A thread was interrupted {}", e.getLocalizedMessage() );
            Thread.currentThread().interrupt();
        }
    }

    private static final class Output {
        private final URI url;
        private final Set<String> tokens;

        private Output( URI url, Set<String> tokens ) {
            this.url = url;
            this.tokens = tokens;
        }
    }
}