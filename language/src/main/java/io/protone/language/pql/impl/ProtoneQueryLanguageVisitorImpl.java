package io.protone.language.pql.impl;

import io.protone.core.constans.ModuleConstants;
import io.protone.language.pql.ProtoneQueryLanguageBaseVisitor;
import io.protone.language.pql.ProtoneQueryLanguageListener;
import io.protone.language.pql.ProtoneQueryLanguageParser;
import io.protone.language.pql.impl.visitor.cor.ProtoneQueryLanguageCorEntityVisitorImpl;
import io.protone.language.pql.impl.visitor.crm.ProtoneQueryLanguageCrmEntityVisitorImpl;
import io.protone.language.pql.impl.visitor.library.ProtoneQueryLanguageLibraryEntityVisitorImpl;
import io.protone.language.pql.impl.visitor.traffic.ProtoneQueryLanguageTrafficEntityVisitorImpl;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.Map;

import static io.protone.core.constans.ModuleConstants.*;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class ProtoneQueryLanguageVisitorImpl extends ProtoneQueryLanguageBaseVisitor<String> {
    private static final String EMPTY_STRING = "";
    Map<String, ProtoneQueryLanguageBaseVisitor> visitorMap = new HashMap<>();

    public ProtoneQueryLanguageVisitorImpl() {
        visitorMap.put(COR, new ProtoneQueryLanguageCorEntityVisitorImpl());
        visitorMap.put(CRM, new ProtoneQueryLanguageCrmEntityVisitorImpl());
        visitorMap.put(LIBRARY, new ProtoneQueryLanguageLibraryEntityVisitorImpl());
        visitorMap.put(TRAFFIC, new ProtoneQueryLanguageTrafficEntityVisitorImpl());
    }

    @Override
    public String visitQl_module(ProtoneQueryLanguageParser.Ql_moduleContext ctx) {

        if (ctx.getChild(0) != null && ctx.getChild(0).getChild(0) != null) {
            ProtoneQueryLanguageBaseVisitor<String> visitor = visitorMap.get(ctx.getChild(0).getChild(0).getText());
            return visitor.visit(ctx);
        }
        return EMPTY_STRING;
    }

}
