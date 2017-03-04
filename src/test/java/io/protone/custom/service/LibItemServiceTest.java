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
public class LibItemServiceTest {
    @Inject
    private LibItemService libItemService;

    @Test
    public void getItem() throws Exception {

    }

    @Test
    public void getItem1() throws Exception {

    }

    @Test
    public void deleteItem() throws Exception {

    }

    @Test
    public void upload() throws Exception {

    }

    @Test
    public void download() throws Exception {

    }

    @Test
    public void getItemFromDB() throws Exception {

    }

}
