package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.api.dto.SchAttachmentConfigurationDTO;
import io.protone.scheduler.domain.SchAttachmentConfiguration;
import io.protone.scheduler.mapper.SchAttachmentConfigurationMapper;
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

@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchAttachmentConfigurationMapperTest {

    @Autowired
    private SchAttachmentConfigurationMapper schAttachmentConfigurationMapper;

    private SchAttachmentConfiguration attachment;

    private SchAttachmentConfigurationDTO attachmentDTO;

    private List<SchAttachmentConfiguration> attachments = new ArrayList<>();

    private List<SchAttachmentConfigurationDTO> attachmentDTOs = new ArrayList<>();
    private CorNetwork network;
    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        // Fill entity instance
        attachment = factory.manufacturePojo(SchAttachmentConfiguration.class);
        attachment.setMediaItem(factory.manufacturePojo(LibMediaItem.class));
        attachments.add(attachment);
        attachmentDTO = factory.manufacturePojo(SchAttachmentConfigurationDTO.class);
        attachmentDTO.setMediaItem(factory.manufacturePojo(LibMediaItemThinDTO.class));
        attachmentDTOs.add(attachmentDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchAttachmentConfigurationDTO dto = schAttachmentConfigurationMapper.DB2DTO(attachment);

        assertNotNull(dto.getAttachmentType());
        assertNotNull(dto.getFadeInLength());
        assertNotNull(dto.getFadeOutLength());
        assertNotNull(dto.getFadeStart());
        assertNotNull(dto.getFadeType());
        assertNotNull(dto.getMediaItem());
        assertNotNull(dto.getVolumeLevel());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchAttachmentConfigurationDTO> dtos = schAttachmentConfigurationMapper.DBs2DTOs(attachments);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getAttachmentType());
            assertNotNull(dto.getFadeInLength());
            assertNotNull(dto.getFadeOutLength());
            assertNotNull(dto.getFadeStart());
            assertNotNull(dto.getFadeType());
            assertNotNull(dto.getMediaItem());
            assertNotNull(dto.getVolumeLevel());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchAttachmentConfiguration entity = schAttachmentConfigurationMapper.DTO2DB(attachmentDTO, network, corChannel);

        assertNotNull(entity.getAttachmentType());
        assertNotNull(entity.getFadeInLength());
        assertNotNull(entity.getFadeOutLength());
        assertNotNull(entity.getFadeStart());
        assertNotNull(entity.getFadeType());
        assertNotNull(entity.getMediaItem());
        assertNotNull(entity.getVolumeLevel());

        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchAttachmentConfiguration> entities = schAttachmentConfigurationMapper.DTOs2DBs(attachmentDTOs, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getAttachmentType());
            assertNotNull(entity.getFadeInLength());
            assertNotNull(entity.getFadeOutLength());
            assertNotNull(entity.getFadeStart());
            assertNotNull(entity.getFadeType());
            assertNotNull(entity.getMediaItem());
            assertNotNull(entity.getVolumeLevel());

            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
        });
    }

}