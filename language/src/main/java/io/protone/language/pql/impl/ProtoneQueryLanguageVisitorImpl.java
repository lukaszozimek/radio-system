package io.protone.language.pql.impl;

import io.protone.language.pql.ProtoneQueryLanguageParser;
import io.protone.language.pql.ProtoneQueryLanguageVisitor;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class ProtoneQueryLanguageVisitorImpl implements ProtoneQueryLanguageVisitor<Void> {
    @Override
    public Void visitProgram(io.protone.language.pql.ProtoneQueryLanguageParser.ProgramContext ctx) {
        return null;
    }

    @Override
    public Void visitQl_module(ProtoneQueryLanguageParser.Ql_moduleContext ctx) {
        return null;
    }

    @Override
    public Void visitCor_module_statemant(ProtoneQueryLanguageParser.Cor_module_statemantContext ctx) {
        return null;
    }

    @Override
    public Void visitTraffic_module_statemant(ProtoneQueryLanguageParser.Traffic_module_statemantContext ctx) {
        return null;
    }

    @Override
    public Void visitCrm_module_statemant(ProtoneQueryLanguageParser.Crm_module_statemantContext ctx) {
        return null;
    }

    @Override
    public Void visitLibrary_module_statemant(ProtoneQueryLanguageParser.Library_module_statemantContext ctx) {
        return null;
    }

    @Override
    public Void visitScheduler_module_statemant(ProtoneQueryLanguageParser.Scheduler_module_statemantContext ctx) {
        return null;
    }

    @Override
    public Void visitCor_entity(ProtoneQueryLanguageParser.Cor_entityContext ctx) {
        return null;
    }

    @Override
    public Void visitTraffic_entity(ProtoneQueryLanguageParser.Traffic_entityContext ctx) {
        return null;
    }

    @Override
    public Void visitCrm_entity(ProtoneQueryLanguageParser.Crm_entityContext ctx) {
        return null;
    }

    @Override
    public Void visitLibrary_entity(ProtoneQueryLanguageParser.Library_entityContext ctx) {
        return null;
    }

    @Override
    public Void visitScheduler_entity(ProtoneQueryLanguageParser.Scheduler_entityContext ctx) {
        return null;
    }

    @Override
    public Void visitCor_module(ProtoneQueryLanguageParser.Cor_moduleContext ctx) {
        return null;
    }

    @Override
    public Void visitTraffic_module(ProtoneQueryLanguageParser.Traffic_moduleContext ctx) {
        return null;
    }

    @Override
    public Void visitCrm_module(ProtoneQueryLanguageParser.Crm_moduleContext ctx) {
        return null;
    }

    @Override
    public Void visitLibrary_module(ProtoneQueryLanguageParser.Library_moduleContext ctx) {
        return null;
    }

    @Override
    public Void visitScheduler_module(ProtoneQueryLanguageParser.Scheduler_moduleContext ctx) {
        return null;
    }

    @Override
    public Void visitCollection_member_declaration(ProtoneQueryLanguageParser.Collection_member_declarationContext ctx) {
        return null;
    }

    @Override
    public Void visitSingle_valued_path_expression(ProtoneQueryLanguageParser.Single_valued_path_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitState_field_path_expression(ProtoneQueryLanguageParser.State_field_path_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitSingle_valued_association_path_expression(ProtoneQueryLanguageParser.Single_valued_association_path_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitCollection_valued_path_expression(ProtoneQueryLanguageParser.Collection_valued_path_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitState_field(ProtoneQueryLanguageParser.State_fieldContext ctx) {
        return null;
    }

    @Override
    public Void visitSelect_expression(ProtoneQueryLanguageParser.Select_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitConstructor_expression(ProtoneQueryLanguageParser.Constructor_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitConstructor_item(ProtoneQueryLanguageParser.Constructor_itemContext ctx) {
        return null;
    }

    @Override
    public Void visitAggregate_expression(ProtoneQueryLanguageParser.Aggregate_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitWhere_clause(ProtoneQueryLanguageParser.Where_clauseContext ctx) {
        return null;
    }

    @Override
    public Void visitGroupby_clause(ProtoneQueryLanguageParser.Groupby_clauseContext ctx) {
        return null;
    }

    @Override
    public Void visitGroupby_item(ProtoneQueryLanguageParser.Groupby_itemContext ctx) {
        return null;
    }

    @Override
    public Void visitHaving_clause(ProtoneQueryLanguageParser.Having_clauseContext ctx) {
        return null;
    }

    @Override
    public Void visitOrderby_clause(ProtoneQueryLanguageParser.Orderby_clauseContext ctx) {
        return null;
    }

    @Override
    public Void visitOrderby_item(ProtoneQueryLanguageParser.Orderby_itemContext ctx) {
        return null;
    }

    @Override
    public Void visitAssociation_path_expression(ProtoneQueryLanguageParser.Association_path_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitConditional_expression(ProtoneQueryLanguageParser.Conditional_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitConditional_term(ProtoneQueryLanguageParser.Conditional_termContext ctx) {
        return null;
    }

    @Override
    public Void visitConditional_factor(ProtoneQueryLanguageParser.Conditional_factorContext ctx) {
        return null;
    }

    @Override
    public Void visitConditional_primary(ProtoneQueryLanguageParser.Conditional_primaryContext ctx) {
        return null;
    }

    @Override
    public Void visitSimple_cond_expression(ProtoneQueryLanguageParser.Simple_cond_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitBetween_expression(ProtoneQueryLanguageParser.Between_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitIn_expression(ProtoneQueryLanguageParser.In_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitIn_item(ProtoneQueryLanguageParser.In_itemContext ctx) {
        return null;
    }

    @Override
    public Void visitLike_expression(ProtoneQueryLanguageParser.Like_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitNull_comparison_expression(ProtoneQueryLanguageParser.Null_comparison_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitEmpty_collection_comparison_expression(ProtoneQueryLanguageParser.Empty_collection_comparison_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitCollection_member_expression(ProtoneQueryLanguageParser.Collection_member_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitExists_expression(ProtoneQueryLanguageParser.Exists_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitAll_or_any_expression(ProtoneQueryLanguageParser.All_or_any_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitComparison_expression(ProtoneQueryLanguageParser.Comparison_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitComparison_operator(ProtoneQueryLanguageParser.Comparison_operatorContext ctx) {
        return null;
    }

    @Override
    public Void visitArithmetic_expression(ProtoneQueryLanguageParser.Arithmetic_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitSimple_arithmetic_expression(ProtoneQueryLanguageParser.Simple_arithmetic_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitArithmetic_term(ProtoneQueryLanguageParser.Arithmetic_termContext ctx) {
        return null;
    }

    @Override
    public Void visitArithmetic_factor(ProtoneQueryLanguageParser.Arithmetic_factorContext ctx) {
        return null;
    }

    @Override
    public Void visitArithmetic_primary(ProtoneQueryLanguageParser.Arithmetic_primaryContext ctx) {
        return null;
    }

    @Override
    public Void visitString_expression(ProtoneQueryLanguageParser.String_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitString_primary(ProtoneQueryLanguageParser.String_primaryContext ctx) {
        return null;
    }

    @Override
    public Void visitDatetime_expression(ProtoneQueryLanguageParser.Datetime_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitDatetime_primary(ProtoneQueryLanguageParser.Datetime_primaryContext ctx) {
        return null;
    }

    @Override
    public Void visitBoolean_expression(ProtoneQueryLanguageParser.Boolean_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitBoolean_primary(ProtoneQueryLanguageParser.Boolean_primaryContext ctx) {
        return null;
    }

    @Override
    public Void visitEnum_expression(ProtoneQueryLanguageParser.Enum_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitEnum_primary(ProtoneQueryLanguageParser.Enum_primaryContext ctx) {
        return null;
    }

    @Override
    public Void visitEntity_expression(ProtoneQueryLanguageParser.Entity_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitSimple_entity_expression(ProtoneQueryLanguageParser.Simple_entity_expressionContext ctx) {
        return null;
    }

    @Override
    public Void visitFunctions_returning_numerics(ProtoneQueryLanguageParser.Functions_returning_numericsContext ctx) {
        return null;
    }

    @Override
    public Void visitFunctions_returning_datetime(ProtoneQueryLanguageParser.Functions_returning_datetimeContext ctx) {
        return null;
    }

    @Override
    public Void visitTrim_specification(ProtoneQueryLanguageParser.Trim_specificationContext ctx) {
        return null;
    }

    @Override
    public Void visitNumeric_literal(ProtoneQueryLanguageParser.Numeric_literalContext ctx) {
        return null;
    }

    @Override
    public Void visitPattern_value(ProtoneQueryLanguageParser.Pattern_valueContext ctx) {
        return null;
    }

    @Override
    public Void visitInput_parameter(ProtoneQueryLanguageParser.Input_parameterContext ctx) {
        return null;
    }

    @Override
    public Void visitLiteral(ProtoneQueryLanguageParser.LiteralContext ctx) {
        return null;
    }

    @Override
    public Void visitConstructor_name(ProtoneQueryLanguageParser.Constructor_nameContext ctx) {
        return null;
    }

    @Override
    public Void visitEnum_literal(ProtoneQueryLanguageParser.Enum_literalContext ctx) {
        return null;
    }

    @Override
    public Void visitBoolean_literal(ProtoneQueryLanguageParser.Boolean_literalContext ctx) {
        return null;
    }

    @Override
    public Void visitSimple_state_field(ProtoneQueryLanguageParser.Simple_state_fieldContext ctx) {
        return null;
    }

    @Override
    public Void visitEmbedded_class_state_field(ProtoneQueryLanguageParser.Embedded_class_state_fieldContext ctx) {
        return null;
    }

    @Override
    public Void visitSingle_valued_association_field(ProtoneQueryLanguageParser.Single_valued_association_fieldContext ctx) {
        return null;
    }

    @Override
    public Void visitCollection_valued_association_field(ProtoneQueryLanguageParser.Collection_valued_association_fieldContext ctx) {
        return null;
    }

    @Override
    public Void visitAbstract_schema_name(ProtoneQueryLanguageParser.Abstract_schema_nameContext ctx) {
        return null;
    }

    @Override
    public Void visit(ParseTree parseTree) {
        return null;
    }

    @Override
    public Void visitChildren(RuleNode ruleNode) {
        return null;
    }

    @Override
    public Void visitTerminal(TerminalNode terminalNode) {
        return null;
    }

    @Override
    public Void visitErrorNode(ErrorNode errorNode) {
        return null;
    }
}
