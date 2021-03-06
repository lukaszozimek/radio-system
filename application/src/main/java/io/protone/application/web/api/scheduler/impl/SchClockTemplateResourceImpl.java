package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchClockTemplateResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchClockTemplateDTO;
import io.protone.scheduler.api.dto.thin.SchClockTemplateThinDTO;
import io.protone.scheduler.domain.SchClockTemplate;
import io.protone.scheduler.mapper.SchClockTemplateMapper;
import io.protone.scheduler.service.SchClockTemplateService;
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
public class SchClockTemplateResourceImpl implements SchClockTemplateResource {
    private final Logger log = LoggerFactory.getLogger(SchClockTemplateResourceImpl.class);


    @Inject
    private SchClockTemplateService schClockTemplateService;

    @Inject
    private SchClockTemplateMapper schClockTemplateMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;

    @Override
    public ResponseEntity<SchClockTemplateDTO> creatSchedulerClockForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                      @ApiParam(value = "clockDTO", required = true) @Valid @RequestBody SchClockTemplateDTO clockDTO) throws URISyntaxException {
        log.debug("REST request to savSchClockConfigurationDTO SchClockTemplateDTO : {}, for Channel {} Network: {}", clockDTO, channelShortcut, networkShortcut);
        if (clockDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("SchClockTemplate", "idexists", "A new SchClockTemplate cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchClockTemplate schClock = schClockTemplateMapper.DTO2DB(clockDTO, corNetwork, corChannel);
        SchClockTemplateDTO response = schClockTemplateService.saveClockConfiguration(schClock);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/channel/" + channelShortcut + "/scheduler/clock/" + response.getShortName()))
                .body(response);
    }

    @Override
    public ResponseEntity<List<SchClockTemplateThinDTO>> getAllSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchClockTemplate, for Channel {}, Network: {}", channelShortcut, networkShortcut);
        Slice<SchClockTemplate> entity = schClockTemplateService.findSchClockConfigurationsForNetworkAndChannel(networkShortcut, channelShortcut, pagable);
        List<SchClockTemplateThinDTO> response = schClockTemplateMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<SchClockTemplateThinDTO>> getAllSchedulerClockForChannelGroupedByCategoryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                                 @ApiParam(value = "name", required = true) @PathVariable("name") String name,
                                                                                                                 @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchClockTemplate, for Channel {}, Network: {}", channelShortcut, networkShortcut);
        Slice<SchClockTemplate> entity = schClockTemplateService.findAllClocksByCategoryName(networkShortcut, channelShortcut, name, pagable);
        List<SchClockTemplateThinDTO> response = schClockTemplateMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchClockTemplateDTO> getSchedulerClockForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get SchClockTemplate : {}, for Network: {}", shortName, networkShortcut);
        SchClockTemplateDTO response = schClockTemplateService.findDTOSchClockConfigurationForNetworkAndChannelAndShortName(networkShortcut, channelShortcut, shortName);

        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchClockTemplateDTO> updateSchedulerClockForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                      @ApiParam(value = "clockDTO", required = true) @Valid @RequestBody SchClockTemplateDTO clockDTO) throws URISyntaxException {
        log.debug("REST request to saveSchClockConfigurationDTO SchClockTemplateDTO : {}, for Channel {} Network: {}", clockDTO, channelShortcut, networkShortcut);

        if (clockDTO.getId() == null) {
            return creatSchedulerClockForChannelUsingPOST(networkShortcut, channelShortcut, clockDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchClockTemplate schClock = schClockTemplateMapper.DTO2DB(clockDTO, corNetwork, corChannel);
        SchClockTemplateDTO response = schClockTemplateService.saveClockConfiguration(schClock);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerClockForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete SchClockTemplate : {}, for Network: {}", shortName, networkShortcut);
        schClockTemplateService.deleteSchClockConfigurationByNetworkAndChannelAndShortName(networkShortcut, channelShortcut, shortName);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("SchClockTemplate", shortName.toString())).build();
    }

}
