package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.CRMContactService;
import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmContact;
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
    private CRMContactService contactService;

    @Override
    public ResponseEntity<CrmContactPT> updateContactUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerPT", required = true) @RequestBody CrmContactPT customeryPT) {
        if (customeryPT.getId() == null) {
            return createContactUsingPOST(networkShortcut, customeryPT);
        }
        return ResponseEntity.ok().body(contactService.update(customeryPT));
    }

    @Override
    public ResponseEntity<CrmContactPT> createContactUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerPT", required = true) @RequestBody CrmContactPT customerPT) {
        return ResponseEntity.ok().body(contactService.saveContact(customerPT));
    }

    @Override
    public ResponseEntity<List<CrmContactPT>> getAllContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return ResponseEntity.ok().body(contactService.getAllContact());
    }

    @Override
    public ResponseEntity<CrmContactPT> getContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return ResponseEntity.ok().body(contactService.getContact(shortName));
    }

    @Override
    public ResponseEntity<Void> deleteContactUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        contactService.deleteContact(shortName);
        return ResponseEntity.ok().build();
    }
}
