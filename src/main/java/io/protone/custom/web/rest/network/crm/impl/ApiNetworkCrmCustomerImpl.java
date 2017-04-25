package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.CrmCustomerService;
import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.web.rest.network.configuration.library.impl.ApiConfigurationLibraryMarkerImpl;
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
public class ApiNetworkCrmCustomerImpl implements io.protone.custom.web.rest.network.crm.ApiNetworkCrmCustomer {
    private final Logger log = LoggerFactory.getLogger(ApiConfigurationLibraryMarkerImpl.class);

    @Inject
    private CrmCustomerService crmCustomerService;

    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<CrmAccountPT> updateCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmAccountPT", required = true) @RequestBody CrmAccountPT crmAccountPT) {
        log.debug("REST request to update CrmAccount : {}, for Network: {}", crmAccountPT, networkShortcut);

        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (crmAccountPT.getId() == null) {
            return createCustomerUsingPOST(networkShortcut, crmAccountPT);
        }
        return ResponseEntity.ok().body(crmCustomerService.saveCustomer(crmAccountPT, corNetwork));
    }

    @Override
    public ResponseEntity<CrmAccountPT> createCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmAccountPT", required = true) @RequestBody CrmAccountPT crmAccountPT) {
        log.debug("REST request to save CrmAccount : {}, for Network: {}", crmAccountPT, networkShortcut);
        if (crmAccountPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmAccount", "idexists", "A new CrmAccount cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmCustomerService.saveCustomer(crmAccountPT, corNetwork));
    }

    @Override
    public ResponseEntity<List<CrmAccountPT>> getAllCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmAccount, for Network: {}", networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmCustomerService.getAllCustomer(corNetwork));
    }

    @Override
    public ResponseEntity<CrmAccountPT> getCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get CrmAccount : {}, for Network: {}", shortName, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(crmCustomerService.getCustomer(shortName, corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteCustomeryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete CrmAccount : {}, for Network: {}", shortName, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        crmCustomerService.deleteCustomer(shortName, corNetwork);
        return ResponseEntity.ok().build();
    }
}
