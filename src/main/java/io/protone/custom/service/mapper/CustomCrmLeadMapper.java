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
    private CustomTRAPersonMapper customTRAPersonMapper;

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
    @Inject
    private CustomCorUserMapperExt corUserMapper;

    public CrmLead createLeadEntity(CrmLeadPT leadPT, CorNetwork corNetwork) {
        if (leadPT == null || corNetwork == null) {
            return new CrmLead();
        }
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
        crmLead.keeper(corUserMapper.coreUserPTToUser(leadPT.getOwner()));
        crmLead.setPerson(customTRAPersonMapper.createPersonEntity(leadPT.getPerson(),corNetwork));
        crmLead.setNetwork(corNetwork);
        return crmLead;
    }

    public CrmLeadPT createDTOFromEntites(CrmLead lead) {
        if (lead == null) {
            return new CrmLeadPT();
        }
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
        crmLeadPT.setPerson(customTRAPersonMapper.createDTOObject(lead.getPerson()));
        crmLeadPT.setOwner(corUserMapper.userToCoreUserPT(lead.getKeeper()));
        return crmLeadPT;
    }
}



