package io.protone.language.pql;

import org.antlr.v4.runtime.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class SchedulerModuleStatemantTest {
    private final Logger log = LoggerFactory.getLogger(SchedulerModuleStatemantTest.class);

    @Test
    public void simpleSchedulerQuery() throws IOException {

        String simpleQuery = "Scheduler  Event";

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(simpleQuery));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);


        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        log.info(context.toString());
    }
}
