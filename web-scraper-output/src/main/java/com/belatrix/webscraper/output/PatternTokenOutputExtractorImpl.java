package com.belatrix.webscraper.output;

import com.belatrix.webscraper.api.output.PatternTokenOutputExtractor;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * @see com.belatrix.webscraper.api.output.PatternTokenOutputExtractor
 * @author Victor Ortiz
 */
public class PatternTokenOutputExtractorImpl implements PatternTokenOutputExtractor {
    private static final String EMPTY = "";

    @Override
    public void createTokenExtractorOutputFile( Set<String> tokens, String path ) throws IOException {
        Objects.requireNonNull( path, "Path is null" );

        if ( EMPTY.equals( path ) )
            throw new NoSuchFileException( "Path to file is empty" );

        if ( Objects.isNull( tokens ) )
            tokens = Collections.emptySet();

        Path actualPath = Paths.get( path );

        if ( actualPath.toFile().exists() )
            throw new FileAlreadyExistsException( "Will not overwrite existing path" );

        Files.write( actualPath, tokens, StandardOpenOption.CREATE, StandardOpenOption.WRITE );
    }
}