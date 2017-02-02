package io.protone.custom.web.rest.network.channel.impl;

import io.protone.custom.service.ChannelService;
import io.protone.custom.service.dto.CoreChannelPT;
import io.protone.custom.web.rest.network.channel.ApiChannel;
import io.protone.domain.CORChannel;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private ChannelService channelService;

    @Inject
    private CustomCORChannelMapper customCORChannelMapper;

    @Override
    public ResponseEntity<CoreChannelPT> updateChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelDTO", required = true) @RequestBody CoreChannelPT channelDTO) {
        log.debug("REST request to update CORChannel : {}", channelDTO);
        if (channelDTO.getId() == null) {
            return createChannelUsingPOST(networkShortcut, channelDTO);
        }
        CORChannel cORChannel = customCORChannelMapper.cORChannelDTOToCORChannel(channelDTO);
        cORChannel = channelService.save(cORChannel);
        CoreChannelPT result = customCORChannelMapper.cORChannelToCORChannelDTO(cORChannel);
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
        CORChannel cORChannel = customCORChannelMapper.cORChannelDTOToCORChannel(channelDTO);
        cORChannel = channelService.save(cORChannel);
        CoreChannelPT result = customCORChannelMapper.cORChannelToCORChannelDTO(cORChannel);
        return ResponseEntity.ok()
            .body(result);
    }

    @Override
    public ResponseEntity<List<CoreChannelPT>> getAllChannelsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CORChannels");
        List<CORChannel> cORChannels = channelService.findAllChannel();
        return ResponseEntity.ok()
            .body(customCORChannelMapper.cORChannelsToCORChannelDTOs(cORChannels));
    }

    @Override
    public ResponseEntity<CoreChannelPT> getChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        log.debug("REST request to get CORChannel : {}", channelShortcut);
        CORChannel cORChannel = channelService.findChannel(channelShortcut);
        CoreChannelPT cORChannelDTO = customCORChannelMapper.cORChannelToCORChannelDTO(cORChannel);
        return Optional.ofNullable(cORChannelDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        log.debug("REST request to delete CORChannel : {}", channelShortcut);
        channelService.deleteChannel(channelShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cORChannel", channelShortcut.toString())).build();

    }
}
