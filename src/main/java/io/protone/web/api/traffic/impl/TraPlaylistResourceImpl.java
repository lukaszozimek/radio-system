package io.protone.web.api.traffic.impl;

import io.protone.domain.CorNetwork;
import io.protone.domain.TraCampaign;
import io.protone.domain.TraOrder;
import io.protone.domain.TraPlaylist;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraPlaylistService;
import io.protone.web.api.traffic.TraPlaylistResource;
import io.protone.web.rest.dto.traffic.TraCampaignDTO;
import io.protone.web.rest.dto.traffic.TraOrderDTO;
import io.protone.web.rest.dto.traffic.TraPlaylistDTO;
import io.protone.web.rest.mapper.TraPlaylistMapper;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 14.05.2017.
 */


@RestController
public class TraPlaylistResourceImpl implements TraPlaylistResource {
    private final Logger log = LoggerFactory.getLogger(TraPlaylistResourceImpl.class);

    @Inject
    private TraPlaylistService traPlaylistService;

    @Inject
    private TraPlaylistMapper traPlaylistMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<TraPlaylistDTO> creatChannelTrafficPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @ApiParam(value = "traPlaylistDTO", required = true) @Valid @RequestBody TraPlaylistDTO traPlaylistDTO) throws URISyntaxException {
        log.debug("REST request to save TraPlaylist : {}, for Channel {} Network: {}", traPlaylistDTO, channelShortcut, networkShortcut);
        if (traPlaylistDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraPlaylist", "idexists", "A new TraPlaylist cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        TraPlaylist traOrder = traPlaylistMapper.DTO2DB(traPlaylistDTO, corNetwork);
        TraPlaylist entity = traPlaylistService.savePlaylist(traOrder);
        TraPlaylistDTO response = traPlaylistMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/traffic/order/" + response.getId()))
            .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteChannelTrafficPlaylistUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "date", required = true) @PathVariable("date") LocalDate date) {
        log.debug("REST request to delete TraOrder : {}, for Network: {}", date, networkShortcut);
        traPlaylistService.deleteOneTraPlaylistList(date, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traOrder", date.toString())).build();
    }

    @Override
    public ResponseEntity<TraPlaylistDTO> getChannelTrafficPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "date", required = true) @PathVariable("date") LocalDate date) {
        log.debug("REST request to get TraPlaylist : {}, for Network: {}", date, networkShortcut);
        TraPlaylist entity = traPlaylistService.getTraPlaylistList(date, networkShortcut);
        TraPlaylistDTO response = traPlaylistMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<TraPlaylistDTO>> getAllChannelTrafficPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                     @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                     @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraPlaylist, for Channel {}, Network: {}", channelShortcut, networkShortcut);
        List<TraPlaylist> entity = traPlaylistService.getAllPlaylistList(networkShortcut, pagable);
        List<TraPlaylistDTO> response = traPlaylistMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<TraPlaylistDTO>> getAllChannelTrafficPlaylistInRangeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                            @ApiParam(value = "fromDate", required = true) @PathVariable("fromDate") LocalDate fromDate,
                                                                                            @ApiParam(value = "toDate", required = true) @PathVariable("toDate") LocalDate toDate) {
        log.debug("REST request to get all TraPlaylist, for Channel {}, Network: {} in Range from {} to {}", channelShortcut, networkShortcut, fromDate, toDate);
        List<TraPlaylist> entity = traPlaylistService.getTraPlaylistListInRange(fromDate, toDate, networkShortcut);
        List<TraPlaylistDTO> response = traPlaylistMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<TraPlaylistDTO>> createChannelTrafficPlaylistInRangeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                             @ApiParam(value = "traPlaylistDTO", required = true) @Valid @RequestBody List<TraPlaylistDTO> traPlaylistDTOs) throws URISyntaxException {
        log.debug("REST request to save List of TraPlaylist : {}, for Channel {} Network: {}", traPlaylistDTOs, channelShortcut, networkShortcut);
        List<TraPlaylistDTO> traPlaylistDTOS = new ArrayList<>();
        traPlaylistDTOs.stream().forEach(traPlaylistDTO -> {
            if (traPlaylistDTO.getId() == null) {
                CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

                TraPlaylist traOrder = traPlaylistMapper.DTO2DB(traPlaylistDTO, corNetwork);
                TraPlaylist entity = traPlaylistService.savePlaylist(traOrder);
                traPlaylistDTOS.add(traPlaylistMapper.DB2DTO(entity));
            }
        });
        return ResponseEntity.created(new URI(""))
            .body(traPlaylistDTOS);
    }

    @Override
    public ResponseEntity<TraPlaylistDTO> getDownloadChannelTrafficPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "date", required = true) @PathVariable("date") LocalDate date) {
        return null;
    }

    @Override
    public ResponseEntity<TraPlaylistDTO> updateChannelTrafficPlaylistUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @ApiParam(value = "traPlaylistDTO", required = true) @Valid @RequestBody TraPlaylistDTO traPlaylistDTO) throws URISyntaxException {
        log.debug("REST request to update TraPlaylistDTO : {}, for Channel {}, Network: {}", traPlaylistDTO, channelShortcut, networkShortcut);
        if (traPlaylistDTO.getId() == null) {
            return creatChannelTrafficPlaylistUsingPOST(networkShortcut, channelShortcut, traPlaylistDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        TraPlaylist traOrder = traPlaylistMapper.DTO2DB(traPlaylistDTO, corNetwork);
        TraPlaylist entity = traPlaylistService.savePlaylist(traOrder);
        TraPlaylistDTO response = traPlaylistMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }
}
