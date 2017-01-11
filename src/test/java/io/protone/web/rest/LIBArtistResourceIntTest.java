package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LIBArtist;
import io.protone.repository.LIBArtistRepository;
import io.protone.service.dto.LIBArtistDTO;
import io.protone.service.mapper.LIBArtistMapper;

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

import io.protone.domain.enumeration.LIBArtistTypeEnum;
/**
 * Test class for the LIBArtistResource REST controller.
 *
 * @see LIBArtistResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LIBArtistResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LIBArtistTypeEnum DEFAULT_TYPE = LIBArtistTypeEnum.AT_PERSON;
    private static final LIBArtistTypeEnum UPDATED_TYPE = LIBArtistTypeEnum.AT_BAND;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private LIBArtistRepository lIBArtistRepository;

    @Inject
    private LIBArtistMapper lIBArtistMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLIBArtistMockMvc;

    private LIBArtist lIBArtist;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LIBArtistResource lIBArtistResource = new LIBArtistResource();
        ReflectionTestUtils.setField(lIBArtistResource, "lIBArtistRepository", lIBArtistRepository);
        ReflectionTestUtils.setField(lIBArtistResource, "lIBArtistMapper", lIBArtistMapper);
        this.restLIBArtistMockMvc = MockMvcBuilders.standaloneSetup(lIBArtistResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LIBArtist createEntity(EntityManager em) {
        LIBArtist lIBArtist = new LIBArtist()
                .name(DEFAULT_NAME)
                .type(DEFAULT_TYPE)
                .description(DEFAULT_DESCRIPTION);
        return lIBArtist;
    }

    @Before
    public void initTest() {
        lIBArtist = createEntity(em);
    }

    @Test
    @Transactional
    public void createLIBArtist() throws Exception {
        int databaseSizeBeforeCreate = lIBArtistRepository.findAll().size();

        // Create the LIBArtist
        LIBArtistDTO lIBArtistDTO = lIBArtistMapper.lIBArtistToLIBArtistDTO(lIBArtist);

        restLIBArtistMockMvc.perform(post("/api/l-ib-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBArtistDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBArtist in the database
        List<LIBArtist> lIBArtistList = lIBArtistRepository.findAll();
        assertThat(lIBArtistList).hasSize(databaseSizeBeforeCreate + 1);
        LIBArtist testLIBArtist = lIBArtistList.get(lIBArtistList.size() - 1);
        assertThat(testLIBArtist.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLIBArtist.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testLIBArtist.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLIBArtistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lIBArtistRepository.findAll().size();

        // Create the LIBArtist with an existing ID
        LIBArtist existingLIBArtist = new LIBArtist();
        existingLIBArtist.setId(1L);
        LIBArtistDTO existingLIBArtistDTO = lIBArtistMapper.lIBArtistToLIBArtistDTO(existingLIBArtist);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLIBArtistMockMvc.perform(post("/api/l-ib-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLIBArtistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LIBArtist> lIBArtistList = lIBArtistRepository.findAll();
        assertThat(lIBArtistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBArtistRepository.findAll().size();
        // set the field null
        lIBArtist.setName(null);

        // Create the LIBArtist, which fails.
        LIBArtistDTO lIBArtistDTO = lIBArtistMapper.lIBArtistToLIBArtistDTO(lIBArtist);

        restLIBArtistMockMvc.perform(post("/api/l-ib-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBArtistDTO)))
            .andExpect(status().isBadRequest());

        List<LIBArtist> lIBArtistList = lIBArtistRepository.findAll();
        assertThat(lIBArtistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLIBArtists() throws Exception {
        // Initialize the database
        lIBArtistRepository.saveAndFlush(lIBArtist);

        // Get all the lIBArtistList
        restLIBArtistMockMvc.perform(get("/api/l-ib-artists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lIBArtist.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLIBArtist() throws Exception {
        // Initialize the database
        lIBArtistRepository.saveAndFlush(lIBArtist);

        // Get the lIBArtist
        restLIBArtistMockMvc.perform(get("/api/l-ib-artists/{id}", lIBArtist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lIBArtist.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLIBArtist() throws Exception {
        // Get the lIBArtist
        restLIBArtistMockMvc.perform(get("/api/l-ib-artists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLIBArtist() throws Exception {
        // Initialize the database
        lIBArtistRepository.saveAndFlush(lIBArtist);
        int databaseSizeBeforeUpdate = lIBArtistRepository.findAll().size();

        // Update the lIBArtist
        LIBArtist updatedLIBArtist = lIBArtistRepository.findOne(lIBArtist.getId());
        updatedLIBArtist
                .name(UPDATED_NAME)
                .type(UPDATED_TYPE)
                .description(UPDATED_DESCRIPTION);
        LIBArtistDTO lIBArtistDTO = lIBArtistMapper.lIBArtistToLIBArtistDTO(updatedLIBArtist);

        restLIBArtistMockMvc.perform(put("/api/l-ib-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBArtistDTO)))
            .andExpect(status().isOk());

        // Validate the LIBArtist in the database
        List<LIBArtist> lIBArtistList = lIBArtistRepository.findAll();
        assertThat(lIBArtistList).hasSize(databaseSizeBeforeUpdate);
        LIBArtist testLIBArtist = lIBArtistList.get(lIBArtistList.size() - 1);
        assertThat(testLIBArtist.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLIBArtist.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLIBArtist.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLIBArtist() throws Exception {
        int databaseSizeBeforeUpdate = lIBArtistRepository.findAll().size();

        // Create the LIBArtist
        LIBArtistDTO lIBArtistDTO = lIBArtistMapper.lIBArtistToLIBArtistDTO(lIBArtist);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLIBArtistMockMvc.perform(put("/api/l-ib-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBArtistDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBArtist in the database
        List<LIBArtist> lIBArtistList = lIBArtistRepository.findAll();
        assertThat(lIBArtistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLIBArtist() throws Exception {
        // Initialize the database
        lIBArtistRepository.saveAndFlush(lIBArtist);
        int databaseSizeBeforeDelete = lIBArtistRepository.findAll().size();

        // Get the lIBArtist
        restLIBArtistMockMvc.perform(delete("/api/l-ib-artists/{id}", lIBArtist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LIBArtist> lIBArtistList = lIBArtistRepository.findAll();
        assertThat(lIBArtistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
