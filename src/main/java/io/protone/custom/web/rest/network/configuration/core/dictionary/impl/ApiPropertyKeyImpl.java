package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.CoreKeyPT;
import io.protone.web.rest.mapper.CorPropertyKeyMapper;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiPropertyKey;
import io.protone.domain.CorPropertyKey;
import io.protone.repository.CorPropertyKeyRepository;
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
import java.util.List;
import java.util.Optional;

@RestController
public class ApiPropertyKeyImpl implements ApiPropertyKey {

    private final Logger log = LoggerFactory.getLogger(ApiPropertyKeyImpl.class);

    @Inject
    private CorPropertyKeyRepository cORPropertyKeyRepository;

    @Inject
    private CorNetworkService networkService;

    @Inject
    private CorPropertyKeyMapper corPropertyKeyMapper;

    @Override
    public ResponseEntity<CoreKeyPT> getPropertyKeyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "keyName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to get CorPropertyKey : {}", id);
        CorPropertyKey cORPropertyKey = cORPropertyKeyRepository.findOne(Long.parseLong(id));
        CoreKeyPT cORPropertyKeyDTO = corPropertyKeyMapper.DB2DTO(cORPropertyKey);
        return Optional.ofNullable(cORPropertyKeyDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deletePropertyKeyUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "keyName", required = true) @PathVariable("id") String id) {
        log.debug("REST request to delete CorPropertyKey : {}", id);
        cORPropertyKeyRepository.delete(Long.parseLong(id));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORPropertyKey", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<CoreKeyPT>> getAllPropertyKeysUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "pagable", required = true)  Pageable pagable) {
        log.debug("REST request to get all CorPropertyKeys");
        List<CorPropertyKey> cORPropertyKeys = cORPropertyKeyRepository.findAll();
        return ResponseEntity.ok().body(corPropertyKeyMapper.DBs2DTOs(cORPropertyKeys));
    }

    @Override
    public ResponseEntity<CoreKeyPT> updatePropertyKeyUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "propertyKeyDTO", required = true) @RequestBody CoreKeyPT propertyKeyDTO) {
        log.debug("REST request to update CorPropertyKey : {}", propertyKeyDTO);
        if (propertyKeyDTO.getId() == null) {
            return createPropertyKeyUsingPOST(networkShortcut, propertyKeyDTO);
        }
        CorPropertyKey cORPropertyKey = corPropertyKeyMapper.DTO2DB(propertyKeyDTO);
        cORPropertyKey = cORPropertyKeyRepository.save(cORPropertyKey);
        CoreKeyPT result = corPropertyKeyMapper.DB2DTO(cORPropertyKey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORPropertyKey", propertyKeyDTO.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CoreKeyPT> createPropertyKeyUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "propertyKeyDTO", required = true) @RequestBody CoreKeyPT propertyKeyDTO) {
        log.debug("REST request to save CorPropertyKey : {}", propertyKeyDTO);
        if (propertyKeyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORPropertyKey", "idexists", "A new cORPropertyKey cannot already have an ID")).body(null);
        }
        CorPropertyKey cORPropertyKey = corPropertyKeyMapper.DTO2DB(propertyKeyDTO);
        cORPropertyKey = cORPropertyKeyRepository.save(cORPropertyKey);
        CoreKeyPT result = corPropertyKeyMapper.DB2DTO(cORPropertyKey);
        return ResponseEntity.ok().body(result);
    }
}
