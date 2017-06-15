package io.protone.web.api.crm;

import io.protone.web.rest.dto.crm.CrmAccountDTO;
import io.protone.web.rest.dto.crm.thin.CrmAccountThinDTO;
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
public interface CrmCustomerResource {


    @ApiOperation(value = "updateCustomer", notes = "", response = CrmAccountDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmAccountDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/customer",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<CrmAccountDTO> updateCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "crmAccountDTO", required = true) @Valid @RequestBody CrmAccountDTO crmAccountDTO) throws URISyntaxException;


    @ApiOperation(value = "createCustomer", notes = "", response = CrmAccountDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountDTO.class),
        @ApiResponse(code = 201, message = "Created", response = CrmAccountDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/customer",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<CrmAccountDTO> createCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "crmAccountDTO", required = true) @Valid @RequestBody CrmAccountDTO crmAccountDTO) throws URISyntaxException;


    @ApiOperation(value = "getAllCustomers", notes = "", response = CrmAccountDTO.class, responseContainer = "List", tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/customer",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<CrmAccountThinDTO>> getAllCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getCustomer", notes = "", response = CrmAccountDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = CrmAccountDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = CrmAccountDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/customer/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<CrmAccountDTO> getCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


    @ApiOperation(value = "deleteCustomer", notes = "", response = Void.class, tags = {"CRM"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/customer/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCustomeryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);


}
