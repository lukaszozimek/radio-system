package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.TraInvoiceService;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficInvoice;
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
public class ApiNetworkTrafficInvoiceImpl implements ApiNetworkTrafficInvoice {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficInvoiceImpl.class);

    @Inject
    private TraInvoiceService traInvoiceService;

    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<List<TraInvoicePT>> getAllInvoicesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CorNetworks, for Network: {}", networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traInvoiceService.getAllInvoice(corNetwork));
    }

    @Override
    public ResponseEntity<TraInvoicePT> updateInvoiceUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traInvoicePT", required = true) @RequestBody TraInvoicePT traInvoicePT) {
        log.debug("REST request to update CorNetwork : {}, for Network: {}", traInvoicePT, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (traInvoicePT.getId() == null) {
            return createInvoiceUsingPOST(networkShortcut, traInvoicePT);
        }
        return ResponseEntity.ok().body(traInvoiceService.saveInvoice(traInvoicePT, corNetwork));

    }

    @Override
    public ResponseEntity<TraInvoicePT> createInvoiceUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traInvoicePT", required = true) @RequestBody TraInvoicePT traInvoicePT) {
        log.debug("REST request to save TraInvoice : {}, for Network: {}", traInvoicePT, networkShortcut);
        if (traInvoicePT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraInvoice", "idexists", "A new TraInvoice cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traInvoiceService.saveInvoice(traInvoicePT, corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteInvoiceUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraInvoice : {}, for Network: {}", id, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        traInvoiceService.deleteInvoice(id, corNetwork);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TraInvoicePT> getInvoiceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraInvoice : {}, for Network: {}", id, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traInvoiceService.getInvoice(id, corNetwork));
    }

    @Override
    public ResponseEntity<Void> notifyAboutUnpaidInvoiceUsingGET(@ApiParam(value = "cutomerId", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
