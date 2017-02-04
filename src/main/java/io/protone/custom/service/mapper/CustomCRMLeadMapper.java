package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.*;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 18.01.2017.
 */
@Service
public class CustomCRMLeadMapper {

    @Inject
    CustomCRMTaskMapper customCRMTaskMapper;

    @Inject
    CustomCORAddressMapper corAddressMapper;

    @Inject
    CustomCORPersonMapper corPersonMapper;

    @Inject
    CustomCORContactMapper corContactMapper;

    @Inject
    CustomTRAIndustryMapper customTRAIndustryMapper;

    @Inject
    CustomCRMLeadSourceMapper customCRMLeadSourceMapper;

    @Inject
    CustomCRMLeadStatusMapper customCRMLeadStatusMapper;

    @Inject
    CustomCORAreaMapper corAreaMapper;

    @Inject
    CustomTRAIndustryMapper industryMapper;


    public CRMLead createLeadEntity(CrmLeadPT leadPT,CORNetwork corNetwork) {
        CRMLead crmLead = new CRMLead();
        crmLead.setId(leadPT.getId());
        crmLead.setName(leadPT.getName());
        crmLead.setDescription(leadPT.getDescription());
        crmLead.setShortname(leadPT.getShortname());
        crmLead.setIndustry(industryMapper.tRAIndustryDTOToTRAIndustry(leadPT.getIndustry()));
        crmLead.setArea(corAreaMapper.cORAreaDTOToCORArea(leadPT.getArea()));
        crmLead.setAddres(corAddressMapper.cORAddressDTOToCORAddress(leadPT.getAdress()));
        crmLead.setLeadSource(customCRMLeadSourceMapper.cRMLeadSourceDTOToCRMLeadSource(leadPT.getSource()));
        crmLead.setLeadStatus(customCRMLeadStatusMapper.cRMLeadStatusDTOToCRMLeadStatus(leadPT.getStatus()));
        crmLead.setPerson(corPersonMapper.cORPersonDTOToCORPerson(leadPT.getPerson()));
        crmLead.setNetwork(corNetwork);
        return crmLead;
    }

    public CrmLeadPT createDTOFromEntites(CRMLead lead) {
        CrmLeadPT crmLeadPT = new CrmLeadPT();
        crmLeadPT.setId(lead.getId());
        crmLeadPT.setName(lead.getName());
        crmLeadPT.setShortname(lead.getShortname());
        crmLeadPT.setDescription(lead.getDescription());
        crmLeadPT.setIndustry(industryMapper.tRAIndustryToTRAIndustryDTO(lead.getIndustry()));
        crmLeadPT.setArea(corAreaMapper.cORAreaToCORAreaDTO(lead.getArea()));
        crmLeadPT.setAdress(corAddressMapper.cORAddressToCORAddressDTO(lead.getAddres()));
        crmLeadPT.setSource(customCRMLeadSourceMapper.cRMLeadSourceToCRMLeadSourceDTO(lead.getLeadSource()));
        crmLeadPT.setStatus(customCRMLeadStatusMapper.cRMLeadStatusToCRMLeadStatusDTO(lead.getLeadStatus()));
        crmLeadPT.setTasks(customCRMTaskMapper.createCrmTasks(lead.getTasks()));
        crmLeadPT.setPerson(corPersonMapper.cORPersonToCORPersonDTO(lead.getPerson()));
        crmLeadPT.setContact(corContactMapper.cORContactsToCORContactDTOs(lead.getPerson().getPersonContacts()));
        return crmLeadPT;
    }
}



