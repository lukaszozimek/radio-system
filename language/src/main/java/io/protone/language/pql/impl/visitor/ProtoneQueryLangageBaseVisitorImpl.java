package io.protone.language.pql.impl.visitor;

import com.google.common.base.Strings;
import io.protone.language.pql.ProtoneQueryLanguageBaseVisitor;
import io.protone.language.pql.ProtoneQueryLanguageParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszozimek on 16/07/2017.
 */
public class ProtoneQueryLangageBaseVisitorImpl extends ProtoneQueryLanguageBaseVisitor<String> {
    protected static final String SPACE = " ";
    protected Map<String, Class> pqlEntityMap = new HashMap<>();

    protected char aliasVariable;


    @Override
    public String visitWhere_clause(ProtoneQueryLanguageParser.Where_clauseContext ctx) {

        return "WHERE " + visitChildren(ctx);
    }


    @Override
    public String visitAnd_expression(ProtoneQueryLanguageParser.And_expressionContext ctx) {
        return "AND";
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
    public String visitOr_expession(ProtoneQueryLanguageParser.Or_expessionContext ctx) {
        return "OR";
    }

    @Override
    public String visitAsc_desc(ProtoneQueryLanguageParser.Asc_descContext ctx) {
        if (!Strings.isNullOrEmpty(ctx.getText())) {
            return " " + ctx.getText();
        }
        return " ASC";
    }

    @Override
    public String visitConditional_expression(ProtoneQueryLanguageParser.Conditional_expressionContext ctx) {
//        if (ctx.getStart().getText().equals("(")) {
//            return ctx.getStart().getText() + visitChildren(ctx);
//        }
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
        if (ctx.getText().split("AND").length > 1) {
            return handleNestedProperties(ctx);
        }
        if (visitChildren(ctx) != null) {
            return visitChildren(ctx);
        }
        return aliasVariable + "." + ctx.getText();
    }

    @Override
    public String visitLike_expression(ProtoneQueryLanguageParser.Like_expressionContext ctx) {
        return aliasVariable + "." + ctx.getStart().getText() + visitChildren(ctx);
    }

    @Override
    public String visitLike_keyword(ProtoneQueryLanguageParser.Like_keywordContext ctx) {
        return " LIKE";
    }

    @Override
    public String visitPattern_value(ProtoneQueryLanguageParser.Pattern_valueContext ctx) {
        return "'%" + ctx.getStart().getText().replace("'", "") + "%'";
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
    public String visitStart_complex_expression(ProtoneQueryLanguageParser.Start_complex_expressionContext ctx) {
        return "(";
    }

    /**
     * Visit a parse tree produced by {@link ProtoneQueryLanguageParser#end_complex_expression}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    @Override
    public String visitEnd_complex_expression(ProtoneQueryLanguageParser.End_complex_expressionContext ctx) {
        return ")";
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

    private String handleNestedProperties(ProtoneQueryLanguageParser.Simple_cond_expressionContext ctx) {
        StringBuilder concatedElement = new StringBuilder();
        String[] splittedCondition = ctx.getText().split("AND");
        for (int i = 0; i < splittedCondition.length; i++) {
            if ((splittedCondition.length - i) - 1 != 0) {
                concatedElement.append(aliasVariable).append(".").append(splittedCondition[i].trim()).append(" AND ");
            } else {
                concatedElement.append(aliasVariable).append(".").append(splittedCondition[i].trim());
            }

        }
        if (visitChildren(ctx) != null) {
            return concatedElement.toString() + visitChildren(ctx);
        }
        return concatedElement.toString();
    }

}
