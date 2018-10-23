package com.belatrix.webscraper.api.processor;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Contract interface that determines the token processor module
 *
 * @author Victor Ortiz
 */
public interface OutputTokenProcessor {
    /**
     * Finds a set of tokens according to a given pattern and a buffer in which to find it
     *
     * @param pattern the pattern to search for
     * @param buffer the buffer to search in
     * @return the set of matching tokens that were found in the given buffer
     */
    Set<String> getMatchingTokensInBuffer( Pattern pattern, StringBuilder buffer );
}