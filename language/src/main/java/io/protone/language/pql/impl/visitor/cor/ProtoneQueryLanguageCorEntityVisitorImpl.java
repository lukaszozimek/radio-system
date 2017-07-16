package io.protone.language.pql.impl.visitor.cor;

import io.protone.core.domain.*;
import io.protone.language.pql.ProtoneQueryLanguageParser;
import io.protone.language.pql.impl.visitor.ProtoneQueryLangageBaseVisitorImpl;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class ProtoneQueryLanguageCorEntityVisitorImpl extends ProtoneQueryLangageBaseVisitorImpl {


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



}
