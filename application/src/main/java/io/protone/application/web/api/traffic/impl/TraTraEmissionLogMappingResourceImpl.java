package io.protone.application.web.api.traffic.impl;

import io.protone.application.web.api.traffic.TraTraEmissionLogMappingResource;
import io.protone.traffic.api.dto.TraEmissionLogDescriptorDTO;
import io.protone.traffic.api.dto.TraPlaylistDiffDTO;
import io.protone.traffic.mapper.TraMediaPlanEmissionMapper;
import io.protone.traffic.mapper.TraPlaylistMapper;
import io.protone.traffic.service.log.TraEmissionLogService;
import io.protone.traffic.service.log.TraLogDiff;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by lukaszozimek on 14/12/2017.
 */
@RestController
public class TraTraEmissionLogMappingResourceImpl implements TraTraEmissionLogMappingResource {
    private final Logger log = LoggerFactory.getLogger(TraTraEmissionLogMappingResourceImpl.class);

    @Inject
    @Qualifier("traPlnLogService")
    private TraEmissionLogService traEmissionLogService;

    @Inject
    private TraPlaylistMapper traPlaylistMapper;

    @Inject
    private TraMediaPlanEmissionMapper traMediaPlanEmissionMapper;

    @Override
    public ResponseEntity<TraPlaylistDiffDTO> assigneMediaPlanOnPlaylistsUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                   @ApiParam(value = "traEmissionLogDescriptorDTO", required = true) @RequestPart("traEmissionLogDescriptorDTO")
                                                                                   @Valid TraEmissionLogDescriptorDTO traEmissionLogDescriptorDTO,
                                                                                   @ApiParam(value = "files", required = true) @RequestPart("file") MultipartFile file) throws IOException {

        TraLogDiff traLogDiff = traEmissionLogService.transform(traEmissionLogDescriptorDTO, file, organizationShortcut, channelShortcut);
        TraPlaylistDiffDTO response = new TraPlaylistDiffDTO();
        if (traLogDiff != null) {
            response.setParsedFromExcel(traMediaPlanEmissionMapper.DBTraLogEmissions2DTOs(traLogDiff.getTraLogEmissions()));
            response.setEntityPlaylist(traPlaylistMapper.DBs2DTOs(traLogDiff.getEntityPlaylist()));
        }
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
