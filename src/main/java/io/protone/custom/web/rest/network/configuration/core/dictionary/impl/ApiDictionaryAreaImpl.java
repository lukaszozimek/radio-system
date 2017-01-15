package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.custom.service.dto.CoreAreaPT;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionaryArea;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class ApiDictionaryAreaImpl implements ApiDictionaryArea {


    @Override
    public ResponseEntity<CoreAreaPT> getAreaUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteAreaUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<CoreAreaPT>> getAllAreaUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<CoreAreaPT> updateAreaUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "personDTO", required = true) @RequestBody CoreAreaPT personDTO) {
        return null;
    }

    @Override
    public ResponseEntity<CoreAreaPT> createAreaUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "personDTO", required = true) @RequestBody CoreAreaPT personDTO) {
        return null;
    }
}
