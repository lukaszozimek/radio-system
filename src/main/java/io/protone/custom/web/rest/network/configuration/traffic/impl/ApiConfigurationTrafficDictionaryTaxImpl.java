package io.protone.custom.web.rest.network.configuration.traffic.impl;

import io.protone.repository.cor.CorTaxRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.ConfTaxPT;
import io.protone.web.rest.mapper.CorTaxMapper;
import io.protone.custom.web.rest.network.configuration.traffic.ApiConfigurationTrafficDictionaryTax;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorTax;
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
public class ApiConfigurationTrafficDictionaryTaxImpl implements ApiConfigurationTrafficDictionaryTax {

    private final Logger log = LoggerFactory.getLogger(ApiConfigurationTrafficDictionaryTaxImpl.class);
    @Inject
    private CorTaxRepository corTaxRepository;
    @Inject
    private CorNetworkService corNetworkService;
    @Inject
    private CorTaxMapper customCorTaxMapper;

    @Override
    public ResponseEntity<List<ConfTaxPT>> getAllTaxesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CorTax");
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        List<CorTax> cORArea = corTaxRepository.findByNetwork(corNetwork);
        List<ConfTaxPT> confTaxPTS = customCorTaxMapper.DBs2DTOs(cORArea);
        return Optional.ofNullable(confTaxPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfTaxPT> getTaxUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorTax : {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorTax corTax = corTaxRepository.findOneByIdAndNetwork(id, corNetwork);
        ConfTaxPT confTaxPTS = customCorTaxMapper.DB2DTO(corTax);
        return Optional.ofNullable(confTaxPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ConfTaxPT> updateTaxUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true) @RequestBody ConfTaxPT taxDTO) {
        log.debug("REST request to update CorTax : {}", taxDTO);
        if (taxDTO.getId() == null) {
            return createTaxUsingPOST(networkShortcut, taxDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorTax corTax = customCorTaxMapper.DTO2DB(taxDTO, corNetwork);
        corTax = corTaxRepository.save(corTax);
        ConfTaxPT result = customCorTaxMapper.DB2DTO(corTax);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORArea", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<ConfTaxPT> createTaxUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true) @RequestBody ConfTaxPT taxDTO) {
        log.debug("REST request to save CorTax : {}", taxDTO);
        if (taxDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORArea", "idexists", "A new cORArea cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorTax corTax = customCorTaxMapper.DTO2DB(taxDTO, corNetwork);
        corTax = corTaxRepository.save(corTax);
        ConfTaxPT result = customCorTaxMapper.DB2DTO(corTax);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> deleteTaxUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CorTax : {}", id);
        corTaxRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CorTax", id.toString())).build();
    }
}
