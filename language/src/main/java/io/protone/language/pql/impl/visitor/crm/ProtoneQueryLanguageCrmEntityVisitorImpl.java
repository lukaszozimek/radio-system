package io.protone.language.pql.impl.visitor.crm;

import com.google.common.base.Strings;
import io.protone.core.domain.*;
import io.protone.crm.domain.*;
import io.protone.language.pql.ProtoneQueryLanguageBaseVisitor;
import io.protone.language.pql.ProtoneQueryLanguageParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class ProtoneQueryLanguageCrmEntityVisitorImpl extends ProtoneQueryLanguageBaseVisitor<String> {
    private static final String SPACE = " ";
    private Map<String, String> pqlEntityMap = new HashMap<>();

    public ProtoneQueryLanguageCrmEntityVisitorImpl() {
        pqlEntityMap.put("Contact", CrmContact.class.getSimpleName());
        pqlEntityMap.put("Lead", CrmLead.class.getSimpleName());
        pqlEntityMap.put("Opportunity", CrmOpportunity.class.getSimpleName());
        pqlEntityMap.put("Customer", CrmAccount.class.getSimpleName());
        pqlEntityMap.put("Task", CrmTask.class.getSimpleName());

    }

    @Override
    public String visitCrm_entity(ProtoneQueryLanguageParser.Crm_entityContext ctx) {
        if (Strings.isNullOrEmpty(pqlEntityMap.get(ctx.getText().trim()))) {
            return null;
        }
        return "SELECT * FROM " + pqlEntityMap.get(ctx.getText().trim());
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
