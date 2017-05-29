package io.protone.web.api.library.impl;

import io.protone.web.api.library.LibraryMarkerConfigurationResource;
import io.protone.web.rest.dto.library.LibMarkerConfigurationDTO;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.rest.mapper.CfgMarkerConfigurationMapper;
import io.protone.domain.CfgMarkerConfiguration;
import io.protone.domain.CorNetwork;
import io.protone.repository.cfg.CfgMarkerConfigurationRepository;
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
public class LibraryMarkerConfigurationResourceImpl implements LibraryMarkerConfigurationResource {
    private final Logger log = LoggerFactory.getLogger(LibraryMarkerConfigurationResourceImpl.class);

    @Inject
    private CfgMarkerConfigurationRepository cfgMarkerConfigurationRepository;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CfgMarkerConfigurationMapper cfgMarkerConfigurationMapper;

    @Override
    public ResponseEntity<LibMarkerConfigurationDTO> updateMarkerConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerConfigurationDTO", required = true) @Valid @RequestBody LibMarkerConfigurationDTO markerConfigurationPT) throws URISyntaxException {
        log.debug("REST request to update ConfMarkerConfiguration : {}", markerConfigurationPT);
        if (markerConfigurationPT.getId() == null) {
            return createMarkerConfigurationUsingPOST(networkShortcut, markerConfigurationPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CfgMarkerConfiguration cFGMarkerConfiguration = cfgMarkerConfigurationMapper.DTO2DB(markerConfigurationPT, corNetwork);
        cFGMarkerConfiguration = cfgMarkerConfigurationRepository.save(cFGMarkerConfiguration);
        LibMarkerConfigurationDTO result = cfgMarkerConfigurationMapper.DB2DTO(cFGMarkerConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cfgMarkerConfiguration", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<LibMarkerConfigurationDTO> createMarkerConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerConfigurationPT", required = true) @Valid @RequestBody LibMarkerConfigurationDTO markerConfigurationPT) throws URISyntaxException {
        log.debug("REST request to save ConfMarkerConfiguration : {}", markerConfigurationPT);

        if (markerConfigurationPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cFGMarkerConfiguration", "idexists", "A new cFGMarkerConfiguration cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CfgMarkerConfiguration cFGMarkerConfiguration = cfgMarkerConfigurationMapper.DTO2DB(markerConfigurationPT, corNetwork);
        cFGMarkerConfiguration = cfgMarkerConfigurationRepository.save(cFGMarkerConfiguration);
        LibMarkerConfigurationDTO result = cfgMarkerConfigurationMapper.DB2DTO(cFGMarkerConfiguration);
        return ResponseEntity
            .created(new URI("/api/v1/network/" + networkShortcut + "/configuration/network/dictionary/country/" + result.getId()))
            .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteMarkerConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to delete ConfMarkerConfiguration.id : {}", id);
        cfgMarkerConfigurationRepository.delete(Long.parseLong(id));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cFGMarkerConfiguration", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<LibMarkerConfigurationDTO>> getAllMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                             @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to getAll ConfMarkerConfiguration");
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        List<CfgMarkerConfiguration> cFGMarkerConfigurations = cfgMarkerConfigurationRepository.findByNetwork(corNetwork);
        return ResponseEntity.ok().body(cfgMarkerConfigurationMapper.DBs2DTOs(cFGMarkerConfigurations));

    }

    @Override
    public ResponseEntity<LibMarkerConfigurationDTO> getMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to get ConfMarkerConfiguration.id : {}", id);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CfgMarkerConfiguration cFGMarkerConfiguration = cfgMarkerConfigurationRepository.findOneByIdAndNetwork(Long.parseLong(id), corNetwork);
        LibMarkerConfigurationDTO cFGMarkerConfigurationDTO = cfgMarkerConfigurationMapper.DB2DTO(cFGMarkerConfiguration);
        return Optional.ofNullable(cFGMarkerConfigurationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
