package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomCRMAccountMapper {
    @Inject
    private CustomTRAPersonMapper customTRAPersonMapper;

    @Inject
    private CustomCORAddressMapper corAddressMapper;

    @Inject
    private CustomTRAIndustryMapper industryMapper;

    @Inject
    private CustomCORRangeMapper customCORRangeMapper;

    @Inject
    private CustomCORAreaMapper customCORAreaMapper;

    @Inject
    private CustomCORSizeMapper customCORSizeMapper;

    @Inject
    private CustomCRMTaskMapper customCRMTaskMapper;

    public CRMAccount createCrmAcountEntity(CrmAccountPT crmAccountPT) {
        CRMAccount crmAccount = new CRMAccount();
        crmAccount.setId(crmAccountPT.getId());
        return crmAccount
            .paymentDelay(crmAccountPT.getPaymentDelay())
            .externalId1(crmAccountPT.getIdNumber1())
            .externalId2(crmAccountPT.getIdNumber2())
            .name(crmAccountPT.getName())
            .shortName(crmAccountPT.getShortName())
            .vatNumber(crmAccountPT.getVatNumber());
    }

    public CRMAccount createCrmAcountEntity(TraCustomerPT traCustomerPT) {
        CRMAccount crmAccount = new CRMAccount();
        crmAccount.setId(traCustomerPT.getId());
        return crmAccount
            .paymentDelay(traCustomerPT.getPaymentDelay())
            .externalId1(traCustomerPT.getIdNumber1())
            .externalId2(traCustomerPT.getIdNumber2())
            .name(traCustomerPT.getName())
            .shortName(traCustomerPT.getShortName())
            .vatNumber(traCustomerPT.getVatNumber());
    }

    public TraCustomerPT createCustomerTrafficDTO(CRMAccount traCustomerPT, CORAddress address,
                                                  CORSize corSize,
                                                  CORRange range,
                                                  CORArea corArea,
                                                  Map<CORPerson, List<CORContact>> corPersonListMap,
                                                  TRAIndustry industry, CoreManagedUserPT coreManagedUserPT) {
        TraCustomerPT crmAccount = new TraCustomerPT();
        crmAccount.setId(traCustomerPT.getId());
        return crmAccount.
            id(traCustomerPT.getId())
            .name(traCustomerPT.getName())
            .idNumber1(traCustomerPT.getExternalId1())
            .idNumber2(traCustomerPT.getExternalId2())
            .shortName(traCustomerPT.getShortName())
            .vatNumber(traCustomerPT.getVatNumber())
            .paymentDelay(traCustomerPT.getPaymentDelay())
            .industry(industryMapper.tRAIndustryToTRAIndustryDTO(industry))
            .account(coreManagedUserPT)
            .size(customCORSizeMapper.cORSizeToCORSizeDTO(corSize))
            .range(customCORRangeMapper.cORRangeToCORRangeDTO(range))
            .adress(corAddressMapper.cORAddressToCORAddressDTO(address))
            .persons(customTRAPersonMapper.createDTOObject(corPersonListMap))
            .area(customCORAreaMapper.cORAreaToCORAreaDTO(corArea));
    }


    public CORAddress createAdressEntity(CrmAccountPT crmContactPT) {
        return corAddressMapper.cORAddressDTOToCORAddress(crmContactPT.getAdress());
    }

    public TRAIndustry createIndustryEntity(CrmAccountPT crmContactPT) {
        return industryMapper.tRAIndustryDTOToTRAIndustry(crmContactPT.getIndustry());
    }

    public CORArea createCorAreaEntity(CrmAccountPT crmContactPT) {
        return customCORAreaMapper.cORAreaDTOToCORArea(crmContactPT.getArea());
    }

    public CORSize createCorSizeEntity(CrmAccountPT crmContactPT) {
        return customCORSizeMapper.cORSizeDTOToCORSize(crmContactPT.getSize());
    }

    public CORRange createRangeEntity(CrmAccountPT crmContactPT) {
        return customCORRangeMapper.cORRangeDTOToCORRange(crmContactPT.getRange());
    }

    public Map<CORPerson, List<CORContact>> createMapPersonContact(CrmAccountPT crmContactPT) {
        return customTRAPersonMapper.createMapPersonToContact(crmContactPT.getPersons());
    }


    public CORAddress createAdressEntity(TraCustomerPT traCustomerPT) {
        return corAddressMapper.cORAddressDTOToCORAddress(traCustomerPT.getAdress());
    }

    public TRAIndustry createIndustryEntity(TraCustomerPT traCustomerPT) {
        return industryMapper.tRAIndustryDTOToTRAIndustry(traCustomerPT.getIndustry());
    }

    public CORArea createCorAreaEntity(TraCustomerPT traCustomerPT) {
        return customCORAreaMapper.cORAreaDTOToCORArea(traCustomerPT.getArea());
    }

    public CORSize createCorSizeEntity(TraCustomerPT traCustomerPT) {
        return customCORSizeMapper.cORSizeDTOToCORSize(traCustomerPT.getSize());
    }

    public CORRange createRangeEntity(TraCustomerPT crmContactPT) {
        return customCORRangeMapper.cORRangeDTOToCORRange(crmContactPT.getRange());
    }

    public Map<CORPerson, List<CORContact>> createMapPersonContact(TraCustomerPT crmContactPT) {
        return customTRAPersonMapper.createMapPersonToContact(crmContactPT.getPersons());
    }


    public List<CRMTask> createTaskEntities(CrmAccountPT crmContactPT) {
        return customCRMTaskMapper.createTasksEntity(crmContactPT.getTasks());
    }

    public CrmAccountPT buildContactDTOFromEntities(CRMAccount crmAccount,
                                                    CORAddress address,
                                                    CORSize corSize,
                                                    CORRange range,
                                                    CORArea corArea,
                                                    List<CRMTask> taskList,
                                                    Map<CORPerson, List<CORContact>> corPersonListMap,
                                                    TRAIndustry industry, CoreManagedUserPT coreManagedUserPT) {
        return new CrmAccountPT()
            .id(crmAccount.getId())
            .name(crmAccount.getName())
            .idNumber1(crmAccount.getExternalId1())
            .idNumber2(crmAccount.getExternalId2())
            .shortName(crmAccount.getShortName())
            .vatNumber(crmAccount.getVatNumber())
            .paymentDelay(crmAccount.getPaymentDelay())
            .industry(industryMapper.tRAIndustryToTRAIndustryDTO(industry))
            .account(coreManagedUserPT)
            .size(customCORSizeMapper.cORSizeToCORSizeDTO(corSize))
            .range(customCORRangeMapper.cORRangeToCORRangeDTO(range))
            .adress(corAddressMapper.cORAddressToCORAddressDTO(address))
            .persons(customTRAPersonMapper.createDTOObject(corPersonListMap))
            .area(customCORAreaMapper.cORAreaToCORAreaDTO(corArea))
            .tasks(customCRMTaskMapper.transformTasksFromEntity(taskList));

    }

    public CORAssociation createAddressAssociationEntity(CRMAccount crmContact, CORAddress address) {
        CORAssociation association = new CORAssociation();
        association.setName("ADDRESS");
        association.setSourceClass(CRMAccount.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(CORAddress.class.getName());
        association.setTargetId(address.getId());
        return association;
    }

    public CORAssociation createAccountRangeAssociationEntity(CRMAccount crmContact, CORRange range) {
        CORAssociation association = new CORAssociation();
        association.setName("RANGE");
        association.setSourceClass(CRMAccount.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(CORRange.class.getName());
        association.setTargetId(range.getId());
        return association;
    }

    public CORAssociation createAccountSizeAssociationEntity(CRMAccount crmContact, CORSize size) {
        CORAssociation association = new CORAssociation();
        association.setName("SIZE");
        association.setSourceClass(CRMAccount.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(CORSize.class.getName());
        association.setTargetId(size.getId());
        return association;
    }

    public CORAssociation createAccountIndustryAssociationEntity(CRMAccount crmContact, TRAIndustry industry) {
        CORAssociation association = new CORAssociation();
        association.setName("TRAIndustry");
        association.setSourceClass(CRMAccount.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(TRAIndustry.class.getName());
        association.setTargetId(industry.getId());
        return association;
    }

    public CORAssociation createAccountAreaAssociationEntity(CRMAccount crmAccount, CORArea area) {
        CORAssociation association = new CORAssociation();
        association.setName("CORArea");
        association.setSourceClass(CRMAccount.class.getName());
        association.setSourceId(crmAccount.getId());
        association.setTargetClass(CORArea.class.getName());
        association.setTargetId(area.getId());
        return association;
    }

    public CORAssociation createAccountOrderAssociationEntity(CRMAccount crmAccount, TRAOrder order) {
        CORAssociation association = new CORAssociation();
        association.setName("CORArea");
        association.setSourceClass(CRMAccount.class.getName());
        association.setSourceId(crmAccount.getId());
        association.setTargetClass(TRAOrder.class.getName());
        association.setTargetId(order.getId());
        return association;
    }

    public CORAssociation createAccountCampaingAssociationEntity(CRMAccount crmAccount, TRACampaign campaign) {
        CORAssociation association = new CORAssociation();
        association.setName("CORArea");
        association.setSourceClass(CRMAccount.class.getName());
        association.setSourceId(crmAccount.getId());
        association.setTargetClass(TRACampaign.class.getName());
        association.setTargetId(campaign.getId());
        return association;
    }

    public CORAssociation createAccountPersonAssociationEntity(CRMAccount crmContact, CORPerson corPerson) {
        CORAssociation association = new CORAssociation();
        association.setName("CORPerson");
        association.setSourceClass(CRMAccount.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(CORPerson.class.getName());
        association.setTargetId(corPerson.getId());
        return association;
    }

    public List<CORAssociation> createPersonAccountAssociationEntity(CORPerson person, List<CORContact> corContacts) {
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

    public List<CORAssociation> createAccountTasksAssociationEntity(CRMAccount crmContact, List<CRMTask> crmTasks) {
        List<CORAssociation> associations = new ArrayList<>();
        for (CRMTask crmTask : crmTasks) {
            associations.add(createAccountTaskAssociationEntity(crmContact, crmTask));
        }
        return associations;
    }

    public CORAssociation createAccountTaskAssociationEntity(CRMAccount crmContact, CRMTask crmTask) {
        CORAssociation association = new CORAssociation();
        association.setName("CRMTask");
        association.setSourceClass(CRMContact.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(CRMTask.class.getName());
        association.setTargetId(crmTask.getId());
        return association;
    }
}
