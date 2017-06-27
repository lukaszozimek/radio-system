package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.library.thin.LibMediaItemThinDTO;
import io.protone.web.rest.dto.traffic.TraMediaPlanDTO;
import io.protone.web.rest.dto.traffic.thin.TraCustomerThinDTO;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    private CrmAccount crmAccount;
    private TraCustomerThinDTO traCustomerThinDTO;

    private LibMediaItemThinDTO libMediaItemThinDTO;

    private LibMediaItem libMediaItem;

    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmAccount = factory.manufacturePojo(CrmAccount.class);
        traCustomerThinDTO = factory.manufacturePojo(TraCustomerThinDTO.class);
        libMediaItemThinDTO = factory.manufacturePojo(LibMediaItemThinDTO.class);
        libMediaItem = factory.manufacturePojo(LibMediaItem.class);

        traMediaPlan = factory.manufacturePojo(TraMediaPlan.class);
        traMediaPlan.setAccount(crmAccount);
        traMediaPlan.setMediaItem(libMediaItem);
        traMediaPlans.add(traMediaPlan);
        traMediaPlanDTO = factory.manufacturePojo(TraMediaPlanDTO.class);
        traMediaPlanDTO.setTraCustomerThinDTO(traCustomerThinDTO);
        traMediaPlanDTO.setMediaItemId(libMediaItemThinDTO);
        traMediaPlanDTOS.add(traMediaPlanDTO);
        traMediaPlanThinDTO = factory.manufacturePojo(TraMediaPlanThinDTO.class);
        traMediaPlanThinDTO.setTraCustomerThinDTO(traCustomerThinDTO);
        traMediaPlanThinDTO.setMediaItemId(libMediaItemThinDTO);
        traMediaPlanThinDTOS.add(traMediaPlanThinDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corChannel = factory.manufacturePojo(CorChannel.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        TraMediaPlanDTO dto = traMediaPlanMapper.DB2DTO(traMediaPlan);

        assertNotNull(dto.getTraCustomerThinDTO());
        assertNotNull(dto.getMediaPlanPlaylistDTOS());
        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getMediaItemId());


    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraMediaPlanDTO> dtos = traMediaPlanMapper.DBs2DTOs(traMediaPlans);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getTraCustomerThinDTO());
            assertNotNull(dto.getMediaPlanPlaylistDTOS());
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getMediaItemId());
        });
    }


    @Test
    public void DTO2DB() throws Exception {
        TraMediaPlan entity = traMediaPlanMapper.DTO2DB(traMediaPlanDTO, corNetwork, corChannel);
        assertNotNull(entity.getId());
        assertNotNull(entity.getPlaylists());
        assertNotEquals(0, entity.getPlaylists().size());
        assertNotNull(entity.getAccount());
        assertNotNull(entity.getName());
        assertNotNull(entity.getMediaItem());
        assertNotNull(entity.getChannel());
        assertNotNull(entity.getNetwork());
    }

    @Test
    public void TraMediaPlan() throws Exception {
        List<TraMediaPlan> entities = traMediaPlanMapper.DTOs2DBs(traMediaPlanDTOS, corNetwork, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getPlaylists());
            assertNotEquals(0, entity.getPlaylists().size());
            assertNotNull(entity.getAccount());
            assertNotNull(entity.getName());
            assertNotNull(entity.getMediaItem());
            assertNotNull(entity.getChannel());
            assertNotNull(entity.getNetwork());
        });
    }

    @Test
    public void DBsThin2DTOsThin() throws Exception {
        List<TraMediaPlanThinDTO> traMediaPlanThinDTOS = traMediaPlanMapper.DBsThin2DTOsThin(traMediaPlans);
        traMediaPlanThinDTOS.stream().forEach(traMediaPlanThinDTO -> {
            assertNotNull(traMediaPlanThinDTO.getTraCustomerThinDTO());
            assertNotNull(traMediaPlanThinDTO.getId());
            assertNotNull(traMediaPlanThinDTO.getName());
            assertNotNull(traMediaPlanThinDTO.getMediaItemId());
        });

    }


    @Test
    public void DBThin2DTOThin() throws Exception {
        TraMediaPlanThinDTO traMediaPlanThinDTO = traMediaPlanMapper.DBThin2DTOThin(traMediaPlan);

        assertNotNull(traMediaPlanThinDTO.getTraCustomerThinDTO());
        assertNotNull(traMediaPlanThinDTO.getId());
        assertNotNull(traMediaPlanThinDTO.getName());
        assertNotNull(traMediaPlanThinDTO.getMediaItemId());
    }


}
