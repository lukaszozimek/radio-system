package io.protone.application.web.api.traffic;


import io.protone.traffic.api.dto.TraPriceDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface TraPriceResource {

    @ApiOperation(value = "getAllPrice", notes = "", response = TraPriceDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraPriceDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraPriceDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraPriceDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraPriceDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/configuration/traffic/price",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<TraPriceDTO>> getAllPriceUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                          @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getPrice", notes = "", response = TraPriceDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraPriceDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraPriceDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraPriceDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraPriceDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/configuration/traffic/price/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<TraPriceDTO> getPriceUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updatePrice", notes = "", response = TraPriceDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraPriceDTO.class),
            @ApiResponse(code = 201, message = "Created", response = TraPriceDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraPriceDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraPriceDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraPriceDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/configuration/traffic/price",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<TraPriceDTO> updatePriceUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                    @ApiParam(value = "traPriceDTO", required = true) @RequestBody TraPriceDTO traPriceDTO) throws URISyntaxException;


    @ApiOperation(value = "createPrice", notes = "", response = TraPriceDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraPriceDTO.class),
            @ApiResponse(code = 201, message = "Created", response = TraPriceDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraPriceDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraPriceDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraPriceDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/configuration/traffic/price",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<TraPriceDTO> createPriceUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                     @ApiParam(value = "traPriceDTO", required = true) @RequestBody TraPriceDTO traPriceDTO) throws URISyntaxException;


    @ApiOperation(value = "deletePrice", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/configuration/traffic/price/{id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deletePriceUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
