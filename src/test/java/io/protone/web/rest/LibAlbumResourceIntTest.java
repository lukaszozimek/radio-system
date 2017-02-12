package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LibAlbum;
import io.protone.repository.LibAlbumRepository;
import io.protone.service.dto.LibAlbumDTO;
import io.protone.service.mapper.LibAlbumMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.protone.domain.enumeration.LibAlbumTypeEnum;
/**
 * Test class for the LibAlbumResource REST controller.
 *
 * @see LibAlbumResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibAlbumResourceIntTest {

    private static final LibAlbumTypeEnum DEFAULT_ALBUM_TYPE = LibAlbumTypeEnum.AT_ALBUM;
    private static final LibAlbumTypeEnum UPDATED_ALBUM_TYPE = LibAlbumTypeEnum.AT_SINGLE;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RELEASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RELEASE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private LibAlbumRepository libAlbumRepository;

    @Autowired
    private LibAlbumMapper libAlbumMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLibAlbumMockMvc;

    private LibAlbum libAlbum;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LibAlbumResource libAlbumResource = new LibAlbumResource(libAlbumRepository, libAlbumMapper);
        this.restLibAlbumMockMvc = MockMvcBuilders.standaloneSetup(libAlbumResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibAlbum createEntity(EntityManager em) {
        LibAlbum libAlbum = new LibAlbum()
                .albumType(DEFAULT_ALBUM_TYPE)
                .name(DEFAULT_NAME)
                .releaseDate(DEFAULT_RELEASE_DATE)
                .description(DEFAULT_DESCRIPTION);
        return libAlbum;
    }

    @Before
    public void initTest() {
        libAlbum = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibAlbum() throws Exception {
        int databaseSizeBeforeCreate = libAlbumRepository.findAll().size();

        // Create the LibAlbum
        LibAlbumDTO libAlbumDTO = libAlbumMapper.libAlbumToLibAlbumDTO(libAlbum);

        restLibAlbumMockMvc.perform(post("/api/lib-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libAlbumDTO)))
            .andExpect(status().isCreated());

        // Validate the LibAlbum in the database
        List<LibAlbum> libAlbumList = libAlbumRepository.findAll();
        assertThat(libAlbumList).hasSize(databaseSizeBeforeCreate + 1);
        LibAlbum testLibAlbum = libAlbumList.get(libAlbumList.size() - 1);
        assertThat(testLibAlbum.getAlbumType()).isEqualTo(DEFAULT_ALBUM_TYPE);
        assertThat(testLibAlbum.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibAlbum.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
        assertThat(testLibAlbum.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLibAlbumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libAlbumRepository.findAll().size();

        // Create the LibAlbum with an existing ID
        LibAlbum existingLibAlbum = new LibAlbum();
        existingLibAlbum.setId(1L);
        LibAlbumDTO existingLibAlbumDTO = libAlbumMapper.libAlbumToLibAlbumDTO(existingLibAlbum);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibAlbumMockMvc.perform(post("/api/lib-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLibAlbumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibAlbum> libAlbumList = libAlbumRepository.findAll();
        assertThat(libAlbumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAlbumTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = libAlbumRepository.findAll().size();
        // set the field null
        libAlbum.setAlbumType(null);

        // Create the LibAlbum, which fails.
        LibAlbumDTO libAlbumDTO = libAlbumMapper.libAlbumToLibAlbumDTO(libAlbum);

        restLibAlbumMockMvc.perform(post("/api/lib-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libAlbumDTO)))
            .andExpect(status().isBadRequest());

        List<LibAlbum> libAlbumList = libAlbumRepository.findAll();
        assertThat(libAlbumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libAlbumRepository.findAll().size();
        // set the field null
        libAlbum.setName(null);

        // Create the LibAlbum, which fails.
        LibAlbumDTO libAlbumDTO = libAlbumMapper.libAlbumToLibAlbumDTO(libAlbum);

        restLibAlbumMockMvc.perform(post("/api/lib-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libAlbumDTO)))
            .andExpect(status().isBadRequest());

        List<LibAlbum> libAlbumList = libAlbumRepository.findAll();
        assertThat(libAlbumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibAlbums() throws Exception {
        // Initialize the database
        libAlbumRepository.saveAndFlush(libAlbum);

        // Get all the libAlbumList
        restLibAlbumMockMvc.perform(get("/api/lib-albums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libAlbum.getId().intValue())))
            .andExpect(jsonPath("$.[*].albumType").value(hasItem(DEFAULT_ALBUM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(DEFAULT_RELEASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLibAlbum() throws Exception {
        // Initialize the database
        libAlbumRepository.saveAndFlush(libAlbum);

        // Get the libAlbum
        restLibAlbumMockMvc.perform(get("/api/lib-albums/{id}", libAlbum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libAlbum.getId().intValue()))
            .andExpect(jsonPath("$.albumType").value(DEFAULT_ALBUM_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.releaseDate").value(DEFAULT_RELEASE_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibAlbum() throws Exception {
        // Get the libAlbum
        restLibAlbumMockMvc.perform(get("/api/lib-albums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibAlbum() throws Exception {
        // Initialize the database
        libAlbumRepository.saveAndFlush(libAlbum);
        int databaseSizeBeforeUpdate = libAlbumRepository.findAll().size();

        // Update the libAlbum
        LibAlbum updatedLibAlbum = libAlbumRepository.findOne(libAlbum.getId());
        updatedLibAlbum
                .albumType(UPDATED_ALBUM_TYPE)
                .name(UPDATED_NAME)
                .releaseDate(UPDATED_RELEASE_DATE)
                .description(UPDATED_DESCRIPTION);
        LibAlbumDTO libAlbumDTO = libAlbumMapper.libAlbumToLibAlbumDTO(updatedLibAlbum);

        restLibAlbumMockMvc.perform(put("/api/lib-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libAlbumDTO)))
            .andExpect(status().isOk());

        // Validate the LibAlbum in the database
        List<LibAlbum> libAlbumList = libAlbumRepository.findAll();
        assertThat(libAlbumList).hasSize(databaseSizeBeforeUpdate);
        LibAlbum testLibAlbum = libAlbumList.get(libAlbumList.size() - 1);
        assertThat(testLibAlbum.getAlbumType()).isEqualTo(UPDATED_ALBUM_TYPE);
        assertThat(testLibAlbum.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibAlbum.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testLibAlbum.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLibAlbum() throws Exception {
        int databaseSizeBeforeUpdate = libAlbumRepository.findAll().size();

        // Create the LibAlbum
        LibAlbumDTO libAlbumDTO = libAlbumMapper.libAlbumToLibAlbumDTO(libAlbum);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibAlbumMockMvc.perform(put("/api/lib-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libAlbumDTO)))
            .andExpect(status().isCreated());

        // Validate the LibAlbum in the database
        List<LibAlbum> libAlbumList = libAlbumRepository.findAll();
        assertThat(libAlbumList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibAlbum() throws Exception {
        // Initialize the database
        libAlbumRepository.saveAndFlush(libAlbum);
        int databaseSizeBeforeDelete = libAlbumRepository.findAll().size();

        // Get the libAlbum
        restLibAlbumMockMvc.perform(delete("/api/lib-albums/{id}", libAlbum.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibAlbum> libAlbumList = libAlbumRepository.findAll();
        assertThat(libAlbumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibAlbum.class);
    }
}
