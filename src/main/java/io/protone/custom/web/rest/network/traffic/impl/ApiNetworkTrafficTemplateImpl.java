package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.dto.SchTemplatePT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficTemplate;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiNetworkTrafficTemplateImpl implements ApiNetworkTrafficTemplate {

    @Override
    public ResponseEntity<SchTemplatePT> updateTrafficTemplatesUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<SchTemplatePT> creatTrafficTemplatesUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchTemplatePT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<List<SchTemplatePT>> getAllTrafficTemplatesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<SchTemplatePT> getTrafficTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteTrafficTemplateUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }
}
