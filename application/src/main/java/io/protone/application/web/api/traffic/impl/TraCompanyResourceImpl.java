package io.protone.application.web.api.traffic.impl;


import io.protone.application.web.api.traffic.TraCompanyResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.traffic.api.dto.TraCompanyDTO;
import io.protone.traffic.domain.TraCompany;
import io.protone.traffic.mapper.TraCompanyMapper;
import io.protone.traffic.service.TraCompanyService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
public class TraCompanyResourceImpl implements TraCompanyResource {
    private final Logger log = LoggerFactory.getLogger(TraCompanyResourceImpl.class);

    @Autowired
    private TraCompanyMapper traCompanyMapper;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private TraCompanyService traCompanyService;


    @Override
    public ResponseEntity<List<TraCompanyDTO>> getAllCompanyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "pagable", required = true) Pageable pagable) {


        log.debug("REST request to get all TraCompanyDTO, for Network: {}", networkShortcut);
        List<TraCompany> entity = traCompanyService.getAllCompany(networkShortcut, pagable);
        List<TraCompanyDTO> response = traCompanyMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @Override
    public ResponseEntity<TraCompanyDTO> getCompanyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraCompanyDTO : {}, for Network: {}", id, networkShortcut);
        TraCompany entity = traCompanyService.getCompany(id, networkShortcut);
        TraCompanyDTO response = traCompanyMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraCompanyDTO> updateCompanyUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "discountPT", required = true) @RequestBody TraCompanyDTO traCompanyDTO) throws URISyntaxException {
        log.debug("REST request to update TraCompanyDTO : {}, for Network: {}", traCompanyDTO, networkShortcut);
        if (traCompanyDTO.getId() == null) {
            return createCompanyUsingPOST(networkShortcut, traCompanyDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraCompany crmAccount = traCompanyMapper.DTO2DB(traCompanyDTO, corNetwork);
        TraCompany entity = traCompanyService.saveCompany(crmAccount);
        TraCompanyDTO response = traCompanyMapper.DB2DTO(entity);
        return ResponseEntity.ok()
                .body(response);

    }

    @Override
    public ResponseEntity<TraCompanyDTO> createCompanyUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "traCompanyDTO", required = true) @RequestBody TraCompanyDTO traCompanyDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact TraCustomer : {}, for Network: {}", traCompanyDTO, networkShortcut);
        if (traCompanyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraCustomer", "idexists", "A new TraCustomer cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraCompany crmAccount = traCompanyMapper.DTO2DB(traCompanyDTO, corNetwork);
        TraCompany entity = traCompanyService.saveCompany(crmAccount);
        TraCompanyDTO response = traCompanyMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/configuration/traffic/company/" + traCompanyDTO.getId()))
                .body(response);
    }


    @Override
    public ResponseEntity<Void> deleteCompanyUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraDiscount : {}", id);
        traCompanyService.deleteCompany(id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("TraDiscount", id.toString())).build();


    }


}
