package com.belatrix.webscraper.api.output;

import java.io.IOException;
import java.util.Set;

/**
 * Contract interface that determines the token list output module
 *
 * @author Victor Ortiz
 */
public interface PatternTokenOutputExtractor {
    /**
     * Outputs a set of String tokens to a given output path
     *
     * @param tokens the set of tokens that will be written
     * @param path the path where the file will be written
     * @throws IOException I/O exceptions are thrown in case files already exist or cannot be created
     */
    void createTokenExtractorOutputFile( Set<String> tokens, String path ) throws IOException;
}