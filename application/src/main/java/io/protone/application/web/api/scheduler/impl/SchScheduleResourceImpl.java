package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchScheduleResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchScheduleDTO;
import io.protone.scheduler.api.dto.thin.SchScheduleThinDTO;
import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.mapper.SchScheduleMapper;
import io.protone.scheduler.service.SchScheduleService;
import io.protone.scheduler.service.schedule.build.SchScheduleBuilderService;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by lukaszozimek on 14.05.2017.
 */
@RestController
public class SchScheduleResourceImpl implements SchScheduleResource {
    private final Logger log = LoggerFactory.getLogger(SchScheduleResourceImpl.class);

    @Inject
    private SchScheduleService schScheduleService;

    @Inject
    private SchScheduleBuilderService schScheduleBuilderService;

    @Inject
    private SchScheduleMapper schScheduleMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;

    @Override
    public ResponseEntity<List<SchScheduleThinDTO>> getAllSchedulerScheduleForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                              @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchSchedule, for Channel {}, Network: {}", channelShortcut, networkShortcut);
        Slice<SchSchedule> entity = schScheduleService.findSchGridsForNetworkAndChannel(networkShortcut, channelShortcut, pagable);
        List<SchScheduleThinDTO> response = schScheduleMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<SchScheduleDTO> creatSchedulerScheduleForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "schScheduleDTO", required = true) @Valid @RequestBody SchScheduleDTO schScheduleDTO) throws URISyntaxException {
        log.debug("REST request to saveScheduleDTO SchSchedule: {}, for Channel {} Network: {}", schScheduleDTO, channelShortcut, networkShortcut);
        if (schScheduleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("SchSchedule", "idexists", "A new SchSchedule cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchSchedule traOrder = schScheduleMapper.DTO2DB(schScheduleDTO, corNetwork, corChannel);
        SchSchedule entity = schScheduleService.saveSchedule(traOrder);
        SchScheduleDTO response = schScheduleMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/channel/" + channelShortcut + "/scheduler/schedule/" + response.getDate()))
                .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerScheduleForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                             @ApiParam(value = "date", required = true) @PathVariable("date") LocalDate date) {
        log.debug("REST request to delete SchSchedule : {}, for Network: {}", date, networkShortcut);
        schScheduleService.deleteSchScheduleByNetworkAndChannelAndShortNAme(networkShortcut, channelShortcut, date);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traOrder", date.toString())).build();
    }

    @Override
    public ResponseEntity<SchScheduleDTO> getSchedulerScheduleForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                 @ApiParam(value = "date", required = true) @PathVariable("date") LocalDate date) {
        SchSchedule entity = schScheduleService.findSchGridForNetworkAndChannelAndDate(networkShortcut, channelShortcut, date);
        SchScheduleDTO response = schScheduleMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<SchScheduleDTO> updateSchedulerScheduleForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                    @ApiParam(value = "schScheduleDTO", required = true) @Valid @RequestBody SchScheduleDTO schScheduleDTO) throws URISyntaxException {
        log.debug("REST request to saveScheduleDTO SchSchedule : {}, for Channel {} Network: {}", schScheduleDTO, channelShortcut, networkShortcut);

        if (schScheduleDTO.getId() == null) {
            return creatSchedulerScheduleForChannelUsingPOST(networkShortcut, channelShortcut, schScheduleDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchSchedule traOrder = schScheduleMapper.DTO2DB(schScheduleDTO, corNetwork, corChannel);
        SchSchedule entity = schScheduleService.saveSchedule(traOrder);
        SchScheduleDTO response = schScheduleMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<SchScheduleDTO> buildSchedulerScheduleForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                   @ApiParam(value = "date", required = true) @PathVariable("date") LocalDate date,
                                                                                   @ApiParam(value = "gridShortName", required = true) @PathVariable("gridShortName") String gridShortName) throws URISyntaxException {
        return null;
    }

    @Override
    public ResponseEntity<SchScheduleDTO> buildDefaultSchedulerScheduleForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                          @ApiParam(value = "date", required = true) @PathVariable("date") LocalDate date) throws URISyntaxException {
        return null;
    }
}
