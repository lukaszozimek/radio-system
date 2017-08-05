package io.protone.application.web.api.traffic.impl;


import io.protone.application.web.api.traffic.TraMediaPlanTemplateResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.traffic.api.dto.TraMediaPlanTemplateDTO;
import io.protone.traffic.domain.TraMediaPlanTemplate;
import io.protone.traffic.mapper.TraMediaPlanTemplateMapper;
import io.protone.traffic.service.TraMediaPlanTemplateService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class TraMediaPlanTemplateResourceImpl implements TraMediaPlanTemplateResource {


    private final Logger log = LoggerFactory.getLogger(TraMediaPlanTemplateResourceImpl.class);

    @Autowired
    private TraMediaPlanTemplateMapper traMediaPlanTemplateMapper;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private TraMediaPlanTemplateService traMediaPlanTemplateService;


    @Override
    public ResponseEntity<List<TraMediaPlanTemplateDTO>> getAllTraMediaPlanTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                            @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraMediaPlanTemplate");

        List<TraMediaPlanTemplate> traMediaPlan = traMediaPlanTemplateService.findAllMediaPlanTemplates(networkShortcut, pagable);
        List<TraMediaPlanTemplateDTO> traMediaPlanPT = traMediaPlanTemplateMapper.DBs2DTOs(traMediaPlan);
        return Optional.ofNullable(traMediaPlanPT)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraMediaPlanTemplateDTO> getTraMediaPlanTemplateUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                   @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to get TraMediaPlanTemplate : {}", networkShortcut);

        TraMediaPlanTemplate traMediaPlan = traMediaPlanTemplateService.findMediaPlanTemplate(id, networkShortcut);
        TraMediaPlanTemplateDTO traMediaPlanPt = traMediaPlanTemplateMapper.DB2DTO(traMediaPlan);
        return Optional.ofNullable(traMediaPlanPt)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraMediaPlanTemplateDTO> updateTraMediaPlanTemplateUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                      @ApiParam(value = "traMediaPlanTemplateDTO", required = true) @RequestBody TraMediaPlanTemplateDTO traMediaPlanTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update TraMediaPlanTemplate : {}", traMediaPlanTemplateDTO);
        if (traMediaPlanTemplateDTO.getId() == null) {
            return createTraMediaPlanTemplateUsingPOST(networkShortcut, traMediaPlanTemplateDTO);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraMediaPlanTemplate traMediaPlan = traMediaPlanTemplateMapper.DTO2DB(traMediaPlanTemplateDTO, corNetwork);
        traMediaPlan = traMediaPlanTemplateService.saveMediaPlanTemplate(traMediaPlan);
        TraMediaPlanTemplateDTO result = traMediaPlanTemplateMapper.DB2DTO(traMediaPlan);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("TraMediaPlanTemplate", result.getId().toString()))
                .body(result);
    }

    @Override
    public ResponseEntity<TraMediaPlanTemplateDTO> createTraMediaPlanTemplateUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                       @ApiParam(value = "traMediaPlanTemplateDTO", required = true) @RequestBody TraMediaPlanTemplateDTO traMediaPlanTemplateDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact TraMediaPlanTemplate : {}", traMediaPlanTemplateDTO);
        if (traMediaPlanTemplateDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraMediaPlanTemplate", "idexists", "A new TraMediaPlanTemplate cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        TraMediaPlanTemplate traMediaPlan = traMediaPlanTemplateMapper.DTO2DB(traMediaPlanTemplateDTO, corNetwork);
        traMediaPlan = traMediaPlanTemplateService.saveMediaPlanTemplate(traMediaPlan);
        TraMediaPlanTemplateDTO result = traMediaPlanTemplateMapper.DB2DTO(traMediaPlan);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/configuration/traffic/dictionary/discount/" + result.getId()))
                .body(result);
    }

    @Override
    public ResponseEntity<Void> deleteTraMediaPlanTemplateUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        log.debug("REST request to delete TraMediaPlanTemplate : {}", id);
        traMediaPlanTemplateService.deleteMediaPlanTemplate(id, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("TraMediaPlanTemplate", id.toString())).build();
    }
}
