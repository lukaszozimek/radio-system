package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.CoreSizePT;
import io.protone.custom.service.mapper.CustomCorSizeMapper;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionarySize;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorSize;
import io.protone.repository.CorSizeRepository;
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
public class ApiDictionarySizeImpl implements ApiDictionarySize {

    private final Logger log = LoggerFactory.getLogger(ApiDictionarySizeImpl.class);

    @Inject
    private CorSizeRepository cORSizeRepository;
    @Inject
    private CorNetworkService networkService;
    @Inject
    private CustomCorSizeMapper customCorSizeMapper;

    @Override
    public ResponseEntity<CoreSizePT> createSizeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "coreSizePT", required = true) @RequestBody CoreSizePT coreSizePT) {
        log.debug("REST request to save CorSize : {}", coreSizePT);
        if (coreSizePT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORSize", "idexists", "A new cORSize cannot already have an ID")).body(null);
        }

        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        CorSize cORSize = customCorSizeMapper.cORSizeDTOToCorSize(coreSizePT);
        cORSize.setNetwork(corNetwork);
        cORSize = cORSizeRepository.save(cORSize);
        CoreSizePT result = customCorSizeMapper.cORSizeToCorSizeDTO(cORSize);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deleteSizeUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CorSize : {}", id);
        cORSizeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORSize", id.toString())).build();
    }

    @Override
    public ResponseEntity<List<CoreSizePT>> getAllSizeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CorSizes");
        List<CorSize> cORSizes = cORSizeRepository.findAll();
        return ResponseEntity.ok().body(customCorSizeMapper.cORSizesToCorSizeDTOs(cORSizes));
    }

    @Override
    public ResponseEntity<CoreSizePT> getSizeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorSize : {}", id);
        CorSize cORSize = cORSizeRepository.findOne(id);
        CoreSizePT cORSizeDTO = customCorSizeMapper.cORSizeToCorSizeDTO(cORSize);
        return Optional.ofNullable(cORSizeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CoreSizePT> updateSizeUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "coreSizePT", required = true) @RequestBody CoreSizePT coreSizePT) {
        log.debug("REST request to update CorSize : {}", coreSizePT);
        if (coreSizePT.getId() == null) {
            return createSizeUsingPOST(networkShortcut, coreSizePT);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        CorSize cORSize = customCorSizeMapper.cORSizeDTOToCorSize(coreSizePT);
        cORSize.setNetwork(corNetwork);
        cORSize = cORSizeRepository.save(cORSize);
        CoreSizePT result = customCorSizeMapper.cORSizeToCorSizeDTO(cORSize);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORSize", coreSizePT.getId().toString()))
            .body(result);
    }
}
