package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.TRAOrderService;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomerOrder;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkTrafficCustomerOrderImpl implements ApiNetworkTrafficCustomerOrder {
    @Inject
    private TRAOrderService orderService;

    @Override
    public ResponseEntity<List<TraOrderPT>> getAllCustomerOrdersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        return ResponseEntity.ok().body(orderService.getCustomerOrders(customerShortcut));
    }
}
