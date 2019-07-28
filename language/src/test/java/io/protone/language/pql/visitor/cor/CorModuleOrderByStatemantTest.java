package io.protone.language.pql.visitor.cor;

import io.protone.language.pal.impl.ProtoneAutomationLanguageListenerImpl;
import io.protone.language.pql.impl.visitor.cor.ProtoneQueryLanguageCorEntityVisitorImpl;
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
public class CorModuleOrderByStatemantTest {
    private final Logger log = LoggerFactory.getLogger(CorModuleOrderByStatemantTest.class);


    @Test
    public void simpleCorPersonQuery() throws IOException {
        String simpleQuery = "Core Person ORDER BY id";
        final String EXPECTED_JPA_QUERY = "SELECT p FROM CorPerson p ORDER BY p.id ASC";


        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();
        ProtoneQueryLanguageCorEntityVisitorImpl visitor = new ProtoneQueryLanguageCorEntityVisitorImpl();
        String parseValue = visitor.visit(context);

        assertEquals(EXPECTED_JPA_QUERY, parseValue);
    }

    @Test
    public void simpleCorTagQuery() throws IOException {
        String simpleQuery = "Core Tag ORDER BY id";
        final String EXPECTED_JPA_QUERY = "SELECT t FROM CorTag t ORDER BY t.id ASC";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        ProtoneQueryLanguageCorEntityVisitorImpl visitor = new ProtoneQueryLanguageCorEntityVisitorImpl();
        String parseValue = visitor.visit(context);

        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }

    @Test
    public void simpleCorImageItemQuery() throws IOException {
        String simpleQuery = "Core  Image ORDER BY id";
        final String EXPECTED_JPA_QUERY = "SELECT i FROM CorImageItem i ORDER BY i.id ASC";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        ProtoneQueryLanguageCorEntityVisitorImpl visitor = new ProtoneQueryLanguageCorEntityVisitorImpl();
        String parseValue = visitor.visit(context);

        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }

    @Test
    public void simpleCorChannelQuery() throws IOException {
        String simpleQuery = "Core  Channel ORDER BY id";
        final String EXPECTED_JPA_QUERY = "SELECT c FROM CorChannel c ORDER BY c.id ASC";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        ProtoneQueryLanguageCorEntityVisitorImpl visitor = new ProtoneQueryLanguageCorEntityVisitorImpl();
        String parseValue = visitor.visit(context);

        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }

    @Test
    public void simpleCorContactQuery() throws IOException {
        String simpleQuery = "Core  Contact ORDER BY id";
        final String EXPECTED_JPA_QUERY = "SELECT c FROM CorContact c ORDER BY c.id ASC";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        ProtoneQueryLanguageCorEntityVisitorImpl visitor = new ProtoneQueryLanguageCorEntityVisitorImpl();
        String parseValue = visitor.visit(context);

        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }



    @Test
    public void simpleCorPropertyQuery() throws IOException {
        String simpleQuery = "Core  Property ORDER BY id ASC";
        final String EXPECTED_JPA_QUERY = "SELECT p FROM CorPropertyKey p ORDER BY p.id ASC";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        ProtoneQueryLanguageCorEntityVisitorImpl visitor = new ProtoneQueryLanguageCorEntityVisitorImpl();
        String parseValue = visitor.visit(context);

        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }

}
