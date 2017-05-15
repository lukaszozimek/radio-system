package io.protone.web.api.traffic.impl;

import io.protone.web.api.traffic.TraPlaylistResource;
import io.protone.web.rest.dto.traffic.TraPlaylistDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by lukaszozimek on 14.05.2017.
 */


@RestController
public class TraPlaylistResourceImpl implements TraPlaylistResource {


    @Override
    public ResponseEntity<TraPlaylistDTO> creatChannelTrafficPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @ApiParam(value = "traPlaylistDTO", required = true) @Valid @RequestBody TraPlaylistDTO traPlaylistDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteChannelTrafficPlaylistUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        return null;
    }

    @Override
    public ResponseEntity<TraPlaylistDTO> getChannelTrafficPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        return null;
    }

    @Override
    public ResponseEntity<TraPlaylistDTO> getAllChannelTrafficPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @ApiParam(value = "pagable", required = true) Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<TraPlaylistDTO> getAllChannelTrafficPlaylistInRangeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                      @ApiParam(value = "fromDate", required = true) @PathVariable("fromDate") String fromDate,
                                                                                      @ApiParam(value = "toDate", required = true) @PathVariable("toDate") String toDate) {
        return null;
    }

    @Override
    public ResponseEntity<List<TraPlaylistDTO>> createChannelTrafficPlaylistInRangeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                             @ApiParam(value = "traPlaylistDTO", required = true) @Valid @RequestBody List<TraPlaylistDTO> traPlaylistDTO) {
        return null;
    }

    @Override
    public ResponseEntity<TraPlaylistDTO> getDownloadChannelTrafficPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "date", required = true) @PathVariable("date") String date) {
        return null;
    }

    @Override
    public ResponseEntity<TraPlaylistDTO> updateChannelTrafficPlaylistUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @ApiParam(value = "traPlaylistDTO", required = true) @Valid @RequestBody TraPlaylistDTO traPlaylistDTO) {
        return null;
    }
}
