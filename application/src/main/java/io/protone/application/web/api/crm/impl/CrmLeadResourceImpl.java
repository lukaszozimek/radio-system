package io.protone.application.web.api.crm.impl;


import io.protone.application.web.api.crm.CrmLeadResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.crm.api.dto.CrmLeadDTO;
import io.protone.crm.api.dto.thin.CrmLeadThinDTO;
import io.protone.crm.domain.CrmLead;
import io.protone.crm.mapper.CrmLeadMapper;
import io.protone.crm.service.CrmLeadService;
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
public class CrmLeadResourceImpl implements CrmLeadResource {
    private final Logger log = LoggerFactory.getLogger(CrmLeadResourceImpl.class);

    @Inject
    private CrmLeadService crmLeadService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmLeadMapper crmLeadMapper;

    @Override
    public ResponseEntity<CrmLeadDTO> updateLeadUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmLeadDTO", required = true) @Valid @RequestBody CrmLeadDTO crmLeadDTO) throws URISyntaxException {
        log.debug("REST request to update CrmLead : {}, for Network: {}", crmLeadDTO);
        if (crmLeadDTO.getId() == null) {
            return createLeadUsingPOST(networkShortcut, crmLeadDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmLead crmLead = crmLeadMapper.DTO2DB(crmLeadDTO, corNetwork);
        CrmLead entity = crmLeadService.saveLead(crmLead);
        CrmLeadDTO response = crmLeadMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmLeadDTO> createLeadUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmLeadDTO", required = true) @Valid @RequestBody CrmLeadDTO crmLeadDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CrmLead : {}, for Network: {}", crmLeadDTO, networkShortcut);
        if (crmLeadDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmLead", "idexists", "A new CrmLead cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmLead crmLead = crmLeadMapper.DTO2DB(crmLeadDTO, corNetwork);
        CrmLead entity = crmLeadService.saveLead(crmLead);
        CrmLeadDTO response = crmLeadMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/lead/" + response.getShortname()))
                .body(response);
    }

    @Override
    public ResponseEntity<List<CrmLeadThinDTO>> getAllLeadsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmLead, for Network: {}", networkShortcut);
        Slice<CrmLead> entity = crmLeadService.getAllLeads(networkShortcut, pagable);
        List<CrmLeadThinDTO> response = crmLeadMapper.DBs2ThinDTOs(entity.getContent());

        return ResponseEntity.ok().headers(PaginationUtil.generateSliceHttpHeaders(entity)).body(response);
    }

    @Override
    public ResponseEntity<CrmLeadDTO> getLeadUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get CrmLead : {}, for Network: {}", shortName, networkShortcut);
        CrmLead entity = crmLeadService.getLead(shortName, networkShortcut);
        CrmLeadDTO response = crmLeadMapper.DB2DTO(entity);

        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteLeadUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete CrmLead : {}, for Network: {}", shortName, networkShortcut);
        crmLeadService.deleteLead(shortName, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmLead", shortName.toString())).build();
    }
}
