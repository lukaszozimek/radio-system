package io.protone.custom.web.rest.network.crm.impl;

import io.protone.service.crm.CrmLeadService;
import io.protone.domain.CrmLead;
import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.web.rest.api.library.impl.LibraryMarkerConfigurationResourceImpl;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmLead;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.mapper.CrmLeadMapper;
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
public class ApiNetworkCrmLeadImpl implements ApiNetworkCrmLead {
    private final Logger log = LoggerFactory.getLogger(LibraryMarkerConfigurationResourceImpl.class);

    @Inject
    private CrmLeadService crmLeadService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmLeadMapper crmLeadMapper;

    @Override
    public ResponseEntity<CrmLeadPT> updateLeadUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmLeadPT", required = true) @RequestBody CrmLeadPT crmLeadPT) throws URISyntaxException {
        log.debug("REST request to update CrmLead : {}, for Network: {}", crmLeadPT);
        if (crmLeadPT.getId() == null) {
            return createLeadUsingPOST(networkShortcut, crmLeadPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmLead crmLead = crmLeadMapper.DTO2DB(crmLeadPT, corNetwork);
        CrmLead entity = crmLeadService.saveLead(crmLead);
        CrmLeadPT response = crmLeadMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmLeadPT> createLeadUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmLeadPT", required = true) @RequestBody CrmLeadPT crmLeadPT) throws URISyntaxException {
        log.debug("REST request to save CrmLead : {}, for Network: {}", crmLeadPT, networkShortcut);
        if (crmLeadPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmLead", "idexists", "A new CrmLead cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmLead crmLead = crmLeadMapper.DTO2DB(crmLeadPT, corNetwork);
        CrmLead entity = crmLeadService.saveLead(crmLead);
        CrmLeadPT response = crmLeadMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/lead/" + response.getShortname()))
            .body(response);
    }

    @Override
    public ResponseEntity<List<CrmLeadPT>> getAllLeadsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmLead, for Network: {}", networkShortcut);
        List<CrmLead> entity = crmLeadService.getAllLeads(networkShortcut, pagable);
        List<CrmLeadPT> response = crmLeadMapper.DBs2DTOs(entity);

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmLeadPT> getLeadUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get CrmLead : {}, for Network: {}", shortName, networkShortcut);
        CrmLead entity = crmLeadService.getLead(networkShortcut, shortName);
        CrmLeadPT response = crmLeadMapper.DB2DTO(entity);

        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteLeadUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete CrmLead : {}, for Network: {}", shortName, networkShortcut);
        crmLeadService.deleteLead(shortName, shortName);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmLead", shortName.toString())).build();
    }
}
