package io.protone.custom.web.rest.network.configuration.traffic.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfTraAdvertismentTypePT;
import io.protone.custom.service.mapper.CustomTraAdvertismentTypeMapper;
import io.protone.custom.web.rest.network.configuration.traffic.ApiConfigurationTrafficDictionaryAdvertismentType;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraAdvertismentType;
import io.protone.repository.TraAdvertismentTypeRepository;
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

/**
 * Created by lukaszozimek on 23/03/2017.
 */
@RestController
public class ApiConfigurationTrafficDictionaryAdvertismentTypeImpl implements ApiConfigurationTrafficDictionaryAdvertismentType {
    private final Logger log = LoggerFactory.getLogger(ApiConfigurationTrafficDictionaryAdvertismentTypeImpl.class);

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private TraAdvertismentTypeRepository traAdvertismentTypeRepository;

    @Inject
    private CustomTraAdvertismentTypeMapper traAdvertismentTypeMapper;

    @Override
    public ResponseEntity<List<ConfTraAdvertismentTypePT>> getAllAdvertismentTypeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get TraAdvertismentType : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<TraAdvertismentType> corCurrencies = traAdvertismentTypeRepository.findByNetwork(corNetwork);
        List<ConfTraAdvertismentTypePT> confCurrencyPTS = traAdvertismentTypeMapper.DBs2DTOs(corCurrencies);
        return Optional.ofNullable(confCurrencyPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<ConfTraAdvertismentTypePT> getAdvertismentTypeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraAdvertismentType : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        TraAdvertismentType traCampaingStatus = traAdvertismentTypeRepository.findOneByIdAndNetwork(id, corNetwork);
        ConfTraAdvertismentTypePT confCampaingStatusPT = traAdvertismentTypeMapper.DB2DTO(traCampaingStatus);
        return Optional.ofNullable(confCampaingStatusPT)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfTraAdvertismentTypePT> updateAdvertismentTypeUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "advertismentTypePT", required = true) @RequestBody ConfTraAdvertismentTypePT advertismentTypePT) {
        log.debug("REST request to update TraAdvertismentType : {}", advertismentTypePT);
        if (advertismentTypePT.getId() == null) {
            return createAdvertismentTypeUsingPOST(networkShortcut, advertismentTypePT);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraAdvertismentType corCountry = traAdvertismentTypeMapper.DTO2DB(advertismentTypePT);
        corCountry.setNetwork(corNetwork);
        corCountry = traAdvertismentTypeRepository.save(corCountry);
        ConfTraAdvertismentTypePT result = traAdvertismentTypeMapper.DB2DTO(corCountry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("TraAdvertismentType", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfTraAdvertismentTypePT> createAdvertismentTypeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                     @ApiParam(value = "advertismentTypePT", required = true) @RequestBody ConfTraAdvertismentTypePT advertismentTypePT) {
        log.debug("REST request to save TraAdvertismentType : {}", advertismentTypePT);
        if (advertismentTypePT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraOrderStTraAdvertismentTypeatus", "idexists", "A new TraAdvertismentType cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraAdvertismentType corCountry = traAdvertismentTypeMapper.DTO2DB(advertismentTypePT);
        corCountry.setNetwork(corNetwork);
        corCountry = traAdvertismentTypeRepository.save(corCountry);
        ConfTraAdvertismentTypePT result = traAdvertismentTypeMapper.DB2DTO(corCountry);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deleteAdvertismentTypeUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraAdvertismentType : {}", id);
        traAdvertismentTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("TraAdvertismentType", id.toString())).build();
    }
}
