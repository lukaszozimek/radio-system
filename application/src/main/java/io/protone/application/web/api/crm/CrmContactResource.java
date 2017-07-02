package io.protone.application.web.api.crm;


import io.protone.crm.api.dto.CrmContactDTO;
import io.protone.crm.api.dto.thin.CrmContactThinDTO;
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
public interface CrmContactResource {

    @ApiOperation(value = "updateContactWithoutAvatar", notes = "", response = CrmContactDTO.class, tags = {"CRM",})
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
    ResponseEntity<CrmContactDTO> updateContactWithoutAvatarUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "crmContactDTO", required = true) @Valid @RequestBody CrmContactDTO crmContactDTO) throws URISyntaxException, TikaException, IOException, SAXException;


    @ApiOperation(value = "updateContactWithAvatar", notes = "", response = CrmContactDTO.class, tags = {"CRM",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CrmContactDTO.class),
            @ApiResponse(code = 201, message = "Created", response = CrmContactDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CrmContactDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CrmContactDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact/{shortName}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<CrmContactDTO> updateContactWithAvatarUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName,
                                                         @ApiParam(value = "crmContactDTO", required = true) @Valid @RequestPart("crmContactDTO") CrmContactDTO crmContactDTO,
                                                         @ApiParam(value = "avatar", required = true) @RequestPart("avatar") MultipartFile avatar) throws URISyntaxException, TikaException, IOException, SAXException;


    @ApiOperation(value = "createContact", notes = "", response = CrmContactDTO.class, tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CrmContactDTO.class),
            @ApiResponse(code = 201, message = "Created", response = CrmContactDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CrmContactDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CrmContactDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<CrmContactDTO> createContactUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                         @ApiParam(value = "crmContactDTO", required = true) @Valid @RequestPart("crmContactDTO") CrmContactDTO crmContactDTO,
                                                         @ApiParam(value = "avatar", required = true) @RequestPart("avatar") MultipartFile avatar) throws URISyntaxException, TikaException, IOException, SAXException;


    @ApiOperation(value = "getAllContact", notes = "", response = CrmContactDTO.class, responseContainer = "List", tags = {"TRAFFIC", "CRM",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CrmContactDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = CrmContactDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = CrmContactDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CrmContactDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/crm/contact",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<CrmContactThinDTO>> getAllContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
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
