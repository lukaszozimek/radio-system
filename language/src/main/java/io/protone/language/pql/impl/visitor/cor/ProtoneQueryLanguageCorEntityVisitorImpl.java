package io.protone.language.pql.impl.visitor.cor;

import com.google.common.base.Strings;
import io.protone.core.domain.*;
import io.protone.language.pql.ProtoneQueryLanguageBaseVisitor;
import io.protone.language.pql.ProtoneQueryLanguageParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class ProtoneQueryLanguageCorEntityVisitorImpl extends ProtoneQueryLanguageBaseVisitor<String> {
    private static final String SPACE = " ";
    private Map<String, Class> pqlEntityMap = new HashMap<>();

    private char aliasVariable;

    public ProtoneQueryLanguageCorEntityVisitorImpl() {
        pqlEntityMap.put("Person", CorPerson.class);
        pqlEntityMap.put("Image", CorImageItem.class);
        pqlEntityMap.put("Channel", CorChannel.class);
        pqlEntityMap.put("Adress", CorAddress.class);
        pqlEntityMap.put("Tag", CorTag.class);
        pqlEntityMap.put("Contact", CorContact.class);
        pqlEntityMap.put("Property", CorPropertyKey.class);
    }


    @Override
    public String visitCor_entity(ProtoneQueryLanguageParser.Cor_entityContext ctx) {
        if (pqlEntityMap.get(ctx.getText().trim()) == null) {
            return null;
        }
        aliasVariable = ctx.getText().toLowerCase().toCharArray()[0];
        return "SELECT " + aliasVariable + " FROM " + pqlEntityMap.get(ctx.getText()).getSimpleName().trim();
    }

    @Override
    public String visitWhere_clause(ProtoneQueryLanguageParser.Where_clauseContext ctx) {

        return "WHERE " + visitChildren(ctx);
    }


    @Override
    public String visitConditional_expression(ProtoneQueryLanguageParser.Conditional_expressionContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public String visitConditional_term(ProtoneQueryLanguageParser.Conditional_termContext ctx) {

        return visitChildren(ctx);
    }

    @Override
    public String visitSimple_cond_expression(ProtoneQueryLanguageParser.Simple_cond_expressionContext ctx) {

        return aliasVariable + "." + ctx.getText();
    }

    @Override
    public String visitConditional_primary(ProtoneQueryLanguageParser.Conditional_primaryContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public String visitConditional_factor(ProtoneQueryLanguageParser.Conditional_factorContext ctx) {

        return visitChildren(ctx);

    }


    @Override
    protected String aggregateResult(String aggregate, String nextResult) {

        if (Strings.isNullOrEmpty(nextResult)) {
            return aggregate;
        }

        if (Strings.isNullOrEmpty(aggregate) && !Strings.isNullOrEmpty(nextResult)) {
            aggregate = "";
            return aggregate.concat(nextResult);
        }

        if (Strings.isNullOrEmpty(nextResult)) {
            return aggregate;
        }
        return aggregate.concat(SPACE).concat(nextResult);
    }


}
