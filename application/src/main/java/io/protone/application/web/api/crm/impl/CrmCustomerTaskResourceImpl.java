package io.protone.application.web.api.crm.impl;


import io.protone.application.web.api.crm.CrmCustomerTaskResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.crm.api.dto.CrmTaskDTO;
import io.protone.crm.api.dto.thin.CrmTaskThinDTO;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.mapper.CrmTaskMapper;
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
public class CrmCustomerTaskResourceImpl implements CrmCustomerTaskResource {
    private final Logger log = LoggerFactory.getLogger(CrmCustomerTaskResourceImpl.class);

    @Inject
    private CrmCustomerService crmCustomerService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmTaskMapper crmTaskMapper;


    @Override
    public ResponseEntity<List<CrmTaskThinDTO>> getAllCustomerActivitiesUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                 @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                 @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmAccount CrmTask, for CrmAccount: {} and Network: {}", shortName, organizationShortcut);
        Slice<CrmTask> entities = crmCustomerService.getTasksAssociatedWithAccount(shortName, organizationShortcut, pagable);
        List<CrmTaskThinDTO> response = crmTaskMapper.DBs2ThinDTOs(entities.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entities),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entities), HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<CrmTaskDTO> updateCustomerActivityUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException {
        log.debug("REST request to update  CrmAccount CrmTask : {}, for CrmAccount: {} and Network: {}", crmTaskDTO, shortName, organizationShortcut);
        if (crmTaskDTO.getId() == null) {
            return createCustomerActivityUsingPOST(organizationShortcut, shortName, crmTaskDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        CrmTask requestEnitity = crmTaskMapper.DTO2DB(crmTaskDTO, corNetwork);
        CrmTask entity = crmCustomerService.saveOrUpdateTaskAssociatiedWithAccount(requestEnitity, shortName, organizationShortcut);
        CrmTaskDTO response = crmTaskMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmTaskDTO> createCustomerActivityUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CrmAccount CrmTask : {}, for CrmAccount: {} and Network: {}", crmTaskDTO, shortName, organizationShortcut);
        if (crmTaskDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTask", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        CrmTask requestEnitity = crmTaskMapper.DTO2DB(crmTaskDTO, corNetwork);
        CrmTask entity = crmCustomerService.saveOrUpdateTaskAssociatiedWithAccount(requestEnitity, shortName, organizationShortcut);
        CrmTaskDTO response = crmTaskMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/crm/customer/" + shortName + "/task/" + entity.getId()))
                .body(response);

    }

    @Override
    public ResponseEntity<Void> deleteCustomerActivityUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmAccount CrmTask : {}, for CrmAccount: {} and Network: {}", id, shortName, organizationShortcut);
        crmCustomerService.deleteCustomerTask(shortName, id, organizationShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmTask", shortName.toString())).build();
    }

    @Override
    public ResponseEntity<CrmTaskDTO> getCustomerActivityUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmAccount CrmTask : {}, for CrmAccount: {} and Network: {}", id, shortName, organizationShortcut);

        CrmTask entity = crmCustomerService.getTaskAssociatedWithAccount(id, organizationShortcut);
        CrmTaskDTO response = crmTaskMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
