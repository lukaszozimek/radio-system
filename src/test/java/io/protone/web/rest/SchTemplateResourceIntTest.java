package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.SchTemplate;
import io.protone.repository.SchTemplateRepository;
import io.protone.service.dto.SchTemplateDTO;
import io.protone.service.mapper.SchTemplateMapper;

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
 * Test class for the SchTemplateResource REST controller.
 *
 * @see SchTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchTemplateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SchTemplateRepository schTemplateRepository;

    @Autowired
    private SchTemplateMapper schTemplateMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restSchTemplateMockMvc;

    private SchTemplate schTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            SchTemplateResource schTemplateResource = new SchTemplateResource(schTemplateRepository, schTemplateMapper);
        this.restSchTemplateMockMvc = MockMvcBuilders.standaloneSetup(schTemplateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchTemplate createEntity(EntityManager em) {
        SchTemplate schTemplate = new SchTemplate()
                .name(DEFAULT_NAME);
        return schTemplate;
    }

    @Before
    public void initTest() {
        schTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchTemplate() throws Exception {
        int databaseSizeBeforeCreate = schTemplateRepository.findAll().size();

        // Create the SchTemplate
        SchTemplateDTO schTemplateDTO = schTemplateMapper.schTemplateToSchTemplateDTO(schTemplate);

        restSchTemplateMockMvc.perform(post("/api/sch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the SchTemplate in the database
        List<SchTemplate> schTemplateList = schTemplateRepository.findAll();
        assertThat(schTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        SchTemplate testSchTemplate = schTemplateList.get(schTemplateList.size() - 1);
        assertThat(testSchTemplate.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSchTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schTemplateRepository.findAll().size();

        // Create the SchTemplate with an existing ID
        SchTemplate existingSchTemplate = new SchTemplate();
        existingSchTemplate.setId(1L);
        SchTemplateDTO existingSchTemplateDTO = schTemplateMapper.schTemplateToSchTemplateDTO(existingSchTemplate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchTemplateMockMvc.perform(post("/api/sch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSchTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchTemplate> schTemplateList = schTemplateRepository.findAll();
        assertThat(schTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schTemplateRepository.findAll().size();
        // set the field null
        schTemplate.setName(null);

        // Create the SchTemplate, which fails.
        SchTemplateDTO schTemplateDTO = schTemplateMapper.schTemplateToSchTemplateDTO(schTemplate);

        restSchTemplateMockMvc.perform(post("/api/sch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<SchTemplate> schTemplateList = schTemplateRepository.findAll();
        assertThat(schTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchTemplates() throws Exception {
        // Initialize the database
        schTemplateRepository.saveAndFlush(schTemplate);

        // Get all the schTemplateList
        restSchTemplateMockMvc.perform(get("/api/sch-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSchTemplate() throws Exception {
        // Initialize the database
        schTemplateRepository.saveAndFlush(schTemplate);

        // Get the schTemplate
        restSchTemplateMockMvc.perform(get("/api/sch-templates/{id}", schTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchTemplate() throws Exception {
        // Get the schTemplate
        restSchTemplateMockMvc.perform(get("/api/sch-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchTemplate() throws Exception {
        // Initialize the database
        schTemplateRepository.saveAndFlush(schTemplate);
        int databaseSizeBeforeUpdate = schTemplateRepository.findAll().size();

        // Update the schTemplate
        SchTemplate updatedSchTemplate = schTemplateRepository.findOne(schTemplate.getId());
        updatedSchTemplate
                .name(UPDATED_NAME);
        SchTemplateDTO schTemplateDTO = schTemplateMapper.schTemplateToSchTemplateDTO(updatedSchTemplate);

        restSchTemplateMockMvc.perform(put("/api/sch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schTemplateDTO)))
            .andExpect(status().isOk());

        // Validate the SchTemplate in the database
        List<SchTemplate> schTemplateList = schTemplateRepository.findAll();
        assertThat(schTemplateList).hasSize(databaseSizeBeforeUpdate);
        SchTemplate testSchTemplate = schTemplateList.get(schTemplateList.size() - 1);
        assertThat(testSchTemplate.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSchTemplate() throws Exception {
        int databaseSizeBeforeUpdate = schTemplateRepository.findAll().size();

        // Create the SchTemplate
        SchTemplateDTO schTemplateDTO = schTemplateMapper.schTemplateToSchTemplateDTO(schTemplate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchTemplateMockMvc.perform(put("/api/sch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the SchTemplate in the database
        List<SchTemplate> schTemplateList = schTemplateRepository.findAll();
        assertThat(schTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchTemplate() throws Exception {
        // Initialize the database
        schTemplateRepository.saveAndFlush(schTemplate);
        int databaseSizeBeforeDelete = schTemplateRepository.findAll().size();

        // Get the schTemplate
        restSchTemplateMockMvc.perform(delete("/api/sch-templates/{id}", schTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SchTemplate> schTemplateList = schTemplateRepository.findAll();
        assertThat(schTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchTemplate.class);
    }
}
