package io.protone.custom.web.rest.network.crm.impl;

import io.protone.service.crm.CrmOpportunityService;
import io.protone.domain.CrmOpportunity;
import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.CrmOpportunityPT;
import io.protone.custom.web.rest.network.configuration.library.impl.ApiConfigurationLibraryMarkerImpl;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmOpportunity;
import io.protone.domain.CorNetwork;
import io.protone.service.mapper.CrmOpportunityMapper;
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
public class ApiNetworkCrmOpportunityImpl implements ApiNetworkCrmOpportunity {
    private final Logger log = LoggerFactory.getLogger(ApiConfigurationLibraryMarkerImpl.class);

    @Inject
    private CrmOpportunityService opportunityService;

    @Inject
    private CorNetworkService networkService;
    @Inject
    private CrmOpportunityMapper crmOpportunityMapper;

    @Override
    public ResponseEntity<CrmOpportunityPT> updateOpportunityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmOpportunityPT", required = true) @RequestBody CrmOpportunityPT crmOpportunityPT) {
        log.debug("REST request to update CorNetwork : {}", crmOpportunityPT);
        if (crmOpportunityPT.getId() == null) {
            return createOpportunityUsingPOST(networkShortcut, crmOpportunityPT);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);

        CrmOpportunity crmOpportunity = crmOpportunityMapper.DTO2DB(crmOpportunityPT, corNetwork);
        CrmOpportunity entity = opportunityService.saveOpportunity(crmOpportunity);
        CrmOpportunityPT response = crmOpportunityMapper.DB2DTO(entity);

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmOpportunityPT> createOpportunityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmOpportunityPT", required = true) @RequestBody CrmOpportunityPT crmOpportunityPT) {
        log.debug("REST request to save CrmOpportunity : {}, for Network: {}", crmOpportunityPT, networkShortcut);
        if (crmOpportunityPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmOpportunity", "idexists", "A new CrmOpportunity cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);

        CrmOpportunity crmOpportunity = crmOpportunityMapper.DTO2DB(crmOpportunityPT, corNetwork);
        CrmOpportunity entity = opportunityService.saveOpportunity(crmOpportunity);
        CrmOpportunityPT response = crmOpportunityMapper.DB2DTO(entity);

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<List<CrmOpportunityPT>> getAllOpportunitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmOpportunity, for Network: {}", networkShortcut);
        List<CrmOpportunity> entity = opportunityService.getAllOpportunity(networkShortcut, pagable);
        List<CrmOpportunityPT> response = crmOpportunityMapper.DBs2DTOs(entity);

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmOpportunityPT> getOpportunityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get CrmOpportunity : {}, for Network: {}", shortName, networkShortcut);
        CrmOpportunity entity = opportunityService.getOpportunity(shortName, networkShortcut);
        CrmOpportunityPT response = crmOpportunityMapper.DB2DTO(entity);

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Void> deleteOpportunityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete CrmOpportunity : {}, for Network: {}", shortName, networkShortcut);
        log.debug("REST request to delete CrmLead : {}, for Network: {}", shortName, networkShortcut);
        opportunityService.deleteOpportunity(shortName, shortName);
        return ResponseEntity.ok().build();
    }
}
