package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.dto.TraOrderPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficOrder;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class ApiNetworkTrafficOrderImpl implements ApiNetworkTrafficOrder {

    @Override
    public ResponseEntity<TraOrderPT> updateAnOrderUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "anOrderDTO", required = true) @RequestBody TraOrderPT anOrderDTO) {
        return null;
    }

    @Override
    public ResponseEntity<TraOrderPT> createAnOrderUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "anOrderDTO", required = true) @RequestBody TraOrderPT anOrderDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<TraOrderPT>> getAllAnOrdersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<TraOrderPT> getAnOrderUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteAnOrderUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<TraOrderPT> notifyCustomerAboutUnpaidOrderUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
