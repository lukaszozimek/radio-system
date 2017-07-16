package io.protone.language.pql.impl.visitor.crm;

import io.protone.crm.domain.*;
import io.protone.language.pql.ProtoneQueryLanguageParser;
import io.protone.language.pql.impl.visitor.ProtoneQueryLangageBaseVisitorImpl;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class ProtoneQueryLanguageCrmEntityVisitorImpl extends ProtoneQueryLangageBaseVisitorImpl {

    public ProtoneQueryLanguageCrmEntityVisitorImpl() {
        pqlEntityMap.put("Contact", CrmContact.class);
        pqlEntityMap.put("Lead", CrmLead.class);
        pqlEntityMap.put("Opportunity", CrmOpportunity.class);
        pqlEntityMap.put("Customer", CrmAccount.class);
        pqlEntityMap.put("Task", CrmTask.class);

    }

    @Override
    public String visitCrm_entity(ProtoneQueryLanguageParser.Crm_entityContext ctx) {
        if (pqlEntityMap.get(ctx.getText().trim()).getSimpleName() == null) {
            return null;
        }
        aliasVariable = ctx.getText().toLowerCase().toCharArray()[0];
        return "SELECT " + aliasVariable + " FROM " + pqlEntityMap.get(ctx.getText()).getSimpleName().trim();
    }



}
