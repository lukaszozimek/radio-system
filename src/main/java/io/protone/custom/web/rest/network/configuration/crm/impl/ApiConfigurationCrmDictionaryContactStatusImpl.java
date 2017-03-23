package io.protone.custom.web.rest.network.configuration.crm.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfCampaingStatusPT;
import io.protone.custom.service.dto.ConfCrmContactStatusPT;
import io.protone.custom.service.mapper.CustomCrmContactMapper;
import io.protone.custom.service.mapper.CustomCrmContactStatusMapper;
import io.protone.custom.web.rest.network.configuration.crm.ApiConfigurationCrmDictionaryContactStatus;
import io.protone.custom.web.rest.network.configuration.traffic.impl.ApiConfigurationTrafficDictionaryCampaingStatusImpl;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmContactStatus;
import io.protone.domain.TraCampaingStatus;
import io.protone.repository.CrmContactStatusRepository;
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
public class ApiConfigurationCrmDictionaryContactStatusImpl implements ApiConfigurationCrmDictionaryContactStatus {

    private final Logger log = LoggerFactory.getLogger(ApiConfigurationCrmDictionaryContactStatusImpl.class);

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmContactStatusRepository crmContactStatusRepository;

    @Inject
    private CustomCrmContactStatusMapper customCrmContactStatusMapper;

    @Override
    public ResponseEntity<List<ConfCrmContactStatusPT>> getAllCrmContactStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get CrmContactStatus : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<CrmContactStatus> corCurrencies = crmContactStatusRepository.findByNetwork(corNetwork);
        List<ConfCrmContactStatusPT> confCurrencyPTS = customCrmContactStatusMapper.DBs2DTOs(corCurrencies);
        return Optional.ofNullable(confCurrencyPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfCrmContactStatusPT> getCrmContactStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraCampaingStatus : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CrmContactStatus traCampaingStatus = crmContactStatusRepository.findOneByIdAndNetwork(id, corNetwork);
        ConfCrmContactStatusPT confCampaingStatusPT = customCrmContactStatusMapper.DB2DTO(traCampaingStatus);
        return Optional.ofNullable(confCampaingStatusPT)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfCrmContactStatusPT> updateCrmContactStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "contactStatusPT", required = true) @RequestBody ConfCrmContactStatusPT contactStatusPT) {
        log.debug("REST request to update CrmContactStatus : {}", contactStatusPT);
        if (contactStatusPT.getId() == null) {
            return createCrmContactStatusUsingPOST(networkShortcut, contactStatusPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmContactStatus corCountry = customCrmContactStatusMapper.DTO2DB(contactStatusPT);
        corCountry.setNetwork(corNetwork);
        corCountry = crmContactStatusRepository.save(corCountry);
        ConfCrmContactStatusPT result = customCrmContactStatusMapper.DB2DTO(corCountry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("CrmContactStatus", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfCrmContactStatusPT> createCrmContactStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "contactStatusPT", required = true) @RequestBody ConfCrmContactStatusPT contactStatusPT) {
        log.debug("REST request to save CrmContactStatus : {}", contactStatusPT);
        if (contactStatusPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmContactStatus", "idexists", "A new CrmContactStatus cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmContactStatus corCountry = customCrmContactStatusMapper.DTO2DB(contactStatusPT);
        corCountry.setNetwork(corNetwork);
        corCountry = crmContactStatusRepository.save(corCountry);
        ConfCrmContactStatusPT result = customCrmContactStatusMapper.DB2DTO(corCountry);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deleteCrmContactStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmContactStatus : {}", id);
        crmContactStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmContactStatus", id.toString())).build();
    }
}
