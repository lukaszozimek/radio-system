package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.custom.service.TRAAdvertismentService;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.service.dto.TraCustomerAdvertismentsPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomerAdvertisement;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkTrafficCustomerAdvertisementImpl implements ApiNetworkTrafficCustomerAdvertisement {
    @Inject
    private TRAAdvertismentService traAdvertismentService;

    @Override
    public ResponseEntity<List<TraAdvertisementPT>> getAllCustomersAdvertismentsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        return ResponseEntity.ok().body(traAdvertismentService.getCustomerAdvertisments(customerShortcut));
    }
}
