package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchLogResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchLogDTO;
import io.protone.scheduler.api.dto.thin.SchLogThinDTO;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.mapper.SchLogMapper;
import io.protone.scheduler.service.SchLogService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by lukaszozimek on 14.05.2017.
 */
@RestController
public class SchLogResourceImpl implements SchLogResource {
    private final Logger log = LoggerFactory.getLogger(SchLogResourceImpl.class);

    @Inject
    private SchLogService schLogService;

    @Inject
    private SchLogMapper schLogMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;

    public ResponseEntity<List<SchLogThinDTO>> getAllLogsForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                            @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                            @ApiParam(value = "extension", required = true) @PathVariable("extension") String extension,
                                                                            @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchLog, for Channel {}, Network: {}", channelShortcut, networkShortcut);

        Slice<SchLog> entity = schLogService.findSchLogForNetworkAndChannelExtension(networkShortcut, channelShortcut, extension, pagable);
        List<SchLogThinDTO> response = schLogMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }


    public ResponseEntity<List<SchLogDTO>> uploadLogForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                        @ApiParam(value = "extension", required = true) @PathVariable("extension") String extension,
                                                                        @ApiParam(value = "files", required = true) @PathParam("files") MultipartFile[] files) throws URISyntaxException {
        log.debug("REST request to saveSchLogDTO SchLogDTO : {}, for Channel {} Network: {}", channelShortcut, networkShortcut);

        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        List<SchLog> entity = schLogService.saveSchLog(files, corNetwork, corChannel);
        List<SchLogDTO> response = schLogMapper.DBs2DTOs(entity);
        return ResponseEntity.created(URI.create(""))
                .body(response);

    }


    public ResponseEntity<Void> deleteLogForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                               @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                               @ApiParam(value = "extension", required = true) @PathVariable("extension") String extension,
                                                               @ApiParam(value = "date", required = true) @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.debug("REST request to delete SchLog : {}, for Network: {}", date, networkShortcut);
        schLogService.deleteSchLogByNetworkAndChannelAndDateAndExtension(networkShortcut, channelShortcut, date, extension);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traOrder", date.toString())).build();
    }
}
