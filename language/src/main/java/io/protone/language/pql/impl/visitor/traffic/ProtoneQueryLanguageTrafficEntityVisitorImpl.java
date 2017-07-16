package io.protone.language.pql.impl.visitor.traffic;

import io.protone.language.pql.ProtoneQueryLanguageParser;
import io.protone.language.pql.impl.visitor.ProtoneQueryLangageBaseVisitorImpl;
import io.protone.traffic.domain.*;

/**
 * Created by lukaszozimek on 13.07.2017.
 */
public class ProtoneQueryLanguageTrafficEntityVisitorImpl  extends ProtoneQueryLangageBaseVisitorImpl {


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


}
