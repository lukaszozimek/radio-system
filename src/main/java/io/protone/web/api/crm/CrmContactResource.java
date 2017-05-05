package io.protone.web.api.crm;

import io.protone.web.rest.dto.crm.CrmContactDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface CrmContactResource {

    @ApiOperation(value = "updateContact", notes = "", response = CrmContactDTO.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmContactDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmContactDTO> updateContactUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "crmContactDTO", required = true) @Valid @RequestBody CrmContactDTO crmContactDTO) throws URISyntaxException;


    @ApiOperation(value = "createContact", notes = "", response = CrmContactDTO.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmContactDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmContactDTO> createContactUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "crmContactDTO", required = true) @Valid @RequestBody CrmContactDTO crmContactDTO) throws URISyntaxException;


    @ApiOperation(value = "getAllContact", notes = "", response = CrmContactDTO.class, responseContainer = "List", tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmContactDTO>> getAllContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "getContactId", notes = "", response = CrmContactDTO.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmContactDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmContactDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmContactDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmContactDTO> getContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                     @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteContact", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteContactUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                  @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


}
