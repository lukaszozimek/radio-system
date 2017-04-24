package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.TraAdvertismentService;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficAdvertisement;
import io.protone.domain.CorNetwork;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkTrafficAdvertisementImpl implements ApiNetworkTrafficAdvertisement {
    @Inject
    private TraAdvertismentService traAdvertismentService;
    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<TraAdvertisementPT> updateAdvertisementUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "advertisementDTO", required = true) @RequestBody TraAdvertisementPT advertisementDTO) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (advertisementDTO.getId() == null) {
            return createAdvertisementUsingPOST(networkShortcut, advertisementDTO);
        }
        return ResponseEntity.ok().body(traAdvertismentService.saveAdvertisement(advertisementDTO, corNetwork));
    }

    @Override
    public ResponseEntity<TraAdvertisementPT> createAdvertisementUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "advertisementDTO", required = true) @RequestBody TraAdvertisementPT advertisementDTO) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traAdvertismentService.saveAdvertisement(advertisementDTO, corNetwork));
    }

    @Override
    public ResponseEntity<List<TraAdvertisementPT>> getAllAdvertisementsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                                 @ApiParam(value = "pagable", required = true) @PathVariable("pagable") Pageable pagable) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traAdvertismentService.getAllAdvertisement(corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteAdvertisementUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "idx", required = true) @PathVariable("idx") Long idx) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        traAdvertismentService.deleteAdvertisement(idx, corNetwork);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TraAdvertisementPT> getAdvertisementUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "idx", required = true) @PathVariable("idx") Long idx) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(traAdvertismentService.getAdvertisement(idx, corNetwork));
    }
}
