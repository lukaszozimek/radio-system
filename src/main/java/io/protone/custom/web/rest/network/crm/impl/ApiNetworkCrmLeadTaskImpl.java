package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.dto.CrmTaskDTO;
import io.protone.domain.CorNetwork;
import io.protone.service.crm.CrmLeadService;
import io.protone.domain.CrmTask;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.rest.api.library.impl.LibraryMarkerConfigurationResourceImpl;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmLeadTask;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiNetworkCrmLeadTaskImpl implements ApiNetworkCrmLeadTask {
    private final Logger log = LoggerFactory.getLogger(LibraryMarkerConfigurationResourceImpl.class);

    @Inject
    private CrmLeadService crmLeadService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmTaskMapper crmTaskMapper;

    @Override
    public ResponseEntity<List<CrmTaskDTO>> getAllLeadActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                         @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all  CrmLead CrmTask, for CrmLead: {} and Network: {}");
        List<CrmTask> crmTasks = crmLeadService.getTasksAssociatedWithLead(shortName, networkShortcut, pagable);
        List<CrmTaskDTO> response = crmTaskMapper.DBs2DTOs(crmTasks);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmTaskDTO> updateLeadActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmTaskDTO", required = true) @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException {
        log.debug("REST request to update CrmLead CrmTask : {}, for CrmLead: {} and Network: {}", crmTaskDTO, shortName, networkShortcut);
        if (crmTaskDTO.getId() == null) {
            return createLeadActivityUsingPOST(networkShortcut, shortName, crmTaskDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTask requestEnitity = crmTaskMapper.DTO2DB(crmTaskDTO, corNetwork);
        CrmTask entity = crmLeadService.saveOrUpdateTaskAssociatiedWithLead(requestEnitity, shortName, networkShortcut);
        CrmTaskDTO response = crmTaskMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmTaskDTO> createLeadActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmTaskDTO", required = true) @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException {
        log.debug("REST request to save CrmLead CrmTask : {}, for CrmLead: {} and Network: {}", crmTaskDTO, shortName, networkShortcut);
        if (crmTaskDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTask", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTask requestEnitity = crmTaskMapper.DTO2DB(crmTaskDTO, corNetwork);
        CrmTask entity = crmLeadService.saveOrUpdateTaskAssociatiedWithLead(requestEnitity, shortName, networkShortcut);
        CrmTaskDTO response = crmTaskMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/lead/" + shortName + "/task/" + response.getId()))
            .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteLeadActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmLead CrmTask : {}, for CrmLead: {} and Network: {}", networkShortcut, shortName, networkShortcut);

        crmLeadService.deleteLeadTask(shortName, id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmTask", id.toString())).build();
    }

    @Override
    public ResponseEntity<CrmTaskDTO> getLeadActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmLead CrmTask : {}, for CrmLead: {} and Network: {}", networkShortcut, shortName, networkShortcut);
        CrmTask entity = crmLeadService.getTaskAssociatedWithLead(id, networkShortcut);
        CrmTaskDTO response = crmTaskMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
