package io.protone.custom.web.rest.network.configuration.traffic.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfCampaingStatusPT;
import io.protone.custom.service.dto.ConfInvoiceStatusPT;
import io.protone.custom.service.mapper.CustomTraInvoiceStatusMapper;
import io.protone.custom.web.rest.network.configuration.traffic.ApiConfigurationTrafficDictionaryInvoiceStatus;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraCampaingStatus;
import io.protone.domain.TraInvoiceStatus;
import io.protone.repository.TraInvoiceStatusRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiConfigurationTrafficDictionaryInvoiceStatusImpl implements ApiConfigurationTrafficDictionaryInvoiceStatus {
    private final Logger log = LoggerFactory.getLogger(ApiConfigurationTrafficDictionaryInvoiceStatusImpl.class);


    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private TraInvoiceStatusRepository traInvoiceStatusRepository;

    @Inject
    private CustomTraInvoiceStatusMapper customTraInvoiceStatusMapper;

    @Override
    public ResponseEntity<List<ConfInvoiceStatusPT>> getAllInvoiceStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get TraInvoiceStatus : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<TraInvoiceStatus> corCurrencies = traInvoiceStatusRepository.findByNetwork(corNetwork);
        List<ConfInvoiceStatusPT> confCurrencyPTS = customTraInvoiceStatusMapper.DBs2DTOs(corCurrencies);
        return Optional.ofNullable(confCurrencyPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfInvoiceStatusPT> getInvoiceStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraInvoiceStatus : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        TraInvoiceStatus traCampaingStatus = traInvoiceStatusRepository.findOneByIdAndNetwork(id, corNetwork);
        ConfInvoiceStatusPT confCampaingStatusPT = customTraInvoiceStatusMapper.DB2DTO(traCampaingStatus);
        return Optional.ofNullable(confCampaingStatusPT)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfInvoiceStatusPT> updateInvoiceStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true) @RequestBody ConfInvoiceStatusPT confInvoiceStatusPT) {
        log.debug("REST request to update TraInvoiceStatus : {}", confInvoiceStatusPT);
        if (confInvoiceStatusPT.getId() == null) {
            return createInvoiceStatusUsingPOST(networkShortcut, confInvoiceStatusPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraInvoiceStatus corCountry = customTraInvoiceStatusMapper.DTO2DB(confInvoiceStatusPT);
        corCountry.setNetwork(corNetwork);
        corCountry = traInvoiceStatusRepository.save(corCountry);
        ConfInvoiceStatusPT result = customTraInvoiceStatusMapper.DB2DTO(corCountry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("TraInvoiceStatus", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfInvoiceStatusPT> createInvoiceStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true) @RequestBody ConfInvoiceStatusPT confInvoiceStatusPT) {
        log.debug("REST request to save TraInvoiceStatus : {}", confInvoiceStatusPT);
        if (confInvoiceStatusPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraInvoiceStatus", "idexists", "A new TraInvoiceStatus cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraInvoiceStatus corCountry = customTraInvoiceStatusMapper.DTO2DB(confInvoiceStatusPT);
        corCountry.setNetwork(corNetwork);
        corCountry = traInvoiceStatusRepository.save(corCountry);
        ConfInvoiceStatusPT result = customTraInvoiceStatusMapper.DB2DTO(corCountry);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deleteInvoiceStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraInvoiceStatus : {}", id);
        traInvoiceStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("TraInvoiceStatus", id.toString())).build();
    }
}
