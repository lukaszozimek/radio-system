package io.protone.application.web.api.traffic.impl;


import io.protone.application.web.api.traffic.TraCompanyResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorOrganization;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorOrganizationService;
import io.protone.traffic.api.dto.TraCompanyDTO;
import io.protone.traffic.domain.TraCompany;
import io.protone.traffic.mapper.TraCompanyMapper;
import io.protone.traffic.service.TraCompanyService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
public class TraCompanyResourceImpl implements TraCompanyResource {
    private final Logger log = LoggerFactory.getLogger(TraCompanyResourceImpl.class);

    @Inject
    private TraCompanyMapper traCompanyMapper;

    @Inject
    private CorChannelService corChannelService;

    @Inject
    private TraCompanyService traCompanyService;


    @Override
    public ResponseEntity<List<TraCompanyDTO>> getAllCompanyUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                     @ApiParam(value = "pagable", required = true) Pageable pagable) {


        log.debug("REST request to get all TraCompanyDTO, for Network: {}", organizationShortcut);
        Slice<TraCompany> entity = traCompanyService.getAllCompany(organizationShortcut, channelShortcut, pagable);
        List<TraCompanyDTO> response = traCompanyMapper.DBs2DTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }


    @Override
    public ResponseEntity<TraCompanyDTO> getCompanyUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                            @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraCompanyDTO : {}, for Network: {}", id, organizationShortcut);
        TraCompany entity = traCompanyService.getCompany(id, organizationShortcut, channelShortcut);
        TraCompanyDTO response = traCompanyMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraCompanyDTO> updateCompanyUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                               @ApiParam(value = "discountPT", required = true) @RequestBody TraCompanyDTO traCompanyDTO) throws URISyntaxException {
        log.debug("REST request to update TraCompanyDTO : {}, for Network: {}", traCompanyDTO, organizationShortcut);
        if (traCompanyDTO.getId() == null) {
            return createCompanyUsingPOST(organizationShortcut, channelShortcut, traCompanyDTO);
        }
        CorChannel corOrganization = corChannelService.findChannel(organizationShortcut, channelShortcut);
        TraCompany crmAccount = traCompanyMapper.DTO2DB(traCompanyDTO, corOrganization);
        TraCompany entity = traCompanyService.saveCompany(crmAccount);
        TraCompanyDTO response = traCompanyMapper.DB2DTO(entity);
        return ResponseEntity.ok()
                .body(response);

    }

    @Override
    public ResponseEntity<TraCompanyDTO> createCompanyUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                @ApiParam(value = "traCompanyDTO", required = true) @RequestBody TraCompanyDTO traCompanyDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact TraCustomer : {}, for Network: {}", traCompanyDTO, organizationShortcut);
        if (traCompanyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraCustomer", "idexists", "A new TraCustomer cannot already have an ID")).body(null);
        }

        CorChannel corOrganization = corChannelService.findChannel(organizationShortcut, channelShortcut);
        TraCompany crmAccount = traCompanyMapper.DTO2DB(traCompanyDTO, corOrganization);
        TraCompany entity = traCompanyService.saveCompany(crmAccount);
        TraCompanyDTO response = traCompanyMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/configuration/traffic/company/" + traCompanyDTO.getId()))
                .body(response);
    }


    @Override
    public ResponseEntity<Void> deleteCompanyUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                         @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraDiscount : {}", id);
        traCompanyService.deleteCompany(id, organizationShortcut, channelShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("TraDiscount", id.toString())).build();


    }


}
