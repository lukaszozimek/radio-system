package io.protone.application.web.api.traffic.impl;


import io.protone.application.web.api.traffic.TraCampaignResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.traffic.api.dto.TraCampaignDTO;
import io.protone.traffic.domain.TraCampaign;
import io.protone.traffic.mapper.TraCampaignMapper;
import io.protone.traffic.service.TraCampaignService;
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
public class TraCampaignResourceImpl implements TraCampaignResource {
    private final Logger log = LoggerFactory.getLogger(TraCampaignResourceImpl.class);

    @Inject
    private TraCampaignService traCampaignService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private TraCampaignMapper traCampaignMapper;

    @Override
    public ResponseEntity<List<TraCampaignDTO>> getAllCampaignsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraCampaign, for Network: {}", networkShortcut);
        List<TraCampaign> entity = traCampaignService.getAllCampaign(networkShortcut, pagable);
        List<TraCampaignDTO> response = traCampaignMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraCampaignDTO> updateCampaignUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traCampaignDTO", required = true) @Valid @RequestBody TraCampaignDTO traCampaignDTO) throws URISyntaxException {
        log.debug("REST request to update TraCampaign : {}, for Network: {}", traCampaignDTO, networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        if (traCampaignDTO.getId() == null) {
            return createCampaignUsingPOST(networkShortcut, traCampaignDTO);
        }

        TraCampaign crmAccount = traCampaignMapper.DTO2DB(traCampaignDTO, corNetwork);
        TraCampaign entity = traCampaignService.saveCampaign(crmAccount);
        TraCampaignDTO response = traCampaignMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraCampaignDTO> createCampaignUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traCampaignDTO", required = true) @Valid @RequestBody TraCampaignDTO traCampaignDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact TraCampaign : {}, for Network: {}", traCampaignDTO, networkShortcut);
        if (traCampaignDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraCampaign", "idexists", "A new TraCampaign cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraCampaign crmAccount = traCampaignMapper.DTO2DB(traCampaignDTO, corNetwork);
        TraCampaign entity = traCampaignService.saveCampaign(crmAccount);
        TraCampaignDTO response = traCampaignMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/traffic/campaign/" + response.getName()))
            .body(response);

    }

    @Override
    public ResponseEntity<Void> deleteCampaignUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete TraCampaign : {}, for Network: {}", shortName, networkShortcut);
        traCampaignService.deleteCampaign(shortName, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traOrder", shortName.toString())).build();
    }

    @Override
    public ResponseEntity<TraCampaignDTO> getCampaignUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get TraCampaign : {}, for Network: {}", shortName, networkShortcut);
        TraCampaign entity = traCampaignService.getCampaign(shortName, networkShortcut);
        TraCampaignDTO response = traCampaignMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<TraCampaignDTO>> getAllCustomerCampaignsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                                @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraCampaign, for TraCustomer: {} and Network: {}", customerShortcut, networkShortcut);
        List<TraCampaign> entity = traCampaignService.getCustomerCampaing(customerShortcut, networkShortcut, pagable);
        List<TraCampaignDTO> response = traCampaignMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
