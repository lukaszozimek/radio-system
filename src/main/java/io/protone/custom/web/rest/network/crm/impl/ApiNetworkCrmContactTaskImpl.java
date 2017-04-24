package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CrmContactService;
import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.domain.CorNetwork;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkCrmContactTaskImpl implements io.protone.custom.web.rest.network.crm.ApiNetworkCrmContactTask {

    @Inject
    private CrmContactService crmContactService;
    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<List<CrmTaskPT>> getAllContactActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                           @ApiParam(value = "pagable", required = true)  Pageable pagable) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);

        return ResponseEntity.ok().body(crmContactService.getTasksAssociatedWithContact(shortName, corNetwork));
    }

    @Override
    public ResponseEntity<CrmTaskPT> updateContactActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmContactService.updateLeadTask(shortName, crmActivityPT, corNetwork));

    }

    @Override
    public ResponseEntity<CrmTaskPT> createContactActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmContactService.updateLeadTask(shortName, crmActivityPT, corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteContactActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        crmContactService.deleteContactTask(shortName, id, corNetwork);
        return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<CrmTaskPT> getContactActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmContactService.getTaskAssociatedWithContact(shortName, id, corNetwork));
    }
}
