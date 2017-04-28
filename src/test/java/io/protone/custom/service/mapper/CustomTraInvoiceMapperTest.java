package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.domain.TraInvoice;
import io.protone.service.mapper.TraInvoiceMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 28.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CustomTraInvoiceMapperTest {
    @Autowired
    private TraInvoiceMapper customTraInvoiceMapper;

    private TraInvoice traInvoice;

    private TraInvoicePT traInvoicePT;

    private List<TraInvoicePT> traInvoicePTS = new ArrayList<>();

    private List<TraInvoice> traInvoices = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoices.add(traInvoice);
        traInvoicePT = factory.manufacturePojo(TraInvoicePT.class);
        traInvoicePTS.add(traInvoicePT);

    }


    @Test
    public void DB2DTO() throws Exception {
        TraInvoicePT dto = customTraInvoiceMapper.DB2DTO(traInvoice);

        assertNotNull(dto.getOrders());
        assertNotNull(dto.getCustomerId());
        assertNotNull(dto.getStatusId());
        assertNotNull(dto.getId());
        assertNotNull(dto.getPaid());
        assertNotNull(dto.getPrice());
        assertNotNull(dto.getPaymentDay());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraInvoicePT> dtos = customTraInvoiceMapper.DBs2DTOs(traInvoices);

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
        List<TraInvoice> entities = customTraInvoiceMapper.DTOs2DBs(traInvoicePTS);

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

            assertNull(entity.getNetwork());

        });
    }

    @Test
    public void DTO2DB() throws Exception {
        TraInvoice entity = customTraInvoiceMapper.DTO2DB(traInvoicePT);


        assertNotNull(entity.getCustomer());
        assertNotNull(entity.getOrders());
        assertNotNull(entity.getStatus());
        assertNotNull(entity.getId());
        assertNotNull(entity.isPaid());
        assertNotNull(entity.getPrice());
        assertNotNull(entity.getPaymentDay());

        assertNull(entity.getNetwork());
    }
}
