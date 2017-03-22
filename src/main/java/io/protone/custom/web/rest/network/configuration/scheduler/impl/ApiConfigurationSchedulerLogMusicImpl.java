package io.protone.custom.web.rest.network.configuration.scheduler.impl;

import io.protone.custom.service.dto.ConfMusicLogPT;
import io.protone.custom.web.rest.network.configuration.scheduler.ApiConfigurationSchedulerLogMusic;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiConfigurationSchedulerLogMusicImpl implements ApiConfigurationSchedulerLogMusic {


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
    public ResponseEntity<Object> getMusicLogConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }
}
