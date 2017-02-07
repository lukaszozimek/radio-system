package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CrmOpportunityService;
import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmOpportunityTask;
import io.protone.domain.CorNetwork;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
@RestController
public class ApiNetworkCrmOpportunityTaskImpl implements ApiNetworkCrmOpportunityTask {

    @Inject
    private CrmOpportunityService crmOpportunityService;
    @Inject
    private NetworkService networkService;
    @Override
    public ResponseEntity<List<CrmTaskPT>> getAllOpportunityActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmOpportunityService.getTasksAssociatedWithOpportunity(shortName,corNetwork));
    }

    @Override
    public ResponseEntity<CrmTaskPT> updateOpportunityActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmOpportunityService.saveTasksAssociatedWithOpportunity(shortName,crmActivityPT,corNetwork));

    }

    @Override
    public ResponseEntity<CrmTaskPT> createOpportunityActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmOpportunityService.saveTasksAssociatedWithOpportunity(shortName, crmActivityPT,corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteOpportunityActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        crmOpportunityService.deleteOpportunityTask(shortName, id,corNetwork);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CrmTaskPT> getOpportunityActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmOpportunityService.getTaskAssociatedWithOpportunity(shortName, id,corNetwork));
    }
}
