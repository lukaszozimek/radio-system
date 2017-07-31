package io.protone.application.web.api.crm.impl;

import io.protone.application.web.api.crm.CrmOpportunityConverterResource;
import io.protone.crm.api.dto.CrmOpportunityDTO;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.domain.CrmContact;
import io.protone.crm.domain.CrmLead;
import io.protone.crm.domain.CrmOpportunity;
import io.protone.crm.mapper.CrmOpportunityMapper;
import io.protone.crm.service.CrmContactService;
import io.protone.crm.service.CrmCustomerService;
import io.protone.crm.service.CrmLeadService;
import io.protone.crm.service.CrmOpportunityService;
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
public class CrmOpportunityConverterResourceImpl implements CrmOpportunityConverterResource {
    private final Logger log = LoggerFactory.getLogger(CrmOpportunityConverterResourceImpl.class);

    @Inject
    private CrmOpportunityService crmOpportunityService;

    @Inject
    private CrmLeadService crmLeadService;

    @Inject
    private CrmCustomerService crmCustomerService;

    @Inject
    private CrmContactService crmContactService;


    @Inject
    private CrmOpportunityMapper crmOpportunityMapper;

    @Override
    public ResponseEntity<CrmOpportunityDTO> convertContactToOpportunityPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                             @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) throws URISyntaxException {
        log.debug("REST request to convert CrmContact : {}, for Network: {} to CrmOpportunity", shortName, networkShortcut);
        CrmContact contact = crmContactService.getContact(shortName, shortName);
        CrmOpportunity crmOpportunity = crmOpportunityService.convertContactToOpportunity(contact);
        CrmOpportunityDTO response = crmOpportunityMapper.DB2DTO(crmOpportunity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/opportunity/" + response.getName()))
                .body(response);
    }

    @Override
    public ResponseEntity<CrmOpportunityDTO> convertLeadToOpportunity(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                      @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) throws URISyntaxException {
        log.debug("REST request to convert Lead : {}, for Network: {} to CrmOpportunity", shortName, networkShortcut);
        CrmLead lead = crmLeadService.getLead(shortName, networkShortcut);
        CrmOpportunity crmOpportunity = crmOpportunityService.convertLeadToOpportunity(lead);
        CrmOpportunityDTO response = crmOpportunityMapper.DB2DTO(crmOpportunity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/opportunity/" + response.getName()))
                .body(response);
    }

    @Override
    public ResponseEntity<CrmOpportunityDTO> convertCustomerToOpportunityPOST(@ApiParam(value = "networkShortcut", required = true) @PathVariable("networkShortcut") String networkShortcut,
                                                                              @ApiParam(value = "shortName", required = true) @PathVariable("shortName") String shortName) throws URISyntaxException {
        log.debug("REST request to convert Customer : {}, for Network: {} to CrmOpportunity", shortName, networkShortcut);

        CrmAccount customer = crmCustomerService.getCustomer(shortName, networkShortcut);
        CrmOpportunity crmOpportunity = crmOpportunityService.convertAccountToOpportunity(customer);
        CrmOpportunityDTO response = crmOpportunityMapper.DB2DTO(crmOpportunity);
        return ResponseEntity.created(new URI("/api/v1/network/" + networkShortcut + "/crm/opportunity/" + response.getName()))
                .body(response);
    }
}
