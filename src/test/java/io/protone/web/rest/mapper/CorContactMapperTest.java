package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.cor.CoreContactDTO;
import io.protone.domain.CorContact;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corContact = factory.manufacturePojo(CorContact.class);
        corContact.setId(1L);
        corContacts.add(corContact);
        coreContactDTO = factory.manufacturePojo(CoreContactDTO.class);
        coreContactDTOS.add(coreContactDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);

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
        CorContact corContact = corContactMapper.DTO2DB(coreContactDTO, corNetwork);

        assertNotNull(corContact.getId());
        assertNotNull(corContact.getContact());
        assertNotNull(corContact.getContactType());

        assertNotNull(corContact.getNetwork());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorContact> entities = corContactMapper.DTOs2DBs(coreContactDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getContact());
            assertNotNull(entity.getContactType());

            assertNotNull(entity.getNetwork());

        });
    }

}
