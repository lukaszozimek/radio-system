package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.service.dto.TraCustomerPersonPT;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Mapper for the entity CRMContact and its DTO CRMContactDTO.
 */
@Service
public class CustomCRMContactMapper {
    @Inject
    CustomTRAPersonMapper customTRAPersonMapper;

    @Inject
    CustomCORAddressMapper corAddressMapper;

    @Inject
    CustomTRAIndustryMapper industryMapper;

    @Inject
    CustomCORRangeMapper customCORRangeMapper;

    @Inject
    CustomCORAreaMapper customCORAreaMapper;

    @Inject
    CustomCORSizeMapper customCORSizeMapper;

    @Inject
    CustomCRMTaskMapper customCRMTaskMapper;
    public CRMContact createCrmContactEntity(CrmContactPT crmContactPT) {
        return new CRMContact()
            .paymentDelay(crmContactPT.getPaymentDelay())
            .externalId1(crmContactPT.getIdNumber1())
            .externalId2(crmContactPT.getIdNumber2())
            .name(crmContactPT.getName())
            .shortName(crmContactPT.getShortName())
            .vatNumber(crmContactPT.getVatNumber());
    }

    public CORAddress createAdressEntity(CrmContactPT crmContactPT) {
        return corAddressMapper.cORAddressDTOToCORAddress(crmContactPT.getAdress());
    }

    public TRAIndustry createIndustryEntity(CrmContactPT crmContactPT) {
        return industryMapper.tRAIndustryDTOToTRAIndustry(crmContactPT.getIndustry());
    }

    public CORArea createCorAreaEntity(CrmContactPT crmContactPT) {
        return customCORAreaMapper.cORAreaDTOToCORArea(crmContactPT.getArea());
    }

    public CORSize createCorSizeEntity(CrmContactPT crmContactPT) {
        return customCORSizeMapper.cORSizeDTOToCORSize(crmContactPT.getSize());
    }

    public CORRange createRangeEntity(CrmContactPT crmContactPT) {
        return customCORRangeMapper.cORRangeDTOToCORRange(crmContactPT.getRange());
    }

    public Map<CORPerson, List<CORContact>> createMapPersonContact(CrmContactPT crmContactPT) {
        return customTRAPersonMapper.createMapPersonToContact(crmContactPT.getPersons());
    }

    public List<CRMTask> createTaskEntities(CrmContactPT crmContactPT) {
        return customCRMTaskMapper.createTasksEntity(crmContactPT.getTasks());
    }

    public CrmContactPT buildContactDTOFromEntities(CRMContact crmContact,
                                                    CORAddress address,
                                                    CORSize corSize,
                                                    CORRange range,
                                                    CORArea corArea,
                                                    List<CRMTask> taskList,
                                                    Map<CORPerson, List<CORContact>> corPersonListMap,
                                                    TRAIndustry industry,CoreManagedUserPT coreManagedUserPT) {
        return new CrmContactPT()
            .id(crmContact.getId())
            .name(crmContact.getName())
            .idNumber1(crmContact.getExternalId1())
            .idNumber2(crmContact.getExternalId2())
            .shortName(crmContact.getShortName())
            .vatNumber(crmContact.getVatNumber())
            .paymentDelay(crmContact.getPaymentDelay())
            .industry(industryMapper.tRAIndustryToTRAIndustryDTO(industry))
            .account(coreManagedUserPT)
            .size(customCORSizeMapper.cORSizeToCORSizeDTO(corSize))
            .range(customCORRangeMapper.cORRangeToCORRangeDTO(range))
            .adress(corAddressMapper.cORAddressToCORAddressDTO(address))
            .persons(customTRAPersonMapper.createDTOObject(corPersonListMap))
            .area(customCORAreaMapper.cORAreaToCORAreaDTO(corArea))
            .tasks(customCRMTaskMapper.transformTasksFromEntity(taskList));

    }

    public CORAssociation createAddressAssociationEntity(CRMContact crmContact, CORAddress address) {
        CORAssociation association = new CORAssociation();
        association.setName("ADDRESS");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(CORAddress.class.getName());
        association.setTargetId(address.getId());
        return association;
    }

    public CORAssociation createContactStatusAssociationEntity(CRMContact crmContact, CORArea area) {
        CORAssociation association = new CORAssociation();
        association.setName("AREA");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(CRMLeadStatus.class.getName());
        association.setTargetId(area.getId());
        return association;
    }

    public CORAssociation createContactSourceAssociationEntity(CRMContact crmContact, CORRange range) {
        CORAssociation association = new CORAssociation();
        association.setName("RANGE");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(CRMLeadSource.class.getName());
        association.setTargetId(range.getId());
        return association;
    }

    public CORAssociation createContactSourceAssociationEntity(CRMContact crmContact, CORSize size) {
        CORAssociation association = new CORAssociation();
        association.setName("SIZE");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(CRMLeadSource.class.getName());
        association.setTargetId(size.getId());
        return association;
    }

    public CORAssociation createContactIndustryAssociationEntity(CRMContact crmContact, TRAIndustry industry) {
        CORAssociation association = new CORAssociation();
        association.setName("TRAIndustry");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(TRAIndustry.class.getName());
        association.setTargetId(industry.getId());
        return association;
    }

    public CORAssociation createContactAreaAssociationEntity(CRMContact crmContact, CORArea area) {
        CORAssociation association = new CORAssociation();
        association.setName("CORArea");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(CORArea.class.getName());
        association.setTargetId(area.getId());
        return association;
    }

    public CORAssociation createContactPersonAssociationEntity(CRMContact crmContact, CORPerson corPerson) {
        CORAssociation association = new CORAssociation();
        association.setName("CORPerson");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(CORPerson.class.getName());
        association.setTargetId(corPerson.getId());
        return association;
    }

    public List<CORAssociation> createPersonContactAssociationEntity(CORPerson person, List<CORContact> corContacts) {
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

    public List<CORAssociation> createContactTasksAssociationEntity(CRMContact crmContact, List<CRMTask> crmTasks) {
        List<CORAssociation> associations = new ArrayList<>();
        for (CRMTask crmTask : crmTasks) {
            associations.add(createContactTaskAssociationEntity(crmContact, crmTask));
        }
        return associations;
    }

    public CORAssociation createContactTaskAssociationEntity(CRMContact crmContact, CRMTask crmTask) {
        CORAssociation association = new CORAssociation();
        association.setName("CRMTask");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(CRMTask.class.getName());
        association.setTargetId(crmTask.getId());
        return association;
    }
}
