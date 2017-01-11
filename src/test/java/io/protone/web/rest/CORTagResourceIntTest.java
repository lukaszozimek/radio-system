package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CORTag;
import io.protone.repository.CORTagRepository;
import io.protone.service.dto.CORTagDTO;
import io.protone.service.mapper.CORTagMapper;

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
 * Test class for the CORTagResource REST controller.
 *
 * @see CORTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CORTagResourceIntTest {

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    @Inject
    private CORTagRepository cORTagRepository;

    @Inject
    private CORTagMapper cORTagMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCORTagMockMvc;

    private CORTag cORTag;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CORTagResource cORTagResource = new CORTagResource();
        ReflectionTestUtils.setField(cORTagResource, "cORTagRepository", cORTagRepository);
        ReflectionTestUtils.setField(cORTagResource, "cORTagMapper", cORTagMapper);
        this.restCORTagMockMvc = MockMvcBuilders.standaloneSetup(cORTagResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CORTag createEntity(EntityManager em) {
        CORTag cORTag = new CORTag()
                .tag(DEFAULT_TAG);
        return cORTag;
    }

    @Before
    public void initTest() {
        cORTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createCORTag() throws Exception {
        int databaseSizeBeforeCreate = cORTagRepository.findAll().size();

        // Create the CORTag
        CORTagDTO cORTagDTO = cORTagMapper.cORTagToCORTagDTO(cORTag);

        restCORTagMockMvc.perform(post("/api/c-or-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORTagDTO)))
            .andExpect(status().isCreated());

        // Validate the CORTag in the database
        List<CORTag> cORTagList = cORTagRepository.findAll();
        assertThat(cORTagList).hasSize(databaseSizeBeforeCreate + 1);
        CORTag testCORTag = cORTagList.get(cORTagList.size() - 1);
        assertThat(testCORTag.getTag()).isEqualTo(DEFAULT_TAG);
    }

    @Test
    @Transactional
    public void createCORTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cORTagRepository.findAll().size();

        // Create the CORTag with an existing ID
        CORTag existingCORTag = new CORTag();
        existingCORTag.setId(1L);
        CORTagDTO existingCORTagDTO = cORTagMapper.cORTagToCORTagDTO(existingCORTag);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCORTagMockMvc.perform(post("/api/c-or-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCORTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CORTag> cORTagList = cORTagRepository.findAll();
        assertThat(cORTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTagIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORTagRepository.findAll().size();
        // set the field null
        cORTag.setTag(null);

        // Create the CORTag, which fails.
        CORTagDTO cORTagDTO = cORTagMapper.cORTagToCORTagDTO(cORTag);

        restCORTagMockMvc.perform(post("/api/c-or-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORTagDTO)))
            .andExpect(status().isBadRequest());

        List<CORTag> cORTagList = cORTagRepository.findAll();
        assertThat(cORTagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCORTags() throws Exception {
        // Initialize the database
        cORTagRepository.saveAndFlush(cORTag);

        // Get all the cORTagList
        restCORTagMockMvc.perform(get("/api/c-or-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cORTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())));
    }

    @Test
    @Transactional
    public void getCORTag() throws Exception {
        // Initialize the database
        cORTagRepository.saveAndFlush(cORTag);

        // Get the cORTag
        restCORTagMockMvc.perform(get("/api/c-or-tags/{id}", cORTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cORTag.getId().intValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCORTag() throws Exception {
        // Get the cORTag
        restCORTagMockMvc.perform(get("/api/c-or-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCORTag() throws Exception {
        // Initialize the database
        cORTagRepository.saveAndFlush(cORTag);
        int databaseSizeBeforeUpdate = cORTagRepository.findAll().size();

        // Update the cORTag
        CORTag updatedCORTag = cORTagRepository.findOne(cORTag.getId());
        updatedCORTag
                .tag(UPDATED_TAG);
        CORTagDTO cORTagDTO = cORTagMapper.cORTagToCORTagDTO(updatedCORTag);

        restCORTagMockMvc.perform(put("/api/c-or-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORTagDTO)))
            .andExpect(status().isOk());

        // Validate the CORTag in the database
        List<CORTag> cORTagList = cORTagRepository.findAll();
        assertThat(cORTagList).hasSize(databaseSizeBeforeUpdate);
        CORTag testCORTag = cORTagList.get(cORTagList.size() - 1);
        assertThat(testCORTag.getTag()).isEqualTo(UPDATED_TAG);
    }

    @Test
    @Transactional
    public void updateNonExistingCORTag() throws Exception {
        int databaseSizeBeforeUpdate = cORTagRepository.findAll().size();

        // Create the CORTag
        CORTagDTO cORTagDTO = cORTagMapper.cORTagToCORTagDTO(cORTag);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCORTagMockMvc.perform(put("/api/c-or-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORTagDTO)))
            .andExpect(status().isCreated());

        // Validate the CORTag in the database
        List<CORTag> cORTagList = cORTagRepository.findAll();
        assertThat(cORTagList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCORTag() throws Exception {
        // Initialize the database
        cORTagRepository.saveAndFlush(cORTag);
        int databaseSizeBeforeDelete = cORTagRepository.findAll().size();

        // Get the cORTag
        restCORTagMockMvc.perform(delete("/api/c-or-tags/{id}", cORTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CORTag> cORTagList = cORTagRepository.findAll();
        assertThat(cORTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
