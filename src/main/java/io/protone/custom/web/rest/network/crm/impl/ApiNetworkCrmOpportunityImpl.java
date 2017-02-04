package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CRMOpportunityService;
import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.CrmOpportunityPT;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmOpportunity;
import io.protone.domain.CORNetwork;
import io.protone.repository.CCORNetworkRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkCrmOpportunityImpl implements ApiNetworkCrmOpportunity {

    @Inject
    private CRMOpportunityService opportunityService;
    @Inject
    private NetworkService networkService;
    @Override
    public ResponseEntity<CrmOpportunityPT> updateOpportunityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmOpportunityPT", required = true) @RequestBody CrmOpportunityPT crmOpportunityPT) {
        CORNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (crmOpportunityPT.getId() == null) {
            return createOpportunityUsingPOST(networkShortcut, crmOpportunityPT);
        }
        return ResponseEntity.ok().body(opportunityService.saveOpportunity(crmOpportunityPT,corNetwork));
    }

    @Override
    public ResponseEntity<CrmOpportunityPT> createOpportunityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmOpportunityPT", required = true) @RequestBody CrmOpportunityPT crmOpportunityPT) {
        CORNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(opportunityService.saveOpportunity(crmOpportunityPT,corNetwork));
    }

    @Override
    public ResponseEntity<List<CrmOpportunityPT>> getAllOpportunitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        CORNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(opportunityService.getAllOpportunity(corNetwork));
    }

    @Override
    public ResponseEntity<CrmOpportunityPT> getOpportunityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        CORNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(opportunityService.getOpportunity(shortName,corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteOpportunityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        CORNetwork corNetwork = networkService.findNetwork(networkShortcut);
        opportunityService.deleteOpportunity(shortName,corNetwork);
        return ResponseEntity.ok().build();
    }
}
