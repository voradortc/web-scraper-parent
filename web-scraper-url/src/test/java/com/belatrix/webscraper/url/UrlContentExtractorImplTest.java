package com.belatrix.webscraper.url;

import com.belatrix.webscraper.api.url.UrlContentExtractor;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class UrlContentExtractorImplTest {
    private static final String BAD_PAGE = "http://bad.page.localhost";
    private static final String TEST_HTML = "./src/test/resources/test.html";

    private UrlContentExtractor service = new UrlContentExtractorImpl();

    @Test( expected = NullPointerException.class )
    public void nullUrlContentExtractorTest() {
        service.extractURLTextContent( null );
    }

    @Test
    public void badUrlContentExtractorTest() throws MalformedURLException {
        URL url = new URL( BAD_PAGE );
        Assert.assertEquals( 0, service.extractURLTextContent( url ).length() );
    }

    @Test
    public void goodUrlContentExtractorTest() throws IOException {
        URL url = Paths.get( TEST_HTML ).toRealPath().toUri().toURL();
        Assert.assertNotEquals( 0, service.extractURLTextContent( url ).length() );
    }
}