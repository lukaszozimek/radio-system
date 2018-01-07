package io.protone.application.service.crm;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorAddress;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.domain.CorPerson;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorOrganizationRepository;
import io.protone.crm.domain.CrmLead;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.repostiory.CrmLeadRepository;
import io.protone.crm.service.CrmLeadService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static io.protone.application.util.TestConstans.*;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 29.04.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CrmLeadServiceTest {
    private static final String TEST_SHORTNAME = "TEST";
    @Autowired
    private CrmLeadService crmLeadService;

    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private CrmLeadRepository crmLeadRepository;

    private CorOrganization corOrganization;

    private CorChannel corChannel;

    private PodamFactory factory;
    @Autowired
    private CorOrganizationRepository corOrganizationRepository;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

    }

    @Test
    public void shouldGetAllLeads() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setChannel(corChannel);
        crmLeadRepository.saveAndFlush(crmLead);

        //then
        Slice<CrmLead> allCustomers = crmLeadService.getAllLeads(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(allCustomers.getContent());
        assertEquals(1, allCustomers.getContent().size());
        assertNotNull(allCustomers.getContent().get(0).getId());
    }

    @Test
    public void shouldSaveCrmLead() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setChannel(corChannel);
        crmLead.setPerson(factory.manufacturePojo(CorPerson.class));
        crmLead.getPerson().setChannel(corChannel);
        crmLead.getPerson().getContacts().stream().forEach(corContact -> corContact.setChannel(corChannel));
        crmLead.setAddres(factory.manufacturePojo(CorAddress.class));
        crmLead.getAddres().setChannel(corChannel);

        //then
        crmLead = crmLeadService.saveLead(crmLead);

        //assert
        assertNotNull(crmLead);
        assertNotNull(crmLead.getId());
        assertNotNull(crmLead.getPerson().getId());
        assertNotNull(crmLead.getPerson().getCreatedBy());
        assertNotNull(crmLead.getPerson().getCreatedBy().getId());
        assertNotNull(crmLead.getAddres().getId());
        assertNotNull(crmLead.getAddres().getCreatedBy());
        assertNotNull(crmLead.getAddres().getCreatedBy().getId());
        assertNotNull(crmLead.getCreatedBy());

        crmLead.getPerson().getContacts().stream().forEach(corContact -> {
            assertNotNull(corContact.getId());
        });


    }

    @Test
    public void shouldSaveCrmLeadWithOutAdress() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setChannel(corChannel);
        crmLead.setPerson(factory.manufacturePojo(CorPerson.class));
        crmLead.getPerson().setChannel(corChannel);
        crmLead.getPerson().getContacts().stream().forEach(corContact -> corContact.setChannel(corChannel));

        //then
        crmLead = crmLeadService.saveLead(crmLead);

        //assert
        assertNotNull(crmLead);
        assertNotNull(crmLead.getId());
        assertNotNull(crmLead.getCreatedBy());
        assertNotNull(crmLead.getPerson().getId());
        assertNull(crmLead.getAddres());
        crmLead.getPerson().getContacts().stream().forEach(corContact -> {
            assertNotNull(corContact.getId());
            assertNotNull(corContact.getCreatedBy());
        });


    }

    @Test
    public void shouldSaveCrmLeadWithOutPerson() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setChannel(corChannel);

        //then
        crmLead = crmLeadService.saveLead(crmLead);

        //assert
        assertNotNull(crmLead);
        assertNotNull(crmLead.getId());
        assertNotNull(crmLead.getCreatedBy());
        assertNull(crmLead.getPerson());

    }

    @Test
    public void shouldSaveCrmLeadWithOutPersonContact() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setChannel(corChannel);
        crmLead.setPerson(factory.manufacturePojo(CorPerson.class));
        crmLead.getPerson().setChannel(corChannel);
        crmLead.getPerson().setContacts(null);
        crmLead.setAddres(factory.manufacturePojo(CorAddress.class));
        crmLead.getAddres().setChannel(corChannel);

        //then
        crmLead = crmLeadService.saveLead(crmLead);

        //assert
        assertNotNull(crmLead);
        assertNotNull(crmLead.getId());
        assertNotNull(crmLead.getPerson().getId());
        assertNull(crmLead.getPerson().getContacts());

    }

    @Test
    public void shouldGetLead() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setChannel(corChannel);
        crmLead = crmLeadRepository.saveAndFlush(crmLead);

        //then
        crmLeadService.getLead(crmLead.getShortname(), corOrganization.getShortcut(), corChannel.getShortcut());
        CrmLead localCrmLead = crmLeadRepository.findOne(crmLead.getId());

        //assert
        assertNotNull(localCrmLead);
    }

    @Test
    public void shouldSaveLeadTask() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setChannel(corChannel);
        crmLead = crmLeadRepository.saveAndFlush(crmLead);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setChannel(corChannel);

        //then
        CrmTask crmTask1 = crmLeadService.saveOrUpdateTaskAssociatiedWithLead(crmTask, crmLead.getShortname(), corOrganization.getShortcut(), corChannel.getShortcut());
        CrmLead lead = crmLeadService.getLead(crmLead.getShortname(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(crmTask1);
        assertNotNull(crmTask1.getId());
        assertNotNull(crmTask1.getLead());
        assertEquals(crmTask1.getLead().getId(), crmLead.getId());

        assertNotNull(lead.getTasks());
        assertEquals(1, lead.getTasks().size());
        assertEquals(crmTask1.getId(), lead.getTasks().stream().findFirst().get().getId());
        assertNotNull(lead.getTasks().stream().findFirst().get().getCreatedBy());
    }

    @Test
    public void shouldNotSaveLeadTaskIfContactDoesntExist() throws Exception {
        //when
        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setChannel(corChannel);

        //then
        CrmTask crmTask1 = crmLeadService.saveOrUpdateTaskAssociatiedWithLead(crmTask, "xxx", corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNull(crmTask1);
    }

    @Test
    public void shouldGetTasksAssociatedWithLead() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setChannel(corChannel);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setChannel(corChannel);

        crmLead = crmLeadRepository.saveAndFlush(crmLead);
        CrmTask crmTask1 = crmLeadService.saveOrUpdateTaskAssociatiedWithLead(crmTask, crmLead.getShortname(), corOrganization.getShortcut(), corChannel.getShortcut());

        //then
        Slice<CrmTask> localTask = crmLeadService.getTasksAssociatedWithLead(crmLead.getShortname(), corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(localTask.getContent());
        assertEquals(1, localTask.getContent().size());
        assertEquals(crmTask1.getId(), localTask.getContent().get(0).getId());
    }

    @Test
    public void shouldGetTaskAssociatedWithLead() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setChannel(corChannel);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setChannel(corChannel);

        crmLead = crmLeadRepository.saveAndFlush(crmLead);
        CrmTask crmTask1 = crmLeadService.saveOrUpdateTaskAssociatiedWithLead(crmTask, crmLead.getShortname(), corOrganization.getShortcut(), corChannel.getShortcut());

        //then
        CrmTask localTask = crmLeadService.getTaskAssociatedWithLead(crmTask1.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(localTask);
        assertEquals(crmTask1.getId(), localTask.getId());

    }

    @Test
    public void shouldDeleteLeadTask() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setChannel(corChannel);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setChannel(corChannel);

        crmLead = crmLeadRepository.saveAndFlush(crmLead);
        CrmTask crmTask1 = crmLeadService.saveOrUpdateTaskAssociatiedWithLead(crmTask, crmLead.getShortname(), corOrganization.getShortcut(), corChannel.getShortcut());

        // then
        crmLeadService.deleteLeadTask(crmLead.getShortname(), crmTask1.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        CrmTask localTask = crmLeadService.getTaskAssociatedWithLead(crmTask1.getId(), corOrganization.getShortcut(), corChannel.getShortcut());
        CrmLead localContact = crmLeadService.getLead(crmLead.getShortname(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNull(localTask);
        assertEquals(true, localContact.getTasks().isEmpty());


    }

    @Test
    public void shouldDeleteLead() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setChannel(corChannel);
        crmLead = crmLeadRepository.saveAndFlush(crmLead);

        //then
        crmLeadService.deleteLead(crmLead.getShortname(), corOrganization.getShortcut(), corChannel.getShortcut());
        CrmLead localContact = crmLeadRepository.findOne(crmLead.getId());

        //assert
        assertNull(localContact);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoOpportunityWithSameShortNameInOneNetwork() {

        /// /when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setShortname(TEST_SHORTNAME);
        crmLead.setChannel(corChannel);
        CrmLead crmLead1 = factory.manufacturePojo(CrmLead.class);
        crmLead1.setId(null);
        crmLead1.setShortname(TEST_SHORTNAME);
        crmLead1.setChannel(corChannel);

        crmLead = crmLeadService.saveLead(crmLead);
        crmLead1 = crmLeadService.saveLead(crmLead1);


    }

    @Test
    public void shouldSaveTwoOpportunityWithSameShortNameInDifferentNetwork() {
        //given
        CorOrganization corOrganizationkSecond = factory.manufacturePojo(CorOrganization.class);
        corOrganizationkSecond.setId(null);
        corOrganizationkSecond = corOrganizationRepository.saveAndFlush(corOrganizationkSecond);
        CorChannel corChannelSecond = factory.manufacturePojo(CorChannel.class);
        corChannelSecond.setId(null);
        corChannelSecond.setOrganization(corOrganizationkSecond);
        corChannelSecond = corChannelRepository.save(corChannelSecond);

        ///when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setShortname(TEST_SHORTNAME);
        crmLead.setChannel(corChannel);
        CrmLead crmLead1 = factory.manufacturePojo(CrmLead.class);
        crmLead1.setId(null);
        crmLead1.setShortname(TEST_SHORTNAME);
        crmLead1.setChannel(corChannelSecond);

        //then
        crmLead = crmLeadService.saveLead(crmLead);
        crmLead1 = crmLeadService.saveLead(crmLead1);

    }

}
