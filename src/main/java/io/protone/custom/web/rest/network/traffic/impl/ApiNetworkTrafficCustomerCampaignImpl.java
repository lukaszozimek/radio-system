package io.protone.custom.web.rest.network.traffic.impl;


import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomerCampaign;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class ApiNetworkTrafficCustomerCampaignImpl implements ApiNetworkTrafficCustomerCampaign {


    @Override
    public ResponseEntity<List<TraCampaignPT>> getAllCustomerCampaignsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        return null;
    }
}
