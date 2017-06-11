package io.protone.web.api.traffic;

import io.protone.web.rest.dto.traffic.TraMediaPlanDTO;
import io.protone.web.rest.dto.traffic.thin.TraMediaPlanThinDTO;
import io.swagger.annotations.*;
import org.apache.tika.exception.TikaException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by lukaszozimek on 14.05.2017.
 */


@Api(value = "protone", description = "Protone backend API documentation")
public interface TraMediaPlanResource {
    @ApiOperation(value = "uploadChannelTrafficMediaPlan", notes = "", response = TraMediaPlanDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraMediaPlanDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraMediaPlanDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraMediaPlanDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraMediaPlanDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraMediaPlanDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/mediaplan",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    ResponseEntity<TraMediaPlanDTO> uploadChannelTrafficMediaPlanUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "traMediaPlanDescriptorDTO", required = true) @PathParam("parsingInfo") String traMediaPlanDescriptorDTO,
                                                                           @ApiParam(value = "files", required = true) @PathParam("files") MultipartFile file) throws URISyntaxException, TikaException, SAXException, IOException;


    @ApiOperation(value = "deleteChannelTrafficMediaPlan", notes = "", response = Void.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Void.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/mediaplan/{id}",
        produces = {"*/*"},
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteChannelTrafficMediaPlanUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                  @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                  @ApiParam(value = "id", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "getChannelTrafficMediaPlan", notes = "", response = TraMediaPlanDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraMediaPlanDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraMediaPlanDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraMediaPlanDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraMediaPlanDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/mediaplan/{id}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraMediaPlanDTO> getChannelTrafficMediaPlanUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                       @ApiParam(value = "id", required = true) @PathVariable("id") Long id);

    @ApiOperation(value = "getAllChannelTrafficMediaPlan", notes = "", response = TraMediaPlanDTO.class, tags = {"SCHEDULER",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraMediaPlanDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraMediaPlanDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraMediaPlanDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraMediaPlanDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/mediaplan",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<TraMediaPlanThinDTO>> getAllChannelTrafficMediaPlanUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "pagable", required = true) Pageable pagable);

    @ApiOperation(value = "updateChannelTrafficMediaPlan", notes = "", response = TraMediaPlanDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraMediaPlanDTO.class),
        @ApiResponse(code = 201, message = "Created", response = TraMediaPlanDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraMediaPlanDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraMediaPlanDTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraMediaPlanDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/mediaplan",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.PUT)
    ResponseEntity<TraMediaPlanDTO> updateChannelTrafficMediaPlanUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "traMediaPlanDTO", required = true) @Valid @RequestBody TraMediaPlanDTO traPlaylistDTO) throws URISyntaxException;
}
