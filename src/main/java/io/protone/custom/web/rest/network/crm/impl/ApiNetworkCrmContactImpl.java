package io.protone.custom.web.rest.network.crm.impl;

import io.protone.service.crm.CrmContactService;
import io.protone.domain.CrmContact;
import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.CrmContactPT;
import io.protone.web.rest.api.library.impl.LibraryMarkerConfigurationResourceImpl;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmContact;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.mapper.CrmContactMapper;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiNetworkCrmContactImpl implements ApiNetworkCrmContact {
    private final Logger log = LoggerFactory.getLogger(LibraryMarkerConfigurationResourceImpl.class);

    @Inject
    private CrmContactService crmContactService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmContactMapper crmContactMapper;


    @Override
    public ResponseEntity<CrmContactPT> updateContactUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmContactPT", required = true) @RequestBody CrmContactPT crmContactPT) throws URISyntaxException {
        log.debug("REST request to update CrmContact : {}, for Network: {}", crmContactPT, networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        if (crmContactPT.getId() == null) {
            return createContactUsingPOST(networkShortcut, crmContactPT);
        }
        CrmContact contact = crmContactMapper.DTO2DB(crmContactPT, corNetwork);
        CrmContact crmContact = crmContactService.saveContact(contact);
        CrmContactPT response = crmContactMapper.DB2DTO(crmContact);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<CrmContactPT> createContactUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmContactPT", required = true) @RequestBody CrmContactPT crmContactPT) throws URISyntaxException {
        log.debug("REST request to save CrmContact : {}, for Network: {}", crmContactPT, networkShortcut);
        if (crmContactPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmContact", "idexists", "A new CrmContact cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmContact contact = crmContactMapper.DTO2DB(crmContactPT, corNetwork);
        crmContactService.saveContact(contact);
        CrmContactPT response = crmContactMapper.DB2DTO(contact);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/contact/" + crmContactPT.getShortName()))
            .body(response);

    }

    @Override
    public ResponseEntity<List<CrmContactPT>> getAllContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmContact, for Network: {}", networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        List<CrmContactPT> response = crmContactMapper.DBs2DTOs(crmContactService.getAllContact(corNetwork.getShortcut(), pagable));
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<CrmContactPT> getContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get CrmContact : {}, for Network: {}", shortName, networkShortcut);
        CorNetwork corNetwork = corNetworkService.findNetwork(networkShortcut);
        CrmContactPT response = crmContactMapper.DB2DTO(crmContactService.getContact(shortName, corNetwork.getShortcut()));
        return Optional.ofNullable(response)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Void> deleteContactUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete CrmContact : {}, for Network: {}", shortName, networkShortcut);
        crmContactService.deleteContact(shortName, networkShortcut);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("CrmContact", shortName.toString())).build();
    }
}
