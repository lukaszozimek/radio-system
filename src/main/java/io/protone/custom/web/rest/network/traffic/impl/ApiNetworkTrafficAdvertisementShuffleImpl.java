package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.TraAdvertisementShuffleService;
import io.protone.custom.service.dto.TraShuffleAdvertisementPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficAdvertisementShuffle;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 11/03/2017.
 */
@RestController
public class ApiNetworkTrafficAdvertisementShuffleImpl implements ApiNetworkTrafficAdvertisementShuffle {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficAdvertisementShuffleImpl.class);

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private TraAdvertisementShuffleService traAdvertisementShuffleService;

    @Override
    public ResponseEntity<Void> shuffleCommercialUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traShuffleAdvertismentPT", required = true) @RequestBody TraShuffleAdvertisementPT traShuffleAdvertismentPT) {
        log.debug("REST request to get all CorNetworks, for Network: {}", networkShortcut);
        if (corNetworkService.findNetwork(networkShortcut) != null) {
            traAdvertisementShuffleService.shuffleCommercials(traShuffleAdvertismentPT);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<Void> approveShuffelingCommercialUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traShuffleAdvertismentPT", required = true) @RequestBody TraShuffleAdvertisementPT traShuffleAdvertismentPT) {
        if (corNetworkService.findNetwork(networkShortcut) != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
