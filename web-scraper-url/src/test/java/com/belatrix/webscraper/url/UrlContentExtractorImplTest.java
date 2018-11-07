package com.belatrix.webscraper.url;

import com.belatrix.webscraper.api.url.UrlContentExtractor;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class UrlContentExtractorImplTest {
    private static final String BAD_URL = "//bad.page.localhost";
    private static final String BAD_PAGE = "http://bad.page.localhost";
    private static final String TEST_HTML = "./src/test/resources/test.html";

    private UrlContentExtractor service = new UrlContentExtractorImpl();

    @Test( expected = NullPointerException.class )
    public void extractURLTextContentNullUrlTest() {
        service.extractURLTextContent( null );
    }

    @Test
    public void extractURLTextContentBadUrlTest() throws URISyntaxException {
        URI url = new URI( BAD_URL );
        StringBuilder buffer = service.extractURLTextContent( url );
        assertNotNull( buffer );
        assertEquals( 0, buffer.length() );
    }

    @Test
    public void extractURLTextContentUrlDoesNotRespondTest() throws URISyntaxException {
        URI url = new URI( BAD_PAGE );
        StringBuilder buffer = service.extractURLTextContent( url );
        assertNotNull( buffer );
        assertEquals( 0, buffer.length() );
    }

    @Test
    public void extractURLTextContentGoodUrlTest() throws IOException {
        URI url = Paths.get( TEST_HTML ).toRealPath().toUri();
        StringBuilder buffer = service.extractURLTextContent( url );
        assertNotNull( buffer );
        assertNotEquals( 0, buffer.length() );
    }
}