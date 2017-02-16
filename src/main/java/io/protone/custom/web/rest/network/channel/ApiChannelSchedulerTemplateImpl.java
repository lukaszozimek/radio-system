package io.protone.custom.web.rest.network.channel;

import io.protone.custom.service.SchTemplateService;
import io.protone.custom.service.dto.SchTemplatePT;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ApiChannelSchedulerTemplateImpl implements ApiChannelSchedulerTemplate {

    private final Logger log = LoggerFactory.getLogger(ApiChannelSchedulerTemplateImpl.class);

    @Inject
    SchTemplateService templateService;

    @Override
    public ResponseEntity<List<SchTemplatePT>> getAllSchedulerTemplatesForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut) {
        log.debug("REST request to get all templates");
        List<SchTemplatePT> templates = templateService.getTemplates(networkShortcut, channelShortcut);
        return ResponseEntity.ok()
            .body(templates);
    }

    @Override
    public ResponseEntity<SchTemplatePT> updateSchedulerTemplatesForChannelUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT template) {
        log.debug("REST request to update template: {}", template);
        SchTemplatePT resultDAO = templateService.createOrUpdateTemplate(networkShortcut, channelShortcut, template);
        return ResponseEntity.ok()
            .body(resultDAO);
    }

    @Override
    public ResponseEntity<SchTemplatePT> creatSchedulerTemplatesForChannelUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT template) {
        log.debug("REST request to update template: {}", template);
        SchTemplatePT resultDAO = templateService.createOrUpdateTemplate(networkShortcut, channelShortcut, template);
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
    public ResponseEntity<SchTemplatePT> getSchedulerTemplateForChannelUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get template: {}", shortName);
        SchTemplatePT templateDAO = templateService.getTemplate(networkShortcut, channelShortcut, shortName);
        return ResponseEntity.ok()
            .body(templateDAO);
    }
}
