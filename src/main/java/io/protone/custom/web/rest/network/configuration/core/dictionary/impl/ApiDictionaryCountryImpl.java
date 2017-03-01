package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.custom.service.dto.ConfCountryPt;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionaryCountry;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiDictionaryCountryImpl implements ApiDictionaryCountry {


    @Override
    public ResponseEntity<ConfCountryPt> updateCountryUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "personDTO", required = true) @RequestBody ConfCountryPt personDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCountryPt> createCountryUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "personDTO", required = true) @RequestBody ConfCountryPt personDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteCountryUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<ConfCountryPt>> getAllCountriesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<ConfCountryPt> getCountryUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
