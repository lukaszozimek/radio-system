package io.protone.language.pql.visitor.crm;

import io.protone.language.pal.impl.ProtoneAutomationLanguageListenerImpl;
import io.protone.language.pql.impl.visitor.crm.ProtoneQueryLanguageCrmEntityVisitorImpl;
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
public class CrmModuleSelectStatemantTest {
    private final Logger log = LoggerFactory.getLogger(CrmModuleSelectStatemantTest.class);


    @Test
    public void simpleCustomerQuery() throws IOException {

        String simpleQuery = "Crm Customer";
        final String EXPECTED_JPA_QUERY = "SELECT c FROM CrmAccount c";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        ProtoneQueryLanguageCrmEntityVisitorImpl visitor = new ProtoneQueryLanguageCrmEntityVisitorImpl();
        String parseValue = visitor.visit(context);

        assertEquals(EXPECTED_JPA_QUERY, parseValue);

        log.info(context.toString());


    }

    @Test
    public void simpleLeadQuery() throws IOException {

        String simpleQuery = "Crm Lead";
        final String EXPECTED_JPA_QUERY = "SELECT l FROM CrmLead l";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        ProtoneQueryLanguageCrmEntityVisitorImpl visitor = new ProtoneQueryLanguageCrmEntityVisitorImpl();
        String parseValue = visitor.visit(context);

        assertEquals(EXPECTED_JPA_QUERY, parseValue);


    }

    @Test
    public void simpleOpportunityQuery() throws IOException {

        String simpleQuery = "Crm Opportunity";
        final String EXPECTED_JPA_QUERY = "SELECT o FROM CrmOpportunity o";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        ProtoneQueryLanguageCrmEntityVisitorImpl visitor = new ProtoneQueryLanguageCrmEntityVisitorImpl();
        String parseValue = visitor.visit(context);

        assertEquals(EXPECTED_JPA_QUERY, parseValue);


    }

    @Test
    public void simpleContactQuery() throws IOException {

        String simpleQuery = "Crm Contact";
        final String EXPECTED_JPA_QUERY = "SELECT c FROM CrmContact c";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        ProtoneQueryLanguageCrmEntityVisitorImpl visitor = new ProtoneQueryLanguageCrmEntityVisitorImpl();
        String parseValue = visitor.visit(context);

        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }

    @Test
    public void simpleTaskQuery() throws IOException {

        String simpleQuery = "Crm Task";
        final String EXPECTED_JPA_QUERY = "SELECT t FROM CrmTask t";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addParseListener(new ProtoneAutomationLanguageListenerImpl());
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        ProtoneQueryLanguageCrmEntityVisitorImpl visitor = new ProtoneQueryLanguageCrmEntityVisitorImpl();
        String parseValue = visitor.visit(context);

        assertEquals(EXPECTED_JPA_QUERY, parseValue);

    }
}
