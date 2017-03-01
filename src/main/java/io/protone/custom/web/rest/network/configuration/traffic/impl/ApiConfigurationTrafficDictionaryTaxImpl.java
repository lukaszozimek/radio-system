package io.protone.custom.web.rest.network.configuration.traffic.impl;

import io.protone.custom.service.dto.ConfTaxPT;
import io.protone.custom.web.rest.network.configuration.traffic.ApiConfigurationTrafficDictionaryTax;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiConfigurationTrafficDictionaryTaxImpl implements ApiConfigurationTrafficDictionaryTax {

    @Override
    public ResponseEntity<List<ConfTaxPT>> getAllTaxesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<ConfTaxPT> getTaxUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ConfTaxPT> updateTaxUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true) @RequestBody ConfTaxPT taxDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ConfTaxPT> createTaxUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true) @RequestBody ConfTaxPT taxDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteTaxUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
