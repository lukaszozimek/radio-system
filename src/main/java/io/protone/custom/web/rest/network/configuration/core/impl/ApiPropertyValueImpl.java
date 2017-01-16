package io.protone.custom.web.rest.network.configuration.core.impl;

import io.protone.custom.service.dto.CoreValuePT;
import io.protone.custom.web.rest.network.configuration.core.ApiPropertyValue;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class ApiPropertyValueImpl implements ApiPropertyValue {


    @Override
    public ResponseEntity<CoreValuePT> createPropertyValueUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "propertyValueDTO", required = true) @RequestBody CoreValuePT propertyValueDTO) {
        return null;

    }

    @Override
    public ResponseEntity<List<CoreValuePT>> getAllPropertyValuesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<CoreValuePT> updatePropertyValueUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "propertyValueDTO", required = true) @RequestBody CoreValuePT propertyValueDTO) {
        return null;
    }
}
