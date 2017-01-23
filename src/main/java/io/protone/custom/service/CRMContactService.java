package io.protone.custom.service;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.mapper.CustomCRMContactMapper;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class CRMContactService {

    @Inject
    private CustomCRMContactMapper customCRMContactMapper;

    @Inject
    private CustomCRMTaskMapper customCRMTaskMapper;

    @Inject
    private CRMContactRepository crmContactRepository;

    @Inject
    private CRMTaskRepository crmTaskRepository;

    @Inject
    private CORAddressRepository corAddressRepository;

    @Inject
    private CORAreaRepository corAreaRepository;

    @Inject
    private CORPersonRepository corPersonRepository;

    @Inject
    private CORContactRepository corContactRepository;

    @Inject
    private CORAssociationRepository corAssociationRepository;

    @Inject
    private TRAIndustryRepository traIndustryRepository;

    @Inject
    private CORRangeRepository corRangeRepository;

    @Inject
    private CORSizeRepository corSizeRepository;

    public List<CrmContactPT> getAllContact() {
        List<CrmContactPT> crmContactPTS = new ArrayList<>();
        List<CRMContact> crmContactList = crmContactRepository.findAll();
        crmContactList.stream().forEach(crmContact -> {
            crmContactPTS.add(fetchContact(crmContact));
        });
        return crmContactPTS;
    }

    public CrmContactPT saveContact(CrmContactPT crmContactPT) {
        CRMContact crmContact = customCRMContactMapper.createCrmContactEntity(crmContactPT);
        Map<CORPerson, List<CORContact>> personCORContactMap = customCRMContactMapper.createMapPersonContact(crmContactPT);
        CORAddress addres = customCRMContactMapper.createAdressEntity(crmContactPT);
        CORArea area = customCRMContactMapper.createCorAreaEntity(crmContactPT);
        TRAIndustry industry = customCRMContactMapper.createIndustryEntity(crmContactPT);
        CORSize corSize = customCRMContactMapper.createCorSizeEntity(crmContactPT);
        CORRange corRange = customCRMContactMapper.createRangeEntity(crmContactPT);
        List<CRMTask> crmTask = crmTaskRepository.save(customCRMContactMapper.createTaskEntities(crmContactPT));
        crmContact = crmContactRepository.save(crmContact);
        addres = corAddressRepository.save(addres);
        CRMContact finalCrmContact = crmContact;
        corAssociationRepository.save(customCRMContactMapper.createContactTasksAssociationEntity(crmContact, crmTask));
        corAssociationRepository.save(customCRMContactMapper.createContactIndustryAssociationEntity(crmContact, industry));
        corAssociationRepository.save(customCRMContactMapper.createContactAreaAssociationEntity(crmContact, area));
        corAssociationRepository.save(customCRMContactMapper.createContactSizeAssociationEntity(crmContact, corSize));
        corAssociationRepository.save(customCRMContactMapper.createContactRangeAssociationEntity(crmContact, corRange));
        corAssociationRepository.save(customCRMContactMapper.createAddressAssociationEntity(crmContact, addres));
        Map<CORPerson, List<CORContact>> savedEntities = new HashMap<>();
        personCORContactMap.keySet().stream().forEach(person -> {
            CORPerson personSaved = corPersonRepository.save(person);
            List<CORContact> personContactList = corContactRepository.save(personCORContactMap.get(person));
            corAssociationRepository.save(customCRMContactMapper.createContactPersonAssociationEntity(finalCrmContact, personSaved));
            corAssociationRepository.save(customCRMContactMapper.createPersonContactAssociationEntity(person, personContactList));
            savedEntities.put(personSaved, personContactList);
        });
        return customCRMContactMapper.buildContactDTOFromEntities(crmContact, addres, corSize, corRange, area, crmTask, savedEntities, industry, new CoreManagedUserPT());
    }

    public void deleteContact(String shortcut) {
        CRMContact crmContact = crmContactRepository.findByShortName(shortcut);
        deleteContact(crmContact);
    }

    public CrmContactPT getContact(String shortcut) {
        CRMContact crmContact = crmContactRepository.findByShortName(shortcut);
        return fetchContact(crmContact);
    }

    private CrmContactPT fetchContact(CRMContact crmContact) {
        List<CORAssociation> contactAddressAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORAddress.class.getName());
        List<CORAssociation> contactAreaAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORArea.class.getName());
        List<CORAssociation> contactSizeAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORSize.class.getName());
        List<CORAssociation> contactRangeAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORRange.class.getName());
        List<CORAssociation> contactIndustryAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), TRAIndustry.class.getName());
        List<CORAssociation> contactPersonAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORPerson.class.getName());
        List<CORAssociation> contactTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CRMTask.class.getName());
        List<CORAssociation> contactPersonContactAssociation = new ArrayList<CORAssociation>();
        Map<CORPerson, List<CORContact>> fetchedEntites = new HashMap<>();
        contactPersonAssociation.stream().forEach(person -> {
            CORPerson corPerson = corPersonRepository.findOne(person.getTargetId());
            List<Long> corContacts = contactPersonContactAssociation.stream().filter(association -> corPerson.getId().equals(association.getId())).map(CORAssociation::getTargetId).collect(toList());
            List<CORContact> contacts = corContactRepository.findAll(corContacts);
            fetchedEntites.put(corPerson, contacts);
        });
        CORAddress address = corAddressRepository.findOne(contactAddressAssociation.get(0).getTargetId());
        CORArea area = corAreaRepository.findOne(contactAreaAssociation.get(0).getTargetId());
        TRAIndustry industry = traIndustryRepository.findOne(contactIndustryAssociation.get(0).getTargetId());
        CORRange range = corRangeRepository.findOne(contactRangeAssociation.get(0).getTargetId());
        CORSize size = corSizeRepository.findOne(contactSizeAssociation.get(0).getTargetId());
        List<Long> tasksID = contactTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        List<CRMTask> taskList = crmTaskRepository.findAll(tasksID);
        return customCRMContactMapper.buildContactDTOFromEntities(crmContact, address, size, range, area, taskList, fetchedEntites, industry, new CoreManagedUserPT());
    }
    public CrmContactPT update(CrmContactPT crmContactPT) {
        return null;
    }
    private void deleteContact(CRMContact crmContact) {
        List<CORAssociation> contactAddressAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORAddress.class.getName());
        List<CORAssociation> contactAreaAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORArea.class.getName());
        List<CORAssociation> contactSizeAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORSize.class.getName());
        List<CORAssociation> contactRangeAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORRange.class.getName());
        List<CORAssociation> contactIndustryAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), TRAIndustry.class.getName());
        List<CORAssociation> contactPersonAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORPerson.class.getName());
        List<CORAssociation> contactTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CRMTask.class.getName());
        List<CORAssociation> contactPersonContactAssociation = new ArrayList<CORAssociation>();
        contactPersonAssociation.stream().forEach(person -> {
            CORPerson corPerson = corPersonRepository.findOne(person.getTargetId());
            contactPersonContactAssociation.stream().filter(association -> corPerson.getId().equals(association.getId())).map(CORAssociation::getTargetId).collect(toList())
                .forEach(contactID -> {
                    corContactRepository.delete(contactID);
                });

        });
        corAddressRepository.delete(contactAddressAssociation.get(0).getTargetId());
        contactTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList()).forEach(taskId -> {
            crmTaskRepository.delete(taskId);
        });
        corAssociationRepository.delete(contactPersonContactAssociation);
        corAssociationRepository.delete(contactPersonAssociation);
        corAssociationRepository.delete(contactIndustryAssociation);
        corAssociationRepository.delete(contactRangeAssociation);
        corAssociationRepository.delete(contactSizeAssociation);
        corAssociationRepository.delete(contactAreaAssociation);
        corAssociationRepository.delete(contactAddressAssociation);
        crmContactRepository.delete(crmContact);
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut) {
        CRMContact crmLead = crmContactRepository.findByShortName(shortcut);
        List<CORAssociation> leadTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CRMTask.class.getName());
        List<Long> tasksID = leadTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        List<CRMTask> taskList = crmTaskRepository.findAll(tasksID);
        return customCRMTaskMapper.transformTasksFromEntity(taskList);
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId) {
        CRMContact crmLead = crmContactRepository.findByShortName(shortcut);
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(crmLead.getId(), taskId, CRMTask.class.getName());
        CRMTask crmTask = crmTaskRepository.findOne(task.getId());
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public void deleteLeadTask(String shortcut, Long taskId) {
        CRMContact crmLead = crmContactRepository.findByShortName(shortcut);
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(crmLead.getId(), taskId, CRMTask.class.getName());
        crmTaskRepository.delete(task.getId());
        corAssociationRepository.delete(task);
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT) {
        CRMContact crmLead = crmContactRepository.findByShortName(shortcut);
        CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(taskPT));
        corAssociationRepository.save(customCRMContactMapper.createContactTaskAssociationEntity(crmLead, crmTask));
        return customCRMTaskMapper.createCrmTask(crmTask);
    }
}
