package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.CoreAreaPT;
import io.protone.custom.service.mapper.CustomCorAreaMapper;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionaryArea;
import io.protone.domain.CorArea;
import io.protone.domain.CorNetwork;
import io.protone.repository.CorAreaRepository;
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
public class ApiDictionaryAreaImpl implements ApiDictionaryArea {

    private final Logger log = LoggerFactory.getLogger(ApiDictionaryAreaImpl.class);

    @Inject
    private CorAreaRepository cORAreaRepository;

    @Inject
    private NetworkService networkService;

    @Inject
    private CustomCorAreaMapper customCorAreaMapper;


    @Override
    public ResponseEntity<CoreAreaPT> getAreaUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorArea : {}", id);
        CorArea cORArea = cORAreaRepository.findOne(id);
        CoreAreaPT cORAreaDTO = customCorAreaMapper.cORAreaToCorAreaDTO(cORArea);
        return Optional.ofNullable(cORAreaDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteAreaUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CorArea : {}", id);
        cORAreaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORArea", id.toString())).build();

    }

    @Override
    public ResponseEntity<List<CoreAreaPT>> getAllAreaUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CorAreas");
        List<CorArea> cORAreas = cORAreaRepository.findAll();
        return ResponseEntity.ok().body(customCorAreaMapper.cORAreasToCorAreaDTOs(cORAreas));
    }

    @Override
    public ResponseEntity<CoreAreaPT> updateAreaUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "coreAreaPT", required = true) @RequestBody CoreAreaPT coreAreaPT) {
        log.debug("REST request to update CorArea : {}", coreAreaPT);
        if (coreAreaPT.getId() == null) {
            return createAreaUsingPOST(networkShortcut, coreAreaPT);
        }
        CorNetwork corNetwork =networkService.findNetwork(networkShortcut);
        CorArea cORArea = customCorAreaMapper.cORAreaDTOToCorArea(coreAreaPT);
        cORArea.setNetwork(corNetwork);
        cORArea = cORAreaRepository.save(cORArea);
        CoreAreaPT result = customCorAreaMapper.cORAreaToCorAreaDTO(cORArea);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORArea", coreAreaPT.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CoreAreaPT> createAreaUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "coreAreaPT", required = true) @RequestBody CoreAreaPT coreAreaPT) {
        log.debug("REST request to save CorArea : {}", coreAreaPT);
        if (coreAreaPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORArea", "idexists", "A new cORArea cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork =networkService.findNetwork(networkShortcut);
        CorArea cORArea = customCorAreaMapper.cORAreaDTOToCorArea(coreAreaPT);
        cORArea.setNetwork(corNetwork);
        cORArea = cORAreaRepository.save(cORArea);
        CoreAreaPT result = customCorAreaMapper.cORAreaToCorAreaDTO(cORArea);
        return ResponseEntity.ok().body(result);
    }
}
