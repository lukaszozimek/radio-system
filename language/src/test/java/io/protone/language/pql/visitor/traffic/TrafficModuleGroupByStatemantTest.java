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
public class TrafficModuleGroupByStatemantTest {
    private final Logger log = LoggerFactory.getLogger(TrafficModuleGroupByStatemantTest.class);

    @Test
    public void simpleTrafficTraAdvertisementQuerry() throws IOException {

        String simpleQuery = "Traffic Advertisement GROUP BY network";
        final String EXPECTED_JPA_QUERY = "SELECT a FROM TraAdvertisement a GROUP BY a.network";

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

        String simpleQuery = "Traffic Campaign GROUP BY network";
        final String EXPECTED_JPA_QUERY = "SELECT c FROM TraCampaign c GROUP BY c.network";

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

        String simpleQuery = "Traffic Invoice GROUP BY network";
        final String EXPECTED_JPA_QUERY = "SELECT i FROM TraInvoice i GROUP BY i.network";

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

        String simpleQuery = "Traffic Media Plan GROUP BY network";
        final String EXPECTED_JPA_QUERY = "SELECT m FROM TraMediaPlan m GROUP BY m.network";

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

        String simpleQuery = "Traffic Order GROUP BY network";
        final String EXPECTED_JPA_QUERY = "SELECT o FROM TraOrder o GROUP BY o.network";

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

        String simpleQuery = "Traffic Playlist GROUP BY network";
        final String EXPECTED_JPA_QUERY = "SELECT p FROM TraPlaylist p GROUP BY p.network";

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
