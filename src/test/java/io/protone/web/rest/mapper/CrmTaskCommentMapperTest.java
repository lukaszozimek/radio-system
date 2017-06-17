package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmTaskComment;
import io.protone.web.rest.dto.traffic.CrmTaskCommentDTO;
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

/**
 * Created by lukaszozimek on 17.06.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmTaskCommentMapperTest {

    @Autowired
    private CrmTaskCommentMapper crmTaskCommentMapper;

    private CrmTaskComment crmTaskComment;

    private CrmTaskCommentDTO crmTaskCommentDTO;

    private CorNetwork corNetwork;

    private List<CrmTaskCommentDTO> crmTaskCommentDTOS = new ArrayList<>();

    private List<CrmTaskComment> crmTaskComments = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmTaskComment = factory.manufacturePojo(CrmTaskComment.class);
        crmTaskComments.add(crmTaskComment);
        crmTaskCommentDTO = factory.manufacturePojo(CrmTaskCommentDTO.class);
        crmTaskCommentDTOS.add(crmTaskCommentDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }

    @Test
    public void DB2DTO() throws Exception {
    }

    @Test
    public void DBs2DTOs() throws Exception {
    }

    @Test
    public void DBs2DTOs1() throws Exception {
    }

    @Test
    public void DTO2DB() throws Exception {
    }

    @Test
    public void DTOs2DBs() throws Exception {
    }

}
