package io.protone.application.web.api.crm;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.crm.impl.CrmLeadTaskCommentResourceImpl;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.crm.api.dto.CrmTaskCommentDTO;
import io.protone.crm.domain.CrmLead;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.domain.CrmTaskComment;
import io.protone.crm.mapper.CrmTaskCommentMapper;
import io.protone.crm.repostiory.CrmLeadRepository;
import io.protone.crm.repostiory.CrmTaskCommentRepository;
import io.protone.crm.repostiory.CrmTaskRepository;
import io.protone.crm.service.CrmLeadService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 18/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmLeadTaskCommentResourceImplTest {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Inject
    private CrmTaskCommentRepository crmTaskCommentRepository;

    @Inject
    private CrmTaskCommentMapper crmTaskCommentMapper;

    @Inject
    private CrmTaskRepository crmTaskRepository;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CrmLeadService crmLeadService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    @Inject
    private CrmLeadRepository crmLeadRepository;

    private CrmTask crmTask;

    private CorNetwork corNetwork;

    private CrmLead crmLead;

    private MockMvc restCrmTaskCommentMockMvc;

    private CrmTaskComment crmTaskComment;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmTaskComment createEntity(EntityManager em) {
        CrmTaskComment crmTaskComment = new CrmTaskComment()
            .comment(DEFAULT_COMMENT);
        return crmTaskComment;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CrmLeadTaskCommentResourceImpl crmTaskCommentResource = new CrmLeadTaskCommentResourceImpl();
        ReflectionTestUtils.setField(crmTaskCommentResource, "crmLeadService", crmLeadService);
        ReflectionTestUtils.setField(crmTaskCommentResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(crmTaskCommentResource, "crmTaskCommentMapper", crmTaskCommentMapper);

        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);

        this.restCrmTaskCommentMockMvc = MockMvcBuilders.standaloneSetup(crmTaskCommentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        crmLead = CrmLeadResourceImplTest.createEntity(em).network(corNetwork);
        crmTask = CrmLeadTaskResourceImplTest.createEntity(em);
        crmTaskComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrmTaskComment() throws Exception {
        crmTaskRepository.deleteAll();
        crmLeadRepository.deleteAll();

        crmLead = crmLeadRepository.saveAndFlush(crmLead.network(corNetwork));
        crmTask = crmTaskRepository.saveAndFlush(crmTask.network(corNetwork).lead(crmLead));

        int databaseSizeBeforeCreate = crmTaskCommentRepository.findAll().size();

        // Create the CrmTaskComment
        CrmTaskCommentDTO crmTaskCommentDTO = crmTaskCommentMapper.DB2DTO(crmTaskComment);

        restCrmTaskCommentMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/crm/lead/{shortName}/task/{taskId}/comment", corNetwork.getShortcut(), crmLead.getShortname(), crmTask.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmTaskCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmTaskComment in the database
        List<CrmTaskComment> crmTaskCommentList = crmTaskCommentRepository.findAll();
        assertThat(crmTaskCommentList).hasSize(databaseSizeBeforeCreate + 1);
        CrmTaskComment testCrmTaskComment = crmTaskCommentList.get(crmTaskCommentList.size() - 1);
        assertThat(testCrmTaskComment.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createCrmTaskCommentWithExistingId() throws Exception {
        crmTaskRepository.deleteAll();
        crmLeadRepository.deleteAll();

        crmLead = crmLeadRepository.saveAndFlush(crmLead.network(corNetwork));
        crmTask = crmTaskRepository.saveAndFlush(crmTask.network(corNetwork).lead(crmLead));

        int databaseSizeBeforeCreate = crmTaskCommentRepository.findAll().size();

        // Create the CrmTaskComment with an existing ID
        CrmTaskComment existingCrmTaskComment = new CrmTaskComment();
        existingCrmTaskComment.setId(1L);
        CrmTaskCommentDTO existingCrmTaskCommentDTO = crmTaskCommentMapper.DB2DTO(existingCrmTaskComment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmTaskCommentMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/crm/lead/{shortName}/task/{taskId}/comment", corNetwork.getShortcut(), crmLead.getShortname(), crmTask.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCrmTaskCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CrmTaskComment> crmTaskCommentList = crmTaskCommentRepository.findAll();
        assertThat(crmTaskCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCrmTaskComments() throws Exception {
        crmTaskRepository.deleteAll();
        crmLeadRepository.deleteAll();

        crmLead = crmLeadRepository.saveAndFlush(crmLead.network(corNetwork));
        crmTask = crmTaskRepository.saveAndFlush(crmTask.network(corNetwork).lead(crmLead));

        // Initialize the database
        crmTaskCommentRepository.saveAndFlush(crmTaskComment.network(corNetwork).taskComment(crmTask));

        // Get all the crmTaskCommentList
        restCrmTaskCommentMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/crm/lead/{shortName}/task/{taskId}/comment?sort=id,desc", corNetwork.getShortcut(), crmLead.getShortname(), crmTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmTaskComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getCrmTaskComment() throws Exception {
        crmTaskRepository.deleteAll();
        crmLeadRepository.deleteAll();

        crmLead = crmLeadRepository.saveAndFlush(crmLead.network(corNetwork));
        crmTask = crmTaskRepository.saveAndFlush(crmTask.network(corNetwork).lead(crmLead));

        // Initialize the database
        crmTaskCommentRepository.saveAndFlush(crmTaskComment.network(corNetwork).taskComment(crmTask));

        // Get the crmTaskComment
        restCrmTaskCommentMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/crm/lead/{shortName}/task/{taskId}/comment/{id}", corNetwork.getShortcut(), crmLead.getShortname(), crmTask.getId(), crmTaskComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmTaskComment.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrmTaskComment() throws Exception {
        // Get the crmTaskComment
        restCrmTaskCommentMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/crm/lead/{shortName}/task/{taskId}/comment/{id}", corNetwork.getShortcut(), crmLead.getShortname(), crmTask.getId(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmTaskComment() throws Exception {
        crmTaskRepository.deleteAll();
        crmLeadRepository.deleteAll();

        crmLead = crmLeadRepository.saveAndFlush(crmLead.network(corNetwork));
        crmTask = crmTaskRepository.saveAndFlush(crmTask.network(corNetwork).lead(crmLead));

        // Initialize the database
        crmTaskCommentRepository.saveAndFlush(crmTaskComment.network(corNetwork).taskComment(crmTask));
        int databaseSizeBeforeUpdate = crmTaskCommentRepository.findAll().size();

        // Update the crmTaskComment
        CrmTaskComment updatedCrmTaskComment = crmTaskCommentRepository.findOne(crmTaskComment.getId());
        updatedCrmTaskComment
            .comment(UPDATED_COMMENT);
        CrmTaskCommentDTO crmTaskCommentDTO = crmTaskCommentMapper.DB2DTO(updatedCrmTaskComment);

        restCrmTaskCommentMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/crm/lead/{shortName}/task/{taskId}/comment", corNetwork.getShortcut(), crmLead.getShortname(), crmTask.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmTaskCommentDTO)))
            .andExpect(status().isOk());

        // Validate the CrmTaskComment in the database
        List<CrmTaskComment> crmTaskCommentList = crmTaskCommentRepository.findAll();
        assertThat(crmTaskCommentList).hasSize(databaseSizeBeforeUpdate);
        CrmTaskComment testCrmTaskComment = crmTaskCommentList.get(crmTaskCommentList.size() - 1);
        assertThat(testCrmTaskComment.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingCrmTaskComment() throws Exception {
        crmTaskRepository.deleteAll();
        crmLeadRepository.deleteAll();

        crmLead = crmLeadRepository.saveAndFlush(crmLead.network(corNetwork));
        crmTask = crmTaskRepository.saveAndFlush(crmTask.network(corNetwork).lead(crmLead));

        int databaseSizeBeforeUpdate = crmTaskCommentRepository.findAll().size();

        // Create the CrmTaskComment
        CrmTaskCommentDTO crmTaskCommentDTO = crmTaskCommentMapper.DB2DTO(crmTaskComment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmTaskCommentMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/crm/lead/{shortName}/task/{taskId}/comment", corNetwork.getShortcut(), crmLead.getShortname(), crmTask.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmTaskCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmTaskComment in the database
        List<CrmTaskComment> crmTaskCommentList = crmTaskCommentRepository.findAll();
        assertThat(crmTaskCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCrmTaskComment() throws Exception {
        crmTaskRepository.deleteAll();
        crmLeadRepository.deleteAll();

        crmLead = crmLeadRepository.saveAndFlush(crmLead.network(corNetwork));
        crmTask = crmTaskRepository.saveAndFlush(crmTask.network(corNetwork).lead(crmLead));

        // Initialize the database
        crmTaskCommentRepository.saveAndFlush(crmTaskComment.network(corNetwork).taskComment(crmTask));
        int databaseSizeBeforeDelete = crmTaskCommentRepository.findAll().size();

        // Get the crmTaskComment
        restCrmTaskCommentMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/crm/lead/{shortName}/task/{taskId}/comment/{id}", corNetwork.getShortcut(), crmLead.getShortname(), crmTask.getId(), crmTaskComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrmTaskComment> crmTaskCommentList = crmTaskCommentRepository.findAll();
        assertThat(crmTaskCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmTaskComment.class);
    }
}
