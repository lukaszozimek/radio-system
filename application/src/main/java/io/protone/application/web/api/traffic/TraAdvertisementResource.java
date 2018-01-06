package io.protone.application.web.api.traffic;


import io.protone.traffic.api.dto.TraAdvertisementDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "protone", description = "Protone backend API documentation")
public interface TraAdvertisementResource {


    @ApiOperation(value = "updateAdvertisement", notes = "", response = TraAdvertisementDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/advertisement",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraAdvertisementDTO> updateAdvertisementUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                    @ApiParam(value = "traAdvertisementDTO", required = true) @RequestBody TraAdvertisementDTO traAdvertisementDTO
    ) throws URISyntaxException, IOException;





    @ApiOperation(value = "createAdvertisement", notes = "", response = TraAdvertisementDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/advertisement",
        produces = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraAdvertisementDTO> createAdvertisementUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                     @ApiParam(value = "traAdvertisementDTO", required = true) @RequestPart("traAdvertisementDTO") @Valid TraAdvertisementDTO traAdvertisementDTO,
                                                                     @ApiParam(value = "commercial") @RequestPart("commercial") MultipartFile commercial) throws URISyntaxException, IOException;


    @ApiOperation(value = "getAllAdvertisements", notes = "", response = TraAdvertisementDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/advertisement",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraAdvertisementDTO>> getAllAdvertisementsUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "deleteAdvertisement", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/advertisement/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAdvertisementUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getAdvertisement", notes = "", response = TraAdvertisementDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/advertisement/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraAdvertisementDTO> getAdvertisementUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "getAllCustomersAdvertismentsUsingGET", notes = "", response = TraAdvertisementDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/advertisement/customer/{customerShortcut}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraAdvertisementDTO>> getAllCustomersAdvertismentsUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                   @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable);


}
