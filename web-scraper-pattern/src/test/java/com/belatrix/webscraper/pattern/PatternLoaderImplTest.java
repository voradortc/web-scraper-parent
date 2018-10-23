package com.belatrix.webscraper.pattern;

import com.belatrix.webscraper.api.pattern.PatternLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;

public class PatternLoaderImplTest {
    private static final String EMPTY_FILE = "./src/test/resources/empty-test.txt";
    private static final String DIR_NOT_EXISTS = "./src/test/resources/not-exists-test/";
    private static final String EMPTY_DIR = "./src/test/resources/empty-test/";
    private static final String ERRORS_DIR = "./src/test/resources/errors-test/";
    private static final String GOOD_DIR = "./src/test/resources/good-test/";

    private PatternLoader service = new PatternLoaderImpl();

    @Test( expected = FileNotFoundException.class )
    public void dirDoesNotExistTest() throws IOException {
        service.loadPatternsFromDirectory( DIR_NOT_EXISTS );
    }

    @Test( expected = NotDirectoryException.class )
    public void notADirTest() throws IOException {
        service.loadPatternsFromDirectory( EMPTY_FILE );
    }

    @Test
    public void emptyDirTest() throws IOException {
        Assert.assertEquals( Collections.emptySet(), service.loadPatternsFromDirectory( EMPTY_DIR ) );
    }

    @Test
    public void dirWithErrorsTest() throws IOException {
        Set<Pattern> patterns = service.loadPatternsFromDirectory( ERRORS_DIR );

        Assert.assertNotNull( patterns );
        Assert.assertEquals( 3, patterns.size() );
    }

    @Test
    public void correctDirTest() throws IOException {
        Set<Pattern> patterns = service.loadPatternsFromDirectory( GOOD_DIR );

        Assert.assertNotNull( patterns );
        Assert.assertEquals( 4, patterns.size() );
    }
}