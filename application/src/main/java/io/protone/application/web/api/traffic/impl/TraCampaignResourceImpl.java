package io.protone.application.web.api.traffic.impl;


import io.protone.application.web.api.traffic.TraCampaignResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.traffic.api.dto.TraCampaignDTO;
import io.protone.traffic.domain.TraCampaign;
import io.protone.traffic.mapper.TraCampaignMapper;
import io.protone.traffic.service.TraCampaignService;
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
public class TraCampaignResourceImpl implements TraCampaignResource {
    private final Logger log = LoggerFactory.getLogger(TraCampaignResourceImpl.class);

    @Inject
    private TraCampaignService traCampaignService;

    @Inject
    private CorChannelService corChannelService;

    @Inject
    private TraCampaignMapper traCampaignMapper;

    @Override
    public ResponseEntity<List<TraCampaignDTO>> getAllCampaignsUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraCampaign, for Network: {}", organizationShortcut);
        Slice<TraCampaign> entity = traCampaignService.getAllCampaign(organizationShortcut, channelShortcut, pagable);
        List<TraCampaignDTO> response = traCampaignMapper.DBs2DTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraCampaignDTO> updateCampaignUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                 @ApiParam(value = "traCampaignDTO", required = true) @Valid @RequestBody TraCampaignDTO traCampaignDTO) throws URISyntaxException {
        log.debug("REST request to update TraCampaign : {}, for Network: {}", traCampaignDTO, organizationShortcut);
        if (traCampaignDTO.getId() == null) {
            return createCampaignUsingPOST(organizationShortcut, channelShortcut, traCampaignDTO);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        TraCampaign crmAccount = traCampaignMapper.DTO2DB(traCampaignDTO, corChannel);
        TraCampaign entity = traCampaignService.saveCampaign(crmAccount);
        TraCampaignDTO response = traCampaignMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraCampaignDTO> createCampaignUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                  @ApiParam(value = "traCampaignDTO", required = true) @Valid @RequestBody TraCampaignDTO traCampaignDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact TraCampaign : {}, for Network: {}", traCampaignDTO, organizationShortcut);
        if (traCampaignDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraCampaign", "idexists", "A new TraCampaign cannot already have an ID")).body(null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        TraCampaign crmAccount = traCampaignMapper.DTO2DB(traCampaignDTO, corChannel);
        TraCampaign entity = traCampaignService.saveCampaign(crmAccount);
        TraCampaignDTO response = traCampaignMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/traffic/campaign/" + response.getName()))
                .body(response);

    }

    @Override
    public ResponseEntity<Void> deleteCampaignUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete TraCampaign : {}, for Network: {}", shortName, organizationShortcut);
        traCampaignService.deleteCampaign(shortName, organizationShortcut, channelShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traOrder", shortName.toString())).build();
    }

    @Override
    public ResponseEntity<TraCampaignDTO> getCampaignUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                              @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get TraCampaign : {}, for Network: {}", shortName, organizationShortcut);
        TraCampaign entity = traCampaignService.getCampaign(shortName, organizationShortcut, channelShortcut);
        TraCampaignDTO response = traCampaignMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<TraCampaignDTO>> getAllCustomerCampaignsUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                                @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraCampaign, for TraCustomer: {} and Network: {}", customerShortcut, organizationShortcut);
        Slice<TraCampaign> entity = traCampaignService.getCustomerCampaing(customerShortcut, organizationShortcut, channelShortcut, pagable);
        List<TraCampaignDTO> response = traCampaignMapper.DBs2DTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }
}
