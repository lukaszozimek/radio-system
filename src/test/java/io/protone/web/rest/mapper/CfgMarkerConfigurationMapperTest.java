package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.library.LibMarkerConfigurationDTO;
import io.protone.domain.CfgMarkerConfiguration;
import io.protone.domain.CorNetwork;
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
    private CfgMarkerConfigurationMapper cfgMarkerConfigurationMapper;

    private CfgMarkerConfiguration cfgMarkerConfiguration;

    private LibMarkerConfigurationDTO libMarkerConfigurationDTO;

    private List<LibMarkerConfigurationDTO> libMarkerConfigurationDTOList = new ArrayList<>();

    private List<CfgMarkerConfiguration> cfgMarkerConfigurations = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        cfgMarkerConfiguration = factory.manufacturePojo(CfgMarkerConfiguration.class);
        cfgMarkerConfigurations.add(cfgMarkerConfiguration);
        libMarkerConfigurationDTO = factory.manufacturePojo(LibMarkerConfigurationDTO.class);
        libMarkerConfigurationDTOList.add(libMarkerConfigurationDTO);

        corNetwork = factory.manufacturePojo(CorNetwork.class);

    }

    @Test
    public void DB2DTO() throws Exception {
        LibMarkerConfigurationDTO mappedDto = cfgMarkerConfigurationMapper.DB2DTO(cfgMarkerConfiguration);
        assertNotNull(mappedDto.getId());
        assertNotNull(mappedDto.getName());
        assertNotNull(mappedDto.getDisplayName());
        assertNotNull(mappedDto.getColor());
        assertNotNull(mappedDto.getKeyboardShortcut());
        assertNotNull(mappedDto.getType());
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<LibMarkerConfigurationDTO> mappedDtos = cfgMarkerConfigurationMapper.DBs2DTOs(cfgMarkerConfigurations);
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
        CfgMarkerConfiguration entity = cfgMarkerConfigurationMapper.DTO2DB(libMarkerConfigurationDTO, corNetwork);
        assertNotNull(entity.getColor());
        assertNotNull(entity.getDisplayName());
        assertNotNull(entity.getId());
        assertNotNull(entity.getKeyboardShortcut());
        assertNotNull(entity.getName());
        assertNotNull(entity.getType());

        assertNotNull(entity.getNetwork());

        assertEquals(entity.getNetwork().getShortcut(), corNetwork.getShortcut());
    }

    @Test
    public void DTOs2DBs() throws Exception {

        List<CfgMarkerConfiguration> entities = cfgMarkerConfigurationMapper.DTOs2DBs(libMarkerConfigurationDTOList, corNetwork);
        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getColor());
            assertNotNull(entity.getDisplayName());
            assertNotNull(entity.getId());
            assertNotNull(entity.getKeyboardShortcut());
            assertNotNull(entity.getName());
            assertNotNull(entity.getType());
            assertNotNull(entity.getNetwork());
            assertEquals(entity.getNetwork().getShortcut(), corNetwork.getShortcut());
        });
    }

}
