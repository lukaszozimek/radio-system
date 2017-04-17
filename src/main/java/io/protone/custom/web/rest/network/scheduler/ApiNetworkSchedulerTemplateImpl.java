package io.protone.custom.web.rest.network.scheduler;

import io.protone.custom.service.SchEventService;
import io.protone.custom.service.dto.SchEventPT;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by grzesiek on 12.02.2017.
 */
@RestController
public class ApiNetworkSchedulerTemplateImpl implements ApiNetworkSchedulerTemplate {

    private final Logger log = LoggerFactory.getLogger(ApiNetworkSchedulerTemplateImpl.class);

    @Inject
    SchEventService templateService;

    /* DISABLED !!!

    @Override
    public ResponseEntity<SchEventPT> updateSchedulerTemplatesUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchEventPT template) {
        log.debug("REST request to update template: {}", template);
        SchEventPT resultDAO = templateService.createOrUpdateTemplate(networkShortcut, template);
        return ResponseEntity.ok()
            .body(resultDAO);
    }

    @Override
    public ResponseEntity<SchEventPT> creatSchedulerTemplatesUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchEventPT template) {
        log.debug("REST request to create template: {}", template);
        SchEventPT resultDAO = templateService.createOrUpdateTemplate(networkShortcut, template);
        return ResponseEntity.ok()
            .body(resultDAO);
    }

    @Override
    public ResponseEntity<List<SchEventPT>> getSchedulerTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get template: {}", shortName);
        List<SchEventPT> templatesDAO = templateService.getTemplates(networkShortcut, shortName);
        return ResponseEntity.ok()
            .body(templatesDAO);
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerTemplateUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete template : {}", shortName);
        templateService.deleteTemplate(networkShortcut, shortName);
        return ResponseEntity.ok().build();
    }

    */

    @Override
    public ResponseEntity<List<SchEventPT>> getAllSchedulerTemplatesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all templates");
        List<SchEventPT> templates = templateService.getTemplates(networkShortcut);
        return ResponseEntity.ok()
            .body(templates);
    }
}
