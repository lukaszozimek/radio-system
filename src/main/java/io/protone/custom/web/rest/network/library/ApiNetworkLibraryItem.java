package io.protone.custom.web.rest.network.library;

import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.LibResponseEntity;
import io.protone.custom.service.dto.LibraryPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiNetworkLibraryItem {


    @ApiOperation(value = "updateItemByNetworShortcutAndLibraryPrefix", notes = "", response = LibItemPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibItemPT.class),
        @ApiResponse(code = 201, message = "Created", response = LibItemPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibItemPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibItemPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibItemPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library/{libraryPrefix}/item",
        produces = {"application/json"},
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<LibItemPT> updateItemByNetworShortcutAndLibraryPrefixUsingPUT(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                 @ApiParam(value = "mediaItem" ,required=true ) @RequestBody LibItemPT mediaItem);


    @ApiOperation(value = "getAllItemsByNetworShortcutAndLibraryPrefix", notes = "", response = LibItemPT.class, responseContainer = "List", tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibItemPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibItemPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibItemPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibItemPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library/{libraryPrefix}/item",
        produces = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<LibItemPT>> getAllItemsByNetworShortcutAndLibraryPrefixUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                                                        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix);

    // TODO: Add uploading


    @ApiOperation(value = "getItemByNetworShortcutAndLibrar", notes = "", response = LibItemPT.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibItemPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibItemPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibItemPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibItemPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library/{libraryPrefix}/item/{idx}",
        produces = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<LibItemPT> getItemByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                       @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("idx") String idx);


    @ApiOperation(value = "deleteItemByNetworShortcutAndLibrar", notes = "", response = Void.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library/{libraryPrefix}/item/{idx}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteItemByNetworShortcutAndLibrarUsingDELETE(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                        @ApiParam(value = "idx",required=true ) @PathVariable("idx") String idx);


    @ApiOperation(value = "getItemStreamByNetworShortcutAndLibrar", notes = "", response = LibResponseEntity.class, tags={ "LIBRARY", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = LibResponseEntity.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = LibResponseEntity.class),
        @ApiResponse(code = 403, message = "Forbidden", response = LibResponseEntity.class),
        @ApiResponse(code = 404, message = "Not Found", response = LibResponseEntity.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/library/{libraryPrefix}/item/{idx}/stream",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<LibResponseEntity> getItemStreamByNetworShortcutAndLibrarUsingGET(@ApiParam(value = "networkShortcut",required=true ) @PathVariable("networkShortcut") String networkShortcut,
                                                                                     @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("libraryPrefix") String libraryPrefix,
                                                                                     @ApiParam(value = "libraryPrefix",required=true ) @PathVariable("idx") String idx);

}
