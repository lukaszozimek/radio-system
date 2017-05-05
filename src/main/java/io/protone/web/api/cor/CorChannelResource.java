package io.protone.web.api.cor;

import io.protone.web.rest.dto.cor.CorChannelDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface CorChannelResource {


    @ApiOperation(value = "updateChannel", notes = "", response = CorChannelDTO.class, tags = {"CHANNEL", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorChannelDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorChannelDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorChannelDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorChannelDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorChannelDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CorChannelDTO> updateChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "channelDTO", required = true) @Valid @RequestBody CorChannelDTO channelDTO) throws URISyntaxException;


    @ApiOperation(value = "createChannel", notes = "", response = CorChannelDTO.class, tags = {"CHANNEL", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorChannelDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorChannelDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorChannelDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorChannelDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorChannelDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CorChannelDTO> createChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "channelDTO", required = true) @Valid @RequestBody CorChannelDTO channelDTO) throws URISyntaxException;


    @ApiOperation(value = "getAllChannels", notes = "", response = CorChannelDTO.class, responseContainer = "List", tags = {"CHANNEL", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorChannelDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorChannelDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorChannelDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorChannelDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CorChannelDTO>> getAllChannelsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "getChannel", notes = "", response = CorChannelDTO.class, tags = {"CHANNEL", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorChannelDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorChannelDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorChannelDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorChannelDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CorChannelDTO> getChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut);

    @ApiOperation(value = "deleteChannel", notes = "", response = Void.class, tags = {"CHANNEL", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut);


}
