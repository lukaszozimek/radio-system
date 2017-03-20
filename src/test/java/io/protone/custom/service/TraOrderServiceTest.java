package io.protone.custom.service;

import io.protone.ProtoneApp;
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
public class TraOrderServiceTest {

    @Inject
    private TraOrderService orderService;

    @Test
    public void getAllOrder() throws Exception {

    }

    @Test
    public void saveOrder() throws Exception {

    }

    @Test
    public void deleteOrder() throws Exception {

    }

    @Test
    public void getOrder() throws Exception {

    }

    @Test
    public void getOrdersByEntitie() throws Exception {

    }

    @Test
    public void getOrdersByEntitie1() throws Exception {

    }

    @Test
    public void getCustomerOrders() throws Exception {

    }

}
