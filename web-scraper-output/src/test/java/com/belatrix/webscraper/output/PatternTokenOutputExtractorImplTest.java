package com.belatrix.webscraper.output;

import com.belatrix.webscraper.api.output.PatternTokenOutputExtractor;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.Set;

public class PatternTokenOutputExtractorImplTest {
    private static final String ALREADY_EXISTS = "./src/test/resources/already-exists.txt";
    private static final String BAD_PATH = ":";
    private static final String NOT_EXISTS = "./src/test/resources/not-exists.txt";
    private static final String EMPTY = "";

    private PatternTokenOutputExtractor service = new PatternTokenOutputExtractorImpl();

    //can't write to path;

    @Test( expected = NullPointerException.class )
    public void createTokenExtractorOutputFileNullPathTest() throws IOException {
        service.createTokenExtractorOutputFile( Collections.emptySet(), null );
    }

    @Test( expected = NoSuchFileException.class )
    public void createTokenExtractorOutputFileEmptyPathTest() throws IOException {
        service.createTokenExtractorOutputFile( Collections.emptySet(), EMPTY );
    }

    @Test( expected = InvalidPathException.class )
    public void createTokenExtractorOutputFileBadPathTest() throws IOException {
        service.createTokenExtractorOutputFile( Collections.emptySet(), BAD_PATH );
    }

    @Test( expected = FileAlreadyExistsException.class )
    public void createTokenExtractorOutputFilePathExistsTest() throws IOException {
        service.createTokenExtractorOutputFile( Collections.emptySet(), ALREADY_EXISTS );
    }

    @Test
    public void createTokenExtractorOutputFileNullTokensTest() throws IOException {
        service.createTokenExtractorOutputFile( null, NOT_EXISTS );
        assertEquals( 0, amountOfTokensChecker() );
    }

    @Test
    public void createTokenExtractorOutputFileGoodTokensTest() throws IOException {
        Set<String> tokens = Set.of( "token-1", "token-2", "token-3" );
        service.createTokenExtractorOutputFile( tokens, NOT_EXISTS );
        assertEquals( 3, amountOfTokensChecker() );
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists( Paths.get( NOT_EXISTS ) );
    }

    private int amountOfTokensChecker() throws IOException {
        return Files.readAllLines( Paths.get( NOT_EXISTS ) ).size();
    }
}