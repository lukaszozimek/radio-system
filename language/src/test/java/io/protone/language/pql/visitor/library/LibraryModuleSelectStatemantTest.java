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
public class LibraryModuleSelectStatemantTest {
    private final Logger log = LoggerFactory.getLogger(LibraryModuleSelectStatemantTest.class);

    @Test
    public void simpleMediaItemyQuery() throws IOException {

        String simpleQuery = "Library  MediaItem";
        final String EXPECTED_JPA_QUERY = "SELECT m FROM LibMediaItem m";

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

        String simpleQuery = "Library  Artist";
        final String EXPECTED_JPA_QUERY = "SELECT a FROM LibArtist a";

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

        String simpleQuery = "Library  Library";
        final String EXPECTED_JPA_QUERY = "SELECT l FROM LibMediaLibrary l";

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

        String simpleQuery = "Library  Label";
        final String EXPECTED_JPA_QUERY = "SELECT l FROM LibLabel l";

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

        String simpleQuery = "Library Album";
        final String EXPECTED_JPA_QUERY = "SELECT a FROM LibAlbum a";

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

        String simpleQuery = "Library Track ";
        final String EXPECTED_JPA_QUERY = "SELECT t FROM LibTrack t";

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
