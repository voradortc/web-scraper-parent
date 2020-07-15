package com.belatrix.webscraper.api.flow;

import java.io.IOException;

/**
 * Contract interface that implements the flow of the program
 *
 * @author Victor Ortiz
 */
public interface ProgramFlow {
    /**
     * Main function that defines the business rules that determine the flow of the Web Scraper program
     *
     * @param urlPath the file path in which the URL addresses are stored
     * @throws IOException I/O exceptions are thrown when input files cannot be opened or found
     */
    void scraperFlow( String urlPath ) throws IOException;
}