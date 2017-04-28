package io.protone.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.ConfMusicLogPT;
import io.protone.domain.CfgExternalSystemLog;
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
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 27.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class ConfMusicLogMapperTest {

    @Autowired
    private ConfMusicLogMapper confMusicLogMapper;

    private CfgExternalSystemLog cfgExternalSystemLog;

    private ConfMusicLogPT confMusicLogPT;

    private List<ConfMusicLogPT> confMusicLogPTS = new ArrayList<>();

    private List<CfgExternalSystemLog> cfgExternalSystemLogs = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        cfgExternalSystemLog = factory.manufacturePojo(CfgExternalSystemLog.class);
        cfgExternalSystemLogs.add(cfgExternalSystemLog);
        confMusicLogPT = factory.manufacturePojo(ConfMusicLogPT.class);
        confMusicLogPTS.add(confMusicLogPT);

    }

    @Test
    public void DB2DTO() throws Exception {
        ConfMusicLogPT mappedDto = confMusicLogMapper.DB2DTO(cfgExternalSystemLog);

        assertNotNull(mappedDto.getId());
        assertNotNull(mappedDto.getName());
        assertNotNull(mappedDto.getLenght());
        assertNotNull(mappedDto.getLogColumn());
        assertNotNull(mappedDto.getDelimiter());
        assertNotNull(mappedDto.getColumnSequence());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<ConfMusicLogPT> mappedDto = confMusicLogMapper.DBs2DTOs(cfgExternalSystemLogs);
        assertNotNull(mappedDto);
        assertEquals(mappedDto.size(), 1);
        mappedDto.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getLenght());
            assertNotNull(dto.getLogColumn());
            assertNotNull(dto.getDelimiter());
            assertNotNull(dto.getColumnSequence());

        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CfgExternalSystemLog entity = confMusicLogMapper.DTO2DB(confMusicLogPT);

        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getLenght());
        assertNotNull(entity.getLogColumn());
        assertNotNull(entity.getDelimiter());
        assertNotNull(entity.getColumnSequence());

        assertNull(entity.getNetwork());
        assertNull(entity.getChannel());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CfgExternalSystemLog> entities = confMusicLogMapper.DTOs2DBs(confMusicLogPTS);
        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getLenght());
            assertNotNull(entity.getLogColumn());
            assertNotNull(entity.getDelimiter());
            assertNotNull(entity.getColumnSequence());

            assertNull(entity.getNetwork());
            assertNull(entity.getChannel());
        });
    }
}