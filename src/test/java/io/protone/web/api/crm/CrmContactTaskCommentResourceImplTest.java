package io.protone.web.api.crm;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmContact;
import io.protone.domain.CrmTask;
import io.protone.domain.CrmTaskComment;
import io.protone.repository.crm.CrmContactRepository;
import io.protone.repository.crm.CrmTaskCommentRepository;
import io.protone.repository.crm.CrmTaskRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.crm.CrmContactService;
import io.protone.util.TestUtil;
import io.protone.web.api.crm.impl.CrmContactTaskCommentResourceImpl;
import io.protone.web.rest.dto.traffic.CrmTaskCommentDTO;
import io.protone.web.rest.mapper.CrmTaskCommentMapper;
import io.protone.web.rest.mapper.CrmTaskMapper;
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

import static io.protone.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 18/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmContactTaskCommentResourceImplTest {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Inject
    private CrmTaskCommentRepository crmTaskCommentRepository;

    @Inject
    private CrmTaskCommentMapper crmTaskCommentMapper;

    @Inject
    private CrmContactService crmContactService;

    @Inject
    private CrmTaskRepository crmTaskRepository;

    @Inject
    private CrmTaskMapper crmTaskMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    @Inject
    private CrmContactRepository crmContactRepository;

    private CrmTask crmTask;

    private CorNetwork corNetwork;

    private CrmContact crmContact;

    private MockMvc restCrmTaskCommentMockMvc;

    private CrmTaskComment crmTaskComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CrmContactTaskCommentResourceImpl crmTaskCommentResource = new CrmContactTaskCommentResourceImpl();
        ReflectionTestUtils.setField(crmTaskCommentResource, "crmContactService", crmContactService);
        ReflectionTestUtils.setField(crmTaskCommentResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(crmTaskCommentResource, "crmTaskCommentMapper", crmTaskCommentMapper);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

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
        crmContact = CrmContactResourceImplTest.createEntity(em).network(corNetwork);
        crmTaskComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrmTaskComment() throws Exception {
        crmContactRepository.deleteAll();
        crmContactRepository.save(crmContact.network(corNetwork));

        int databaseSizeBeforeCreate = crmTaskCommentRepository.findAll().size();

        // Create the CrmTaskComment
        CrmTaskCommentDTO crmTaskCommentDTO = crmTaskCommentMapper.DB2DTO(crmTaskComment);

        restCrmTaskCommentMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{taskId}/comment")
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
        crmContactRepository.deleteAll();
        crmContactRepository.save(crmContact.network(corNetwork));
        int databaseSizeBeforeCreate = crmTaskCommentRepository.findAll().size();

        // Create the CrmTaskComment with an existing ID
        CrmTaskComment existingCrmTaskComment = new CrmTaskComment();
        existingCrmTaskComment.setId(1L);
        CrmTaskCommentDTO existingCrmTaskCommentDTO = crmTaskCommentMapper.DB2DTO(existingCrmTaskComment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmTaskCommentMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{taskId}/comment")
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
        crmContactRepository.deleteAll();
        crmContactRepository.save(crmContact.network(corNetwork));

        // Initialize the database
        crmTaskCommentRepository.saveAndFlush(crmTaskComment);

        // Get all the crmTaskCommentList
        restCrmTaskCommentMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{taskId}/comment?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmTaskComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getCrmTaskComment() throws Exception {
        crmContactRepository.deleteAll();
        crmContactRepository.save(crmContact.network(corNetwork));

        // Initialize the database
        crmTaskCommentRepository.saveAndFlush(crmTaskComment);

        // Get the crmTaskComment
        restCrmTaskCommentMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{taskId}/comment/{id}", crmTaskComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmTaskComment.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrmTaskComment() throws Exception {
        // Get the crmTaskComment
        restCrmTaskCommentMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{taskId}/comment/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmTaskComment() throws Exception {
        crmContactRepository.deleteAll();
        crmContactRepository.save(crmContact.network(corNetwork));

        // Initialize the database
        crmTaskCommentRepository.saveAndFlush(crmTaskComment);
        int databaseSizeBeforeUpdate = crmTaskCommentRepository.findAll().size();

        // Update the crmTaskComment
        CrmTaskComment updatedCrmTaskComment = crmTaskCommentRepository.findOne(crmTaskComment.getId());
        updatedCrmTaskComment
            .comment(UPDATED_COMMENT);
        CrmTaskCommentDTO crmTaskCommentDTO = crmTaskCommentMapper.DB2DTO(updatedCrmTaskComment);

        restCrmTaskCommentMockMvc.perform(put("/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{taskId}/comment")
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
        crmContactRepository.deleteAll();
        crmContactRepository.save(crmContact.network(corNetwork));

        int databaseSizeBeforeUpdate = crmTaskCommentRepository.findAll().size();

        // Create the CrmTaskComment
        CrmTaskCommentDTO crmTaskCommentDTO = crmTaskCommentMapper.DB2DTO(crmTaskComment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmTaskCommentMockMvc.perform(put("/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{taskId}/comment")
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
        crmContactRepository.deleteAll();
        crmContactRepository.save(crmContact.network(corNetwork));

        // Initialize the database
        crmTaskCommentRepository.saveAndFlush(crmTaskComment);
        int databaseSizeBeforeDelete = crmTaskCommentRepository.findAll().size();

        // Get the crmTaskComment
        restCrmTaskCommentMockMvc.perform(delete("/api/v1/network/{networkShortcut}/crm/contact/{shortName}/task/{taskId}/comment/{id}", crmTaskComment.getId())
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
