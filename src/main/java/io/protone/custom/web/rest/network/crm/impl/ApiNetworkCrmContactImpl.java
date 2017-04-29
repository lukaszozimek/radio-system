package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CrmContactService;
import io.protone.service.cor.CorNetworkService;
import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.web.rest.network.configuration.library.impl.ApiConfigurationLibraryMarkerImpl;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmContact;
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
public class ApiNetworkCrmContactImpl implements ApiNetworkCrmContact {
    private final Logger log = LoggerFactory.getLogger(ApiConfigurationLibraryMarkerImpl.class);

    @Inject
    private CrmContactService contactService;

    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<CrmContactPT> updateContactUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmContactPT", required = true) @RequestBody CrmContactPT crmContactPT) {
        log.debug("REST request to update CrmContact : {}, for Network: {}", crmContactPT, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (crmContactPT.getId() == null) {
            return createContactUsingPOST(networkShortcut, crmContactPT);
        }
        return ResponseEntity.ok().body(contactService.saveContact(crmContactPT, corNetwork));
    }

    @Override
    public ResponseEntity<CrmContactPT> createContactUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "crmContactPT", required = true) @RequestBody CrmContactPT crmContactPT) {
        log.debug("REST request to save CrmContact : {}, for Network: {}", crmContactPT, networkShortcut);
        if (crmContactPT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CrmContact", "idexists", "A new CrmContact cannot already have an ID")).body(null);
        }
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(contactService.saveContact(crmContactPT, corNetwork));
    }

    @Override
    public ResponseEntity<List<CrmContactPT>> getAllContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                    @ApiParam(value = "pagable", required = true) Pageable pagable) {
        log.debug("REST request to get all CrmContact, for Network: {}", networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(contactService.getAllContact(corNetwork));
    }

    @Override
    public ResponseEntity<CrmContactPT> getContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to get CrmContact : {}, for Network: {}", shortName, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        return ResponseEntity.ok().body(contactService.getContact(shortName, corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteContactUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        log.debug("REST request to delete CrmContact : {}, for Network: {}", shortName, networkShortcut);
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        contactService.deleteContact(shortName, corNetwork);
        return ResponseEntity.ok().build();
    }
}
