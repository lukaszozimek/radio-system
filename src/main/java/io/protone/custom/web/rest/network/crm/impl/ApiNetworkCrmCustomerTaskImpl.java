package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CRMCustomerService;
import io.protone.custom.service.dto.CrmTaskPT;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import java.util.List;

public class ApiNetworkCrmCustomerTaskImpl implements io.protone.custom.web.rest.network.crm.ApiNetworkCrmCustomerTask {
    @Inject
    private CRMCustomerService crmCustomerService;

    @Override
    public ResponseEntity<List<CrmTaskPT>> getAllCustomerActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return ResponseEntity.ok().body(crmCustomerService.getTasksAssociatedWithLead(shortName));

    }

    @Override
    public ResponseEntity<CrmTaskPT> updateCustomerActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        return null;
    }

    @Override
    public ResponseEntity<CrmTaskPT> createCustomerActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        return ResponseEntity.ok().body(crmCustomerService.createTasksAssociatedWithLead(shortName, crmActivityPT));

    }

    @Override
    public ResponseEntity<Void> deleteCustomerActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        crmCustomerService.deleteLeadTask(shortName, id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CrmTaskPT> getCustomerActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return ResponseEntity.ok().body(crmCustomerService.getTaskAssociatedWithLead(shortName, id));

    }
}
