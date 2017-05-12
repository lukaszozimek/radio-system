package io.protone.custom.web.rest.network.traffic;

import io.protone.web.rest.dto.traffic.TraAdvertisementDTO;
import io.protone.custom.service.dto.TraShuffleAdvertisementPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(value = "protone", description = "Protone backend API documentation")
public interface ApiNetworkTrafficAdvertisementShuffle {


    @ApiOperation(value = "updateAdvertisement", notes = "", response = TraAdvertisementDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/advertisement/shuffle",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<Void> shuffleCommercialUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "traShuffleAdvertismentPT", required = true) @RequestBody TraShuffleAdvertisementPT traShuffleAdvertismentPT);


    @ApiOperation(value = "updateAdvertisement", notes = "", response = TraAdvertisementDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/traffic/advertisement/shuffle/",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<Void> approveShuffelingCommercialUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                              @ApiParam(value = "traShuffleAdvertismentPT", required = true) @RequestBody TraShuffleAdvertisementPT traShuffleAdvertismentPT);

}
