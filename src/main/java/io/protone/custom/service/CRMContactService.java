package io.protone.custom.service;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.mapper.CustomCRMContactMapper;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import io.protone.service.UserService;
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

    @Inject
    private UserService userService;
    @Inject
    private CRMTaskService crmTaskService;

    public List<CrmContactPT> getAllContact(CORNetwork corNetwork) {
        List<CrmContactPT> crmContactPTS = new ArrayList<>();
        List<CRMContact> crmContactList = crmContactRepository.findAll();
        crmContactList.stream().forEach(crmContact -> {
            crmContactPTS.add(fetchContact(crmContact, corNetwork));
        });
        return crmContactPTS;
    }

    public CrmContactPT saveContact(CrmContactPT crmContactPT, CORNetwork corNetwork) {
        CRMContact crmContact = customCRMContactMapper.createCrmContactEntity(crmContactPT);
        Map<CORPerson, List<CORContact>> personCORContactMap = customCRMContactMapper.createMapPersonContact(crmContactPT);
        CORAddress addres = customCRMContactMapper.createAdressEntity(crmContactPT);
        CORArea area = customCRMContactMapper.createCorAreaEntity(crmContactPT);
        TRAIndustry industry = customCRMContactMapper.createIndustryEntity(crmContactPT);
        CORSize corSize = customCRMContactMapper.createCorSizeEntity(crmContactPT);
        CORRange corRange = customCRMContactMapper.createRangeEntity(crmContactPT);
        crmContact = crmContactRepository.save(crmContact);
        addres = corAddressRepository.save(addres);
        CRMContact finalCrmContact = crmContact;
        corAssociationRepository.save(customCRMContactMapper.createContactIndustryAssociationEntity(crmContact, industry));
        corAssociationRepository.save(customCRMContactMapper.createContactAreaAssociationEntity(crmContact, area));
        corAssociationRepository.save(customCRMContactMapper.createContactSizeAssociationEntity(crmContact, corSize));
        corAssociationRepository.save(customCRMContactMapper.createContactRangeAssociationEntity(crmContact, corRange));
        corAssociationRepository.save(customCRMContactMapper.createAddressAssociationEntity(crmContact, addres));
        List<CrmTaskPT> crmTask = crmTaskService.saveContactTask(crmContact, crmContactPT, corNetwork);

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

    public void deleteContact(String shortcut, CORNetwork corNetwork) {
        CRMContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut,corNetwork);
        deleteContact(crmContact, corNetwork);
    }

    public CrmContactPT getContact(String shortcut, CORNetwork corNetwork) {
        CRMContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut,corNetwork);
        return fetchContact(crmContact, corNetwork);
    }

    private CrmContactPT fetchContact(CRMContact crmContact, CORNetwork corNetwork) {
        List<CORAssociation> contactAddressAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), CORAddress.class.getName(),corNetwork);
        List<CORAssociation> contactAreaAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), CORArea.class.getName(),corNetwork);
        List<CORAssociation> contactSizeAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), CORSize.class.getName(),corNetwork);
        List<CORAssociation> contactRangeAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), CORRange.class.getName(),corNetwork);
        List<CORAssociation> contactIndustryAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), TRAIndustry.class.getName(),corNetwork);
        List<CORAssociation> contactPersonAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), CORPerson.class.getName(),corNetwork);

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


        List<CrmTaskPT> crmTaskPTS = crmTaskService.getCrmContactTask(crmContact, corNetwork);
        return customCRMContactMapper.buildContactDTOFromEntities(crmContact, address, size, range, area, crmTaskPTS, fetchedEntites, industry, new CoreManagedUserPT());
    }

    public CrmContactPT update(CrmContactPT crmContactPT, CORNetwork corNetwork) {

        deleteContact(crmContactPT.getShortName(), corNetwork);
        return saveContact(crmContactPT, corNetwork);

    }

    private void deleteContact(CRMContact crmContact, CORNetwork corNetwork) {
        List<CORAssociation> contactAddressAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), CORAddress.class.getName(),corNetwork);
        List<CORAssociation> contactAreaAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), CORArea.class.getName(),corNetwork);
        List<CORAssociation> contactSizeAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), CORSize.class.getName(),corNetwork);
        List<CORAssociation> contactRangeAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), CORRange.class.getName(),corNetwork);
        List<CORAssociation> contactIndustryAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), TRAIndustry.class.getName(),corNetwork);
        List<CORAssociation> contactPersonAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), CORPerson.class.getName(),corNetwork);
        List<CORAssociation> contactPersonContactAssociation = new ArrayList<CORAssociation>();
        contactPersonAssociation.stream().forEach(person -> {
            CORPerson corPerson = corPersonRepository.findOne(person.getTargetId());
            contactPersonContactAssociation.stream().filter(association -> corPerson.getId().equals(association.getId())).map(CORAssociation::getTargetId).collect(toList())
                .forEach(contactID -> {
                    corContactRepository.delete(contactID);
                });

        });
        corAddressRepository.delete(contactAddressAssociation.get(0).getTargetId());
        crmTaskService.deleteCrmContactTask(crmContact, corNetwork);
        corAssociationRepository.delete(contactPersonContactAssociation);
        corAssociationRepository.delete(contactPersonAssociation);
        corAssociationRepository.delete(contactIndustryAssociation);
        corAssociationRepository.delete(contactRangeAssociation);
        corAssociationRepository.delete(contactSizeAssociation);
        corAssociationRepository.delete(contactAreaAssociation);
        corAssociationRepository.delete(contactAddressAssociation);
        crmContactRepository.delete(crmContact);
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut, CORNetwork corNetwork) {
        CRMContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut,corNetwork);
        return crmTaskService.getTasksAssociatedWithContact(crmContact, corNetwork);
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut,corNetwork);
        return crmTaskService.getTaskAssociatedWithContact(crmContact, taskId, corNetwork);
    }

    public void deleteLeadTask(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut,corNetwork);
        crmTaskService.deleteContactTask(crmContact, taskId, corNetwork);
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT, CORNetwork corNetwork) {
        CRMContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut,corNetwork);
        return crmTaskService.createTasksAssociatedWithContact(crmContact, taskPT, corNetwork);
    }

    public CrmTaskPT updateLeadTask(String shortcut, CrmTaskPT crmTask, CORNetwork corNetwork) {
        CRMContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut,corNetwork);
        return crmTaskService.updateTaskContact(crmContact, crmTask, corNetwork);


    }
}
