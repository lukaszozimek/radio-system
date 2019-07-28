package io.protone.application.web.rest.mapper;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.crm.domain.CrmAccount;
import io.protone.traffic.api.dto.TraInvoiceDTO;
import io.protone.traffic.domain.TraInvoice;
import io.protone.traffic.domain.TraOrder;
import io.protone.traffic.mapper.TraInvoiceMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 28.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraInvoiceMapperTest {
    @Autowired
    private TraInvoiceMapper customTraInvoiceMapper;

    private TraInvoice traInvoice;

    private TraInvoiceDTO traInvoiceDTO;

    private List<TraInvoiceDTO> traInvoiceDTOS = new ArrayList<>();

    private List<TraInvoice> traInvoices = new ArrayList<>();
    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoice.setCustomer(factory.manufacturePojo(CrmAccount.class));
        traInvoice.setOrders(Sets.newHashSet(factory.manufacturePojo(TraOrder.class)));
        traInvoice.setStatus(factory.manufacturePojo(CorDictionary.class));
        traInvoice.setCreatedBy(factory.manufacturePojo(CorUser.class));
        traInvoice.setLastModifiedBy(factory.manufacturePojo(CorUser.class));
        traInvoice.setPrice(new BigDecimal(1));
        traInvoice.setId(1L);
        traInvoices.add(traInvoice);
        traInvoiceDTO = factory.manufacturePojo(TraInvoiceDTO.class);
        traInvoiceDTOS.add(traInvoiceDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        TraInvoiceDTO dto = customTraInvoiceMapper.DB2DTO(traInvoice);

        assertNotNull(dto.getOrders());
        assertNotNull(dto.getCustomerId());
        assertNotNull(dto.getStatusId());
        assertNotNull(dto.getId());
        assertNotNull(dto.getPaid());
        assertNotNull(dto.getPrice());
        assertNotNull(dto.getPaymentDay());
        assertNotNull(dto.getCreatedBy());
        assertNotNull(dto.getCreatedDate());
        assertNotNull(dto.getLastModifiedBy());
        assertNotNull(dto.getLastModifiedDate());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraInvoiceDTO> dtos = customTraInvoiceMapper.DBs2DTOs(traInvoices);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getOrders());
            assertNotNull(dto.getCustomerId());
            assertNotNull(dto.getStatusId());
            assertNotNull(dto.getId());
            assertNotNull(dto.getPaid());
            assertNotNull(dto.getPrice());
            assertNotNull(dto.getPaymentDay());


        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraInvoice> entities = customTraInvoiceMapper.DTOs2DBs(traInvoiceDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getCustomer());
            assertNotNull(entity.getOrders());
            assertNotNull(entity.getStatus());
            assertNotNull(entity.getId());
            assertNotNull(entity.isPaid());
            assertNotNull(entity.getPrice());
            assertNotNull(entity.getPaymentDay());

            assertNotNull(entity.getNetwork());

        });
    }

    @Test
    public void DTO2DB() throws Exception {
        TraInvoice entity = customTraInvoiceMapper.DTO2DB(traInvoiceDTO, corNetwork);


        assertNotNull(entity.getCustomer());
        assertNotNull(entity.getOrders());
        assertNotNull(entity.getStatus());
        assertNotNull(entity.getId());
        assertNotNull(entity.isPaid());
        assertNotNull(entity.getPrice());
        assertNotNull(entity.getPaymentDay());

        assertNotNull(entity.getNetwork());
    }
}
