package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LIBAlbum;
import io.protone.repository.LIBAlbumRepository;
import io.protone.service.dto.LIBAlbumDTO;
import io.protone.service.mapper.LIBAlbumMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.protone.domain.enumeration.LIBAlbumTypeEnum;
/**
 * Test class for the LIBAlbumResource REST controller.
 *
 * @see LIBAlbumResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LIBAlbumResourceIntTest {

    private static final LIBAlbumTypeEnum DEFAULT_ALBUM_TYPE = LIBAlbumTypeEnum.AT_ALBUM;
    private static final LIBAlbumTypeEnum UPDATED_ALBUM_TYPE = LIBAlbumTypeEnum.AT_SINGLE;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RELEASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RELEASE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private LIBAlbumRepository lIBAlbumRepository;

    @Inject
    private LIBAlbumMapper lIBAlbumMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLIBAlbumMockMvc;

    private LIBAlbum lIBAlbum;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LIBAlbumResource lIBAlbumResource = new LIBAlbumResource();
        ReflectionTestUtils.setField(lIBAlbumResource, "lIBAlbumRepository", lIBAlbumRepository);
        ReflectionTestUtils.setField(lIBAlbumResource, "lIBAlbumMapper", lIBAlbumMapper);
        this.restLIBAlbumMockMvc = MockMvcBuilders.standaloneSetup(lIBAlbumResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LIBAlbum createEntity(EntityManager em) {
        LIBAlbum lIBAlbum = new LIBAlbum()
                .albumType(DEFAULT_ALBUM_TYPE)
                .name(DEFAULT_NAME)
                .releaseDate(DEFAULT_RELEASE_DATE)
                .description(DEFAULT_DESCRIPTION);
        return lIBAlbum;
    }

    @Before
    public void initTest() {
        lIBAlbum = createEntity(em);
    }

    @Test
    @Transactional
    public void createLIBAlbum() throws Exception {
        int databaseSizeBeforeCreate = lIBAlbumRepository.findAll().size();

        // Create the LIBAlbum
        LIBAlbumDTO lIBAlbumDTO = lIBAlbumMapper.lIBAlbumToLIBAlbumDTO(lIBAlbum);

        restLIBAlbumMockMvc.perform(post("/api/l-ib-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBAlbumDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBAlbum in the database
        List<LIBAlbum> lIBAlbumList = lIBAlbumRepository.findAll();
        assertThat(lIBAlbumList).hasSize(databaseSizeBeforeCreate + 1);
        LIBAlbum testLIBAlbum = lIBAlbumList.get(lIBAlbumList.size() - 1);
        assertThat(testLIBAlbum.getAlbumType()).isEqualTo(DEFAULT_ALBUM_TYPE);
        assertThat(testLIBAlbum.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLIBAlbum.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
        assertThat(testLIBAlbum.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLIBAlbumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lIBAlbumRepository.findAll().size();

        // Create the LIBAlbum with an existing ID
        LIBAlbum existingLIBAlbum = new LIBAlbum();
        existingLIBAlbum.setId(1L);
        LIBAlbumDTO existingLIBAlbumDTO = lIBAlbumMapper.lIBAlbumToLIBAlbumDTO(existingLIBAlbum);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLIBAlbumMockMvc.perform(post("/api/l-ib-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLIBAlbumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LIBAlbum> lIBAlbumList = lIBAlbumRepository.findAll();
        assertThat(lIBAlbumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAlbumTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBAlbumRepository.findAll().size();
        // set the field null
        lIBAlbum.setAlbumType(null);

        // Create the LIBAlbum, which fails.
        LIBAlbumDTO lIBAlbumDTO = lIBAlbumMapper.lIBAlbumToLIBAlbumDTO(lIBAlbum);

        restLIBAlbumMockMvc.perform(post("/api/l-ib-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBAlbumDTO)))
            .andExpect(status().isBadRequest());

        List<LIBAlbum> lIBAlbumList = lIBAlbumRepository.findAll();
        assertThat(lIBAlbumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBAlbumRepository.findAll().size();
        // set the field null
        lIBAlbum.setName(null);

        // Create the LIBAlbum, which fails.
        LIBAlbumDTO lIBAlbumDTO = lIBAlbumMapper.lIBAlbumToLIBAlbumDTO(lIBAlbum);

        restLIBAlbumMockMvc.perform(post("/api/l-ib-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBAlbumDTO)))
            .andExpect(status().isBadRequest());

        List<LIBAlbum> lIBAlbumList = lIBAlbumRepository.findAll();
        assertThat(lIBAlbumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLIBAlbums() throws Exception {
        // Initialize the database
        lIBAlbumRepository.saveAndFlush(lIBAlbum);

        // Get all the lIBAlbumList
        restLIBAlbumMockMvc.perform(get("/api/l-ib-albums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lIBAlbum.getId().intValue())))
            .andExpect(jsonPath("$.[*].albumType").value(hasItem(DEFAULT_ALBUM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(DEFAULT_RELEASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLIBAlbum() throws Exception {
        // Initialize the database
        lIBAlbumRepository.saveAndFlush(lIBAlbum);

        // Get the lIBAlbum
        restLIBAlbumMockMvc.perform(get("/api/l-ib-albums/{id}", lIBAlbum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lIBAlbum.getId().intValue()))
            .andExpect(jsonPath("$.albumType").value(DEFAULT_ALBUM_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.releaseDate").value(DEFAULT_RELEASE_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLIBAlbum() throws Exception {
        // Get the lIBAlbum
        restLIBAlbumMockMvc.perform(get("/api/l-ib-albums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLIBAlbum() throws Exception {
        // Initialize the database
        lIBAlbumRepository.saveAndFlush(lIBAlbum);
        int databaseSizeBeforeUpdate = lIBAlbumRepository.findAll().size();

        // Update the lIBAlbum
        LIBAlbum updatedLIBAlbum = lIBAlbumRepository.findOne(lIBAlbum.getId());
        updatedLIBAlbum
                .albumType(UPDATED_ALBUM_TYPE)
                .name(UPDATED_NAME)
                .releaseDate(UPDATED_RELEASE_DATE)
                .description(UPDATED_DESCRIPTION);
        LIBAlbumDTO lIBAlbumDTO = lIBAlbumMapper.lIBAlbumToLIBAlbumDTO(updatedLIBAlbum);

        restLIBAlbumMockMvc.perform(put("/api/l-ib-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBAlbumDTO)))
            .andExpect(status().isOk());

        // Validate the LIBAlbum in the database
        List<LIBAlbum> lIBAlbumList = lIBAlbumRepository.findAll();
        assertThat(lIBAlbumList).hasSize(databaseSizeBeforeUpdate);
        LIBAlbum testLIBAlbum = lIBAlbumList.get(lIBAlbumList.size() - 1);
        assertThat(testLIBAlbum.getAlbumType()).isEqualTo(UPDATED_ALBUM_TYPE);
        assertThat(testLIBAlbum.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLIBAlbum.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testLIBAlbum.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLIBAlbum() throws Exception {
        int databaseSizeBeforeUpdate = lIBAlbumRepository.findAll().size();

        // Create the LIBAlbum
        LIBAlbumDTO lIBAlbumDTO = lIBAlbumMapper.lIBAlbumToLIBAlbumDTO(lIBAlbum);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLIBAlbumMockMvc.perform(put("/api/l-ib-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBAlbumDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBAlbum in the database
        List<LIBAlbum> lIBAlbumList = lIBAlbumRepository.findAll();
        assertThat(lIBAlbumList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLIBAlbum() throws Exception {
        // Initialize the database
        lIBAlbumRepository.saveAndFlush(lIBAlbum);
        int databaseSizeBeforeDelete = lIBAlbumRepository.findAll().size();

        // Get the lIBAlbum
        restLIBAlbumMockMvc.perform(delete("/api/l-ib-albums/{id}", lIBAlbum.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LIBAlbum> lIBAlbumList = lIBAlbumRepository.findAll();
        assertThat(lIBAlbumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
