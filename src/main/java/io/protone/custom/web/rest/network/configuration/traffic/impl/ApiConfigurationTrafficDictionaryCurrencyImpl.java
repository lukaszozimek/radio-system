package io.protone.custom.web.rest.network.configuration.traffic.impl;

import io.protone.custom.service.dto.ConfCurrencyPT;
import io.protone.custom.web.rest.network.configuration.traffic.ApiConfigurationTrafficDictionaryCurrency;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiConfigurationTrafficDictionaryCurrencyImpl  implements ApiConfigurationTrafficDictionaryCurrency{


    @Override
    public ResponseEntity<List<ConfCurrencyPT>> getAllCurrencyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCurrencyPT> getCurrencyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCurrencyPT> updateCurrencyUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true) @RequestBody ConfCurrencyPT taxDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCurrencyPT> createCurrencyUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "taxDTO", required = true) @RequestBody ConfCurrencyPT taxDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteCurrencyUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
