package io.protone.custom.web.rest.network.configuration.traffic.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfCampaingStatusPT;
import io.protone.custom.web.rest.network.configuration.traffic.ApiConfigurationTrafficDictionaryCampaingStatus;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiConfigurationTrafficDictionaryCampaingStatusImpl implements ApiConfigurationTrafficDictionaryCampaingStatus {

    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<List<ConfCampaingStatusPT>> getAllCampaingStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCampaingStatusPT> getCampaingStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCampaingStatusPT> updateCampaingStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confCampaingStatusPT", required = true) @RequestBody ConfCampaingStatusPT confCampaingStatusPT) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCampaingStatusPT> createCampaingStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confCampaingStatusPT", required = true) @RequestBody ConfCampaingStatusPT confCampaingStatusPT) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteCampaingStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
