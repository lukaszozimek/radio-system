package io.protone.application.web.api.cor;


import io.protone.core.api.dto.CorFilterDTO;
import io.protone.core.domain.enumeration.CorEntityTypeEnum;
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
public interface CorFilterResource {

    @ApiOperation(value = "getAllFilters", notes = "", response = CorFilterDTO.class, responseContainer = "List", tags = {"CONFIGURATION",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CorFilterDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CorFilterDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CorFilterDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CorFilterDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/configuration/core/filter/{type}",
            produces = {"application/json"},

            method = RequestMethod.GET)
    ResponseEntity<List<CorFilterDTO>> getAllFilterForTypeUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                   @ApiParam(value = "type", required = true)  @PathVariable("type") CorEntityTypeEnum typeEnum,
                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getFilter", notes = "", response = CorFilterDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CorFilterDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CorFilterDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CorFilterDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CorFilterDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/configuration/core/filter/{type}/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<CorFilterDTO> getFilterForTypeUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                          @ApiParam(value = "type", required = true)  @PathVariable("type") CorEntityTypeEnum typeEnum,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "updateFilter", notes = "", response = CorFilterDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CorFilterDTO.class),
            @ApiResponse(code = 201, message = "Created", response = CorFilterDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CorFilterDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CorFilterDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CorFilterDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/configuration/core/filter",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<CorFilterDTO> updateFilterUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                      @ApiParam(value = "corFilterDTO", required = true) @Valid @RequestBody CorFilterDTO corFilterDTO) throws URISyntaxException;

    @ApiOperation(value = "createFilter", notes = "", response = CorFilterDTO.class, tags = {"CORE"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CorFilterDTO.class),
            @ApiResponse(code = 201, message = "Created", response = CorFilterDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CorFilterDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CorFilterDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CorFilterDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/configuration/core/filter",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<CorFilterDTO> createFilterUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                       @ApiParam(value = "CorFilterDTO", required = true) @Valid @RequestBody CorFilterDTO corFilterDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteFilter", notes = "", response = Void.class, tags = {"CORE"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/configuration/core/filter/{type}/{id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteFilterUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                 @ApiParam(value = "type", required = true)  @PathVariable("type") CorEntityTypeEnum typeEnum,
                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
