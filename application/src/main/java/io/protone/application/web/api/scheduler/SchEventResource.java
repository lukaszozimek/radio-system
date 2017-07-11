package io.protone.application.web.api.scheduler;

import io.protone.scheduler.api.dto.SchEventDTO;
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
public interface SchEventResource {

    @ApiOperation(value = "getAllSchedulerEventForChannel", notes = "", response = SchEventDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<SchEventDTO>> getAllSchedulerTemplatesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                 @ApiParam(value = "pagable", required = true) Pageable pagable);


    @ApiOperation(value = "createSchedulerTemplatesForChannel", notes = "", response = SchEventDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventDTO.class),
        @ApiResponse(code = 201, message = "Created", response = SchEventDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<SchEventDTO> creatSchedulerTemplatesForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventDTO schEventDTO);



    @ApiOperation(value = "deleteSchedulerEventForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerEventForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName);

    @ApiOperation(value = "getSchedulerEventForChannel", notes = "", response = SchEventDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/{shortName}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<SchEventDTO> getSchedulerEventForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                    @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName
    );

    @ApiOperation(value = "updateSchedulerEventForChannel", notes = "", response = SchEventDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchEventDTO.class),
        @ApiResponse(code = 201, message = "Created", response = SchEventDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchEventDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchEventDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchEventDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<SchEventDTO> updateSchedulerEventForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                       @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventDTO schEventDTO);

}
