package io.protone.web.api.crm.impl;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmTask;
import io.protone.domain.CrmTaskComment;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.crm.CrmContactService;
import io.protone.web.api.crm.CrmContactTaskResource;
import io.protone.web.api.library.impl.LibraryMarkerConfigurationResourceImpl;
import io.protone.web.rest.dto.crm.CrmTaskDTO;
import io.protone.web.rest.dto.traffic.CrmTaskCommentDTO;
import io.protone.web.rest.mapper.CrmTaskCommentMapper;
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
public class CrmContactTaskResourceImpl implements CrmContactTaskResource {
    private final Logger log = LoggerFactory.getLogger(LibraryMarkerConfigurationResourceImpl.class);

    @Inject
    private CrmContactService crmContactService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmTaskMapper crmTaskMapper;

    @Inject
    private CrmTaskCommentMapper crmTaskCommentMapper;

    @Override
    public ResponseEntity<List<CrmTaskDTO>> getAllContactActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                            @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmContact CrmTask,for CrmContact: {} and Network: {}", shortName, networkShortcut);
        List<CrmTask> reposesEntity = crmContactService.getTasksAssociatedWithContact(shortName, networkShortcut, pagable);
        List<CrmTaskDTO> response = crmTaskMapper.DBs2DTOs(reposesEntity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmTaskDTO> updateContactActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                    @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException {
        log.debug("REST request to update CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", crmTaskDTO, shortName, networkShortcut);
        if (crmTaskDTO.getId() == null) {
            return createContactActivityUsingPOST(networkShortcut, shortName, crmTaskDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTask crmTask = crmTaskMapper.DTO2DB(crmTaskDTO, corNetwork);
        CrmTask reposesEntity = crmContactService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, shortName, networkShortcut);
        CrmTaskDTO response = crmTaskMapper.DB2DTO(reposesEntity);
        return ResponseEntity.ok().body(response);

    }

    @Override
    public ResponseEntity<CrmTaskDTO> createContactActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                     @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", crmTaskDTO, shortName, networkShortcut);
        if (crmTaskDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTask", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTask crmTask = crmTaskMapper.DTO2DB(crmTaskDTO, corNetwork);
        CrmTask reposesEntity = crmContactService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, shortName, corNetwork.getShortcut());
        CrmTaskDTO response = crmTaskMapper.DB2DTO(reposesEntity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/contact/" + shortName + "/task/" + crmTask.getId()))
            .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteContactActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", id, shortName, networkShortcut);
        crmContactService.deleteContactTask(shortName, id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmTask", id.toString())).build();

    }

    @Override
    public ResponseEntity<CrmTaskDTO> getContactActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", id, shortName, networkShortcut);
        CrmTask reposesEntity = crmContactService.getTaskAssociatedWithContact(id, networkShortcut);
        CrmTaskDTO response = crmTaskMapper.DB2DTO(reposesEntity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<CrmTaskCommentDTO>> getContactTaskCommentsUsingGET(String networkShortcut, String shortName, Long taskId, Long id, Pageable pagable) {
        log.debug("REST request to get all CrmContact CrmTask,for CrmContact: {} and Network: {}", shortName, networkShortcut);
        List<CrmTaskComment> reposesEntity = crmContactService.getTaskCommentsAssociatedWithTaskAndContact(shortName, networkShortcut, pagable);
        List<CrmTaskCommentDTO> response = crmTaskCommentMapper.DBs2DTOs(reposesEntity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> createContactActivtyCommentUsigPOST(String networkShortcut, String shortName, Long taskId, CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", taskCommentDTO, shortName, networkShortcut);
        if (taskCommentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTask", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTaskComment crmTask = crmTaskCommentMapper.DTO2DB(taskCommentDTO, corNetwork);
        CrmTaskComment reposesEntity = crmContactService.saveOrUpdateTaskCommentAssociatedWithTaskAndContact(taskCommentDTO, shortName, corNetwork.getShortcut());
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(reposesEntity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/contact/" + shortName + "/task/" + crmTask.getId()))
            .body(response);
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> editContactActivtyCommentUsigPUT(String networkShortcut, String shortName, Long taskId, CrmTaskCommentDTO taskCommentDTO) throws URISyntaxException {
        log.debug("REST request to update CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", taskCommentDTO, shortName, networkShortcut);
        if (taskCommentDTO.getId() == null) {
            return createContactActivtyCommentUsigPOST(networkShortcut, shortName, taskId, taskCommentDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTaskComment crmTask = crmTaskCommentMapper.DTO2DB(taskCommentDTO, corNetwork);
        CrmTaskComment reposesEntity = crmContactService.saveOrUpdateTaskCommentAssociatedWithTaskAndContact(taskCommentDTO, shortName, networkShortcut);
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(reposesEntity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmTaskCommentDTO> getContactTaskCommentUsingGET(String networkShortcut, String shortName, Long taskId, Long id) {
        log.debug("REST request to get CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", id, shortName, networkShortcut);
        CrmTaskComment reposesEntity = crmContactService.getTaskCommentAssociatedWithTaskAndContact(id, networkShortcut);
        CrmTaskCommentDTO response = crmTaskCommentMapper.DB2DTO(reposesEntity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteContactTaskCommentUsingDELETE(String networkShortcut, String shortName, Long taskId, Long id) {
        log.debug("REST request to delete CrmContact CrmTask : {}, for CrmContact: {} and Network: {}", id, shortName, networkShortcut);
        crmContactService.deleteContactTaskComment(shortName, id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmTask", id.toString())).build();

    }
}
