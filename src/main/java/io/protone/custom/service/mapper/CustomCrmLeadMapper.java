package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmLead;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 18.01.2017.
 */
@Service
public class CustomCrmLeadMapper {

    @Inject
    private CustomCrmTaskMapper customCrmTaskMapper;

    @Inject
    private CustomCorAddressMapper corAddressMapper;

    @Inject
    private CustomCorPersonMapper corPersonMapper;

    @Inject
    private CustomCorContactMapper corContactMapper;

    @Inject
    private CustomTraIndustryMapper customTraIndustryMapper;

    @Inject
    private CustomCrmLeadSourceMapper customCrmLeadSourceMapper;

    @Inject
    private CustomCrmLeadStatusMapper customCrmLeadStatusMapper;

    @Inject
    private CustomCorAreaMapper corAreaMapper;

    @Inject
    private CustomTraIndustryMapper industryMapper;


    public CrmLead createLeadEntity(CrmLeadPT leadPT, CorNetwork corNetwork) {
        CrmLead crmLead = new CrmLead();
        crmLead.setId(leadPT.getId());
        crmLead.setName(leadPT.getName());
        crmLead.setDescription(leadPT.getDescription());
        crmLead.setShortname(leadPT.getShortname());
        crmLead.setIndustry(industryMapper.tRAIndustryDTOToTraIndustry(leadPT.getIndustry()));
        crmLead.setArea(corAreaMapper.cORAreaDTOToCorArea(leadPT.getArea()));
        crmLead.setAddres(corAddressMapper.cORAddressDTOToCorAddress(leadPT.getAdress()));
        crmLead.setLeadSource(customCrmLeadSourceMapper.cRMLeadSourceDTOToCrmLeadSource(leadPT.getSource()));
        crmLead.setLeadStatus(customCrmLeadStatusMapper.cRMLeadStatusDTOToCrmLeadStatus(leadPT.getStatus()));
        crmLead.setPerson(corPersonMapper.cORPersonDTOToCorPerson(leadPT.getPerson()));
        crmLead.setNetwork(corNetwork);
        return crmLead;
    }

    public CrmLeadPT createDTOFromEntites(CrmLead lead) {
        CrmLeadPT crmLeadPT = new CrmLeadPT();
        crmLeadPT.setId(lead.getId());
        crmLeadPT.setName(lead.getName());
        crmLeadPT.setShortname(lead.getShortname());
        crmLeadPT.setDescription(lead.getDescription());
        crmLeadPT.setIndustry(industryMapper.tRAIndustryToTraIndustryDTO(lead.getIndustry()));
        crmLeadPT.setArea(corAreaMapper.cORAreaToCorAreaDTO(lead.getArea()));
        crmLeadPT.setAdress(corAddressMapper.cORAddressToCorAddressDTO(lead.getAddres()));
        crmLeadPT.setSource(customCrmLeadSourceMapper.cRMLeadSourceToCrmLeadSourceDTO(lead.getLeadSource()));
        crmLeadPT.setStatus(customCrmLeadStatusMapper.cRMLeadStatusToCrmLeadStatusDTO(lead.getLeadStatus()));
        crmLeadPT.setTasks(customCrmTaskMapper.createCrmTasks(lead.getTasks()));
        crmLeadPT.setPerson(corPersonMapper.cORPersonToCorPersonDTO(lead.getPerson()));
        crmLeadPT.setContact(corContactMapper.cORContactsToCorContactDTOs(lead.getPerson().getContacts()));
        return crmLeadPT;
    }
}



