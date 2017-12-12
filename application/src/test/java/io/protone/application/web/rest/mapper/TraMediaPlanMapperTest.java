package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmAccount;
import io.protone.library.api.dto.LibFileItemDTO;
import io.protone.library.domain.LibFileItem;
import io.protone.traffic.api.dto.TraMediaPlanDTO;
import io.protone.traffic.api.dto.thin.TraCustomerThinDTO;
import io.protone.traffic.api.dto.thin.TraMediaPlanThinDTO;
import io.protone.traffic.domain.TraMediaPlan;
import io.protone.traffic.mapper.TraMediaPlanMapper;
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

    private LibFileItemDTO libMediaItemThinDTO;

    private LibFileItem libMediaItem;

    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmAccount = factory.manufacturePojo(CrmAccount.class);
        traCustomerThinDTO = factory.manufacturePojo(TraCustomerThinDTO.class);
        libMediaItemThinDTO = factory.manufacturePojo(LibFileItemDTO.class);
        libMediaItem = factory.manufacturePojo(LibFileItem.class);

        traMediaPlan = factory.manufacturePojo(TraMediaPlan.class);
        traMediaPlan.setAccount(crmAccount);
        traMediaPlan.setLibFileItem(libMediaItem);
        traMediaPlans.add(traMediaPlan);
        traMediaPlanDTO = factory.manufacturePojo(TraMediaPlanDTO.class);
        traMediaPlanDTO.setTraCustomerThinDTO(traCustomerThinDTO);
        traMediaPlanDTO.setLibFileItemDTO(libMediaItemThinDTO);
        traMediaPlanDTOS.add(traMediaPlanDTO);
        traMediaPlanThinDTO = factory.manufacturePojo(TraMediaPlanThinDTO.class);
        traMediaPlanThinDTO.setTraCustomerThinDTO(traCustomerThinDTO);
        traMediaPlanThinDTO.setLibFileItemDTO(libMediaItemThinDTO);
        traMediaPlanThinDTOS.add(traMediaPlanThinDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corChannel = factory.manufacturePojo(CorChannel.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        TraMediaPlanDTO dto = traMediaPlanMapper.DB2DTO(traMediaPlan);

        assertNotNull(dto.getTraCustomerThinDTO());
        assertNotNull(dto.getTraMediaPlanBlockDTOS());
        assertNotNull(dto.getTraMediaPlanEmissionDTOS());
        assertNotNull(dto.getTraMediaPlanPlaylistDateDTOS());
        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getLibFileItemDTO());


    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraMediaPlanDTO> dtos = traMediaPlanMapper.DBs2DTOs(traMediaPlans);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getTraCustomerThinDTO());
            assertNotNull(dto.getTraMediaPlanBlockDTOS());
            assertNotNull(dto.getTraMediaPlanEmissionDTOS());
            assertNotNull(dto.getTraMediaPlanPlaylistDateDTOS());
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getLibFileItemDTO());
        });
    }


    @Test
    public void DTO2DB() throws Exception {
        TraMediaPlan entity = traMediaPlanMapper.DTO2DB(traMediaPlanDTO, corNetwork, corChannel);
        assertNotNull(entity.getId());
        assertNotNull(entity.getAccount());
        assertNotNull(entity.getName());
        assertNotNull(entity.getLibFileItem());
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
            assertNotNull(entity.getAccount());
            assertNotNull(entity.getName());
            assertNotNull(entity.getLibFileItem());
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
            assertNotNull(traMediaPlanThinDTO.getLibFileItemDTO());
        });

    }


    @Test
    public void DBThin2DTOThin() throws Exception {
        TraMediaPlanThinDTO traMediaPlanThinDTO = traMediaPlanMapper.DBThin2DTOThin(traMediaPlan);

        assertNotNull(traMediaPlanThinDTO.getTraCustomerThinDTO());
        assertNotNull(traMediaPlanThinDTO.getId());
        assertNotNull(traMediaPlanThinDTO.getName());
        assertNotNull(traMediaPlanThinDTO.getLibFileItemDTO());
    }


}
