package com.belatrix.webscraper.api.url;

import java.net.URI;

/**
 * Contract interface that determines the content extractor module
 *
 * @author Victor ortiz
 */
public interface UrlContentExtractor {
    /**
     * Extracts the content of a given URL to a buffer object
     *
     * @param url the URL for which to extract the contents
     * @return the textual content that was found
     */
    StringBuilder extractURLTextContent( URI url );
}