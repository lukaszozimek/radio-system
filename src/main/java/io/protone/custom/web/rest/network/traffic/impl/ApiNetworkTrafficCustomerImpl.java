package io.protone.custom.web.rest.network.traffic.impl;


import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.TraCustomerService;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomer;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkTrafficCustomerImpl implements ApiNetworkTrafficCustomer {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficCustomerImpl.class);

    @Inject
    private TraCustomerService customerService;

    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<TraCustomerPT> updateTrafficCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "traCustomerPt", required = true) @RequestBody TraCustomerPT traCustomerPt) {
        log.debug("REST request to update TraCustomer : {}, for Network: {}", traCustomerPt, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (traCustomerPt.getId() == null) {
            return createTrafficCustomerUsingPOST(networkShortcut, traCustomerPt);
        }
        return ResponseEntity.ok().body(customerService.update(traCustomerPt, corNetwork));
    }

    @Override
    public ResponseEntity<TraCustomerPT> createTrafficCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traCustomerPt", required = true) @RequestBody TraCustomerPT traCustomerPt) {
        log.debug("REST request to save TraCustomer : {}, for Network: {}", traCustomerPt, networkShortcut);
        if (traCustomerPt.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraCustomer", "idexists", "A new TraCustomer cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(customerService.saveCustomers(traCustomerPt, corNetwork));
    }

    @Override
    public ResponseEntity<List<TraCustomerPT>> getAllTrafficCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraCustomer, for Network: {}", networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(customerService.getAllCustomers(corNetwork, pagable));
    }

    @Override
    public ResponseEntity<TraCustomerPT> getTrafficCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        log.debug("REST request to get TraCustomer : {}, for Network: {}", customerShortcut, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(customerService.getCustomer(customerShortcut, corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteTrafficCustomerUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        log.debug("REST request to delete TraCustomer : {}, for Network: {}", customerShortcut, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        customerService.deleteCustomer(customerShortcut, corNetwork);
        return ResponseEntity.ok().build();
    }
}
