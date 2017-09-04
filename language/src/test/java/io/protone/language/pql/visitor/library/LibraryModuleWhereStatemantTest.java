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
public class LibraryModuleWhereStatemantTest {
    private final Logger log = LoggerFactory.getLogger(LibraryModuleWhereStatemantTest.class);

    @Test
    public void simpleMediaItemyQuery() throws IOException {

        String simpleQuery = "Library  MediaItem AND id=1";
        final String EXPECTED_JPA_QUERY = "SELECT m FROM LibMediaItem m WHERE m.id=1";

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
    public void simpleMediaItemyORQuery() throws IOException {

        String simpleQuery = "Library  MediaItem AND id=1 OR id=2";
        final String EXPECTED_JPA_QUERY = "SELECT m FROM LibMediaItem m WHERE m.id=1 OR m.id=2";

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

        String simpleQuery = "Library  Artist AND id=1";
        final String EXPECTED_JPA_QUERY = "SELECT a FROM LibArtist a WHERE a.id=1";

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
    public void simpleArtistORQuery() throws IOException {

        String simpleQuery = "Library  Artist AND id=1 OR id=2";
        final String EXPECTED_JPA_QUERY = "SELECT a FROM LibArtist a WHERE a.id=1 OR a.id=2";

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

        String simpleQuery = "Library  Library AND id=1";
        final String EXPECTED_JPA_QUERY = "SELECT l FROM LibMediaLibrary l WHERE l.id=1";

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
    public void simpleLibraryORQuery() throws IOException {

        String simpleQuery = "Library  Library AND id=1 OR id=2";
        final String EXPECTED_JPA_QUERY = "SELECT l FROM LibMediaLibrary l WHERE l.id=1 OR l.id=2";

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

        String simpleQuery = "Library  Label AND id=1";
        final String EXPECTED_JPA_QUERY = "SELECT l FROM LibLabel l WHERE l.id=1";

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
    public void simpleLabelORQuery() throws IOException {

        String simpleQuery = "Library  Label AND id=1 OR id=2";
        final String EXPECTED_JPA_QUERY = "SELECT l FROM LibLabel l WHERE l.id=1 OR l.id=2";

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

        String simpleQuery = "Library Album AND id=1";
        final String EXPECTED_JPA_QUERY = "SELECT a FROM LibAlbum a WHERE a.id=1";

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
    public void simpleAlbumORQuery() throws IOException {

        String simpleQuery = "Library Album AND id=1 OR id=2";
        final String EXPECTED_JPA_QUERY = "SELECT a FROM LibAlbum a WHERE a.id=1 OR a.id=2";

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

        String simpleQuery = "Library Track AND id=1";
        final String EXPECTED_JPA_QUERY = "SELECT t FROM LibTrack t WHERE t.id=1";

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
    public void simpleTrackORQuery() throws IOException {

        String simpleQuery = "Library Track AND id=1 OR id=2";
        final String EXPECTED_JPA_QUERY = "SELECT t FROM LibTrack t WHERE t.id=1 OR t.id=2";

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
