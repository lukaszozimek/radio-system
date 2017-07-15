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

        String simpleQuery = "Traffic Advertisement AND id=1";
        final String EXPECTED_JPA_QUERY = "SELECT a FROM TraAdvertisement WHERE a.id=1";

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

        String simpleQuery = "Traffic Campaign AND id=1";
        final String EXPECTED_JPA_QUERY = "SELECT c FROM TraCampaign WHERE c.id=1";

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

        String simpleQuery = "Traffic Invoice AND id=1";
        final String EXPECTED_JPA_QUERY = "SELECT i FROM TraInvoice WHERE i.id=1";

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

        String simpleQuery = "Traffic Media Plan AND id=1";
        final String EXPECTED_JPA_QUERY = "SELECT m FROM TraMediaPlan WHERE m.id=1";

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

        String simpleQuery = "Traffic Order AND id=1";
        final String EXPECTED_JPA_QUERY = "SELECT o FROM TraOrder WHERE o.id=1";

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

        String simpleQuery = "Traffic Playlist AND id=1";
        final String EXPECTED_JPA_QUERY = "SELECT p FROM TraPlaylist WHERE p.id=1";

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
