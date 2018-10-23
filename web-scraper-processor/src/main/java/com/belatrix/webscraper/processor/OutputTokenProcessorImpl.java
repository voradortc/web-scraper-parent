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
        if ( Objects.isNull( pattern ) )
            throw new NullPointerException( "Pattern is null" );

        if ( Objects.isNull( buffer ) )
            throw new NullPointerException( "Buffer is null" );

        Matcher matcher = pattern.matcher( buffer );
        Set<String> matches = new HashSet<>();

        while ( matcher.find() ) {
            matches.add( matcher.group() );
        }

        return matches;
    }
}