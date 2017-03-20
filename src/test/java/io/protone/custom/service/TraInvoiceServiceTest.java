package io.protone.custom.service;

import io.protone.ProtoneApp;
import io.protone.domain.TraInvoice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraInvoiceServiceTest {
    @Inject
    private TraInvoiceService traInvoiceService;

    @Test
    public void getAllInvoice() throws Exception {

    }

    @Test
    public void saveInvoice() throws Exception {

    }

    @Test
    public void deleteInvoice() throws Exception {

    }

    @Test
    public void getInvoice() throws Exception {

    }

    @Test
    public void getCustomerInvoice() throws Exception {

    }

}
