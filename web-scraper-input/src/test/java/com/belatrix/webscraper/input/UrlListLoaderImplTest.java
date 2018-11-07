package com.belatrix.webscraper.input;

import com.belatrix.webscraper.api.input.UrlListLoader;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.Set;

import static org.junit.Assert.*;

public class UrlListLoaderImplTest {
    private static final String NOT_TEXT_FILE = "./src/test/resources";
    private static final String EMPTY_FILE = "./src/test/resources/empty-test.txt";
    private static final String ERRORS_FILE = "./src/test/resources/errors-test.txt";
    private static final String GOOD_FILE = "./src/test/resources/good-test.txt";

    private UrlListLoader service = new UrlListLoaderImpl();

    @Test( expected = IOException.class )
    public void loadURLsFromTextFileFileIsNotATextFileTest() throws IOException {
        service.loadURLsFromTextFile( NOT_TEXT_FILE );
    }

    @Test
    public void loadURLsFromTextFileMappingEmptyFileTest() throws IOException {
        Set<URI> urls = service.loadURLsFromTextFile( EMPTY_FILE );
        assertNotNull( urls );
        assertTrue( urls.isEmpty() );
    }

    @Test
    public void loadURLsFromTextFileMappingFileWithErrorsTest() throws IOException {
        Set<URI> urls = service.loadURLsFromTextFile( ERRORS_FILE );
        assertNotNull( urls );
        assertEquals( 3, urls.size() );
    }

    @Test
    public void loadURLsFromTextFileCorrectMappingFileTest() throws IOException {
        Set<URI> urls = service.loadURLsFromTextFile( GOOD_FILE );
        assertNotNull( urls );
        assertEquals( 4, urls.size() );
    }
}