package io.protone.web.api.crm.impl;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmTaskComment;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.crm.CrmOpportunityService;
import io.protone.web.api.crm.CrmOpportunityTaskCommentResource;
import io.protone.web.rest.dto.traffic.CrmTaskCommentDTO;
import io.protone.web.rest.mapper.CrmTaskCommentMapper;
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
public class CrmOpportunityTaskCommentResourceImpl implements CrmOpportunityTaskCommentResource {
    private final Logger log = LoggerFactory.getLogger(CrmOpportunityTaskCommentResourceImpl.class);

    @Inject
    private CrmOpportunityService crmOpportunityService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmTaskCommentMapper crmTaskCommentMapper;


    @Override
    public ResponseEntity<List<CrmTaskCommentDTO>> getOpportunityTaskCommentsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                      @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmTaskComment, for CrmTask : {},  CrmOpportunity: {} and Network: {}", taskId, shortName, networkShortcut);
        List<CrmTaskComment> crmTasks = crmOpportunityService.getTaskCommentsAssociatedWithTask(taskId, networkShortcut, pagable);
        List<CrmTaskCommentDTO> crmTaskDTOS = crmTaskCommentMapper.DBs2DTOs(crmTasks);
        return Optional.ofNullable(crmTaskDTOS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> createOpportunityActivtyCommentUsigPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                     @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                     @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                                     @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CrmTaskComment{}, for CrmTask : {}, CrmOpportunity: {} and Network: {}", taskCommentDTO, taskId, shortName, networkShortcut);
        if (taskCommentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTaskComment", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTaskComment requestEnitity = crmTaskCommentMapper.DTO2DB(taskCommentDTO, corNetwork);
        CrmTaskComment entity = crmOpportunityService.saveOrUpdateTaskCommentAssociatedWithTask(requestEnitity, taskId, networkShortcut);
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/opportunity/" + shortName + "/task/" + taskId + "/" + "comment/" + response.getId()))
            .body(response);
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> editOpportunityActivtyCommentUsigPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                  @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                                  @ApiParam(value = "taskCommentDTO", required = true) @Valid @RequestBody CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException {
        log.debug("REST request to update CrmTaskComment{}, for CrmTask : {}, CrmOpportunity: {} and Network: {}", taskCommentDTO, taskId, shortName, networkShortcut);
        if (taskCommentDTO.getId() == null) {
            return createOpportunityActivtyCommentUsigPOST(networkShortcut, shortName, taskId, taskCommentDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CrmTaskComment requestEnitity = crmTaskCommentMapper.DTO2DB(taskCommentDTO, corNetwork);
        CrmTaskComment entity = crmOpportunityService.saveOrUpdateTaskCommentAssociatedWithTask(requestEnitity, taskId, networkShortcut);
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> getOpportunityTaskCommentUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                               @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                               @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmTaskComment{}, for CrmTask : {}, CrmOpportunity: {} and Network: {}", id, taskId, shortName, networkShortcut);
        CrmTaskComment entity = crmOpportunityService.getTaskCommentAssociatedWithTask(networkShortcut, taskId, id);
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteOpportunityTaskCommentUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                        @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmOpportunity CrmTask : {}, for CrmOpportunity: {} and Network: {}", id, shortName, networkShortcut);
        crmOpportunityService.deleteOpportunityTaskComment(taskId, id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmTask", id.toString())).build();

    }
}
