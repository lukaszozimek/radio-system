package io.protone.custom.web.rest.network.scheduler;

import io.protone.custom.service.dto.SchTemplatePT;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by grzesiek on 12.02.2017.
 */
@RestController
public class ApiNetworkSchedulerTemplateImpl implements ApiNetworkSchedulerTemplate {
    @Override
    public ResponseEntity<SchTemplatePT> updateSchedulerTemplatesUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<SchTemplatePT> creatSchedulerTemplatesUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<List<SchTemplatePT>> getAllSchedulerTemplatesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<SchTemplatePT> getSchedulerTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSchedulerTemplateUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }
}
