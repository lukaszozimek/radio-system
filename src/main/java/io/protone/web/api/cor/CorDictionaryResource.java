package io.protone.web.api.cor;

import io.protone.web.rest.dto.cor.CorDictionaryDTO;
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
public interface CorDictionaryResource {
    @ApiOperation(value = "updateDictionaryValue", notes = "", response = CorDictionaryDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorDictionaryDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorDictionaryDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorDictionaryDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorDictionaryDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorDictionaryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CorDictionaryDTO> updateDictionaryValueUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                                   @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                                   @ApiParam(value = "corDictionaryDTO", required = true) @Valid @RequestBody CorDictionaryDTO corDictionaryDTO) throws URISyntaxException;


    @ApiOperation(value = "createDictionaryValue", notes = "", response = CorDictionaryDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorDictionaryDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CorDictionaryDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorDictionaryDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorDictionaryDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorDictionaryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CorDictionaryDTO> createDictionaryValueUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                                    @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                                    @ApiParam(value = "corDictionaryDTO", required = true) @Valid @RequestBody CorDictionaryDTO corDictionaryDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteDictionaryValue", notes = "", response = Void.class, tags = {"CORE"})
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


    @ApiOperation(value = "getAllDictionaryValue", notes = "", response = CorDictionaryDTO.class, responseContainer = "List", tags = {"DICTIONARY", "CONFIGURATION",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorDictionaryDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorDictionaryDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorDictionaryDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorDictionaryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CorDictionaryDTO>> getAllDictionaryValueUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                                         @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                                         @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getDictionaryValue", notes = "", response = CorDictionaryDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CorDictionaryDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CorDictionaryDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CorDictionaryDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CorDictionaryDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CorDictionaryDTO> getDictionaryValueGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                           @ApiParam(value = "module", required = true) @PathVariable("module") String module,
                                                           @ApiParam(value = "type", required = true) @PathVariable("type") String type,
                                                           @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

}
