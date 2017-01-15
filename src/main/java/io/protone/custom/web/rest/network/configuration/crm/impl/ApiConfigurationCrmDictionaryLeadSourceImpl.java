package io.protone.custom.web.rest.network.configuration.crm.impl;

import io.protone.custom.service.dto.ConfLeadSourcePT;
import io.protone.custom.service.mapper.CRMLeadSourceMapper;
import io.protone.custom.web.rest.network.configuration.crm.ApiConfigurationCrmDictionaryLeadSource;
import io.protone.domain.CRMLeadSource;
import io.protone.repository.CRMLeadSourceRepository;
import io.protone.service.dto.CRMLeadSourceDTO;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class ApiConfigurationCrmDictionaryLeadSourceImpl implements ApiConfigurationCrmDictionaryLeadSource {

    private final Logger log = LoggerFactory.getLogger(ApiConfigurationCrmDictionaryLeadSourceImpl.class);

    @Inject
    private CRMLeadSourceRepository leadSourceRepository;

    @Inject
    private CRMLeadSourceMapper crmLeadSourceMapper;

    @Override
    public ResponseEntity<ConfLeadSourcePT> createLeadsourceUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "leadStatus", required = true) @RequestBody ConfLeadSourcePT leadSourcePt) {
        log.debug("REST request to save CRMLead : {}", leadSourcePt);
        if (leadSourcePt.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cRMLead", "idexists", "A new cRMLead cannot already have an ID")).body(null);
        }
        CRMLeadSource cRMLead = crmLeadSourceMapper.cRMLeadSourceDTOToCRMLeadSource(leadSourcePt);
        cRMLead = leadSourceRepository.save(cRMLead);
        ConfLeadSourcePT result = crmLeadSourceMapper.cRMLeadSourceToCRMLeadSourceDTO(cRMLead);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cRMLeadSource", result.getId().toString()))
            .body(result);

    }

    @Override
    public ResponseEntity<Void> deleteLeadsourceUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CRMLeadSource : {}", id);
        leadSourceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cRMLeadSource", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<ConfLeadSourcePT>> getAllLeadsourceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CRMLeadSources");
        List<CRMLeadSource> cRMLeadSources = leadSourceRepository.findAll();

        return new ResponseEntity<>(
            crmLeadSourceMapper.cRMLeadSourcesToCRMLeadSourceDTOs(cRMLeadSources),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ConfLeadSourcePT> getLeadSourceUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CRMLeadSource : {}", id);
        CRMLeadSource cRMLeadSource = leadSourceRepository.findOne(id);
        ConfLeadSourcePT cRMLeadSourceDTO = crmLeadSourceMapper.cRMLeadSourceToCRMLeadSourceDTO(cRMLeadSource);
        return Optional.ofNullable(cRMLeadSourceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfLeadSourcePT> updateLeadSourceUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "leadStatus", required = true) @RequestBody ConfLeadSourcePT leadSourcePT) {
        log.debug("REST request to update CRMLeadSource : {}", leadSourcePT);
        if (leadSourcePT.getId() == null) {
            return createLeadsourceUsingPOST(networkShortcut, leadSourcePT);
        }
        CRMLeadSource cRMLeadSource = crmLeadSourceMapper.cRMLeadSourceDTOToCRMLeadSource(leadSourcePT);
        cRMLeadSource = leadSourceRepository.save(cRMLeadSource);
        ConfLeadSourcePT result = crmLeadSourceMapper.cRMLeadSourceToCRMLeadSourceDTO(cRMLeadSource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cRMLeadSource", result.getId().toString()))
            .body(result);

    }
}
