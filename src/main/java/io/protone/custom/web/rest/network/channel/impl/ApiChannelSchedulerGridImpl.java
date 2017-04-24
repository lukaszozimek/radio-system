package io.protone.custom.web.rest.network.channel.impl;

import io.protone.custom.service.dto.SchGridPT;
import io.protone.custom.service.dto.SchPlaylistPT;
import io.protone.custom.web.rest.network.channel.ApiChannelSchedulerGrid;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@RestController
public class ApiChannelSchedulerGridImpl  implements ApiChannelSchedulerGrid{


    @Override
    public ResponseEntity<List<SchGridPT>> getAllSchedulerGridForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                 @ApiParam(value = "pagable", required = true)  Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<SchGridPT> updateSchedulerGridForChanneltUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchGridPT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<SchGridPT> creatSchedulerPlaylistForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchGridPT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerGridForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<SchGridPT> getSchedulerGridForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }
}
