package com.belatrix.webscraper.flow;

import com.belatrix.webscraper.api.flow.ProgramFlow;
import com.belatrix.webscraper.api.input.UrlListLoader;
import com.belatrix.webscraper.api.output.PatternTokenOutputExtractor;
import com.belatrix.webscraper.api.pattern.PatternLoader;
import com.belatrix.webscraper.api.processor.OutputTokenProcessor;
import com.belatrix.webscraper.api.url.UrlContentExtractor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProgramFlowImplTest {
    private static final int ZERO = 0;
    private static final int TIMES = 3;

    private static final String EMPTY = "";
    private static final String OUTPUT_PATH = "~/";

    @Mock
    private UrlListLoader urlListLoader;

    @Mock
    private PatternLoader patternLoader;

    @Mock
    private UrlContentExtractor urlContentExtractor;

    @Mock
    private OutputTokenProcessor outputTokenProcessor;

    @Mock
    private PatternTokenOutputExtractor patternTokenOutputExtractor;

    @InjectMocks
    private ProgramFlow service = new ProgramFlowImpl( OUTPUT_PATH, EMPTY );

    @Before
    public void setup() {
        initMocks( this );
    }

    @Test( expected = NullPointerException.class )
    public void programFlowImplNullPathPrefixTest() {
        new ProgramFlowImpl( null, EMPTY );
    }

    @Test( expected = NullPointerException.class )
    public void programFlowImplNullPatternPathTest() {
        new ProgramFlowImpl( OUTPUT_PATH, null );
    }

    @Test
    public void programFlowImplNoUrlsLoadedTest() throws IOException {
        initLoadingMocks( Collections.emptySet(), initPatterns(), Collections.emptySet() );

        service.scraperFlow( EMPTY );
        verify( urlContentExtractor, times( ZERO ) ).extractURLTextContent( any() );
    }

    @Test
    public void programFlowImplNoPatternsLoadedTest() throws IOException, URISyntaxException {
        initLoadingMocks( initURLs(), Collections.emptySet(), Collections.emptySet() );

        service.scraperFlow( EMPTY );
        verify( urlContentExtractor, times( ZERO ) ).extractURLTextContent( any() );
    }

    @Test
    public void programFlowImplOutputEvenWhenNoTokensTest() throws IOException, URISyntaxException {
        initLoadingMocks( initURLs(), initPatterns(), Collections.emptySet() );

        service.scraperFlow( EMPTY );
        verify( patternTokenOutputExtractor, times( TIMES ) ).createTokenExtractorOutputFile( any(), any() );
    }

    @Test
    public void programFlowImplOutputErrorTest() throws IOException, URISyntaxException {
        initLoadingMocks( initURLs(), initPatterns(), Collections.emptySet() );
        doThrow( new IOException( "Some IO Error" ) ).when( patternTokenOutputExtractor ).createTokenExtractorOutputFile( any(), any() );

        service.scraperFlow( EMPTY );
        verify( patternTokenOutputExtractor, times( TIMES ) ).createTokenExtractorOutputFile( any(), any() );
    }

    @Test
    public void programFlowImplCorrectOutputTest() throws IOException, URISyntaxException {
        initLoadingMocks( initURLs(), initPatterns(), initTokens() );

        service.scraperFlow( EMPTY );
        verify( patternTokenOutputExtractor, times( TIMES ) ).createTokenExtractorOutputFile( any(), any() );
    }

    private void initLoadingMocks( Set<URI> urls, Set<Pattern> patterns, Set<String> tokens ) throws IOException {
        when( urlListLoader.loadURLsFromTextFile( any() ) ).thenReturn( urls );
        when( patternLoader.loadPatternsFromDirectory( any() ) ).thenReturn( patterns );
        when( urlContentExtractor.extractURLTextContent( any() ) ).thenReturn( new StringBuilder() );

        for ( Pattern pattern: patterns )
            when( outputTokenProcessor.getMatchingTokensInBuffer( eq( pattern ), any() ) ).thenReturn( tokens );
    }

    private Set<URI> initURLs() throws URISyntaxException {
        URI url1 = new URI( "http://www.google.com" );
        URI url2 = new URI( "http://www.microsoft.com" );
        URI url3 = new URI( "http://www.oracle.com" );

        return Set.of( url1, url2, url3 );
    }

    private Set<Pattern> initPatterns() {
        Pattern pattern1 = Pattern.compile( "[a-z]*" );
        Pattern pattern2 = Pattern.compile( "\\w*" );
        Pattern pattern3 = Pattern.compile( "Lorem[0-9]+" );

        return Set.of( pattern1, pattern2, pattern3 );
    }

    private Set<String> initTokens() {
        return Set.of( "Lorem", "ipsum", "dolor", "sit amet, consectetur adipiscing elit." );
    }
}