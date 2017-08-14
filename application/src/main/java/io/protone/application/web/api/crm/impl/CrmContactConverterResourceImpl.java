package io.protone.application.web.api.crm.impl;

import io.protone.application.web.api.crm.CrmContactConverterResource;
import io.protone.crm.api.dto.CrmContactDTO;
import io.protone.crm.domain.CrmContact;
import io.protone.crm.domain.CrmLead;
import io.protone.crm.mapper.CrmContactMapper;
import io.protone.crm.service.CrmContactService;
import io.protone.crm.service.CrmLeadService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by lukaszozimek on 30/07/2017.
 */
@RestController
public class CrmContactConverterResourceImpl implements CrmContactConverterResource {
    private final Logger log = LoggerFactory.getLogger(CrmContactConverterResourceImpl.class);

    @Inject
    private CrmLeadService crmLeadService;

    @Inject
    private CrmContactService crmContactService;

    @Inject
    private CrmContactMapper crmContactMapper;

    @Override
    public ResponseEntity<CrmContactDTO> convertLeadToContact(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) throws URISyntaxException {
        log.debug("REST request to convert Lead : {}, for Network: {} to CrmContact", shortName, networkShortcut);
        CrmLead lead = crmLeadService.getLead(shortName, networkShortcut);
        CrmContact crmOpportunity = crmContactService.convertCrmLeadToContact(lead);
        CrmContactDTO response = crmContactMapper.DB2DTO(crmOpportunity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/opportunity/" + response.getName()))
                .body(response);
    }
}
