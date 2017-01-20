package io.protone.custom.service;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.service.mapper.CustomCRMContactMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class CRMContactService {

    @Inject
    private CustomCRMContactMapper customCRMContactMapper;

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

    public List<CrmContactPT> getAllContact() {
        List<CRMContact> crmContactList = crmContactRepository.findAll();
        crmContactList.stream().forEach(crmContact -> {

            List<CORAssociation> contactAddressAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORAddress.class.getName());
            List<CORAssociation> contactAreaAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORArea.class.getName());
            List<CORAssociation> contactStatusAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORSize.class.getName());
            List<CORAssociation> contactSourceAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORRange.class.getName());
            List<CORAssociation> contactIndustryAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), TRAIndustry.class.getName());
            List<CORAssociation> contactPersonAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CORPerson.class.getName());
            List<CORAssociation> contactTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CRMTask.class.getName());
            List<CORAssociation> contactPersonContactAssociation = new ArrayList<CORAssociation>();
            contactPersonAssociation.stream().forEach(person -> {

                ////TODO Implementation of associating person with Contact
            });
        });
        return null;
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
        corAssociationRepository.save(customCRMContactMapper.createContactSourceAssociationEntity(crmContact, corSize));
        corAssociationRepository.save(customCRMContactMapper.createContactSourceAssociationEntity(crmContact, corRange));
        corAssociationRepository.save(customCRMContactMapper.createAddressAssociationEntity(crmContact, addres));
        corAssociationRepository.save(customCRMContactMapper.createContactStatusAssociationEntity(crmContact, area));
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

    }

    public CrmContactPT getContact(String shortcut) {
        CRMContact crmContact = crmContactRepository.findByShortName(shortcut);


        return null;
    }
}
