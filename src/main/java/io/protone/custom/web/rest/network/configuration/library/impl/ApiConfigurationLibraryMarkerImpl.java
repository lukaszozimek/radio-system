package io.protone.custom.web.rest.network.configuration.library.impl;

import io.protone.custom.service.dto.ConfMarkerConfigurationPT;
import io.protone.custom.service.mapper.CustomCFGMarkerConfigurationMapper;
import io.protone.custom.web.rest.network.configuration.library.ApiConfigurationLibraryMarker;
import io.protone.domain.CFGMarkerConfiguration;
import io.protone.repository.CCORNetworkRepository;
import io.protone.repository.CFGMarkerConfigurationRepository;
import io.protone.web.rest.CFGMarkerConfigurationResource;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiConfigurationLibraryMarkerImpl implements ApiConfigurationLibraryMarker {

    private final Logger log = LoggerFactory.getLogger(CFGMarkerConfigurationResource.class);

    @Inject
    private CFGMarkerConfigurationRepository cFGMarkerConfigurationRepository;
    @Inject
    private CCORNetworkRepository networkRepository;
    @Inject
    private CustomCFGMarkerConfigurationMapper cFGMarkerConfigurationMapper;

    @Override
    public ResponseEntity<ConfMarkerConfigurationPT> updateMarkerConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerConfigurationDTO", required = true) @RequestBody ConfMarkerConfigurationPT markerConfigurationDTO) {
        log.debug("REST request to save CFGMarkerConfiguration : {}", markerConfigurationDTO);
        if (markerConfigurationDTO.getId() == null) {
            return createMarkerConfigurationUsingPOST(networkShortcut, markerConfigurationDTO);
        }

        CFGMarkerConfiguration cFGMarkerConfiguration = cFGMarkerConfigurationMapper.cFGMarkerConfigurationDTOToCFGMarkerConfiguration(markerConfigurationDTO);
        cFGMarkerConfiguration = cFGMarkerConfigurationRepository.save(cFGMarkerConfiguration);
        ConfMarkerConfigurationPT result = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(cFGMarkerConfiguration);
        return ResponseEntity.ok()
            .body(result);
    }

    @Override
    public ResponseEntity<ConfMarkerConfigurationPT> createMarkerConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerConfigurationPT", required = true) @RequestBody ConfMarkerConfigurationPT markerConfigurationPT) {
        log.debug("REST request to update CFGMarkerConfiguration : {}", markerConfigurationPT);
        if (markerConfigurationPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cFGMarkerConfiguration", "idexists", "A new cFGMarkerConfiguration cannot already have an ID")).body(null);
        }
        CFGMarkerConfiguration cFGMarkerConfiguration = cFGMarkerConfigurationMapper.cFGMarkerConfigurationDTOToCFGMarkerConfiguration(markerConfigurationPT);
        cFGMarkerConfiguration = cFGMarkerConfigurationRepository.save(cFGMarkerConfiguration);
        ConfMarkerConfigurationPT result = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(cFGMarkerConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cFGMarkerConfiguration", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteMarkerConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to delete CFGMarkerConfiguration : {}", id);
        cFGMarkerConfigurationRepository.delete(Long.parseLong(id));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cFGMarkerConfiguration", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<ConfMarkerConfigurationPT>> getAllMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CFGMarkerConfigurations");
        List<CFGMarkerConfiguration> cFGMarkerConfigurations = cFGMarkerConfigurationRepository.findAll();
        return ResponseEntity.ok().body(cFGMarkerConfigurationMapper.cFGMarkerConfigurationsToCFGMarkerConfigurationDTOs(cFGMarkerConfigurations));

    }

    @Override
    public ResponseEntity<ConfMarkerConfigurationPT> getMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to get CFGMarkerConfiguration : {}", id);
        CFGMarkerConfiguration cFGMarkerConfiguration = cFGMarkerConfigurationRepository.findOne(Long.parseLong(id));
        ConfMarkerConfigurationPT cFGMarkerConfigurationDTO = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(cFGMarkerConfiguration);
        return Optional.ofNullable(cFGMarkerConfigurationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
