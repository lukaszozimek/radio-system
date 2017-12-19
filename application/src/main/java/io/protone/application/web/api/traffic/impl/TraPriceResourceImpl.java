package io.protone.application.web.api.traffic.impl;


import io.protone.application.web.api.traffic.TraPriceResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.traffic.api.dto.TraPriceDTO;
import io.protone.traffic.domain.TraPrice;
import io.protone.traffic.mapper.TraPriceMapper;
import io.protone.traffic.service.TraPriceService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
public class TraPriceResourceImpl implements TraPriceResource {
    private final Logger log = LoggerFactory.getLogger(TraPriceResourceImpl.class);

    @Autowired
    private TraPriceMapper traPriceMapper;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private TraPriceService traPriceService;


    @Override
    public ResponseEntity<List<TraPriceDTO>> getAllPriceUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                 @ApiParam(value = "pagable", required = true) Pageable pagable) {


        log.debug("REST request to get all TraPriceDTO, for Network: {}", organizationShortcut);
        Slice<TraPrice> entity = traPriceService.getAllPrice(organizationShortcut, pagable);
        List<TraPriceDTO> response = traPriceMapper.DBs2DTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }


    @Override
    public ResponseEntity<TraPriceDTO> getPriceUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraPriceDTO : {}, for Network: {}", id, organizationShortcut);
        TraPrice entity = traPriceService.getPrice(id, organizationShortcut);
        TraPriceDTO response = traPriceMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraPriceDTO> updatePriceUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                           @ApiParam(value = "discountPT", required = true) @RequestBody TraPriceDTO traPriceDTO) throws URISyntaxException {
        log.debug("REST request to update TraPriceDTO : {}, for Network: {}", traPriceDTO, organizationShortcut);
        if (traPriceDTO.getId() == null) {
            return createPriceUsingPOST(organizationShortcut, traPriceDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        TraPrice crmAccount = traPriceMapper.DTO2DB(traPriceDTO, corNetwork);
        TraPrice entity = traPriceService.savePrice(crmAccount);
        TraPriceDTO response = traPriceMapper.DB2DTO(entity);
        return ResponseEntity.ok()
                .body(response);

    }

    @Override
    public ResponseEntity<TraPriceDTO> createPriceUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                            @ApiParam(value = "traPriceDTO", required = true) @RequestBody TraPriceDTO traPriceDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact TraPrice : {}, for Network: {}", traPriceDTO, organizationShortcut);
        if (traPriceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraPrice", "idexists", "A new TraPrice cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);
        TraPrice crmAccount = traPriceMapper.DTO2DB(traPriceDTO, corNetwork);
        TraPrice entity = traPriceService.savePrice(crmAccount);
        TraPriceDTO response = traPriceMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/configuration/traffic/price/" + traPriceDTO.getId()))
                .body(response);
    }


    @Override
    public ResponseEntity<Void> deletePriceUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraPrice : {}", id);
        traPriceService.deletePrice(id, organizationShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("TraPrice", id.toString())).build();


    }


}
