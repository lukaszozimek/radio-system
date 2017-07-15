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
public class CrmModuleWhereStatemantTest {
    private final Logger log = LoggerFactory.getLogger(CrmModuleWhereStatemantTest.class);


    @Test
    public void simpleCustomerQuery() throws IOException {

        String simpleQuery = "Crm Customer WHERE id=1";
        final String EXPECTED_JPA_QUERY = "SELECT * FROM CrmAccount WHERE id=1";

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

        String simpleQuery = "Crm Lead WHERE id=1";
        final String EXPECTED_JPA_QUERY = "SELECT * FROM CrmLead WHERE id=1";

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

        String simpleQuery = "Crm Opportunity WHERE id=1";
        final String EXPECTED_JPA_QUERY = "SELECT * FROM CrmOpportunity WHERE id=1";

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

        String simpleQuery = "Crm Contact WHERE id='1'";
        final String EXPECTED_JPA_QUERY = "SELECT * FROM CrmContact WHERE id=1";

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

        String simpleQuery = "Crm Task WHERE id=1";
        final String EXPECTED_JPA_QUERY = "SELECT * FROM CrmTask WHERE id=1";

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
