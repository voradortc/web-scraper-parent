package com.belatrix.webscraper.api.pattern;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Contract interface the determines the patter loader module
 *
 * @author Victor Ortiz
 */
public interface PatternLoader {
    /**
     * Loads a set of patterns from a given directory where each file contains a different pattern
     *
     * @param path a path to a directory where the each file contains a different pattern to load
     * @return a set of patterns loaded from a set of inputs
     * @throws IOException I/O exceptions are thrown when files cannot be opened or found
     */
    Set<Pattern> loadPatternsFromDirectory( String path ) throws IOException;
}