package io.protone.application.web.api.library.impl;


import io.protone.application.web.api.library.LibraryMarkerConfigurationResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.library.api.dto.LibMarkerConfigurationDTO;
import io.protone.library.domain.LibMarkerConfiguration;
import io.protone.library.mapper.LibMarkerConfigurationMapper;
import io.protone.library.repository.LibMarkerConfigurationRepository;
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
    private LibMarkerConfigurationRepository libMarkerConfigurationRepository;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private LibMarkerConfigurationMapper libMarkerConfigurationMapper;

    @Override
    public ResponseEntity<LibMarkerConfigurationDTO> updateMarkerConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerConfigurationDTO", required = true) @Valid @RequestBody LibMarkerConfigurationDTO markerConfigurationPT) throws URISyntaxException {
        log.debug("REST request to update ConfMarkerConfiguration : {}", markerConfigurationPT);
        if (markerConfigurationPT.getId() == null) {
            return createMarkerConfigurationUsingPOST(networkShortcut, markerConfigurationPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        LibMarkerConfiguration cFGMarkerConfiguration = libMarkerConfigurationMapper.DTO2DB(markerConfigurationPT, corNetwork);
        cFGMarkerConfiguration = libMarkerConfigurationRepository.save(cFGMarkerConfiguration);
        LibMarkerConfigurationDTO result = libMarkerConfigurationMapper.DB2DTO(cFGMarkerConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cfgMarkerConfiguration", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<LibMarkerConfigurationDTO> createMarkerConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerConfigurationPT", required = true) @Valid @RequestBody LibMarkerConfigurationDTO markerConfigurationPT) throws URISyntaxException {
        log.debug("REST request to saveCorContact ConfMarkerConfiguration : {}", markerConfigurationPT);

        if (markerConfigurationPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cFGMarkerConfiguration", "idexists", "A new cFGMarkerConfiguration cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        LibMarkerConfiguration cFGMarkerConfiguration = libMarkerConfigurationMapper.DTO2DB(markerConfigurationPT, corNetwork);
        cFGMarkerConfiguration = libMarkerConfigurationRepository.save(cFGMarkerConfiguration);
        LibMarkerConfigurationDTO result = libMarkerConfigurationMapper.DB2DTO(cFGMarkerConfiguration);
        return ResponseEntity
            .created(new URI("/api/v1/network/" + networkShortcut + "/configuration/network/dictionary/country/" + result.getId()))
            .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteMarkerConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to delete ConfMarkerConfiguration.id : {}", id);
        libMarkerConfigurationRepository.delete(Long.parseLong(id));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cFGMarkerConfiguration", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<LibMarkerConfigurationDTO>> getAllMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                             @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to getAll ConfMarkerConfiguration");
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        List<LibMarkerConfiguration> cFGMarkerConfigurations = libMarkerConfigurationRepository.findByNetwork(corNetwork);
        return ResponseEntity.ok().body(libMarkerConfigurationMapper.DBs2DTOs(cFGMarkerConfigurations));

    }

    @Override
    public ResponseEntity<LibMarkerConfigurationDTO> getMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to get ConfMarkerConfiguration.id : {}", id);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        LibMarkerConfiguration cFGMarkerConfiguration = libMarkerConfigurationRepository.findOneByIdAndNetwork(Long.parseLong(id), corNetwork);
        LibMarkerConfigurationDTO cFGMarkerConfigurationDTO = libMarkerConfigurationMapper.DB2DTO(cFGMarkerConfiguration);
        return Optional.ofNullable(cFGMarkerConfigurationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
