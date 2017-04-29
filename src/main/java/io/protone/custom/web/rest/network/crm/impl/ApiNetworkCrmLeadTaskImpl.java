package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CrmLeadService;
import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.web.rest.network.configuration.library.impl.ApiConfigurationLibraryMarkerImpl;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmLeadTask;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkCrmLeadTaskImpl implements ApiNetworkCrmLeadTask {
    private final Logger log = LoggerFactory.getLogger(ApiConfigurationLibraryMarkerImpl.class);

    @Inject
    private CrmLeadService crmLeadService;

    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<List<CrmTaskPT>> getAllLeadActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                        @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all  CrmLead CrmTask, for CrmLead: {} and Network: {}");
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmLeadService.getTasksAssociatedWithLead(shortName, corNetwork));
    }

    @Override
    public ResponseEntity<CrmTaskPT> updateLeadActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmTaskPT", required = true) @RequestBody CrmTaskPT crmTaskPT) {
        log.debug("REST request to update CrmLead CrmTask : {}, for CrmLead: {} and Network: {}", crmTaskPT, shortName, networkShortcut);
        if (crmTaskPT.getId() == null) {
            return createLeadActivityUsingPOST(networkShortcut, shortName, crmTaskPT);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmLeadService.createTasksAssociatedWithLead(shortName, crmTaskPT, corNetwork));
    }

    @Override
    public ResponseEntity<CrmTaskPT> createLeadActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "crmTaskPT", required = true) @RequestBody CrmTaskPT crmTaskPT) {
        log.debug("REST request to save CrmLead CrmTask : {}, for CrmLead: {} and Network: {}", crmTaskPT, shortName, networkShortcut);
        if (crmTaskPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmTask", "idexists", "A new CrmTask cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmLeadService.createTasksAssociatedWithLead(shortName, crmTaskPT, corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteLeadActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CrmLead CrmTask : {}, for CrmLead: {} and Network: {}", networkShortcut, shortName, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        crmLeadService.deleteLeadTask(shortName, id, corNetwork);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CrmTaskPT> getLeadActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CrmLead CrmTask : {}, for CrmLead: {} and Network: {}", networkShortcut, shortName, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmLeadService.getTaskAssociatedWithLead(shortName, id, corNetwork));
    }
}
