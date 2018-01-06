package io.protone.application.web.api.cor;


import io.protone.core.api.dto.CorCurrencyDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface CorCurrencyResource {

    @ApiOperation(value = "getAllCurrency", notes = "", response = CorCurrencyDTO.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorCurrencyDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorCurrencyDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorCurrencyDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorCurrencyDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/traffic/dictionary/currency",
        produces = {"application/json"},

        method = RequestMethod.GET)
    ResponseEntity<List<CorCurrencyDTO>> getAllCurrencyUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getCurrency", notes = "", response = CorCurrencyDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorCurrencyDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorCurrencyDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorCurrencyDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorCurrencyDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/traffic/dictionary/currency/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CorCurrencyDTO> getCurrencyUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateCurrency", notes = "", response = CorCurrencyDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorCurrencyDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorCurrencyDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorCurrencyDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorCurrencyDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorCurrencyDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/traffic/dictionary/currency",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CorCurrencyDTO> updateCurrencyUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                          @ApiParam(value = "currencyDTO", required = true) @Valid @RequestBody CorCurrencyDTO currencyDTO) throws URISyntaxException;

    @ApiOperation(value = "createCurrency", notes = "", response = CorCurrencyDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorCurrencyDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorCurrencyDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorCurrencyDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorCurrencyDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorCurrencyDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/traffic/dictionary/currency",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CorCurrencyDTO> createCurrencyUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                           @ApiParam(value = "corCurrencyDTO", required = true) @Valid @RequestBody CorCurrencyDTO corCurrencyDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteCurrency", notes = "", response = Void.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/configuration/traffic/dictionary/currency/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCurrencyUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                   @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
