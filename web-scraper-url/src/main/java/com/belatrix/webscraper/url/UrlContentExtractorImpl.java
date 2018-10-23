package com.belatrix.webscraper.url;

import com.belatrix.webscraper.api.url.UrlContentExtractor;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class UrlContentExtractorImpl implements UrlContentExtractor {
    private static final Logger logger = LoggerFactory.getLogger( UrlContentExtractorImpl.class );

    @Override
    public StringBuilder extractURLTextContent( URL url ) {
        if ( Objects.isNull( url ) )
            throw new NullPointerException( "URL is null" );

        StringBuilder buffer = new StringBuilder();

        try( WebClient webClient = new WebClient() ) {
            HtmlPage page = webClient.getPage( url );
            buffer.append( page.asText() );

        } catch ( FailingHttpStatusCodeException | IOException e ) {
            logger.error( e.getLocalizedMessage() );
        }

        return buffer;
    }
}