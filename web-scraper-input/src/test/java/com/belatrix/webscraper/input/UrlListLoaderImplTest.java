package com.belatrix.webscraper.input;

import com.belatrix.webscraper.api.input.UrlListLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Set;

public class UrlListLoaderImplTest {
    private static final String FILE_NOT_EXISTS = "./src/test/resources/not-exists-test.txt";
    private static final String EMPTY_FILE = "./src/test/resources/empty-test.txt";
    private static final String ERRORS_FILE = "./src/test/resources/errors-test.txt";
    private static final String GOOD_FILE = "./src/test/resources/good-test.txt";

    private UrlListLoader service = new UrlListLoaderImpl();

    @Test( expected = FileNotFoundException.class )
    public void fileDoesNotExistTest() throws IOException {
        service.loadURLsFromTextFile( FILE_NOT_EXISTS );
    }

    @Test
    public void emptyFileTest() throws IOException {
        Assert.assertEquals( Collections.emptySet(), service.loadURLsFromTextFile( EMPTY_FILE ) );
    }

    @Test
    public void fileWithErrorsTest() throws IOException {
        Set<URL> urls = service.loadURLsFromTextFile( ERRORS_FILE );

        Assert.assertNotNull( urls );
        Assert.assertEquals( 3, urls.size() );
    }

    @Test
    public void correctFileTest() throws IOException {
        Set<URL> urls = service.loadURLsFromTextFile( GOOD_FILE );

        Assert.assertNotNull( urls );
        Assert.assertEquals( 4, urls.size() );
    }
}