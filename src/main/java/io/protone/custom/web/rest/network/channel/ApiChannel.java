package io.protone.custom.web.rest.network.channel;

import io.protone.custom.service.dto.CoreChannelPT;
import io.protone.custom.service.dto.CoreNetworkPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiChannel {


    @ApiOperation(value = "updateChannel", notes = "", response = CoreChannelPT.class, tags = {"CHANNEL", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreChannelPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreChannelPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreChannelPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreChannelPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreChannelPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CoreChannelPT> updateChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "channelDTO", required = true) @RequestBody CoreChannelPT channelDTO);


    @ApiOperation(value = "createChannel", notes = "", response = CoreChannelPT.class, tags = {"CHANNEL", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreChannelPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreChannelPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreChannelPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreChannelPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreChannelPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CoreChannelPT> createChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "channelDTO", required = true) @RequestBody CoreChannelPT channelDTO);


    @ApiOperation(value = "getAllChannels", notes = "", response = CoreChannelPT.class, responseContainer = "List", tags = {"CHANNEL", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreChannelPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreChannelPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreChannelPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreChannelPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CoreChannelPT>> getAllChannelsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);

    @ApiOperation(value = "getChannel", notes = "", response = CoreChannelPT.class, tags = {"CHANNEL", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreChannelPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreChannelPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreChannelPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreChannelPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CoreChannelPT> getChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut);

    @ApiOperation(value = "deleteChannel", notes = "", response = Void.class, tags = {"CHANNEL", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut);


}
