package io.protone.application.web.api.cor.impl;


import io.protone.application.web.api.cor.CorChannelResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.api.dto.CorChannelDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorChannelMapper;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.swagger.annotations.ApiParam;
import org.apache.tika.exception.TikaException;
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
import org.xml.sax.SAXException;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Created by lukaszozimek on 16.01.2017.
 */
@RestController
public class CorChannelResourceImpl implements CorChannelResource {
    private static final String ENTITY_NAME = "corChannel";
    private final Logger log = LoggerFactory.getLogger(CorChannelResourceImpl.class);
    private CorChannelService channelService;

    private CorChannelMapper corChannelMapper;

    private CorNetworkService networkService;

    public CorChannelResourceImpl(CorChannelService channelService, CorChannelMapper corChannelMapper, CorNetworkService networkService) {
        this.channelService = channelService;
        this.corChannelMapper = corChannelMapper;
        this.networkService = networkService;
    }

    @Override
    public ResponseEntity<CorChannelDTO> updateChannelUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                               @ApiParam(value = "channelDTO", required = true) @Valid @RequestBody CorChannelDTO channelDTO) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to update CORChannel : {}", channelDTO);
        if (channelDTO.getId() == null) {
            return createChannelUsingPOST(organizationShortcut, channelDTO, null);
        }
        CorNetwork network = networkService.findNetwork(organizationShortcut);

        CorChannel corChannel = corChannelMapper.DTO2DB(channelDTO, network);
        corChannel = channelService.save(corChannel);
        CorChannelDTO result = corChannelMapper.DB2DTO(corChannel);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getShortcut().toString()))
                .body(result);
    }

    @Override
    public ResponseEntity<CorChannelDTO> updateChannelWithLogoUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "channelDTO", required = true) @Valid @RequestPart CorChannelDTO channelDTO,
                                                                        @ApiParam(value = "logo", required = true) @RequestPart("logo") MultipartFile logo) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to update CORChannel : {}", channelDTO);
        if (channelDTO.getId() == null) {
            return createChannelUsingPOST(organizationShortcut, channelDTO, logo);
        }
        CorNetwork network = networkService.findNetwork(organizationShortcut);

        CorChannel corChannel = corChannelMapper.DTO2DB(channelDTO, network);
        corChannel = channelService.save(corChannel, logo);
        CorChannelDTO result = corChannelMapper.DB2DTO(corChannel);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getShortcut().toString()))
                .body(result);
    }

    @Override
    public ResponseEntity<CorChannelDTO> createChannelUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                @ApiParam(value = "channelDTO", required = true) @Valid @RequestPart("channelDTO") CorChannelDTO channelDTO,
                                                                @ApiParam(value = "logo", required = true) @RequestPart("logo") MultipartFile logo) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to saveCorContact CORChannel : {}", channelDTO);
        if (channelDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORChannel", "idexists", "A new cORChannel cannot already have an ID")).body(null);
        }

        CorNetwork network = networkService.findNetwork(organizationShortcut);
        CorChannel corChannel = corChannelMapper.DTO2DB(channelDTO, network);
        corChannel = channelService.save(corChannel, logo);
        CorChannelDTO result = corChannelMapper.DB2DTO(corChannel);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/channel" + result.getShortcut()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getShortcut().toString()))
                .body(result);
    }

    @Override
    public ResponseEntity<List<CorChannelDTO>> getAllChannelsUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CORChannels");
        Slice<CorChannel> corChannels = channelService.findAllChannel(organizationShortcut, pagable);
        return ResponseEntity.ok().headers(PaginationUtil.generateSliceHttpHeaders(corChannels))
                .body(corChannelMapper.DBs2DTOs(corChannels.getContent()));
    }

    @Override
    public ResponseEntity<CorChannelDTO> getChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        log.debug("REST request to get CORChannel : {}", channelShortcut);
        CorChannel corChannel = channelService.findChannel(organizationShortcut, channelShortcut);
        CorChannelDTO corChannelDTO = corChannelMapper.DB2DTO(corChannel);
        return Optional.ofNullable(corChannelDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteChannelUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        log.debug("REST request to delete CORChannel : {}", channelShortcut);
        channelService.deleteChannel(organizationShortcut, channelShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORChannel", channelShortcut.toString())).build();

    }
}
