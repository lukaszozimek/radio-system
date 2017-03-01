package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CrmContactPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.CrmContact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CustomCrmContactMapperTest {

    @Autowired
    private CustomCrmContactMapper customCrmContactMapper;

    private CrmContact mockCrmContact = null;
    private CorNetwork mockCorNetwork = null;
    private CrmContactPT mockCrmContactPT = null;

    @Before
    public void initialize() {

    }

    @Test
    public void createCrmContactEntity() throws Exception {
        //then
        CrmContact result = customCrmContactMapper.createCrmContactEntity(mockCrmContactPT, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void buildContactDTOFromEntities() throws Exception {
        //then
        CrmContactPT result = customCrmContactMapper.buildContactDTOFromEntities(mockCrmContact);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
