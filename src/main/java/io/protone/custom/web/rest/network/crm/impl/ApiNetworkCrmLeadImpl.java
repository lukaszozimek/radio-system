package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CrmLeadService;
import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmLead;
import io.protone.domain.CorNetwork;
import io.protone.repository.CCorNetworkRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkCrmLeadImpl implements ApiNetworkCrmLead {

    @Inject
    private CrmLeadService crmLeadService;
    @Inject
    private NetworkService networkService;

    @Override
    public ResponseEntity<CrmLeadPT> updateLeadUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmLeadPT", required = true) @RequestBody CrmLeadPT crmLeadPT) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);

        if (crmLeadPT.getId() == null) {
            return createLeadUsingPOST(networkShortcut, crmLeadPT);
        }
        return ResponseEntity.ok().body(crmLeadService.saveLead(crmLeadPT, corNetwork));
    }

    @Override
    public ResponseEntity<CrmLeadPT> createLeadUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmLeadPT", required = true) @RequestBody CrmLeadPT crmLeadPT) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);

        return ResponseEntity.ok().body(crmLeadService.saveLead(crmLeadPT, corNetwork));
    }

    @Override
    public ResponseEntity<List<CrmLeadPT>> getAllLeadsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmLeadService.getAllLeads(corNetwork));
    }

    @Override
    public ResponseEntity<CrmLeadPT> getLeadUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmLeadService.getLead(shortName, corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteLeadUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        crmLeadService.deleteLead(shortName, corNetwork);
        return ResponseEntity.ok().build();
    }
}
