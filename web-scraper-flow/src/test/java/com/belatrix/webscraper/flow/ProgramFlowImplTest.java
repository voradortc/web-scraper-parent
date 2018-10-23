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
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.regex.Pattern;

import static org.mockito.Mockito.*;

public class ProgramFlowImplTest {
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

    @Test
    public void mockedIntegrationTest() throws IOException {
        service.scraperFlow( EMPTY );
        verify( patternTokenOutputExtractor, times( TIMES ) ).createTokenExtractorOutputFile( any(), any() );
    }

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks( this );
        URL url1 = new URL( "http://www.google.com" );
        URL url2 = new URL( "http://www.microsoft.com" );
        URL url3 = new URL( "http://www.oracle.com" );

        Pattern pattern1 = Pattern.compile( "[a-z]*" );
        Pattern pattern2 = Pattern.compile( "\\w*" );
        Pattern pattern3 = Pattern.compile( "Lorem[0-9]+" );

        Set<String> tokens1 = Set.of( "Lorem", "ipsum", "dolor", "sit amet, consectetur adipiscing elit." );
        Set<String> tokens2 = Set.of( "Maecenas", "consectetur", "lorem turpis, quis tempus mi dapibus ut." );
        Set<String> tokens3 = Set.of( "Morbi", "sollicitudin", "nunc felis, a finibus nulla ultricies at." );

        Set<URL> urls = Set.of( url1, url2, url3 );
        Set<Pattern> patterns = Set.of( pattern1, pattern2, pattern3 );

        when( urlListLoader.loadURLsFromTextFile( any() ) ).thenReturn( urls );
        when( patternLoader.loadPatternsFromDirectory( any() ) ).thenReturn( patterns );
        when( urlContentExtractor.extractURLTextContent( any() ) ).thenReturn( new StringBuilder() );
        when( outputTokenProcessor.getMatchingTokensInBuffer( eq( pattern1 ), any() ) ).thenReturn( tokens1 );
        when( outputTokenProcessor.getMatchingTokensInBuffer( eq( pattern2 ), any() ) ).thenReturn( tokens2 );
        when( outputTokenProcessor.getMatchingTokensInBuffer( eq( pattern3 ), any() ) ).thenReturn( tokens3 );
    }
}