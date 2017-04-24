package io.protone.custom.web.rest.network.channel.impl;

import io.protone.custom.service.SchEventService;
import io.protone.custom.service.dto.SchEventPT;
import io.protone.custom.web.rest.network.channel.ApiChannelSchedulerEvent;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by grzesiek on 15.02.2017.
 */
@RestController
public class ApiChannelSchedulerEventImpl implements ApiChannelSchedulerEvent {

    private final Logger log = LoggerFactory.getLogger(ApiChannelSchedulerEventImpl.class);

    @Inject
    private SchEventService templateService;

    @Override
    public ResponseEntity<List<SchEventPT>> getAllSchedulerTemplatesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                       @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all templates");
        List<SchEventPT> templates = templateService.getTemplates(networkShortcut, channelShortcut);
        return ResponseEntity.ok()
            .body(templates);
    }

    @Override
    public ResponseEntity<SchEventPT> updateSchedulerTemplatesForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchEventPT template) {
        log.debug("REST request to update template: {}", template);
        SchEventPT resultDAO = templateService.createOrUpdateTemplate(networkShortcut, channelShortcut, template);
        return ResponseEntity.ok()
            .body(resultDAO);
    }

    @Override
    public ResponseEntity<SchEventPT> creatSchedulerTemplatesForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchEventPT template) {
        log.debug("REST request to update template: {}", template);
        SchEventPT resultDAO = templateService.createOrUpdateTemplate(networkShortcut, channelShortcut, template);
        return ResponseEntity.ok()
            .body(resultDAO);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerTemplateForChannelUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete template : {}", shortName);
        templateService.deleteTemplate(networkShortcut, channelShortcut, shortName);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<SchEventPT> getSchedulerTemplateForChannelUsingGET(String networkShortcut, String channelShortcut, String shortName) {
        log.debug("REST request to get template: {}", shortName);
        SchEventPT result = templateService.getTemplateByChannelAndShortcut(networkShortcut, channelShortcut, shortName);
        return ResponseEntity.ok()
            .body(result);
    }

}
