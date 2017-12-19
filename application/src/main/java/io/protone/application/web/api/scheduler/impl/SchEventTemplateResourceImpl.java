package io.protone.application.web.api.scheduler.impl;


import io.protone.application.web.api.scheduler.SchEventTemplateResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.application.web.rest.util.PaginationUtil;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchEventTemplateDTO;
import io.protone.scheduler.api.dto.thin.SchEventTemplateThinDTO;
import io.protone.scheduler.domain.SchEventTemplate;
import io.protone.scheduler.mapper.SchEventTemplateMapper;
import io.protone.scheduler.service.SchEventTemplateService;
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
public class SchEventTemplateResourceImpl implements SchEventTemplateResource {
    private final Logger log = LoggerFactory.getLogger(SchEventTemplateResourceImpl.class);

    @Inject
    private SchEventTemplateService schEventTemplateService;

    @Inject
    private SchEventTemplateMapper schEventTemplateMapper;
    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;

    @Override
    public ResponseEntity<List<SchEventTemplateThinDTO>> getAllSchedulerTemplatesForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                                    @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                    @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchEventTemplate, for Channel {}, Network: {}", channelShortcut, organizationShortcut);

        Slice<SchEventTemplate> entity = schEventTemplateService.findSchEventTemplatesForNetworkAndChannel(organizationShortcut, channelShortcut, pagable);
        List<SchEventTemplateThinDTO> response = schEventTemplateMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<SchEventTemplateThinDTO>> getAllSchedulerEventGroupedByCategoryForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                                                 @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                                                 @ApiParam(value = "name", required = true) @PathVariable("name") String name,
                                                                                                                 @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all SchEventTemplate, for Channel {}, Network: {}", channelShortcut, organizationShortcut);

        Slice<SchEventTemplate> entity = schEventTemplateService.findAllEventsByCategoryName(organizationShortcut, channelShortcut, name, pagable);
        List<SchEventTemplateThinDTO> response = schEventTemplateMapper.DBs2ThinDTOs(entity.getContent());
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        PaginationUtil.generateSliceHttpHeaders(entity),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        PaginationUtil.generateSliceHttpHeaders(entity), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchEventTemplateDTO> creatSchedulerTemplatesForChannelUsingPOST(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                          @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventTemplateDTO schEventDTO) throws URISyntaxException {
        log.debug("REST request to saveSchEventTemplateDTO SchEventTemplateDTO : {}, for Channel {} Network: {}", schEventDTO, channelShortcut, organizationShortcut);
        if (schEventDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("SchEventTemplate", "idexists", "A new SchEventTemplate cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);

        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        SchEventTemplate traOrder = schEventTemplateMapper.DTO2DB(schEventDTO, corNetwork, corChannel);
        SchEventTemplate entity = schEventTemplateService.saveEventConfiguration(traOrder);
        SchEventTemplateDTO response = schEventTemplateMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/organization/" + organizationShortcut + "/channel/" + channelShortcut + "/scheduler/event/" + response.getShortName()))
                .body(response);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerEventForChannelUsingDELETE(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                          @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                          @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete SchEventTemplate : {}, for Network: {}", shortName, organizationShortcut);
        schEventTemplateService.deleteSchEventTemplateByNetworkAndChannelAndShortName(organizationShortcut, channelShortcut, shortName);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<SchEventTemplateDTO> getSchedulerEventForChannelUsingGET(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                   @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                   @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get SchEventTemplate : {}, for Network: {}", shortName, organizationShortcut);

        SchEventTemplateDTO response = schEventTemplateService.findSchEventTemplatesForNetworkAndChannelAndShortNameDTO(organizationShortcut, channelShortcut, shortName);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<SchEventTemplateDTO> updateSchedulerEventForChannelUsingPUT(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                                                                                      @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                      @ApiParam(value = "schEventDTO", required = true) @Valid @RequestBody SchEventTemplateDTO schEventDTO) throws URISyntaxException {
        log.debug("REST request to saveSchEventTemplateDTO SchEventTemplateDTO : {}, for Channel {} Network: {}", schEventDTO, channelShortcut, organizationShortcut);
        if (schEventDTO.getId() == null) {
            return creatSchedulerTemplatesForChannelUsingPOST(organizationShortcut, channelShortcut, schEventDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(organizationShortcut);

        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        SchEventTemplate schEventConfiguration = schEventTemplateMapper.DTO2DB(schEventDTO, corNetwork, corChannel);
        SchEventTemplate entity = schEventTemplateService.saveEventConfiguration(schEventConfiguration);
        SchEventTemplateDTO response = schEventTemplateMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }
}
