package io.protone.application.web.api.traffic;


import io.protone.traffic.api.dto.TraCompanyDTO;
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
public interface TraCompanyResource {

    @ApiOperation(value = "getAllCompany", notes = "", response = TraCompanyDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraCompanyDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraCompanyDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraCompanyDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraCompanyDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/company",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<TraCompanyDTO>> getAllCompanyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getCompany", notes = "", response = TraCompanyDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraCompanyDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraCompanyDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraCompanyDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraCompanyDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/company/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<TraCompanyDTO> getCompanyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateCompany", notes = "", response = TraCompanyDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraCompanyDTO.class),
            @ApiResponse(code = 201, message = "Created", response = TraCompanyDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraCompanyDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraCompanyDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraCompanyDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/company",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<TraCompanyDTO> updateCompanyUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "traCompanyDTO", required = true) @RequestBody TraCompanyDTO traCompanyDTO) throws URISyntaxException;


    @ApiOperation(value = "createCompany", notes = "", response = TraCompanyDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraCompanyDTO.class),
            @ApiResponse(code = 201, message = "Created", response = TraCompanyDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraCompanyDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraCompanyDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraCompanyDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/company",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<TraCompanyDTO> createCompanyUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "traCompanyDTO", required = true) @RequestBody TraCompanyDTO traCompanyDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteCompany", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/traffic/company/{id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCompanyUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
