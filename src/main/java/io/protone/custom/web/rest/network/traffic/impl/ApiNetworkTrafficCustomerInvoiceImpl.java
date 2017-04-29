package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.domain.TraInvoice;
import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.TraInvoiceService;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomerInvoice;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.mapper.TraInvoiceMapper;
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
public class ApiNetworkTrafficCustomerInvoiceImpl implements ApiNetworkTrafficCustomerInvoice {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficCustomerInvoiceImpl.class);

    @Inject
    private TraInvoiceService traInvoiceService;

    @Inject
    private CorNetworkService networkService;

    @Inject
    private TraInvoiceMapper traInvoiceMapper;

    @Override
    public ResponseEntity<List<TraInvoicePT>> getAllTrafficInvoicesForCustomerGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                                  @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraInvoice, for TraCustomer: {} and Network: {}", customerShortcut, networkShortcut);
        List<TraInvoice> entity = traInvoiceService.getCustomerInvoice(customerShortcut, networkShortcut, pagable);
        List<TraInvoicePT> response = traInvoiceMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> notifyAboutUnpaidInvoiceCustomerUsingGET(@ApiParam(value = "cutomerId", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
