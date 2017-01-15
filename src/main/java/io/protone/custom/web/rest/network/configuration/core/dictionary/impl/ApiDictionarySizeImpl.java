package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.custom.service.dto.CoreSizePT;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionarySize;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class ApiDictionarySizeImpl  implements ApiDictionarySize{


    @Override
    public ResponseEntity<CoreSizePT> createSizeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "personDTO", required = true) @RequestBody CoreSizePT personDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSizeUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<CoreSizePT>> getAllSizeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<CoreSizePT> getSizeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CoreSizePT> updateSizeUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "personDTO", required = true) @RequestBody CoreSizePT personDTO) {
        return null;
    }
}
