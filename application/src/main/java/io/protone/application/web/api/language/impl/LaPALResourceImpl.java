package io.protone.application.web.api.language.impl;

import io.protone.application.web.api.language.LaPALResource;
import io.protone.core.api.dto.thin.CorCommandThinDTO;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by lukaszozimek on 13/07/2017.
 */
@RestController
public class LaPALResourceImpl implements LaPALResource {
    @Override
    public void executCommand(@ApiParam(value = "organizationShortcut", required = true) @PathVariable("organizationShortcut") String organizationShortcut,
                              @ApiParam(value = "channelShortcut", required = true) @PathVariable("channelShortcut") String channelShortcut,
                              @ApiParam(value = "corCommandDTO", required = true) @RequestBody @Valid CorCommandThinDTO corCommandDTO) {

    }
}
