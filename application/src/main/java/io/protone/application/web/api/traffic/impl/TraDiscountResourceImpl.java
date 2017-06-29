package io.protone.application.web.api.traffic.impl;


import io.protone.application.web.api.traffic.TraDiscountResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.core.domain.CorDiscount;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorDiscountRepository;
import io.protone.core.service.CorNetworkService;
import io.protone.traffic.api.dto.TraDiscountDTO;
import io.protone.traffic.mapper.TraDiscountMapper;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Created by lukaszozimek on 23/03/2017.
 */

@RestController
public class TraDiscountResourceImpl implements TraDiscountResource {

    private final Logger log = LoggerFactory.getLogger(TraDiscountResourceImpl.class);

    @Autowired
    private TraDiscountMapper traDiscountMapper;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private CorDiscountRepository traDiscountRepository;

    @Override
    public ResponseEntity<List<TraDiscountDTO>> getAllDiscountUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable) {

        log.debug("REST request to get all TraDiscount");
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<CorDiscount> traDiscount = traDiscountRepository.findByNetwork(corNetwork);
        List<TraDiscountDTO> traDiscountPT = traDiscountMapper.DBs2DTOs(traDiscount);
        return Optional.ofNullable(traDiscountPT)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraDiscountDTO> getDiscountUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {

        log.debug("REST request to get TraDiscount : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorDiscount traDiscount = traDiscountRepository.findOneByIdAndNetwork(id, corNetwork);
        TraDiscountDTO traDiscountPt = traDiscountMapper.DB2DTO(traDiscount);
        return Optional.ofNullable(traDiscountPt)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraDiscountDTO> updateDiscountUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "discountPT", required = true) @RequestBody TraDiscountDTO discountPT) throws URISyntaxException {

        log.debug("REST request to update TraDiscount : {}", discountPT);
        if (discountPT.getId() == null) {
            return createDiscountUsingPOST(networkShortcut, discountPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorDiscount traDiscount = traDiscountMapper.DTO2DB(discountPT, corNetwork);
        traDiscount = traDiscountRepository.save(traDiscount);
        TraDiscountDTO result = traDiscountMapper.DB2DTO(traDiscount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("TraDiscount", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<TraDiscountDTO> createDiscountUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "discountPT", required = true) @RequestBody TraDiscountDTO discountPT) throws URISyntaxException {
        log.debug("REST request to saveCorContact TraDiscount : {}", discountPT);
        if (discountPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraDiscount", "idexists", "A new TraDiscount cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorDiscount traDiscount = traDiscountMapper.DTO2DB(discountPT, corNetwork);
        traDiscount = traDiscountRepository.save(traDiscount);
        TraDiscountDTO result = traDiscountMapper.DB2DTO(traDiscount);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/configuration/traffic/dictionary/discount/" + result.getId()))
            .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteDiscountUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraDiscount : {}", id);
        traDiscountRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("TraDiscount", id.toString())).build();
    }
}
