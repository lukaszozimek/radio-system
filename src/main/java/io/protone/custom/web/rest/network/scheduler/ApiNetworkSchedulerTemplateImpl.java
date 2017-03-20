package io.protone.custom.web.rest.network.scheduler;

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
 * Created by grzesiek on 12.02.2017.
 */
@RestController
public class ApiNetworkSchedulerTemplateImpl implements ApiNetworkSchedulerTemplate {

    private final Logger log = LoggerFactory.getLogger(ApiNetworkSchedulerTemplateImpl.class);

    @Inject
    SchTemplateService templateService;

    /* DISABLED !!!

    @Override
    public ResponseEntity<SchTemplatePT> updateSchedulerTemplatesUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT template) {
        log.debug("REST request to update template: {}", template);
        SchTemplatePT resultDAO = templateService.createOrUpdateTemplate(networkShortcut, template);
        return ResponseEntity.ok()
            .body(resultDAO);
    }

    @Override
    public ResponseEntity<SchTemplatePT> creatSchedulerTemplatesUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT template) {
        log.debug("REST request to create template: {}", template);
        SchTemplatePT resultDAO = templateService.createOrUpdateTemplate(networkShortcut, template);
        return ResponseEntity.ok()
            .body(resultDAO);
    }

    @Override
    public ResponseEntity<List<SchTemplatePT>> getSchedulerTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get template: {}", shortName);
        List<SchTemplatePT> templatesDAO = templateService.getTemplates(networkShortcut, shortName);
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
    public ResponseEntity<List<SchTemplatePT>> getAllSchedulerTemplatesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all templates");
        List<SchTemplatePT> templates = templateService.getTemplates(networkShortcut);
        return ResponseEntity.ok()
            .body(templates);
    }
}
