package io.protone.pql;

import io.protone.core.service.CorAddressService;
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
    public void testExploratoryString() throws IOException {

        String simplestProgram = "CORE  Person WHERE x = z" ;

        CharStream inputCharStream = new ANTLRInputStream(new StringReader(simplestProgram));
        TokenSource tokenSource = new io.protone.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);

        io.protone.pql.ProtoneQueryLanguageParser parser = new io.protone.pql.ProtoneQueryLanguageParser(inputTokenStream);
        parser.addErrorListener(new TestErrorListener());

        io.protone.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();

        log.info(context.toString());


    }
}
