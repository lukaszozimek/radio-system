package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.custom.service.dto.CoreRangePT;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionaryRange;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class ApiDictionaryRangeImpl implements ApiDictionaryRange{


    @Override
    public ResponseEntity<CoreRangePT> createRangeUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "personDTO", required = true) @RequestBody CoreRangePT personDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<CoreRangePT>> getAllRangeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }

    @Override
    public ResponseEntity<CoreRangePT> getRangeUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CoreRangePT> updateRangeUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "personDTO", required = true) @RequestBody CoreRangePT personDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteRangeUsingDELETE(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        return null;
    }
}
