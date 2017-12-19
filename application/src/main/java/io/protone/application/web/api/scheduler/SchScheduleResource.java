package io.protone.application.web.api.scheduler;


import io.protone.scheduler.api.dto.SchScheduleDTO;
import io.protone.scheduler.api.dto.thin.SchScheduleThinDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@Api(value = "protone", description = "Protone backend API documentation")
public interface SchScheduleResource {

    @ApiOperation(value = "getAllSchedulerScheduleForChannel", notes = "", response = SchScheduleDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchScheduleDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchScheduleDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchScheduleDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchScheduleDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<SchScheduleThinDTO>> getAllSchedulerScheduleForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "getAllSchedulerScheduleFromToDateForChannel", notes = "", response = SchScheduleDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchScheduleDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchScheduleDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchScheduleDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchScheduleDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule/from/{from}/to/{to}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<SchScheduleThinDTO>> getAllSchedulerScheduleFromToDateForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                 @ApiParam(value = "from", required = true) @PathVariable("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                                                                 @ApiParam(value = "to", required = true) @PathVariable("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to);

    @ApiOperation(value = "createSchedulerForChannelSchedule", notes = "", response = SchScheduleDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchScheduleDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchScheduleDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchScheduleDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchScheduleDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchScheduleDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<SchScheduleDTO> creatSchedulerScheduleForChannelUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "schScheduleDTO", required = true) @Valid @RequestBody SchScheduleDTO schScheduleDTO) throws URISyntaxException;

    @ApiOperation(value = "deleteSchedulerScheduleForChannel", notes = "", response = Void.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Void.class),
            @ApiResponse(code = 204, message = "No Content", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule/{date}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerScheduleForChannelUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                      @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);


    @ApiOperation(value = "getSchedulerScheduleForChannel", notes = "", response = SchScheduleDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchScheduleDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchScheduleDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchScheduleDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchScheduleDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule/{date}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<SchScheduleDTO> getSchedulerScheduleForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    @ApiOperation(value = "updateSchedulerScheduleForChannel", notes = "", response = SchScheduleDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchScheduleDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchScheduleDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchScheduleDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchScheduleDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchScheduleDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<SchScheduleDTO> updateSchedulerScheduleForChannelUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "schScheduleDTO", required = true) @Valid @RequestBody SchScheduleDTO schScheduleDTO) throws URISyntaxException;

    @ApiOperation(value = "buildSchedulerScheduleForDateForChannel", notes = "", response = SchScheduleDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchScheduleDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchScheduleDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchScheduleDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchScheduleDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchScheduleDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule/{date}/{gridShortName}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<SchScheduleDTO> buildSchedulerScheduleForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                            @ApiParam(value = "gridShortName", required = true) @PathVariable("gridShortName") String gridShortName) throws Exception;

    @ApiOperation(value = "buildDefaultSchedulerScheduleForChannel", notes = "", response = SchScheduleDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchScheduleDTO.class),
            @ApiResponse(code = 201, message = "Created", response = SchScheduleDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = SchScheduleDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = SchScheduleDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = SchScheduleDTO.class)})
    @RequestMapping(value = "/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule/{date}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<SchScheduleDTO> buildDefaultSchedulerScheduleForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                   @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws URISyntaxException;

}
