package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.TraCampaignService;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCampaign;
import io.protone.domain.CorNetwork;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkTrafficCampaignImpl implements ApiNetworkTrafficCampaign {
    @Inject
    private TraCampaignService campaignService;
    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<List<TraCampaignPT>> getAllCampaignsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "pagable", required = true)  Pageable pagable) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(campaignService.getAllCampaign(corNetwork));
    }

    @Override
    public ResponseEntity<TraCampaignPT> updateCampaignUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "campaignDTO", required = true) @RequestBody TraCampaignPT campaignDTO) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (campaignDTO.getId() == null) {
            return createCampaignUsingPOST(networkShortcut, campaignDTO);
        }
        return ResponseEntity.ok().body(campaignService.saveCampaign(campaignDTO, corNetwork));
    }

    @Override
    public ResponseEntity<TraCampaignPT> createCampaignUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "campaignDTO", required = true) @RequestBody TraCampaignPT campaignDTO) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(campaignService.saveCampaign(campaignDTO, corNetwork));

    }

    @Override
    public ResponseEntity<Void> deleteCampaignUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        campaignService.deleteCampaign(shortName, corNetwork);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TraCampaignPT> getCampaignUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(campaignService.getCampaign(shortName, corNetwork));
    }
}
