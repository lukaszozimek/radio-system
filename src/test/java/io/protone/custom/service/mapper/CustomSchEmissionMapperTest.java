package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.SchEmission;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CustomSchEmissionMapperTest {
    @Autowired
    private CustomSchEmissionMapper customSchEmissionMapper;

    private CorNetwork mockCorNetwork = null;

    private SchEmission mockSchEmission = null;

    private SchEmissionPT mockSchEmissionPt = null;
    private Set<SchEmission> mockListSchEmission = null;

    private List<SchEmissionPT> mockListSchEmissionPt = null;

    @Before
    public void initialize() {

    }

    @Test
    public void creteDTOFromEntities() throws Exception {
        //then
        SchEmissionPT result = customSchEmissionMapper.creteDTOFromEntities(mockSchEmission);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void createListEmissionFromListDTO() throws Exception {
        //then
        List<SchEmission> result = customSchEmissionMapper.createListEmissionFromListDTO(mockListSchEmissionPt);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void createEmissionFromDTO() throws Exception {
        //then
        SchEmission result = customSchEmissionMapper.createEmissionFromDTO(mockSchEmissionPt);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void createDTOFromListEntites() throws Exception {
        //then
        List<SchEmissionPT> result = customSchEmissionMapper.createDTOFromListEntites(mockListSchEmission);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
