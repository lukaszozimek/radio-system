package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.traffic.api.dto.TraMediaPlanDescriptorDTO;
import io.protone.traffic.mapper.TraMediaPlanDescriptorMapper;
import io.protone.traffic.service.mediaplan.descriptor.TraMediaPlanDescriptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.Assert.assertNotNull;

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

        assertNotNull(traMediaPlanDescriptor.getTraMediaPlanTemplate().getBlockStartCell());
        assertNotNull(traMediaPlanDescriptor.getTraMediaPlanTemplate().getBlockEndCell());
        assertNotNull(traMediaPlanDescriptor.getTraMediaPlanTemplate().getBlockHourSeparator());
        assertNotNull(traMediaPlanDescriptor.getTraMediaPlanTemplate().getPlaylistDateStartColumn());
        assertNotNull(traMediaPlanDescriptor.getTraMediaPlanTemplate().getPlaylistDateEndColumn());
        assertNotNull(traMediaPlanDescriptor.getTraMediaPlanTemplate().getPlaylistFirsValueCell());
        assertNotNull(traMediaPlanDescriptor.getTraMediaPlanTemplate().getPlaylistDatePattern());
        assertNotNull(traMediaPlanDescriptor.getTraMediaPlanTemplate().getSheetIndexOfMediaPlan());
        assertNotNull(traMediaPlanDescriptor.getOrder());

    }
}
