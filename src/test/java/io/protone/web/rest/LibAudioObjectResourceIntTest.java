package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LibAudioObject;
import io.protone.repository.LibAudioObjectRepository;
import io.protone.service.dto.LibAudioObjectDTO;
import io.protone.service.mapper.LibAudioObjectMapper;

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

import io.protone.domain.enumeration.LibAudioQualityEnum;
/**
 * Test class for the LibAudioObjectResource REST controller.
 *
 * @see LibAudioObjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibAudioObjectResourceIntTest {

    private static final Long DEFAULT_LENGTH = 1L;
    private static final Long UPDATED_LENGTH = 2L;

    private static final Integer DEFAULT_BI_TRATE = 1;
    private static final Integer UPDATED_BI_TRATE = 2;

    private static final String DEFAULT_CODEC = "AAAAAAAAAA";
    private static final String UPDATED_CODEC = "BBBBBBBBBB";

    private static final LibAudioQualityEnum DEFAULT_QUALITY = LibAudioQualityEnum.AQ_ORIGINAL;
    private static final LibAudioQualityEnum UPDATED_QUALITY = LibAudioQualityEnum.AQ_LOSSLESS;

    @Autowired
    private LibAudioObjectRepository libAudioObjectRepository;

    @Autowired
    private LibAudioObjectMapper libAudioObjectMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLibAudioObjectMockMvc;

    private LibAudioObject libAudioObject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LibAudioObjectResource libAudioObjectResource = new LibAudioObjectResource(libAudioObjectRepository, libAudioObjectMapper);
        this.restLibAudioObjectMockMvc = MockMvcBuilders.standaloneSetup(libAudioObjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibAudioObject createEntity(EntityManager em) {
        LibAudioObject libAudioObject = new LibAudioObject()
                .length(DEFAULT_LENGTH)
                .biTrate(DEFAULT_BI_TRATE)
                .codec(DEFAULT_CODEC)
                .quality(DEFAULT_QUALITY);
        return libAudioObject;
    }

    @Before
    public void initTest() {
        libAudioObject = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibAudioObject() throws Exception {
        int databaseSizeBeforeCreate = libAudioObjectRepository.findAll().size();

        // Create the LibAudioObject
        LibAudioObjectDTO libAudioObjectDTO = libAudioObjectMapper.libAudioObjectToLibAudioObjectDTO(libAudioObject);

        restLibAudioObjectMockMvc.perform(post("/api/lib-audio-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libAudioObjectDTO)))
            .andExpect(status().isCreated());

        // Validate the LibAudioObject in the database
        List<LibAudioObject> libAudioObjectList = libAudioObjectRepository.findAll();
        assertThat(libAudioObjectList).hasSize(databaseSizeBeforeCreate + 1);
        LibAudioObject testLibAudioObject = libAudioObjectList.get(libAudioObjectList.size() - 1);
        assertThat(testLibAudioObject.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testLibAudioObject.getBiTrate()).isEqualTo(DEFAULT_BI_TRATE);
        assertThat(testLibAudioObject.getCodec()).isEqualTo(DEFAULT_CODEC);
        assertThat(testLibAudioObject.getQuality()).isEqualTo(DEFAULT_QUALITY);
    }

    @Test
    @Transactional
    public void createLibAudioObjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libAudioObjectRepository.findAll().size();

        // Create the LibAudioObject with an existing ID
        LibAudioObject existingLibAudioObject = new LibAudioObject();
        existingLibAudioObject.setId(1L);
        LibAudioObjectDTO existingLibAudioObjectDTO = libAudioObjectMapper.libAudioObjectToLibAudioObjectDTO(existingLibAudioObject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibAudioObjectMockMvc.perform(post("/api/lib-audio-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLibAudioObjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibAudioObject> libAudioObjectList = libAudioObjectRepository.findAll();
        assertThat(libAudioObjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = libAudioObjectRepository.findAll().size();
        // set the field null
        libAudioObject.setLength(null);

        // Create the LibAudioObject, which fails.
        LibAudioObjectDTO libAudioObjectDTO = libAudioObjectMapper.libAudioObjectToLibAudioObjectDTO(libAudioObject);

        restLibAudioObjectMockMvc.perform(post("/api/lib-audio-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libAudioObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LibAudioObject> libAudioObjectList = libAudioObjectRepository.findAll();
        assertThat(libAudioObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBiTrateIsRequired() throws Exception {
        int databaseSizeBeforeTest = libAudioObjectRepository.findAll().size();
        // set the field null
        libAudioObject.setBiTrate(null);

        // Create the LibAudioObject, which fails.
        LibAudioObjectDTO libAudioObjectDTO = libAudioObjectMapper.libAudioObjectToLibAudioObjectDTO(libAudioObject);

        restLibAudioObjectMockMvc.perform(post("/api/lib-audio-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libAudioObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LibAudioObject> libAudioObjectList = libAudioObjectRepository.findAll();
        assertThat(libAudioObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodecIsRequired() throws Exception {
        int databaseSizeBeforeTest = libAudioObjectRepository.findAll().size();
        // set the field null
        libAudioObject.setCodec(null);

        // Create the LibAudioObject, which fails.
        LibAudioObjectDTO libAudioObjectDTO = libAudioObjectMapper.libAudioObjectToLibAudioObjectDTO(libAudioObject);

        restLibAudioObjectMockMvc.perform(post("/api/lib-audio-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libAudioObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LibAudioObject> libAudioObjectList = libAudioObjectRepository.findAll();
        assertThat(libAudioObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibAudioObjects() throws Exception {
        // Initialize the database
        libAudioObjectRepository.saveAndFlush(libAudioObject);

        // Get all the libAudioObjectList
        restLibAudioObjectMockMvc.perform(get("/api/lib-audio-objects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libAudioObject.getId().intValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].biTrate").value(hasItem(DEFAULT_BI_TRATE)))
            .andExpect(jsonPath("$.[*].codec").value(hasItem(DEFAULT_CODEC.toString())))
            .andExpect(jsonPath("$.[*].quality").value(hasItem(DEFAULT_QUALITY.toString())));
    }

    @Test
    @Transactional
    public void getLibAudioObject() throws Exception {
        // Initialize the database
        libAudioObjectRepository.saveAndFlush(libAudioObject);

        // Get the libAudioObject
        restLibAudioObjectMockMvc.perform(get("/api/lib-audio-objects/{id}", libAudioObject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libAudioObject.getId().intValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.intValue()))
            .andExpect(jsonPath("$.biTrate").value(DEFAULT_BI_TRATE))
            .andExpect(jsonPath("$.codec").value(DEFAULT_CODEC.toString()))
            .andExpect(jsonPath("$.quality").value(DEFAULT_QUALITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibAudioObject() throws Exception {
        // Get the libAudioObject
        restLibAudioObjectMockMvc.perform(get("/api/lib-audio-objects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibAudioObject() throws Exception {
        // Initialize the database
        libAudioObjectRepository.saveAndFlush(libAudioObject);
        int databaseSizeBeforeUpdate = libAudioObjectRepository.findAll().size();

        // Update the libAudioObject
        LibAudioObject updatedLibAudioObject = libAudioObjectRepository.findOne(libAudioObject.getId());
        updatedLibAudioObject
                .length(UPDATED_LENGTH)
                .biTrate(UPDATED_BI_TRATE)
                .codec(UPDATED_CODEC)
                .quality(UPDATED_QUALITY);
        LibAudioObjectDTO libAudioObjectDTO = libAudioObjectMapper.libAudioObjectToLibAudioObjectDTO(updatedLibAudioObject);

        restLibAudioObjectMockMvc.perform(put("/api/lib-audio-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libAudioObjectDTO)))
            .andExpect(status().isOk());

        // Validate the LibAudioObject in the database
        List<LibAudioObject> libAudioObjectList = libAudioObjectRepository.findAll();
        assertThat(libAudioObjectList).hasSize(databaseSizeBeforeUpdate);
        LibAudioObject testLibAudioObject = libAudioObjectList.get(libAudioObjectList.size() - 1);
        assertThat(testLibAudioObject.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testLibAudioObject.getBiTrate()).isEqualTo(UPDATED_BI_TRATE);
        assertThat(testLibAudioObject.getCodec()).isEqualTo(UPDATED_CODEC);
        assertThat(testLibAudioObject.getQuality()).isEqualTo(UPDATED_QUALITY);
    }

    @Test
    @Transactional
    public void updateNonExistingLibAudioObject() throws Exception {
        int databaseSizeBeforeUpdate = libAudioObjectRepository.findAll().size();

        // Create the LibAudioObject
        LibAudioObjectDTO libAudioObjectDTO = libAudioObjectMapper.libAudioObjectToLibAudioObjectDTO(libAudioObject);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibAudioObjectMockMvc.perform(put("/api/lib-audio-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libAudioObjectDTO)))
            .andExpect(status().isCreated());

        // Validate the LibAudioObject in the database
        List<LibAudioObject> libAudioObjectList = libAudioObjectRepository.findAll();
        assertThat(libAudioObjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibAudioObject() throws Exception {
        // Initialize the database
        libAudioObjectRepository.saveAndFlush(libAudioObject);
        int databaseSizeBeforeDelete = libAudioObjectRepository.findAll().size();

        // Get the libAudioObject
        restLibAudioObjectMockMvc.perform(delete("/api/lib-audio-objects/{id}", libAudioObject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibAudioObject> libAudioObjectList = libAudioObjectRepository.findAll();
        assertThat(libAudioObjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibAudioObject.class);
    }
}
