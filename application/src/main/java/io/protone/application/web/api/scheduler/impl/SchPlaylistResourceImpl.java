package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchPlaylistResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchBlockChainDTO;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.api.dto.thin.SchPlaylistThinDTO;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.mapper.SchPlaylistMapper;
import io.protone.scheduler.service.SchPlaylistService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by lukaszozimek on 14.05.2017.
 */
@RestController
public class SchPlaylistResourceImpl implements SchPlaylistResource {
    private final Logger log = LoggerFactory.getLogger(SchPlaylistResourceImpl.class);
    @Inject
    private SchPlaylistService schPlaylistService;

    @Inject
    private SchPlaylistMapper schPlaylistMapper;

    @Inject
    private CorChannelService corChannelService;

    @Override
    public ResponseEntity<List<SchPlaylistThinDTO>> getAllSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                              @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchPlaylist, for Channel {}, Network: {}", channelShortcut, organizationShortcut);
        Slice<SchPlaylist> entity = schPlaylistService.findSchPlaylistForNetworkAndChannel(organizationShortcut, channelShortcut, pagable);
        List<SchPlaylistThinDTO> response = schPlaylistMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchPlaylistDTO> creatSchedulerPlaylistForChannelUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "schPlaylistDTO", required = true) @Valid @RequestBody SchPlaylistDTO schPlaylistDTO) throws URISyntaxException {
        log.debug("REST request to saveSchPlaylistDTO SchPlaylistDTO : {}, for Channel {} Network: {}", schPlaylistDTO, channelShortcut, organizationShortcut);
        if (schPlaylistDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("SchPlaylist", "idexists", "A new SchPlaylist cannot already have an ID")).body(null);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        SchPlaylist schPlaylist = schPlaylistMapper.DTO2DB(schPlaylistDTO, corChannel);
        SchPlaylist entity = schPlaylistService.saveSchPlaylist(schPlaylist);
        SchPlaylistDTO response = schPlaylistMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/organization/" + channelShortcut + "/scheduler/event/" + response.getId()))
                .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerPlaylistForChannelUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.debug("REST request to delete SchPlaylistDTO : {}, for Network: {}", date, organizationShortcut);
        schPlaylistService.deleteSchPlaylistByNetworkAndChannelAndDate(organizationShortcut, channelShortcut, date);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("SchPlaylistDTO", date.toString())).build();

    }

    @Override
    public ResponseEntity<Void> deleteSchedulerPlaylistElementForChannelUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                                    @ApiParam(value = "seqNumber", required = true) @PathVariable("seqNumber") Long seqNumber) {
        return null;
    }

    @Override
    public ResponseEntity<SchPlaylistDTO> getSchedulerPlaylistForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                 @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.debug("REST request to get SchPlaylist : {}, for Network: {}", date, organizationShortcut);
        SchPlaylistDTO response = schPlaylistService.findSchPlaylistForNetworkAndChannelAndDate(organizationShortcut, channelShortcut, date);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchEmissionDTO> getSchedulerPlaylistElementForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                        @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                                        @ApiParam(value = "seqNumber", required = true) @PathVariable("seqNumber") Long seqNumber) {
        return null;
    }

    @Override
    public ResponseEntity<SchPlaylistDTO> updateSchedulerPlaylistForChannelUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "schPlaylistDTO", required = true) @Valid @RequestBody SchPlaylistDTO schPlaylistDTO) throws URISyntaxException {
        log.debug("REST request to saveScheduleDTO SchSchedule : {}, for Channel {} Network: {}", schPlaylistDTO, channelShortcut, organizationShortcut);

        if (schPlaylistDTO.getId() == null) {
            return creatSchedulerPlaylistForChannelUsingPOST(organizationShortcut, channelShortcut, schPlaylistDTO);
        }
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        SchPlaylist schPlaylist = schPlaylistMapper.DTO2DB(schPlaylistDTO, corChannel);
        SchPlaylist entity = schPlaylistService.saveSchPlaylist(schPlaylist);
        SchPlaylistDTO response = schPlaylistMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);

    }

    @Override
    public ResponseEntity<SchPlaylistDTO> moveElementInPlaylistUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                         @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                         @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                         @ApiParam(value = "from", required = true) @PathVariable("from") Long from,
                                                                         @ApiParam(value = "to", required = true) @PathVariable("to") Long to) {
        return null;
    }


    @Override
    public ResponseEntity<SchBlockChainDTO> getPlaylistBlock(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                             @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                             @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<SchPlaylistDTO> addElementInPlaylistUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                        @ApiParam(value = "seqNumber", required = true) @PathVariable("seqNumber") Long seqNumber, @RequestBody SchEmissionDTO emissionDTO) {
        return null;
    }
}
