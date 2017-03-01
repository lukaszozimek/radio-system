package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmLead;
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
public class CustomCrmLeadMapperTest {
    @Autowired
    private CustomCrmLeadMapper customCrmLeadMapper;
    private CrmLead mockCrmLead = null;
    private CrmLeadPT mockCrmLeadPT = null;
    private CorNetwork mockCorNetwork = null;

    @Before
    public void initialize() {

    }

    @Test
    public void createLeadEntity() throws Exception {
        //then
        CrmLead result = customCrmLeadMapper.createLeadEntity(mockCrmLeadPT, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void createDTOFromEntites() throws Exception {
        //then
        CrmLeadPT result = customCrmLeadMapper.createDTOFromEntites(mockCrmLead);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
