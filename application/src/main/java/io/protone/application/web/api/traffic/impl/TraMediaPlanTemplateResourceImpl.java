package io.protone.application.web.api.traffic.impl;


import io.protone.application.web.api.traffic.TraMediaPlanTemplateResource;
import io.protone.traffic.api.dto.TraMediaPlanTemplateDTO;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

@RestController
public class TraMediaPlanTemplateResourceImpl implements TraMediaPlanTemplateResource {


    @Override
    public ResponseEntity<List<TraMediaPlanTemplateDTO>> getAllTraMediaPlanTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                            @ApiParam(value = "pagable", required = true) Pageable pagable) {
        return null;
    }

    @Override
    public ResponseEntity<TraMediaPlanTemplateDTO> getTraMediaPlanTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<TraMediaPlanTemplateDTO> updateTraMediaPlanTemplateUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                      @ApiParam(value = "traMediaPlanTemplateDTO", required = true) @RequestBody TraMediaPlanTemplateDTO traMediaPlanTemplateDTO) throws URISyntaxException {
        return null;
    }

    @Override
    public ResponseEntity<TraMediaPlanTemplateDTO> createTraMediaPlanTemplateUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                       @ApiParam(value = "traMediaPlanTemplateDTO", required = true) @RequestBody TraMediaPlanTemplateDTO traMediaPlanTemplateDTO) throws URISyntaxException {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteTraMediaPlanTemplateUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
