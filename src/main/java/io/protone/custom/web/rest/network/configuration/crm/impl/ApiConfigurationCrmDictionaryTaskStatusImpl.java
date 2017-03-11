package io.protone.custom.web.rest.network.configuration.crm.impl;

import io.protone.custom.service.dto.ConfCrmStagePT;
import io.protone.custom.service.dto.ConfCrmTaskStatusPT;
import io.protone.custom.web.rest.network.configuration.crm.ApiConfigurationCrmDictionaryTaskStatus;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiConfigurationCrmDictionaryTaskStatusImpl implements ApiConfigurationCrmDictionaryTaskStatus {

    @Override
    public ResponseEntity<List<ConfCrmStagePT>> getAllCrmTaskStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCrmTaskStatusPT> getCrmTaskStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCrmTaskStatusPT> updateCrmTaskStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmStage", required = true) @RequestBody ConfCrmTaskStatusPT crmStage) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCrmTaskStatusPT> createCrmTaskStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmStage", required = true) @RequestBody ConfCrmTaskStatusPT crmStage) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteCrmTaskStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
