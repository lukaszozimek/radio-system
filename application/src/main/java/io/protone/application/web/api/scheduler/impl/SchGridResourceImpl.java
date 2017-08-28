package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchGridResource;
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
public class SchGridResourceImpl implements SchGridResource {
    private final Logger log = LoggerFactory.getLogger(SchGridResourceImpl.class);

    @Inject
    private SchGridService schGridService;

    @Inject
    private SchGridMapper schGridMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;


    @Override
    public ResponseEntity<SchGridDTO> getSchedulerGridForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get SchGrid : {}, for Network: {}", shortName, networkShortcut);
        SchGrid entity = schGridService.findSchGridForNetworkAndChannelAndShortName(networkShortcut, channelShortcut, shortName);
        SchGridDTO response = schGridMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<SchGridThinDTO>> getAllSchedulerGridForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchGrid, for Channel {}, Network: {}", channelShortcut, networkShortcut);
        Slice<SchGrid> entity = schGridService.findSchGridsForNetworkAndChannel(networkShortcut, channelShortcut, pagable);
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
    public ResponseEntity<SchGridDTO> creatSchedulerPlaylistForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                @ApiParam(value = "schGridDTO", required = true) @Valid @RequestBody SchGridDTO schGridDTO) throws URISyntaxException {
        log.debug("REST request to saveSchGridDTO SchGridDTO : {}, for Channel {} Network: {}", schGridDTO, channelShortcut, networkShortcut);
        if (schGridDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("SchGrid", "idexists", "A new SchGrid cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchGrid traOrder = schGridMapper.DTO2DB(schGridDTO, corNetwork, corChannel);
        SchGrid entity = schGridService.saveGrid(traOrder);
        SchGridDTO response = schGridMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/channel/" + channelShortcut + "/scheduler/grid/" + response.getShortName()))
                .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerGridForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete SchGridDTO : {}, for Network: {}", shortName, networkShortcut);
        schGridService.deleteSchGridByNetworkAndChannelAndShortNAme(networkShortcut, channelShortcut, shortName);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traOrder", shortName.toString())).build();
    }

    @Override
    public ResponseEntity<SchGridDTO> updateSchedulerGridForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "schGridDTO", required = true) @Valid @RequestBody SchGridDTO schGridDTO) throws URISyntaxException {
        log.debug("REST request to updateSchGridDTO SchGridDTO : {}, for Channel {} Network: {}", schGridDTO, channelShortcut, networkShortcut);
        if (schGridDTO.getId() == null) {
            return creatSchedulerPlaylistForChannelUsingPOST(networkShortcut, channelShortcut, schGridDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchGrid traOrder = schGridMapper.DTO2DB(schGridDTO, corNetwork, corChannel);
        SchGrid entity = schGridService.saveGrid(traOrder);
        SchGridDTO response = schGridMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }
}
