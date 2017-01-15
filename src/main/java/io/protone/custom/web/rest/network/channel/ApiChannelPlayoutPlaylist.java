package io.protone.custom.web.rest.network.channel;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiChannelPlayoutPlaylist {


    @ApiOperation(value = "loadPlaylist", notes = "", response = Void.class, tags={ "PLAYOUT", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/playout/playlist/{date}/load",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> loadPlaylistUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                               @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
                                               @ApiParam(value = "date",required=true ) @PathVariable("date") String date);

    @ApiOperation(value = "refreshPlaylist", notes = "", response = Void.class, tags={ "PLAYOUT", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/playout/playlist/{date}/refresh",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> refreshPlaylistUsingPOST(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "channelShortcut",required=true ) @PathVariable("channelShortcut") String channelShortcut,
                                                  @ApiParam(value = "date",required=true ) @PathVariable("date") String date);




}
