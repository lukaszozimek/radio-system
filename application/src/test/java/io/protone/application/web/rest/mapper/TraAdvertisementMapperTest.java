package io.protone.application.web.rest.mapper;


import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.crm.domain.CrmAccount;
import io.protone.library.domain.LibMediaItem;
import io.protone.traffic.api.dto.TraAdvertisementDTO;
import io.protone.traffic.domain.TraAdvertisement;
import io.protone.traffic.mapper.TraAdvertisementMapper;
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
 * Created by lukaszozimek on 28.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraAdvertisementMapperTest {


    @Autowired
    private TraAdvertisementMapper customTRAAdvertisementMapper;

    private TraAdvertisement traAdvertisement;

    private TraAdvertisementDTO traAdvertisementDTO;

    private List<TraAdvertisementDTO> traAdvertisementDTOS = new ArrayList<>();

    private List<TraAdvertisement> traAdvertisements = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setId(1L);
        traAdvertisement.setCustomer(factory.manufacturePojo(CrmAccount.class));
        traAdvertisement.setIndustry(factory.manufacturePojo(CorDictionary.class));
        traAdvertisement.setType(factory.manufacturePojo(CorDictionary.class));
        traAdvertisement.setCreatedBy(factory.manufacturePojo(CorUser.class));
        traAdvertisement.setLastModifiedBy(factory.manufacturePojo(CorUser.class));
        LibMediaItem libMediaItem =factory.manufacturePojo(LibMediaItem.class);
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));
        traAdvertisements.add(traAdvertisement);
        traAdvertisementDTO = factory.manufacturePojo(TraAdvertisementDTO.class);
        traAdvertisementDTOS.add(traAdvertisementDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);

    }

    @Test
    public void DB2DTO() throws Exception {
        TraAdvertisementDTO dto = customTRAAdvertisementMapper.DB2DTO(traAdvertisement);

        assertNotNull(dto.getId());
        assertNotNull(dto.getLibMediaItemThinDTOList());
        assertNotNull(dto.getIndustryId());
        assertNotNull(dto.getTypeId());
        assertNotNull(dto.getCustomerId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getCreatedBy());
        assertNotNull(dto.getCreatedDate());
        assertNotNull(dto.getLastModifiedBy());
        assertNotNull(dto.getLastModifiedDate());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraAdvertisementDTO> dtos = customTRAAdvertisementMapper.DBs2DTOs(traAdvertisements);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());
            assertNotNull(dto.getLibMediaItemThinDTOList());
            assertNotNull(dto.getIndustryId());
            assertNotNull(dto.getTypeId());
            assertNotNull(dto.getCustomerId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getDescription());
            assertNotNull(dto.getCreatedBy());
            assertNotNull(dto.getCreatedDate());
            assertNotNull(dto.getLastModifiedBy());
            assertNotNull(dto.getLastModifiedDate());
        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraAdvertisement> entities = customTRAAdvertisementMapper.DTOs2DBs(traAdvertisementDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getId());
            assertNotNull(entity.getCustomer());
            assertNotNull(entity.getMediaItem());
            assertNotNull(entity.getIndustry());
            assertNotNull(entity.getType());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());

            assertNotNull(entity.getNetwork());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        TraAdvertisement entity = customTRAAdvertisementMapper.DTO2DB(traAdvertisementDTO, corNetwork);


        assertNotNull(entity.getId());
        assertNotNull(entity.getCustomer());
        assertNotNull(entity.getMediaItem());
        assertNotNull(entity.getIndustry());
        assertNotNull(entity.getType());
        assertNotNull(entity.getDescription());
        assertNotNull(entity.getId());
        assertNotNull(entity.getName());

        assertNotNull(entity.getNetwork());
    }
}
