package io.protone.custom.web.rest.network.scheduler;

import io.protone.custom.service.SchPlaylistService;
import io.protone.custom.service.dto.SchPlaylistPT;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by grzesiek on 12.02.2017.
 */
@RestController
public class ApiNetworkSchedulerPlaylistImpl implements ApiNetworkSchedulerPlaylist {

    private final Logger log = LoggerFactory.getLogger(ApiNetworkSchedulerPlaylistImpl.class);

    @Inject
    SchPlaylistService playlistService;

    @Override
    public ResponseEntity<SchPlaylistPT> updateSchedulerPlaylistUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT playlist) {
        log.debug("REST request to update playlist: {}", playlist);
        SchPlaylistPT resultDAO = playlistService.createOrUpdatePlaylist(networkShortcut, playlist);
        return ResponseEntity.ok()
            .body(resultDAO);
    }

    @Override
    public ResponseEntity<SchPlaylistPT> creatSchedulerPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT playlist) {
        log.debug("REST request to create playlist: {}", playlist);
        SchPlaylistPT resultDAO = playlistService.createOrUpdatePlaylist(networkShortcut, playlist);
        return ResponseEntity.ok()
            .body(resultDAO);
    }

    @Override
    public ResponseEntity<List<SchPlaylistPT>> getAllSchedulerPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all playlists");
        List<SchPlaylistPT> playlists = playlistService.getPlaylists(networkShortcut);
        return ResponseEntity.ok()
            .body(playlists);
    }

    @Override
    public ResponseEntity<List<SchPlaylistPT>> getSchedulerPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        log.debug("REST request to get playlist: {}", date);
        List<SchPlaylistPT> resultsDAO = playlistService.getPlaylist(networkShortcut, date);
        return ResponseEntity.ok()
            .body(resultsDAO);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerPlaylistUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        log.debug("REST request to delete playlist : {}", date);
        playlistService.deletePlaylist(networkShortcut, date);
        return ResponseEntity.ok().build();
    }
}
