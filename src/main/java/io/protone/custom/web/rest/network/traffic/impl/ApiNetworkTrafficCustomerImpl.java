package io.protone.custom.web.rest.network.traffic.impl;


import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.custom.web.rest.network.traffic.ApiNetworkTrafficCustomer;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.crm.CrmCustomerService;
import io.protone.web.rest.mapper.CrmAccountMapper;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiNetworkTrafficCustomerImpl implements ApiNetworkTrafficCustomer {
    private final Logger log = LoggerFactory.getLogger(ApiNetworkTrafficCustomerImpl.class);

    @Inject
    private CrmCustomerService customerService;

    @Inject
    private CorNetworkService networkService;
    @Inject
    private CrmAccountMapper crmAccountMapper;

    @Override
    public ResponseEntity<TraCustomerPT> updateTrafficCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                       @ApiParam(value = "traCustomerPt", required = true) @RequestBody TraCustomerPT traCustomerPt) {
        log.debug("REST request to update TraCustomer : {}, for Network: {}", traCustomerPt, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (traCustomerPt.getId() == null) {
            return createTrafficCustomerUsingPOST(networkShortcut, traCustomerPt);
        }
        CrmAccount crmAccount = crmAccountMapper.traDTO2DB(traCustomerPt, corNetwork);
        CrmAccount entity = customerService.saveCustomer(crmAccount);
        TraCustomerPT response = crmAccountMapper.traDB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraCustomerPT> createTrafficCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "traCustomerPt", required = true) @RequestBody TraCustomerPT traCustomerPt) {
        log.debug("REST request to save TraCustomer : {}, for Network: {}", traCustomerPt, networkShortcut);
        if (traCustomerPt.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraCustomer", "idexists", "A new TraCustomer cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        CrmAccount crmAccount = crmAccountMapper.traDTO2DB(traCustomerPt, corNetwork);
        CrmAccount entity = customerService.saveCustomer(crmAccount);
        TraCustomerPT response = crmAccountMapper.traDB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<List<TraCustomerPT>> getAllTrafficCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraCustomer, for Network: {}", networkShortcut);
        List<CrmAccount> entity = customerService.getAllCustomers(networkShortcut, pagable);
        List<TraCustomerPT> response = crmAccountMapper.traDBs2DTOs(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraCustomerPT> getTrafficCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        log.debug("REST request to get TraCustomer : {}, for Network: {}", customerShortcut, networkShortcut);
        CrmAccount entity = customerService.getCustomer(customerShortcut, networkShortcut);
        TraCustomerPT response = crmAccountMapper.traDB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<Void> deleteTrafficCustomerUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        log.debug("REST request to delete TraCustomer : {}, for Network: {}", customerShortcut, networkShortcut);
        customerService.deleteCustomer(customerShortcut, networkShortcut);
        return ResponseEntity.ok().build();
    }
}
