package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
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

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        attachment = factory.manufacturePojo(SchAttachment.class);
        attachments.add(attachment);
        attachmentDTO = factory.manufacturePojo(SchAttachmentDTO.class);
        attachmentDTO.setMediaItem(null); //TODO: extend test scenarios to media item
        attachmentDTOs.add(attachmentDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchAttachmentDTO dto = attachmentMapper.toDto(attachment);

        assertNotNull(dto.getAttachmentType());
        assertNotNull(dto.getFadeInLength());
        assertNotNull(dto.getFadeOutLength());
        assertNotNull(dto.getFadeStart());
        assertNotNull(dto.getFadeType());
        //assertNotNull(dto.getMediaItem()); TODO: test media item instance mapping
        assertNotNull(dto.getVolumeLevel());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchAttachmentDTO> dtos = attachmentMapper.toDto(attachments);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getAttachmentType());
            assertNotNull(dto.getFadeInLength());
            assertNotNull(dto.getFadeOutLength());
            assertNotNull(dto.getFadeStart());
            assertNotNull(dto.getFadeType());
            //assertNotNull(dto.getMediaItem()); TODO: test media item instance mapping
            assertNotNull(dto.getVolumeLevel());
        });
    }

    @Test
    public void toEntity() throws Exception {
        SchAttachment entity = attachmentMapper.toEntity(attachmentDTO);

        assertNotNull(entity.getAttachmentType());
        assertNotNull(entity.getFadeInLength());
        assertNotNull(entity.getFadeOutLength());
        assertNotNull(entity.getFadeStart());
        assertNotNull(entity.getFadeType());
        //assertNotNull(dto.getMediaItem()); TODO: test media item instance mapping
        assertNotNull(entity.getVolumeLevel());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchAttachment> entities = attachmentMapper.toEntity(attachmentDTOs);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getAttachmentType());
            assertNotNull(entity.getFadeInLength());
            assertNotNull(entity.getFadeOutLength());
            assertNotNull(entity.getFadeStart());
            assertNotNull(entity.getFadeType());
            //assertNotNull(dto.getMediaItem()); TODO: test media item instance mapping
            assertNotNull(entity.getVolumeLevel());
        });
    }

}