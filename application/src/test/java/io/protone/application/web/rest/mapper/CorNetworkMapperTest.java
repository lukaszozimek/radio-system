package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorNetworkDTO;
import io.protone.core.api.dto.CorOrganizationDTO;
import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorOrganization;
import io.protone.core.mapper.CorNetworkMapper;
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
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorNetworkMapperTest {

    @Autowired
    private CorNetworkMapper corChannelMapper;

    private CorOrganization corOrganization;

    private CorOrganizationDTO corOrganizationDTO;

    private CorNetwork corNetwork;

    private CorNetworkDTO corNetworkDTO;

    private List<CorNetworkDTO> corNetworkDTOS = new ArrayList<>();

    private List<CorNetwork> corNetworks = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setCorImageItem(factory.manufacturePojo(CorImageItem.class));
        corNetworks.add(corNetwork);
        corNetworkDTO = factory.manufacturePojo(CorNetworkDTO.class);


        corNetworkDTOS.add(corNetworkDTO);
    }


    @Test
    public void DTO2DB() throws Exception {
        CorNetwork entity = corChannelMapper.DTO2DB(corNetworkDTO, corOrganization);

        assertNotNull(entity.getId());
        assertNotNull(entity.getShortcut());
        assertNotNull(entity.getName());
        assertNotNull(entity.getDescription());

    }

    @Test
    public void DB2DTO() throws Exception {
        CorNetworkDTO dto = corChannelMapper.DB2DTO(corNetwork);

        assertNotNull(dto.getId());
        assertNotNull(dto.getShortcut());
        assertNotNull(dto.getName());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getPublicUrl());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CorNetworkDTO> dtos = corChannelMapper.DBs2DTOs(corNetworks);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getShortcut());
            assertNotNull(dto.getName());
            assertNotNull(dto.getDescription());

            assertNotNull(dto.getPublicUrl());
        });
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorNetwork> entities = corChannelMapper.DTOs2DBs(corNetworkDTOS);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getShortcut());
            assertNotNull(entity.getName());
            assertNotNull(entity.getDescription());

        });
    }

}
