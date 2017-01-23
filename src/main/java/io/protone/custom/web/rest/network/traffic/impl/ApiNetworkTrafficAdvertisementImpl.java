package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.TRAAdvertismentService;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficAdvertisement;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkTrafficAdvertisementImpl implements ApiNetworkTrafficAdvertisement {
    @Inject
    private TRAAdvertismentService traAdvertismentService;

    @Override
    public ResponseEntity<TraAdvertisementPT> updateAdvertisementUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "advertisementDTO", required = true) @RequestBody TraAdvertisementPT advertisementDTO) {
        if(advertisementDTO.getId()==null){
            return createAdvertisementUsingPOST(networkShortcut,advertisementDTO);
        }
        return ResponseEntity.ok().body(traAdvertismentService.update(advertisementDTO));
    }

    @Override
    public ResponseEntity<TraAdvertisementPT> createAdvertisementUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "advertisementDTO", required = true) @RequestBody TraAdvertisementPT advertisementDTO) {
        return ResponseEntity.ok().body(traAdvertismentService.saveAdvertisement(advertisementDTO));
    }

    @Override
    public ResponseEntity<List<TraAdvertisementPT>> getAllAdvertisementsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return ResponseEntity.ok().body(traAdvertismentService.getAllAdvertisement());
    }

    @Override
    public ResponseEntity<Void> deleteAdvertisementUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "idx", required = true) @PathVariable("idx") Long idx) {
        traAdvertismentService.deleteAdvertisement(idx);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TraAdvertisementPT> getAdvertisementUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "idx", required = true) @PathVariable("idx") Long idx) {
        return ResponseEntity.ok().body(traAdvertismentService.getAdvertisement(idx));
    }
}
