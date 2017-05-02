package io.protone.web.rest.cor.impl;

import io.protone.web.rest.dto.cor.CorKeyDTO;
import io.protone.domain.CorNetwork;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.rest.mapper.CorPropertyKeyMapper;
import io.protone.web.rest.cor.CorPropertyKeyResource;
import io.protone.domain.CorPropertyKey;
import io.protone.repository.cor.CorPropertyKeyRepository;
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
public class CorPropertyKeyResourceImpl implements CorPropertyKeyResource {

    private final Logger log = LoggerFactory.getLogger(CorPropertyKeyResourceImpl.class);

    @Inject
    private CorPropertyKeyRepository corPropertyKeyRepository;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorPropertyKeyMapper corPropertyKeyMapper;

    @Override
    public ResponseEntity<CorKeyDTO> getPropertyKeyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "keyName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to get CorPropertyKey : {}", id);
        CorPropertyKey cORPropertyKey = corPropertyKeyRepository.findByIdAndNetwork_Shortcut(Long.parseLong(id), networkShortcut);
        CorKeyDTO cORPropertyKeyDTO = corPropertyKeyMapper.DB2DTO(cORPropertyKey);
        return Optional.ofNullable(cORPropertyKeyDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deletePropertyKeyUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "keyName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to delete CorPropertyKey : {}", id);
        corPropertyKeyRepository.deleteByIdAndNetwork_Shortcut(Long.parseLong(id), networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORPropertyKey", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<CorKeyDTO>> getAllPropertyKeysUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CorPropertyKeys");
        List<CorPropertyKey> cORPropertyKeys = corPropertyKeyRepository.findByNetwork_Shortcut(networkShortcut, pagable);
        List<CorKeyDTO> corKeyDTOList = corPropertyKeyMapper.DBs2DTOs(cORPropertyKeys);
        return Optional.ofNullable(corKeyDTOList)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @Override
    public ResponseEntity<CorKeyDTO> updatePropertyKeyUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "propertyKeyDTO", required = true) @Valid @RequestBody CorKeyDTO propertyKeyDTO) throws URISyntaxException {
        log.debug("REST request to update CorPropertyKey : {}", propertyKeyDTO);
        if (propertyKeyDTO.getId() == null) {
            return createPropertyKeyUsingPOST(networkShortcut, propertyKeyDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorPropertyKey cORPropertyKey = corPropertyKeyMapper.DTO2DB(propertyKeyDTO, corNetwork);
        cORPropertyKey = corPropertyKeyRepository.save(cORPropertyKey);
        CorKeyDTO result = corPropertyKeyMapper.DB2DTO(cORPropertyKey);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("cORPropertyKey", propertyKeyDTO.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CorKeyDTO> createPropertyKeyUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "propertyKeyDTO", required = true) @Valid @RequestBody CorKeyDTO propertyKeyDTO) throws URISyntaxException {
        log.debug("REST request to save CorPropertyKey : {}", propertyKeyDTO);
        if (propertyKeyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORPropertyKey", "idexists", "A new cORPropertyKey cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorPropertyKey cORPropertyKey = corPropertyKeyMapper.DTO2DB(propertyKeyDTO, corNetwork);
        cORPropertyKey = corPropertyKeyRepository.save(cORPropertyKey);
        CorKeyDTO cORPropertyKeyDTO = corPropertyKeyMapper.DB2DTO(cORPropertyKey);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/configuration/network/dictionary/property/key" + cORPropertyKeyDTO.getId()))
            .headers(HeaderUtil.createEntityUpdateAlert("cORPropertyKey", cORPropertyKeyDTO.getId().toString()))
            .body(cORPropertyKeyDTO);
    }
}
