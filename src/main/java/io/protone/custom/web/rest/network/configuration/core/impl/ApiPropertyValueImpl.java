package io.protone.custom.web.rest.network.configuration.core.impl;

import io.protone.custom.service.dto.CoreValuePT;
import io.protone.custom.service.mapper.CustomCORPropertyValueMapper;
import io.protone.custom.web.rest.network.configuration.core.ApiPropertyValue;
import io.protone.domain.CORPropertyValue;
import io.protone.repository.CORPropertyValueRepository;
import io.protone.service.dto.CORPropertyValueDTO;
import io.protone.service.mapper.CORPropertyValueMapper;
import io.protone.web.rest.CORPropertyValueResource;
import io.protone.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.util.List;

@RestController
public class ApiPropertyValueImpl implements ApiPropertyValue {
    private final Logger log = LoggerFactory.getLogger(ApiPropertyValueImpl.class);

    @Inject
    private CORPropertyValueRepository cORPropertyValueRepository;

    @Inject
    private CustomCORPropertyValueMapper cORPropertyValueMapper;

    @Override
    public ResponseEntity<CoreValuePT> createPropertyValueUsingPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "propertyValueDTO", required = true) @RequestBody CoreValuePT propertyValueDTO) {
        log.debug("REST request to save CORPropertyValue : {}", propertyValueDTO);
        if (propertyValueDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cORPropertyValue", "idexists", "A new cORPropertyValue cannot already have an ID")).body(null);
        }
        CORPropertyValue cORPropertyValue = cORPropertyValueMapper.cORPropertyValueDTOToCORPropertyValue(propertyValueDTO);
        cORPropertyValue = cORPropertyValueRepository.save(cORPropertyValue);
        CoreValuePT result = cORPropertyValueMapper.cORPropertyValueToCORPropertyValueDTO(cORPropertyValue);
        return ResponseEntity.ok().body(result);

    }

    @Override
    public ResponseEntity<List<CoreValuePT>> getAllPropertyValuesUsingGET(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut) {
        log.debug("REST request to get all CORPropertyValues");
        List<CORPropertyValue> cORPropertyValues = cORPropertyValueRepository.findAll();
        return ResponseEntity.ok().body(cORPropertyValueMapper.cORPropertyValuesToCORPropertyValueDTOs(cORPropertyValues));

    }

    @Override
    public ResponseEntity<CoreValuePT> updatePropertyValueUsingPUT(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut, @ApiParam(value = "propertyValueDTO", required = true) @RequestBody CoreValuePT propertyValueDTO) {
        log.debug("REST request to update CORPropertyValue : {}", propertyValueDTO);
        if (propertyValueDTO.getId() == null) {
            return createPropertyValueUsingPOST(networkShortcut, propertyValueDTO);
        }
        CORPropertyValue cORPropertyValue = cORPropertyValueMapper.cORPropertyValueDTOToCORPropertyValue(propertyValueDTO);
        cORPropertyValue = cORPropertyValueRepository.save(cORPropertyValue);
        CoreValuePT result = cORPropertyValueMapper.cORPropertyValueToCORPropertyValueDTO(cORPropertyValue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cORPropertyValue", propertyValueDTO.getId().toString()))
            .body(result);

    }
}
