package com.belatrix.webscraper.commons;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class CommonUtilsTest {
    private static final String COLON = ":";
    private static final String DOT = ".";
    private static final String EMPTY = "";
    private static final String BAD_FILE = "./not-a-file";

    private static final Path BASE = Paths.get( DOT );

    @Test( expected = NullPointerException.class )
    public void testPathReadabilityPathIsNullTest() throws FileNotFoundException {
        CommonUtils.testPathReadability( null );
    }

    @Test
    public void testPathReadabilityPathIsEmptyTest() throws FileNotFoundException {
        assertEquals( BASE.normalize().toAbsolutePath(), CommonUtils.testPathReadability( EMPTY ).toAbsolutePath() );
    }

    @Test( expected = InvalidPathException.class )
    public void testPathReadabilityPathIsNotACorrectPathTest() throws FileNotFoundException {
        CommonUtils.testPathReadability( COLON );
    }

    @Test( expected = FileNotFoundException.class )
    public void testPathReadabilityPathIsNotReadableTest() throws FileNotFoundException {
        CommonUtils.testPathReadability( BAD_FILE );
    }
}