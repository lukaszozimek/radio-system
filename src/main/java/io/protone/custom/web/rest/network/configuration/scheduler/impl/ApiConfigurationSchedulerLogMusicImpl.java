package io.protone.custom.web.rest.network.configuration.scheduler.impl;


import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfMusicLogPT;
import io.protone.custom.service.mapper.CustomConfMusicLogMapper;
import io.protone.custom.web.rest.network.configuration.scheduler.ApiConfigurationSchedulerLogMusic;
import io.protone.repository.CfgExternalSystemLogRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiConfigurationSchedulerLogMusicImpl implements ApiConfigurationSchedulerLogMusic {

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CustomConfMusicLogMapper confCommercialLogMapper;

    @Inject
    private CfgExternalSystemLogRepository cfgExternalSystemLogRepository;

    @Override
    public ResponseEntity<ConfMusicLogPT> updateMusicLogConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfMusicLogPT confMusicLogPT) {
        return null;
    }

    @Override
    public ResponseEntity<ConfMusicLogPT> createMusicLogConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confMusicLogPT", required = true) @RequestBody ConfMusicLogPT confMusicLogPT) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteMusicLogConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<List<ConfMusicLogPT>> getAllMusicLogConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<ConfMusicLogPT> getMusicLogConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") String id) {
        return null;
    }
}
