package io.protone.custom.web.rest.network.configuration.traffic.impl;

import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.ConfCurrencyPT;
import io.protone.service.mapper.CorCurrencyMapper;
import io.protone.custom.web.rest.network.configuration.traffic.ApiConfigurationTrafficDictionaryCurrency;
import io.protone.domain.CorCurrency;
import io.protone.domain.CorNetwork;
import io.protone.repository.custom.CustomCorCurrencyRepository;
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
public class ApiConfigurationTrafficDictionaryCurrencyImpl implements ApiConfigurationTrafficDictionaryCurrency {

    private final Logger log = LoggerFactory.getLogger(ApiConfigurationTrafficDictionaryCurrencyImpl.class);

    @Inject
    private CustomCorCurrencyRepository corCurrencyRepository;
    @Inject
    private CorNetworkService corNetworkService;
    @Inject
    private CorCurrencyMapper customCorCurrencyMapper;


    @Override
    public ResponseEntity<List<ConfCurrencyPT>> getAllCurrencyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CorCurrency");
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<CorCurrency> corCurrencies = corCurrencyRepository.findByNetwork(corNetwork);
        List<ConfCurrencyPT> confCurrencyPTS = customCorCurrencyMapper.DBs2DTOs(corCurrencies);
        return Optional.ofNullable(confCurrencyPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfCurrencyPT> getCurrencyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorCurrency : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorCurrency corCurrency = corCurrencyRepository.findOneByIdAndNetwork(id, corNetwork);
        ConfCurrencyPT confCurrencyPT = customCorCurrencyMapper.DB2DTO(corCurrency);
        return Optional.ofNullable(confCurrencyPT)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfCurrencyPT> updateCurrencyUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true) @RequestBody ConfCurrencyPT taxDTO) {
        log.debug("REST request to update CorCurrency : {}", taxDTO);
        if (taxDTO.getId() == null) {
            return createCurrencyUsingPOST(networkShortcut, taxDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorCurrency corCurrency = customCorCurrencyMapper.DTO2DB(taxDTO);
        corCurrency.setNetwork(corNetwork);
        corCurrency = corCurrencyRepository.save(corCurrency);
        ConfCurrencyPT result = customCorCurrencyMapper.DB2DTO(corCurrency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("corCurrency", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfCurrencyPT> createCurrencyUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true) @RequestBody ConfCurrencyPT taxDTO) {
        log.debug("REST request to save CorCurrency : {}", taxDTO);
        if (taxDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORArea", "idexists", "A new cORArea cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorCurrency corCurrency = customCorCurrencyMapper.DTO2DB(taxDTO);
        corCurrency.setNetwork(corNetwork);
        corCurrency = corCurrencyRepository.save(corCurrency);
        ConfCurrencyPT result = customCorCurrencyMapper.DB2DTO(corCurrency);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deleteCurrencyUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CorCurrency : {}", id);
        corCurrencyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CorTax", id.toString())).build();
    }
}
