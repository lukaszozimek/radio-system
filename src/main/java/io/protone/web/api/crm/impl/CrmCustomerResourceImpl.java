package io.protone.web.api.crm.impl;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.crm.CrmCustomerService;
import io.protone.web.api.crm.CrmCustomerResource;
import io.protone.web.api.library.impl.LibraryMarkerConfigurationResourceImpl;
import io.protone.web.rest.dto.crm.CrmAccountDTO;
import io.protone.web.rest.dto.crm.thin.CrmAccountThinDTO;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class CrmCustomerResourceImpl implements CrmCustomerResource {
    private final Logger log = LoggerFactory.getLogger(LibraryMarkerConfigurationResourceImpl.class);

    @Inject
    private CrmCustomerService crmCustomerService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmAccountMapper crmAccountMapper;

    @Override
    public ResponseEntity<CrmAccountDTO> updateCustomerUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmAccountDTO", required = true) @Valid @RequestBody CrmAccountDTO crmAccountDTO) throws URISyntaxException {
        log.debug("REST request to update CrmAccount : {}, for Network: {}", crmAccountDTO, networkShortcut);

        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        if (crmAccountDTO.getId() == null) {
            return createCustomerUsingPOST(networkShortcut, crmAccountDTO);
        }
        CrmAccount crmAccount = crmAccountMapper.DTO2DB(crmAccountDTO, corNetwork);
        CrmAccount entity = crmCustomerService.saveCustomer(crmAccount);
        CrmAccountDTO response = crmAccountMapper.DB2DTO(entity);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmAccountDTO> createCustomerUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmAccountDTO", required = true) @Valid @RequestBody CrmAccountDTO crmAccountDTO) throws URISyntaxException {
        log.debug("REST request to saveCorContact CrmAccount : {}, for Network: {}", crmAccountDTO, networkShortcut);
        if (crmAccountDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmAccount", "idexists", "A new CrmAccount cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmAccount crmAccount = crmAccountMapper.DTO2DB(crmAccountDTO, corNetwork);
        CrmAccount entity = crmCustomerService.saveCustomer(crmAccount);
        CrmAccountDTO response = crmAccountMapper.DB2DTO(entity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/customer/" + crmAccount.getShortName()))
            .body(response);
    }

    @Override
    public ResponseEntity<List<CrmAccountThinDTO>> getAllCustomersUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                           @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmAccount, for Network: {}", networkShortcut);
        List<CrmAccount> entity = crmCustomerService.getAllCustomers(networkShortcut, pagable);
        List<CrmAccountThinDTO> response = crmAccountMapper.DBs2ThinDTOs(entity);

        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CrmAccountDTO> getCustomerUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get CrmAccount : {}, for Network: {}", shortName, networkShortcut);
        CrmAccount entity = crmCustomerService.getCustomer(shortName, networkShortcut);
        CrmAccountDTO response = crmAccountMapper.DB2DTO(entity);
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteCustomeryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete CrmAccount : {}, for Network: {}", shortName, networkShortcut);
        crmCustomerService.deleteCustomer(shortName, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmAccount", shortName.toString())).build();
    }
}
