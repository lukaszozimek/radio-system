package io.protone.custom.web.rest.network.traffic.impl;


import io.protone.domain.TraCampaign;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraCampaignService;
import io.protone.web.rest.dto.traffic.TraCampaignDTO;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomerCampaign;
import io.protone.web.rest.mapper.TraCampaignMapper;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiNetworkTrafficCustomerCampaignImpl implements ApiNetworkTrafficCustomerCampaign {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficCustomerCampaignImpl.class);

    @Inject
    private TraCampaignService campaignService;

    @Inject
    private CorNetworkService networkService;

    @Inject
    private TraCampaignMapper traCampaignMapper;

    @Override
    public ResponseEntity<List<TraCampaignDTO>> getAllCustomerCampaignsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                                @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraCampaign, for TraCustomer: {} and Network: {}", customerShortcut, networkShortcut);
        List<TraCampaign> entity = campaignService.getCustomerCampaing(customerShortcut, networkShortcut, pagable);
        List<TraCampaignDTO> response = traCampaignMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
