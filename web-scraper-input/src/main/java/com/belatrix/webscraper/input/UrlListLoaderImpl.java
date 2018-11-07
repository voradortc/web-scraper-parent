package com.belatrix.webscraper.input;

import com.belatrix.webscraper.api.input.UrlListLoader;
import com.belatrix.webscraper.commons.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    public Set<URI> loadURLsFromTextFile( String path ) throws IOException {
        Path actualPath = CommonUtils.testPathReadability( path );

        return Files.readAllLines( actualPath ).stream()
                .map( this::createURL )
                .filter( Objects::nonNull )
                .collect( Collectors.toSet() );
    }

    private URI createURL( String urlText ) {
        URI url = null;

        try {
            url = new URI( urlText );
        } catch ( URISyntaxException exception ) {
            logger.error( exception.getLocalizedMessage() );
        }

        return url;
    }
}