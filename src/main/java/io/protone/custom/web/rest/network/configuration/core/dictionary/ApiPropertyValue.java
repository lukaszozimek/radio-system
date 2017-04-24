package io.protone.custom.web.rest.network.configuration.core.dictionary;

import io.protone.custom.service.dto.CoreValuePT;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiPropertyValue {


    @ApiOperation(value = "createPropertyValue", notes = "", response = CoreValuePT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreValuePT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreValuePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreValuePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreValuePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreValuePT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/value",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CoreValuePT> createPropertyValueUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "propertyValueDTO", required = true) @RequestBody CoreValuePT propertyValueDTO);

    @ApiOperation(value = "getAllPropertyValues", notes = "", response = CoreValuePT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreValuePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreValuePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreValuePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreValuePT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/value",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CoreValuePT>> getAllPropertyValuesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "updatePropertyValue", notes = "", response = CoreValuePT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CoreValuePT.class),
        @ApiResponse(code = 201, message = "Created", response = CoreValuePT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CoreValuePT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CoreValuePT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CoreValuePT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/value",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CoreValuePT> updatePropertyValueUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "propertyValueDTO", required = true) @RequestBody CoreValuePT propertyValueDTO);

}
