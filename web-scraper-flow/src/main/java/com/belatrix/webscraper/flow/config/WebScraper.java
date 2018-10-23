package com.belatrix.webscraper.flow.config;

import com.belatrix.webscraper.api.flow.ProgramFlow;
import com.belatrix.webscraper.api.input.UrlListLoader;
import com.belatrix.webscraper.api.output.PatternTokenOutputExtractor;
import com.belatrix.webscraper.api.pattern.PatternLoader;
import com.belatrix.webscraper.api.processor.OutputTokenProcessor;
import com.belatrix.webscraper.api.url.UrlContentExtractor;
import com.belatrix.webscraper.flow.ProgramFlowImpl;
import com.belatrix.webscraper.input.UrlListLoaderImpl;
import com.belatrix.webscraper.output.PatternTokenOutputExtractorImpl;
import com.belatrix.webscraper.pattern.PatternLoaderImpl;
import com.belatrix.webscraper.processor.OutputTokenProcessorImpl;
import com.belatrix.webscraper.url.UrlContentExtractorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile( "default" )
@SpringBootApplication
@PropertySource( "web-scraper.properties" )
public class WebScraper {
    private static final Logger logger = LoggerFactory.getLogger( ProgramFlowImpl.class );

    public static void main( String[] args ) {
        SpringApplication.run( WebScraper.class, args );
    }

    @Bean
    public CommandLineRunner commandLineRunner( ProgramFlow programFlow ) {
        return args -> initialLauncher( args, programFlow );
    }

    @Bean
    public ProgramFlow programFlow( @Value( "${output.path}" ) String outputPrefix, @Value( "${pattern.path}" ) String patternPath ) {
        return new ProgramFlowImpl( outputPrefix, patternPath );
    }

    @Bean
    public UrlListLoader urlListLoader() {
        return new UrlListLoaderImpl();
    }

    @Bean
    public PatternLoader patternLoader() {
        return new PatternLoaderImpl();
    }

    @Bean
    public UrlContentExtractor urlContentExtractor() {
        return new UrlContentExtractorImpl();
    }

    @Bean
    public OutputTokenProcessor outputTokenProcessor() {
        return new OutputTokenProcessorImpl();
    }

    @Bean
    public PatternTokenOutputExtractor patternTokenOutputExtractor() {
        return new PatternTokenOutputExtractorImpl();
    }

    private void initialLauncher( String[] args, ProgramFlow programFlow ) {
        if ( 1 != args.length ) {
            logger.error( "Please provide the path for the file that contains the URLs" );
        } else {
            try {
                programFlow.scraperFlow( args[0] );
            } catch ( Exception e ) {
                logger.error( e.getLocalizedMessage() );
            }
        }
    }
}