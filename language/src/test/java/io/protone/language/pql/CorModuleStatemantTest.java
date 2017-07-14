package io.protone.language.pql;

import io.protone.language.pal.impl.ProtoneAutomationLanguageListenerImpl;
import org.antlr.v4.runtime.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class CorModuleStatemantTest {
    private final Logger log = LoggerFactory.getLogger(CorModuleStatemantTest.class);


    @Test
    public void simpleCorPersonQuery() throws IOException {
        String simpleQuery = "Core Person ";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        log.info(context.toString());

    }

    @Test
    public void simpleCorTagQuery() throws IOException {
        String simpleQuery = "Core Tag ";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();
        log.info(context.toString());

    }

    @Test
    public void simpleCorImageItemQuery() throws IOException {
        String simpleQuery = "Core  ImageItem ";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        log.info(context.toString());

    }

    @Test
    public void simpleCorChannelQuery() throws IOException {
        String simpleQuery = "Core  Channel ";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());

        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();
        log.info(context.toString());

    }

    @Test
    public void simpleCorContactQuery() throws IOException {
        String simpleQuery = "Core  Contact ";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();
        log.info(context.toString());

    }

    @Test
    public void simpleCorAdressQuery() throws IOException {
        String simpleQuery = "Core  Adress ";
        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();
        log.info(context.toString());

    }

    @Test
    public void simpleCorPropertyQuery() throws IOException {
        String simpleQuery = "Core  Property ";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        log.info(context.toString());

    }

}
