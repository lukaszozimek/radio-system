package io.protone.language.pql.visitor.library;

import io.protone.language.pql.impl.visitor.library.ProtoneQueryLanguageLibraryEntityVisitorImpl;
import org.antlr.v4.runtime.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class LibraryModuleGroupByStatemantTest {
    private final Logger log = LoggerFactory.getLogger(LibraryModuleGroupByStatemantTest.class);

    @Test
    public void simpleMediaItemyQuery() throws IOException {

        String simpleQuery = "Library  MediaItem GROUP BY";
        final String EXPECTED_JPA_QUERY = "SELECT m FROM LibMediaItem GROUP BY";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        ProtoneQueryLanguageLibraryEntityVisitorImpl visitor = new ProtoneQueryLanguageLibraryEntityVisitorImpl();
        String parseValue = visitor.visit(context);


        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }
    @Test
    public void simpleArtistQuery() throws IOException {

        String simpleQuery = "Library  Artist GROUP BY";
        final String EXPECTED_JPA_QUERY = "SELECT a FROM LibArtist GROUP BY";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();
        ProtoneQueryLanguageLibraryEntityVisitorImpl visitor = new ProtoneQueryLanguageLibraryEntityVisitorImpl();
        String parseValue = visitor.visit(context);


        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }

    @Test
    public void simpleLibraryQuery() throws IOException {

        String simpleQuery = "Library  Library GROUP BY";
        final String EXPECTED_JPA_QUERY = "SELECT l FROM LibLibrary GROUP BY";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();
        ProtoneQueryLanguageLibraryEntityVisitorImpl visitor = new ProtoneQueryLanguageLibraryEntityVisitorImpl();
        String parseValue = visitor.visit(context);


        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }

    @Test
    public void simpleLabelQuery() throws IOException {

        String simpleQuery = "Library  Label GROUP BY";
        final String EXPECTED_JPA_QUERY = "SELECT l FROM LibLabel GROUP BY";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();
        ProtoneQueryLanguageLibraryEntityVisitorImpl visitor = new ProtoneQueryLanguageLibraryEntityVisitorImpl();
        String parseValue = visitor.visit(context);


        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }

    @Test
    public void simpleAlbumQuery() throws IOException {

        String simpleQuery = "Library Album GROUP BY";
        final String EXPECTED_JPA_QUERY = "SELECT a FROM LibAlbum GROUP BY";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();
        ProtoneQueryLanguageLibraryEntityVisitorImpl visitor = new ProtoneQueryLanguageLibraryEntityVisitorImpl();
        String parseValue = visitor.visit(context);


        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }

    @Test
    public void simpleTrackQuery() throws IOException {

        String simpleQuery = "Library Track GROUP BY";
        final String EXPECTED_JPA_QUERY = "SELECT t FROM LibTrack GROUP BY";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();
        ProtoneQueryLanguageLibraryEntityVisitorImpl visitor = new ProtoneQueryLanguageLibraryEntityVisitorImpl();
        String parseValue = visitor.visit(context);


        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }
}
