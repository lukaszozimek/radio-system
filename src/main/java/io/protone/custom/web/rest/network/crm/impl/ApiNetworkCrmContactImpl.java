package io.protone.custom.web.rest.network.crm.impl;

import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.web.rest.network.crm.ApiNetworkCrmContact;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class ApiNetworkCrmContactImpl implements ApiNetworkCrmContact {


    @Override
    public ResponseEntity<CrmContactPT> updateContactUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerPT", required = true) @RequestBody CrmContactPT customeryPT) {
        return null;
    }

    @Override
    public ResponseEntity<CrmContactPT> createContactUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "customerPT", required = true) @RequestBody CrmContactPT customerPT) {
        return null;
    }

    @Override
    public ResponseEntity<List<CrmContactPT>> getAllContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<CrmContactPT> getContactUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteContactUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) {
        return null;
    }
}
