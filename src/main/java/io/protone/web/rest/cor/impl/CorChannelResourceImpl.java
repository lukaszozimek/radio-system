package io.protone.web.rest.cor.impl;

import io.protone.service.cor.CorChannelService;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.rest.dto.cor.CorChannelDTO;
import io.protone.web.rest.mapper.CorChannelMapper;
import io.protone.web.rest.cor.CorChannelResource;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
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

import javax.validation.Valid;
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

    private CorChannelMapper customCORChannelMapper;

    private CorNetworkService networkService;

    public CorChannelResourceImpl(CorChannelService channelService, CorChannelMapper customCORChannelMapper, CorNetworkService networkService) {
        this.channelService = channelService;
        this.customCORChannelMapper = customCORChannelMapper;
        this.networkService = networkService;
    }

    @Override
    public ResponseEntity<CorChannelDTO> updateChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "channelDTO", required = true) @Valid @RequestBody CorChannelDTO channelDTO) throws URISyntaxException {
        log.debug("REST request to update CORChannel : {}", channelDTO);
        if (channelDTO.getId() == null) {
            return createChannelUsingPOST(networkShortcut, channelDTO);
        }
        CorNetwork network = networkService.findNetwork(networkShortcut);

        CorChannel corChannel = customCORChannelMapper.DTO2DB(channelDTO, network);
        corChannel = channelService.save(corChannel);
        CorChannelDTO result = customCORChannelMapper.DB2DTO(corChannel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getShortcut().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CorChannelDTO> createChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelDTO", required = true) @Valid @RequestBody CorChannelDTO channelDTO) throws URISyntaxException {
        log.debug("REST request to save CORChannel : {}", channelDTO);
        if (channelDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORChannel", "idexists", "A new cORChannel cannot already have an ID")).body(null);
        }

        CorNetwork network = networkService.findNetwork(networkShortcut);
        CorChannel corChannel = customCORChannelMapper.DTO2DB(channelDTO, network);
        corChannel = channelService.save(corChannel);
        CorChannelDTO result = customCORChannelMapper.DB2DTO(corChannel);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/channel" + result.getShortcut()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getShortcut().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<List<CorChannelDTO>> getAllChannelsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CORChannels");
        List<CorChannel> corChannels = channelService.findAllChannel(networkShortcut, pagable);
        return ResponseEntity.ok()
            .body(customCORChannelMapper.DBs2DTOs(corChannels));
    }

    @Override
    public ResponseEntity<CorChannelDTO> getChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        log.debug("REST request to get CORChannel : {}", channelShortcut);
        CorChannel corChannel = channelService.findChannel(networkShortcut, channelShortcut);
        CorChannelDTO corChannelDTO = customCORChannelMapper.DB2DTO(corChannel);
        return Optional.ofNullable(corChannelDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        log.debug("REST request to delete CORChannel : {}", channelShortcut);
        channelService.deleteChannel(networkShortcut, channelShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORChannel", channelShortcut.toString())).build();

    }
}
