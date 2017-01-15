package io.protone.custom.web.rest.network.configuration.traffic;

import io.protone.custom.service.dto.ConfIndustryPT;
import io.protone.custom.service.dto.ConfMarkerConfigurationPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiConfigurationTrafficDictionaryIndustry {


    @ApiOperation(value = "getIndustry", notes = "", response = ConfIndustryPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfIndustryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfIndustryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfIndustryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfIndustryPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/industry/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<ConfIndustryPT> getIndustryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                       @ApiParam(value = "id", required = true) @PathVariable("id") String industryName);


    @ApiOperation(value = "updateIndustry", notes = "", response = ConfIndustryPT.class, tags = {"CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfIndustryPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfIndustryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfIndustryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfIndustryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfIndustryPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/industry",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<ConfIndustryPT> updateIndustryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "industryDTO", required = true) @RequestBody ConfIndustryPT industryDTO);


    @ApiOperation(value = "createIndustry", notes = "", response = ConfIndustryPT.class, tags = {"CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfIndustryPT.class),
        @ApiResponse(code = 201, message = "Created", response = ConfIndustryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfIndustryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfIndustryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfIndustryPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/industry",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<ConfIndustryPT> createIndustryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "industryDTO", required = true) @RequestBody ConfIndustryPT industryDTO);


    @ApiOperation(value = "deleteIndustry", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/industry/{id}",
        produces = {"*/*"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteIndustryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "id", required = true) @PathVariable("id") String id);


    @ApiOperation(value = "getAllIndustries", notes = "", response = ConfIndustryPT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = ConfIndustryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ConfIndustryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ConfIndustryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = ConfIndustryPT.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/configuration/traffic/dictionary/industry",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<ConfIndustryPT>> getAllIndustriesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


}
