package com.belatrix.webscraper.pattern;

import com.belatrix.webscraper.api.pattern.PatternLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class PatternLoaderImplTest {
    private static final String EMPTY_FILE = "./src/test/resources/empty-test.txt";
    private static final String EMPTY_DIR = "./src/test/resources/empty-test/";
    private static final String DIR_WITHIN_DIR = "./src/test/resources/dir-within-dir-test/";
    private static final String ERRORS_DIR = "./src/test/resources/errors-test/";
    private static final String GOOD_DIR = "./src/test/resources/good-test/";

    private final PatternLoader service = new PatternLoaderImpl();

    @Test
    void loadPatternsFromDirectoryNotADirectoryTest() {
        Executable executable = () -> service.loadPatternsFromDirectory( EMPTY_FILE );
        assertThrows( NotDirectoryException.class, executable );

    }

    @Test
    void loadPatternsFromDirectoryEmptyDirectoryTest() throws IOException {
        Set<Pattern> patterns = service.loadPatternsFromDirectory( EMPTY_DIR );
        assertNotNull( patterns );
        assertTrue( patterns.isEmpty() );
    }

    @Test
    void loadPatternsFromDirectoryDirWithinDirTest() throws IOException {
        Set<Pattern> patterns = service.loadPatternsFromDirectory( DIR_WITHIN_DIR );
        assertNotNull( patterns );
        assertEquals( 1, patterns.size() );
    }

    @Test
    void loadPatternsFromDirectoryDirWithBadFiles() throws IOException {
        Set<Pattern> patterns = service.loadPatternsFromDirectory( ERRORS_DIR );
        assertNotNull( patterns );
        assertEquals( 3, patterns.size() );
    }

    @Test
    void loadPatternsFromDirectoryDirWithGoodFiles() throws IOException {
        Set<Pattern> patterns = service.loadPatternsFromDirectory( GOOD_DIR );
        assertNotNull( patterns );
        assertEquals( 4, patterns.size() );
    }
}