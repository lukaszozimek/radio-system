package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.dto.SchEventPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficTemplate;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiNetworkTrafficTemplateImpl implements ApiNetworkTrafficTemplate {

    @Override
    public ResponseEntity<SchEventPT> updateTrafficTemplatesUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchEventPT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<SchEventPT> creatTrafficTemplatesUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "schdeulerTemplate", required = true) @RequestBody SchEventPT schdeulerTemplate) {
        return null;
    }

    @Override
    public ResponseEntity<List<SchEventPT>> getAllTrafficTemplatesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "pagable", required = true) @PathVariable("pagable") Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<SchEventPT> getTrafficTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteTrafficTemplateUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }
}
