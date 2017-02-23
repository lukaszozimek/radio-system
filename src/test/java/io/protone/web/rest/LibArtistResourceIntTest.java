package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LibArtist;
import io.protone.repository.LibArtistRepository;
import io.protone.service.dto.LibArtistDTO;
import io.protone.service.mapper.LibArtistMapper;

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

import io.protone.domain.enumeration.LibArtistTypeEnum;
/**
 * Test class for the LibArtistResource REST controller.
 *
 * @see LibArtistResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibArtistResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LibArtistTypeEnum DEFAULT_TYPE = LibArtistTypeEnum.AT_PERSON;
    private static final LibArtistTypeEnum UPDATED_TYPE = LibArtistTypeEnum.AT_BAND;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private LibArtistRepository libArtistRepository;

    @Autowired
    private LibArtistMapper libArtistMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLibArtistMockMvc;

    private LibArtist libArtist;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LibArtistResource libArtistResource = new LibArtistResource(libArtistRepository, libArtistMapper);
        this.restLibArtistMockMvc = MockMvcBuilders.standaloneSetup(libArtistResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibArtist createEntity(EntityManager em) {
        LibArtist libArtist = new LibArtist()
                .name(DEFAULT_NAME)
                .type(DEFAULT_TYPE)
                .description(DEFAULT_DESCRIPTION);
        return libArtist;
    }

    @Before
    public void initTest() {
        libArtist = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibArtist() throws Exception {
        int databaseSizeBeforeCreate = libArtistRepository.findAll().size();

        // Create the LibArtist
        LibArtistDTO libArtistDTO = libArtistMapper.libArtistToLibArtistDTO(libArtist);

        restLibArtistMockMvc.perform(post("/api/lib-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libArtistDTO)))
            .andExpect(status().isCreated());

        // Validate the LibArtist in the database
        List<LibArtist> libArtistList = libArtistRepository.findAll();
        assertThat(libArtistList).hasSize(databaseSizeBeforeCreate + 1);
        LibArtist testLibArtist = libArtistList.get(libArtistList.size() - 1);
        assertThat(testLibArtist.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibArtist.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testLibArtist.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLibArtistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libArtistRepository.findAll().size();

        // Create the LibArtist with an existing ID
        LibArtist existingLibArtist = new LibArtist();
        existingLibArtist.setId(1L);
        LibArtistDTO existingLibArtistDTO = libArtistMapper.libArtistToLibArtistDTO(existingLibArtist);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibArtistMockMvc.perform(post("/api/lib-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLibArtistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibArtist> libArtistList = libArtistRepository.findAll();
        assertThat(libArtistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libArtistRepository.findAll().size();
        // set the field null
        libArtist.setName(null);

        // Create the LibArtist, which fails.
        LibArtistDTO libArtistDTO = libArtistMapper.libArtistToLibArtistDTO(libArtist);

        restLibArtistMockMvc.perform(post("/api/lib-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libArtistDTO)))
            .andExpect(status().isBadRequest());

        List<LibArtist> libArtistList = libArtistRepository.findAll();
        assertThat(libArtistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibArtists() throws Exception {
        // Initialize the database
        libArtistRepository.saveAndFlush(libArtist);

        // Get all the libArtistList
        restLibArtistMockMvc.perform(get("/api/lib-artists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libArtist.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLibArtist() throws Exception {
        // Initialize the database
        libArtistRepository.saveAndFlush(libArtist);

        // Get the libArtist
        restLibArtistMockMvc.perform(get("/api/lib-artists/{id}", libArtist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libArtist.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibArtist() throws Exception {
        // Get the libArtist
        restLibArtistMockMvc.perform(get("/api/lib-artists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibArtist() throws Exception {
        // Initialize the database
        libArtistRepository.saveAndFlush(libArtist);
        int databaseSizeBeforeUpdate = libArtistRepository.findAll().size();

        // Update the libArtist
        LibArtist updatedLibArtist = libArtistRepository.findOne(libArtist.getId());
        updatedLibArtist
                .name(UPDATED_NAME)
                .type(UPDATED_TYPE)
                .description(UPDATED_DESCRIPTION);
        LibArtistDTO libArtistDTO = libArtistMapper.libArtistToLibArtistDTO(updatedLibArtist);

        restLibArtistMockMvc.perform(put("/api/lib-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libArtistDTO)))
            .andExpect(status().isOk());

        // Validate the LibArtist in the database
        List<LibArtist> libArtistList = libArtistRepository.findAll();
        assertThat(libArtistList).hasSize(databaseSizeBeforeUpdate);
        LibArtist testLibArtist = libArtistList.get(libArtistList.size() - 1);
        assertThat(testLibArtist.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibArtist.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLibArtist.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLibArtist() throws Exception {
        int databaseSizeBeforeUpdate = libArtistRepository.findAll().size();

        // Create the LibArtist
        LibArtistDTO libArtistDTO = libArtistMapper.libArtistToLibArtistDTO(libArtist);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibArtistMockMvc.perform(put("/api/lib-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libArtistDTO)))
            .andExpect(status().isCreated());

        // Validate the LibArtist in the database
        List<LibArtist> libArtistList = libArtistRepository.findAll();
        assertThat(libArtistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibArtist() throws Exception {
        // Initialize the database
        libArtistRepository.saveAndFlush(libArtist);
        int databaseSizeBeforeDelete = libArtistRepository.findAll().size();

        // Get the libArtist
        restLibArtistMockMvc.perform(delete("/api/lib-artists/{id}", libArtist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibArtist> libArtistList = libArtistRepository.findAll();
        assertThat(libArtistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibArtist.class);
    }
}
