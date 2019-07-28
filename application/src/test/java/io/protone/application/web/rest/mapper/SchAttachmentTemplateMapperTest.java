package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.api.dto.SchAttachmentTemplateDTO;
import io.protone.scheduler.domain.SchAttachmentTemplate;
import io.protone.scheduler.domain.SchEmissionTemplate;
import io.protone.scheduler.mapper.SchAttachmentTemplateMapper;
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
public class SchAttachmentTemplateMapperTest {

    @Autowired
    private SchAttachmentTemplateMapper schAttachmentTemplateMapper;

    private SchAttachmentTemplate attachment;

    private SchAttachmentTemplateDTO attachmentDTO;

    private List<SchAttachmentTemplate> attachments = new ArrayList<>();

    private List<SchAttachmentTemplateDTO> attachmentDTOs = new ArrayList<>();
    private CorNetwork network;
    private CorChannel corChannel;
    private SchEmissionTemplate schEmissionTemplate;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        // Fill entity instance
        attachment = factory.manufacturePojo(SchAttachmentTemplate.class);
        attachment.setMediaItem(factory.manufacturePojo(LibMediaItem.class));
        attachments.add(attachment);
        attachmentDTO = factory.manufacturePojo(SchAttachmentTemplateDTO.class);
        schEmissionTemplate = factory.manufacturePojo(SchEmissionTemplate.class);
        attachmentDTO.setMediaItem(factory.manufacturePojo(LibMediaItemThinDTO.class));
        attachmentDTOs.add(attachmentDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchAttachmentTemplateDTO dto = schAttachmentTemplateMapper.DB2DTO(attachment);

        assertNotNull(dto.getAttachmentType());
        assertNotNull(dto.getFadeInLength());
        assertNotNull(dto.getFadeOutLength());
        assertNotNull(dto.getFadeStart());
        assertNotNull(dto.getFadeType());
        assertNotNull(dto.getSequence());
        assertNotNull(dto.getMediaItem());
        assertNotNull(dto.getVolumeLevel());

        assertNotNull(dto.getLength());
        assertNotNull(dto.getRelativeDelay());
        assertNotNull(dto.getSequence());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchAttachmentTemplateDTO> dtos = schAttachmentTemplateMapper.DBs2DTOs(attachments);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getAttachmentType());
            assertNotNull(dto.getFadeInLength());
            assertNotNull(dto.getFadeOutLength());
            assertNotNull(dto.getFadeStart());
            assertNotNull(dto.getFadeType());
            assertNotNull(dto.getMediaItem());
            assertNotNull(dto.getSequence());
            assertNotNull(dto.getVolumeLevel());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchAttachmentTemplate entity = schAttachmentTemplateMapper.DTO2DB(attachmentDTO, network, corChannel, schEmissionTemplate);

        assertNotNull(entity.getAttachmentType());
        assertNotNull(entity.getFadeInLength());
        assertNotNull(entity.getFadeOutLength());
        assertNotNull(entity.getFadeStart());
        assertNotNull(entity.getFadeType());
        assertNotNull(entity.getMediaItem());
        assertNotNull(entity.getVolumeLevel());
        assertNotNull(entity.getSequence());
        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());

        assertNotNull(entity.getLength());
        assertNotNull(entity.getRelativeDelay());
        assertNotNull(entity.getSequence());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchAttachmentTemplate> entities = schAttachmentTemplateMapper.DTOs2DBs(attachmentDTOs, network, corChannel, schEmissionTemplate);

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
            assertNotNull(entity.getSequence());
            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
            assertNotNull(entity.getLength());
            assertNotNull(entity.getRelativeDelay());
            assertNotNull(entity.getSequence());
        });
    }

}