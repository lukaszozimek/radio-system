package io.protone.custom.web.rest.network.channel.impl;

import io.protone.custom.service.SchPlaylistService;
import io.protone.custom.service.dto.SchPlaylistPT;
import io.protone.custom.web.rest.network.channel.ApiChannelSchedulerPlaylist;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by grzesiek on 15.02.2017.
 */
@RestController
public class ApiChannelSchedulerPlaylistImpl implements ApiChannelSchedulerPlaylist {

    private final Logger log = LoggerFactory.getLogger(ApiChannelSchedulerEventImpl.class);

    @Inject
    private SchPlaylistService playlistService;

    @Override
    public ResponseEntity<List<SchPlaylistPT>> getAllSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                         @ApiParam(value = "pagable", required = true) @PathVariable("pagable") Pageable pagable) {
        log.debug("REST request to get all playlists");
        List<SchPlaylistPT> playlists = playlistService.getAllPlaylistsByChannel(networkShortcut, channelShortcut);
        return ResponseEntity.ok()
            .body(playlists);
    }

    @Override
    public ResponseEntity<SchPlaylistPT> updateSchedulerPlaylisForChanneltUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT playlist) {
        log.debug("REST request to update playlist: {}", playlist);
        SchPlaylistPT resultDAO = playlistService.createOrUpdatePlaylist(networkShortcut, channelShortcut, playlist);
        return ResponseEntity.ok()
            .body(resultDAO);
    }

    @Override
    public ResponseEntity<SchPlaylistPT> creatSchedulerPlaylistForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT playlist) {
        log.debug("REST request to create playlist: {}", playlist);
        SchPlaylistPT resultDAO = playlistService.createOrUpdatePlaylist(networkShortcut, channelShortcut, playlist);
        return ResponseEntity.ok()
            .body(resultDAO);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerPlaylistForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut, @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        log.debug("REST request to delete playlist : {}", date);
        playlistService.deletePlaylist(networkShortcut, channelShortcut, date);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<SchPlaylistPT> getSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut, @ApiParam(value = "date", required = true) @PathVariable("date") String date, @ApiParam(value = "random", required = false) @RequestParam(value = "random", required = false) Boolean random) {
        log.debug("REST request to get playlist: {}", date);
        SchPlaylistPT playlistDAO = null;
        if (random != null && random)
            playlistDAO = playlistService.randomPlaylist(networkShortcut, channelShortcut, date);
        else
            playlistDAO = playlistService.getPlaylistByChannelAndDate(networkShortcut, channelShortcut, date);
        return ResponseEntity.ok()
            .body(playlistDAO);
    }
}
