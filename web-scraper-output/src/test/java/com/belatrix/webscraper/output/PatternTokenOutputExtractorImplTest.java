package com.belatrix.webscraper.output;

import com.belatrix.webscraper.api.output.PatternTokenOutputExtractor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PatternTokenOutputExtractorImplTest {
    private static final String ALREADY_EXISTS = "./src/test/resources/already-exists.txt";
    private static final String BAD_PATH = "\0";
    private static final String NOT_EXISTS = "./src/test/resources/not-exists.txt";
    private static final String EMPTY = "";

    private final PatternTokenOutputExtractor service = new PatternTokenOutputExtractorImpl();

    @Test
    void createTokenExtractorOutputFileNullPathTest() {
        Executable executable = () -> service.createTokenExtractorOutputFile( Collections.emptySet(), null );
        assertThrows( NullPointerException.class, executable );
    }

    @Test
    void createTokenExtractorOutputFileEmptyPathTest() {
        Executable executable = () -> service.createTokenExtractorOutputFile( Collections.emptySet(), EMPTY );
        assertThrows( NoSuchFileException.class, executable );
    }

    @Test
    void createTokenExtractorOutputFileBadPathTest() {
        Executable executable = () -> service.createTokenExtractorOutputFile( Collections.emptySet(), BAD_PATH );
        assertThrows( InvalidPathException.class, executable );
    }

    @Test
    void createTokenExtractorOutputFilePathExistsTest() {
        Executable executable = () -> service.createTokenExtractorOutputFile( Collections.emptySet(), ALREADY_EXISTS );
        assertThrows( FileAlreadyExistsException.class, executable );
    }

    @Test
    void createTokenExtractorOutputFileNullTokensTest() throws IOException {
        service.createTokenExtractorOutputFile( null, NOT_EXISTS );
        assertEquals( 0, amountOfTokensChecker() );
    }

    @Test
    void createTokenExtractorOutputFileGoodTokensTest() throws IOException {
        Set<String> tokens = new HashSet<>( Arrays.asList( "token-1", "token-2", "token-3" ) );
        service.createTokenExtractorOutputFile( tokens, NOT_EXISTS );
        assertEquals( 3, amountOfTokensChecker() );
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists( Paths.get( NOT_EXISTS ) );
    }

    private int amountOfTokensChecker() throws IOException {
        return Files.readAllLines( Paths.get( NOT_EXISTS ) ).size();
    }
}