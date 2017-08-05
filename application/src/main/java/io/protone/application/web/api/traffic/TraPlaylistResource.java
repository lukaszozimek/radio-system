package io.protone.application.web.api.traffic;


import io.protone.traffic.api.dto.TraPlaylistDTO;
import io.protone.traffic.api.dto.TraShuffleAdvertisementDTO;
import io.protone.traffic.api.dto.thin.TraPlaylistThinDTO;
import io.protone.traffic.service.shuffle.exception.TrafficShuffleReindexException;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by lukaszozimek on 14.05.2017.
 */


@Api(value = "protone", description = "Protone backend API documentation")
public interface TraPlaylistResource {
    @ApiOperation(value = "creatChannelTrafficPlaylist", notes = "", response = TraPlaylistDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraPlaylistDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraPlaylistDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraPlaylistDTO> creatChannelTrafficPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @RequestBody TraPlaylistDTO traPlaylistDTO) throws URISyntaxException;


    @ApiOperation(value = "deleteChannelTrafficPlaylist", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}",
        produces = {"*/*"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteChannelTrafficPlaylistUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                 @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);


    @ApiOperation(value = "getChannelTrafficPlaylist", notes = "", response = TraPlaylistDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraPlaylistDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraPlaylistDTO> getChannelTrafficPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                     @ApiParam(value = "date", required = true) @Valid @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    @ApiOperation(value = "getAllChannelTrafficPlaylist", notes = "", response = TraPlaylistDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraPlaylistDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraPlaylistThinDTO>> getAllChannelTrafficPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                  @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "getAllChannelTrafficPlaylistInRange", notes = "", response = TraPlaylistDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraPlaylistDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{fromDate}/{toDate}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraPlaylistDTO>> getAllChannelTrafficPlaylistInRangeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                     @ApiParam(value = "fromDate", required = true) @PathVariable("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                                                     @ApiParam(value = "toDate", required = true) @PathVariable("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate);

    @ApiOperation(value = "createChannelTrafficPlaylistInRange", notes = "", response = TraPlaylistDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraPlaylistDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/batch",
        produces = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<List<TraPlaylistDTO>> createChannelTrafficPlaylistInRangeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                      @ApiParam(value = "traPlaylistDTO", required = true) @Valid @RequestBody List<TraPlaylistDTO> traPlaylistDTO) throws URISyntaxException;


    @ApiOperation(value = "getDownloadChannelTrafficPlaylist", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}/download",
        produces = {"application/json"},
        method = RequestMethod.GET)
    void getDownloadChannelTrafficPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                   @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                   HttpServletResponse response) throws IOException;


    @ApiOperation(value = "updateChannelTrafficPlaylist", notes = "", response = TraPlaylistDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraPlaylistDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraPlaylistDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraPlaylistDTO> updateChannelTrafficPlaylistUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "traPlaylistDTO", required = true) @Valid @RequestBody TraPlaylistDTO traPlaylistDTO) throws URISyntaxException;

    @ApiOperation(value = "shuffleCommercial", notes = "", response = TraPlaylistDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraPlaylistDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraPlaylistDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraPlaylistDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraPlaylistDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraPlaylistDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/advertisment/shuffle",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<List<TraPlaylistDTO>> shuffleCommercialUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                   @ApiParam(value = "traShuffleAdvertismentDTO", required = true) @RequestBody TraShuffleAdvertisementDTO traShuffleAdvertismentDTO) throws InterruptedException, TrafficShuffleReindexException;



}
