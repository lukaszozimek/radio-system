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
public class CorNetworkServiceTest {
    @Inject
    private CorNetworkService corNetworkService;

    @Test
    public void findAllNetworks() throws Exception {

    }

    @Test
    public void findNetwork() throws Exception {

    }

    @Test
    public void deleteNetwork() throws Exception {

    }

    @Test
    public void save() throws Exception {

    }

}
