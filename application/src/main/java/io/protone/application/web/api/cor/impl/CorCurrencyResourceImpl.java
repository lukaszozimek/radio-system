package io.protone.application.web.api.cor.impl;

import io.protone.application.web.api.cor.CorCurrencyResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.api.dto.CorCurrencyDTO;
import io.protone.core.domain.CorCurrency;
import io.protone.core.domain.CorOrganization;
import io.protone.core.mapper.CorCurrencyMapper;
import io.protone.core.repository.CorCurrencyRepository;
import io.protone.core.service.CorOrganizationService;
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
public class CorCurrencyResourceImpl implements CorCurrencyResource {

    private final Logger log = LoggerFactory.getLogger(CorCurrencyResourceImpl.class);

    @Inject
    private CorCurrencyRepository corCurrencyRepository;

    @Inject
    private CorOrganizationService corOrganizationService;

    @Inject
    private CorCurrencyMapper corCurrencyMapper;


    @Override
    public ResponseEntity<List<CorCurrencyDTO>> getAllCurrencyUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CorCurrency");
        Slice<CorCurrency> corCurrencies = corCurrencyRepository.findSliceByOrganization_Shortcut(organizationShortcut, pagable);
        List<CorCurrencyDTO> corCurrencyDTOS = corCurrencyMapper.DBs2DTOs(corCurrencies.getContent());
        return Optional.ofNullable(corCurrencyDTOS)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(corCurrencies),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(corCurrencies),HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CorCurrencyDTO> getCurrencyUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorCurrency : {}", organizationShortcut);

        CorCurrency corCurrency = corCurrencyRepository.findOneByIdAndOrganization_Shortcut(id, organizationShortcut);
        CorCurrencyDTO corCurrencyDTO = corCurrencyMapper.DB2DTO(corCurrency);
        return Optional.ofNullable(corCurrencyDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CorCurrencyDTO> updateCurrencyUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "taxDTO", required = true)
    @Valid @RequestBody CorCurrencyDTO currencyDTO) throws URISyntaxException {
        log.debug("REST request to update CorCurrency : {}", currencyDTO);
        if (currencyDTO.getId() == null) {
            return createCurrencyUsingPOST(organizationShortcut, currencyDTO);
        }
        CorOrganization organization = corOrganizationService.findOrganization(organizationShortcut);
        CorCurrency corCurrency = corCurrencyMapper.DTO2DB(currencyDTO, organization);
        corCurrency = corCurrencyRepository.save(corCurrency);
        CorCurrencyDTO result = corCurrencyMapper.DB2DTO(corCurrency);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("corCurrency", result.getId().toString()))
                .body(result);
    }

    @Override
    public ResponseEntity<CorCurrencyDTO> createCurrencyUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "taxDTO", required = true)
    @Valid @RequestBody CorCurrencyDTO corCurrencyDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CorCurrency : {}", corCurrencyDTO);
        if (corCurrencyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("corCurrency", "idexists", "A new corCurrency cannot already have an ID")).body(null);
        }
        CorOrganization organization = corOrganizationService.findOrganization(organizationShortcut);
        CorCurrency corCurrency = corCurrencyMapper.DTO2DB(corCurrencyDTO, organization);
        corCurrency = corCurrencyRepository.save(corCurrency);
        CorCurrencyDTO result = corCurrencyMapper.DB2DTO(corCurrency);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/configuration/traffic/dictionary/currency/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("corCurrency", result.getId().toString())).body(result);
    }

    @Override
    public ResponseEntity<Void> deleteCurrencyUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String
                                                                  organizationShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CorCurrency : {}", id);
        corCurrencyRepository.deleteByIdAndOrganization_Shortcut(id, organizationShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CorTax", id.toString())).build();
    }
}
