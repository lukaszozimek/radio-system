package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.service.traffic.mediaplan.descriptor.TraMediaPlanDescriptor;
import io.protone.web.rest.dto.traffic.TraMediaPlanDescriptorDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Created by lukaszozimek on 11/06/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraMediaPlanDescriptorMapperTest {


    @Autowired
    private TraMediaPlanDescriptorMapper traMediaPlanDescriptorMapper;

    private TraMediaPlanDescriptor traMediaPlanDescriptor;

    private TraMediaPlanDescriptorDTO traMediaPlanDescriptorDTO;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traMediaPlanDescriptor = factory.manufacturePojo(TraMediaPlanDescriptor.class);
        traMediaPlanDescriptorDTO = factory.manufacturePojo(TraMediaPlanDescriptorDTO.class);
    }

    @Test
    public void DTO2DB() throws Exception {
    }
}
