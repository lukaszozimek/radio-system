package io.protone.web.api.traffic;

import io.protone.service.traffic.TraPlaylistMediaPlanMappingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lukaszozimek on 12/06/2017.
 */

public interface TraMediaPlanMappingResource {
    @ApiOperation(value = "assigneMediaPlanOnPlaylists", notes = "", response = TraPlaylistMediaPlanMappingService.PlaylistDiff.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = TraPlaylistMediaPlanMappingService.PlaylistDiff.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TraPlaylistMediaPlanMappingService.PlaylistDiff.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TraPlaylistMediaPlanMappingService.PlaylistDiff.class),
        @ApiResponse(code = 404, message = "Not Found", response = TraPlaylistMediaPlanMappingService.PlaylistDiff.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/assigne/mediaplan/{mediaPlanId}/advertisement/{advertisementId}",
        produces = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<TraPlaylistMediaPlanMappingService.PlaylistDiff> assigneMediaPlanOnPlaylistsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                        @ApiParam(value = "mediaPlanId", required = true) @PathVariable("mediaPlanId") Long mediaPlanId,
                                                                                                        @ApiParam(value = "advertismentId", required = true) @PathVariable("advertisementId") Long advertismentId);
}
