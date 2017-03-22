package io.protone.custom.web.rest.network.configuration.scheduler.impl;

import io.protone.custom.service.dto.ConfCommercialLogPT;
import io.protone.custom.web.rest.network.configuration.scheduler.ApiConfigurationSchedulerLogCommercial;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiConfigurationSchedulerLogCommercialImpl implements ApiConfigurationSchedulerLogCommercial {

    @Override
    public ResponseEntity<ConfCommercialLogPT> updateCommercialLogConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfCommercialLogPT confMusicLogPT) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCommercialLogPT> createCommercialLogConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfCommercialLogPT confMusicLogPT) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteCommercialLogConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getCommercialLogConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }
}
