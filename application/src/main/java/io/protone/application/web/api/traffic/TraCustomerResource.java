package io.protone.application.web.api.traffic;

import io.protone.traffic.api.dto.TraCustomerDTO;
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
public interface TraCustomerResource {


    @ApiOperation(value = "updateTrafficCustomer", notes = "", response = TraCustomerDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraCustomerDTO.class),
            @ApiResponse(code = 201, message = "Created", response = TraCustomerDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraCustomerDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/customer",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<TraCustomerDTO> updateTrafficCustomerUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                 @ApiParam(value = "traCustomerDTO", required = true) @Valid @RequestBody TraCustomerDTO traCustomerDTO) throws URISyntaxException, TikaException, IOException, SAXException;


    @ApiOperation(value = "createTrafficCustomer", notes = "", response = TraCustomerDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraCustomerDTO.class),
            @ApiResponse(code = 201, message = "Created", response = TraCustomerDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraCustomerDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/customer",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<TraCustomerDTO> createTrafficCustomerUsingPOST(
            @ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
            @ApiParam(value = "traCustomerDTO", required = true) @Valid @RequestPart TraCustomerDTO traCustomerDTO,
            @ApiParam(value = "avatar", required = false) @RequestPart("avatar") MultipartFile avatar) throws URISyntaxException, TikaException, IOException, SAXException;


    @ApiOperation(value = "updateTrafficCustomer", notes = "", response = TraCustomerDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraCustomerDTO.class),
            @ApiResponse(code = 201, message = "Created", response = TraCustomerDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraCustomerDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/customer/{customerShortcut}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<TraCustomerDTO> updateTrafficCustomerUsingPOST(
            @ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
            @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
            @ApiParam(value = "traCustomerDTO", required = true) @Valid @RequestPart TraCustomerDTO traCustomerDTO,
            @ApiParam(value = "avatar", required = false) @RequestPart("avatar") MultipartFile avatar) throws URISyntaxException, TikaException, IOException, SAXException;


    @ApiOperation(value = "getAllTrafficCustomersUsingGET", notes = "", response = TraCustomerDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraCustomerDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraCustomerDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/customer",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<TraCustomerDTO>> getAllTrafficCustomersUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getTrafficCustomerUsingGET", notes = "", response = TraCustomerDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraCustomerDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraCustomerDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraCustomerDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraCustomerDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/customer/{customerShortcut}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<TraCustomerDTO> getTrafficCustomerUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                              @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut);


    @ApiOperation(value = "deleteTrafficCustomerUsingDELETE", notes = "", tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/customer/{customerShortcut}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTrafficCustomerUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                          @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut);


}
