package io.protone.custom.web.rest.network.scheduler;

import io.protone.custom.service.dto.SchPlaylistPT;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by grzesiek on 12.02.2017.
 */
@RestController
public class ApiNetworkSchedulerPlaylistImpl implements ApiNetworkSchedulerPlaylist {
    @Override
    public ResponseEntity<SchPlaylistPT> updateSchedulerPlaylistUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<SchPlaylistPT> creatSchedulerPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<List<SchPlaylistPT>> getAllSchedulerPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<SchPlaylistPT> getSchedulerPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerPlaylistUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        return null;
    }
}
