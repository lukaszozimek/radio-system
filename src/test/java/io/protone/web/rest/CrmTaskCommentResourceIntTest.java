package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CrmTaskComment;
import io.protone.repository.CrmTaskCommentRepository;
import io.protone.service.dto.CrmTaskCommentDTO;
import io.protone.service.mapper.CrmTaskCommentMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CrmTaskCommentResource REST controller.
 *
 * @see CrmTaskCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmTaskCommentResourceIntTest {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private CrmTaskCommentRepository crmTaskCommentRepository;

    @Autowired
    private CrmTaskCommentMapper crmTaskCommentMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCrmTaskCommentMockMvc;

    private CrmTaskComment crmTaskComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CrmTaskCommentResource crmTaskCommentResource = new CrmTaskCommentResource(crmTaskCommentRepository, crmTaskCommentMapper);
        this.restCrmTaskCommentMockMvc = MockMvcBuilders.standaloneSetup(crmTaskCommentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmTaskComment createEntity(EntityManager em) {
        CrmTaskComment crmTaskComment = new CrmTaskComment()
                .comment(DEFAULT_COMMENT);
        return crmTaskComment;
    }

    @Before
    public void initTest() {
        crmTaskComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrmTaskComment() throws Exception {
        int databaseSizeBeforeCreate = crmTaskCommentRepository.findAll().size();

        // Create the CrmTaskComment
        CrmTaskCommentDTO crmTaskCommentDTO = crmTaskCommentMapper.crmTaskCommentToCrmTaskCommentDTO(crmTaskComment);

        restCrmTaskCommentMockMvc.perform(post("/api/crm-task-comments")
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
        int databaseSizeBeforeCreate = crmTaskCommentRepository.findAll().size();

        // Create the CrmTaskComment with an existing ID
        CrmTaskComment existingCrmTaskComment = new CrmTaskComment();
        existingCrmTaskComment.setId(1L);
        CrmTaskCommentDTO existingCrmTaskCommentDTO = crmTaskCommentMapper.crmTaskCommentToCrmTaskCommentDTO(existingCrmTaskComment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmTaskCommentMockMvc.perform(post("/api/crm-task-comments")
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
        // Initialize the database
        crmTaskCommentRepository.saveAndFlush(crmTaskComment);

        // Get all the crmTaskCommentList
        restCrmTaskCommentMockMvc.perform(get("/api/crm-task-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmTaskComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getCrmTaskComment() throws Exception {
        // Initialize the database
        crmTaskCommentRepository.saveAndFlush(crmTaskComment);

        // Get the crmTaskComment
        restCrmTaskCommentMockMvc.perform(get("/api/crm-task-comments/{id}", crmTaskComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmTaskComment.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrmTaskComment() throws Exception {
        // Get the crmTaskComment
        restCrmTaskCommentMockMvc.perform(get("/api/crm-task-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmTaskComment() throws Exception {
        // Initialize the database
        crmTaskCommentRepository.saveAndFlush(crmTaskComment);
        int databaseSizeBeforeUpdate = crmTaskCommentRepository.findAll().size();

        // Update the crmTaskComment
        CrmTaskComment updatedCrmTaskComment = crmTaskCommentRepository.findOne(crmTaskComment.getId());
        updatedCrmTaskComment
                .comment(UPDATED_COMMENT);
        CrmTaskCommentDTO crmTaskCommentDTO = crmTaskCommentMapper.crmTaskCommentToCrmTaskCommentDTO(updatedCrmTaskComment);

        restCrmTaskCommentMockMvc.perform(put("/api/crm-task-comments")
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
        int databaseSizeBeforeUpdate = crmTaskCommentRepository.findAll().size();

        // Create the CrmTaskComment
        CrmTaskCommentDTO crmTaskCommentDTO = crmTaskCommentMapper.crmTaskCommentToCrmTaskCommentDTO(crmTaskComment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmTaskCommentMockMvc.perform(put("/api/crm-task-comments")
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
        // Initialize the database
        crmTaskCommentRepository.saveAndFlush(crmTaskComment);
        int databaseSizeBeforeDelete = crmTaskCommentRepository.findAll().size();

        // Get the crmTaskComment
        restCrmTaskCommentMockMvc.perform(delete("/api/crm-task-comments/{id}", crmTaskComment.getId())
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
