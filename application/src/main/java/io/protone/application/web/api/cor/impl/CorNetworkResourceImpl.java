package io.protone.application.web.api.cor.impl;


import io.protone.application.web.api.cor.CorNetworkResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.core.api.dto.CorNetworkDTO;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorOrganization;
import io.protone.core.mapper.CorNetworkMapper;
import io.protone.core.service.CorNetworkService;
import io.protone.core.service.CorOrganizationService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
public class CorNetworkResourceImpl implements CorNetworkResource {
    private final Logger log = LoggerFactory.getLogger(CorNetworkResourceImpl.class);

    @Inject
    private CorNetworkMapper corNetworkMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorOrganizationService corOrganizationService;

    @Override
    public ResponseEntity<List<CorNetworkDTO>> getAllNetworksUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut) {
        log.debug("REST request to get all CorNetworks");
        Slice<CorNetwork> networks = corNetworkService.findAllNetworks(organizationShortcut);
        return ResponseEntity.ok()
                .body(corNetworkMapper.DBs2DTOs(networks.getContent()));

    }

    @Override
    public ResponseEntity<CorNetworkDTO> createNetworkUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                @ApiParam(value = "organization", required = true) @Valid @RequestPart("organization") CorNetworkDTO network,
                                                                @ApiParam(value = "logo", required = true) @RequestPart("logo") MultipartFile logo) throws URISyntaxException {
        log.debug("REST request to saveCorContact CorNetwork : {}", network);
        if (network.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("corNetwork", "idexists", "A new corNetwork cannot already have an ID")).body(null);
        }
        CorOrganization corOrganization = corOrganizationService.findOrganization(organizationShortcut);
        CorNetwork corNetwork = corNetworkMapper.DTO2DB(network, corOrganization);
        corNetwork = corNetworkService.save(corNetwork);
        CorNetworkDTO result = corNetworkMapper.DB2DTO(corNetwork);
        return ResponseEntity.created(new URI("/api/v1/organization/" + result.getShortcut()))
                .headers(HeaderUtil.createEntityCreationAlert("corNetwork", result.getId().toString()))
                .body(result);
    }

    @Override
    public ResponseEntity<CorNetworkDTO> updateNetworkUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "organization", required = true) @Valid @RequestBody CorNetworkDTO network) throws URISyntaxException {
        log.debug("REST request to update CorNetwork : {}", network);
        if (network.getId() == null) {
            return createNetworkUsingPOST(organizationShortcut, network, null);
        }
        CorOrganization corOrganization = corOrganizationService.findOrganization(organizationShortcut);
        CorNetwork corNetwork = corNetworkMapper.DTO2DB(network, corOrganization);
        corNetwork = corNetworkService.save(corNetwork);
        CorNetworkDTO result = corNetworkMapper.DB2DTO(corNetwork);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("corNetwork", network.getId().toString()))
                .body(result);
    }

    @Override
    public ResponseEntity<CorNetworkDTO> updateNetworkWithLogoUsingPOST(
            @ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
            @ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
            @ApiParam(value = "organization", required = true) @Valid @RequestPart("organization") CorNetworkDTO network,
            @ApiParam(value = "logo", required = true) @RequestPart("logo") MultipartFile logo) throws URISyntaxException {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteNetworkUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                         @ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to delete CorNetwork : {}", networkShortcut);
        corNetworkService.deleteNetwork(networkShortcut, organizationShortcut);
        return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<CorNetworkDTO> getNetworkUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get CorNetwork : {}", networkShortcut);
        CorNetwork cORNetwork = corNetworkService.findNetwork(networkShortcut, organizationShortcut);
        CorNetworkDTO cORNetworkDTO = corNetworkMapper.DB2DTO(cORNetwork);
        return Optional.ofNullable(cORNetworkDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
