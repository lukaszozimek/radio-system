package io.protone.custom.service;

import io.protone.ProtoneApp;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmContact;
import io.protone.repository.CorNetworkRepository;
import io.protone.repository.CrmContactRepository;
import io.protone.repository.custom.CustomCorChannelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.DefaultClassInfoStrategy;
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
    private final static DefaultClassInfoStrategy classInfoStrategy = DefaultClassInfoStrategy.getInstance();
    @Autowired
    private CrmContactService crmContactService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CrmContactRepository crmContactRepository;

    private CrmContact crmContact;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        crmContact = factory.manufacturePojo(CrmContact.class);
        crmContact.setId(null);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
        crmContact.setNetwork(corNetwork);
        crmContact = crmContactRepository.saveAndFlush(crmContact);

    }

    @Test
    public void getAllContact() throws Exception {
        List<CrmContact> crmContactList = crmContactService.getAllContact(corNetwork.getShortcut(), new PageRequest(0, 10));

        assertNotNull(crmContactList);
        assertEquals(1, crmContactList.size());
        assertNotNull(crmContactList.get(0).getId());
    }

    @Test
    public void saveContact() throws Exception {
        CrmContact localCrmContact = factory.manufacturePojo(CrmContact.class);

    }

    @Test
    public void deleteContact() throws Exception {
    }

    @Test
    public void getContact() throws Exception {
    }

    @Test
    public void getTasksAssociatedWithContact() throws Exception {
    }

    @Test
    public void getTaskAssociatedWithContact() throws Exception {
    }

    @Test
    public void deleteContactTask() throws Exception {
    }

    @Test
    public void updateContactTask() throws Exception {
    }

}
