package io.protone.custom.web.rest.network.configuration.core.dictionary;

import io.protone.web.rest.dto.cor.CorValueDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface ApiPropertyValue {


    @ApiOperation(value = "createPropertyValue", notes = "", response = CorValueDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorValueDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorValueDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorValueDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorValueDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorValueDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/value",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CorValueDTO> createPropertyValueUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "propertyValueDTO", required = true) @RequestBody CorValueDTO propertyValueDTO);

    @ApiOperation(value = "getAllPropertyValues", notes = "", response = CorValueDTO.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorValueDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorValueDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorValueDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorValueDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/value",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CorValueDTO>> getAllPropertyValuesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "updatePropertyValue", notes = "", response = CorValueDTO.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorValueDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorValueDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorValueDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorValueDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorValueDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/value",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CorValueDTO> updatePropertyValueUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "propertyValueDTO", required = true) @RequestBody CorValueDTO propertyValueDTO);

}
