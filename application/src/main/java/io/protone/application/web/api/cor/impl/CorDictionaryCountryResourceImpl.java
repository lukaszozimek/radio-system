package io.protone.application.web.api.cor.impl;

import io.protone.application.web.api.cor.CorDictionaryCountryResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.api.dto.CorCountryDTO;
import io.protone.core.domain.CorCountry;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorCountryMapper;
import io.protone.core.repository.CorCountryRepository;
import io.protone.core.service.CorNetworkService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    public ResponseEntity<CorCountryDTO> updateCountryUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "confCountryPt", required = true) @Valid @RequestBody CorCountryDTO countryPt) throws URISyntaxException {
        log.debug("REST request to update CorTax : {}", countryPt);
        if (countryPt.getId() == null) {
            return createCountryUsingPOST(organizationShortcut, countryPt);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        CorCountry corCountry = corCountryMapper.DTO2DB(countryPt, corNetwork);
        corCountry = corCountryRepository.save(corCountry);
        CorCountryDTO result = corCountryMapper.DB2DTO(corCountry);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("corCountry", result.getId().toString()))
                .body(result);
    }

    @Override
    public ResponseEntity<CorCountryDTO> createCountryUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "confCountryPt", required = true) @Valid @RequestBody CorCountryDTO countryPt) throws URISyntaxException {
        log.debug("REST request to saveCorContact CorCountry : {}", countryPt);
        if (countryPt.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CorCountry", "idexists", "A new CorCountry cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        CorCountry corCountry = corCountryMapper.DTO2DB(countryPt, corNetwork);
        corCountry = corCountryRepository.save(corCountry);
        CorCountryDTO result = corCountryMapper.DB2DTO(corCountry);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/configuration/organization/dictionary/country/" + result.getId()))
                .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteCountryUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CorCountry : {}", id);
        corCountryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("corCountry", id.toString())).build();
    }

    @Override
    public ResponseEntity<List<CorCountryDTO>> getAllCountriesUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get CorCountry : {}", organizationShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);

        Slice<CorCountry> corCurrencies = corCountryRepository.findSliceByNetwork(corNetwork, pagable);
        List<CorCountryDTO> confCurrencyPTS = corCountryMapper.DBs2DTOs(corCurrencies.getContent());
        return Optional.ofNullable(confCurrencyPTS)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(corCurrencies),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(PaginationUtil.generateSliceHttpHeaders(corCurrencies), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CorCountryDTO> getCountryUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorCountry : {}", organizationShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);

        CorCountry corTax = corCountryRepository.findOneByIdAndNetwork(id, corNetwork);
        CorCountryDTO confTaxPTS = corCountryMapper.DB2DTO(corTax);
        return Optional.ofNullable(confTaxPTS)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
