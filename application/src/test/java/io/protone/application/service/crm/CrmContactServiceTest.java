package io.protone.application.service.crm;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorAddress;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorPerson;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorImageItemService;
import io.protone.crm.domain.CrmContact;
import io.protone.crm.domain.CrmLead;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.repostiory.CrmContactRepository;
import io.protone.crm.repostiory.CrmLeadRepository;
import io.protone.crm.service.CrmContactService;
import org.apache.tika.exception.TikaException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.xml.sax.SAXException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 29.04.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CrmContactServiceTest {

    private static final String TEST_SHORTNAME = "TEST";
    @Autowired
    private CrmContactService crmContactService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CrmContactRepository crmContactRepository;

    @Autowired
    private CorImageItemService corImageItemService;

    @Autowired
    private CrmLeadRepository crmLeadRepository;

    @Mock
    private S3Client s3Client;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        MockitoAnnotations.initMocks(this);
        doNothing().when(s3Client).upload(anyString(), anyString(), anyObject(), anyString());
        when(s3Client.getCover(anyString(), anyString())).thenReturn("test");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        ReflectionTestUtils.setField(corImageItemService, "s3Client", s3Client);
        ReflectionTestUtils.setField(crmContactService, "corImageItemService", corImageItemService);
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
        Slice<CrmContact> crmContactList = crmContactService.getAllContact(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(crmContactList.getContent());
        assertEquals(1, crmContactList.getContent().size());
        assertNotNull(crmContactList.getContent().get(0).getId());
        assertNotNull(crmContactList.getContent().get(0).getCreatedBy());
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

        assertNotNull(localCrmContact.getCreatedBy());


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

        assertNotNull(localCrmContact.getCreatedBy());


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

        assertNotNull(localCrmContact.getCreatedBy());

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
        assertNotNull(localCrmContact.getPerson().getCreatedBy());
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
        assertEquals(crmTask1.getCreatedBy().getId(), crmContact.getCreatedBy().getId());

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
        Slice<CrmTask> localTask = crmContactService.getTasksAssociatedWithContact(crmContact.getShortName(), crmContact.getNetwork().getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(localTask.getContent());
        assertEquals(1, localTask.getContent().size());
        assertEquals(crmTask1.getId(), localTask.getContent().get(0).getId());
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

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoContactWithSameShortNameInOneNetwork() {

        /// /when
        CrmContact crmContact = factory.manufacturePojo(CrmContact.class);
        crmContact.setId(null);
        crmContact.setShortName(TEST_SHORTNAME);
        crmContact.setNetwork(corNetwork);
        CrmContact crmContact1 = factory.manufacturePojo(CrmContact.class);
        crmContact1.setId(null);
        crmContact1.setShortName(TEST_SHORTNAME);
        crmContact1.setNetwork(corNetwork);

        crmContact = crmContactService.saveContact(crmContact);
        crmContact1 = crmContactService.saveContact(crmContact1);


    }

    @Test
    public void shouldSaveTwoContactWithSameShortNameInDifferentNetwork() {
        //given
        CorNetwork corNetworkSecond = factory.manufacturePojo(CorNetwork.class);
        corNetworkSecond.setId(null);
        corNetworkSecond = corNetworkRepository.save(corNetworkSecond);

        ///when
        CrmContact crmContact = factory.manufacturePojo(CrmContact.class);
        crmContact.setId(null);
        crmContact.setShortName(TEST_SHORTNAME);
        crmContact.setNetwork(corNetwork);
        CrmContact crmContact1 = factory.manufacturePojo(CrmContact.class);
        crmContact1.setId(null);
        crmContact1.setShortName(TEST_SHORTNAME);
        crmContact1.setNetwork(corNetworkSecond);

        //then
        crmContact = crmContactService.saveContact(crmContact);
        crmContact1 = crmContactService.saveContact(crmContact1);

    }

    @Test
    public void shoudlConvertLeadToContact() {
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.network(corNetwork);
        CrmLead crmLeadEntity = crmLeadRepository.saveAndFlush(crmLead);
        CrmContact crmContact = crmContactService.convertCrmLeadToContact(crmLeadEntity);

        //assert
        assertNotNull(crmContact);
        assertNotNull(crmContact.getId());
        assertNotNull(crmContact.getCrmLead());

    }

    @Test
    public void shouldSaveCrmContactWithImage() throws IOException, TikaException, SAXException {
        MockMultipartFile logo = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));

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
        localCrmContact = crmContactService.saveContactWithImage(localCrmContact, logo);

        //assert
        assertNotNull(localCrmContact);
        assertNotNull(localCrmContact.getId());
        assertNotNull(localCrmContact.getPerson().getId());
        assertNotNull(localCrmContact.getAddres().getId());
        localCrmContact.getPerson().getContacts().stream().forEach(corContact -> {
            assertNotNull(corContact.getId());
        });

        assertNotNull(localCrmContact.getCreatedBy());
        assertNotNull(localCrmContact.getCorImageItem());
    }
}
