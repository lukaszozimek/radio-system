package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CRMContactService;
import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.CrmTaskPT;
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
public class ApiNetworkCrmContactTaskImpl implements io.protone.custom.web.rest.network.crm.ApiNetworkCrmContactTask {

    @Inject
    private CRMContactService crmContactService;
    @Inject
    private NetworkService networkService;
    @Override
    public ResponseEntity<List<CrmTaskPT>> getAllContactActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        CORNetwork corNetwork = networkService.findNetwork(networkShortcut);

        return ResponseEntity.ok().body(crmContactService.getTasksAssociatedWithLead(shortName,corNetwork));
    }

    @Override
    public ResponseEntity<CrmTaskPT> updateContactActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        CORNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmContactService.updateLeadTask(shortName,crmActivityPT,corNetwork));

    }

    @Override
    public ResponseEntity<CrmTaskPT> createContactActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        CORNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmContactService.createTasksAssociatedWithLead(shortName, crmActivityPT,corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteContactActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        CORNetwork corNetwork = networkService.findNetwork(networkShortcut);
        crmContactService.deleteLeadTask(shortName, id,corNetwork);
        return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<CrmTaskPT> getContactActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        CORNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmContactService.getTaskAssociatedWithLead(shortName, id,corNetwork));
    }
}
