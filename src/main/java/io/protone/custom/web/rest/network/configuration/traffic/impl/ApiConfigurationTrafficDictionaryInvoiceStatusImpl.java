package io.protone.custom.web.rest.network.configuration.traffic.impl;

import io.protone.custom.service.CorNetworkService;
import io.protone.custom.service.dto.ConfInvoiceStatusPT;
import io.protone.custom.web.rest.network.configuration.traffic.ApiConfigurationTrafficDictionaryInvoiceStatus;
import io.protone.domain.CorNetwork;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ApiConfigurationTrafficDictionaryInvoiceStatusImpl implements ApiConfigurationTrafficDictionaryInvoiceStatus {

    @Inject
    private CorNetworkService corNetworkService;

    @Override
    public ResponseEntity<List<ConfInvoiceStatusPT>> getAllInvoiceStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<ConfInvoiceStatusPT> getInvoiceStatusUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ConfInvoiceStatusPT> updateInvoiceStatusUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true) @RequestBody ConfInvoiceStatusPT taxDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ConfInvoiceStatusPT> createInvoiceStatusUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true) @RequestBody ConfInvoiceStatusPT taxDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteInvoiceStatusUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
