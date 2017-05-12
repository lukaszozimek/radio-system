package io.protone.service.traffic;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraInvoice;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.traffic.TraInvoiceRepository;
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
 * Created by lukaszozimek on 30/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraInvoiceServiceTest {
    @Autowired
    private TraInvoiceService traInvoiceService;

    @Autowired
    private TraInvoiceRepository traInvoiceRepository;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

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
    public void shouldGetAllInvoice() throws Exception {
        //when
        TraInvoice traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoice.setNetwork(corNetwork);
        traInvoice = traInvoiceRepository.save(traInvoice);

        //then
        List<TraInvoice> fetchedEntity = traInvoiceService.getAllInvoice(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(traInvoice.getId(), fetchedEntity.get(0).getId());
        assertEquals(traInvoice.getPaymentDay(), fetchedEntity.get(0).getPaymentDay());
        assertEquals(traInvoice.getNetwork(), fetchedEntity.get(0).getNetwork());

    }

    @Test
    public void shouldSaveInvoice() throws Exception {
        //when
        TraInvoice traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoice.setNetwork(corNetwork);

        //then
        TraInvoice fetchedEntity = traInvoiceService.saveInvoice(traInvoice);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertEquals(traInvoice.getPaymentDay(), fetchedEntity.getPaymentDay());
        assertEquals(traInvoice.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteInvoice() throws Exception {
        //when
        TraInvoice traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoice.setNetwork(corNetwork);
        traInvoice = traInvoiceRepository.save(traInvoice);
        //then
        traInvoiceService.deleteInvoice(traInvoice.getId(), corNetwork.getShortcut());
        TraInvoice fetchedEntity = traInvoiceService.getInvoice(traInvoice.getId(), corNetwork.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetInvoice() throws Exception {
        //when
        TraInvoice traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoice.setNetwork(corNetwork);
        traInvoice = traInvoiceRepository.save(traInvoice);

        //then
        TraInvoice fetchedEntity = traInvoiceService.getInvoice(traInvoice.getId(), corNetwork.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(traInvoice.getId(), fetchedEntity.getId());
        assertEquals(traInvoice.getPaymentDay(), fetchedEntity.getPaymentDay());
        assertEquals(traInvoice.getNetwork(), fetchedEntity.getNetwork());

    }

    @Test
    public void shouldGetInvoiceAssociatedWithCustomer() throws Exception {

        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setNetwork(corNetwork);
        crmAccount = crmAccountRepository.save(crmAccount);
        TraInvoice traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoice.setNetwork(corNetwork);
        traInvoice.setCustomer(crmAccount);
        traInvoice = traInvoiceRepository.save(traInvoice);

        //then
        List<TraInvoice> fetchedEntity = traInvoiceService.getCustomerInvoice(crmAccount.getShortName(), corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(traInvoice.getId(), fetchedEntity.get(0).getId());
        assertEquals(traInvoice.getCustomer(), fetchedEntity.get(0).getCustomer());
        assertEquals(traInvoice.getPaymentDay(), fetchedEntity.get(0).getPaymentDay());
        assertEquals(traInvoice.getNetwork(), fetchedEntity.get(0).getNetwork());
    }
}
