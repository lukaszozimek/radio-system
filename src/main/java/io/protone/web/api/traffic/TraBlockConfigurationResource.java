package io.protone.web.api.traffic;

import io.protone.web.rest.dto.traffic.TraBlockConfigurationDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@Api(value = "protone", description = "Protone backend API documentation")
public interface TraBlockConfigurationResource {
    @ApiOperation(value = "createSchedulerForChannelPlaylist", notes = "", response = TraBlockConfigurationDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraBlockConfigurationDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraBlockConfigurationDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraBlockConfigurationDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraBlockConfigurationDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraBlockConfigurationDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/block",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraBlockConfigurationDTO> creatSchedulerScheduleForChannelUsingPOSTUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                @Valid @RequestBody TraBlockConfigurationDTO traBlockConfigurationDTO);


    @ApiOperation(value = "getAllSchedulerPlaylistForChannel", notes = "", response = TraBlockConfigurationDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraBlockConfigurationDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraBlockConfigurationDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraBlockConfigurationDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraBlockConfigurationDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/block",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraBlockConfigurationDTO> getAllSchedulerScheduleForChannelUsingGETUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                               @ApiParam(value = "pagable", required = true) Pageable pagable
                                                                                               );


    @ApiOperation(value = "getAllSchedulerPlaylistForChannel", notes = "", response = TraBlockConfigurationDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraBlockConfigurationDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraBlockConfigurationDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraBlockConfigurationDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraBlockConfigurationDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/block/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraBlockConfigurationDTO> getAllSchedulerScheduleForChannelUsingGETUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                               @ApiParam(value = "id", required = true) @PathVariable("id") Long id

    );

    @ApiOperation(value = "updateSchedulerPlaylistForChannel", notes = "", response = TraBlockConfigurationDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraBlockConfigurationDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraBlockConfigurationDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraBlockConfigurationDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraBlockConfigurationDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraBlockConfigurationDTO.class)})
    @RequestMapping(value = "/api/network/{networkShortcut}/channel/{channelShortcut}/traffic/block/{id}",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraBlockConfigurationDTO> updateSchedulerScheduleForChanneltUsingPUTUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                @ApiParam(value = "id", required = true) @PathVariable("id") Long id
    );


}
