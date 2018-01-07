package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CoreContactDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorContact;
import io.protone.core.mapper.CorContactMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 27.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorContactMapperTest {
    @Autowired
    private CorContactMapper corContactMapper;

    private CorContact corContact;

    private CoreContactDTO coreContactDTO;

    private List<CoreContactDTO> coreContactDTOS = new ArrayList<>();

    private Set<CorContact> corContacts = new HashSet<>();

    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corContact = factory.manufacturePojo(CorContact.class);
        corContact.setId(1L);
        corContacts.add(corContact);
        coreContactDTO = factory.manufacturePojo(CoreContactDTO.class);
        coreContactDTOS.add(coreContactDTO);
        corChannel = factory.manufacturePojo(CorChannel.class);

    }

    @Test
    public void DB2DTO() throws Exception {

        CoreContactDTO dto = corContactMapper.DB2DTO(corContact);

        assertNotNull(dto.getId());
        assertNotNull(dto.getContact());
        assertNotNull(dto.getContactType());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CoreContactDTO> dtos = corContactMapper.DBs2DTOs(corContacts);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getContact());
            assertNotNull(dto.getContactType());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CorContact corContact = corContactMapper.DTO2DB(coreContactDTO, corChannel);

        assertNotNull(corContact.getId());
        assertNotNull(corContact.getContact());
        assertNotNull(corContact.getContactType());

        assertNotNull(corContact.getChannel());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorContact> entities = corContactMapper.DTOs2DBs(coreContactDTOS, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getContact());
            assertNotNull(entity.getContactType());

            assertNotNull(entity.getChannel());

        });
    }

}
