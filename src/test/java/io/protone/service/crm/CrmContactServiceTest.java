package io.protone.service.crm;

import io.protone.ProtoneApp;
import io.protone.domain.*;
import io.protone.repository.crm.CrmContactRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 29.04.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CrmContactServiceTest {

    @Autowired
    private CrmContactService crmContactService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CrmContactRepository crmContactRepository;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);

    }

    @Test
    public void shouldGetAllContact() throws Exception {
        //when
        CrmContact crmContact = factory.manufacturePojo(CrmContact.class);
        crmContact.setId(null);
        crmContact.setNetwork(corNetwork);
        crmContactRepository.saveAndFlush(crmContact);

        //then
        List<CrmContact> crmContactList = crmContactService.getAllContact(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(crmContactList);
        assertEquals(1, crmContactList.size());
        assertNotNull(crmContactList.get(0).getId());
    }

    @Test
    public void shouldSaveCrmContact() throws Exception {
        //when
        CrmContact localCrmContact = factory.manufacturePojo(CrmContact.class);
        localCrmContact.setId(null);
        localCrmContact.setNetwork(corNetwork);
        localCrmContact.setPerson(factory.manufacturePojo(CorPerson.class));
        localCrmContact.getPerson().setNetwork(corNetwork);
        localCrmContact.getPerson().getContacts().stream().forEach(corContact -> corContact.setNetwork(corNetwork));
        localCrmContact.setAddres(factory.manufacturePojo(CorAddress.class));
        localCrmContact.getAddres().setNetwork(corNetwork);

        //then
        localCrmContact = crmContactService.saveContact(localCrmContact);

        //assert
        assertNotNull(localCrmContact);
        assertNotNull(localCrmContact.getId());
        assertNotNull(localCrmContact.getPerson().getId());
        assertNotNull(localCrmContact.getAddres().getId());
        localCrmContact.getPerson().getContacts().stream().forEach(corContact -> {
            assertNotNull(corContact.getId());
        });


    }

    @Test
    public void shouldSaveCrmContactWithOutAdress() throws Exception {
        //when
        CrmContact localCrmContact = factory.manufacturePojo(CrmContact.class);
        localCrmContact.setId(null);
        localCrmContact.setNetwork(corNetwork);
        localCrmContact.setPerson(factory.manufacturePojo(CorPerson.class));
        localCrmContact.getPerson().setNetwork(corNetwork);
        localCrmContact.getPerson().getContacts().stream().forEach(corContact -> corContact.setNetwork(corNetwork));

        //then
        localCrmContact = crmContactService.saveContact(localCrmContact);

        //assert
        assertNotNull(localCrmContact);
        assertNotNull(localCrmContact.getId());
        assertNotNull(localCrmContact.getPerson().getId());
        assertNull(localCrmContact.getAddres());
        localCrmContact.getPerson().getContacts().stream().forEach(corContact -> {
            assertNotNull(corContact.getId());
        });


    }

    @Test
    public void shouldSaveCrmContactWithOutPerson() throws Exception {
        //when
        CrmContact localCrmContact = factory.manufacturePojo(CrmContact.class);
        localCrmContact.setId(null);
        localCrmContact.setNetwork(corNetwork);

        //then
        localCrmContact = crmContactService.saveContact(localCrmContact);

        //assert
        assertNotNull(localCrmContact);
        assertNotNull(localCrmContact.getId());
        assertNull(localCrmContact.getPerson());

    }

    @Test
    public void shouldSaveCrmContactWithOutPersonContact() throws Exception {
        //when
        CrmContact localCrmContact = factory.manufacturePojo(CrmContact.class);
        localCrmContact.setId(null);
        localCrmContact.setNetwork(corNetwork);
        localCrmContact.setPerson(factory.manufacturePojo(CorPerson.class));
        localCrmContact.getPerson().setNetwork(corNetwork);
        localCrmContact.getPerson().setContacts(null);
        localCrmContact.setAddres(factory.manufacturePojo(CorAddress.class));
        localCrmContact.getAddres().setNetwork(corNetwork);

        //then
        localCrmContact = crmContactService.saveContact(localCrmContact);

        //assert
        assertNotNull(localCrmContact);
        assertNotNull(localCrmContact.getId());
        assertNotNull(localCrmContact.getPerson().getId());
        assertNull(localCrmContact.getPerson().getContacts());

    }

    @Test
    public void shouldGetContact() throws Exception {
        //when
        CrmContact crmContact = factory.manufacturePojo(CrmContact.class);
        crmContact.setId(null);
        crmContact.setNetwork(corNetwork);
        crmContact = crmContactRepository.saveAndFlush(crmContact);

        //then
        crmContactService.getContact(crmContact.getShortName(), crmContact.getNetwork().getShortcut());
        CrmContact localCrmContact = crmContactRepository.findOne(crmContact.getId());

        //assert
        assertNotNull(localCrmContact);
    }

    @Test
    public void shouldSaveContactTask() throws Exception {
        //when
        CrmContact crmContact = factory.manufacturePojo(CrmContact.class);
        crmContact.setId(null);
        crmContact.setNetwork(corNetwork);
        crmContact = crmContactRepository.saveAndFlush(crmContact);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        //then
        CrmTask crmTask1 = crmContactService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, crmContact.getShortName(), crmContact.getNetwork().getShortcut());
        CrmContact localContact = crmContactService.getContact(crmContact.getShortName(), crmContact.getNetwork().getShortcut());

        //assert
        assertNotNull(crmTask1);
        assertNotNull(crmTask1.getId());
        assertNotNull(crmTask1.getContact());
        assertEquals(crmTask1.getContact().getId(), crmContact.getId());

        assertNotNull(localContact.getTasks());
        assertEquals(1, localContact.getTasks().size());
        assertEquals(crmTask1.getId(), localContact.getTasks().stream().findFirst().get().getId());
    }

    @Test
    public void shouldNotSaveContactTaskIfContactDoesntExist() throws Exception {
        //when
        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        //then
        CrmTask crmTask1 = crmContactService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, "", corNetwork.getShortcut());

        //assert
        assertNull(crmTask1);
    }

    @Test
    public void shouldGetTasksAssociatedWithContact() throws Exception {
        //when
        CrmContact crmContact = factory.manufacturePojo(CrmContact.class);
        crmContact.setId(null);
        crmContact.setNetwork(corNetwork);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        crmContact = crmContactRepository.saveAndFlush(crmContact);
        CrmTask crmTask1 = crmContactService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, crmContact.getShortName(), crmContact.getNetwork().getShortcut());

        //then
        List<CrmTask> localTask = crmContactService.getTasksAssociatedWithContact(crmContact.getShortName(), crmContact.getNetwork().getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(localTask);
        assertEquals(1, localTask.size());
        assertEquals(crmTask1.getId(), localTask.get(0).getId());
    }

    @Test
    public void shouldGetTaskAssociatedWithContact() throws Exception {
        //when
        CrmContact crmContact = factory.manufacturePojo(CrmContact.class);
        crmContact.setId(null);
        crmContact.setNetwork(corNetwork);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        crmContact = crmContactRepository.saveAndFlush(crmContact);
        CrmTask crmTask1 = crmContactService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, crmContact.getShortName(), crmContact.getNetwork().getShortcut());

        //then
        CrmTask localTask = crmContactService.getTaskAssociatedWithContact(crmTask1.getId(), crmContact.getNetwork().getShortcut());

        //assert
        assertNotNull(localTask);
        assertEquals(crmTask1.getId(), localTask.getId());

    }

    @Test
    public void shouldDeleteContactTask() throws Exception {
        //when
        CrmContact crmContact = factory.manufacturePojo(CrmContact.class);
        crmContact.setId(null);
        crmContact.setNetwork(corNetwork);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        crmContact = crmContactRepository.saveAndFlush(crmContact);
        CrmTask crmTask1 = crmContactService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, crmContact.getShortName(), crmContact.getNetwork().getShortcut());

        // then
        crmContactService.deleteContactTask(crmContact.getShortName(), crmTask1.getId(), crmContact.getNetwork().getShortcut());

        CrmTask localTask = crmContactService.getTaskAssociatedWithContact(crmTask1.getId(), crmContact.getNetwork().getShortcut());
        CrmContact localContact = crmContactService.getContact(crmContact.getShortName(), crmContact.getNetwork().getShortcut());

        //assert
        assertNull(localTask);
        assertEquals(true, localContact.getTasks().isEmpty());


    }

    @Test
    public void shouldDeleteContact() throws Exception {
        //when
        CrmContact crmContact = factory.manufacturePojo(CrmContact.class);
        crmContact.setId(null);
        crmContact.setNetwork(corNetwork);
        crmContact = crmContactRepository.saveAndFlush(crmContact);

        //then
        crmContactService.deleteContact(crmContact.getShortName(), crmContact.getNetwork().getShortcut());
        CrmContact localContact = crmContactRepository.findOne(crmContact.getId());

        //assert
        assertNull(localContact);
    }


}
