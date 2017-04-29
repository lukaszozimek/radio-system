package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.TraAdvertismentService;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomerAdvertisement;
import io.protone.domain.CorNetwork;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkTrafficCustomerAdvertisementImpl implements ApiNetworkTrafficCustomerAdvertisement {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficCustomerAdvertisementImpl.class);

    @Inject
    private TraAdvertismentService traAdvertismentService;
    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<List<TraAdvertisementPT>> getAllCustomersAdvertismentsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                         @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                                         @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraAdvertisement, for TraCustomer: {} and Network: {}", customerShortcut, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traAdvertismentService.getCustomerAdvertisments(customerShortcut, corNetwork));
    }
}
