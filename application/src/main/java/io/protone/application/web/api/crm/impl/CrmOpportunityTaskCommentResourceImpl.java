package io.protone.application.web.api.crm.impl;


import io.protone.application.web.api.crm.CrmOpportunityTaskCommentResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.crm.api.dto.CrmTaskCommentDTO;
import io.protone.crm.domain.CrmTaskComment;
import io.protone.crm.mapper.CrmTaskCommentMapper;
import io.protone.crm.service.CrmOpportunityService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    private CorChannelService corChannelService;

    @Inject
    private CrmTaskCommentMapper crmTaskCommentMapper;


    @Override
    public ResponseEntity<List<CrmTaskCommentDTO>> getOpportunityTaskCommentsUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                      @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmTaskComment, for CrmTask : {},  CrmOpportunity: {} and Network: {}", taskId, shortName, organizationShortcut);
        Slice<CrmTaskComment> crmTasks = crmOpportunityService.getTaskCommentsAssociatedWithTask(taskId, organizationShortcut, channelShortcut, pagable);
        List<CrmTaskCommentDTO> crmTaskDTOS = crmTaskCommentMapper.DBs2DTOs(crmTasks.getContent());
        return Optional.ofNullable(crmTaskDTOS)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(crmTasks),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(crmTasks), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> createOpportunityActivtyCommentUsigPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                     @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                     @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                                     @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CrmTaskComment{}, for CrmTask : {}, CrmOpportunity: {} and Network: {}", taskCommentDTO, taskId, shortName, organizationShortcut);
        if (taskCommentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTaskComment", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        CrmTaskComment requestEnitity = crmTaskCommentMapper.DTO2DB(taskCommentDTO, corChannel);
        CrmTaskComment entity = crmOpportunityService.saveOrUpdateTaskCommentAssociatedWithTask(requestEnitity, taskId, organizationShortcut, channelShortcut);
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/crm/opportunity/" + shortName + "/task/" + taskId + "/" + "comment/" + response.getId()))
                .body(response);
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> editOpportunityActivtyCommentUsigPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                  @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                                  @ApiParam(value = "taskCommentDTO", required = true) @Valid @RequestBody CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException {
        log.debug("REST request to update CrmTaskComment{}, for CrmTask : {}, CrmOpportunity: {} and Network: {}", taskCommentDTO, taskId, shortName, organizationShortcut);
        if (taskCommentDTO.getId() == null) {
            return createOpportunityActivtyCommentUsigPOST(organizationShortcut, channelShortcut, shortName, taskId, taskCommentDTO);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        CrmTaskComment requestEnitity = crmTaskCommentMapper.DTO2DB(taskCommentDTO, corChannel);
        CrmTaskComment entity = crmOpportunityService.saveOrUpdateTaskCommentAssociatedWithTask(requestEnitity, taskId, organizationShortcut, channelShortcut);
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> getOpportunityTaskCommentUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                               @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                               @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmTaskComment{}, for CrmTask : {}, CrmOpportunity: {} and Network: {}", id, taskId, shortName, organizationShortcut);
        CrmTaskComment entity = crmOpportunityService.getTaskCommentAssociatedWithTask(organizationShortcut, channelShortcut, taskId, id);
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteOpportunityTaskCommentUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                        @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmOpportunity CrmTask : {}, for CrmOpportunity: {} and Network: {}", id, shortName, organizationShortcut);
        crmOpportunityService.deleteOpportunityTaskComment(taskId, id, organizationShortcut, channelShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmTask", id.toString())).build();

    }
}
