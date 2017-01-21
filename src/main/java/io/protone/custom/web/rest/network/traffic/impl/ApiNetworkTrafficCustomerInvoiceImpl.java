package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.dto.TraCustomerOrdersPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomerInvoice;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiNetworkTrafficCustomerInvoiceImpl implements ApiNetworkTrafficCustomerInvoice {

    @Override
    public ResponseEntity<List<TraCustomerOrdersPT>> getAllTrafficInvoicesForCustomerGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<Void> notifyAboutUnpaidInvoiceCustomerUsingGET(@ApiParam(value = "cutomerId", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
