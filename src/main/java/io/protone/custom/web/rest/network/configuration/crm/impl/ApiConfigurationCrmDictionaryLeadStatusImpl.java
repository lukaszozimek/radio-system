package io.protone.custom.web.rest.network.configuration.crm.impl;


import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.ConfLeadStatusPT;
import io.protone.custom.service.mapper.CustomCRMLeadStatusMapper;
import io.protone.custom.web.rest.network.configuration.crm.ApiConfigurationCrmDictionaryLeadStatus;
import io.protone.domain.CRMLeadStatus;
import io.protone.repository.CCORNetworkRepository;
import io.protone.repository.CRMLeadStatusRepository;
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
public class ApiConfigurationCrmDictionaryLeadStatusImpl implements ApiConfigurationCrmDictionaryLeadStatus {

    private final Logger log = LoggerFactory.getLogger(ApiConfigurationCrmDictionaryLeadStatusImpl.class);

    @Inject
    private CRMLeadStatusRepository cRMLeadStatusRepository;
    @Inject
    private NetworkService networkService;
    @Inject
    private CustomCRMLeadStatusMapper customCRMLeadStatusMapper;

    @Override
    public ResponseEntity<Void> deleteLeadStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CRMLeadStatus : {}", id);
        cRMLeadStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cRMLeadStatus", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<ConfLeadStatusPT>> getAllLeadStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CRMLeadStatuses");
        List<CRMLeadStatus> cRMLeadStatuses = cRMLeadStatusRepository.findAll();
        return ResponseEntity.ok().body(customCRMLeadStatusMapper.cRMLeadStatusesToCRMLeadStatusDTOs(cRMLeadStatuses));
    }

    @Override
    public ResponseEntity<ConfLeadStatusPT> getLeadStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CRMLeadStatus : {}", id);
        CRMLeadStatus cRMLeadStatus = cRMLeadStatusRepository.findOne(id);
        ConfLeadStatusPT cRMLeadStatusDTO = customCRMLeadStatusMapper.cRMLeadStatusToCRMLeadStatusDTO(cRMLeadStatus);
        return Optional.ofNullable(cRMLeadStatusDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfLeadStatusPT> updateleadStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "leadStatus", required = true) @RequestBody ConfLeadStatusPT leadStatus) {
        log.debug("REST request to update CRMLeadStatus : {}", leadStatus);
        if (leadStatus.getId() == null) {
            return createLeadStatusUsingPOST(networkShortcut, leadStatus);
        }
        CRMLeadStatus cRMLeadStatus = customCRMLeadStatusMapper.cRMLeadStatusDTOToCRMLeadStatus(leadStatus);
        cRMLeadStatus = cRMLeadStatusRepository.save(cRMLeadStatus);
        ConfLeadStatusPT result = customCRMLeadStatusMapper.cRMLeadStatusToCRMLeadStatusDTO(cRMLeadStatus);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<ConfLeadStatusPT> createLeadStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "leadStatus", required = true) @RequestBody ConfLeadStatusPT leadStatus) {
        log.debug("REST request to save CRMLeadStatus : {}", leadStatus);
        if (leadStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cRMLeadStatus", "idexists", "A new cRMLeadStatus cannot already have an ID")).body(null);
        }
        CRMLeadStatus cRMLeadStatus = customCRMLeadStatusMapper.cRMLeadStatusDTOToCRMLeadStatus(leadStatus);
        cRMLeadStatus = cRMLeadStatusRepository.save(cRMLeadStatus);
        ConfLeadStatusPT result = customCRMLeadStatusMapper.cRMLeadStatusToCRMLeadStatusDTO(cRMLeadStatus);
        return ResponseEntity.ok()
            .body(result);
    }
}
