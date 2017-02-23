package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.CrmCustomerService;
import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.domain.CorNetwork;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkCrmCustomerImpl implements io.protone.custom.web.rest.network.crm.ApiNetworkCrmCustomer {

    @Inject
    private CrmCustomerService crmCustomerService;
    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<CrmAccountPT> updateCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerPT", required = true) @RequestBody CrmAccountPT customeryPT) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);

        if (customeryPT.getId() == null) {
            return createCustomerUsingPOST(networkShortcut, customeryPT);
        }
        return ResponseEntity.ok().body(crmCustomerService.saveCustomer(customeryPT, corNetwork));
    }

    @Override
    public ResponseEntity<CrmAccountPT> createCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerPT", required = true) @RequestBody CrmAccountPT customerPT) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);

        return ResponseEntity.ok().body(crmCustomerService.saveCustomer(customerPT, corNetwork));
    }

    @Override
    public ResponseEntity<List<CrmAccountPT>> getAllCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmCustomerService.getAllCustomer(corNetwork));
    }

    @Override
    public ResponseEntity<CrmAccountPT> getCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmCustomerService.getCustomer(shortName, corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteCustomeryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        crmCustomerService.deleteCustomer(shortName, corNetwork);
        return ResponseEntity.ok().build();
    }
}
