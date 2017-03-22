package io.protone.custom.web.rest.network.configuration.traffic.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfCampaingStatusPT;
import io.protone.custom.service.dto.ConfTaxPT;
import io.protone.custom.service.dto.ConfTraOrderStatusPT;
import io.protone.custom.service.mapper.CustomTraOrderStatusMapper;
import io.protone.custom.web.rest.network.configuration.traffic.ApiConfigurationTrafficDictionaryOrderStatus;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraCampaingStatus;
import io.protone.domain.TraOrderStatus;
import io.protone.repository.TraOrderStatusRepository;
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
public class ApiConfigurationTrafficDictionaryOrderStatusImpl implements ApiConfigurationTrafficDictionaryOrderStatus {

    private final Logger log = LoggerFactory.getLogger(ApiConfigurationTrafficDictionaryOrderStatusImpl.class);

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private TraOrderStatusRepository traOrderStatusRepository;

    @Inject
    private CustomTraOrderStatusMapper traOrderStatusMapper;


    @Override
    public ResponseEntity<List<ConfTraOrderStatusPT>> getAllOrderStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get TraOrderStatus : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<TraOrderStatus> corCurrencies = traOrderStatusRepository.findByNetwork(corNetwork);
        List<ConfTraOrderStatusPT> confCurrencyPTS = traOrderStatusMapper.DBs2DTOs(corCurrencies);
        return Optional.ofNullable(confCurrencyPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfTraOrderStatusPT> getOrderStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraOrderStatus : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        TraOrderStatus traCampaingStatus = traOrderStatusRepository.findOneByIdAndNetwork(id, corNetwork);
        ConfTraOrderStatusPT confCampaingStatusPT = traOrderStatusMapper.DB2DTO(traCampaingStatus);
        return Optional.ofNullable(confCampaingStatusPT)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfTraOrderStatusPT> updateOrderStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confTraOrderStatusPT", required = true) @RequestBody ConfTraOrderStatusPT confTraOrderStatusPT) {
        log.debug("REST request to update TraOrderStatus : {}", confTraOrderStatusPT);
        if (confTraOrderStatusPT.getId() == null) {
            return createOrderStatusUsingPOST(networkShortcut, confTraOrderStatusPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraOrderStatus corCountry = traOrderStatusMapper.DTO2DB(confTraOrderStatusPT);
        corCountry.setNetwork(corNetwork);
        corCountry = traOrderStatusRepository.save(corCountry);
        ConfTraOrderStatusPT result = traOrderStatusMapper.DB2DTO(corCountry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("TraCampaingStatus", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfTraOrderStatusPT> createOrderStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confTraOrderStatusPT", required = true) @RequestBody ConfTraOrderStatusPT confTraOrderStatusPT) {
        log.debug("REST request to save TraOrderStatus : {}", confTraOrderStatusPT);
        if (confTraOrderStatusPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraOrderStatus", "idexists", "A new TraOrderStatus cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraOrderStatus corCountry = traOrderStatusMapper.DTO2DB(confTraOrderStatusPT);
        corCountry.setNetwork(corNetwork);
        corCountry = traOrderStatusRepository.save(corCountry);
        ConfTraOrderStatusPT result = traOrderStatusMapper.DB2DTO(corCountry);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deleteOrderStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraOrderStatus : {}", id);
        traOrderStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("TraOrderStatus", id.toString())).build();
    }
}
