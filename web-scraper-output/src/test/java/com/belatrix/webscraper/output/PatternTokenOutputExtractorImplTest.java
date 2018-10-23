package com.belatrix.webscraper.output;

import com.belatrix.webscraper.api.output.PatternTokenOutputExtractor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

public class PatternTokenOutputExtractorImplTest {
    private static final String ALREADY_EXISTS = "./src/test/resources/already-exists.txt";
    private static final String NOT_EXISTS = "./src/test/resources/not-exists.txt";
    private static final String EMPTY = "";

    private PatternTokenOutputExtractor service = new PatternTokenOutputExtractorImpl();

    @Test( expected = NullPointerException.class )
    public void nullPathExtractorTest() throws IOException {
        service.createTokenExtractorOutputFile( Collections.emptySet(), null );
    }

    @Test( expected = NoSuchFileException.class )
    public void emptyPathExtractorTest() throws IOException {
        service.createTokenExtractorOutputFile( Collections.emptySet(), EMPTY );
    }

    @Test( expected = FileAlreadyExistsException.class )
    public void pathAlreadyExistsTest() throws IOException {
        service.createTokenExtractorOutputFile( Collections.emptySet(), ALREADY_EXISTS );
    }

    @Test
    public void nullTokenExtractorTest() throws IOException {
        service.createTokenExtractorOutputFile( null, NOT_EXISTS );
        Assert.assertEquals( 0, amountOfTokensChecker() );
    }

    @Test
    public void emptyTokenExtractorTest() throws IOException {
        service.createTokenExtractorOutputFile( Collections.emptySet(), NOT_EXISTS );
        Assert.assertEquals( 0, amountOfTokensChecker() );
    }

    @Test
    public void anyTokenExtractorTest() throws IOException {
        Set<String> tokens = Set.of( "token-1", "token-2", "token-3" );
        service.createTokenExtractorOutputFile( tokens, NOT_EXISTS );
        Assert.assertEquals( 3, amountOfTokensChecker() );
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists( Paths.get( NOT_EXISTS ) );
    }

    private int amountOfTokensChecker() throws IOException {
        return Files.readAllLines( Paths.get( NOT_EXISTS ) ).size();
    }
}