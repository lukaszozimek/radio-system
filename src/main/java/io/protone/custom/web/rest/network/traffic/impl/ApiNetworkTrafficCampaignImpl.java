package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.TRACampaignService;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCampaign;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkTrafficCampaignImpl implements ApiNetworkTrafficCampaign {
    @Inject
    private TRACampaignService campaignService;

    @Override
    public ResponseEntity<List<TraCampaignPT>> getAllCampaignsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return ResponseEntity.ok().body(campaignService.getAllCampaign());
    }

    @Override
    public ResponseEntity<TraCampaignPT> updateCampaignUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "campaignDTO", required = true) @RequestBody TraCampaignPT campaignDTO) {
        return null;
    }

    @Override
    public ResponseEntity<TraCampaignPT> createCampaignUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "campaignDTO", required = true) @RequestBody TraCampaignPT campaignDTO) {
        return ResponseEntity.ok().body(campaignService.saveCampaign(campaignDTO));

    }

    @Override
    public ResponseEntity<Void> deleteCampaignUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        campaignService.deleteCampaign(shortName);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TraCampaignPT> getCampaignUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return ResponseEntity.ok().body(campaignService.getCampaign(shortName));
    }
}
