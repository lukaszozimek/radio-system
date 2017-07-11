package io.protone.application.web.api.crm.impl;


import io.protone.application.web.api.crm.CrmLeadTaskCommentResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.crm.api.dto.CrmTaskCommentDTO;
import io.protone.crm.domain.CrmTaskComment;
import io.protone.crm.mapper.CrmTaskCommentMapper;
import io.protone.crm.service.CrmLeadService;
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
public class CrmLeadTaskCommentResourceImpl implements CrmLeadTaskCommentResource {
    private final Logger log = LoggerFactory.getLogger(CrmLeadTaskCommentResourceImpl.class);

    @Inject
    private CrmLeadService crmLeadService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmTaskCommentMapper crmTaskCommentMapper;

    @Override
    public ResponseEntity<List<CrmTaskCommentDTO>> getLeadTaskCommentsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                               @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                               @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all  CrmLead CrmTaskComment, for CrmLead: {} and Network: {}");
        List<CrmTaskComment> crmTasks = crmLeadService.getTaskCommentsAssociatedWithTask(taskId, networkShortcut, pagable);
        List<CrmTaskCommentDTO> response = crmTaskCommentMapper.DBs2DTOs(crmTasks);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> createLeadActivtyCommentUsigPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                              @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                              @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CrmLead CrmTaskComment : {}, for CrmLead: {} and Network: {}", taskCommentDTO, shortName, networkShortcut);
        if (taskCommentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTask", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTaskComment requestEnitity = crmTaskCommentMapper.DTO2DB(taskCommentDTO, corNetwork);
        CrmTaskComment entity = crmLeadService.saveOrUpdateTaskCommentAssociatedWithTask(requestEnitity, taskId, networkShortcut);
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/ap«i/v1/network/" + networkShortcut + "/crm/lead/" + shortName + "/task/" + taskId + "/" + "comment/" + response.getId()))
            .body(response);
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> editLeadActivtyCommentUsigPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                           @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                           @ApiParam(value = "taskCommentDTO", required = true) @Valid @RequestBody CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException {
        log.debug("REST request to update CrmLead CrmTaskComment : {}, for CrmLead: {} and Network: {}", taskCommentDTO, shortName, networkShortcut);
        if (taskCommentDTO.getId() == null) {
            return createLeadActivtyCommentUsigPOST(networkShortcut, shortName, taskId, taskCommentDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTaskComment requestEnitity = crmTaskCommentMapper.DTO2DB(taskCommentDTO, corNetwork);
        CrmTaskComment entity = crmLeadService.saveOrUpdateTaskCommentAssociatedWithTask(requestEnitity, taskId, networkShortcut);
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> getLeadTaskCommentUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                        @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmLead CrmTaskComment : {}, for CrmLead: {} and Network: {}", networkShortcut, shortName, networkShortcut);
        CrmTaskComment entity = crmLeadService.getTaskCommentAssociatedWithTask(networkShortcut, taskId, id);
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteLeadTaskCommentUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                 @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmLead CrmTaskComment : {}, for CrmLead: {} and Network: {}", networkShortcut, shortName, networkShortcut);
        crmLeadService.deleteLeadTaskComment(taskId, id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmTaskComment", id.toString())).build();

    }
}
