package com.belatrix.webscraper.processor;

import com.belatrix.webscraper.api.processor.OutputTokenProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OutputTokenProcessorImplTest {
    private static final String EMPTY = "";
    private static final String SOME_BUFFER = "Lorem Ipsum";
    private static final String SOME_REGEX = "\\w+";

    private final OutputTokenProcessor service = new OutputTokenProcessorImpl();

    @Test
    void getMatchingTokensInBufferNullPatternProcessorTest() {
        Executable executable = () -> service.getMatchingTokensInBuffer( null, new StringBuilder() );
        assertThrows( NullPointerException.class, executable );
    }

    @Test
    void getMatchingTokensInBufferNullBufferProcessorTest() {
        Executable executable = () -> service.getMatchingTokensInBuffer( Pattern.compile( EMPTY ), null );
        assertThrows( NullPointerException.class, executable );
    }

    @Test
    void getMatchingTokensInBufferEmptyPatternProcessorTest() {
        assertEquals( 1, service.getMatchingTokensInBuffer( Pattern.compile( EMPTY ), new StringBuilder( EMPTY ) ).size() );
    }

    @Test
    void getMatchingTokensInBufferSomePatternProcessorTest() {
        assertEquals( 2, service.getMatchingTokensInBuffer( Pattern.compile( SOME_REGEX ), new StringBuilder( SOME_BUFFER ) ).size() );
    }
}