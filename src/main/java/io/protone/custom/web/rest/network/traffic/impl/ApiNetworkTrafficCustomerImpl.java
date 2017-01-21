package io.protone.custom.web.rest.network.traffic.impl;


import io.protone.custom.service.TRACustomerService;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomer;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkTrafficCustomerImpl implements ApiNetworkTrafficCustomer {
    @Inject
    private TRACustomerService customerService;

    @Override
    public ResponseEntity<TraCustomerPT> updateTrafficCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "CustomerVM", required = true) @RequestBody TraCustomerPT customer) {
        return null;
    }

    @Override
    public ResponseEntity<TraCustomerPT> createTrafficCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerVM", required = true) @RequestBody TraCustomerPT customer) {
        return ResponseEntity.ok().body(customerService.saveCustomers(customer));
    }

    @Override
    public ResponseEntity<List<TraCustomerPT>> getAllTrafficCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return ResponseEntity.ok().body(customerService.getAllCustomers());
    }

    @Override
    public ResponseEntity<TraCustomerPT> getTrafficCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        return ResponseEntity.ok().body(customerService.getCustomer(customerShortcut));
    }

    @Override
    public ResponseEntity<Void> deleteTrafficCustomerUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        customerService.deleteCustomer(customerShortcut);
        return ResponseEntity.ok().build();
    }
}
