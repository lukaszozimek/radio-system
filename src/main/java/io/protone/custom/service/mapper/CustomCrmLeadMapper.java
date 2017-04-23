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
    private CustomCorDictionaryMapper corDictionaryMapper;

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
        crmLead.setIndustry(corDictionaryMapper.corDictionaryDTOToCorDictionary(leadPT.getIndustry()));
        crmLead.setArea(corDictionaryMapper.corDictionaryDTOToCorDictionary(leadPT.getArea()));
        crmLead.setAddres(corAddressMapper.cORAddressDTOToCorAddress(leadPT.getAdress()));
        crmLead.setLeadSource(corDictionaryMapper.corDictionaryDTOToCorDictionary(leadPT.getSource()));
        crmLead.setLeadStatus(corDictionaryMapper.corDictionaryDTOToCorDictionary(leadPT.getStatus()));
        crmLead.keeper(corUserMapper.coreUserPTToUser(leadPT.getOwner()));
        crmLead.setPerson(customTRAPersonMapper.createPersonEntity(leadPT.getPerson(), corNetwork));
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
        crmLeadPT.setIndustry(corDictionaryMapper.corDictionaryToCorDictionaryDTO(lead.getIndustry()));
        crmLeadPT.setArea(corDictionaryMapper.corDictionaryToCorDictionaryDTO(lead.getArea()));
        crmLeadPT.setAdress(corAddressMapper.cORAddressToCorAddressDTO(lead.getAddres()));
        crmLeadPT.setSource(corDictionaryMapper.corDictionaryToCorDictionaryDTO(lead.getLeadSource()));
        crmLeadPT.setStatus(corDictionaryMapper.corDictionaryToCorDictionaryDTO(lead.getLeadStatus()));
        crmLeadPT.setTasks(customCrmTaskMapper.createCrmTasks(lead.getTasks()));
        crmLeadPT.setPerson(customTRAPersonMapper.createDTOObject(lead.getPerson()));
        crmLeadPT.setOwner(corUserMapper.userToCoreUserPT(lead.getKeeper()));
        return crmLeadPT;
    }
}



