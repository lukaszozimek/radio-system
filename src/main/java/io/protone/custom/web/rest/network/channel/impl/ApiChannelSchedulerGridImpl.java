package io.protone.custom.web.rest.network.channel.impl;

import io.protone.custom.service.dto.SchGridPT;
import io.protone.custom.service.dto.SchPlaylistPT;
import io.protone.custom.web.rest.network.channel.ApiChannelSchedulerGrid;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@RestController
public class ApiChannelSchedulerGridImpl  implements ApiChannelSchedulerGrid{


    @Override
    public ResponseEntity<List<SchGridPT>> getAllSchedulerGridForChannelUsingGET(String networkShortcut, String channelShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<SchGridPT> updateSchedulerGridForChanneltUsingPUT(String networkShortcut, String channelShortcut, SchPlaylistPT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<SchGridPT> creatSchedulerPlaylistForChannelUsingPOST(String networkShortcut, String channelShortcut, SchGridPT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerGridForChannelUsingDELETE(String networkShortcut, String channelShortcut, String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<SchGridPT> getSchedulerGridForChannelUsingGET(String networkShortcut, String channelShortcut, String shortName) {
        return null;
    }
}
