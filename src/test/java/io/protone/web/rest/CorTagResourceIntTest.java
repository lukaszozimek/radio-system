package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorTag;
import io.protone.repository.CorTagRepository;
import io.protone.service.dto.CorTagDTO;
import io.protone.service.mapper.CorTagMapper;

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
 * Test class for the CorTagResource REST controller.
 *
 * @see CorTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorTagResourceIntTest {

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    @Autowired
    private CorTagRepository corTagRepository;

    @Autowired
    private CorTagMapper corTagMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorTagMockMvc;

    private CorTag corTag;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorTagResource corTagResource = new CorTagResource(corTagRepository, corTagMapper);
        this.restCorTagMockMvc = MockMvcBuilders.standaloneSetup(corTagResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorTag createEntity(EntityManager em) {
        CorTag corTag = new CorTag()
                .tag(DEFAULT_TAG);
        return corTag;
    }

    @Before
    public void initTest() {
        corTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorTag() throws Exception {
        int databaseSizeBeforeCreate = corTagRepository.findAll().size();

        // Create the CorTag
        CorTagDTO corTagDTO = corTagMapper.corTagToCorTagDTO(corTag);

        restCorTagMockMvc.perform(post("/api/cor-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corTagDTO)))
            .andExpect(status().isCreated());

        // Validate the CorTag in the database
        List<CorTag> corTagList = corTagRepository.findAll();
        assertThat(corTagList).hasSize(databaseSizeBeforeCreate + 1);
        CorTag testCorTag = corTagList.get(corTagList.size() - 1);
        assertThat(testCorTag.getTag()).isEqualTo(DEFAULT_TAG);
    }

    @Test
    @Transactional
    public void createCorTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corTagRepository.findAll().size();

        // Create the CorTag with an existing ID
        CorTag existingCorTag = new CorTag();
        existingCorTag.setId(1L);
        CorTagDTO existingCorTagDTO = corTagMapper.corTagToCorTagDTO(existingCorTag);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorTagMockMvc.perform(post("/api/cor-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorTag> corTagList = corTagRepository.findAll();
        assertThat(corTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTagIsRequired() throws Exception {
        int databaseSizeBeforeTest = corTagRepository.findAll().size();
        // set the field null
        corTag.setTag(null);

        // Create the CorTag, which fails.
        CorTagDTO corTagDTO = corTagMapper.corTagToCorTagDTO(corTag);

        restCorTagMockMvc.perform(post("/api/cor-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corTagDTO)))
            .andExpect(status().isBadRequest());

        List<CorTag> corTagList = corTagRepository.findAll();
        assertThat(corTagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorTags() throws Exception {
        // Initialize the database
        corTagRepository.saveAndFlush(corTag);

        // Get all the corTagList
        restCorTagMockMvc.perform(get("/api/cor-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())));
    }

    @Test
    @Transactional
    public void getCorTag() throws Exception {
        // Initialize the database
        corTagRepository.saveAndFlush(corTag);

        // Get the corTag
        restCorTagMockMvc.perform(get("/api/cor-tags/{id}", corTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corTag.getId().intValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorTag() throws Exception {
        // Get the corTag
        restCorTagMockMvc.perform(get("/api/cor-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorTag() throws Exception {
        // Initialize the database
        corTagRepository.saveAndFlush(corTag);
        int databaseSizeBeforeUpdate = corTagRepository.findAll().size();

        // Update the corTag
        CorTag updatedCorTag = corTagRepository.findOne(corTag.getId());
        updatedCorTag
                .tag(UPDATED_TAG);
        CorTagDTO corTagDTO = corTagMapper.corTagToCorTagDTO(updatedCorTag);

        restCorTagMockMvc.perform(put("/api/cor-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corTagDTO)))
            .andExpect(status().isOk());

        // Validate the CorTag in the database
        List<CorTag> corTagList = corTagRepository.findAll();
        assertThat(corTagList).hasSize(databaseSizeBeforeUpdate);
        CorTag testCorTag = corTagList.get(corTagList.size() - 1);
        assertThat(testCorTag.getTag()).isEqualTo(UPDATED_TAG);
    }

    @Test
    @Transactional
    public void updateNonExistingCorTag() throws Exception {
        int databaseSizeBeforeUpdate = corTagRepository.findAll().size();

        // Create the CorTag
        CorTagDTO corTagDTO = corTagMapper.corTagToCorTagDTO(corTag);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorTagMockMvc.perform(put("/api/cor-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corTagDTO)))
            .andExpect(status().isCreated());

        // Validate the CorTag in the database
        List<CorTag> corTagList = corTagRepository.findAll();
        assertThat(corTagList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorTag() throws Exception {
        // Initialize the database
        corTagRepository.saveAndFlush(corTag);
        int databaseSizeBeforeDelete = corTagRepository.findAll().size();

        // Get the corTag
        restCorTagMockMvc.perform(delete("/api/cor-tags/{id}", corTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorTag> corTagList = corTagRepository.findAll();
        assertThat(corTagList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorTag.class);
    }
}
