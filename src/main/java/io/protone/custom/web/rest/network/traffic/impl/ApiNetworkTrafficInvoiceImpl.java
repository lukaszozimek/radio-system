package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.TRAInvoiceService;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficInvoice;
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
    private TRAInvoiceService traInvoiceService;

    @Override
    public ResponseEntity<List<TraInvoicePT>> getAllInvoicesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return ResponseEntity.ok().body(traInvoiceService.getAllInvoice());
    }

    @Override
    public ResponseEntity<TraInvoicePT> updateInvoiceUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "invoiceDTO", required = true) @RequestBody TraInvoicePT invoiceDTO) {
        return null;
    }

    @Override
    public ResponseEntity<TraInvoicePT> createInvoiceUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "invoiceDTO", required = true) @RequestBody TraInvoicePT invoiceDTO) {
        return ResponseEntity.ok().body(traInvoiceService.saveInvoice(invoiceDTO));
    }

    @Override
    public ResponseEntity<Void> deleteInvoiceUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        traInvoiceService.deleteInvoice(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TraInvoicePT> getInvoiceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return ResponseEntity.ok().body(traInvoiceService.getInvoice(id));
    }

    @Override
    public ResponseEntity<Void> notifyAboutUnpaidInvoiceUsingGET(@ApiParam(value = "cutomerId", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
