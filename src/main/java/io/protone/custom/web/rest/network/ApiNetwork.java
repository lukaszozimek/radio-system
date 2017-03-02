package io.protone.custom.web.rest.network;

import io.protone.custom.service.dto.CoreNetworkPT;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Api(value = "custom", description = "the api API")
public interface ApiNetwork {

    @ApiOperation(value = "getAllNetworks", notes = "", response = CoreNetworkPT.class, responseContainer = "List", tags = {"NETWORK", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class)})
    @RequestMapping(value = "/api/network",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CoreNetworkPT>> getAllNetworksUsingGET();

    @ApiOperation(value = "createNetwork", notes = "", response = CoreNetworkPT.class, tags = {"NETWORK", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class)})
    @RequestMapping(value = "/api/network",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CoreNetworkPT> createNetworkUsingPOST(@ApiParam(value = "network", required = true) @RequestBody CoreNetworkPT network);

    @ApiOperation(value = "updateNetwork", notes = "", response = CoreNetworkPT.class, tags = {"NETWORK", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class)})
    @RequestMapping(value = "/api/network",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CoreNetworkPT> updateNetworkUsingPUT(@ApiParam(value = "network", required = true) @RequestBody CoreNetworkPT network);

    @ApiOperation(value = "deleteNetwork", notes = "", response = Void.class, tags = {"NETWORK", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteNetworkUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);

    @ApiOperation(value = "getNetwork", notes = "", response = CoreNetworkPT.class, tags = {"NETWORK", "CORE",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreNetworkPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreNetworkPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreNetworkPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreNetworkPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CoreNetworkPT> getNetworkUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);
}
