package io.protone.custom.web.rest.network.configuration.core.impl;

import io.protone.custom.service.dto.CoreKeyPT;
import io.protone.custom.service.mapper.CustomCORPropertyKeyMapper;
import io.protone.custom.web.rest.network.configuration.core.ApiPropertyKey;
import io.protone.domain.CORPropertyKey;
import io.protone.repository.CORPropertyKeyRepository;
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
public class ApiPropertyKeyImpl implements ApiPropertyKey {

    private final Logger log = LoggerFactory.getLogger(ApiPropertyKeyImpl.class);

    @Inject
    private CORPropertyKeyRepository cORPropertyKeyRepository;

    @Inject
    private CustomCORPropertyKeyMapper corPropertyKeyMapper;

    @Override
    public ResponseEntity<CoreKeyPT> getPropertyKeyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "keyName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to get CORPropertyKey : {}", id);
        CORPropertyKey cORPropertyKey = cORPropertyKeyRepository.findOne(Long.parseLong(id));
        CoreKeyPT cORPropertyKeyDTO = corPropertyKeyMapper.cORPropertyKeyToCORPropertyKeyDTO(cORPropertyKey);
        return Optional.ofNullable(cORPropertyKeyDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deletePropertyKeyUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "keyName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to delete CORPropertyKey : {}", id);
        cORPropertyKeyRepository.delete(Long.parseLong(id));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORPropertyKey", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<CoreKeyPT>> getAllPropertyKeysUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CORPropertyKeys");
        List<CORPropertyKey> cORPropertyKeys = cORPropertyKeyRepository.findAll();
        return ResponseEntity.ok().body(corPropertyKeyMapper.cORPropertyKeysToCORPropertyKeyDTOs(cORPropertyKeys));
    }

    @Override
    public ResponseEntity<CoreKeyPT> updatePropertyKeyUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "propertyKeyDTO", required = true) @RequestBody CoreKeyPT propertyKeyDTO) {
        log.debug("REST request to update CORPropertyKey : {}", propertyKeyDTO);
        if (propertyKeyDTO.getId() == null) {
            return createPropertyKeyUsingPOST(networkShortcut, propertyKeyDTO);
        }
        CORPropertyKey cORPropertyKey = corPropertyKeyMapper.cORPropertyKeyDTOToCORPropertyKey(propertyKeyDTO);
        cORPropertyKey = cORPropertyKeyRepository.save(cORPropertyKey);
        CoreKeyPT result = corPropertyKeyMapper.cORPropertyKeyToCORPropertyKeyDTO(cORPropertyKey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORPropertyKey", propertyKeyDTO.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CoreKeyPT> createPropertyKeyUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "propertyKeyDTO", required = true) @RequestBody CoreKeyPT propertyKeyDTO) {
        log.debug("REST request to save CORPropertyKey : {}", propertyKeyDTO);
        if (propertyKeyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORPropertyKey", "idexists", "A new cORPropertyKey cannot already have an ID")).body(null);
        }
        CORPropertyKey cORPropertyKey = corPropertyKeyMapper.cORPropertyKeyDTOToCORPropertyKey(propertyKeyDTO);
        cORPropertyKey = cORPropertyKeyRepository.save(cORPropertyKey);
        CoreKeyPT result = corPropertyKeyMapper.cORPropertyKeyToCORPropertyKeyDTO(cORPropertyKey);
        return ResponseEntity.ok().body(result);
    }
}
