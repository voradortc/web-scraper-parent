package com.belatrix.webscraper.commons;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class that is used to store methods that are commonly used by several classes
 *
 * @author Victor Ortiz
 */
public interface CommonUtils {
    /**
     * Locates a file within the file system and verifies it for readability
     *
     * @param path the String path to find
     * @return the path object to the file
     * @throws FileNotFoundException when a file cannot be read or found
     */
    static Path testPathReadability( String path ) throws FileNotFoundException {
        Path actualPath = Paths.get( path );

        if ( !Files.isReadable( actualPath ) )
            throw new FileNotFoundException( String.format( "File not found or not readable '%s'", path ) );

        return actualPath;
    }
}