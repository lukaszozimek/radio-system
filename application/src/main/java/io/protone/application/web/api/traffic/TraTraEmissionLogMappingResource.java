package io.protone.application.web.api.traffic;


import io.protone.traffic.api.dto.TraEmissionLogDescriptorDTO;
import io.protone.traffic.api.dto.TraPlaylistDiffDTO;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by lukaszozimek on 14.05.2017.
 */


@Api(value = "protone", description = "Protone backend API documentation")
public interface TraTraEmissionLogMappingResource {
    
    @ApiOperation(value = "assigneLognOnPlaylists", notes = "", response = TraPlaylistDiffDTO.class, tags = {"TRAFFIC",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TraPlaylistDiffDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = TraPlaylistDiffDTO.class),
            @ApiResponse(code = 403, message = "Forbidden", response = TraPlaylistDiffDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = TraPlaylistDiffDTO.class)})
    @RequestMapping(value = "/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/assigne/log",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<TraPlaylistDiffDTO> assigneMediaPlanOnPlaylistsUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "traEmissionLogDescriptorDTO", required = true) @RequestPart("traEmissionLogDescriptorDTO") @Valid TraEmissionLogDescriptorDTO traEmissionLogDescriptorDTO,
                                                                            @ApiParam(value = "files", required = true) @RequestPart("file") MultipartFile file) throws IOException;


}
