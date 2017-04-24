package io.protone.custom.web.rest.network.traffic.impl;


import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.TraCustomerService;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomer;
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
public class ApiNetworkTrafficCustomerImpl implements ApiNetworkTrafficCustomer {
    @Inject
    private TraCustomerService customerService;
    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<TraCustomerPT> updateTrafficCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "CustomerVM", required = true) @RequestBody TraCustomerPT customer) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (customer.getId() == null) {
            return createTrafficCustomerUsingPOST(networkShortcut, customer);
        }
        return ResponseEntity.ok().body(customerService.update(customer, corNetwork));
    }

    @Override
    public ResponseEntity<TraCustomerPT> createTrafficCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerVM", required = true) @RequestBody TraCustomerPT customer) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(customerService.saveCustomers(customer, corNetwork));
    }

    @Override
    public ResponseEntity<List<TraCustomerPT>> getAllTrafficCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "pagable", required = true) @PathVariable("pagable") Pageable pagable) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(customerService.getAllCustomers(corNetwork));
    }

    @Override
    public ResponseEntity<TraCustomerPT> getTrafficCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(customerService.getCustomer(customerShortcut, corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteTrafficCustomerUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        customerService.deleteCustomer(customerShortcut, corNetwork);
        return ResponseEntity.ok().build();
    }
}
