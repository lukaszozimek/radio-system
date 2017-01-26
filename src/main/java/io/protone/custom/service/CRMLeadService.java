package io.protone.custom.service;

import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.custom.service.dto.CrmTaskPT;
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

import static java.util.stream.Collectors.reducing;
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
    @Inject
    private CRMTaskService crmTaskService;

    @Transactional
    public List<CrmLeadPT> getAllLeads(CORNetwork corNetwork) {
        List<CrmLeadPT> crmLeadPTList = new ArrayList<>();
        List<CRMLead> crmLeadList = crmLeadRepository.findByNetwork(corNetwork);
        crmLeadList.stream().forEach(crmLead -> {

            List<CORAssociation> leadAddressAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CORAddress.class.getName(), corNetwork);
            List<CORAssociation> leadAreaAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CORArea.class.getName(), corNetwork);
            List<CORAssociation> leadStatusAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CRMLeadStatus.class.getName(), corNetwork);
            List<CORAssociation> leadSourceAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CRMLeadSource.class.getName(), corNetwork);
            List<CORAssociation> leadIndustryAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), TRAIndustry.class.getName(), corNetwork);
            List<CORAssociation> leadPersonAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CORPerson.class.getName(), corNetwork);
            List<CORAssociation> leadPersonContactAssociation = new ArrayList<CORAssociation>();

            leadPersonAssociation.forEach(person -> {
                List<CORAssociation> personContactAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(person.getSourceId(), CORContact.class.getName(), corNetwork);
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

            List<CrmTaskPT> taskList = crmTaskService.getLeadTasks(crmLead, corNetwork);

            crmLeadPTList.add(customCRMLeadMapper.createDTOFromEntites(crmLead,
                taskList, leadPersonContact, address, corContact, industry, area, source, status));
        });
        return crmLeadPTList;
    }

    public CrmLeadPT saveLead(CrmLeadPT lead, CORNetwork corNetwork) {
        List<CORAssociation> associations = new ArrayList<>();
        CRMLeadStatus leadStatus = crmLeadStatusRepository.findByNameAndNetwork(lead.getStatus().getName(), corNetwork);
        CRMLeadSource leadSource = crmLeadSourceRepository.findOneByNameAndNetwork(lead.getSource().getName(), corNetwork);
        CORArea corArea = corAreaRepository.findOneByName(lead.getArea().getName());
        TRAIndustry industry = industryRepository.findByNameAndNetwork(lead.getIndustry().getName(), corNetwork);

        CRMLead crmLead = crmLeadRepository.save(customCRMLeadMapper.createLeadEntity(lead));
        CORAddress corAddress = corAddressRepository.save(customCRMLeadMapper.createAdressEntity(lead));
        List<CORContact> contact = corContactRepository.save(customCRMLeadMapper.createContactEntity(lead));
        CORPerson person = corPersonRepository.save(customCRMLeadMapper.createPersonEntity(lead));

        associations.add(customCRMLeadMapper.createAddressAssociationEntity(crmLead, corAddress));
        associations.add(customCRMLeadMapper.createLeadAreaAssociationEntity(crmLead, corArea));
        associations.add(customCRMLeadMapper.createLeadStatusAssociationEntity(crmLead, leadStatus));
        associations.add(customCRMLeadMapper.createLeadSourceAssociationEntity(crmLead, leadSource));
        associations.add(customCRMLeadMapper.createLeadIndustryAssociationEntity(crmLead, industry));
        associations.add(customCRMLeadMapper.createLeadPersonAssociationEntity(crmLead, person));
        associations.addAll(customCRMLeadMapper.createLeadContactAssociationEntity(person, contact));
        associationRepository.save(associations);
        List<CrmTaskPT> crmTaskPTS = crmTaskService.saveLeadTasks(lead, crmLead, corNetwork);
        return customCRMLeadMapper.createDTOFromEntites(crmLead, crmTaskPTS, person, corAddress, contact, industry, corArea, leadSource, leadStatus);
    }

    public void deleteLead(String shortcut, CORNetwork corNetwork) {

        CRMLead crmLead = crmLeadRepository.findOneByShortcutAndNetwork(shortcut, corNetwork);
        List<CORAssociation> leadAddressAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CORAddress.class.getName(), corNetwork);
        List<CORAssociation> leadPersonAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CORPerson.class.getName(), corNetwork);
        List<CORAssociation> leadPersonContactAssociation = new ArrayList<CORAssociation>();

        leadPersonAssociation.forEach(person -> {
            List<CORAssociation> personContactAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(person.getId(), CORContact.class.getName(), corNetwork);
            leadPersonContactAssociation.addAll(personContactAssociation);
        });
        corAddressRepository.delete(leadAddressAssociation.get(0).getTargetId());
        personRepository.delete(leadPersonAssociation.get(0).getTargetId());
        leadPersonContactAssociation.stream().map(CORAssociation::getTargetId).collect(toList()).forEach(id -> {
            corContactRepository.delete(id);
        });
        crmTaskService.deleteLeadTask(crmLead, corNetwork);
        associationRepository.deleteBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CORAddress.class.getName(), corNetwork);
        associationRepository.deleteBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CORArea.class.getName(), corNetwork);
        associationRepository.deleteBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CRMLeadStatus.class.getName(), corNetwork);
        associationRepository.deleteBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CRMLeadSource.class.getName(), corNetwork);
        associationRepository.deleteBySourceIdAndTargetClassAndNetwork(crmLead.getId(), TRAIndustry.class.getName(), corNetwork);
        associationRepository.deleteBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CORPerson.class.getName(), corNetwork);
        crmLeadRepository.delete(crmLead);

    }

    @Transactional
    public CrmLeadPT getLead(String shortcut, CORNetwork corNetwork) {

        CRMLead crmLead = crmLeadRepository.findOneByShortcutAndNetwork(shortcut, corNetwork);
        List<CORAssociation> leadAddressAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CORAddress.class.getName(), corNetwork);
        List<CORAssociation> leadAreaAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CORArea.class.getName(), corNetwork);
        List<CORAssociation> leadStatusAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CRMLeadStatus.class.getName(), corNetwork);
        List<CORAssociation> leadSourceAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CRMLeadSource.class.getName(), corNetwork);
        List<CORAssociation> leadIndustryAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), TRAIndustry.class.getName(), corNetwork);
        List<CORAssociation> leadPersonAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CORPerson.class.getName(), corNetwork);
        List<CORAssociation> leadPersonContactAssociation = new ArrayList<CORAssociation>();

        leadPersonAssociation.forEach(person -> {
            List<CORAssociation> personContactAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(person.getId(), CORContact.class.getName(), corNetwork);
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

        List<CrmTaskPT> taskList = crmTaskService.getLeadTasks(crmLead, corNetwork);
        return customCRMLeadMapper.createDTOFromEntites(crmLead,
            taskList, leadPersonContact, corAddress, corContact, industry, area, source, status);

    }

    public CrmLeadPT update(CrmLeadPT leadPT, CORNetwork corNetwork) {
        deleteLead(leadPT.getShortname(), corNetwork);
        return saveLead(leadPT, corNetwork);
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut, CORNetwork corNetwork) {
        CRMLead crmLead = crmLeadRepository.findOneByShortcutAndNetwork(shortcut, corNetwork);
        return crmTaskService.getTasksAssociatedWithLead(crmLead, corNetwork);
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMLead crmLead = crmLeadRepository.findOneByShortcutAndNetwork(shortcut, corNetwork);
        return crmTaskService.getTaskAssociatedWithLead(crmLead, taskId, corNetwork);
    }

    public void deleteLeadTask(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMLead crmLead = crmLeadRepository.findOneByShortcutAndNetwork(shortcut, corNetwork);
        crmTaskService.deleteLeadTask(crmLead, taskId, corNetwork);
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT, CORNetwork corNetwork) {
        CRMLead crmLead = crmLeadRepository.findOneByShortcutAndNetwork(shortcut, corNetwork);
        return crmTaskService.createTasksAssociatedWithLead(crmLead, taskPT, corNetwork);
    }

    public CrmTaskPT updateLeadTask(String shortcut, CrmTaskPT crmTask, CORNetwork corNetwork) {
        CRMLead crmLead = crmLeadRepository.findOneByShortcutAndNetwork(shortcut, corNetwork);
        return crmTaskService.updateLeadTask(crmLead, crmTask, corNetwork);
    }
}
