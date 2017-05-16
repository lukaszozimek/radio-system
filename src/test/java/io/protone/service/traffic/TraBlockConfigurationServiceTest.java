package io.protone.service.traffic;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.traffic.TraBlockRepository;
import io.protone.service.crm.CrmCustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 16/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraBlockConfigurationServiceTest {

    @Autowired
    private TraBlockConfigurationService traBlockConfigurationService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private TraBlockRepository traBlockRepository;

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
    public void findConfigurationBlock() throws Exception {
    }

    @Test
    public void deleteBlockConfiguration() throws Exception {
    }

    @Test
    public void saveBlockConfiguration() throws Exception {
    }

    @Test
    public void getAllBlockConfigurations() throws Exception {
    }

    @Test
    public void getAllBlockConfigurationsByDay() throws Exception {
    }

}
