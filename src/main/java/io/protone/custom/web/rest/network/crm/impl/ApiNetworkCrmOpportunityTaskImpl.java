package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmOpportunityTask;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class ApiNetworkCrmOpportunityTaskImpl implements ApiNetworkCrmOpportunityTask {


    @Override
    public ResponseEntity<List<CrmTaskPT>> getAllOpportunityActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<CrmTaskPT> updateOpportunityActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        return null;
    }

    @Override
    public ResponseEntity<CrmTaskPT> createOpportunityActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmActivityPT", required = true) @RequestBody CrmTaskPT crmActivityPT) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteOpportunityActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CrmTaskPT> getOpportunityActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
