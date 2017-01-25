package io.protone.custom.service;

import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.mapper.CustomCRMLeadMapper;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import io.protone.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class CRMLeadService {

    @Inject
    private CustomCRMLeadMapper customCRMLeadMapper;

    @Inject
    private CRMLeadRepository crmLeadRepository;

    @Inject
    private CRMLeadStatusRepository crmLeadStatusRepository;

    @Inject
    private CRMLeadSourceRepository crmLeadSourceRepository;

    @Inject
    private TRAIndustryRepository industryRepository;

    @Inject
    private CORPersonRepository personRepository;

    @Inject
    private CRMTaskRepository crmTaskRepository;

    @Inject
    private CORAssociationRepository associationRepository;


    @Inject
    private CORAddressRepository corAddressRepository;

    @Inject
    private CORAreaRepository corAreaRepository;

    @Inject
    private NetworkService networkService;

    @Inject
    private CORContactRepository corContactRepository;

    @Inject
    private CORPersonRepository corPersonRepository;

    @Inject
    private CustomCRMTaskMapper customCRMTaskMapper;
    @Inject
    private UserService userService;
    @Transactional
    public List<CrmLeadPT> getAllLeads(CORNetwork corNetwork) {
        List<CrmLeadPT> crmLeadPTList = new ArrayList<>();
        List<CRMLead> crmLeadList = crmLeadRepository.findAll();
        crmLeadList.stream().forEach(crmLead -> {

            List<CORAssociation> leadAddressAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CORAddress.class.getName());
            List<CORAssociation> leadAreaAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CORArea.class.getName());
            List<CORAssociation> leadStatusAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CRMLeadStatus.class.getName());
            List<CORAssociation> leadSourceAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CRMLeadSource.class.getName());
            List<CORAssociation> leadIndustryAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), TRAIndustry.class.getName());
            List<CORAssociation> leadPersonAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CORPerson.class.getName());
            List<CORAssociation> leadTaskAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CRMTask.class.getName());
            List<CORAssociation> leadPersonContactAssociation = new ArrayList<CORAssociation>();

            leadPersonAssociation.forEach(person -> {
                List<CORAssociation> personContactAssociation = associationRepository.findBySourceIdAndTargetClass(person.getSourceId(), CORContact.class.getName());
                leadPersonContactAssociation.addAll(personContactAssociation);
            });

            CORAddress address = corAddressRepository.findOne(leadAddressAssociation.get(0).getTargetId());
            CORAddress corAddress = corAddressRepository.findOne(leadAddressAssociation.get(0).getTargetId());
            CORArea area = corAreaRepository.findOne(leadAreaAssociation.get(0).getTargetId());
            CRMLeadStatus status = crmLeadStatusRepository.findOne(leadStatusAssociation.get(0).getTargetId());
            CRMLeadSource source = crmLeadSourceRepository.findOne(leadSourceAssociation.get(0).getTargetId());
            TRAIndustry industry = industryRepository.findOne(leadIndustryAssociation.get(0).getTargetId());
            CORPerson leadPersonContact = personRepository.findOne(leadPersonAssociation.get(0).getTargetId());
            List<Long> corContactID = leadPersonContactAssociation.stream().map(CORAssociation::getTargetId).collect(toList());

            List<CORContact> corContact = corContactRepository.findAll(corContactID);
            List<Long> tasksID = leadTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
            List<CRMTask> taskList = crmTaskRepository.findAll(tasksID);

            crmLeadPTList.add(customCRMLeadMapper.createDTOFromEntites(crmLead,
                taskList, leadPersonContact, address, corContact, industry, area, source, status));
        });
        return crmLeadPTList;
    }

    public CrmLeadPT saveLead(CrmLeadPT lead,CORNetwork corNetwork) {
        List<CORAssociation> associations = new ArrayList<>();
        CRMLeadStatus leadStatus = crmLeadStatusRepository.findByName(lead.getStatus().getName());
        CRMLeadSource leadSource = crmLeadSourceRepository.findByName(lead.getSource().getName());
        CORArea corArea = corAreaRepository.findByName(lead.getArea().getName());
        TRAIndustry industry = industryRepository.findByName(lead.getIndustry().getName());

        CRMLead crmLead = crmLeadRepository.save(customCRMLeadMapper.createLeadEntity(lead));
        CORAddress corAddress = corAddressRepository.save(customCRMLeadMapper.createAdressEntity(lead));
        List<CORContact> contact = corContactRepository.save(customCRMLeadMapper.createContactEntity(lead));
        CORPerson person = corPersonRepository.save(customCRMLeadMapper.createPersonEntity(lead));
        List<CRMTask> crmTasksList = crmTaskRepository.save(customCRMTaskMapper.createTasksEntity(lead.getTasks()));

        associations.add(customCRMLeadMapper.createAddressAssociationEntity(crmLead, corAddress));
        associations.add(customCRMLeadMapper.createLeadAreaAssociationEntity(crmLead, corArea));
        associations.add(customCRMLeadMapper.createLeadStatusAssociationEntity(crmLead, leadStatus));
        associations.add(customCRMLeadMapper.createLeadSourceAssociationEntity(crmLead, leadSource));
        associations.add(customCRMLeadMapper.createLeadIndustryAssociationEntity(crmLead, industry));
        associations.add(customCRMLeadMapper.createLeadPersonAssociationEntity(crmLead, person));
        associations.addAll(customCRMLeadMapper.createLeadContactAssociationEntity(person, contact));
        associations.addAll(customCRMLeadMapper.createLeadTasksAssociationEntity(crmLead, crmTasksList));
        associationRepository.save(associations);
        return customCRMLeadMapper.createDTOFromEntites(crmLead, crmTasksList, person, corAddress, contact, industry, corArea, leadSource, leadStatus);
    }

    public void deleteLead(String shortcut,CORNetwork corNetwork) {

        CRMLead crmLead = crmLeadRepository.findByShortcut(shortcut);
        List<CORAssociation> leadAddressAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CORAddress.class.getName());
        List<CORAssociation> leadPersonAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CORPerson.class.getName());
        List<CORAssociation> leadTaskAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CRMTask.class.getName());
        List<CORAssociation> leadPersonContactAssociation = new ArrayList<CORAssociation>();

        leadPersonAssociation.forEach(person -> {
            List<CORAssociation> personContactAssociation = associationRepository.findBySourceIdAndTargetClass(person.getId(), CORContact.class.getName());
            leadPersonContactAssociation.addAll(personContactAssociation);
        });
        corAddressRepository.delete(leadAddressAssociation.get(0).getTargetId());
        personRepository.delete(leadPersonAssociation.get(0).getTargetId());
        leadPersonContactAssociation.stream().map(CORAssociation::getTargetId).collect(toList()).forEach(id -> {
            corContactRepository.delete(id);
        });
        leadTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList()).forEach(id -> {
            crmTaskRepository.delete(id);
        });
        associationRepository.deleteBySourceIdAndTargetClass(crmLead.getId(), CORAddress.class.getName());
        associationRepository.deleteBySourceIdAndTargetClass(crmLead.getId(), CORArea.class.getName());
        associationRepository.deleteBySourceIdAndTargetClass(crmLead.getId(), CRMLeadStatus.class.getName());
        associationRepository.deleteBySourceIdAndTargetClass(crmLead.getId(), CRMLeadSource.class.getName());
        associationRepository.deleteBySourceIdAndTargetClass(crmLead.getId(), TRAIndustry.class.getName());
        associationRepository.deleteBySourceIdAndTargetClass(crmLead.getId(), CORPerson.class.getName());
        associationRepository.deleteBySourceIdAndTargetClass(crmLead.getId(), CRMTask.class.getName());
        crmLeadRepository.delete(crmLead);

    }

    @Transactional
    public CrmLeadPT getLead(String shortcut,CORNetwork corNetwork) {

        CRMLead crmLead = crmLeadRepository.findByShortcut(shortcut);
        List<CORAssociation> leadAddressAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CORAddress.class.getName());
        List<CORAssociation> leadAreaAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CORArea.class.getName());
        List<CORAssociation> leadStatusAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CRMLeadStatus.class.getName());
        List<CORAssociation> leadSourceAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CRMLeadSource.class.getName());
        List<CORAssociation> leadIndustryAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), TRAIndustry.class.getName());
        List<CORAssociation> leadPersonAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CORPerson.class.getName());
        List<CORAssociation> leadTaskAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CRMTask.class.getName());
        List<CORAssociation> leadPersonContactAssociation = new ArrayList<CORAssociation>();

        leadPersonAssociation.forEach(person -> {
            List<CORAssociation> personContactAssociation = associationRepository.findBySourceIdAndTargetClass(person.getId(), CORContact.class.getName());
            leadPersonContactAssociation.addAll(personContactAssociation);
        });

        CORAddress address = corAddressRepository.findOne(leadAddressAssociation.get(0).getTargetId());
        CORAddress corAddress = corAddressRepository.findOne(leadAddressAssociation.get(0).getTargetId());
        CORArea area = corAreaRepository.findOne(leadAreaAssociation.get(0).getTargetId());
        CRMLeadStatus status = crmLeadStatusRepository.findOne(leadStatusAssociation.get(0).getTargetId());
        CRMLeadSource source = crmLeadSourceRepository.findOne(leadSourceAssociation.get(0).getTargetId());
        TRAIndustry industry = industryRepository.findOne(leadIndustryAssociation.get(0).getTargetId());
        CORPerson leadPersonContact = personRepository.findOne(leadPersonAssociation.get(0).getTargetId());
        List<Long> corContactID = leadPersonContactAssociation.stream().map(CORAssociation::getTargetId).collect(toList());

        List<CORContact> corContact = corContactRepository.findAll(corContactID);
        List<Long> tasksID = leadTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        List<CRMTask> taskList = crmTaskRepository.findAll(tasksID);

        return customCRMLeadMapper.createDTOFromEntites(crmLead,
            taskList, leadPersonContact, corAddress, corContact, industry, area, source, status);

    }

    public CrmLeadPT update(CrmLeadPT leadPT,CORNetwork corNetwork) {
        deleteLead(leadPT.getShortname(),corNetwork);
        return saveLead(leadPT,corNetwork);
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut,CORNetwork corNetwork) {
        CRMLead crmLead = crmLeadRepository.findByShortcut(shortcut);
        List<CORAssociation> leadTaskAssociation = associationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CRMTask.class.getName());
        List<Long> tasksID = leadTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        List<CRMTask> taskList = crmTaskRepository.findAll(tasksID);
        return customCRMTaskMapper.transformTasksFromEntity(taskList);
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId,CORNetwork corNetwork) {
        CRMLead crmLead = crmLeadRepository.findByShortcut(shortcut);
        CORAssociation task = associationRepository.findBySourceIdAndTargetIdAndTargetClass(crmLead.getId(), taskId, CRMTask.class.getName());
        CRMTask crmTask = crmTaskRepository.findOne(task.getId());
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public void deleteLeadTask(String shortcut, Long taskId,CORNetwork corNetwork) {
        CRMLead crmLead = crmLeadRepository.findByShortcut(shortcut);
        CORAssociation task = associationRepository.findBySourceIdAndTargetIdAndTargetClass(crmLead.getId(), taskId, CRMTask.class.getName());
        crmTaskRepository.delete(task.getId());
        associationRepository.delete(task);
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT,CORNetwork corNetwork) {
        CRMLead crmLead = crmLeadRepository.findByShortcut(shortcut);
        CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(taskPT));
        associationRepository.save(customCRMLeadMapper.createLeadTaskAssociationEntity(crmLead, crmTask));
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public CrmTaskPT updateLeadTask(String shortcut, CrmTaskPT crmTask,CORNetwork corNetwork) {
        CRMLead crmAccount = crmLeadRepository.findByShortcut(shortcut);
        CORAssociation task = associationRepository.findBySourceIdAndTargetIdAndTargetClass(crmAccount.getId(), crmTask.getId(), CRMTask.class.getName());
        CRMTask crmTask1 = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmTask));
        return customCRMTaskMapper.createCrmTask(crmTask1);
    }
}
