package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.*;
import io.protone.domain.*;
import io.protone.domain.enumeration.CORContactTypeEnum;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 18.01.2017.
 */
@Service
public class CustomCRMLeadMapper {



    public CRMLead createLeadEntity(CrmLeadPT leadPT) {
        CRMLead crmLead = new CRMLead();
        crmLead.setId(leadPT.getId());
        crmLead.setName(leadPT.getName());
        crmLead.setDescription(leadPT.getDescription());
        crmLead.setShortcut(leadPT.getShortname());
        return crmLead;
    }

    public CORAddress createAdressEntity(CrmLeadPT leadPT) {
        CORAddress corAddress = new CORAddress();
        corAddress.setId(leadPT.getId());
        corAddress.setCity(leadPT.getAdress().getCity());
        corAddress.setCountry(leadPT.getAdress().getCountry());
        corAddress.setNumber(leadPT.getAdress().getNumber());
        corAddress.setPostalCode(leadPT.getAdress().getPostalCode());
        corAddress.setStreet(leadPT.getAdress().getStreet());
        return corAddress;
    }

    public CORPerson createPersonEntity(CrmLeadPT leadPT) {
        CORPerson person = new CORPerson();
        person.setFirstName(leadPT.getPerson().getFirstName());
        person.setLastName(leadPT.getPerson().getLastName());
        person.setDescription(leadPT.getPerson().getDescription());
        return person;
    }

    public List<CORContact> createContactEntity(CrmLeadPT leadPT) {
        List<CORContact> corContacts = new ArrayList<>();
        for (CoreContactPT contactPT : leadPT.getContact()) {
            CORContact contact = new CORContact();
            contact.contact(contactPT.getContact());
            contact.contactType(contactPT.getContactType());
            corContacts.add(contact);
        }
        return corContacts;
    }

    public List<CRMTask> createTasksEntity(List<CrmTaskPT> leadTasks) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<CRMTask> taskList = new ArrayList<>();
        for (CrmTaskPT task : leadTasks) {
            CRMTask crmTask = new CRMTask();
            crmTask.setId(task.getId());
            crmTask.setSubject(task.getSubject());
            crmTask.setActivityDate(LocalDate.parse(task.getActivityDate(), formatter));
            crmTask.setActivityLength(task.getActivityLenght());
            crmTask.setComment(task.getComment());
            taskList.add(crmTask);
        }
        return taskList;
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

    public List<CORAssociation> createLeadTaskAssociationEntity(CRMLead lead, List<CRMTask> crmTasks) {
        List<CORAssociation> associations = new ArrayList<>();
        for (CRMTask crmTask : crmTasks) {
            CORAssociation association = new CORAssociation();
            association.setName("CRMTask");
            association.setSourceClass(CRMLead.class.getName());
            association.setSourceId(lead.getId());
            association.setTargetClass(CRMTask.class.getName());
            association.setTargetId(crmTask.getId());
            associations.add(association);
        }
        return associations;
    }

    public CrmLeadPT createDTOFromEntites(CRMLead lead,
                                          List<CRMTask> tasks,
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
        crmLeadPT.setIndustry(new ConfIndustryPT().id(industry.getId())
            .name(industry.getName())
            .networkId(industry.getId()));
        crmLeadPT.setArea(new CoreAreaPT().id(area
            .getId())
            .name(area.getName())
            .networkId(area.getNetwork().getId()));
        crmLeadPT.setAdress(new CoreAddressPT()
            .id(address.getId())
            .country(address.getCountry())
            .city(address.getCity())
            .number(address.getNumber())
            .postalCode(address.getPostalCode())
            .street(address.getStreet()))
        ;
        crmLeadPT.setSource(new ConfLeadSourcePT()
            .id(leadSource.getId())
            .name(leadSource.getName()));
        crmLeadPT.setStatus(new ConfLeadStatusPT()
            .id(leadStatus.getId())
            .name(leadStatus.getName()));
        crmLeadPT.setTasks(transformTasksFromEntity(tasks));
        crmLeadPT.setPerson(transformPersonFromEntity(person));
        crmLeadPT.setContact(transformContactFromEntity(corContacts));
        return crmLeadPT;
    }

    private List<CoreContactPT> transformContactFromEntity(List<CORContact> contacts) {
        List<CoreContactPT> contactPTList = new ArrayList<>();
        contacts.stream().forEach(contact -> {
            contactPTList.add(new CoreContactPT().id(contact.getId())
                .contact(contact.getContact())
                .contactType(contact.getContactType()));
        });
        return contactPTList;
    }

    private ConfPersonPT transformPersonFromEntity(CORPerson person) {
        return new ConfPersonPT().id(person.getId())
            .firstName(person.getFirstName())
            .lastName(person.getLastName())
            .description(person.getDescription());
    }

    private List<CrmTaskPT> transformTasksFromEntity(List<CRMTask> tasks) {
        List<CrmTaskPT> crmTaskPTList = new ArrayList<>();
        tasks.stream().forEach(task -> {
            crmTaskPTList.add(new CrmTaskPT().id(task.getId())
                .activityDate(task.getActivityDate().toString())
                .activityLenght(task.getActivityLength())
                .assignedTo(new CoreManagedUserPT())
                .comment(task.getComment())
                .subject(task.getSubject())
                .createdBy(new CoreManagedUserPT()));
        });
        return crmTaskPTList;
    }

}
