package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchLogConfigurationResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchLogConfigurationDTO;
import io.protone.scheduler.api.dto.thin.SchLogConfigurationThinDTO;
import io.protone.scheduler.domain.SchLogConfiguration;
import io.protone.scheduler.mapper.SchLogConfigurationMapper;
import io.protone.scheduler.service.SchLogConfigurationService;
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
public class SchLogConfigurationResourceImpl implements SchLogConfigurationResource {
    private final Logger log = LoggerFactory.getLogger(SchClockTemplateResourceImpl.class);


    @Inject
    private SchLogConfigurationService schLogConfigurationService;

    @Inject
    private SchLogConfigurationMapper schLogConfigurationMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;

    @Override
    public ResponseEntity<List<SchLogConfigurationThinDTO>> getAllLogsConfigurationsForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable) {

        log.debug("REST request to get all SchLogConfiguration, for Channel {}, Network: {}", channelShortcut, networkShortcut);

        Slice<SchLogConfiguration> entity = schLogConfigurationService.findSchLogConfigurationForNetworkAndChannel(networkShortcut, channelShortcut, pagable);
        List<SchLogConfigurationThinDTO> response = schLogConfigurationMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }


    @Override
    public ResponseEntity<SchLogConfigurationDTO> creatLogsConfigurationsForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                             @ApiParam(value = "schLogConfigurationDTO", required = true) @Valid @RequestBody SchLogConfigurationDTO schLogConfigurationDTO) throws URISyntaxException, CreateBucketException {
        log.debug("REST request to saveSchLogConfigurationDTO SchLogConfigurationDTO : {}, for Channel {} Network: {}", schLogConfigurationDTO, channelShortcut, networkShortcut);
        if (schLogConfigurationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("SchLogConfiguration", "idexists", "A new SchLogConfiguration cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchLogConfiguration schLogConfiguration = schLogConfigurationMapper.DTO2DB(schLogConfigurationDTO, corNetwork, corChannel);
        SchLogConfiguration entity = schLogConfigurationService.saveSchLogConfiguration(schLogConfiguration);
        SchLogConfigurationDTO response = schLogConfigurationMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/channel/" + channelShortcut + "/scheduler/event/" + response.getId()))
                .body(response);
    }


    @Override
    public ResponseEntity<Void> deleteLogsConfigurationsForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                              @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete SchLogConfiguration : {}, for Network: {}", id, networkShortcut);
        schLogConfigurationService.deleteSchLogConfigurationByNetworkAndChannelAndId(networkShortcut, channelShortcut, id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traOrder", id.toString())).build();
    }


    @Override
    public ResponseEntity<SchLogConfigurationDTO> getLogsConfigurationsForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                          @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get SchLogConfiguration : {}, for Network: {}", id, networkShortcut);

        SchLogConfiguration entity = schLogConfigurationService.findSchLogConfigurationForNetworkAndChannelAndId(networkShortcut, channelShortcut, id);
        SchLogConfigurationDTO response = schLogConfigurationMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @Override
    public ResponseEntity<SchLogConfigurationDTO> updateLogsConfigurationsForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                             @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                             @ApiParam(value = "schLogConfigurationDTO", required = true) @Valid @RequestBody SchLogConfigurationDTO schLogConfigurationDTO) throws URISyntaxException, CreateBucketException {
        log.debug("REST request to saveSchLogConfigurationDTO SchLogConfigurationDTO : {}, for Channel {} Network: {}", schLogConfigurationDTO, channelShortcut, networkShortcut);
        if (schLogConfigurationDTO.getId() == null) {
            return creatLogsConfigurationsForChannelUsingPOST(networkShortcut, channelShortcut, schLogConfigurationDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);

        CorChannel corChannel = corChannelService.findChannel(networkShortcut, channelShortcut);
        SchLogConfiguration schEventConfiguration = schLogConfigurationMapper.DTO2DB(schLogConfigurationDTO, corNetwork, corChannel);
        SchLogConfiguration entity = schLogConfigurationService.saveSchLogConfiguration(schEventConfiguration);
        SchLogConfigurationDTO response = schLogConfigurationMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }


}
