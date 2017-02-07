package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.NetworkService;
import io.protone.custom.service.TraInvoiceService;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficInvoice;
import io.protone.domain.CorNetwork;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;


@RestController
public class ApiNetworkTrafficInvoiceImpl implements ApiNetworkTrafficInvoice {
    @Inject
    private TraInvoiceService traInvoiceService;
    @Inject
    private NetworkService networkService;

    @Override
    public ResponseEntity<List<TraInvoicePT>> getAllInvoicesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traInvoiceService.getAllInvoice(corNetwork));
    }

    @Override
    public ResponseEntity<TraInvoicePT> updateInvoiceUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "invoiceDTO", required = true) @RequestBody TraInvoicePT invoiceDTO) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (invoiceDTO.getId() == null) {
            return createInvoiceUsingPOST(networkShortcut, invoiceDTO);
        }
        return ResponseEntity.ok().body(traInvoiceService.saveInvoice(invoiceDTO, corNetwork));

    }

    @Override
    public ResponseEntity<TraInvoicePT> createInvoiceUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "invoiceDTO", required = true) @RequestBody TraInvoicePT invoiceDTO) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traInvoiceService.saveInvoice(invoiceDTO, corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteInvoiceUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        traInvoiceService.deleteInvoice(id, corNetwork);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TraInvoicePT> getInvoiceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traInvoiceService.getInvoice(id, corNetwork));
    }

    @Override
    public ResponseEntity<Void> notifyAboutUnpaidInvoiceUsingGET(@ApiParam(value = "cutomerId", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
