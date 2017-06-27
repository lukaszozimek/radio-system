package io.protone.application.web.api.traffic;

import io.protone.web.rest.dto.traffic.TraAdvertisementDTO;
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
public interface TraAdvertisementResource {


    @ApiOperation(value = "updateAdvertisement", notes = "", response = TraAdvertisementDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/advertisement",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraAdvertisementDTO> updateAdvertisementUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "traAdvertisementDTO", required = true) @RequestBody TraAdvertisementDTO traAdvertisementDTO) throws URISyntaxException;


    @ApiOperation(value = "createAdvertisement", notes = "", response = TraAdvertisementDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/advertisement",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraAdvertisementDTO> createAdvertisementUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "traAdvertisementDTO", required = true) @RequestBody TraAdvertisementDTO traAdvertisementDTO) throws URISyntaxException;


    @ApiOperation(value = "getAllAdvertisements", notes = "", response = TraAdvertisementDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/advertisement",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraAdvertisementDTO>> getAllAdvertisementsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "deleteAdvertisement", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/advertisement/{id}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAdvertisementUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                        @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getAdvertisement", notes = "", response = TraAdvertisementDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/advertisement/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraAdvertisementDTO> getAdvertisementUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "getAllCustomersAdvertismentsUsingGET", notes = "", response = TraAdvertisementDTO.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/advertisement/customer/{customerShortcut}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraAdvertisementDTO>> getAllCustomersAdvertismentsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut,
                                                                                   @ApiParam(value = "pagable", required = true) Pageable pagable);


}
