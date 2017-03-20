package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.domain.*;
import org.hibernate.annotations.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProtoneApp.class)
public class CustomTraInvoiceMapperTest {
    @Autowired
    private CustomTraInvoiceMapper customTraInvoiceMapper;

    private CorNetwork mockCorNetwork = null;

    private TraInvoice mockTraInvoice = null;

    private TraInvoicePT mockTraInvoicePt = null;

  //  @Before
    public void initialize() {
        mockCorNetwork = new CorNetwork();
        mockTraInvoice = new TraInvoice();
        mockTraInvoice.setId((long) 2);
        mockTraInvoice.setPaid(true);
        mockTraInvoice.price(new BigDecimal(22222));
        mockTraInvoice.paymentDay(LocalDate.now());
        mockTraInvoice.customer(new CrmAccount());
        mockTraInvoice.network(mockCorNetwork);
        mockTraInvoice.setStatus(new TraInvoiceStatus());
        mockTraInvoice.setOrders(new HashSet<>());

        mockTraInvoicePt = new TraInvoicePT();
        mockTraInvoicePt.setId((long) 2);
        mockTraInvoicePt.setPaid(true);
        mockTraInvoicePt.price(new BigDecimal(222222222));
        mockTraInvoicePt.paymentDay(LocalDate.now());
        mockTraInvoicePt.customerId(new TraCustomerPT());
        mockTraInvoicePt.setTraStatus(new TraInvoiceStatus());
        mockTraInvoicePt.setOrder(new ArrayList<>());


    }

 //   @Test
    public void createEntityFromDTO() throws Exception {
        //then
        TraInvoice result = customTraInvoiceMapper.createEntityFromDTO(mockTraInvoicePt, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

 //   @Test
    public void createDTOFromEnity() throws Exception {
        //then
        TraInvoicePT result = customTraInvoiceMapper.createDTOFromEnity(mockTraInvoice);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
