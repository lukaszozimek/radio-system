package io.protone.web.api.crm.impl;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmOpportunity;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.crm.CrmOpportunityService;
import io.protone.web.api.crm.CrmOpportunityResource;
import io.protone.web.rest.dto.crm.CrmOpportunityDTO;
import io.protone.web.rest.dto.crm.thin.CrmOpportunityThinDTO;
import io.protone.web.rest.mapper.CrmOpportunityMapper;
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
public class CrmOpportunityResourceImpl implements CrmOpportunityResource {
    private final Logger log = LoggerFactory.getLogger(CrmOpportunityResourceImpl.class);

    @Inject
    private CrmOpportunityService crmOpportunityService;

    @Inject
    private CorNetworkService corNetworkService;
    @Inject
    private CrmOpportunityMapper crmOpportunityMapper;

    @Override
    public ResponseEntity<CrmOpportunityDTO> updateOpportunityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmOpportunityDTO", required = true) @Valid @RequestBody CrmOpportunityDTO crmOpportunityDTO) throws URISyntaxException {
        log.debug("REST request to update CorNetwork : {}", crmOpportunityDTO);
        if (crmOpportunityDTO.getId() == null) {
            return createOpportunityUsingPOST(networkShortcut, crmOpportunityDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CrmOpportunity crmOpportunity = crmOpportunityMapper.DTO2DB(crmOpportunityDTO, corNetwork);
        CrmOpportunity entity = crmOpportunityService.saveOpportunity(crmOpportunity);
        CrmOpportunityDTO response = crmOpportunityMapper.DB2DTO(entity);

        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmOpportunityDTO> createOpportunityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmOpportunityDTO", required = true) @Valid @RequestBody CrmOpportunityDTO crmOpportunityDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CrmOpportunity : {}, for Network: {}", crmOpportunityDTO, networkShortcut);
        if (crmOpportunityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmOpportunity", "idexists", "A new CrmOpportunity cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CrmOpportunity crmOpportunity = crmOpportunityMapper.DTO2DB(crmOpportunityDTO, corNetwork);
        CrmOpportunity entity = crmOpportunityService.saveOpportunity(crmOpportunity);
        CrmOpportunityDTO response = crmOpportunityMapper.DB2DTO(entity);

        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/opportunity/" + response.getName()))
            .body(response);
    }

    @Override
    public ResponseEntity<List<CrmOpportunityThinDTO>> getAllOpportunitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmOpportunity, for Network: {}", networkShortcut);
        List<CrmOpportunity> entity = crmOpportunityService.getAllOpportunity(networkShortcut, pagable);
        List<CrmOpportunityThinDTO> response = crmOpportunityMapper.DBs2ThinDTOs(entity);

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmOpportunityDTO> getOpportunityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get CrmOpportunity : {}, for Network: {}", shortName, networkShortcut);
        CrmOpportunity entity = crmOpportunityService.getOpportunity(shortName, networkShortcut);
        CrmOpportunityDTO response = crmOpportunityMapper.DB2DTO(entity);

        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteOpportunityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete CrmOpportunity : {}, for Network: {}", shortName, networkShortcut);
        crmOpportunityService.deleteOpportunity(shortName, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmOpportunity", shortName.toString())).build();
    }
}
