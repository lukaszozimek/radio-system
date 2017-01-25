package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.CoreRangePT;
import io.protone.custom.service.mapper.CustomCORAreaMapper;
import io.protone.custom.service.mapper.CustomCORRangeMapper;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionaryRange;
import io.protone.domain.CORRange;
import io.protone.repository.CCORNetworkRepository;
import io.protone.repository.CORRangeRepository;
import io.protone.service.dto.CORRangeDTO;
import io.protone.service.mapper.CORRangeMapper;
import io.protone.web.rest.CORRangeResource;
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
public class ApiDictionaryRangeImpl implements ApiDictionaryRange {

    private final Logger log = LoggerFactory.getLogger(ApiDictionaryRangeImpl.class);

    @Inject
    private CORRangeRepository cORRangeRepository;
    @Inject
    private NetworkService networkService;
    @Inject
    private CustomCORRangeMapper customCORAreaMapper;

    @Override
    public ResponseEntity<CoreRangePT> createRangeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "coreRangePT", required = true) @RequestBody CoreRangePT coreRangePT) {
        log.debug("REST request to save CORRange : {}", coreRangePT);
        if (coreRangePT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORRange", "idexists", "A new cORRange cannot already have an ID")).body(null);
        }
        CORRange cORRange = customCORAreaMapper.cORRangeDTOToCORRange(coreRangePT);
        cORRange = cORRangeRepository.save(cORRange);
        CoreRangePT result = customCORAreaMapper.cORRangeToCORRangeDTO(cORRange);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<List<CoreRangePT>> getAllRangeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CORRanges");
        List<CORRange> cORRanges = cORRangeRepository.findAll();
        return ResponseEntity.ok().body(customCORAreaMapper.cORRangesToCORRangeDTOs(cORRanges));
    }

    @Override
    public ResponseEntity<CoreRangePT> getRangeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CORRange : {}", id);
        CORRange cORRange = cORRangeRepository.findOne(id);
        CoreRangePT cORRangeDTO = customCORAreaMapper.cORRangeToCORRangeDTO(cORRange);
        return Optional.ofNullable(cORRangeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CoreRangePT> updateRangeUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "coreRangePT", required = true) @RequestBody CoreRangePT coreRangePT) {
        log.debug("REST request to update CORRange : {}", coreRangePT);
        if (coreRangePT.getId() == null) {
            return createRangeUsingPOST(networkShortcut, coreRangePT);
        }
        CORRange cORRange = customCORAreaMapper.cORRangeDTOToCORRange(coreRangePT);
        cORRange = cORRangeRepository.save(cORRange);
        CoreRangePT result = customCORAreaMapper.cORRangeToCORRangeDTO(cORRange);
        return ResponseEntity.ok()
            .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteRangeUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CORRange : {}", id);
        cORRangeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORRange", id.toString())).build();

    }
}
