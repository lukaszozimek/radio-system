package io.protone.application.web.api.library;

import io.protone.web.rest.dto.library.LibMediaItemDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface LibMediaItemResource {


    @ApiOperation(value = "updateItemByNetworShortcutAndLibraryPrefix", notes = "", response = LibMediaItemDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibMediaItemDTO.class),
        @ApiResponse(code = 201, message = "Created", response = LibMediaItemDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaItemDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibMediaItemDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibMediaItemDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<LibMediaItemDTO> updateItemByNetworShortcutAndLibraryPrefixUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                       @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                       @ApiParam(value = "mediaItem", required = true) @Valid @RequestBody LibMediaItemDTO mediaItem);

    @ApiOperation(value = "getAllItemsByNetworShortcutAndLibraryPrefix", notes = "", response = LibMediaItemDTO.class, responseContainer = "List", tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibMediaItemDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaItemDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibMediaItemDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibMediaItemDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item",
        consumes = {"*/*"},
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<LibMediaItemDTO>> getAllItemsByNetworShortcutAndLibraryPrefixUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                              @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                              @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "uploadItemsByNetworShortcutAndLibraryPrefix", notes = "", response = LibMediaItemDTO.class, responseContainer = "List", tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibMediaItemDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaItemDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibMediaItemDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibMediaItemDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item",
        produces = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<List<LibMediaItemDTO>> uploadItemsByNetworShortcutAndLibraryPrefix(
        @ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
        @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
        @ApiParam(value = "files", required = true) @PathParam("files") MultipartFile[] files) throws Exception;




    @ApiOperation(value = "getItemByNetworShortcutAndLibrar", notes = "", response = LibMediaItemDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibMediaItemDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaItemDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibMediaItemDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibMediaItemDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item/{idx}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<LibMediaItemDTO> getItemByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                             @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx);


    @ApiOperation(value = "deleteItemByNetworShortcutAndLibrar", notes = "", response = Void.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item/{idx}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteItemByNetworShortcutAndLibrarUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                        @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx);


    @ApiOperation(value = "getItemStreamByNetworShortcutAndLibrar", notes = "", response = ByteArrayInputStream.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ByteArrayInputStream.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ByteArrayInputStream.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ByteArrayInputStream.class),
        @ApiResponse(code = 404, message = "Not Found", response = ByteArrayInputStream.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item/{idx}/stream",
        method = RequestMethod.GET)
    ResponseEntity<byte[]> streamItemByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                       @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx) throws IOException;
    @ApiOperation(value = "updateItemByNetworShortcutAndLibraryPrefix", notes = "", response = LibMediaItemDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibMediaItemDTO.class),
        @ApiResponse(code = 201, message = "Created", response = LibMediaItemDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaItemDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibMediaItemDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibMediaItemDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<LibMediaItemDTO> updateItemByNetworShortcutAndLibraryPrefixUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                       @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                       @ApiParam(value = "mediaItem", required = true) @RequestBody LibMediaItemDTO mediaItem);

    @ApiOperation(value = "deleteItemsByNetworShortcutAndChannelShortcutAndLibrary", notes = "", response = Void.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<List<LibMediaItemDTO>> getAllItemsByNetworShortcutAndLibraryPrefixUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                              @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                              @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getItemsByNetworShortcutAndChannelShortcutAndLibrar", notes = "", response = LibMediaItemDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibMediaItemDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaItemDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibMediaItemDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibMediaItemDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<LibMediaItemDTO> getItemsByNetworShortcutAndChannelShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix);


    @ApiOperation(value = "deleteItemByNetworShortcutAndChannelShortcutAndLibrary", notes = "", response = Void.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item/{idx}",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteItemByNetworShortcutAndChannelShortcutAndLibraryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                           @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                           @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx);


    @ApiOperation(value = "getItemByNetworShortcutAndChannelShortcutAndLibrary", notes = "", response = LibMediaItemDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibMediaItemDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibMediaItemDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibMediaItemDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibMediaItemDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item/{idx}",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<LibMediaItemDTO> getItemByNetworShortcutAndChannelShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                               @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                               @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx);


    @ApiOperation(value = "getItemStreamByNetworShortcutAndChannelShortcutAndLibrary", notes = "", response = ByteArrayInputStream.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ByteArrayInputStream.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ByteArrayInputStream.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ByteArrayInputStream.class),
        @ApiResponse(code = 404, message = "Not Found", response = ByteArrayInputStream.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/library/{libraryPrefix}/item/{idx}/stream",
        produces = {"*/*"},
        consumes = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<byte[]> getItemStreamByNetworShortcutAndChannelShortcutAndLibraryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                             @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                             @ApiParam(value = "libraryPrefix", required = true) @PathVariable("idx") String idx);

}
