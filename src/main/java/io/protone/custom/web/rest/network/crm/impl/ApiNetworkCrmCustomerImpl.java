package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CRMCustomerService;
import io.protone.custom.service.dto.CrmAccountPT;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import java.util.List;

public class ApiNetworkCrmCustomerImpl implements io.protone.custom.web.rest.network.crm.ApiNetworkCrmCustomer {

    @Inject
    private CRMCustomerService crmCustomerService;

    @Override

    public ResponseEntity<CrmAccountPT> updateCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerPT", required = true) @RequestBody CrmAccountPT customeryPT) {
        if (customeryPT.getId() == null) {
            return createCustomerUsingPOST(networkShortcut, customeryPT);
        }
        return ResponseEntity.ok().body(crmCustomerService.update(customeryPT));
    }

    @Override
    public ResponseEntity<CrmAccountPT> createCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerPT", required = true) @RequestBody CrmAccountPT customerPT) {
        return ResponseEntity.ok().body(crmCustomerService.saveCustomer(customerPT));
    }

    @Override
    public ResponseEntity<List<CrmAccountPT>> getAllCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return ResponseEntity.ok().body(crmCustomerService.getAllCustomer());
    }

    @Override
    public ResponseEntity<CrmAccountPT> getCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return ResponseEntity.ok().body(crmCustomerService.getCustomer(shortName));
    }

    @Override
    public ResponseEntity<Void> deleteCustomeryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        crmCustomerService.deleteCustomer(shortName);
        return ResponseEntity.ok().build();
    }
}
