package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CrmLeadService;
import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmLeadTask;
import io.protone.domain.CorNetwork;
import io.protone.repository.CCorNetworkRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.xml.ws.Response;
import java.util.List;

@RestController
public class ApiNetworkCrmLeadTaskImpl implements ApiNetworkCrmLeadTask {

    @Inject
    private CrmLeadService crmLeadService;

    @Inject
    private NetworkService networkService;

    @Override
    public ResponseEntity<List<CrmTaskPT>> getAllLeadActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmLeadService.getTasksAssociatedWithLead(shortName, corNetwork));
    }

    @Override
    public ResponseEntity<CrmTaskPT> updateLeadActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmLeadService.createTasksAssociatedWithLead(shortName,crmActivityPT, corNetwork));
    }

    @Override
    public ResponseEntity<CrmTaskPT> createLeadActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmLeadService.createTasksAssociatedWithLead(shortName, crmActivityPT, corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteLeadActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        crmLeadService.deleteLeadTask(shortName, id, corNetwork);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CrmTaskPT> getLeadActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmLeadService.getTaskAssociatedWithLead(shortName, id, corNetwork));
    }
}
