package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.domain.TraInvoice;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraInvoiceService;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficInvoice;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.mapper.TraInvoiceMapper;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


@RestController
public class ApiNetworkTrafficInvoiceImpl implements ApiNetworkTrafficInvoice {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficInvoiceImpl.class);

    @Inject
    private TraInvoiceService traInvoiceService;

    @Inject
    private TraInvoiceMapper traInvoiceMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<List<TraInvoicePT>> getAllInvoicesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraInvoice, for Network: {}", networkShortcut);
        List<TraInvoice> entity = traInvoiceService.getAllInvoice(networkShortcut, pagable);
        List<TraInvoicePT> response = traInvoiceMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraInvoicePT> updateInvoiceUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traInvoicePT", required = true) @RequestBody TraInvoicePT traInvoicePT) {
        log.debug("REST request to update TraInvoice : {}, for Network: {}", traInvoicePT, networkShortcut);
        if (traInvoicePT.getId() == null) {
            return createInvoiceUsingPOST(networkShortcut, traInvoicePT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraInvoice traInvoice = traInvoiceMapper.DTO2DB(traInvoicePT, corNetwork);
        TraInvoice entity = traInvoiceService.saveInvoice(traInvoice);
        TraInvoicePT response = traInvoiceMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    @Override
    public ResponseEntity<TraInvoicePT> createInvoiceUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traInvoicePT", required = true) @RequestBody TraInvoicePT traInvoicePT) {
        log.debug("REST request to save TraInvoice : {}, for Network: {}", traInvoicePT, networkShortcut);
        if (traInvoicePT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraInvoice", "idexists", "A new TraInvoice cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraInvoice traInvoice = traInvoiceMapper.DTO2DB(traInvoicePT, corNetwork);
        TraInvoice entity = traInvoiceService.saveInvoice(traInvoice);
        TraInvoicePT response = traInvoiceMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteInvoiceUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraInvoice : {}, for Network: {}", id, networkShortcut);
        traInvoiceService.deleteInvoice(id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traOrder", id.toString())).build();
    }

    @Override
    public ResponseEntity<TraInvoicePT> getInvoiceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraInvoice : {}, for Network: {}", id, networkShortcut);
        TraInvoice entity = traInvoiceService.getInvoice(id, networkShortcut);
        TraInvoicePT response = traInvoiceMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> notifyAboutUnpaidInvoiceUsingGET(@ApiParam(value = "cutomerId", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
