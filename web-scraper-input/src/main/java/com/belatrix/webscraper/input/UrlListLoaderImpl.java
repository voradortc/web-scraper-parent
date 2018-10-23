package com.belatrix.webscraper.input;

import com.belatrix.webscraper.api.input.UrlListLoader;
import com.belatrix.webscraper.commons.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @see com.belatrix.webscraper.api.input.UrlListLoader
 * @author Victor Ortiz
 */
public class UrlListLoaderImpl implements UrlListLoader {
    private static final Logger logger = LoggerFactory.getLogger( UrlListLoaderImpl.class );

    @Override
    public Set<URL> loadURLsFromTextFile( String path ) throws IOException {
        Path actualPath = CommonUtils.testPathReadability( path );

        return Files.readAllLines( actualPath ).stream()
                .map( this::createURL )
                .filter( Objects::nonNull )
                .collect( Collectors.toSet() );
    }

    private URL createURL( String urlText ) {
        URL url = null;

        try {
            url = new URL( urlText );
        } catch ( MalformedURLException exception ) {
            logger.error( exception.getLocalizedMessage() );
        }

        return url;
    }
}