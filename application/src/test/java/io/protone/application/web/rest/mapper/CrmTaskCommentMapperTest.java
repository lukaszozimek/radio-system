package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.crm.api.dto.CrmTaskCommentDTO;
import io.protone.crm.domain.CrmTaskComment;
import io.protone.crm.mapper.CrmTaskCommentMapper;
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

import static io.jsonwebtoken.lang.Assert.notNull;

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

    private Set<CrmTaskComment> crmTaskCommentsSet = new HashSet<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmTaskComment = factory.manufacturePojo(CrmTaskComment.class);
        crmTaskComment.setCreatedBy(factory.manufacturePojo(CorUser.class));
        crmTaskComments.add(crmTaskComment);
        crmTaskCommentDTO = factory.manufacturePojo(CrmTaskCommentDTO.class);
        crmTaskCommentDTOS.add(crmTaskCommentDTO);
        crmTaskCommentsSet.add(crmTaskComment);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        CrmTaskCommentDTO dto = crmTaskCommentMapper.DB2DTO(crmTaskComment);

        notNull(dto.getCreatedBy());
        notNull(dto.getId());
        notNull(dto.getComment());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CrmTaskCommentDTO> dtos = crmTaskCommentMapper.DBs2DTOs(crmTaskComments);
        dtos.stream().forEach(dto -> {
            notNull(dto.getCreatedBy());
            notNull(dto.getId());
            notNull(dto.getComment());

        });

    }

    @Test
    public void DBs2DTOs1() throws Exception {
        List<CrmTaskCommentDTO> dtos = crmTaskCommentMapper.DBs2DTOs(crmTaskCommentsSet);
        dtos.stream().forEach(dto -> {
            notNull(dto.getCreatedBy());
            notNull(dto.getId());
            notNull(dto.getComment());

        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CrmTaskComment entity = crmTaskCommentMapper.DTO2DB(crmTaskCommentDTO, corNetwork);
        notNull(entity.getCreatedBy());
        notNull(entity.getId());
        notNull(entity.getComment());
        notNull(entity.getNetwork());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        Set<CrmTaskComment> entities = crmTaskCommentMapper.DTOs2DBs(crmTaskCommentDTOS, corNetwork);
        entities.stream().forEach(entity -> {
            notNull(entity.getCreatedBy());
            notNull(entity.getId());
            notNull(entity.getComment());
            notNull(entity.getNetwork());
        });

    }

}
