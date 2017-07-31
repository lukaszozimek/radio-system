package io.protone.application.web.api.traffic.impl;


import io.protone.application.web.api.traffic.TraPlaylistResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.traffic.api.dto.TraPlaylistDTO;
import io.protone.traffic.api.dto.TraShuffleAdvertisementDTO;
import io.protone.traffic.domain.TraBlock;
import io.protone.traffic.domain.TraEmission;
import io.protone.traffic.domain.TraPlaylist;
import io.protone.traffic.mapper.TraPlaylistMapper;
import io.protone.traffic.service.TraAdvertisementShuffleService;
import io.protone.traffic.service.TraPlaylistService;
import io.protone.traffic.service.shuffle.exception.TrafficShuffleReindexException;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Inject
    private CorChannelService corChannelService;

    @Inject
    private TraAdvertisementShuffleService traAdvertisementShuffleService;

    @Override
    public ResponseEntity<TraPlaylistDTO> creatChannelTrafficPlaylistUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                               @Valid @RequestBody TraPlaylistDTO traPlaylistDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact TraPlaylist : {}, for Channel {} Network: {}", traPlaylistDTO, channelShortcut, networkShortcut);
        if (traPlaylistDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraPlaylist", "idexists", "A new TraPlaylist cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        TraPlaylist traOrder = traPlaylistMapper.DTO2DB(traPlaylistDTO, corNetwork, corChannel);
        TraPlaylist entity = traPlaylistService.savePlaylist(traOrder);
        TraPlaylistDTO response = traPlaylistMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/channel/" + channelShortcut + "/traffic/playlist/" + response.getPlaylistDate()))
            .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteChannelTrafficPlaylistUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.debug("REST request to delete TraOrder : {}, for Network: {}", date, networkShortcut);
        traPlaylistService.deleteOneTraPlaylistList(date, networkShortcut, channelShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traOrder", date.toString())).build();
    }

    @Override
    public ResponseEntity<TraPlaylistDTO> getChannelTrafficPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.debug("REST request to get TraPlaylist : {}, for Network: {}", date, networkShortcut);
        TraPlaylist entity = traPlaylistService.getTraPlaylistList(date, networkShortcut, channelShortcut);
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
        List<TraPlaylist> entity = traPlaylistService.getAllPlaylistList(networkShortcut, channelShortcut, pagable);
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
                                                                                            @ApiParam(value = "fromDate", required = true) @PathVariable("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                                                            @ApiParam(value = "toDate", required = true) @PathVariable("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        log.debug("REST request to get all TraPlaylist, for Channel {}, Network: {} in Range from {} to {}", channelShortcut, networkShortcut, fromDate, toDate);
        List<TraPlaylist> entity = traPlaylistService.getTraPlaylistListInRange(fromDate, toDate, networkShortcut, channelShortcut);
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
        log.debug("REST request to saveCorContact List of TraPlaylist : {}, for Channel {} Network: {}", traPlaylistDTOs, channelShortcut, networkShortcut);
        List<TraPlaylistDTO> traPlaylistDTOS = new ArrayList<>();
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);

        traPlaylistDTOs.stream().forEach(traPlaylistDTO -> {
            if (traPlaylistDTO.getId() == null) {
                TraPlaylist traOrder = traPlaylistMapper.DTO2DB(traPlaylistDTO, corNetwork, corChannel);
                TraPlaylist entity = traPlaylistService.savePlaylist(traOrder);
                traPlaylistDTOS.add(traPlaylistMapper.DB2DTO(entity));
            }
        });
        return ResponseEntity.created(new URI(""))
            .body(traPlaylistDTOS);
    }

    @Override
    public void getDownloadChannelTrafficPlaylistUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                          @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                          HttpServletResponse response) throws IOException {
        String csvFileName = date + ".csv";

        response.setContentType("text/csv");

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
            csvFileName);
        response.setHeader(headerKey, headerValue);

        TraPlaylist traPlaylist = traPlaylistService.getTraPlaylistList(date, networkShortcut, channelShortcut);
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
            CsvPreference.STANDARD_PREFERENCE);

        String[] header = {"id", "time_start", "time_stop"};

        csvWriter.writeHeader(header);
        for (TraBlock traBlock : traPlaylist.getPlaylists()) {
            for (TraEmission traEmission : traBlock.getEmissions()) {
                csvWriter.write(traEmission, header);
            }
        }

        csvWriter.close();
        response.setStatus(200);
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

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        TraPlaylist traOrder = traPlaylistMapper.DTO2DB(traPlaylistDTO, corNetwork, corChannel);
        TraPlaylist entity = traPlaylistService.savePlaylist(traOrder);
        TraPlaylistDTO response = traPlaylistMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<List<TraPlaylistDTO>> shuffleCommercialUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "traShuffleAdvertismentPT", required = true) @RequestBody TraShuffleAdvertisementDTO traShuffleAdvertismentDTO) throws InterruptedException, TrafficShuffleReindexException {
        log.debug("REST request to shuffle TraAdvertisments : {}, for Channel {}, Network: {}", traShuffleAdvertismentDTO.getLibMediaItemThinDTO(), channelShortcut, networkShortcut);
        List<TraPlaylist> entities = traAdvertisementShuffleService.shuffleCommercials(traShuffleAdvertismentDTO, networkShortcut, channelShortcut);
        List<TraPlaylistDTO> response = traPlaylistMapper.DBs2DTOs(entities);
        return ResponseEntity.ok().body(response);
    }

}
