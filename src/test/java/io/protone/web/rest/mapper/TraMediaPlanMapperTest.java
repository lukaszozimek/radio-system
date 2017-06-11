package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraMediaPlan;
import io.protone.web.rest.dto.traffic.TraMediaPlanDTO;
import io.protone.web.rest.dto.traffic.thin.TraMediaPlanThinDTO;
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

/**
 * Created by lukaszozimek on 11/06/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraMediaPlanMapperTest {
    @Autowired
    private TraMediaPlanMapper traMediaPlanMapper;

    private TraMediaPlan traMediaPlan;

    private TraMediaPlanDTO traMediaPlanDTO;

    private TraMediaPlanThinDTO traMediaPlanThinDTO;

    private List<TraMediaPlanDTO> traMediaPlanDTOS = new ArrayList<>();


    private List<TraMediaPlanThinDTO> traMediaPlanThinDTOS = new ArrayList<>();

    private List<TraMediaPlan> traMediaPlans = new ArrayList<>();

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traMediaPlan = factory.manufacturePojo(TraMediaPlan.class);
        traMediaPlans.add(traMediaPlan);
        traMediaPlanDTO = factory.manufacturePojo(TraMediaPlanDTO.class);
        traMediaPlanDTOS.add(traMediaPlanDTO);
        traMediaPlanThinDTO = factory.manufacturePojo(TraMediaPlanThinDTO.class);
        traMediaPlanThinDTOS.add(traMediaPlanThinDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corChannel = factory.manufacturePojo(CorChannel.class);
    }

    @Test
    public void DB2DTO() throws Exception {
    }

    @Test
    public void DBThin2DTOThin() throws Exception {
    }

    @Test
    public void DBs2DTOs() throws Exception {
    }

    @Test
    public void DBsThin2DTOsThin() throws Exception {
    }

    @Test
    public void DTO2DB() throws Exception {
    }

    @Test
    public void DTOs2DBs() throws Exception {
    }


}
