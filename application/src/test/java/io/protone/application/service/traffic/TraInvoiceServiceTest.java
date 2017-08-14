package io.protone.application.service.traffic;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.traffic.domain.TraCompany;
import io.protone.traffic.domain.TraInvoice;
import io.protone.traffic.repository.TraCompanyRepository;
import io.protone.traffic.repository.TraInvoiceRepository;
import io.protone.traffic.service.TraInvoiceService;
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

    @Autowired
    private TraCompanyRepository traCompanyRepository;

    private CorNetwork corNetwork;

    private CrmAccount crmAccount;

    private TraCompany traCompany;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);

        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setNetwork(corNetwork);
        crmAccount = crmAccountRepository.save(crmAccount);
        traCompany = factory.manufacturePojo(TraCompany.class);
        traCompany.setNetwork(corNetwork);
        traCompany = traCompanyRepository.save(traCompany);

    }

    @Test
    public void shouldGetAllInvoice() throws Exception {
        //when
        TraInvoice traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoice.setCustomer(crmAccount);
        traInvoice.setNetwork(corNetwork);
        traInvoice.setCompany(traCompany);
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
        traInvoice.setCustomer(crmAccount);
        traInvoice.setCompany(traCompany);
        traInvoice.setNetwork(corNetwork);

        //then
        TraInvoice fetchedEntity = traInvoiceService.saveInvoice(traInvoice);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(traInvoice.getPaymentDay(), fetchedEntity.getPaymentDay());
        assertEquals(traInvoice.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteInvoice() throws Exception {
        //when
        TraInvoice traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoice.setCustomer(crmAccount);
        traInvoice.setCompany(traCompany);
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
        traInvoice.setCustomer(crmAccount);
        traInvoice.setCompany(traCompany);
        traInvoice.setNetwork(corNetwork);
        traInvoice = traInvoiceRepository.save(traInvoice);

        //then
        TraInvoice fetchedEntity = traInvoiceService.getInvoice(traInvoice.getId(), corNetwork.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(traInvoice.getId(), fetchedEntity.getId());
        assertEquals(traInvoice.getPaymentDay(), fetchedEntity.getPaymentDay());
        assertEquals(traInvoice.getNetwork(), fetchedEntity.getNetwork());

    }

    @Test
    public void shouldGetInvoiceAssociatedWithCustomer() throws Exception {

        //when

        TraInvoice traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoice.setNetwork(corNetwork);
        traInvoice.setCompany(traCompany);
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
