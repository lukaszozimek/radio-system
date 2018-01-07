package io.protone.application.service.traffic;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
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
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static io.protone.application.util.TestConstans.*;
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
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private TraCompanyRepository traCompanyRepository;

    private CorChannel corChannel;

    private CorOrganization corOrganization;

    private CrmAccount crmAccount;

    private TraCompany traCompany;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        corChannel.setOrganization(corOrganization);

        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setChannel(corChannel);
        crmAccount = crmAccountRepository.save(crmAccount);
        traCompany = factory.manufacturePojo(TraCompany.class);
        traCompany.setChannel(corChannel);
        traCompany = traCompanyRepository.save(traCompany);

    }

    @Test
    public void shouldGetAllInvoice() throws Exception {
        //when
        TraInvoice traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoice.setCustomer(crmAccount);
        traInvoice.setCompany(traCompany);
        traInvoice.setChannel(corChannel);
        traInvoice = traInvoiceRepository.save(traInvoice);

        //then
        Slice<TraInvoice> fetchedEntity = traInvoiceService.getAllInvoice(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(traInvoice.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(traInvoice.getPaymentDay(), fetchedEntity.getContent().get(0).getPaymentDay());

    }

    @Test
    public void shouldSaveInvoice() throws Exception {
        //when
        TraInvoice traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoice.setCustomer(crmAccount);
        traInvoice.setCompany(traCompany);
        traInvoice.setChannel(corChannel);
        //then
        TraInvoice fetchedEntity = traInvoiceService.saveInvoice(traInvoice);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(traInvoice.getPaymentDay(), fetchedEntity.getPaymentDay());
    }

    @Test
    public void shouldDeleteInvoice() throws Exception {
        //when
        TraInvoice traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoice.setCustomer(crmAccount);
        traInvoice.setCompany(traCompany);
        traInvoice.setChannel(corChannel);
        traInvoice = traInvoiceRepository.save(traInvoice);
        //then
        traInvoiceService.deleteInvoice(traInvoice.getId(), corOrganization.getShortcut(), corChannel.getShortcut());
        TraInvoice fetchedEntity = traInvoiceService.getInvoice(traInvoice.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetInvoice() throws Exception {
        //when
        TraInvoice traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoice.setCustomer(crmAccount);
        traInvoice.setCompany(traCompany);
        traInvoice.setChannel(corChannel);
        traInvoice = traInvoiceRepository.save(traInvoice);

        //then
        TraInvoice fetchedEntity = traInvoiceService.getInvoice(traInvoice.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(traInvoice.getId(), fetchedEntity.getId());
        assertEquals(traInvoice.getPaymentDay(), fetchedEntity.getPaymentDay());

    }

    @Test
    public void shouldGetInvoiceAssociatedWithCustomer() throws Exception {

        //when

        TraInvoice traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoice.setCompany(traCompany);
        traInvoice.setCustomer(crmAccount);
        traInvoice.setChannel(corChannel);
        traInvoice = traInvoiceRepository.save(traInvoice);

        //then
        Slice<TraInvoice> fetchedEntity = traInvoiceService.getCustomerInvoice(crmAccount.getShortName(), corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(traInvoice.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(traInvoice.getCustomer(), fetchedEntity.getContent().get(0).getCustomer());
        assertEquals(traInvoice.getPaymentDay(), fetchedEntity.getContent().get(0).getPaymentDay());
    }
}
