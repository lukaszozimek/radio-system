package io.protone.custom.web.rest.network.configuration.traffic.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfCampaingStatusPT;
import io.protone.custom.service.dto.ConfCountryPt;
import io.protone.custom.service.mapper.CustomTraCampaingStatusMapper;
import io.protone.custom.web.rest.network.configuration.core.dictionary.impl.ApiDictionaryCountryImpl;
import io.protone.custom.web.rest.network.configuration.traffic.ApiConfigurationTrafficDictionaryCampaingStatus;
import io.protone.domain.CorCountry;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraCampaingStatus;
import io.protone.repository.TraCampaingStatusRepository;
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
public class ApiConfigurationTrafficDictionaryCampaingStatusImpl implements ApiConfigurationTrafficDictionaryCampaingStatus {
    private final Logger log = LoggerFactory.getLogger(ApiConfigurationTrafficDictionaryCampaingStatusImpl.class);

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private TraCampaingStatusRepository traCampaingStatusRepository;

    @Inject
    private CustomTraCampaingStatusMapper customTraCampaingStatusMapper;

    @Override
    public ResponseEntity<List<ConfCampaingStatusPT>> getAllCampaingStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get TraCampaingStatus : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<TraCampaingStatus> corCurrencies = traCampaingStatusRepository.findByNetwork(corNetwork);
        List<ConfCampaingStatusPT> confCurrencyPTS = customTraCampaingStatusMapper.DBs2DTOs(corCurrencies);
        return Optional.ofNullable(confCurrencyPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfCampaingStatusPT> getCampaingStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraCampaingStatus : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        TraCampaingStatus traCampaingStatus = traCampaingStatusRepository.findOneByIdAndNetwork(id, corNetwork);
        ConfCampaingStatusPT confCampaingStatusPT = customTraCampaingStatusMapper.DB2DTO(traCampaingStatus);
        return Optional.ofNullable(confCampaingStatusPT)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfCampaingStatusPT> updateCampaingStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confCampaingStatusPT", required = true) @RequestBody ConfCampaingStatusPT confCampaingStatusPT) {
        log.debug("REST request to update TraCampaingStatus : {}", confCampaingStatusPT);
        if (confCampaingStatusPT.getId() == null) {
            return createCampaingStatusUsingPOST(networkShortcut, confCampaingStatusPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraCampaingStatus corCountry = customTraCampaingStatusMapper.DTO2DB(confCampaingStatusPT);
        corCountry.setNetwork(corNetwork);
        corCountry = traCampaingStatusRepository.save(corCountry);
        ConfCampaingStatusPT result = customTraCampaingStatusMapper.DB2DTO(corCountry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("TraCampaingStatus", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfCampaingStatusPT> createCampaingStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confCampaingStatusPT", required = true) @RequestBody ConfCampaingStatusPT confCampaingStatusPT) {
        log.debug("REST request to save TraCampaingStatus : {}", confCampaingStatusPT);
        if (confCampaingStatusPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraCampaingStatus", "idexists", "A new TraCampaingStatus cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraCampaingStatus corCountry = customTraCampaingStatusMapper.DTO2DB(confCampaingStatusPT);
        corCountry.setNetwork(corNetwork);
        corCountry = traCampaingStatusRepository.save(corCountry);
        ConfCampaingStatusPT result = customTraCampaingStatusMapper.DB2DTO(corCountry);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deleteCampaingStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraCampaingStatus : {}", id);
        traCampaingStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("TraCampaingStatus", id.toString())).build();

    }
}
