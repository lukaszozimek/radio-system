package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CrmContactService;
import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmContact;
import io.protone.domain.CorNetwork;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiNetworkCrmContactImpl implements ApiNetworkCrmContact {

    @Inject
    private CrmContactService contactService;
    @Inject
    private CorNetworkService networkService;

    @Override
    public ResponseEntity<CrmContactPT> updateContactUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerPT", required = true) @RequestBody CrmContactPT customeryPT) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);
        if (customeryPT.getId() == null) {
            return createContactUsingPOST(networkShortcut, customeryPT);
        }
        return ResponseEntity.ok().body(contactService.saveContact(customeryPT,corNetwork));
    }

    @Override
    public ResponseEntity<CrmContactPT> createContactUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerPT", required = true) @RequestBody CrmContactPT customerPT) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);

        return ResponseEntity.ok().body(contactService.saveContact(customerPT,corNetwork));
    }

    @Override
    public ResponseEntity<List<CrmContactPT>> getAllContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);

        return ResponseEntity.ok().body(contactService.getAllContact(corNetwork));
    }

    @Override
    public ResponseEntity<CrmContactPT> getContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);

        return ResponseEntity.ok().body(contactService.getContact(shortName,corNetwork));
    }

    @Override
    public ResponseEntity<Void> deleteContactUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        CorNetwork corNetwork = networkService.findNetwork(networkShortcut);

        contactService.deleteContact(shortName,corNetwork);
        return ResponseEntity.ok().build();
    }
}