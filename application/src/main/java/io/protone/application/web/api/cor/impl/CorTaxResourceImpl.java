package io.protone.application.web.api.cor.impl;

import io.protone.core.repository.CorTaxRepository;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorTax;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.api.cor.CorTaxResource;
import io.protone.web.rest.dto.cor.CorTaxDTO;
import io.protone.web.rest.mapper.CorTaxMapper;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
public class CorTaxResourceImpl implements CorTaxResource {

    private final Logger log = LoggerFactory.getLogger(CorTaxResourceImpl.class);

    @Inject
    private CorTaxRepository corTaxRepository;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorTaxMapper corTaxMapper;

    @Override
    public ResponseEntity<List<CorTaxDTO>> getAllTaxesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CorTax");

        List<CorTax> cORArea = corTaxRepository.findAllByNetwork_Shortcut(networkShortcut, pagable);
        List<CorTaxDTO> corTaxDTOS = corTaxMapper.DBs2DTOs(cORArea);
        return Optional.ofNullable(corTaxDTOS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CorTaxDTO> getTaxUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get CorTax : {}", networkShortcut);

        CorTax corTax = corTaxRepository.findOneByIdAndNetwork_Shortcut(id, networkShortcut);
        CorTaxDTO corTaxPTS = corTaxMapper.DB2DTO(corTax);
        return Optional.ofNullable(corTaxPTS)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CorTaxDTO> updateTaxUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true) @RequestBody CorTaxDTO taxDTO) throws URISyntaxException {
        log.debug("REST request to update CorTax : {}", taxDTO);
        if (taxDTO.getId() == null) {
            return createTaxUsingPOST(networkShortcut, taxDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorTax corTax = corTaxMapper.DTO2DB(taxDTO, corNetwork);
        corTax = corTaxRepository.save(corTax);
        CorTaxDTO result = corTaxMapper.DB2DTO(corTax);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("CorTax", result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CorTaxDTO> createTaxUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true) @RequestBody CorTaxDTO taxDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CorTax : {}", taxDTO);
        if (taxDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CorTax", "idexists", "A new CorTax cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorTax corTax = corTaxMapper.DTO2DB(taxDTO, corNetwork);
        corTax = corTaxRepository.save(corTax);
        CorTaxDTO result = corTaxMapper.DB2DTO(corTax);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/configuration/traffic/dictionary/tax/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("CorTax", result.getId().toString())).body(result);

    }

    @Override
    public ResponseEntity<Void> deleteTaxUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete CorTax : {}", id);
        corTaxRepository.deleteByIdAndNetwork_Shortcut(id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CorTax", id.toString())).build();
    }
}
