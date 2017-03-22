package io.protone.custom.web.rest.network.configuration.crm.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfCrmTaskStatusPT;
import io.protone.custom.service.mapper.CustomCrmTaskStatusMapper;
import io.protone.custom.web.rest.network.configuration.crm.ApiConfigurationCrmDictionaryTaskStatus;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmTaskStatus;
import io.protone.repository.CrmTaskStatusRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiConfigurationCrmDictionaryTaskStatusImpl implements ApiConfigurationCrmDictionaryTaskStatus {
    private final Logger log = LoggerFactory.getLogger(ApiConfigurationCrmDictionaryTaskStatusImpl.class);

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmTaskStatusRepository crmTaskStatusRepository;

    @Inject
    private CustomCrmTaskStatusMapper customCrmTaskStatusMapper;

    @Override
    public ResponseEntity<List<ConfCrmTaskStatusPT>> getAllCrmTaskStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get CrmTaskStatus : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<CrmTaskStatus> corCurrencies = crmTaskStatusRepository.findByNetwork(corNetwork);
        List<ConfCrmTaskStatusPT> confCurrencyPTS = customCrmTaskStatusMapper.DBs2DTOs(corCurrencies);
        return Optional.ofNullable(confCurrencyPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfCrmTaskStatusPT> getCrmTaskStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmTaskStatus : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CrmTaskStatus traCampaingStatus = crmTaskStatusRepository.findOneByIdAndNetwork(id, corNetwork);
        ConfCrmTaskStatusPT confCampaingStatusPT = customCrmTaskStatusMapper.DB2DTO(traCampaingStatus);
        return Optional.ofNullable(confCampaingStatusPT)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfCrmTaskStatusPT> updateCrmTaskStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmStage", required = true) @RequestBody ConfCrmTaskStatusPT confCrmTaskStatusPT) {
        log.debug("REST request to update CrmTaskStatus : {}", confCrmTaskStatusPT);
        if (confCrmTaskStatusPT.getId() == null) {
            return createCrmTaskStatusUsingPOST(networkShortcut, confCrmTaskStatusPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTaskStatus corCountry = customCrmTaskStatusMapper.DTO2DB(confCrmTaskStatusPT);
        corCountry.setNetwork(corNetwork);
        corCountry = crmTaskStatusRepository.save(corCountry);
        ConfCrmTaskStatusPT result = customCrmTaskStatusMapper.DB2DTO(corCountry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("CrmTaskStatus", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfCrmTaskStatusPT> createCrmTaskStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmStage", required = true) @RequestBody ConfCrmTaskStatusPT confCrmTaskStatusPT) {
        log.debug("REST request to save CrmTaskStatus : {}", confCrmTaskStatusPT);
        if (confCrmTaskStatusPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTaskStatus", "idexists", "A new CrmTaskStatus cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTaskStatus corCountry = customCrmTaskStatusMapper.DTO2DB(confCrmTaskStatusPT);
        corCountry.setNetwork(corNetwork);
        corCountry = crmTaskStatusRepository.save(corCountry);
        ConfCrmTaskStatusPT result = customCrmTaskStatusMapper.DB2DTO(corCountry);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deleteCrmTaskStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmTaskStatus : {}", id);
        crmTaskStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmTaskStatus", id.toString())).build();
    }
}
