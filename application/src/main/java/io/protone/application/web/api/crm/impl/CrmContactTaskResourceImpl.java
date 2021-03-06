package io.protone.application.web.api.crm.impl;


import io.protone.application.web.api.crm.CrmContactTaskResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.crm.api.dto.CrmTaskDTO;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.mapper.CrmTaskMapper;
import io.protone.crm.service.CrmContactService;
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
public class CrmContactTaskResourceImpl implements CrmContactTaskResource {
    private final Logger log = LoggerFactory.getLogger(CrmContactTaskResourceImpl.class);

    @Inject
    private CrmContactService crmContactService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmTaskMapper crmTaskMapper;


    @Override
    public ResponseEntity<List<CrmTaskDTO>> getAllContactActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                            @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmContact CrmTask,for CrmContact: {} and Network: {}", shortName, networkShortcut);
        Slice<CrmTask> reposesEntity = crmContactService.getTasksAssociatedWithContact(shortName, networkShortcut, pagable);
        List<CrmTaskDTO> response = crmTaskMapper.DBs2DTOs(reposesEntity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(reposesEntity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(reposesEntity), HttpStatus.NOT_FOUND));
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


}
