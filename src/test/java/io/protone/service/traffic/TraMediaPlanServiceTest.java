package io.protone.service.traffic;

import io.protone.ProtoneApp;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.repository.cor.CorChannelRepository;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.repository.traffic.TraAdvertisementRepository;
import io.protone.repository.traffic.TraMediaPlanRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;

import javax.transaction.Transactional;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraMediaPlanServiceTest {
    @Autowired
    private TraMediaPlanService traMediaPlanService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;


    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private TraMediaPlanRepository traPlaylistRepository;

    @Autowired
    private TraAdvertisementRepository traAdvertisementRepository;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private PodamFactory factory;

    @Test
    public void saveMediaPlan() throws Exception {
    }

    @Test
    public void updateMediaPlan() throws Exception {
    }

    @Test
    public void getMediaPlans() throws Exception {
    }

    @Test
    public void deleteMediaPlan() throws Exception {
    }

    @Test
    public void getMediaPlan() throws Exception {
    }

    @Test
    public void getCustomerMediaPlan() throws Exception {
    }

}
