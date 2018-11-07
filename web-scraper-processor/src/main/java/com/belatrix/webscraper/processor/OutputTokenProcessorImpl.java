package com.belatrix.webscraper.processor;

import com.belatrix.webscraper.api.processor.OutputTokenProcessor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @see com.belatrix.webscraper.api.processor.OutputTokenProcessor
 * @author Victor Ortiz
 */
public class OutputTokenProcessorImpl implements OutputTokenProcessor {
    @Override
    public Set<String> getMatchingTokensInBuffer( Pattern pattern, StringBuilder buffer ) {
        Objects.requireNonNull( pattern, "Pattern is null" );
        Objects.requireNonNull( buffer, "Buffer is null" );
        Set<String> matches = new HashSet<>();
        Matcher matcher = pattern.matcher( buffer );

        while ( matcher.find() ) {
            matches.add( matcher.group() );
        }

        return matches;
    }
}