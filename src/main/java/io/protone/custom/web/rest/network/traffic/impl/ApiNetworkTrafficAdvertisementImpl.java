package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.TraAdvertismentService;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficAdvertisement;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkTrafficAdvertisementImpl implements ApiNetworkTrafficAdvertisement {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficAdvertisementImpl.class);

    @Inject
    private TraAdvertismentService traAdvertismentService;

    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<TraAdvertisementPT> updateAdvertisementUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traAdvertisementPT", required = true) @RequestBody TraAdvertisementPT traAdvertisementPT) {
        log.debug("REST request to update TraAdvertisement : {}, for Network: {}", traAdvertisementPT, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (traAdvertisementPT.getId() == null) {
            return createAdvertisementUsingPOST(networkShortcut, traAdvertisementPT);
        }
        return ResponseEntity.ok().body(traAdvertismentService.saveAdvertisement(traAdvertisementPT, corNetwork));
    }

    @Override
    public ResponseEntity<TraAdvertisementPT> createAdvertisementUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traAdvertisementPT", required = true) @RequestBody TraAdvertisementPT traAdvertisementPT) {
        log.debug("REST request to save TraAdvertisement : {}, for Network: {}", traAdvertisementPT, networkShortcut);
        if (traAdvertisementPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraAdvertisement", "idexists", "A new TraAdvertisement cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traAdvertismentService.saveAdvertisement(traAdvertisementPT, corNetwork));
    }

    @Override
    public ResponseEntity<List<TraAdvertisementPT>> getAllAdvertisementsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraAdvertisement, for Network: {}", networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traAdvertismentService.getAllAdvertisement(corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteAdvertisementUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "idx", required = true) @PathVariable("idx") Long idx) {
        log.debug("REST request to delete TraAdvertisement : {}, for Network: {}", idx, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        traAdvertismentService.deleteAdvertisement(idx, corNetwork);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TraAdvertisementPT> getAdvertisementUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "idx", required = true) @PathVariable("idx") Long idx) {
        log.debug("REST request to get TraAdvertisement : {}, for Network: {}", idx, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traAdvertismentService.getAdvertisement(idx, corNetwork));
    }
}
