package io.protone.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.ConfMarkerConfigurationPT;
import io.protone.domain.CfgMarkerConfiguration;
import io.protone.service.mapper.CfgMarkerConfigurationMapper;
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

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 27.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CfgMarkerConfigurationMapperTest {

    @Autowired
    private CfgMarkerConfigurationMapper customCfgMarkerConfigurationMapper;

    private CfgMarkerConfiguration cfgMarkerConfiguration;

    private ConfMarkerConfigurationPT confMarkerConfigurationPT;

    private List<ConfMarkerConfigurationPT> confMarkerConfigurationPTList = new ArrayList<>();

    private List<CfgMarkerConfiguration> cfgMarkerConfigurations = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        cfgMarkerConfiguration = factory.manufacturePojo(CfgMarkerConfiguration.class);
        cfgMarkerConfigurations.add(cfgMarkerConfiguration);
        confMarkerConfigurationPT = factory.manufacturePojo(ConfMarkerConfigurationPT.class);
        confMarkerConfigurationPTList.add(confMarkerConfigurationPT);

    }

    @Test
    public void DB2DTO() throws Exception {
        ConfMarkerConfigurationPT mappedDto = customCfgMarkerConfigurationMapper.DB2DTO(cfgMarkerConfiguration);
        assertNotNull(mappedDto.getId());
        assertNotNull(mappedDto.getName());
        assertNotNull(mappedDto.getDisplayName());
        assertNotNull(mappedDto.getColor());
        assertNotNull(mappedDto.getKeyboardShortcut());
        assertNotNull(mappedDto.getType());
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<ConfMarkerConfigurationPT> mappedDtos = customCfgMarkerConfigurationMapper.DBs2DTOs(cfgMarkerConfigurations);
        assertNotNull(mappedDtos);
        assertEquals(mappedDtos.size(), 1);
        mappedDtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getDisplayName());
            assertNotNull(dto.getColor());
            assertNotNull(dto.getKeyboardShortcut());
            assertNotNull(dto.getType());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CfgMarkerConfiguration entity = customCfgMarkerConfigurationMapper.DTO2DB(confMarkerConfigurationPT);
        assertNotNull(entity.getColor());
        assertNotNull(entity.getDisplayName());
        assertNotNull(entity.getId());
        assertNotNull(entity.getKeyboardShortcut());
        assertNotNull(entity.getName());
        assertNotNull(entity.getType());

        assertNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {

        List<CfgMarkerConfiguration> entities = customCfgMarkerConfigurationMapper.DTOs2DBs(confMarkerConfigurationPTList);
        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getColor());
            assertNotNull(entity.getDisplayName());
            assertNotNull(entity.getId());
            assertNotNull(entity.getKeyboardShortcut());
            assertNotNull(entity.getName());
            assertNotNull(entity.getType());

            assertNull(entity.getNetwork());
        });
    }

}
