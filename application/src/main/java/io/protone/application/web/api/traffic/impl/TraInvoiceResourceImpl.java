package io.protone.application.web.api.traffic.impl;


import io.protone.application.web.api.traffic.TraInvoiceResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.traffic.api.dto.TraInvoiceDTO;
import io.protone.traffic.api.dto.thin.TraInvoiceThinDTO;
import io.protone.traffic.domain.TraInvoice;
import io.protone.traffic.mapper.TraInvoiceMapper;
import io.protone.traffic.service.TraInvoiceService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
public class TraInvoiceResourceImpl implements TraInvoiceResource {
    private final Logger log = LoggerFactory.getLogger(TraInvoiceResourceImpl.class);

    @Inject
    private TraInvoiceService traInvoiceService;

    @Inject
    private TraInvoiceMapper traInvoiceMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<List<TraInvoiceThinDTO>> getAllInvoicesUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                          @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraInvoice, for Network: {}", organizationShortcut);
        Slice<TraInvoice> entity = traInvoiceService.getAllInvoice(organizationShortcut, pagable);
        List<TraInvoiceThinDTO> response = traInvoiceMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraInvoiceDTO> updateInvoiceUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "traInvoiceDTO", required = true) @Valid @RequestBody TraInvoiceDTO traInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to update TraInvoice : {}, for Network: {}", traInvoiceDTO, organizationShortcut);
        if (traInvoiceDTO.getId() == null) {
            return createInvoiceUsingPOST(organizationShortcut, traInvoiceDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        TraInvoice traInvoice = traInvoiceMapper.DTO2DB(traInvoiceDTO, corNetwork);
        TraInvoice entity = traInvoiceService.saveInvoice(traInvoice);
        TraInvoiceDTO response = traInvoiceMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    @Override
    public ResponseEntity<TraInvoiceDTO> createInvoiceUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "traInvoiceDTO", required = true) @Valid @RequestBody TraInvoiceDTO traInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact TraInvoice : {}, for Network: {}", traInvoiceDTO, organizationShortcut);
        if (traInvoiceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraInvoice", "idexists", "A new TraInvoice cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        TraInvoice traInvoice = traInvoiceMapper.DTO2DB(traInvoiceDTO, corNetwork);
        TraInvoice entity = traInvoiceService.saveInvoice(traInvoice);
        TraInvoiceDTO response = traInvoiceMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/traffic/invoice/" + response.getId()))
                .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteInvoiceUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraInvoice : {}, for Network: {}", id, organizationShortcut);
        traInvoiceService.deleteInvoice(id, organizationShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traOrder", id.toString())).build();
    }

    @Override
    public ResponseEntity<TraInvoiceDTO> getInvoiceUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraInvoice : {}, for Network: {}", id, organizationShortcut);
        TraInvoice entity = traInvoiceService.getInvoice(id, organizationShortcut);
        TraInvoiceDTO response = traInvoiceMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<TraInvoiceThinDTO>> getAllTrafficInvoicesForCustomerGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                       @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraInvoice, for TraCustomer: {} and Network: {}", customerShortcut, organizationShortcut);
        Slice<TraInvoice> entity = traInvoiceService.getCustomerInvoice(customerShortcut, organizationShortcut, pagable);
        List<TraInvoiceThinDTO> response = traInvoiceMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> notifyAboutUnpaidInvoiceUsingGET(@ApiParam(value = "cutomerId", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
