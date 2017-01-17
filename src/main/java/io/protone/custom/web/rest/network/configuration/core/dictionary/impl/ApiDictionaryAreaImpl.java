package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.custom.service.dto.CoreAreaPT;
import io.protone.custom.service.mapper.CustomCORAreaMapper;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionaryArea;
import io.protone.domain.CORArea;
import io.protone.repository.CORAreaRepository;
import io.protone.service.dto.CORAreaDTO;
import io.protone.service.mapper.CORAreaMapper;
import io.protone.web.rest.CORAreaResource;
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
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiDictionaryAreaImpl implements ApiDictionaryArea {

    private final Logger log = LoggerFactory.getLogger(ApiDictionaryAreaImpl.class);

    @Inject
    private CORAreaRepository cORAreaRepository;

    @Inject
    private CustomCORAreaMapper customCORAreaMapper;


    @Override
    public ResponseEntity<CoreAreaPT> getAreaUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CORArea : {}", id);
        CORArea cORArea = cORAreaRepository.findOne(id);
        CoreAreaPT cORAreaDTO = customCORAreaMapper.cORAreaToCORAreaDTO(cORArea);
        return Optional.ofNullable(cORAreaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteAreaUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CORArea : {}", id);
        cORAreaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORArea", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<CoreAreaPT>> getAllAreaUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CORAreas");
        List<CORArea> cORAreas = cORAreaRepository.findAll();
        return ResponseEntity.ok().body(customCORAreaMapper.cORAreasToCORAreaDTOs(cORAreas));
    }

    @Override
    public ResponseEntity<CoreAreaPT> updateAreaUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "coreAreaPT", required = true) @RequestBody CoreAreaPT coreAreaPT) {
        log.debug("REST request to update CORArea : {}", coreAreaPT);
        if (coreAreaPT.getId() == null) {
            return createAreaUsingPOST(networkShortcut, coreAreaPT);
        }
        CORArea cORArea = customCORAreaMapper.cORAreaDTOToCORArea(coreAreaPT);
        cORArea = cORAreaRepository.save(cORArea);
        CoreAreaPT result = customCORAreaMapper.cORAreaToCORAreaDTO(cORArea);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORArea", coreAreaPT.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CoreAreaPT> createAreaUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "coreAreaPT", required = true) @RequestBody CoreAreaPT coreAreaPT) {
        log.debug("REST request to save CORArea : {}", coreAreaPT);
        if (coreAreaPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORArea", "idexists", "A new cORArea cannot already have an ID")).body(null);
        }
        CORArea cORArea = customCORAreaMapper.cORAreaDTOToCORArea(coreAreaPT);
        cORArea = cORAreaRepository.save(cORArea);
        CoreAreaPT result = customCORAreaMapper.cORAreaToCORAreaDTO(cORArea);
        return ResponseEntity.ok().body(result);
    }
}
