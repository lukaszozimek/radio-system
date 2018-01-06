package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchGridConfigurationResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.api.dto.thin.SchGridThinDTO;
import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.mapper.SchGridMapper;
import io.protone.scheduler.service.SchGridService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Created by lukaszozimek on 14.05.2017.
 */
@RestController
public class SchGridConfigurationResourceImpl implements SchGridConfigurationResource {
    private final Logger log = LoggerFactory.getLogger(SchGridConfigurationResourceImpl.class);

    @Inject
    private SchGridService schGridService;

    @Inject
    private SchGridMapper schGridMapper;

    @Inject
    private CorChannelService corChannelService;


    @Override
    public ResponseEntity<SchGridDTO> getSchedulerGridForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get SchGrid : {}, for Network: {}", shortName, organizationShortcut);

        SchGridDTO response = schGridService.findSchGridForNetworkAndChannelAndShortNameDTO(organizationShortcut, channelShortcut, shortName);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<SchGridThinDTO>> getAllSchedulerDefaultGridForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                             @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchGrid, for Channel {}, Network: {}", channelShortcut, organizationShortcut);
        Slice<SchGrid> entity = schGridService.findAllDefaultGrids(organizationShortcut, channelShortcut, pagable);
        List<SchGridThinDTO> response = schGridMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<SchGridThinDTO>> getAllSchedulerGridForChannelGroupedByCategoryUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                       @ApiParam(value = "name", required = true) @PathVariable("name") String name,
                                                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchGrid, for Channel {}, Network: {}", channelShortcut, organizationShortcut);
        Slice<SchGrid> entity = schGridService.findAllGridsByCategoryNaem(organizationShortcut, channelShortcut, name, pagable);
        List<SchGridThinDTO> response = schGridMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<SchGridThinDTO>> getAllSchedulerGridForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchGrid, for Channel {}, Network: {}", channelShortcut, organizationShortcut);
        Slice<SchGrid> entity = schGridService.findSchGridsForNetworkAndChannel(organizationShortcut, channelShortcut, pagable);
        List<SchGridThinDTO> response = schGridMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchGridDTO> creatSchedulerPlaylistForChannelUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                @ApiParam(value = "schGridDTO", required = true) @Valid @RequestBody SchGridDTO schGridDTO) throws URISyntaxException {
        log.debug("REST request to saveSchGridDTO SchGridDTO : {}, for Channel {} Network: {}", schGridDTO, channelShortcut, organizationShortcut);
        if (schGridDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("SchGrid", "idexists", "A new SchGrid cannot already have an ID")).body(null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        SchGrid traOrder = schGridMapper.DTO2DB(schGridDTO,  corChannel);
        SchGridDTO response = schGridService.saveGrid(traOrder);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/organization/" + channelShortcut + "/scheduler/grid/" + response.getShortName()))
                .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerGridForChannelUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete SchGridDTO : {}, for Network: {}", shortName, organizationShortcut);
        schGridService.deleteSchGridByNetworkAndChannelAndShortNAme(organizationShortcut, channelShortcut, shortName);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traOrder", shortName.toString())).build();
    }

    @Override
    public ResponseEntity<SchGridDTO> updateSchedulerGridForChannelUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "schGridDTO", required = true) @Valid @RequestBody SchGridDTO schGridDTO) throws URISyntaxException {
        log.debug("REST request to updateSchGridDTO SchGridDTO : {}, for Channel {} Network: {}", schGridDTO, channelShortcut, organizationShortcut);
        if (schGridDTO.getId() == null) {
            return creatSchedulerPlaylistForChannelUsingPOST(organizationShortcut, channelShortcut, schGridDTO);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        SchGrid traOrder = schGridMapper.DTO2DB(schGridDTO,  corChannel);
        SchGridDTO response = schGridService.saveGrid(traOrder);
        return ResponseEntity.ok().body(response);
    }
}
