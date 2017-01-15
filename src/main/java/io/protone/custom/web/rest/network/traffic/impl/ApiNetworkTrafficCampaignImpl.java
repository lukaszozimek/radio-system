package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCampaign;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class ApiNetworkTrafficCampaignImpl implements ApiNetworkTrafficCampaign{


    @Override
    public ResponseEntity<List<TraCampaignPT>> getAllCampaignsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<TraCampaignPT> updateCampaignUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "campaignDTO", required = true) @RequestBody TraCampaignPT campaignDTO) {
        return null;
    }

    @Override
    public ResponseEntity<TraCampaignPT> createCampaignUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "campaignDTO", required = true) @RequestBody TraCampaignPT campaignDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteCampaignUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<TraCampaignPT> getCampaignUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }
}
