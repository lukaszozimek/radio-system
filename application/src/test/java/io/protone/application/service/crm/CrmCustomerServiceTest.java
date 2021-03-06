package io.protone.application.service.crm;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorAddress;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorPerson;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorImageItemService;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.crm.service.CrmCustomerService;
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
public class CrmCustomerServiceTest {

    private static final String TEST_SHORTNAME = "TEST";

    @Autowired
    private CrmCustomerService crmCustomerService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private CorImageItemService corImageItemService;

    @Mock
    private S3Client s3Client;

    private CorNetwork corNetwork;

    private PodamFactory factory;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        doNothing().when(s3Client).upload(anyString(), anyString(), anyString(), anyObject(), anyString());
        when(s3Client.getCover(anyString(), anyString(), anyString())).thenReturn("test");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        ReflectionTestUtils.setField(corImageItemService, "s3Client", s3Client);
        ReflectionTestUtils.setField(crmCustomerService, "corImageItemService", corImageItemService);
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);

    }

    @Test
    public void shouldGetAllAccount() throws Exception {
        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(null);
        crmAccount.setNetwork(corNetwork);
        crmAccountRepository.saveAndFlush(crmAccount);

        //then
        Slice<CrmAccount> allCustomers = crmCustomerService.getAllCustomers(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(allCustomers.getContent());
        assertEquals(1, allCustomers.getContent().size());
        assertNotNull(allCustomers.getContent().get(0).getId());
    }

    @Test
    public void shouldSaveCrmAccount() throws Exception {
        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(null);
        crmAccount.setNetwork(corNetwork);
        crmAccount.setPerson(factory.manufacturePojo(CorPerson.class));
        crmAccount.getPerson().setNetwork(corNetwork);
        crmAccount.getPerson().getContacts().stream().forEach(corContact -> corContact.setNetwork(corNetwork));
        crmAccount.setAddres(factory.manufacturePojo(CorAddress.class));
        crmAccount.getAddres().setNetwork(corNetwork);

        //then
        crmAccount = crmCustomerService.saveCustomer(crmAccount);

        //assert
        assertNotNull(crmAccount);
        assertNotNull(crmAccount.getId());
        assertNotNull(crmAccount.getCreatedBy());
        assertNotNull(crmAccount.getPerson().getId());
        assertNotNull(crmAccount.getAddres().getId());
        assertNotNull(crmAccount.getAddres().getCreatedBy());

        crmAccount.getPerson().getContacts().stream().forEach(corContact -> {
            assertNotNull(corContact.getId());
        });


    }

    @Test
    public void shouldSaveCrmAccountWithOutAdress() throws Exception {
        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(null);
        crmAccount.setNetwork(corNetwork);
        crmAccount.setPerson(factory.manufacturePojo(CorPerson.class));
        crmAccount.getPerson().setNetwork(corNetwork);
        crmAccount.getPerson().getContacts().stream().forEach(corContact -> corContact.setNetwork(corNetwork));

        //then
        crmAccount = crmCustomerService.saveCustomer(crmAccount);

        //assert
        assertNotNull(crmAccount);
        assertNotNull(crmAccount.getId());
        assertNotNull(crmAccount.getCreatedBy());
        assertNotNull(crmAccount.getPerson().getId());
        assertNull(crmAccount.getAddres());
        crmAccount.getPerson().getContacts().stream().forEach(corContact -> {
            assertNotNull(corContact.getId());
            assertNotNull(corContact.getCreatedBy());

        });


    }

    @Test
    public void shouldSaveCrmAccountWithOutPerson() throws Exception {
        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(null);
        crmAccount.setNetwork(corNetwork);

        //then
        crmAccount = crmCustomerService.saveCustomer(crmAccount);

        //assert
        assertNotNull(crmAccount);
        assertNotNull(crmAccount.getId());
        assertNotNull(crmAccount.getCreatedBy());
        assertNull(crmAccount.getPerson());


    }

    @Test
    public void shouldSaveCrmAccountWithOutPersonContact() throws Exception {
        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(null);
        crmAccount.setNetwork(corNetwork);
        crmAccount.setPerson(factory.manufacturePojo(CorPerson.class));
        crmAccount.getPerson().setNetwork(corNetwork);
        crmAccount.getPerson().setContacts(null);
        crmAccount.setAddres(factory.manufacturePojo(CorAddress.class));
        crmAccount.getAddres().setNetwork(corNetwork);

        //then
        crmAccount = crmCustomerService.saveCustomer(crmAccount);

        //assert
        assertNotNull(crmAccount);
        assertNotNull(crmAccount.getId());
        assertNotNull(crmAccount.getCreatedBy());
        assertNotNull(crmAccount.getPerson().getId());
        assertNull(crmAccount.getPerson().getContacts());

    }

    @Test
    public void shouldGetAccount() throws Exception {
        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(null);
        crmAccount.setNetwork(corNetwork);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);

        //then
        crmCustomerService.getCustomer(crmAccount.getShortName(), crmAccount.getNetwork().getShortcut());
        CrmAccount localCrmContact = crmAccountRepository.findOne(crmAccount.getId());

        //assert
        assertNotNull(localCrmContact);
    }

    @Test
    public void shouldSaveAccountTask() throws Exception {
        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(null);
        crmAccount.setNetwork(corNetwork);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        //then
        CrmTask crmTask1 = crmCustomerService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, crmAccount.getShortName(), crmAccount.getNetwork().getShortcut());
        CrmAccount customer = crmCustomerService.getCustomer(crmAccount.getShortName(), crmAccount.getNetwork().getShortcut());

        //assert
        assertNotNull(crmTask1);
        assertNotNull(crmTask1.getId());
        assertNotNull(crmTask1.getCreatedBy());
        assertNotNull(crmTask1.getAccount());
        assertNotNull(crmTask1.getCreatedBy());

        assertEquals(crmTask1.getAccount().getId(), crmAccount.getId());

        assertNotNull(customer.getTasks());
        assertEquals(1, customer.getTasks().size());
        assertEquals(crmTask1.getId(), customer.getTasks().stream().findFirst().get().getId());
    }

    @Test
    public void shouldNotSaveAccountTaskIfContactDoesntExist() throws Exception {
        //when
        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        //then
        CrmTask crmTask1 = crmCustomerService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, "xxx", corNetwork.getShortcut());

        //assert
        assertNull(crmTask1);
    }

    @Test
    public void shouldGetTasksAssociatedWithAccount() throws Exception {
        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(null);
        crmAccount.setNetwork(corNetwork);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);
        CrmTask crmTask1 = crmCustomerService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, crmAccount.getShortName(), crmAccount.getNetwork().getShortcut());

        //then
        Slice<CrmTask> localTask = crmCustomerService.getTasksAssociatedWithAccount(crmAccount.getShortName(), crmAccount.getNetwork().getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(localTask.getContent());
        assertEquals(1, localTask.getContent().size());
        assertEquals(crmTask1.getId(), localTask.getContent().get(0).getId());

        assertNotNull(localTask.getContent().get(0).getCreatedBy());
    }

    @Test
    public void shouldGetTaskAssociatedWithAccount() throws Exception {
        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(null);
        crmAccount.setNetwork(corNetwork);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);
        CrmTask crmTask1 = crmCustomerService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, crmAccount.getShortName(), crmAccount.getNetwork().getShortcut());

        //then
        CrmTask localTask = crmCustomerService.getTaskAssociatedWithAccount(crmTask1.getId(), crmAccount.getNetwork().getShortcut());

        //assert
        assertNotNull(localTask);
        assertNotNull(localTask.getCreatedBy());
        assertEquals(crmTask1.getId(), localTask.getId());

    }

    @Test
    public void shouldDeleteAccountTask() throws Exception {
        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(null);
        crmAccount.setNetwork(corNetwork);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);
        CrmTask crmTask1 = crmCustomerService.saveOrUpdateTaskAssociatiedWithAccount(crmTask, crmAccount.getShortName(), crmAccount.getNetwork().getShortcut());

        // then
        crmCustomerService.deleteCustomerTask(crmAccount.getShortName(), crmTask1.getId(), crmAccount.getNetwork().getShortcut());

        CrmTask localTask = crmCustomerService.getTaskAssociatedWithAccount(crmTask1.getId(), crmAccount.getNetwork().getShortcut());
        CrmAccount localContact = crmCustomerService.getCustomer(crmAccount.getShortName(), crmAccount.getNetwork().getShortcut());

        //assert
        assertNull(localTask);
        assertEquals(true, localContact.getTasks().isEmpty());


    }

    @Test
    public void shouldDeleteAccount() throws Exception {
        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(null);
        crmAccount.setNetwork(corNetwork);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);

        //then
        crmCustomerService.deleteCustomer(crmAccount.getShortName(), crmAccount.getNetwork().getShortcut());
        CrmAccount localContact = crmAccountRepository.findOne(crmAccount.getId());

        //assert
        assertNull(localContact);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoCustomerWithSameShortNameInOneNetwork() throws TikaException, IOException, SAXException {

        /// /when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(null);
        crmAccount.setShortName(TEST_SHORTNAME);
        crmAccount.setNetwork(corNetwork);
        CrmAccount crmAccount1 = factory.manufacturePojo(CrmAccount.class);
        crmAccount1.setId(null);
        crmAccount1.setShortName(TEST_SHORTNAME);
        crmAccount1.setNetwork(corNetwork);

        crmAccount = crmCustomerService.saveCustomer(crmAccount);
        crmAccount1 = crmCustomerService.saveCustomer(crmAccount1);


    }

    @Test
    public void shouldSaveTwoCustomerWithSameShortNameInDifferentNetwork() throws TikaException, IOException, SAXException {
        //given
        CorNetwork corNetworkSecond = factory.manufacturePojo(CorNetwork.class);
        corNetworkSecond.setId(null);
        corNetworkSecond = corNetworkRepository.save(corNetworkSecond);

        ///when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(null);
        crmAccount.setShortName(TEST_SHORTNAME);
        crmAccount.setNetwork(corNetwork);
        CrmAccount crmAccount1 = factory.manufacturePojo(CrmAccount.class);
        crmAccount1.setId(null);
        crmAccount1.setShortName(TEST_SHORTNAME);
        crmAccount1.setNetwork(corNetworkSecond);

        //then
        crmAccount = crmCustomerService.saveCustomer(crmAccount);
        crmAccount1 = crmCustomerService.saveCustomer(crmAccount1);

    }

    @Test
    public void shouldSaveCrmContactWithImage() throws Exception {
        MockMultipartFile logo = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));

        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(null);
        crmAccount.setNetwork(corNetwork);
        crmAccount.setPerson(factory.manufacturePojo(CorPerson.class));
        crmAccount.getPerson().setNetwork(corNetwork);
        crmAccount.getPerson().getContacts().stream().forEach(corContact -> corContact.setNetwork(corNetwork));
        crmAccount.setAddres(factory.manufacturePojo(CorAddress.class));
        crmAccount.getAddres().setNetwork(corNetwork);

        //then
        crmAccount = crmCustomerService.saveCustomerWithImage(crmAccount, logo);

        //assert
        assertNotNull(crmAccount);
        assertNotNull(crmAccount.getId());
        assertNotNull(crmAccount.getCreatedBy());
        assertNotNull(crmAccount.getPerson().getId());
        assertNotNull(crmAccount.getAddres().getId());
        assertNotNull(crmAccount.getAddres().getCreatedBy());

        crmAccount.getPerson().getContacts().stream().forEach(corContact -> {
            assertNotNull(corContact.getId());
        });
        assertNotNull(crmAccount.getCorImageItem());
    }

}
