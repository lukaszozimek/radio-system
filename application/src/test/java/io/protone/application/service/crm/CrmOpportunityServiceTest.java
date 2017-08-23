package io.protone.application.service.crm;


import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorAuthority;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.repository.CorUserRepository;
import io.protone.crm.domain.*;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.crm.repostiory.CrmContactRepository;
import io.protone.crm.repostiory.CrmLeadRepository;
import io.protone.crm.repostiory.CrmOpportunityRepository;
import io.protone.crm.service.CrmOpportunityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static io.protone.core.security.AuthoritiesConstants.ADMIN;
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

    @Autowired
    private CorUserRepository corUserRepository;

    private CorNetwork corNetwork;

    private PodamFactory factory;
    private String CONVERTED = "conv";

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
        Slice<CrmOpportunity> allOpportunity = crmOpportunityService.getAllOpportunity(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(allOpportunity);
        assertEquals(1, allOpportunity.getContent().size());
        assertNotNull(allOpportunity.getContent().get(0).getId());
        assertNotNull(allOpportunity.getContent().get(0).getCreatedBy());

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
        Slice<CrmTask> localTask = crmOpportunityService.getTasksAssociatedWithOpportunity(crmOpportunity.getShortName(), crmOpportunity.getNetwork().getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(localTask.getContent());
        assertEquals(1, localTask.getContent().size());
        assertEquals(crmTask1.getId(), localTask.getContent().get(0).getId());
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

    @Test
    public void shouldConvertLeadToOpportunity() {
        CorUser corUser1 = factory.manufacturePojo(CorUser.class);
        CorUser userEntity = corUserRepository.saveAndFlush(corUser1.networks(Sets.newHashSet(corNetwork)).authorities(Sets.newHashSet(new CorAuthority().name(ADMIN))).channels(null));

        CrmLead crmLead = factory.manufacturePojoWithFullData(CrmLead.class);
        CrmLead leadEntity = crmLeadRepository.saveAndFlush(crmLead.network(corNetwork).keeper(userEntity));
        CrmOpportunity crmOpportunity = crmOpportunityService.convertLeadToOpportunity(leadEntity);

        assertNotNull(crmOpportunity);
        assertEquals(crmOpportunity.getLead().getId(), leadEntity.getId());
        assertEquals(leadEntity.getId(), crmOpportunity.getLead().getId());
        assertEquals(CONVERTED + leadEntity.getShortname(), crmOpportunity.getShortName());
        assertEquals(crmOpportunity.getKeeper().getId(), leadEntity.getKeeper().getId());
        assertEquals(leadEntity.getNetwork().getId(), crmOpportunity.getNetwork().getId());
    }

    @Test
    public void shouldConvertContactToOpportunity() {
        CorUser corUser1 = factory.manufacturePojo(CorUser.class);
        CorUser userEntity = corUserRepository.saveAndFlush(corUser1.networks(Sets.newHashSet(corNetwork)).authorities(Sets.newHashSet(new CorAuthority().name(ADMIN))).channels(null));
        CrmContact crmContact1 = factory.manufacturePojoWithFullData(CrmContact.class);
        CrmContact contactEntity = crmContactRepository.saveAndFlush(crmContact1.network(corNetwork).keeper(userEntity));

        CrmOpportunity crmOpportunity = crmOpportunityService.convertContactToOpportunity(contactEntity);

        assertNotNull(crmOpportunity);
        assertEquals(crmOpportunity.getContact().getId(), contactEntity.getId());
        assertEquals(contactEntity.getId(), crmOpportunity.getContact().getId());
        assertEquals(CONVERTED + contactEntity.getShortName(), crmOpportunity.getShortName());
        assertEquals(crmOpportunity.getKeeper().getId(), contactEntity.getKeeper().getId());
        assertEquals(contactEntity.getNetwork().getId(), crmOpportunity.getNetwork().getId());
    }

    @Test
    public void shouldConvertAccountToOpportunity() {
        CorUser corUser1 = factory.manufacturePojo(CorUser.class);
        CorUser userEntity = corUserRepository.saveAndFlush(corUser1.networks(Sets.newHashSet(corNetwork)).authorities(Sets.newHashSet(new CorAuthority().name(ADMIN))).channels(null));
        CrmAccount crmAccount1 = factory.manufacturePojoWithFullData(CrmAccount.class);
        CrmAccount accountEntity = crmAccountRepository.saveAndFlush(crmAccount1.keeper(userEntity).network(corNetwork));

        CrmOpportunity crmOpportunity = crmOpportunityService.convertAccountToOpportunity(accountEntity);


        assertNotNull(crmOpportunity);
        assertEquals(crmOpportunity.getAccount().getId(), accountEntity.getId());
        assertEquals(accountEntity.getId(), crmOpportunity.getAccount().getId());
        assertEquals(CONVERTED + accountEntity.getShortName(), crmOpportunity.getShortName());
        assertEquals(crmOpportunity.getKeeper().getId(), accountEntity.getKeeper().getId());
        assertEquals(accountEntity
                .getNetwork().getId(), crmOpportunity.getNetwork().getId());
    }
}
