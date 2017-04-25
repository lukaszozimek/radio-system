package io.protone.custom.web.rest.network.configuration.traffic.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfCurrencyPT;
import io.protone.custom.service.dto.ConfDiscountPT;
import io.protone.custom.service.dto.ConfTaxPT;
import io.protone.custom.service.mapper.CustomTraDiscountMapper;
import io.protone.custom.web.rest.network.configuration.traffic.ApiConfigurationTrafficDictionaryDiscount;
import io.protone.domain.CorCurrency;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraDiscount;
import io.protone.repository.TraDiscountRepository;
import io.protone.web.rest.util.HeaderUtil;
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

import java.util.List;
import java.util.Optional;

/**
 * Created by lukaszozimek on 23/03/2017.
 */

@RestController
public class ApiConfigurationTrafficDictionaryDiscountImpl implements ApiConfigurationTrafficDictionaryDiscount {

    private final Logger log = LoggerFactory.getLogger(ApiConfigurationTrafficDictionaryDiscountImpl.class);

    @Autowired
    private CustomTraDiscountMapper customTraDiscountMapper;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private TraDiscountRepository traDiscountRepository;

    @Override
    public ResponseEntity<List<ConfDiscountPT>> getAllDiscountUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable) {

        log.debug("REST request to get all TraDiscount");
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<TraDiscount> traDiscount = traDiscountRepository.findByNetwork(corNetwork);
        List<ConfDiscountPT> traDiscountPT = customTraDiscountMapper.traDiscountsToTraDiscountDTOs(traDiscount);
        return Optional.ofNullable(traDiscountPT)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfDiscountPT> getDiscountUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {

        log.debug("REST request to get TraDiscount : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        TraDiscount traDiscount = traDiscountRepository.findOneByIdAndNetwork(id, corNetwork);
        ConfDiscountPT traDiscountPt = customTraDiscountMapper.traDiscountToTraDiscountDTO(traDiscount);
        return Optional.ofNullable(traDiscountPt)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfDiscountPT> updateDiscountUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "discountPT", required = true) @RequestBody ConfDiscountPT discountPT) {

        log.debug("REST request to update TraDiscount : {}", discountPT);
        if (discountPT.getId() == null) {
            return createDiscountUsingPOST(networkShortcut, discountPT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraDiscount traDiscount = customTraDiscountMapper.traDiscountDTOToTraDiscount(discountPT);
        traDiscount.setNetwork(corNetwork);
        traDiscount = traDiscountRepository.save(traDiscount);
        ConfDiscountPT result = customTraDiscountMapper.traDiscountToTraDiscountDTO(traDiscount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("TraDiscount", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfDiscountPT> createDiscountUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "discountPT", required = true) @RequestBody ConfDiscountPT discountPT) {
        log.debug("REST request to save TraDiscount : {}", discountPT);
        if (discountPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraDiscount", "idexists", "A new TraDiscount cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraDiscount traDiscount = customTraDiscountMapper.traDiscountDTOToTraDiscount(discountPT);
        traDiscount.setNetwork(corNetwork);

        traDiscount = traDiscountRepository.save(traDiscount);
        ConfDiscountPT result = customTraDiscountMapper.traDiscountToTraDiscountDTO(traDiscount);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deleteDiscountUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraDiscount : {}", id);
        traDiscountRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("TraDiscount", id.toString())).build();
    }
}
