package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CrmOpportunityPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmOpportunity;
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
public class CustomCrmOpportunityMapperTest {
    @Autowired
    private CustomCrmOpportunityMapper customCrmOpportunityMapper;
    private CorNetwork mockCorNetwork = null;
    private CrmOpportunity mockCrmOpportunity = null;
    private CrmOpportunityPT mockCrmOpportunityPT = null;

    @Before
    public void initialize() {

    }

    @Test
    public void createOpportunity() throws Exception {
        //then
        CrmOpportunity result = customCrmOpportunityMapper.createOpportunity(mockCrmOpportunityPT, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void buildDTOFromEntites() throws Exception {
        //then
        CrmOpportunityPT result = customCrmOpportunityMapper.buildDTOFromEntites(mockCrmOpportunity);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
