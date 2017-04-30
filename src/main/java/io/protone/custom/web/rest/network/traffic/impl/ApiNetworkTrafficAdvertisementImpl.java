package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.domain.TraAdvertisement;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraAdvertisementService;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficAdvertisement;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.mapper.TraAdvertisementMapper;
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
public class ApiNetworkTrafficAdvertisementImpl implements ApiNetworkTrafficAdvertisement {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficAdvertisementImpl.class);

    @Inject
    private TraAdvertisementService traAdvertisementService;

    @Inject
    private TraAdvertisementMapper traAdvertisementMapper;

    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<TraAdvertisementPT> updateAdvertisementUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traAdvertisementPT", required = true) @RequestBody TraAdvertisementPT traAdvertisementPT) {
        log.debug("REST request to update TraAdvertisement : {}, for Network: {}", traAdvertisementPT, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (traAdvertisementPT.getId() == null) {
            return createAdvertisementUsingPOST(networkShortcut, traAdvertisementPT);
        }
        TraAdvertisement traAdvertisement = traAdvertisementMapper.DTO2DB(traAdvertisementPT, corNetwork);
        TraAdvertisement entity = traAdvertisementService.saveAdvertisement(traAdvertisement);
        TraAdvertisementPT response = traAdvertisementMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraAdvertisementPT> createAdvertisementUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traAdvertisementPT", required = true) @RequestBody TraAdvertisementPT traAdvertisementPT) {
        log.debug("REST request to save TraAdvertisement : {}, for Network: {}", traAdvertisementPT, networkShortcut);
        if (traAdvertisementPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraAdvertisement", "idexists", "A new TraAdvertisement cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        TraAdvertisement traAdvertisement = traAdvertisementMapper.DTO2DB(traAdvertisementPT, corNetwork);
        TraAdvertisement entity = traAdvertisementService.saveAdvertisement(traAdvertisement);
        TraAdvertisementPT response = traAdvertisementMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<TraAdvertisementPT>> getAllAdvertisementsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraAdvertisement, for Network: {}", networkShortcut);
        List<TraAdvertisement> entity = traAdvertisementService.getAllAdvertisement(networkShortcut, pagable);
        List<TraAdvertisementPT> response = traAdvertisementMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteAdvertisementUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "idx", required = true) @PathVariable("idx") Long idx) {
        log.debug("REST request to delete TraAdvertisement : {}, for Network: {}", idx, networkShortcut);
        traAdvertisementService.deleteAdvertisement(idx, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("TraAdvertisement", idx.toString())).build();
    }

    @Override
    public ResponseEntity<TraAdvertisementPT> getAdvertisementUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "idx", required = true) @PathVariable("idx") Long idx) {
        log.debug("REST request to get TraAdvertisement : {}, for Network: {}", idx, networkShortcut);
        TraAdvertisement entity = traAdvertisementService.getAdvertisement(idx, networkShortcut);
        TraAdvertisementPT response = traAdvertisementMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
