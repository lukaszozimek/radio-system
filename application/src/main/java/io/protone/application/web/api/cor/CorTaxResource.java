package io.protone.application.web.api.cor;


import io.protone.core.api.dto.CorTaxDTO;
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
public interface CorTaxResource {

    @ApiOperation(value = "getAllTaxes", notes = "", response = CorTaxDTO.class, responseContainer = "List", tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorTaxDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorTaxDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorTaxDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorTaxDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/traffic/dictionary/tax",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CorTaxDTO>> getAllTaxesUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                        @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getTax", notes = "", response = CorTaxDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorTaxDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorTaxDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorTaxDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorTaxDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/traffic/dictionary/tax/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CorTaxDTO> getTaxUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                             @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateTax", notes = "", response = CorTaxDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorTaxDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorTaxDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorTaxDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorTaxDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorTaxDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/traffic/dictionary/tax",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CorTaxDTO> updateTaxUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                @ApiParam(value = "taxDTO", required = true) @RequestBody CorTaxDTO taxDTO) throws URISyntaxException;


    @ApiOperation(value = "createTax", notes = "", response = CorTaxDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorTaxDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorTaxDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorTaxDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorTaxDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorTaxDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/traffic/dictionary/tax",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CorTaxDTO> createTaxUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                 @ApiParam(value = "taxDTO", required = true) @RequestBody CorTaxDTO taxDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteTax", notes = "", response = Void.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/traffic/dictionary/tax/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTaxUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
