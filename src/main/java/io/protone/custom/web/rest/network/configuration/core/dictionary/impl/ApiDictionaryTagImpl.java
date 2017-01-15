package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.custom.service.dto.ConfTagPT;
import io.protone.custom.web.rest.network.configuration.core.dictionary.ApiDictionaryTag;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class ApiDictionaryTagImpl implements ApiDictionaryTag {

    @Override
    public ResponseEntity<ConfTagPT> updateTagUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "tagDTO", required = true) @RequestBody ConfTagPT tagDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ConfTagPT> createTagUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "tagDTO", required = true) @RequestBody ConfTagPT tagDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<ConfTagPT>> getAllTagsUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        return null;
    }
}
