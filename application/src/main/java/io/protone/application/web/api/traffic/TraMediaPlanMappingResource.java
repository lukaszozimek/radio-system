package io.protone.application.web.api.traffic;


import io.protone.traffic.api.dto.TraMediaPlanAdvertisementAssigneDTO;
import io.protone.traffic.api.dto.TraPlaylistDiffDTO;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lukaszozimek on 12/06/2017.
 */
@Api(value = "protone", description = "Protone backend API documentation")
public interface TraMediaPlanMappingResource {

    @ApiOperation(value = "assigneMediaPlanOnPlaylists", notes = "", response = TraPlaylistDiffDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraPlaylistDiffDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraPlaylistDiffDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraPlaylistDiffDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraPlaylistDiffDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/assigne/mediaplan",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<TraPlaylistDiffDTO> assigneMediaPlanOnPlaylistsUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                           @ApiParam(value = "traMediaPlanAdvertisementAssigneDTO") @RequestBody TraMediaPlanAdvertisementAssigneDTO traMediaPlanAdvertisementAssigneDTO);
}
