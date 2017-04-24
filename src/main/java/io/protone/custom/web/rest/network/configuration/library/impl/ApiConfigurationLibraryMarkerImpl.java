package io.protone.custom.web.rest.network.configuration.library.impl;

import io.protone.custom.service.dto.ConfMarkerConfigurationPT;
import io.protone.custom.service.mapper.CustomCfgMarkerConfigurationMapper;
import io.protone.custom.web.rest.network.configuration.library.ApiConfigurationLibraryMarker;
import io.protone.domain.CfgMarkerConfiguration;
import io.protone.repository.CfgMarkerConfigurationRepository;
import io.protone.repository.CorNetworkRepository;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
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

    @Inject
    private CfgMarkerConfigurationRepository cFGMarkerConfigurationRepository;
    @Inject
    private CorNetworkRepository networkRepository;
    @Inject
    private CustomCfgMarkerConfigurationMapper cFGMarkerConfigurationMapper;

    @Override
    public ResponseEntity<ConfMarkerConfigurationPT> updateMarkerConfigurationUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerConfigurationDTO", required = true) @RequestBody ConfMarkerConfigurationPT markerConfigurationDTO) {
        if (markerConfigurationDTO.getId() == null) {
            return createMarkerConfigurationUsingPOST(networkShortcut, markerConfigurationDTO);
        }

        CfgMarkerConfiguration cFGMarkerConfiguration = cFGMarkerConfigurationMapper.cFGMarkerConfigurationDTOToCfgMarkerConfiguration(markerConfigurationDTO);
        cFGMarkerConfiguration = cFGMarkerConfigurationRepository.save(cFGMarkerConfiguration);
        ConfMarkerConfigurationPT result = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCfgMarkerConfigurationDTO(cFGMarkerConfiguration);
        return ResponseEntity.ok()
            .body(result);
    }

    @Override
    public ResponseEntity<ConfMarkerConfigurationPT> createMarkerConfigurationUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerConfigurationPT", required = true) @RequestBody ConfMarkerConfigurationPT markerConfigurationPT) {
        if (markerConfigurationPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cFGMarkerConfiguration", "idexists", "A new cFGMarkerConfiguration cannot already have an ID")).body(null);
        }
        CfgMarkerConfiguration cFGMarkerConfiguration = cFGMarkerConfigurationMapper.cFGMarkerConfigurationDTOToCfgMarkerConfiguration(markerConfigurationPT);
        cFGMarkerConfiguration = cFGMarkerConfigurationRepository.save(cFGMarkerConfiguration);
        ConfMarkerConfigurationPT result = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCfgMarkerConfigurationDTO(cFGMarkerConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cFGMarkerConfiguration", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteMarkerConfigurationUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerName", required = true) @PathVariable("id") String id) {
        cFGMarkerConfigurationRepository.delete(Long.parseLong(id));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cFGMarkerConfiguration", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<ConfMarkerConfigurationPT>> getAllMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                             @ApiParam(value = "pagable", required = true)  Pageable pagable) {
        List<CfgMarkerConfiguration> cFGMarkerConfigurations = cFGMarkerConfigurationRepository.findAll();
        return ResponseEntity.ok().body(cFGMarkerConfigurationMapper.cFGMarkerConfigurationsToCfgMarkerConfigurationDTOs(cFGMarkerConfigurations));

    }

    @Override
    public ResponseEntity<ConfMarkerConfigurationPT> getMarkerConfigurationUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "markerName", required = true) @PathVariable("id") String id) {
        CfgMarkerConfiguration cFGMarkerConfiguration = cFGMarkerConfigurationRepository.findOne(Long.parseLong(id));
        ConfMarkerConfigurationPT cFGMarkerConfigurationDTO = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCfgMarkerConfigurationDTO(cFGMarkerConfiguration);
        return Optional.ofNullable(cFGMarkerConfigurationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
