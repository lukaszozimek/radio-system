package io.protone.custom.web.rest.network.configuration.crm.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfCrmContactStatusPT;
import io.protone.custom.web.rest.network.configuration.crm.ApiConfigurationCrmDictionaryContactStatus;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import java.util.List;

public class ApiConfigurationCrmDictionaryContactStatusImpl implements ApiConfigurationCrmDictionaryContactStatus {

    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<List<ConfCrmContactStatusPT>> getAllCrmContactStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCrmContactStatusPT> getCrmContactStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCrmContactStatusPT> updateCrmContactStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "contactStatusPT", required = true) @RequestBody ConfCrmContactStatusPT contactStatusPT) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCrmContactStatusPT> createCrmContactStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "contactStatusPT", required = true) @RequestBody ConfCrmContactStatusPT contactStatusPT) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteCrmContactStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
