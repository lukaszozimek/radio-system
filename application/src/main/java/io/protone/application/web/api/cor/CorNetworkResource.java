package io.protone.application.web.api.cor;


import io.protone.core.api.dto.CorNetworkDTO;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;


@Api(value = "protone", description = "Protone backend API documentation")
public interface CorNetworkResource {

    @ApiOperation(value = "getAllNetworks", notes = "", response = CorNetworkDTO.class, responseContainer = "List", tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorNetworkDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorNetworkDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorNetworkDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorNetworkDTO.class)})
    @RequestMapping(value = "/api/v1/network",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CorNetworkDTO>> getAllNetworksUsingGET();

    @ApiOperation(value = "createNetwork", notes = "", response = CorNetworkDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorNetworkDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorNetworkDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorNetworkDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorNetworkDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorNetworkDTO.class)})
    @RequestMapping(value = "/api/v1/network",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CorNetworkDTO> createNetworkUsingPOST(@ApiParam(value = "network", required = true) @Valid @RequestBody CorNetworkDTO network) throws URISyntaxException;

    @ApiOperation(value = "updateNetwork", notes = "", response = CorNetworkDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorNetworkDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorNetworkDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorNetworkDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorNetworkDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorNetworkDTO.class)})
    @RequestMapping(value = "/api/v1/network",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CorNetworkDTO> updateNetworkUsingPUT(@ApiParam(value = "network", required = true) @Valid @RequestBody CorNetworkDTO network) throws URISyntaxException;

    @ApiOperation(value = "deleteNetwork", notes = "", response = Void.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteNetworkUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);

    @ApiOperation(value = "getNetwork", notes = "", response = CorNetworkDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorNetworkDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorNetworkDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorNetworkDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorNetworkDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CorNetworkDTO> getNetworkUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);
}
