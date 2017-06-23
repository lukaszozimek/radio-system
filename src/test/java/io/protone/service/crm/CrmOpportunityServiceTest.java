package io.protone.service.crm;

import io.protone.ProtoneApp;
import io.protone.domain.*;
import io.protone.repository.*;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.crm.CrmContactRepository;
import io.protone.repository.crm.CrmLeadRepository;
import io.protone.repository.crm.CrmOpportunityRepository;
import io.protone.service.crm.CrmOpportunityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 29/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CrmOpportunityServiceTest {

    private static final String TEST_SHORTNAME = "Test";
    @Autowired
    private CrmOpportunityService crmOpportunityService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CrmOpportunityRepository crmOpportunityRepository;

    @Autowired
    private CrmLeadRepository crmLeadRepository;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

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
    public void shouldGetAllOpportunites() throws Exception {
        //when
        CrmOpportunity crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(null);
        crmOpportunity.setNetwork(corNetwork);
        crmOpportunityRepository.saveAndFlush(crmOpportunity);

        //then
        List<CrmOpportunity> allOpportunity = crmOpportunityService.getAllOpportunity(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(allOpportunity);
        assertEquals(1, allOpportunity.size());
        assertNotNull(allOpportunity.get(0).getId());
        assertNotNull(allOpportunity.get(0).getCreatedBy());

    }

    @Test
    public void shouldSaveCrmOpportunity() throws Exception {
        //when
        CrmOpportunity crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(null);
        crmOpportunity.setNetwork(corNetwork);

        //then
        crmOpportunity = crmOpportunityService.saveOpportunity(crmOpportunity);

        //assert
        assertNotNull(crmOpportunity);
        assertNotNull(crmOpportunity.getId());
        assertNotNull(crmOpportunity.getCreatedBy());


    }

    @Test
    public void shouldSaveCrmOpportuniteWithAssociatedLead() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.network(corNetwork);
        crmLead = crmLeadRepository.saveAndFlush(crmLead);

        CrmOpportunity crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(null);
        crmOpportunity.setNetwork(corNetwork);
        crmOpportunity.setLead(crmLead);

        //then
        crmOpportunity = crmOpportunityService.saveOpportunity(crmOpportunity);

        //assert
        assertNotNull(crmOpportunity);
        assertNotNull(crmOpportunity.getId());
        assertNotNull(crmOpportunity.getLead());
        assertNotNull(crmOpportunity.getCreatedBy());
        assertEquals(crmOpportunity.getLead().getId(), crmLead.getId());

    }

    @Test
    public void shouldSaveCrmContactWithWithAssociatedContact() throws Exception {
        //when
        CrmContact crmContact = factory.manufacturePojo(CrmContact.class);
        crmContact.setNetwork(corNetwork);
        crmContact = crmContactRepository.saveAndFlush(crmContact);

        CrmOpportunity crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(null);
        crmOpportunity.setNetwork(corNetwork);
        crmOpportunity.setContact(crmContact);

        //then
        CrmOpportunity localOppotunity = crmOpportunityService.saveOpportunity(crmOpportunity);

        //assert
        assertNotNull(localOppotunity);
        assertNotNull(localOppotunity.getId());
        assertNotNull(localOppotunity.getContact());
        assertNotNull(localOppotunity.getCreatedBy());
        assertEquals(localOppotunity.getContact().getId(), crmContact.getId());


    }

    @Test
    public void shouldSaveCrmContacthWithAssociatedAccount() throws Exception {
        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setNetwork(corNetwork);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);

        CrmOpportunity crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(null);
        crmOpportunity.setNetwork(corNetwork);
        crmOpportunity.setAccount(crmAccount);


        //then
        CrmOpportunity localOppotunity = crmOpportunityService.saveOpportunity(crmOpportunity);

        //assert
        assertNotNull(localOppotunity);
        assertNotNull(localOppotunity.getId());
        assertNotNull(localOppotunity.getCreatedBy());
        assertNotNull(localOppotunity.getAccount());
        assertNotNull(localOppotunity.getAccount().getCreatedBy());
        assertEquals(localOppotunity.getAccount().getId(), crmAccount.getId());

    }

    @Test
    public void shouldGetContact() throws Exception {
        //when
        CrmOpportunity crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(null);
        crmOpportunity.setNetwork(corNetwork);
        crmOpportunity = crmOpportunityRepository.saveAndFlush(crmOpportunity);

        //then
        crmOpportunityService.getOpportunity(crmOpportunity.getShortName(), crmOpportunity.getNetwork().getShortcut());
        CrmOpportunity localCrmContact = crmOpportunityRepository.findOne(crmOpportunity.getId());

        //assert
        assertNotNull(localCrmContact);
    }

    @Test
    public void shouldSaveContactTask() throws Exception {
        //when
        CrmOpportunity crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(null);
        crmOpportunity.setNetwork(corNetwork);
        crmOpportunity = crmOpportunityRepository.saveAndFlush(crmOpportunity);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        //then
        CrmTask crmTask1 = crmOpportunityService.saveOrUpdateTaskAssociatiedWithOpportunity(crmTask, crmOpportunity.getShortName(), crmOpportunity.getNetwork().getShortcut());
        CrmOpportunity localContact = crmOpportunityService.getOpportunity(crmOpportunity.getShortName(), crmOpportunity.getNetwork().getShortcut());

        //assert
        assertNotNull(crmTask1);
        assertNotNull(crmTask1.getId());
        assertNotNull(crmTask1.getOpportunity());
        assertEquals(crmTask1.getOpportunity().getId(), crmOpportunity.getId());

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
        CrmTask crmTask1 = crmOpportunityService.saveOrUpdateTaskAssociatiedWithOpportunity(crmTask, "", corNetwork.getShortcut());

        //assert
        assertNull(crmTask1);
    }

    @Test
    public void shouldGetTasksAssociatedWithOpportunity() throws Exception {
        //when
        CrmOpportunity crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(null);
        crmOpportunity.setNetwork(corNetwork);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        crmOpportunity = crmOpportunityRepository.saveAndFlush(crmOpportunity);
        CrmTask crmTask1 = crmOpportunityService.saveOrUpdateTaskAssociatiedWithOpportunity(crmTask, crmOpportunity.getShortName(), crmOpportunity.getNetwork().getShortcut());

        //then
        List<CrmTask> localTask = crmOpportunityService.getTasksAssociatedWithOpportunity(crmOpportunity.getShortName(), crmOpportunity.getNetwork().getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(localTask);
        assertEquals(1, localTask.size());
        assertEquals(crmTask1.getId(), localTask.get(0).getId());
    }

    @Test
    public void shouldGetTaskAssociatedWithOpportunity() throws Exception {
        //when
        CrmOpportunity crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(null);
        crmOpportunity.setNetwork(corNetwork);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        crmOpportunity = crmOpportunityRepository.saveAndFlush(crmOpportunity);
        CrmTask crmTask1 = crmOpportunityService.saveOrUpdateTaskAssociatiedWithOpportunity(crmTask, crmOpportunity.getShortName(), crmOpportunity.getNetwork().getShortcut());

        //then
        CrmTask localTask = crmOpportunityService.getTaskAssociatedWithOpportunity(crmTask1.getId(), crmOpportunity.getNetwork().getShortcut());

        //assert
        assertNotNull(localTask);
        assertEquals(crmTask1.getId(), localTask.getId());

    }

    @Test
    public void shouldDeleteOpportunityTask() throws Exception {
        //when
        CrmOpportunity crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(null);
        crmOpportunity.setNetwork(corNetwork);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        crmOpportunity = crmOpportunityRepository.saveAndFlush(crmOpportunity);
        CrmTask crmTask1 = crmOpportunityService.saveOrUpdateTaskAssociatiedWithOpportunity(crmTask, crmOpportunity.getShortName(), crmOpportunity.getNetwork().getShortcut());

        // then
        crmOpportunityService.deleteOpportunityTask(crmOpportunity.getShortName(), crmTask1.getId(), crmOpportunity.getNetwork().getShortcut());

        CrmTask localTask = crmOpportunityService.getTaskAssociatedWithOpportunity(crmTask1.getId(), crmOpportunity.getNetwork().getShortcut());
        CrmOpportunity localContact = crmOpportunityService.getOpportunity(crmOpportunity.getShortName(), crmOpportunity.getNetwork().getShortcut());

        //assert
        assertNull(localTask);
        assertEquals(true, localContact.getTasks().isEmpty());


    }

    @Test
    public void shouldDeleteOpportunity() throws Exception {
        //when
        CrmOpportunity crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(null);
        crmOpportunity.setNetwork(corNetwork);
        crmOpportunity = crmOpportunityRepository.saveAndFlush(crmOpportunity);

        //then
        crmOpportunityService.deleteOpportunity(crmOpportunity.getShortName(), crmOpportunity.getNetwork().getShortcut());
        CrmOpportunity localContact = crmOpportunityRepository.findOne(crmOpportunity.getId());

        //assert
        assertNull(localContact);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoLeadWithSameShortNameInOneNetwork() {

        /// /when
        CrmOpportunity crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(null);
        crmOpportunity.setShortName(TEST_SHORTNAME);
        crmOpportunity.setNetwork(corNetwork);
        CrmOpportunity crmOpportunity1 = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity1.setId(null);
        crmOpportunity1.setShortName(TEST_SHORTNAME);
        crmOpportunity1.setNetwork(corNetwork);

        crmOpportunity = crmOpportunityService.saveOpportunity(crmOpportunity);
        crmOpportunity1 = crmOpportunityService.saveOpportunity(crmOpportunity1);


    }

    @Test
    public void shouldSaveTwoLeadWithSameShortNameInDifferentNetwork() {
        //given
        CorNetwork corNetworkSecond = factory.manufacturePojo(CorNetwork.class);
        corNetworkSecond.setId(null);
        corNetworkSecond = corNetworkRepository.save(corNetworkSecond);

        ///when
        CrmOpportunity crmOpportunity = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity.setId(null);
        crmOpportunity.setShortName(TEST_SHORTNAME);
        crmOpportunity.setNetwork(corNetwork);
        CrmOpportunity crmOpportunity1 = factory.manufacturePojo(CrmOpportunity.class);
        crmOpportunity1.setId(null);
        crmOpportunity1.setShortName(TEST_SHORTNAME);
        crmOpportunity1.setNetwork(corNetworkSecond);

        //then
        crmOpportunity = crmOpportunityService.saveOpportunity(crmOpportunity);
        crmOpportunity1 = crmOpportunityService.saveOpportunity(crmOpportunity1);

    }
}
