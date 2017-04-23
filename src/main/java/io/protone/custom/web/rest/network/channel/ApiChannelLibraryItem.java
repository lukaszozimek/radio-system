package io.protone.custom.web.rest.network.channel;

import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.LibResponseEntity;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiChannelLibraryItem {

    @ApiOperation(value = "deleteItemsByNetworShortcutAndChannelShortcutAndLibrary", notes = "", response = Void.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteItemsByNetworShortcutAndChannelShortcutAndLibraryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                            @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "getItemsByNetworShortcutAndChannelShortcutAndLibrar", notes = "", response = LibItemPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibItemPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibItemPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibItemPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibItemPT.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<LibItemPT> getItemsByNetworShortcutAndChannelShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                          @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "deleteItemByNetworShortcutAndChannelShortcutAndLibrary", notes = "", response = Void.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item/{idx}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteItemByNetworShortcutAndChannelShortcutAndLibraryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                           @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                           @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx);


    @ApiOperation(value = "getItemByNetworShortcutAndChannelShortcutAndLibrary", notes = "", response = LibItemPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibItemPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibItemPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibItemPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibItemPT.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item/{idx}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<LibItemPT> getItemByNetworShortcutAndChannelShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                         @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                         @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx);


    @ApiOperation(value = "getItemStreamByNetworShortcutAndChannelShortcutAndLibrary", notes = "", response = LibResponseEntity.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibResponseEntity.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibResponseEntity.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibResponseEntity.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibResponseEntity.class) })
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item/{idx}/stream",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<LibResponseEntity> getItemStreamByNetworShortcutAndChannelShortcutAndLibraryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                        @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                        @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx);



}
