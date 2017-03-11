package io.protone.custom.web.rest.network.configuration.traffic.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfTaxPT;
import io.protone.custom.service.dto.ConfTraOrderStatusPT;
import io.protone.custom.web.rest.network.configuration.traffic.ApiConfigurationTrafficDictionaryOrderStatus;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiConfigurationTrafficDictionaryOrderStatusImpl implements ApiConfigurationTrafficDictionaryOrderStatus {


    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<List<ConfTraOrderStatusPT>> getAllOrderStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<ConfTraOrderStatusPT> getOrderStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ConfTraOrderStatusPT> updateOrderStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confTraOrderStatusPT", required = true) @RequestBody ConfTraOrderStatusPT taxDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ConfTraOrderStatusPT> createOrderStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confTraOrderStatusPT", required = true) @RequestBody ConfTraOrderStatusPT confTraOrderStatusPT) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteOrderStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
