package io.protone.custom.web.rest.network.configuration.core.dictionary;

import io.protone.custom.service.dto.ConfCountryPt;
import io.protone.custom.service.dto.ConfTagPT;
import io.protone.custom.service.dto.CorDictionaryPT;
import io.protone.domain.CorDictionary;
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
public interface ApiDictionaryCoreDictionary {
    @ApiOperation(value = "updateDictionaryValue", notes = "", response = CorDictionaryPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorDictionaryPT.class),
        @ApiResponse(code = 201, message = "Created", response = CorDictionaryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorDictionaryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorDictionaryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorDictionaryPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CorDictionaryPT> updateDictionaryValueUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                                  @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                                  @ApiParam(value = "corDictionaryPT", required = true) @RequestBody CorDictionaryPT corDictionaryPT);


    @ApiOperation(value = "createDictionaryValue", notes = "", response = CorDictionaryPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorDictionaryPT.class),
        @ApiResponse(code = 201, message = "Created", response = CorDictionaryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorDictionaryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorDictionaryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorDictionaryPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CorDictionaryPT> createDictionaryValueUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                                   @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                                   @ApiParam(value = "corDictionaryPT", required = true) @RequestBody CorDictionaryPT corDictionaryPT);


    @ApiOperation(value = "deleteDictionaryValue", notes = "", response = Void.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteDictionaryValueUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                          @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getAllDictionaryValue", notes = "", response = CorDictionaryPT.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorDictionaryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorDictionaryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorDictionaryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorDictionaryPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CorDictionaryPT>> getAllDictionaryValueUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                                        @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                                        @ApiParam(value = "pagable", required = true) @PathVariable("pagable") Pageable pagable);


    @ApiOperation(value = "getDictionaryValue", notes = "", response = CorDictionaryPT.class, tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorDictionaryPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorDictionaryPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorDictionaryPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorDictionaryPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CorDictionaryPT> getDictionaryValueGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                          @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

}
