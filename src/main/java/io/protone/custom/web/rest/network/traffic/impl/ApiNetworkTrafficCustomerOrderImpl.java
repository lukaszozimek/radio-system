package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.TraOrderService;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomerOrder;
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
public class ApiNetworkTrafficCustomerOrderImpl implements ApiNetworkTrafficCustomerOrder {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficCustomerOrderImpl.class);

    @Inject
    private TraOrderService orderService;

    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<List<TraOrderPT>> getAllCustomerOrdersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                         @ApiParam(value = "pagable", required = true)  Pageable pagable) {
        log.debug("REST request to get all TraOrder, for TraCustomer: {} and Network: {}", customerShortcut, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(orderService.getCustomerOrders(customerShortcut,corNetwork));
    }
}
