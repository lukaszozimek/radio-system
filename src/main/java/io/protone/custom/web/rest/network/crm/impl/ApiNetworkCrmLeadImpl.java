package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmLead;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class ApiNetworkCrmLeadImpl implements ApiNetworkCrmLead {

    @Override
    public ResponseEntity<CrmLeadPT> updateLeadUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmLeadPT", required = true) @RequestBody CrmLeadPT crmLeadPT) {
        return null;
    }

    @Override
    public ResponseEntity<CrmLeadPT> createLeadUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmLeadPT", required = true) @RequestBody CrmLeadPT crmLeadPT) {
        return null;
    }

    @Override
    public ResponseEntity<List<CrmLeadPT>> getAllLeadsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<CrmLeadPT> getLeadUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteLeadUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }
}
