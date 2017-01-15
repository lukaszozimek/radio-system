package io.protone.custom.web.rest.network.configuration.core.impl;

import io.protone.custom.service.dto.CoreKeyPT;
import io.protone.custom.web.rest.network.configuration.core.ApiPropertyKey;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class ApiPropertyKeyImpl implements ApiPropertyKey {

    @Override
    public ResponseEntity<CoreKeyPT> getPropertyKeyUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "keyName", required = true) @PathVariable("keyName") String keyName) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deletePropertyKeyUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "keyName", required = true) @PathVariable("keyName") String keyName) {
        return null;
    }

    @Override
    public ResponseEntity<List<CoreKeyPT>> getAllPropertyKeysUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<CoreKeyPT> updatePropertyKeyUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "propertyKeyDTO", required = true) @RequestBody CoreKeyPT propertyKeyDTO) {
        return null;
    }

    @Override
    public ResponseEntity<CoreKeyPT> createPropertyKeyUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "propertyKeyDTO", required = true) @RequestBody CoreKeyPT propertyKeyDTO) {
        return null;
    }
}
