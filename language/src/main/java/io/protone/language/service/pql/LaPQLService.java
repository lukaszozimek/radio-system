package io.protone.language.service.pql;

import io.protone.core.domain.CorFilter;
import io.protone.language.pal.impl.ProtoneAutomationLanguageListenerImpl;
import io.protone.language.pql.impl.ProtoneQueryLanguageVisitorImpl;
import org.antlr.v4.runtime.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Created by lukaszozimek on 13/07/2017.
 */
@Service
public class LaPQLService {

    @Inject
    private EntityManager em;

    public List getObjectList(CorFilter corFilter) throws IOException {
        String jpaQuery =getJpaQuery(corFilter);
        Query query = em.createQuery(jpaQuery);
        return query.getResultList();
    }


    private String getJpaQuery(CorFilter corFilter) throws IOException {

        CharStream inputCharStream = CharStreams.fromReader(new StringReader(corFilter.getValue()));
        TokenSource tokenSource = new io.protone.language.pql.ProtoneQueryLanguageLexer(inputCharStream);
        TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
        io.protone.language.pql.ProtoneQueryLanguageParser parser = new io.protone.language.pql.ProtoneQueryLanguageParser(inputTokenStream);
        io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext context = parser.program();
        ProtoneQueryLanguageVisitorImpl visitor = new ProtoneQueryLanguageVisitorImpl();
        return visitor.visit(context);
    }
}
