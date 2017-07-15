package io.protone.language.pql.impl.visitor.cor;

import com.google.common.base.Strings;
import io.protone.core.domain.*;
import io.protone.language.intermediet.JpaValueObject;
import io.protone.language.pql.ProtoneQueryLanguageBaseVisitor;
import io.protone.language.pql.ProtoneQueryLanguageParser;
import io.protone.language.pql.ProtoneQueryLanguageVisitor;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class ProtoneQueryLanguageCorEntityVisitorImpl extends ProtoneQueryLanguageBaseVisitor<String> {
    private static final String SPACE = " ";
    private Map<String, String> pqlEntityMap = new HashMap<>();

    public ProtoneQueryLanguageCorEntityVisitorImpl() {
        pqlEntityMap.put("Person", CorPerson.class.getSimpleName());
        pqlEntityMap.put("Image", CorImageItem.class.getSimpleName());
        pqlEntityMap.put("Channel", CorChannel.class.getSimpleName());
        pqlEntityMap.put("Adress", CorAddress.class.getSimpleName());
        pqlEntityMap.put("Tag", CorTag.class.getSimpleName());
        pqlEntityMap.put("Contact", CorContact.class.getSimpleName());
        pqlEntityMap.put("Property", CorPropertyKey.class.getSimpleName());

    }

    @Override
    public String visitCor_module(ProtoneQueryLanguageParser.Cor_moduleContext ctx) {
        return "SELECT * FROM";
    }

    @Override
    public String visitCor_entity(ProtoneQueryLanguageParser.Cor_entityContext ctx) {

        return pqlEntityMap.get(ctx.getText().trim());
    }


    @Override
    protected String aggregateResult(String aggregate, String nextResult) {
        if (Strings.isNullOrEmpty(aggregate)) {
            aggregate = "";
            return aggregate.concat(nextResult);
        }
        return aggregate.concat(SPACE).concat(nextResult);
    }


}
