package io.protone.service.crm;

import io.protone.ProtoneApp;
import io.protone.domain.*;
import io.protone.repository.CorNetworkRepository;
import io.protone.repository.CrmLeadRepository;
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
public class CrmLeadServiceTest {
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
        List<CrmLead> allCustomers = crmLeadService.getAllLeads(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(allCustomers);
        assertEquals(1, allCustomers.size());
        assertNotNull(allCustomers.get(0).getId());
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
        assertNotNull(crmLead.getAddres().getId());
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
        assertNotNull(crmLead.getPerson().getId());
        assertNull(crmLead.getAddres());
        crmLead.getPerson().getContacts().stream().forEach(corContact -> {
            assertNotNull(corContact.getId());
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
        CrmLead customer = crmLeadService.getLead(crmLead.getShortname(), crmLead.getNetwork().getShortcut());

        //assert
        assertNotNull(crmTask1);
        assertNotNull(crmTask1.getId());
        assertNotNull(crmTask1.getLead());
        assertEquals(crmTask1.getLead().getId(), crmLead.getId());

        assertNotNull(customer.getTasks());
        assertEquals(1, customer.getTasks().size());
        assertEquals(crmTask1.getId(), customer.getTasks().stream().findFirst().get().getId());
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
        List<CrmTask> localTask = crmLeadService.getTasksAssociatedWithLead(crmLead.getShortname(), crmLead.getNetwork().getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(localTask);
        assertEquals(1, localTask.size());
        assertEquals(crmTask1.getId(), localTask.get(0).getId());
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

}
