package io.protone.custom.web.rest.network.configuration.crm.impl;

import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.ConfLeadSourcePT;
import io.protone.custom.service.mapper.CustomCrmLeadSourceMapper;
import io.protone.custom.web.rest.network.configuration.crm.ApiConfigurationCrmDictionaryLeadSource;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmLeadSource;
import io.protone.repository.CCorNetworkRepository;
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
public class ApiConfigurationCrmDictionaryLeadSourceImpl implements ApiConfigurationCrmDictionaryLeadSource {

    private final Logger log = LoggerFactory.getLogger(ApiConfigurationCrmDictionaryLeadSourceImpl.class);

    @Inject
    private CrmLeadSourceRepository leadSourceRepository;
    @Inject
    private NetworkService networkService;
    @Inject
    private CustomCrmLeadSourceMapper crmLeadSourceMapper;

    @Override
    public ResponseEntity<ConfLeadSourcePT> createLeadsourceUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "leadStatus", required = true) @RequestBody ConfLeadSourcePT leadSourcePt) {
        log.debug("REST request to save CrmLead : {}", leadSourcePt);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        CrmLeadSource cRMLead = crmLeadSourceMapper.cRMLeadSourceDTOToCrmLeadSource(leadSourcePt);
        cRMLead.setNetwork(corNetwork);
        cRMLead = leadSourceRepository.save(cRMLead);
        ConfLeadSourcePT result = crmLeadSourceMapper.cRMLeadSourceToCrmLeadSourceDTO(cRMLead);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cRMLeadSource", result.getId().toString()))
            .body(result);

    }

    @Override
    public ResponseEntity<Void> deleteLeadsourceUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmLeadSource : {}", id);
        leadSourceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cRMLeadSource", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<ConfLeadSourcePT>> getAllLeadsourceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CrmLeadSources");
        List<CrmLeadSource> cRMLeadSources = leadSourceRepository.findAll();

        return ResponseEntity.ok()
            .body(crmLeadSourceMapper.cRMLeadSourcesToCrmLeadSourceDTOs(cRMLeadSources));

    }

    @Override
    public ResponseEntity<ConfLeadSourcePT> getLeadSourceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmLeadSource : {}", id);
        CrmLeadSource cRMLeadSource = leadSourceRepository.findOne(id);
        ConfLeadSourcePT cRMLeadSourceDTO = crmLeadSourceMapper.cRMLeadSourceToCrmLeadSourceDTO(cRMLeadSource);
        return Optional.ofNullable(cRMLeadSourceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfLeadSourcePT> updateLeadSourceUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "leadStatus", required = true) @RequestBody ConfLeadSourcePT leadSourcePT) {
        log.debug("REST request to update CrmLeadSource : {}", leadSourcePT);
        if (leadSourcePT.getId() == null) {
            return createLeadsourceUsingPOST(networkShortcut, leadSourcePT);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        CrmLeadSource cRMLeadSource = crmLeadSourceMapper.cRMLeadSourceDTOToCrmLeadSource(leadSourcePT);
        cRMLeadSource.setNetwork(corNetwork);
        cRMLeadSource = leadSourceRepository.save(cRMLeadSource);
        ConfLeadSourcePT result = crmLeadSourceMapper.cRMLeadSourceToCrmLeadSourceDTO(cRMLeadSource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cRMLeadSource", result.getId().toString()))
            .body(result);

    }
}
