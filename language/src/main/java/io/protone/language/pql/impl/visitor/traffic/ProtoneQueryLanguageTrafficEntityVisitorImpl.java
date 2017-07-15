
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
    private Map<String, String> pqlEntityMap = new HashMap<>();

    public ProtoneQueryLanguageTrafficEntityVisitorImpl() {

        pqlEntityMap.put("Advertisement", TraAdvertisement.class.getSimpleName());
        pqlEntityMap.put("Campaign", TraCampaign.class.getSimpleName());
        pqlEntityMap.put("Order", TraOrder.class.getSimpleName());
        pqlEntityMap.put("Invoice", TraInvoice.class.getSimpleName());
        pqlEntityMap.put("Media Plan", TraMediaPlan.class.getSimpleName());
        pqlEntityMap.put("Playlist", TraPlaylist.class.getSimpleName());

    }

    @Override
    public String visitTraffic_module(ProtoneQueryLanguageParser.Traffic_moduleContext ctx) {
        return "SELECT * FROM";
    }

    @Override
    public String visitTraffic_entity(ProtoneQueryLanguageParser.Traffic_entityContext ctx) {

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
