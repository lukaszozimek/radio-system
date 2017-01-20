package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CRMContactService;
import io.protone.custom.service.dto.CrmTaskPT;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import java.util.List;

public class ApiNetworkCrmContactTaskImpl implements io.protone.custom.web.rest.network.crm.ApiNetworkCrmContactTask {

    @Inject
    private CRMContactService crmContactService;

    @Override
    public ResponseEntity<List<CrmTaskPT>> getAllContactActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return ResponseEntity.ok().body(crmContactService.getTasksAssociatedWithLead(shortName));
    }

    @Override
    public ResponseEntity<CrmTaskPT> updateContactActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        return null;
    }

    @Override
    public ResponseEntity<CrmTaskPT> createContactActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        return ResponseEntity.ok().body(crmContactService.createTasksAssociatedWithLead(shortName, crmActivityPT));
    }

    @Override
    public ResponseEntity<Void> deleteContactActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        crmContactService.deleteLeadTask(shortName, id);
        return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<CrmTaskPT> getContactActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return ResponseEntity.ok().body(crmContactService.getTaskAssociatedWithLead(shortName, id));
    }
}
