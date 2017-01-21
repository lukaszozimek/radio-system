package io.protone.custom.web.rest.network.traffic.impl;


import io.protone.custom.service.TRACampaignService;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomerCampaign;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkTrafficCustomerCampaignImpl implements ApiNetworkTrafficCustomerCampaign {
    @Inject
    private TRACampaignService campaignService;

    @Override
    public ResponseEntity<List<TraCampaignPT>> getAllCustomerCampaignsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        return ResponseEntity.ok().body(campaignService.getCustomerCampaing(customerShortcut));
    }
}
