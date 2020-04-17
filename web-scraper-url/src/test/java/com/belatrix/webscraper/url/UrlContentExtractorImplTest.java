package com.belatrix.webscraper.url;

import com.belatrix.webscraper.api.url.UrlContentExtractor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class UrlContentExtractorImplTest {
    private static final String BAD_URL = "//bad.page.localhost";
    private static final String BAD_PAGE = "http://bad.page.localhost";
    private static final String TEST_HTML = "./src/test/resources/test.html";

    private final UrlContentExtractor service = new UrlContentExtractorImpl();

    @Test
    void extractURLTextContentNullUrlTest() {
        Executable executable = () -> service.extractURLTextContent( null );
        assertThrows( NullPointerException.class, executable );
    }

    @Test
    void extractURLTextContentBadUrlTest() throws URISyntaxException {
        URI url = new URI( BAD_URL );
        StringBuilder buffer = service.extractURLTextContent( url );
        assertNotNull( buffer );
        assertEquals( 0, buffer.length() );
    }

    @Test
    void extractURLTextContentUrlDoesNotRespondTest() throws URISyntaxException {
        URI url = new URI( BAD_PAGE );
        StringBuilder buffer = service.extractURLTextContent( url );
        assertNotNull( buffer );
        assertEquals( 0, buffer.length() );
    }

    @Test
    void extractURLTextContentGoodUrlTest() throws IOException {
        URI url = Paths.get( TEST_HTML ).toRealPath().toUri();
        StringBuilder buffer = service.extractURLTextContent( url );
        assertNotNull( buffer );
        assertNotEquals( 0, buffer.length() );
    }
}