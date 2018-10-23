package com.belatrix.webscraper.api.input;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * Contract interface that determines the URL loader module
 *
 * @author Victor Ortiz
 */
public interface UrlListLoader {
    /**
     * Loads a set of URLs from an input file
     *
     * @param path the path to the file that contains a set of URLs (one per line)
     * @return the set of URLs that were found on the input file
     * @throws IOException I/O exceptions are thrown when the input files cannot be read or found
     */
    Set<URL> loadURLsFromTextFile( String path ) throws IOException;
}