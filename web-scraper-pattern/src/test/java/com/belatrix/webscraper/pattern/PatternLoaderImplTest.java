package com.belatrix.webscraper.pattern;

import com.belatrix.webscraper.api.pattern.PatternLoader;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class PatternLoaderImplTest {
    private static final String EMPTY_FILE = "./src/test/resources/empty-test.txt";
    private static final String EMPTY_DIR = "./src/test/resources/empty-test/";
    private static final String DIR_WITHIN_DIR = "./src/test/resources/dir-within-dir-test/";
    private static final String ERRORS_DIR = "./src/test/resources/errors-test/";
    private static final String GOOD_DIR = "./src/test/resources/good-test/";

    private PatternLoader service = new PatternLoaderImpl();

    @Test( expected = NotDirectoryException.class )
    public void loadPatternsFromDirectoryNotADirectoryTest() throws IOException {
        service.loadPatternsFromDirectory( EMPTY_FILE );
    }

    @Test
    public void loadPatternsFromDirectoryEmptyDirectoryTest() throws IOException {
        Set<Pattern> patterns = service.loadPatternsFromDirectory( EMPTY_DIR );
        assertNotNull( patterns );
        assertTrue( patterns.isEmpty() );
    }

    @Test
    public void loadPatternsFromDirectoryDirWithinDirTest() throws IOException {
        Set<Pattern> patterns = service.loadPatternsFromDirectory( DIR_WITHIN_DIR );
        assertNotNull( patterns );
        assertEquals( 1, patterns.size() );
    }

    @Test
    public void loadPatternsFromDirectoryDirWithBadFiles() throws IOException {
        Set<Pattern> patterns = service.loadPatternsFromDirectory( ERRORS_DIR );
        assertNotNull( patterns );
        assertEquals( 3, patterns.size() );
    }

    @Test
    public void loadPatternsFromDirectoryDirWithGoodFiles() throws IOException {
        Set<Pattern> patterns = service.loadPatternsFromDirectory( GOOD_DIR );
        assertNotNull( patterns );
        assertEquals( 4, patterns.size() );
    }
}