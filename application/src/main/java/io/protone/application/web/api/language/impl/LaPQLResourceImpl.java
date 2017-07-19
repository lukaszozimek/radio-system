package io.protone.application.web.api.language.impl;

import io.protone.application.web.api.language.LaPQLResource;
import io.protone.core.api.dto.thin.CorFilterThinDTO;
import io.protone.core.domain.CorFilter;
import io.protone.core.mapper.CorFilterMapper;
import io.protone.language.service.pql.LaPQLMappingService;
import io.protone.language.service.pql.LaPQLService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by lukaszozimek on 13/07/2017.
 */

@RestController
public class LaPQLResourceImpl implements LaPQLResource {
    @Autowired
    private LaPQLService pqlService;

    @Autowired
    private CorFilterMapper corFilterMapper;
    @Autowired
    private LaPQLMappingService pqlMappingService;

    @Override
    public List queryElements(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                              @ApiParam(value = "corFilterDTO", required = true) @RequestBody @Valid CorFilterThinDTO corFilterDTO) throws IOException {

        CorFilter corFilter = corFilterMapper.DTO2DB(corFilterDTO);
        List entites = pqlService.getObjectList(corFilter);
        return pqlMappingService.DBs2DTOs(entites, corFilter.getType());
    }
}
