package io.protone.web.api.scheduler;

import io.protone.web.rest.dto.scheduler.SchClockDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@Api(value = "protone", description = "Protone backend API documentation")
public interface SchClockResource {

    @ApiOperation(value = "createSchedulerClockForChannel", notes = "", response = SchClockDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchClockDTO.class),
        @ApiResponse(code = 201, message = "Created", response = SchClockDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchClockDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchClockDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchClockDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<SchClockDTO> creatSchedulerClockForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                       @ApiParam(value = "clockDTO", required = true) @Valid @RequestBody SchClockDTO clockDTO);

    @ApiOperation(value = "getAllSchedulerClockForChannel", notes = "", response = SchClockDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchClockDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchClockDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchClockDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchClockDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<SchClockDTO>> getAllSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "getSchedulerClockForChannel", notes = "", response = SchClockDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchClockDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchClockDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchClockDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchClockDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<SchClockDTO> getSchedulerClockForChannelUsingGETUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName
    );


    @ApiOperation(value = "updateSchedulerClockForChannel", notes = "", response = SchClockDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchClockDTO.class),
        @ApiResponse(code = 201, message = "Created", response = SchClockDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchClockDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchClockDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchClockDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<SchClockDTO> updateSchedulerClockForChanneltUsingPUTUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                @ApiParam(value = "clockDTO", required = true) @Valid @RequestBody SchClockDTO clockDTO);


}
