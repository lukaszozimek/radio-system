package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.api.dto.SchAttachmentDTO;
import io.protone.scheduler.domain.SchAttachment;
import io.protone.scheduler.mapper.SchAttachmentMapper;
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
public class SchAttachmentMapperTest {

    @Autowired
    private SchAttachmentMapper attachmentMapper;

    private SchAttachment attachment;

    private SchAttachmentDTO attachmentDTO;

    private List<SchAttachment> attachments = new ArrayList<>();

    private List<SchAttachmentDTO> attachmentDTOs = new ArrayList<>();
    private CorChannel corChannel;


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corChannel = factory.manufacturePojo(CorChannel.class);
        // Fill entity instance
        attachment = factory.manufacturePojo(SchAttachment.class);
        attachment.setMediaItem(factory.manufacturePojo(LibMediaItem.class));
        attachments.add(attachment);
        attachmentDTO = factory.manufacturePojo(SchAttachmentDTO.class);
        attachmentDTO.setMediaItem(factory.manufacturePojo(LibMediaItemThinDTO.class));
        attachmentDTOs.add(attachmentDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchAttachmentDTO dto = attachmentMapper.DB2DTO(attachment);

        assertNotNull(dto.getAttachmentType());
        assertNotNull(dto.getFadeInLength());
        assertNotNull(dto.getFadeOutLength());
        assertNotNull(dto.getFadeStart());
        assertNotNull(dto.getFadeType());
        assertNotNull(dto.getMediaItem());
        assertNotNull(dto.getSequence());
        assertNotNull(dto.getVolumeLevel());
        assertNotNull(dto.getStartTime());
        assertNotNull(dto.getEndTime());
        assertNotNull(dto.getRelativeDelay());
        assertNotNull(dto.getSequence());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchAttachmentDTO> dtos = attachmentMapper.DBs2DTOs(attachments);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getAttachmentType());
            assertNotNull(dto.getFadeInLength());
            assertNotNull(dto.getFadeOutLength());
            assertNotNull(dto.getFadeStart());
            assertNotNull(dto.getFadeType());
            assertNotNull(dto.getSequence());
            assertNotNull(dto.getMediaItem());
            assertNotNull(dto.getVolumeLevel());
            assertNotNull(dto.getStartTime());
            assertNotNull(dto.getEndTime());
            assertNotNull(dto.getRelativeDelay());
            assertNotNull(dto.getSequence());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchAttachment entity = attachmentMapper.DTO2DB(attachmentDTO, corChannel);

        assertNotNull(entity.getAttachmentType());
        assertNotNull(entity.getFadeInLength());
        assertNotNull(entity.getFadeOutLength());
        assertNotNull(entity.getFadeStart());
        assertNotNull(entity.getFadeType());
        assertNotNull(entity.getMediaItem());
        assertNotNull(entity.getVolumeLevel());
        assertNotNull(entity.getSequence());
        assertNotNull(entity.getChannel());
        assertNotNull(entity.getStartTime());
        assertNotNull(entity.getEndTime());
        assertNotNull(entity.getRelativeDelay());
        assertNotNull(entity.getSequence());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchAttachment> entities = attachmentMapper.DTOs2DBs(attachmentDTOs, corChannel);

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
            assertNotNull(entity.getStartTime());
            assertNotNull(entity.getEndTime());
            assertNotNull(entity.getRelativeDelay());
            assertNotNull(entity.getSequence());
            assertNotNull(entity.getChannel());
        });
    }

}