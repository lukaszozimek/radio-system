package io.protone.application.web.api.cor.impl;

import io.protone.core.repository.CorCurrencyRepository;
import io.protone.domain.CorCurrency;
import io.protone.domain.CorNetwork;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.api.cor.CorCurrencyResource;
import io.protone.web.rest.dto.cor.CorCurrencyDTO;
import io.protone.web.rest.mapper.CorCurrencyMapper;
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
public class CorCurrencyResourceImpl implements CorCurrencyResource {

    private final Logger log = LoggerFactory.getLogger(CorCurrencyResourceImpl.class);

    @Inject
    private CorCurrencyRepository corCurrencyRepository;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorCurrencyMapper corCurrencyMapper;


    @Override
    public ResponseEntity<List<CorCurrencyDTO>> getAllCurrencyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CorCurrency");
        List<CorCurrency> corCurrencies = corCurrencyRepository.findAllByNetwork_Shortcut(networkShortcut, pagable);
        List<CorCurrencyDTO> corCurrencyDTOS = corCurrencyMapper.DBs2DTOs(corCurrencies);
        return Optional.ofNullable(corCurrencyDTOS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CorCurrencyDTO> getCurrencyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorCurrency : {}", networkShortcut);

        CorCurrency corCurrency = corCurrencyRepository.findOneByIdAndNetwork_Shortcut(id, networkShortcut);
        CorCurrencyDTO corCurrencyDTO = corCurrencyMapper.DB2DTO(corCurrency);
        return Optional.ofNullable(corCurrencyDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CorCurrencyDTO> updateCurrencyUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true)
    @Valid @RequestBody CorCurrencyDTO currencyDTO) throws URISyntaxException {
        log.debug("REST request to update CorCurrency : {}", currencyDTO);
        if (currencyDTO.getId() == null) {
            return createCurrencyUsingPOST(networkShortcut, currencyDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorCurrency corCurrency = corCurrencyMapper.DTO2DB(currencyDTO, corNetwork);
        corCurrency = corCurrencyRepository.save(corCurrency);
        CorCurrencyDTO result = corCurrencyMapper.DB2DTO(corCurrency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("corCurrency", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CorCurrencyDTO> createCurrencyUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true)
    @Valid @RequestBody CorCurrencyDTO corCurrencyDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CorCurrency : {}", corCurrencyDTO);
        if (corCurrencyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("corCurrency", "idexists", "A new corCurrency cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorCurrency corCurrency = corCurrencyMapper.DTO2DB(corCurrencyDTO, corNetwork);
        corCurrency = corCurrencyRepository.save(corCurrency);
        CorCurrencyDTO result = corCurrencyMapper.DB2DTO(corCurrency);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/configuration/traffic/dictionary/currency/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("corCurrency", result.getId().toString())).body(result);
    }

    @Override
    public ResponseEntity<Void> deleteCurrencyUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String
                                                              networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CorCurrency : {}", id);
        corCurrencyRepository.deleteByIdAndNetwork_Shortcut(id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CorTax", id.toString())).build();
    }
}
