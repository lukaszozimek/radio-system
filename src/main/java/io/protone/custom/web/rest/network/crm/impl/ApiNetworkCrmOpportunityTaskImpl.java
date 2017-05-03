package io.protone.custom.web.rest.network.crm.impl;

import io.protone.domain.CorNetwork;
import io.protone.service.crm.CrmOpportunityService;
import io.protone.domain.CrmTask;
import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.web.rest.api.library.impl.LibraryMarkerConfigurationResourceImpl;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmOpportunityTask;
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
public class ApiNetworkCrmOpportunityTaskImpl implements ApiNetworkCrmOpportunityTask {
    private final Logger log = LoggerFactory.getLogger(LibraryMarkerConfigurationResourceImpl.class);

    @Inject
    private CrmOpportunityService crmOpportunityService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmTaskMapper crmTaskMapper;

    @Override
    public ResponseEntity<List<CrmTaskPT>> getAllOpportunityActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                               @ApiParam(value = "pagable", required = true) Pageable pageable) {
        log.debug("REST request to get all CrmOpportunity CrmTask, for CrmOpportunity: {} and Network: {}", shortName, networkShortcut);
        List<CrmTask> crmTasks = crmOpportunityService.getTasksAssociatedWithOpportunity(shortName, networkShortcut, pageable);
        List<CrmTaskPT> crmTaskPTS = crmTaskMapper.DBs2DTOs(crmTasks);
        return Optional.ofNullable(crmTaskPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmTaskPT> updateOpportunityActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmTaskPT", required = true) @RequestBody CrmTaskPT crmTaskPT) throws URISyntaxException {
        log.debug("REST request to update CrmOpportunity CrmTask : {}, for CrmOpportunity: {} and Network: {}", crmTaskPT, shortName, networkShortcut);
        if (crmTaskPT.getId() == null) {
            return createOpportunityActivityUsingPOST(networkShortcut, shortName, crmTaskPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CrmTask requestEnitity = crmTaskMapper.DTO2DB(crmTaskPT, corNetwork);
        CrmTask entity = crmOpportunityService.saveOrUpdateTaskAssociatiedWithOpportunity(requestEnitity, shortName, networkShortcut);
        CrmTaskPT response = crmTaskMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);

    }

    @Override
    public ResponseEntity<CrmTaskPT> createOpportunityActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmTaskPT", required = true) @RequestBody CrmTaskPT crmTaskPT) throws URISyntaxException {
        log.debug("REST request to save CrmOpportunity CrmTask : {}, for CrmOpportunity: {} and Network: {}", crmTaskPT, shortName, networkShortcut);
        if (crmTaskPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTask", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmTask requestEnitity = crmTaskMapper.DTO2DB(crmTaskPT, corNetwork);
        CrmTask entity = crmOpportunityService.saveOrUpdateTaskAssociatiedWithOpportunity(requestEnitity, shortName, networkShortcut);
        CrmTaskPT response = crmTaskMapper.DB2DTO(entity);
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
    public ResponseEntity<CrmTaskPT> getOpportunityActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmOpportunity CrmTask : {}, for CrmOpportunity: {} and Network: {}", id, shortName, networkShortcut);
        CrmTask entity = crmOpportunityService.getTaskAssociatedWithOpportunity(id, networkShortcut);
        CrmTaskPT response = crmTaskMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
