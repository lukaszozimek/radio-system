package io.protone.application.web.api.cor;


import io.protone.core.api.dto.CorNetworkDTO;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/network",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<CorNetworkDTO>> getAllNetworksUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut);

    @ApiOperation(value = "createNetwork", notes = "", response = CorNetworkDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CorNetworkDTO.class),
            @ApiResponse(code = 201, message = "Created", response = CorNetworkDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CorNetworkDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CorNetworkDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CorNetworkDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/network",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<CorNetworkDTO> createNetworkUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                         @ApiParam(value = "organization", required = true) @Valid @RequestPart("organization") CorNetworkDTO network,
                                                         @ApiParam(value = "logo", required = true) @RequestPart("logo") MultipartFile logo) throws URISyntaxException;

    @ApiOperation(value = "updateNetwork", notes = "", response = CorNetworkDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CorNetworkDTO.class),
            @ApiResponse(code = 201, message = "Created", response = CorNetworkDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CorNetworkDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CorNetworkDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CorNetworkDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/network",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<CorNetworkDTO> updateNetworkUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut, @ApiParam(value = "organization", required = true) @Valid @RequestBody CorNetworkDTO network) throws URISyntaxException;

    @ApiOperation(value = "updateNetworkWithLogo", notes = "", response = CorNetworkDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CorNetworkDTO.class),
            @ApiResponse(code = 201, message = "Created", response = CorNetworkDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CorNetworkDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CorNetworkDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CorNetworkDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/network/{networkShortcut}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<CorNetworkDTO> updateNetworkWithLogoUsingPOST(
            @ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
            @ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
            @ApiParam(value = "organization", required = true) @Valid @RequestPart("organization") CorNetworkDTO network,
            @ApiParam(value = "logo", required = true) @RequestPart("logo") MultipartFile logo) throws URISyntaxException;

    @ApiOperation(value = "deleteNetwork", notes = "", response = Void.class, tags = {"CORE"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/network/{networkShortcut}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteNetworkUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                  @ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);

    @ApiOperation(value = "getChannel", notes = "", response = CorNetworkDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CorNetworkDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CorNetworkDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CorNetworkDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CorNetworkDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/network/{networkShortcut}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<CorNetworkDTO> getNetworkUsingGET(
            @ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
            @ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);
}
