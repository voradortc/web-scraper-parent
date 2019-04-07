package com.belatrix.webscraper.commons;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommonUtilsTest {
    private static final String COLON = ":";
    private static final String DOT = ".";
    private static final String EMPTY = "";
    private static final String BAD_FILE = "./not-a-file";

    private static final Path BASE = Paths.get( DOT );

    @Test
    void testPathReadabilityPathIsNullTest() {
        Executable executable = () -> CommonUtils.testPathReadability( null );
        assertThrows( NullPointerException.class, executable );
    }

    @Test
    void testPathReadabilityPathIsEmptyTest() throws FileNotFoundException {
        assertEquals( BASE.normalize().toAbsolutePath(), CommonUtils.testPathReadability( EMPTY ).toAbsolutePath() );
    }

    @Test
    void testPathReadabilityPathIsNotACorrectPathTest() {
        Executable executable = () -> CommonUtils.testPathReadability( COLON );
        assertThrows( InvalidPathException.class, executable );
    }

    @Test
    void testPathReadabilityPathIsNotReadableTest() {
        Executable executable = () -> CommonUtils.testPathReadability( BAD_FILE );
        assertThrows( FileNotFoundException.class, executable );
    }
}