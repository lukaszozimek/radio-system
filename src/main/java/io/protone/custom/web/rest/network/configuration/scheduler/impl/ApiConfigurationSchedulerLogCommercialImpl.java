package io.protone.custom.web.rest.network.configuration.scheduler.impl;

import java.util.List;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfCommercialLogPT;
import io.protone.custom.service.mapper.CustomConfCommercialLogMapper;
import io.protone.custom.service.mapper.CustomConfMusicLogMapper;
import io.protone.custom.web.rest.network.configuration.scheduler.ApiConfigurationSchedulerLogCommercial;
import io.protone.repository.CfgExternalSystemLogRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class ApiConfigurationSchedulerLogCommercialImpl implements ApiConfigurationSchedulerLogCommercial {

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CustomConfCommercialLogMapper confCommercialLogMapper;

    @Inject
    private CfgExternalSystemLogRepository cfgExternalSystemLogRepository;

    @Override
    public ResponseEntity<ConfCommercialLogPT> updateCommercialLogConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfCommercialLogPT confMusicLogPT) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCommercialLogPT> createCommercialLogConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfCommercialLogPT confMusicLogPT) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteCommercialLogConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") String id) {
        return null;
    }

    @Override
    public ResponseEntity<List<ConfCommercialLogPT>> getAllCommercialLogConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCommercialLogPT> getCommercialLogConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") String id) {
        return null;
    }
}
