package io.protone.application.web.api.crm.impl;


import io.protone.application.web.api.crm.CrmCustomerTaskCommentResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.crm.api.dto.CrmTaskCommentDTO;
import io.protone.crm.domain.CrmTaskComment;
import io.protone.crm.mapper.CrmTaskCommentMapper;
import io.protone.crm.service.CrmCustomerService;
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
public class CrmCustomerTaskCommentResourceImpl implements CrmCustomerTaskCommentResource {
    private final Logger log = LoggerFactory.getLogger(CrmCustomerTaskCommentResourceImpl.class);

    @Inject
    private CrmCustomerService crmCustomerService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmTaskCommentMapper crmTaskCommentMapper;

    @Override
    public ResponseEntity<List<CrmTaskCommentDTO>> getCustomerTaskCommentsUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                   @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmAccount CrmTask, for CrmAccount: {} and Network: {}", shortName, organizationShortcut);
        Slice<CrmTaskComment> entities = crmCustomerService.getTaskCommentsAssociatedWithTask(taskId, organizationShortcut, pagable);
        List<CrmTaskCommentDTO> response = crmTaskCommentMapper.DBs2DTOs(entities.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entities),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entities), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> createCustomerActivtyCommentUsigPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                  @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                                  @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CrmAccount CrmTask : {}, for CrmAccount: {} and Network: {}", taskCommentDTO, shortName, organizationShortcut);
        if (taskCommentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTask", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        CrmTaskComment requestEnitity = crmTaskCommentMapper.DTO2DB(taskCommentDTO, corNetwork);
        CrmTaskComment entity = crmCustomerService.saveOrUpdateTaskCommentAssociatedWithTask(requestEnitity, taskId, organizationShortcut);
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/crm/customer/" + shortName + "/task/" + taskId + "/" + "comment/" + response.getId()))
                .body(response);
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> editCustomerActivtyCommentUsigPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                               @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                               @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                               @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException {
        log.debug("REST request to update  CrmAccount CrmTask : {}, for CrmAccount: {} and Network: {}", taskCommentDTO, shortName, organizationShortcut);
        if (taskCommentDTO.getId() == null) {
            return createCustomerActivtyCommentUsigPOST(organizationShortcut, shortName, taskId, taskCommentDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        CrmTaskComment requestEnitity = crmTaskCommentMapper.DTO2DB(taskCommentDTO, corNetwork);
        CrmTaskComment entity = crmCustomerService.saveOrUpdateTaskCommentAssociatedWithTask(requestEnitity, taskId, organizationShortcut);
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> getCustomerTaskCommentUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                            @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                            @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                            @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmAccount CrmTask : {}, for CrmAccount: {} and Network: {}", id, shortName, organizationShortcut);

        CrmTaskComment entity = crmCustomerService.getTaskCommentAssociatedWithTask(organizationShortcut, taskId, id);
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteCustomerTaskCommentUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                     @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                     @ApiParam(value = "taskId", required = true) @PathVariable("taskId") Long taskId,
                                                                     @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmAccount CrmTask : {}, for CrmAccount: {} and Network: {}", id, shortName, organizationShortcut);
        crmCustomerService.deleteCustomerTaskComment(taskId, id, organizationShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmTask", shortName.toString())).build();

    }
}
