package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfCountryPt;
import io.protone.service.mapper.CorCountryMapper;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionaryCountry;
import io.protone.domain.CorCountry;
import io.protone.domain.CorNetwork;
import io.protone.repository.custom.CustomCorCountryRepository;
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
public class ApiDictionaryCountryImpl implements ApiDictionaryCountry {

    private final Logger log = LoggerFactory.getLogger(ApiDictionaryCountryImpl.class);

    @Inject
    private CustomCorCountryRepository customCorCountryRepository;
    @Inject
    private CorNetworkService corNetworkService;
    @Inject
    private CorCountryMapper customCorCurrencyMapper;

    @Override
    public ResponseEntity<ConfCountryPt> updateCountryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confCountryPt", required = true) @RequestBody ConfCountryPt confCountryPt) {
        log.debug("REST request to update CorTax : {}", confCountryPt);
        if (confCountryPt.getId() == null) {
            return createCountryUsingPOST(networkShortcut, confCountryPt);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorCountry corCountry = customCorCurrencyMapper.DTO2DB(confCountryPt);
        corCountry.setNetwork(corNetwork);
        corCountry = customCorCountryRepository.save(corCountry);
        ConfCountryPt result = customCorCurrencyMapper.DB2DTO(corCountry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORArea", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfCountryPt> createCountryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confCountryPt", required = true) @RequestBody ConfCountryPt confCountryPt) {
        log.debug("REST request to save CorCountry : {}", confCountryPt);
        if (confCountryPt.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CorCountry", "idexists", "A new CorCountry cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorCountry corCountry = customCorCurrencyMapper.DTO2DB(confCountryPt);
        corCountry.setNetwork(corNetwork);
        corCountry = customCorCountryRepository.save(corCountry);
        ConfCountryPt result = customCorCurrencyMapper.DB2DTO(corCountry);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deleteCountryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CorCountry : {}", id);
        customCorCountryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CorTax", id.toString())).build();
    }

    @Override
    public ResponseEntity<List<ConfCountryPt>> getAllCountriesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "pagable", required = true)  Pageable pagable) {
        log.debug("REST request to get CorCountry : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<CorCountry> corCurrencies = customCorCountryRepository.findByNetwork(corNetwork);
        List<ConfCountryPt> confCurrencyPTS = customCorCurrencyMapper.DBs2DTOs(corCurrencies);
        return Optional.ofNullable(confCurrencyPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfCountryPt> getCountryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorCountry : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorCountry corTax = customCorCountryRepository.findOneByIdAndNetwork(id, corNetwork);
        ConfCountryPt confTaxPTS = customCorCurrencyMapper.DB2DTO(corTax);
        return Optional.ofNullable(confTaxPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
