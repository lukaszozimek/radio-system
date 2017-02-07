package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.custom.service.NetworkService;
import io.protone.custom.service.dto.CoreRangePT;
import io.protone.custom.service.mapper.CustomCorRangeMapper;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionaryRange;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorRange;
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
public class ApiDictionaryRangeImpl implements ApiDictionaryRange {

    private final Logger log = LoggerFactory.getLogger(ApiDictionaryRangeImpl.class);

    @Inject
    private CorRangeRepository cORRangeRepository;
    @Inject
    private NetworkService networkService;
    @Inject
    private CustomCorRangeMapper customCorAreaMapper;

    @Override
    public ResponseEntity<CoreRangePT> createRangeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "coreRangePT", required = true) @RequestBody CoreRangePT coreRangePT) {
        log.debug("REST request to save CorRange : {}", coreRangePT);
        if (coreRangePT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORRange", "idexists", "A new cORRange cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        CorRange cORRange = customCorAreaMapper.cORRangeDTOToCorRange(coreRangePT);
        cORRange.setNetwork(corNetwork);
        cORRange = cORRangeRepository.save(cORRange);
        CoreRangePT result = customCorAreaMapper.cORRangeToCorRangeDTO(cORRange);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<List<CoreRangePT>> getAllRangeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CorRanges");
        List<CorRange> cORRanges = cORRangeRepository.findAll();
        return ResponseEntity.ok().body(customCorAreaMapper.cORRangesToCorRangeDTOs(cORRanges));
    }

    @Override
    public ResponseEntity<CoreRangePT> getRangeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorRange : {}", id);
        CorRange cORRange = cORRangeRepository.findOne(id);
        CoreRangePT cORRangeDTO = customCorAreaMapper.cORRangeToCorRangeDTO(cORRange);
        return Optional.ofNullable(cORRangeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CoreRangePT> updateRangeUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "coreRangePT", required = true) @RequestBody CoreRangePT coreRangePT) {
        log.debug("REST request to update CorRange : {}", coreRangePT);
        if (coreRangePT.getId() == null) {
            return createRangeUsingPOST(networkShortcut, coreRangePT);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        CorRange cORRange = customCorAreaMapper.cORRangeDTOToCorRange(coreRangePT);
        cORRange.setNetwork(corNetwork);
        cORRange = cORRangeRepository.save(cORRange);
        CoreRangePT result = customCorAreaMapper.cORRangeToCorRangeDTO(cORRange);
        return ResponseEntity.ok()
            .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteRangeUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CorRange : {}", id);
        cORRangeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORRange", id.toString())).build();

    }
}
