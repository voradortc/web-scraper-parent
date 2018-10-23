package com.belatrix.webscraper.pattern;

import com.belatrix.webscraper.api.pattern.PatternLoader;
import com.belatrix.webscraper.commons.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @see com.belatrix.webscraper.api.pattern.PatternLoader
 * @author Victor Ortiz
 */
public class PatternLoaderImpl implements PatternLoader {
    private static final String EMPTY = "";
    private static final Logger logger = LoggerFactory.getLogger( PatternLoaderImpl.class );

    @Override
    public Set<Pattern> loadPatternsFromDirectory( String path ) throws IOException {
        Path actualPath = CommonUtils.testPathReadability( path );

        try ( Stream<Path> paths = Files.list( actualPath ) )  {
            return paths
                    .map( this::createPattern )
                    .filter( Objects::nonNull )
                    .collect( Collectors.toSet() );
        }
    }

    private Pattern createPattern( Path path ) {
        Pattern pattern = null;

        try {
            pattern = loadPatternFromTextFile( path.toString() );
        } catch ( Exception exception ) {
            logger.error( exception.getLocalizedMessage() );
        }

        return pattern;
    }

    private Pattern loadPatternFromTextFile( String path ) throws IOException {
        Path actualPath = CommonUtils.testPathReadability( path );
        String regex = new String( Files.readAllBytes( actualPath ) );

        if ( EMPTY.equals( regex ) )
            throw new PatternSyntaxException( "Empty REGEX", regex, 0 );

        return Pattern.compile( regex );
    }
}