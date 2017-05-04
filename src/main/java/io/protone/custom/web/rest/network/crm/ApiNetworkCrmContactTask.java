package io.protone.custom.web.rest.network.crm;

import io.protone.custom.service.dto.CrmTaskDTO;
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
public interface ApiNetworkCrmContactTask {

    @ApiOperation(value = "getAllContactActivities", notes = "", response = CrmTaskDTO.class, responseContainer = "List", tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmTaskDTO>> getAllContactActivitiesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                     @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "updateContactActivity", notes = "", response = CrmTaskDTO.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmTaskDTO> updateContactActivityUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                             @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                             @ApiParam(value = "crmTaskDTO", required = true) @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException;


    @ApiOperation(value = "createContactActivity", notes = "", response = CrmTaskDTO.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmTaskDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmTaskDTO> createContactActivityUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                              @ApiParam(value = "crmTaskDTO", required = true) @RequestBody CrmTaskDTO crmTaskDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteContactActivityActivity", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteContactActivityUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "getContactActivity", notes = "", response = CrmTaskDTO.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmTaskDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmTaskDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmTaskDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmTaskDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmTaskDTO> getContactActivityUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
