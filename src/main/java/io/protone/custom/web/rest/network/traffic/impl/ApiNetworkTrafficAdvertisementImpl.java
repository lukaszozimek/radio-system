package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficAdvertisement;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class ApiNetworkTrafficAdvertisementImpl implements ApiNetworkTrafficAdvertisement {
    @Override
    public ResponseEntity<TraAdvertisementPT> updateAdvertisementUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "advertisementDTO", required = true) @RequestBody TraAdvertisementPT advertisementDTO) {
        return null;
    }

    @Override
    public ResponseEntity<TraAdvertisementPT> createAdvertisementUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "advertisementDTO", required = true) @RequestBody TraAdvertisementPT advertisementDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<TraAdvertisementPT>> getAllAdvertisementsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteAdvertisementUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "idx", required = true) @PathVariable("idx") Long idx) {
        return null;
    }

    @Override
    public ResponseEntity<TraAdvertisementPT> getAdvertisementUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "idx", required = true) @PathVariable("idx") Long idx) {
        return null;
    }
}
