package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.domain.*;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraCampaignService;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCampaign;
import io.protone.web.rest.mapper.TraCampaignMapper;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiNetworkTrafficCampaignImpl implements ApiNetworkTrafficCampaign {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficCampaignImpl.class);

    @Inject
    private TraCampaignService campaignService;

    @Inject
    private CorNetworkService networkService;

    @Inject
    private TraCampaignMapper traCampaignMapper;

    @Override
    public ResponseEntity<List<TraCampaignPT>> getAllCampaignsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraCampaign, for Network: {}", networkShortcut);
        List<TraCampaign> entity = campaignService.getAllCampaign(networkShortcut, pagable);
        List<TraCampaignPT> response = traCampaignMapper.DBs2DTOs(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraCampaignPT> updateCampaignUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traCampaignPT", required = true) @RequestBody TraCampaignPT traCampaignPT) {
        log.debug("REST request to update TraCampaign : {}, for Network: {}", traCampaignPT, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (traCampaignPT.getId() == null) {
            return createCampaignUsingPOST(networkShortcut, traCampaignPT);
        }

        TraCampaign crmAccount = traCampaignMapper.DTO2DB(traCampaignPT, corNetwork);
        TraCampaign entity = campaignService.saveCampaign(crmAccount);
        TraCampaignPT response = traCampaignMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraCampaignPT> createCampaignUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traCampaignPT", required = true) @RequestBody TraCampaignPT traCampaignPT) {
        log.debug("REST request to save TraCampaign : {}, for Network: {}", traCampaignPT, networkShortcut);
        if (traCampaignPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraCampaign", "idexists", "A new TraCampaign cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        TraCampaign crmAccount = traCampaignMapper.DTO2DB(traCampaignPT, corNetwork);
        TraCampaign entity = campaignService.saveCampaign(crmAccount);
        TraCampaignPT response = traCampaignMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<Void> deleteCampaignUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete TraCampaign : {}, for Network: {}", shortName, networkShortcut);
        campaignService.deleteCampaign(shortName, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traOrder", shortName.toString())).build();
    }

    @Override
    public ResponseEntity<TraCampaignPT> getCampaignUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get TraCampaign : {}, for Network: {}", shortName, networkShortcut);
        TraCampaign entity = campaignService.getCampaign(shortName, networkShortcut);
        TraCampaignPT response = traCampaignMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
