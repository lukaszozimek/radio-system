package io.protone.custom.web.rest.network.crm.impl;

import io.protone.domain.CrmAccount;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.crm.CrmCustomerService;
import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.web.rest.api.library.impl.LibraryMarkerConfigurationResourceImpl;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.mapper.CrmAccountMapper;
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
    private final Logger log = LoggerFactory.getLogger(LibraryMarkerConfigurationResourceImpl.class);

    @Inject
    private CrmCustomerService crmCustomerService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmAccountMapper crmAccountMapper;

    @Override
    public ResponseEntity<CrmAccountPT> updateCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmAccountPT", required = true) @RequestBody CrmAccountPT crmAccountPT) {
        log.debug("REST request to update CrmAccount : {}, for Network: {}", crmAccountPT, networkShortcut);

        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        if (crmAccountPT.getId() == null) {
            return createCustomerUsingPOST(networkShortcut, crmAccountPT);
        }
        CrmAccount crmAccount = crmAccountMapper.DTO2DB(crmAccountPT, corNetwork);
        CrmAccount entity = crmCustomerService.saveCustomer(crmAccount);
        CrmAccountPT response = crmAccountMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmAccountPT> createCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmAccountPT", required = true) @RequestBody CrmAccountPT crmAccountPT) {
        log.debug("REST request to save CrmAccount : {}, for Network: {}", crmAccountPT, networkShortcut);
        if (crmAccountPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmAccount", "idexists", "A new CrmAccount cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmAccount crmAccount = crmAccountMapper.DTO2DB(crmAccountPT, corNetwork);
        CrmAccount entity = crmCustomerService.saveCustomer(crmAccount);
        CrmAccountPT response = crmAccountMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<List<CrmAccountPT>> getAllCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmAccount, for Network: {}", networkShortcut);
        List<CrmAccount> entity = crmCustomerService.getAllCustomers(networkShortcut, pagable);
        List<CrmAccountPT> response = crmAccountMapper.DBs2DTOs(entity);

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmAccountPT> getCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get CrmAccount : {}, for Network: {}", shortName, networkShortcut);
        CrmAccount entity = crmCustomerService.getCustomer(shortName, networkShortcut);
        CrmAccountPT response = crmAccountMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Void> deleteCustomeryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete CrmAccount : {}, for Network: {}", shortName, networkShortcut);
        crmCustomerService.deleteCustomer(shortName, networkShortcut);
        return ResponseEntity.ok().build();
    }
}
