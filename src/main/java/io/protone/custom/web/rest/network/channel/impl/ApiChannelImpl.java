package io.protone.custom.web.rest.network.channel.impl;

import io.protone.custom.service.CorChannelService;
import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.CoreChannelPT;
import io.protone.custom.service.mapper.CustomCORChannelMapper;
import io.protone.custom.web.rest.network.channel.ApiChannel;
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

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Created by lukaszozimek on 16.01.2017.
 */
@RestController
public class ApiChannelImpl implements ApiChannel {
    private final Logger log = LoggerFactory.getLogger(ApiChannelImpl.class);

    @Inject
    private CorChannelService channelService;

    @Inject
    private CustomCORChannelMapper customCORChannelMapper;

    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<CoreChannelPT> updateChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "channelDTO", required = true) @RequestBody CoreChannelPT channelDTO) {
        log.debug("REST request to update CORChannel : {}", channelDTO);
        if (channelDTO.getId() == null) {
            return createChannelUsingPOST(networkShortcut, channelDTO);
        }
        CorChannel corChannel = customCORChannelMapper.DTO2DB(channelDTO);
        CorNetwork network = networkService.findNetwork(networkShortcut);
        corChannel.setNetwork(network);
        corChannel = channelService.save(corChannel);
        CoreChannelPT result = customCORChannelMapper.DB2DTO(corChannel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORChannel", channelDTO.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<CoreChannelPT> createChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelDTO", required = true) @RequestBody CoreChannelPT channelDTO) {
        log.debug("REST request to save CORChannel : {}", channelDTO);
        if (channelDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORChannel", "idexists", "A new cORChannel cannot already have an ID")).body(null);
        }

        CorChannel corChannel = customCORChannelMapper.DTO2DB(channelDTO);
        CorNetwork network = networkService.findNetwork(networkShortcut);
        corChannel.setNetwork(network);
        corChannel = channelService.save(corChannel);
        CoreChannelPT result = customCORChannelMapper.DB2DTO(corChannel);
        return ResponseEntity.ok()
            .body(result);
    }

    @Override
    public ResponseEntity<List<CoreChannelPT>> getAllChannelsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CORChannels");
        List<CorChannel> corChannels = channelService.findAllChannel();
        return ResponseEntity.ok()
            .body(customCORChannelMapper.DBs2DTOs(corChannels));
    }

    @Override
    public ResponseEntity<CoreChannelPT> getChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        log.debug("REST request to get CORChannel : {}", channelShortcut);
        CorChannel corChannel = channelService.findChannel(networkShortcut, channelShortcut);
        CoreChannelPT corChannelDTO = customCORChannelMapper.DB2DTO(corChannel);
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
