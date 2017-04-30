package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.domain.TraAdvertisement;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraAdvertisementService;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomerAdvertisement;
import io.protone.web.rest.mapper.TraAdvertisementMapper;
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
public class ApiNetworkTrafficCustomerAdvertisementImpl implements ApiNetworkTrafficCustomerAdvertisement {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficCustomerAdvertisementImpl.class);

    @Inject
    private TraAdvertisementService traAdvertisementService;
    @Inject
    private CorNetworkService networkService;
    @Inject
    private TraAdvertisementMapper traAdvertisementMapper;

    @Override
    public ResponseEntity<List<TraAdvertisementPT>> getAllCustomersAdvertismentsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                         @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                                         @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraAdvertisement, for TraCustomer: {} and Network: {}", customerShortcut, networkShortcut);
        List<TraAdvertisement> entity = traAdvertisementService.getCustomerAdvertisements(customerShortcut, networkShortcut, pagable);
        List<TraAdvertisementPT> response = traAdvertisementMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
