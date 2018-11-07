package com.belatrix.webscraper.processor;

import com.belatrix.webscraper.api.processor.OutputTokenProcessor;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.regex.Pattern;

public class OutputTokenProcessorImplTest {
    private static final String EMPTY = "";
    private static final String SOME_BUFFER = "Lorem Ipsum";
    private static final String SOME_REGEX = "\\w+";

    private OutputTokenProcessor service = new OutputTokenProcessorImpl();

    @Test( expected = NullPointerException.class )
    public void getMatchingTokensInBufferNullPatternProcessorTest() {
        service.getMatchingTokensInBuffer( null, new StringBuilder() );
    }

    @Test( expected = NullPointerException.class )
    public void getMatchingTokensInBufferNullBufferProcessorTest() {
        service.getMatchingTokensInBuffer( Pattern.compile( EMPTY ), null );
    }

    @Test
    public void getMatchingTokensInBufferEmptyPatternProcessorTest() {
        assertEquals( 1, service.getMatchingTokensInBuffer( Pattern.compile( EMPTY ), new StringBuilder( EMPTY ) ).size() );
    }

    @Test
    public void getMatchingTokensInBufferSomePatternProcessorTest() {
        assertEquals( 2, service.getMatchingTokensInBuffer( Pattern.compile( SOME_REGEX ), new StringBuilder( SOME_BUFFER ) ).size() );
    }
}