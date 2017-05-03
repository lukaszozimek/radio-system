package io.protone.custom.web.rest.network.traffic;

import io.protone.custom.service.dto.TraAdvertisementPT;
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
public interface ApiNetworkTrafficAdvertisement {


    @ApiOperation(value = "updateAdvertisement", notes = "", response = TraAdvertisementPT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraAdvertisementPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/advertisement",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraAdvertisementPT> updateAdvertisementUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "traAdvertisementPT", required = true) @RequestBody TraAdvertisementPT traAdvertisementPT) throws URISyntaxException;


    @ApiOperation(value = "createAdvertisement", notes = "", response = TraAdvertisementPT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementPT.class),
        @ApiResponse(code = 201, message = "Created", response = TraAdvertisementPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/advertisement",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraAdvertisementPT> createAdvertisementUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "traAdvertisementPT", required = true) @RequestBody TraAdvertisementPT traAdvertisementPT) throws URISyntaxException;


    @ApiOperation(value = "getAllAdvertisements", notes = "", response = TraAdvertisementPT.class, responseContainer = "List", tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/advertisement",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraAdvertisementPT>> getAllAdvertisementsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
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


    @ApiOperation(value = "getAdvertisement", notes = "", response = TraAdvertisementPT.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraAdvertisementPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraAdvertisementPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraAdvertisementPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraAdvertisementPT.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/advertisement/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraAdvertisementPT> getAdvertisementUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


}
