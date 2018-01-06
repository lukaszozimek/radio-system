package io.protone.application.web.api.library;


import io.protone.library.api.dto.LibFileItemDTO;
import io.protone.library.api.dto.thin.LibFileItemThinDTO;
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
public interface LibFileItemResource {


    @ApiOperation(value = "updateItemByWithoutImagesNetworShortcutAndLibraryPrefix", notes = "", response = LibFileItemDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibFileItemDTO.class),
            @ApiResponse(code = 201, message = "Created", response = LibFileItemDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibFileItemDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibFileItemDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibFileItemDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/file/{libraryPrefix}/item",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<LibFileItemDTO> updateFileNetworShortcutAndLibraryPrefixUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                    @ApiParam(value = "fileItem", required = true) @RequestBody @Valid LibFileItemDTO fileItem
    ) throws IOException;

    @ApiOperation(value = "moveMediaItemUsingGET", notes = "", response = Void.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 201, message = "Created", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/file/{libraryPrefix}/item/{idx}/move/{libraryShortcut}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Void> moveFileItemUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                              @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                              @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx,
                                              @ApiParam(value = "libraryShortcut", required = true) @PathVariable("libraryShortcut") String libraryShortcut);

    @ApiOperation(value = "getAllFilesByNetworShortcutAndLibraryPrefix", notes = "", response = LibFileItemThinDTO.class, responseContainer = "List", tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibFileItemThinDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibFileItemThinDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibFileItemThinDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibFileItemThinDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/file/{libraryPrefix}/item",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<LibFileItemThinDTO>> getAllFilesByNetworShortcutAndLibraryPrefixUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                 @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                                 @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "uploadFilesByNetworShortcutAndLibraryPrefix", notes = "", response = LibFileItemThinDTO.class, responseContainer = "List", tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibFileItemThinDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibFileItemThinDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibFileItemThinDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibFileItemThinDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/file/{libraryPrefix}/item",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<LibFileItemThinDTO> uploadItemsByNetworShortcutAndLibraryPrefix(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                   @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                   @ApiParam(value = "files", required = true) @PathParam("files") MultipartFile files) throws Exception;


    @ApiOperation(value = "getFileByNetworShortcutAndLibrar", notes = "", response = LibFileItemDTO.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LibFileItemDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = LibFileItemDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = LibFileItemDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = LibFileItemDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/file/{libraryPrefix}/item/{idx}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<LibFileItemDTO> getFileByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                            @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx);


    @ApiOperation(value = "deleteFileByNetworShortcutAndLibrar", notes = "", response = Void.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/file/{libraryPrefix}/item/{idx}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteFileByNetworShortcutAndLibrarUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                        @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx);


    @ApiOperation(value = "downloadFileByNetworShortcutAndLibrar", notes = "", response = ByteArrayInputStream.class, tags = {"LIBRARY",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ByteArrayInputStream.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ByteArrayInputStream.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ByteArrayInputStream.class),
            @ApiResponse(code = 404, message = "Not Found", response = ByteArrayInputStream.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/file/{libraryPrefix}/item/{idx}/stream",
            produces = {"*/*"},
            method = RequestMethod.GET)
    ResponseEntity<byte[]> downloadFileByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "libraryPrefix", required = true) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                         @ApiParam(value = "idx", required = true) @PathVariable("idx") String idx) throws IOException;


}
