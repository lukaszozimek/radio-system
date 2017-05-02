package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.cor.CorChannelDTO;
import io.protone.domain.CorChannel;
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
public class CorChannelMapperTest {


    @Autowired
    private CorChannelMapper corChannelMapper;

    private CorChannel channel;

    private CorChannelDTO corChannelDTO;

    private List<CorChannelDTO> corChannelDTOS = new ArrayList<>();

    private List<CorChannel> channels = new ArrayList<>();
    private CorNetwork corNetwork;


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        channel = factory.manufacturePojo(CorChannel.class);
        channels.add(channel);
        corChannelDTO = factory.manufacturePojo(CorChannelDTO.class);
        corChannelDTOS.add(corChannelDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        CorChannelDTO channelPT = corChannelMapper.DB2DTO(channel);

        assertNotNull(channelPT.getId());
        assertNotNull(channelPT.getShortcut());
        assertNotNull(channelPT.getName());
        assertNotNull(channelPT.getDescription());
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CorChannelDTO> channelPTS = corChannelMapper.DBs2DTOs(channels);

        assertNotNull(channelPTS);
        assertEquals(channelPTS.size(), 1);
        channelPTS.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getShortcut());
            assertNotNull(dto.getName());
            assertNotNull(dto.getDescription());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CorChannel entity = corChannelMapper.DTO2DB(corChannelDTO,corNetwork);

        assertNotNull(entity.getId());
        assertNotNull(entity.getShortcut());
        assertNotNull(entity.getName());
        assertNotNull(entity.getDescription());

        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorChannel> entities = corChannelMapper.DTOs2DBs(corChannelDTOS,corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getShortcut());
            assertNotNull(entity.getName());
            assertNotNull(entity.getDescription());

            assertNotNull(entity.getNetwork());
        });
    }

}
