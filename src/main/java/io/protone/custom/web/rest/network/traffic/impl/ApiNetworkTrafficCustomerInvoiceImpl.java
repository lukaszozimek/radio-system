package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.TraInvoiceService;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomerInvoice;
import io.protone.domain.CorNetwork;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkTrafficCustomerInvoiceImpl implements ApiNetworkTrafficCustomerInvoice {
    @Inject
    private TraInvoiceService traInvoiceService;
    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<List<TraInvoicePT>> getAllTrafficInvoicesForCustomerGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                                  @ApiParam(value = "pagable", required = true)  Pageable pagable) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traInvoiceService.getCustomerInvoice(customerShortcut,corNetwork));
    }

    @Override
    public ResponseEntity<Void> notifyAboutUnpaidInvoiceCustomerUsingGET(@ApiParam(value = "cutomerId", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
