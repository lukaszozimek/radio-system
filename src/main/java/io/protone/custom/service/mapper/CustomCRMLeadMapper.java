package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.*;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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


    public CRMLead createLeadEntity(CrmLeadPT leadPT) {
        CRMLead crmLead = new CRMLead();
        crmLead.setId(leadPT.getId());
        crmLead.setName(leadPT.getName());
        crmLead.setDescription(leadPT.getDescription());
        crmLead.setShortcut(leadPT.getShortname());
        return crmLead;
    }

    public CORAddress createAdressEntity(CrmLeadPT leadPT) {
        return corAddressMapper.cORAddressDTOToCORAddress(leadPT.getAdress());
    }

    public CORPerson createPersonEntity(CrmLeadPT leadPT) {
        return corPersonMapper.cORPersonDTOToCORPerson(leadPT.getPerson());
    }

    public List<CORContact> createContactEntity(CrmLeadPT leadPT) {
        return corContactMapper.cORContactDTOsToCORContacts(leadPT.getContact());
    }

    public CORAssociation createAddressAssociationEntity(CRMLead lead, CORAddress address) {
        CORAssociation association = new CORAssociation();
        association.setName("ADDRESS");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(lead.getId());
        association.setTargetClass(CORAddress.class.getName());
        association.setTargetId(address.getId());
        return association;
    }

    public CORAssociation createLeadStatusAssociationEntity(CRMLead lead, CRMLeadStatus address) {
        CORAssociation association = new CORAssociation();
        association.setName("STATUS");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(lead.getId());
        association.setTargetClass(CRMLeadStatus.class.getName());
        association.setTargetId(address.getId());
        return association;
    }

    public CORAssociation createLeadSourceAssociationEntity(CRMLead lead, CRMLeadSource leadSource) {
        CORAssociation association = new CORAssociation();
        association.setName("STATUS");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(lead.getId());
        association.setTargetClass(CRMLeadSource.class.getName());
        association.setTargetId(leadSource.getId());
        return association;
    }

    public CORAssociation createLeadIndustryAssociationEntity(CRMLead lead, TRAIndustry industry) {
        CORAssociation association = new CORAssociation();
        association.setName("TRAIndustry");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(lead.getId());
        association.setTargetClass(TRAIndustry.class.getName());
        association.setTargetId(industry.getId());
        return association;
    }

    public CORAssociation createLeadAreaAssociationEntity(CRMLead lead, CORArea area) {
        CORAssociation association = new CORAssociation();
        association.setName("CORArea");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(lead.getId());
        association.setTargetClass(CORArea.class.getName());
        association.setTargetId(area.getId());
        return association;
    }

    public CORAssociation createLeadPersonAssociationEntity(CRMLead lead, CORPerson corPerson) {
        CORAssociation association = new CORAssociation();
        association.setName("CORPerson");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(lead.getId());
        association.setTargetClass(CORPerson.class.getName());
        association.setTargetId(corPerson.getId());
        return association;
    }

    public List<CORAssociation> createLeadContactAssociationEntity(CORPerson person, List<CORContact> corContacts) {
        List<CORAssociation> associations = new ArrayList<>();
        for (CORContact corContact : corContacts) {
            CORAssociation association = new CORAssociation();
            association.setName("CORPerson");
            association.setSourceClass(CORPerson.class.getName());
            association.setSourceId(person.getId());
            association.setTargetClass(CORContact.class.getName());
            association.setTargetId(corContact.getId());
            associations.add(association);
        }
        return associations;
    }


    public CrmLeadPT createDTOFromEntites(CRMLead lead,
                                          List<CrmTaskPT> tasks,
                                          CORPerson person,
                                          CORAddress address,
                                          List<CORContact> corContacts,
                                          TRAIndustry industry,
                                          CORArea area,
                                          CRMLeadSource leadSource,
                                          CRMLeadStatus leadStatus) {
        CrmLeadPT crmLeadPT = new CrmLeadPT();
        crmLeadPT.setId(lead.getId());
        crmLeadPT.setName(lead.getName());
        crmLeadPT.setShortname(lead.getShortcut());
        crmLeadPT.setDescription(lead.getDescription());
        crmLeadPT.setIndustry(industryMapper.tRAIndustryToTRAIndustryDTO(industry));
        crmLeadPT.setArea(corAreaMapper.cORAreaToCORAreaDTO(area));
        crmLeadPT.setAdress(corAddressMapper.cORAddressToCORAddressDTO(address));
        crmLeadPT.setSource(customCRMLeadSourceMapper.cRMLeadSourceToCRMLeadSourceDTO(leadSource));
        crmLeadPT.setStatus(customCRMLeadStatusMapper.cRMLeadStatusToCRMLeadStatusDTO(leadStatus));
        crmLeadPT.setTasks(tasks);
        crmLeadPT.setPerson(corPersonMapper.cORPersonToCORPersonDTO(person));
        crmLeadPT.setContact(corContactMapper.cORContactsToCORContactDTOs(corContacts));
        return crmLeadPT;
    }
}



