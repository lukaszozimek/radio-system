package io.protone.language.pql.pal;
import io.protone.language.pal.ProtoneAutomationLanguageParser;
import io.protone.language.pal.ProtoneAutomationLanguageVisitor;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class ProtoneAutomationLanguageVisitorImpl implements ProtoneAutomationLanguageVisitor  {
    @Override
    public Object visitProgram(io.protone.language.pal.ProtoneAutomationLanguageParser.ProgramContext ctx) {
        return null;
    }

    @Override
    public Object visitAutomation_module(ProtoneAutomationLanguageParser.Automation_moduleContext ctx) {
        return null;
    }

    @Override
    public Object visitProtone_playout_statemant(ProtoneAutomationLanguageParser.Protone_playout_statemantContext ctx) {
        return null;
    }

    @Override
    public Object visitProtone_playout_basic(ProtoneAutomationLanguageParser.Protone_playout_basicContext ctx) {
        return null;
    }

    @Override
    public Object visitChannel(ProtoneAutomationLanguageParser.ChannelContext ctx) {
        return null;
    }

    @Override
    public Object visitPlayout(ProtoneAutomationLanguageParser.PlayoutContext ctx) {
        return null;
    }

    @Override
    public Object visitAction(ProtoneAutomationLanguageParser.ActionContext ctx) {
        return null;
    }

    @Override
    public Object visitAction_content(ProtoneAutomationLanguageParser.Action_contentContext ctx) {
        return null;
    }

    @Override
    public Object visitChannel_id(ProtoneAutomationLanguageParser.Channel_idContext ctx) {
        return null;
    }

    @Override
    public Object visitPlayout_id(ProtoneAutomationLanguageParser.Playout_idContext ctx) {
        return null;
    }

    @Override
    public Object visitContent(ProtoneAutomationLanguageParser.ContentContext ctx) {
        return null;
    }

    @Override
    public Object visit(ParseTree parseTree) {
        return null;
    }

    @Override
    public Object visitChildren(RuleNode ruleNode) {
        return null;
    }

    @Override
    public Object visitTerminal(TerminalNode terminalNode) {
        return null;
    }

    @Override
    public Object visitErrorNode(ErrorNode errorNode) {
        return null;
    }
}
