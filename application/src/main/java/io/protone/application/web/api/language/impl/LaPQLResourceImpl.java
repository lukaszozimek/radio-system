package io.protone.application.web.api.language.impl;

import io.protone.application.web.api.language.LaPQLResource;
import io.protone.core.api.dto.thin.CorFilterThinDTO;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by lukaszozimek on 13/07/2017.
 */

@RestController
public class LaPQLResourceImpl  implements LaPQLResource{
    @Override
    public void queryElements(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                              @ApiParam(value = "corFilterDTO", required = true) @RequestBody @Valid CorFilterThinDTO corFilterDTO) {

    }
}
