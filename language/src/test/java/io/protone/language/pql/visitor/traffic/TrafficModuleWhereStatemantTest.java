package io.protone.language.pql.visitor.traffic;

import io.protone.language.pal.impl.ProtoneAutomationLanguageListenerImpl;
import io.protone.language.pql.impl.visitor.traffic.ProtoneQueryLanguageTrafficEntityVisitorImpl;
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
public class TrafficModuleWhereStatemantTest {
    private final Logger log = LoggerFactory.getLogger(TrafficModuleWhereStatemantTest.class);

    @Test
    public void simpleTrafficTraAdvertisementQuerry() throws IOException {

        String simpleQuery = "Traffic Advertisement WHERE id=1";
        final String EXPECTED_JPA_QUERY = "SELECT * FROM TraAdvertisement WHERE id=1 ";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        ProtoneQueryLanguageTrafficEntityVisitorImpl visitor = new ProtoneQueryLanguageTrafficEntityVisitorImpl();
        String parseValue = visitor.visit(context);


        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }
    @Test
    public void simpleTrafficTraCampaignQuerry() throws IOException {

        String simpleQuery = "Traffic Campaign WHERE id=1";
        final String EXPECTED_JPA_QUERY = "SELECT * FROM TraCampaign WHERE id=1";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        ProtoneQueryLanguageTrafficEntityVisitorImpl visitor = new ProtoneQueryLanguageTrafficEntityVisitorImpl();
        String parseValue = visitor.visit(context);

        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }
    @Test
    public void simpleTrafficTraInvoiceQuerry() throws IOException {

        String simpleQuery = "Traffic Invoice WHERE id=1";
        final String EXPECTED_JPA_QUERY = "SELECT * FROM TraInvoice WHERE id=1";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();
        ProtoneQueryLanguageTrafficEntityVisitorImpl visitor = new ProtoneQueryLanguageTrafficEntityVisitorImpl();
        String parseValue = visitor.visit(context);


        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }
    @Test
    public void simpleTrafficTraMediaPlanQuerry() throws IOException {

        String simpleQuery = "Traffic Media Plan WHERE id=1";
        final String EXPECTED_JPA_QUERY = "SELECT * FROM TraMediaPlan WHERE id=1";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();
        ProtoneQueryLanguageTrafficEntityVisitorImpl visitor = new ProtoneQueryLanguageTrafficEntityVisitorImpl();
        String parseValue = visitor.visit(context);


        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }
    @Test
    public void simpleTrafficTraOrderQuerry() throws IOException {

        String simpleQuery = "Traffic Order WHERE id=1";
        final String EXPECTED_JPA_QUERY = "SELECT * FROM TraOrder WHERE id=1";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();
        ProtoneQueryLanguageTrafficEntityVisitorImpl visitor = new ProtoneQueryLanguageTrafficEntityVisitorImpl();
        String parseValue = visitor.visit(context);


        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }

    @Test
    public void simpleTrafficTraPlaylistQuerry() throws IOException {

        String simpleQuery = "Traffic Playlist WHERE id=1";
        final String EXPECTED_JPA_QUERY = "SELECT * FROM TraPlaylist WHERE id=1";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        ProtoneQueryLanguageTrafficEntityVisitorImpl visitor = new ProtoneQueryLanguageTrafficEntityVisitorImpl();
        String parseValue = visitor.visit(context);
        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }


}
