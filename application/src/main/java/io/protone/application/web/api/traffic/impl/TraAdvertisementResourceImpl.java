package io.protone.application.web.api.traffic.impl;


import io.protone.application.web.api.traffic.TraAdvertisementResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.traffic.api.dto.TraAdvertisementDTO;
import io.protone.traffic.domain.TraAdvertisement;
import io.protone.traffic.mapper.TraAdvertisementMapper;
import io.protone.traffic.service.TraAdvertisementService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class TraAdvertisementResourceImpl implements TraAdvertisementResource {
    private final Logger log = LoggerFactory.getLogger(TraAdvertisementResourceImpl.class);

    @Inject
    private TraAdvertisementService traAdvertisementService;

    @Inject
    private TraAdvertisementMapper traAdvertisementMapper;

    @Inject
    private CorChannelService corChannelService;

    @Override
    public ResponseEntity<TraAdvertisementDTO> updateAdvertisementUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "traAdvertisementDTO", required = true) @RequestBody @Valid TraAdvertisementDTO traAdvertisementDTO) throws URISyntaxException, IOException {
        log.debug("REST request to update TraAdvertisement : {}, for Network: {}", traAdvertisementDTO, organizationShortcut);
        if (traAdvertisementDTO.getId() == null) {
            return createAdvertisementUsingPOST(organizationShortcut, channelShortcut, traAdvertisementDTO, null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        TraAdvertisement traAdvertisement = traAdvertisementMapper.DTO2DB(traAdvertisementDTO, corChannel);
        TraAdvertisement entity = traAdvertisementService.saveAdvertisement(traAdvertisement);
        TraAdvertisementDTO response = traAdvertisementMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraAdvertisementDTO> createAdvertisementUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "traAdvertisementDTO", required = true) @RequestPart("traAdvertisementDTO") @Valid TraAdvertisementDTO traAdvertisementDTO,
                                                                            @ApiParam(value = "commercial") @RequestPart("commercial") MultipartFile commercial) throws URISyntaxException, IOException {


        log.debug("REST request to saveCorContact TraAdvertisement : {}, for Network: {}", traAdvertisementDTO, organizationShortcut);
        if (traAdvertisementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraAdvertisement", "idexists", "A new TraAdvertisement cannot already have an ID")).body(null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        TraAdvertisement traAdvertisement = traAdvertisementMapper.DTO2DB(traAdvertisementDTO, corChannel);
        TraAdvertisement entity = traAdvertisementService.saveAdvertisement(traAdvertisement);
        TraAdvertisementDTO response = traAdvertisementMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/traffic/advertisement/" + response.getId()))
                .body(response);
    }

    @Override
    public ResponseEntity<List<TraAdvertisementDTO>> getAllAdvertisementsUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                  @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraAdvertisement, for Network: {}", organizationShortcut);
        Slice<TraAdvertisement> entity = traAdvertisementService.getAllAdvertisement(organizationShortcut, channelShortcut, pagable);
        List<TraAdvertisementDTO> response = traAdvertisementMapper.DBs2DTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteAdvertisementUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                               @ApiParam(value = "id", required = true) @PathVariable("id") Long idx) {
        log.debug("REST request to delete TraAdvertisement : {}, for Network: {}", idx, organizationShortcut);
        traAdvertisementService.deleteAdvertisement(idx, organizationShortcut, channelShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("TraAdvertisement", idx.toString())).build();
    }

    @Override
    public ResponseEntity<TraAdvertisementDTO> getAdvertisementUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long idx) {
        log.debug("REST request to get TraAdvertisement : {}, for Network: {}", idx, organizationShortcut);
        TraAdvertisement entity = traAdvertisementService.getAdvertisement(idx, organizationShortcut, channelShortcut);
        TraAdvertisementDTO response = traAdvertisementMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<TraAdvertisementDTO>> getAllCustomersAdvertismentsUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                          @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                                          @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraAdvertisement, for TraCustomer: {} and Network: {}", customerShortcut, organizationShortcut);
        List<TraAdvertisement> entity = traAdvertisementService.getCustomerAdvertisements(customerShortcut, organizationShortcut, channelShortcut, pagable);
        List<TraAdvertisementDTO> response = traAdvertisementMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
