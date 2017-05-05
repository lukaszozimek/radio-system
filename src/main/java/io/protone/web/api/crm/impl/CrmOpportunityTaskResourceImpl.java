package io.protone.web.api.crm.impl;

import io.protone.web.api.crm.CrmOpportunityTaskResource;
import io.protone.web.rest.dto.crm.CrmTaskDTO;
import io.protone.domain.CorNetwork;
import io.protone.service.crm.CrmOpportunityService;
import io.protone.domain.CrmTask;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.api.library.impl.LibraryMarkerConfigurationResourceImpl;
import io.protone.web.rest.mapper.CrmTaskMapper;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
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
public class CrmOpportunityTaskResourceImpl implements CrmOpportunityTaskResource {
    private final Logger log = LoggerFactory.getLogger(LibraryMarkerConfigurationResourceImpl.class);

    @Inject
    private CrmOpportunityService crmOpportunityService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmTaskMapper crmTaskMapper;

    @Override
    public ResponseEntity<List<CrmTaskDTO>> getAllOpportunityActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                @ApiParam(value = "pagable", required = true) Pageable pageable) {
        log.debug("REST request to get all CrmOpportunity CrmTask, for CrmOpportunity: {} and Network: {}", shortName, networkShortcut);
        List<CrmTask> crmTasks = crmOpportunityService.getTasksAssociatedWithOpportunity(shortName, networkShortcut, pageable);
        List<CrmTaskDTO> crmTaskDTOS = crmTaskMapper.DBs2DTOs(crmTasks);
        return Optional.ofNullable(crmTaskDTOS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmTaskDTO> updateOpportunityActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmTaskDTO", required = true)@Valid @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException {
        log.debug("REST request to update CrmOpportunity CrmTask : {}, for CrmOpportunity: {} and Network: {}", crmTaskDTO, shortName, networkShortcut);
        if (crmTaskDTO.getId() == null) {
            return createOpportunityActivityUsingPOST(networkShortcut, shortName, crmTaskDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CrmTask requestEnitity = crmTaskMapper.DTO2DB(crmTaskDTO, corNetwork);
        CrmTask entity = crmOpportunityService.saveOrUpdateTaskAssociatiedWithOpportunity(requestEnitity, shortName, networkShortcut);
        CrmTaskDTO response = crmTaskMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);

    }

    @Override
    public ResponseEntity<CrmTaskDTO> createOpportunityActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmTaskDTO", required = true)@Valid @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException {
        log.debug("REST request to save CrmOpportunity CrmTask : {}, for CrmOpportunity: {} and Network: {}", crmTaskDTO, shortName, networkShortcut);
        if (crmTaskDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTask", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTask requestEnitity = crmTaskMapper.DTO2DB(crmTaskDTO, corNetwork);
        CrmTask entity = crmOpportunityService.saveOrUpdateTaskAssociatiedWithOpportunity(requestEnitity, shortName, networkShortcut);
        CrmTaskDTO response = crmTaskMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/opportunity/" + shortName + "/task/" + response.getId()))
            .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteOpportunityActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmOpportunity CrmTask : {}, for CrmOpportunity: {} and Network: {}", id, shortName, networkShortcut);
        crmOpportunityService.deleteOpportunityTask(shortName, id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmTask", id.toString())).build();
    }

    @Override
    public ResponseEntity<CrmTaskDTO> getOpportunityActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmOpportunity CrmTask : {}, for CrmOpportunity: {} and Network: {}", id, shortName, networkShortcut);
        CrmTask entity = crmOpportunityService.getTaskAssociatedWithOpportunity(id, networkShortcut);
        CrmTaskDTO response = crmTaskMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
