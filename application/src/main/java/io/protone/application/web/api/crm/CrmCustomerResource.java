package io.protone.application.web.api.crm;


import io.protone.crm.api.dto.CrmAccountDTO;
import io.protone.crm.api.dto.thin.CrmAccountThinDTO;
import io.swagger.annotations.*;
import org.apache.tika.exception.TikaException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface CrmCustomerResource {


    @ApiOperation(value = "updateCustomerWithoutAvatar", notes = "", response = CrmAccountDTO.class, tags = {"CRM"})
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
    ResponseEntity<CrmAccountDTO> updateCustomerWithoutAvatarUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "crmAccountDTO", required = true) @Valid @RequestBody CrmAccountDTO crmAccountDTO) throws URISyntaxException, TikaException, IOException, SAXException;


    @ApiOperation(value = "updateCustomerWithoutAvatar", notes = "", response = CrmAccountDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CrmAccountDTO.class),
            @ApiResponse(code = 201, message = "Created", response = CrmAccountDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CrmAccountDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/customer/{shortName}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<CrmAccountDTO> updateCustomerWithAvatarUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                                    @ApiParam(value = "crmAccountDTO", required = true) @Valid @RequestPart("crmAccountDTO") CrmAccountDTO crmAccountDTO,
                                                                    @ApiParam(value = "avatar", required = true) @RequestPart("avatar") MultipartFile avatar) throws URISyntaxException, TikaException, IOException, SAXException;


    @ApiOperation(value = "createCustomer", notes = "", response = CrmAccountDTO.class, tags = {"CRM"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CrmAccountDTO.class),
            @ApiResponse(code = 201, message = "Created", response = CrmAccountDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CrmAccountDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CrmAccountDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CrmAccountDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/customer",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<CrmAccountDTO> createCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "crmAccountDTO", required = true) @Valid @RequestPart("crmAccountDTO") CrmAccountDTO crmAccountDTO,
                                                          @ApiParam(value = "avatar", required = true) @RequestPart("avatar") MultipartFile avatar) throws URISyntaxException, TikaException, IOException, SAXException;


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
