package io.protone.application.web.api.cor.impl;

import io.protone.core.repository.CorCountryRepository;
import io.protone.domain.CorCountry;
import io.protone.domain.CorNetwork;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.api.cor.CorDictionaryCountryResource;
import io.protone.web.rest.dto.cor.CorCountryDTO;
import io.protone.web.rest.mapper.CorCountryMapper;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class CorDictionaryCountryResourceImpl implements CorDictionaryCountryResource {

    private final Logger log = LoggerFactory.getLogger(CorDictionaryCountryResourceImpl.class);

    @Inject
    private CorCountryRepository corCountryRepository;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorCountryMapper corCountryMapper;

    @Override
    public ResponseEntity<CorCountryDTO> updateCountryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confCountryPt", required = true) @Valid @RequestBody CorCountryDTO countryPt) throws URISyntaxException {
        log.debug("REST request to update CorTax : {}", countryPt);
        if (countryPt.getId() == null) {
            return createCountryUsingPOST(networkShortcut, countryPt);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorCountry corCountry = corCountryMapper.DTO2DB(countryPt, corNetwork);
        corCountry = corCountryRepository.save(corCountry);
        CorCountryDTO result = corCountryMapper.DB2DTO(corCountry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("corCountry", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CorCountryDTO> createCountryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "confCountryPt", required = true) @Valid @RequestBody CorCountryDTO countryPt) throws URISyntaxException {
        log.debug("REST request to saveCorContact CorCountry : {}", countryPt);
        if (countryPt.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CorCountry", "idexists", "A new CorCountry cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorCountry corCountry = corCountryMapper.DTO2DB(countryPt, corNetwork);
        corCountry = corCountryRepository.save(corCountry);
        CorCountryDTO result = corCountryMapper.DB2DTO(corCountry);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/configuration/network/dictionary/country/" + result.getId()))
            .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteCountryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CorCountry : {}", id);
        corCountryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("corCountry", id.toString())).build();
    }

    @Override
    public ResponseEntity<List<CorCountryDTO>> getAllCountriesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get CorCountry : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<CorCountry> corCurrencies = corCountryRepository.findByNetwork(corNetwork);
        List<CorCountryDTO> confCurrencyPTS = corCountryMapper.DBs2DTOs(corCurrencies);
        return Optional.ofNullable(confCurrencyPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CorCountryDTO> getCountryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorCountry : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorCountry corTax = corCountryRepository.findOneByIdAndNetwork(id, corNetwork);
        CorCountryDTO confTaxPTS = corCountryMapper.DB2DTO(corTax);
        return Optional.ofNullable(confTaxPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
