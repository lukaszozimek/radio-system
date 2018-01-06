package io.protone.application.web.api.crm.impl;


import io.protone.application.web.api.crm.CrmOpportunityTaskResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.crm.api.dto.CrmTaskDTO;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.mapper.CrmTaskMapper;
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
public class CrmOpportunityTaskResourceImpl implements CrmOpportunityTaskResource {
    private final Logger log = LoggerFactory.getLogger(CrmOpportunityTaskResourceImpl.class);

    @Inject
    private CrmOpportunityService crmOpportunityService;

    @Inject
    private CorChannelService corChannelService;

    @Inject
    private CrmTaskMapper crmTaskMapper;


    @Override
    public ResponseEntity<List<CrmTaskDTO>> getAllOpportunityActivitiesUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                                @ApiParam(value = "pagable", required = true) Pageable pageable) {
        log.debug("REST request to get all CrmOpportunity CrmTask, for CrmOpportunity: {} and Network: {}", shortName, organizationShortcut);
        Slice<CrmTask> crmTasks = crmOpportunityService.getTasksAssociatedWithOpportunity(shortName, organizationShortcut, channelShortcut, pageable);
        List<CrmTaskDTO> crmTaskDTOS = crmTaskMapper.DBs2DTOs(crmTasks.getContent());
        return Optional.ofNullable(crmTaskDTOS)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(crmTasks),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(crmTasks), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmTaskDTO> updateOpportunityActivityUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException {
        log.debug("REST request to update CrmOpportunity CrmTask : {}, for CrmOpportunity: {} and Network: {}", crmTaskDTO, shortName, organizationShortcut);
        if (crmTaskDTO.getId() == null) {
            return createOpportunityActivityUsingPOST(organizationShortcut, channelShortcut, shortName, crmTaskDTO);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        CrmTask requestEnitity = crmTaskMapper.DTO2DB(crmTaskDTO, corChannel);
        CrmTask entity = crmOpportunityService.saveOrUpdateTaskAssociatiedWithOpportunity(requestEnitity, shortName, organizationShortcut, channelShortcut);
        CrmTaskDTO response = crmTaskMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);

    }

    @Override
    public ResponseEntity<CrmTaskDTO> createOpportunityActivityUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmTaskDTO", required = true) @Valid @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CrmOpportunity CrmTask : {}, for CrmOpportunity: {} and Network: {}", crmTaskDTO, shortName, organizationShortcut);
        if (crmTaskDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTask", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        CrmTask requestEnitity = crmTaskMapper.DTO2DB(crmTaskDTO, corChannel);
        CrmTask entity = crmOpportunityService.saveOrUpdateTaskAssociatiedWithOpportunity(requestEnitity, shortName, organizationShortcut, channelShortcut);
        CrmTaskDTO response = crmTaskMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/crm/opportunity/" + shortName + "/task/" + response.getId()))
                .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteOpportunityActivityUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                     @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmOpportunity CrmTask : {}, for CrmOpportunity: {} and Network: {}", id, shortName, organizationShortcut);
        crmOpportunityService.deleteOpportunityTask(shortName, id, organizationShortcut, channelShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmTask", id.toString())).build();
    }

    @Override
    public ResponseEntity<CrmTaskDTO> getOpportunityActivityUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                     @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmOpportunity CrmTask : {}, for CrmOpportunity: {} and Network: {}", id, shortName, organizationShortcut);
        CrmTask entity = crmOpportunityService.getTaskAssociatedWithOpportunity(id, organizationShortcut, channelShortcut);
        CrmTaskDTO response = crmTaskMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
