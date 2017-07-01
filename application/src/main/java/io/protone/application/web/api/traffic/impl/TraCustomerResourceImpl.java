package io.protone.application.web.api.traffic.impl;


import io.protone.application.web.api.traffic.TraCustomerResource;
import io.protone.application.web.rest.util.HeaderUtil;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.crm.api.dto.CrmAccountDTO;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.service.CrmCustomerService;
import io.protone.traffic.api.dto.TraCustomerDTO;
import io.protone.traffic.mapper.TraCustomerMapper;
import io.swagger.annotations.ApiParam;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class TraCustomerResourceImpl implements TraCustomerResource {
    private final Logger log = LoggerFactory.getLogger(TraCustomerResourceImpl.class);

    @Inject
    private CrmCustomerService crmCustomerService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private TraCustomerMapper accountMapper;

    @Override
    public ResponseEntity<TraCustomerDTO> updateTrafficCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                        @ApiParam(value = "traCustomerDTO", required = true) @Valid @RequestBody TraCustomerDTO traCustomerDTO) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to update TraCustomer : {}, for Network: {}", traCustomerDTO, networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        if (traCustomerDTO.getId() == null) {
            return createTrafficCustomerUsingPOST(networkShortcut, traCustomerDTO, null);
        }
        CrmAccount crmAccount = accountMapper.traDTO2DB(traCustomerDTO, corNetwork);
        CrmAccount entity = crmCustomerService.saveCustomer(crmAccount);
        TraCustomerDTO response = accountMapper.traDB2DTO(entity);
        return ResponseEntity.ok()
                .body(response);
    }

    @Override
    public ResponseEntity<TraCustomerDTO> createTrafficCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                         @ApiParam(value = "traCustomerDTO", required = true) @Valid @RequestPart TraCustomerDTO traCustomerDTO,
                                                                         @ApiParam(value = "avatar", required = true) @RequestPart("avatar") MultipartFile avatar) throws URISyntaxException, TikaException, IOException, SAXException {
        log.debug("REST request to saveCorContact TraCustomer : {}, for Network: {}", traCustomerDTO, networkShortcut);
        if (traCustomerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("TraCustomer", "idexists", "A new TraCustomer cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmAccount crmAccount = accountMapper.traDTO2DB(traCustomerDTO, corNetwork);
        CrmAccount entity = crmCustomerService.saveCustomerWithImage(crmAccount, avatar);
        TraCustomerDTO response = accountMapper.traDB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/traffic/customer/" + traCustomerDTO.getShortName()))
                .body(response);

    }

    @Override
    public ResponseEntity<List<TraCustomerDTO>> getAllTrafficCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                               @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all TraCustomer, for Network: {}", networkShortcut);
        List<CrmAccount> entity = crmCustomerService.getAllCustomers(networkShortcut, pagable);
        List<TraCustomerDTO> response = accountMapper.traDBs2DTOs(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<TraCustomerDTO> getTrafficCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        log.debug("REST request to get TraCustomer : {}, for Network: {}", customerShortcut, networkShortcut);
        CrmAccount entity = crmCustomerService.getCustomer(customerShortcut, networkShortcut);
        TraCustomerDTO response = accountMapper.traDB2DTO(entity);
        return Optional.ofNullable(response)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<Void> deleteTrafficCustomerUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerShortcut", required = true) @PathVariable("customerShortcut") String customerShortcut) {
        log.debug("REST request to delete TraCustomer : {}, for Network: {}", customerShortcut, networkShortcut);
        crmCustomerService.deleteCustomer(customerShortcut, networkShortcut);
        return ResponseEntity.ok().build();
    }
}
