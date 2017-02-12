package io.protone.custom.web.rest.network.scheduler;

import io.protone.custom.service.dto.SchPlaylistPT;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-01-11T20:29:48.904Z")

@Api(value = "custom", description = "the api API")
public interface ApiNetworkSchedulerPlaylist {


    @ApiOperation(value = "updateSchedulerPlaylist", notes = "", response = SchPlaylistPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/playlist",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<SchPlaylistPT> updateSchedulerPlaylistUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT schdeulerTemplate);


    @ApiOperation(value = "createSchedulerPlaylist", notes = "", response = SchPlaylistPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 201, message = "Created", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/playlist",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SchPlaylistPT> creatSchedulerPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchPlaylistPT schdeulerTemplate);


    @ApiOperation(value = "getAllSchedulerPlaylist", notes = "", response = SchPlaylistPT.class, responseContainer = "List", tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/playlist",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<SchPlaylistPT>> getAllSchedulerPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut);


    @ApiOperation(value = "getSchedulerPlaylist", notes = "", response = SchPlaylistPT.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = SchPlaylistPT.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = SchPlaylistPT.class),
        @ApiResponse(code = 403, message = "Forbidden", response = SchPlaylistPT.class),
        @ApiResponse(code = 404, message = "Not Found", response = SchPlaylistPT.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/playlist/{date}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<SchPlaylistPT> getSchedulerPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "date", required = true) @PathVariable("date") String date);


    @ApiOperation(value = "deleteSchedulerPlaylist", notes = "", response = Void.class, tags={ "SCHEDULER", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class) })
    @RequestMapping(value = "/api/network/{networkShortcut}/scheduler/playlist/{date}",
        produces = { "*/*" },
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSchedulerPlaylistUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                            @ApiParam(value = "date", required = true) @PathVariable("date") String date);



}
