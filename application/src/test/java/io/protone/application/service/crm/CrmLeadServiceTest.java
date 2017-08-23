package io.protone.application.service.crm;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorAddress;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorPerson;
import io.protone.core.repository.CorNetworkRepository;
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
import java.util.List;

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
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CrmLeadRepository crmLeadRepository;

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
    public void shouldGetAllLeads() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setNetwork(corNetwork);
        crmLeadRepository.saveAndFlush(crmLead);

        //then
        Slice<CrmLead> allCustomers = crmLeadService.getAllLeads(corNetwork.getShortcut(), new PageRequest(0, 10));

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
        crmLead.setNetwork(corNetwork);
        crmLead.setPerson(factory.manufacturePojo(CorPerson.class));
        crmLead.getPerson().setNetwork(corNetwork);
        crmLead.getPerson().getContacts().stream().forEach(corContact -> corContact.setNetwork(corNetwork));
        crmLead.setAddres(factory.manufacturePojo(CorAddress.class));
        crmLead.getAddres().setNetwork(corNetwork);

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
        crmLead.setNetwork(corNetwork);
        crmLead.setPerson(factory.manufacturePojo(CorPerson.class));
        crmLead.getPerson().setNetwork(corNetwork);
        crmLead.getPerson().getContacts().stream().forEach(corContact -> corContact.setNetwork(corNetwork));

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
        crmLead.setNetwork(corNetwork);

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
        crmLead.setNetwork(corNetwork);
        crmLead.setPerson(factory.manufacturePojo(CorPerson.class));
        crmLead.getPerson().setNetwork(corNetwork);
        crmLead.getPerson().setContacts(null);
        crmLead.setAddres(factory.manufacturePojo(CorAddress.class));
        crmLead.getAddres().setNetwork(corNetwork);

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
        crmLead.setNetwork(corNetwork);
        crmLead = crmLeadRepository.saveAndFlush(crmLead);

        //then
        crmLeadService.getLead(crmLead.getShortname(), crmLead.getNetwork().getShortcut());
        CrmLead localCrmLead = crmLeadRepository.findOne(crmLead.getId());

        //assert
        assertNotNull(localCrmLead);
    }

    @Test
    public void shouldSaveLeadTask() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setNetwork(corNetwork);
        crmLead = crmLeadRepository.saveAndFlush(crmLead);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        //then
        CrmTask crmTask1 = crmLeadService.saveOrUpdateTaskAssociatiedWithLead(crmTask, crmLead.getShortname(), crmLead.getNetwork().getShortcut());
        CrmLead lead = crmLeadService.getLead(crmLead.getShortname(), crmLead.getNetwork().getShortcut());

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
        crmTask.setNetwork(corNetwork);

        //then
        CrmTask crmTask1 = crmLeadService.saveOrUpdateTaskAssociatiedWithLead(crmTask, "xxx", corNetwork.getShortcut());

        //assert
        assertNull(crmTask1);
    }

    @Test
    public void shouldGetTasksAssociatedWithLead() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setNetwork(corNetwork);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        crmLead = crmLeadRepository.saveAndFlush(crmLead);
        CrmTask crmTask1 = crmLeadService.saveOrUpdateTaskAssociatiedWithLead(crmTask, crmLead.getShortname(), crmLead.getNetwork().getShortcut());

        //then
        Slice<CrmTask> localTask = crmLeadService.getTasksAssociatedWithLead(crmLead.getShortname(), crmLead.getNetwork().getShortcut(), new PageRequest(0, 10));

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
        crmLead.setNetwork(corNetwork);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        crmLead = crmLeadRepository.saveAndFlush(crmLead);
        CrmTask crmTask1 = crmLeadService.saveOrUpdateTaskAssociatiedWithLead(crmTask, crmLead.getShortname(), crmLead.getNetwork().getShortcut());

        //then
        CrmTask localTask = crmLeadService.getTaskAssociatedWithLead(crmTask1.getId(), crmLead.getNetwork().getShortcut());

        //assert
        assertNotNull(localTask);
        assertEquals(crmTask1.getId(), localTask.getId());

    }

    @Test
    public void shouldDeleteLeadTask() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setNetwork(corNetwork);

        CrmTask crmTask = factory.manufacturePojo(CrmTask.class);
        crmTask.setId(null);
        crmTask.setNetwork(corNetwork);

        crmLead = crmLeadRepository.saveAndFlush(crmLead);
        CrmTask crmTask1 = crmLeadService.saveOrUpdateTaskAssociatiedWithLead(crmTask, crmLead.getShortname(), crmLead.getNetwork().getShortcut());

        // then
        crmLeadService.deleteLeadTask(crmLead.getShortname(), crmTask1.getId(), crmLead.getNetwork().getShortcut());

        CrmTask localTask = crmLeadService.getTaskAssociatedWithLead(crmTask1.getId(), crmLead.getNetwork().getShortcut());
        CrmLead localContact = crmLeadService.getLead(crmLead.getShortname(), crmLead.getNetwork().getShortcut());

        //assert
        assertNull(localTask);
        assertEquals(true, localContact.getTasks().isEmpty());


    }

    @Test
    public void shouldDeleteLead() throws Exception {
        //when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setNetwork(corNetwork);
        crmLead = crmLeadRepository.saveAndFlush(crmLead);

        //then
        crmLeadService.deleteLead(crmLead.getShortname(), crmLead.getNetwork().getShortcut());
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
        crmLead.setNetwork(corNetwork);
        CrmLead crmLead1 = factory.manufacturePojo(CrmLead.class);
        crmLead1.setId(null);
        crmLead1.setShortname(TEST_SHORTNAME);
        crmLead1.setNetwork(corNetwork);

        crmLead = crmLeadService.saveLead(crmLead);
        crmLead1 = crmLeadService.saveLead(crmLead1);


    }

    @Test
    public void shouldSaveTwoOpportunityWithSameShortNameInDifferentNetwork() {
        //given
        CorNetwork corNetworkSecond = factory.manufacturePojo(CorNetwork.class);
        corNetworkSecond.setId(null);
        corNetworkSecond = corNetworkRepository.save(corNetworkSecond);

        ///when
        CrmLead crmLead = factory.manufacturePojo(CrmLead.class);
        crmLead.setId(null);
        crmLead.setShortname(TEST_SHORTNAME);
        crmLead.setNetwork(corNetwork);
        CrmLead crmLead1 = factory.manufacturePojo(CrmLead.class);
        crmLead1.setId(null);
        crmLead1.setShortname(TEST_SHORTNAME);
        crmLead1.setNetwork(corNetworkSecond);

        //then
        crmLead = crmLeadService.saveLead(crmLead);
        crmLead1 = crmLeadService.saveLead(crmLead1);

    }

}
