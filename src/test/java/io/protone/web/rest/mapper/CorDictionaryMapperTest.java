package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.domain.CorDictionary;
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
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorDictionaryMapperTest {

    @Autowired
    private CorDictionaryMapper corDictionaryMapper;

    private CorDictionary corDictionary;

    private CorDictionaryDTO corDictionaryDTO;

    private List<CorDictionaryDTO> corDictionaryDTOS = new ArrayList<>();

    private List<CorDictionary> corDictionaries = new ArrayList<>();
    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corDictionary = factory.manufacturePojo(CorDictionary.class);
        corDictionaries.add(corDictionary);
        corDictionaryDTO = factory.manufacturePojo(CorDictionaryDTO.class);
        corDictionaryDTOS.add(corDictionaryDTO);

        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }

    @Test
    public void DTO2DB() throws Exception {
        CorDictionary entity = corDictionaryMapper.DTO2DB(corDictionaryDTO, corNetwork);


        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getDescription());
        assertNotNull(entity.getCorDictionaryType());
        assertNotNull(entity.getSeqNumber());
        assertNotNull(entity.getCorModule());


        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DB2DTO() throws Exception {
        CorDictionaryDTO dto = corDictionaryMapper.DB2DTO(corDictionary);


        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getCorDictionaryType());
        assertNotNull(dto.getSeqNumber());
        assertNotNull(dto.getCorModule());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CorDictionaryDTO> dtos = corDictionaryMapper.DBs2DTOs(corDictionaries);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getDescription());
            assertNotNull(dto.getCorDictionaryType());
            assertNotNull(dto.getSeqNumber());
            assertNotNull(dto.getCorModule());
        });
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorDictionary> entities = corDictionaryMapper.DTOs2DBs(corDictionaryDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getCorDictionaryType());
            assertNotNull(entity.getSeqNumber());
            assertNotNull(entity.getCorModule());


            assertNotNull(entity.getNetwork());

        });
    }

}
