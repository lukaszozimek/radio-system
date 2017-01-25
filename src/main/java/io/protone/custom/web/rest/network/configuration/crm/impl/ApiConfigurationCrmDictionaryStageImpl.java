package io.protone.custom.web.rest.network.configuration.crm.impl;

import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.ConfCrmStagePT;
import io.protone.custom.service.mapper.CustomCRMStageMapper;
import io.protone.custom.web.rest.network.configuration.crm.ApiConfigurationCrmDictionaryStage;
import io.protone.domain.CRMStage;
import io.protone.repository.CCORNetworkRepository;
import io.protone.repository.CRMStageRepository;
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
public class ApiConfigurationCrmDictionaryStageImpl implements ApiConfigurationCrmDictionaryStage {

    private final Logger log = LoggerFactory.getLogger(ApiConfigurationCrmDictionaryStageImpl.class);

    @Inject
    private CRMStageRepository cRMStageRepository;
    @Inject
    private NetworkService networkService;
    @Inject
    private CustomCRMStageMapper customCRMStageMapper;

    @Override
    public ResponseEntity<List<ConfCrmStagePT>> getAllCrmStagesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CRMStages");
        List<CRMStage> cRMStages = cRMStageRepository.findAll();
        return ResponseEntity.ok().body(customCRMStageMapper.cRMStagesToCRMStageDTOs(cRMStages));
    }

    @Override
    public ResponseEntity<ConfCrmStagePT> getCrmStageUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CRMStage : {}", id);
        CRMStage cRMStage = cRMStageRepository.findOne(id);
        ConfCrmStagePT cRMStageDTO = customCRMStageMapper.cRMStageToCRMStageDTO(cRMStage);
        return Optional.ofNullable(cRMStageDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfCrmStagePT> updateCrmStageUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmStage", required = true) @RequestBody ConfCrmStagePT crmStage) {
        log.debug("REST request to update CRMStage : {}", crmStage);
        if (crmStage.getId() == null) {
            return createCrmStageUsingPOST(networkShortcut, crmStage);
        }
        CRMStage cRMStage = customCRMStageMapper.cRMStageDTOToCRMStage(crmStage);
        cRMStage = cRMStageRepository.save(cRMStage);
        ConfCrmStagePT result = customCRMStageMapper.cRMStageToCRMStageDTO(cRMStage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cRMStage", crmStage.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfCrmStagePT> createCrmStageUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmStage", required = true) @RequestBody ConfCrmStagePT crmStage) {
        log.debug("REST request to save CRMStage : {}", crmStage);
        if (crmStage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cRMStage", "idexists", "A new cRMStage cannot already have an ID")).body(null);
        }
        CRMStage cRMStage = customCRMStageMapper.cRMStageDTOToCRMStage(crmStage);
        cRMStage = cRMStageRepository.save(cRMStage);
        ConfCrmStagePT result = customCRMStageMapper.cRMStageToCRMStageDTO(cRMStage);
        return ResponseEntity.ok().body(result);
    }


    @Override
    public ResponseEntity<Void> deleteCrmStageUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CRMStage : {}", id);
        cRMStageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cRMStage", id.toString())).build();

    }
}
