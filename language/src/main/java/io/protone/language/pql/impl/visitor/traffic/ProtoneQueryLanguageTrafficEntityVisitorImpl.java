package io.protone.language.pql.impl.visitor.traffic;

import com.google.common.base.Strings;
import io.protone.crm.domain.*;
import io.protone.language.pql.ProtoneQueryLanguageBaseVisitor;
import io.protone.language.pql.ProtoneQueryLanguageParser;
import io.protone.traffic.domain.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class ProtoneQueryLanguageTrafficEntityVisitorImpl extends ProtoneQueryLanguageBaseVisitor<String> {
    private static final String SPACE = " ";
    private Map<String, Class> pqlEntityMap = new HashMap<>();
    private char aliasVariable;

    public ProtoneQueryLanguageTrafficEntityVisitorImpl() {

        pqlEntityMap.put("Advertisement", TraAdvertisement.class);
        pqlEntityMap.put("Campaign", TraCampaign.class);
        pqlEntityMap.put("Order", TraOrder.class);
        pqlEntityMap.put("Invoice", TraInvoice.class);
        pqlEntityMap.put("Media Plan", TraMediaPlan.class);
        pqlEntityMap.put("Playlist", TraPlaylist.class);

    }


    @Override
    public String visitTraffic_entity(ProtoneQueryLanguageParser.Traffic_entityContext ctx) {

        if (pqlEntityMap.get(ctx.getText().trim()).getSimpleName() == null) {
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
    public String visitGroupby_clause(ProtoneQueryLanguageParser.Groupby_clauseContext ctx) {
        return "GROUP BY " + visitChildren(ctx);
    }


    @Override
    public String visitGroupby_item(ProtoneQueryLanguageParser.Groupby_itemContext ctx) {
        return aliasVariable + "." + ctx.getText();
    }

    @Override
    public String visitOrderby_clause(ProtoneQueryLanguageParser.Orderby_clauseContext ctx) {
        return "ORDER BY " + visitChildren(ctx);
    }


    @Override
    public String visitOrderby_item(ProtoneQueryLanguageParser.Orderby_itemContext ctx) {
        return aliasVariable + "." + ctx.getChild(0).getText() + visitChildren(ctx);
    }

    @Override
    public String visitAsc_desc(ProtoneQueryLanguageParser.Asc_descContext ctx) {
        return " " + ctx.getText();
    }

    @Override
    public String visitConditional_expression(ProtoneQueryLanguageParser.Conditional_expressionContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public String visitOr_expession(ProtoneQueryLanguageParser.Or_expessionContext ctx) {
        return "OR";
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
