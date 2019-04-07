package com.belatrix.webscraper.input;

import com.belatrix.webscraper.api.input.UrlListLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.net.URI;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UrlListLoaderImplTest {
    private static final String NOT_TEXT_FILE = "./src/test/resources";
    private static final String EMPTY_FILE = "./src/test/resources/empty-test.txt";
    private static final String ERRORS_FILE = "./src/test/resources/errors-test.txt";
    private static final String GOOD_FILE = "./src/test/resources/good-test.txt";

    private UrlListLoader service = new UrlListLoaderImpl();

    @Test
    void loadURLsFromTextFileFileIsNotATextFileTest() {
        Executable executable = () -> service.loadURLsFromTextFile( NOT_TEXT_FILE );
        assertThrows( IOException.class, executable );
    }

    @Test
    void loadURLsFromTextFileMappingEmptyFileTest() throws IOException {
        Set<URI> urls = service.loadURLsFromTextFile( EMPTY_FILE );
        assertNotNull( urls );
        assertTrue( urls.isEmpty() );
    }

    @Test
    void loadURLsFromTextFileMappingFileWithErrorsTest() throws IOException {
        Set<URI> urls = service.loadURLsFromTextFile( ERRORS_FILE );
        assertNotNull( urls );
        assertEquals( 3, urls.size() );
    }

    @Test
    void loadURLsFromTextFileCorrectMappingFileTest() throws IOException {
        Set<URI> urls = service.loadURLsFromTextFile( GOOD_FILE );
        assertNotNull( urls );
        assertEquals( 4, urls.size() );
    }
}