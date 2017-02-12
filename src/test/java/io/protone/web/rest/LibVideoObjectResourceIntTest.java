package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LibVideoObject;
import io.protone.repository.LibVideoObjectRepository;
import io.protone.service.dto.LibVideoObjectDTO;
import io.protone.service.mapper.LibVideoObjectMapper;

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

import io.protone.domain.enumeration.LibVideoQualityEnum;
import io.protone.domain.enumeration.LibAspecTratioEnum;
/**
 * Test class for the LibVideoObjectResource REST controller.
 *
 * @see LibVideoObjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibVideoObjectResourceIntTest {

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Long DEFAULT_LENGTH = 1L;
    private static final Long UPDATED_LENGTH = 2L;

    private static final Integer DEFAULT_BI_TRATE = 1;
    private static final Integer UPDATED_BI_TRATE = 2;

    private static final String DEFAULT_CODEC = "AAAAAAAAAA";
    private static final String UPDATED_CODEC = "BBBBBBBBBB";

    private static final LibVideoQualityEnum DEFAULT_QUALITY = LibVideoQualityEnum.VQ_2160P;
    private static final LibVideoQualityEnum UPDATED_QUALITY = LibVideoQualityEnum.VQ_1440P;

    private static final LibAspecTratioEnum DEFAULT_ASPEC_TRATIO = LibAspecTratioEnum.AR_4BY3;
    private static final LibAspecTratioEnum UPDATED_ASPEC_TRATIO = LibAspecTratioEnum.AR_16BY9;

    @Autowired
    private LibVideoObjectRepository libVideoObjectRepository;

    @Autowired
    private LibVideoObjectMapper libVideoObjectMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLibVideoObjectMockMvc;

    private LibVideoObject libVideoObject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LibVideoObjectResource libVideoObjectResource = new LibVideoObjectResource(libVideoObjectRepository, libVideoObjectMapper);
        this.restLibVideoObjectMockMvc = MockMvcBuilders.standaloneSetup(libVideoObjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibVideoObject createEntity(EntityManager em) {
        LibVideoObject libVideoObject = new LibVideoObject()
                .width(DEFAULT_WIDTH)
                .height(DEFAULT_HEIGHT)
                .length(DEFAULT_LENGTH)
                .biTrate(DEFAULT_BI_TRATE)
                .codec(DEFAULT_CODEC)
                .quality(DEFAULT_QUALITY)
                .aspecTratio(DEFAULT_ASPEC_TRATIO);
        return libVideoObject;
    }

    @Before
    public void initTest() {
        libVideoObject = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibVideoObject() throws Exception {
        int databaseSizeBeforeCreate = libVideoObjectRepository.findAll().size();

        // Create the LibVideoObject
        LibVideoObjectDTO libVideoObjectDTO = libVideoObjectMapper.libVideoObjectToLibVideoObjectDTO(libVideoObject);

        restLibVideoObjectMockMvc.perform(post("/api/lib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libVideoObjectDTO)))
            .andExpect(status().isCreated());

        // Validate the LibVideoObject in the database
        List<LibVideoObject> libVideoObjectList = libVideoObjectRepository.findAll();
        assertThat(libVideoObjectList).hasSize(databaseSizeBeforeCreate + 1);
        LibVideoObject testLibVideoObject = libVideoObjectList.get(libVideoObjectList.size() - 1);
        assertThat(testLibVideoObject.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testLibVideoObject.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testLibVideoObject.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testLibVideoObject.getBiTrate()).isEqualTo(DEFAULT_BI_TRATE);
        assertThat(testLibVideoObject.getCodec()).isEqualTo(DEFAULT_CODEC);
        assertThat(testLibVideoObject.getQuality()).isEqualTo(DEFAULT_QUALITY);
        assertThat(testLibVideoObject.getAspecTratio()).isEqualTo(DEFAULT_ASPEC_TRATIO);
    }

    @Test
    @Transactional
    public void createLibVideoObjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libVideoObjectRepository.findAll().size();

        // Create the LibVideoObject with an existing ID
        LibVideoObject existingLibVideoObject = new LibVideoObject();
        existingLibVideoObject.setId(1L);
        LibVideoObjectDTO existingLibVideoObjectDTO = libVideoObjectMapper.libVideoObjectToLibVideoObjectDTO(existingLibVideoObject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibVideoObjectMockMvc.perform(post("/api/lib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLibVideoObjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibVideoObject> libVideoObjectList = libVideoObjectRepository.findAll();
        assertThat(libVideoObjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkWidthIsRequired() throws Exception {
        int databaseSizeBeforeTest = libVideoObjectRepository.findAll().size();
        // set the field null
        libVideoObject.setWidth(null);

        // Create the LibVideoObject, which fails.
        LibVideoObjectDTO libVideoObjectDTO = libVideoObjectMapper.libVideoObjectToLibVideoObjectDTO(libVideoObject);

        restLibVideoObjectMockMvc.perform(post("/api/lib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libVideoObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LibVideoObject> libVideoObjectList = libVideoObjectRepository.findAll();
        assertThat(libVideoObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = libVideoObjectRepository.findAll().size();
        // set the field null
        libVideoObject.setHeight(null);

        // Create the LibVideoObject, which fails.
        LibVideoObjectDTO libVideoObjectDTO = libVideoObjectMapper.libVideoObjectToLibVideoObjectDTO(libVideoObject);

        restLibVideoObjectMockMvc.perform(post("/api/lib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libVideoObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LibVideoObject> libVideoObjectList = libVideoObjectRepository.findAll();
        assertThat(libVideoObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = libVideoObjectRepository.findAll().size();
        // set the field null
        libVideoObject.setLength(null);

        // Create the LibVideoObject, which fails.
        LibVideoObjectDTO libVideoObjectDTO = libVideoObjectMapper.libVideoObjectToLibVideoObjectDTO(libVideoObject);

        restLibVideoObjectMockMvc.perform(post("/api/lib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libVideoObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LibVideoObject> libVideoObjectList = libVideoObjectRepository.findAll();
        assertThat(libVideoObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBiTrateIsRequired() throws Exception {
        int databaseSizeBeforeTest = libVideoObjectRepository.findAll().size();
        // set the field null
        libVideoObject.setBiTrate(null);

        // Create the LibVideoObject, which fails.
        LibVideoObjectDTO libVideoObjectDTO = libVideoObjectMapper.libVideoObjectToLibVideoObjectDTO(libVideoObject);

        restLibVideoObjectMockMvc.perform(post("/api/lib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libVideoObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LibVideoObject> libVideoObjectList = libVideoObjectRepository.findAll();
        assertThat(libVideoObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodecIsRequired() throws Exception {
        int databaseSizeBeforeTest = libVideoObjectRepository.findAll().size();
        // set the field null
        libVideoObject.setCodec(null);

        // Create the LibVideoObject, which fails.
        LibVideoObjectDTO libVideoObjectDTO = libVideoObjectMapper.libVideoObjectToLibVideoObjectDTO(libVideoObject);

        restLibVideoObjectMockMvc.perform(post("/api/lib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libVideoObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LibVideoObject> libVideoObjectList = libVideoObjectRepository.findAll();
        assertThat(libVideoObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibVideoObjects() throws Exception {
        // Initialize the database
        libVideoObjectRepository.saveAndFlush(libVideoObject);

        // Get all the libVideoObjectList
        restLibVideoObjectMockMvc.perform(get("/api/lib-video-objects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libVideoObject.getId().intValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].biTrate").value(hasItem(DEFAULT_BI_TRATE)))
            .andExpect(jsonPath("$.[*].codec").value(hasItem(DEFAULT_CODEC.toString())))
            .andExpect(jsonPath("$.[*].quality").value(hasItem(DEFAULT_QUALITY.toString())))
            .andExpect(jsonPath("$.[*].aspecTratio").value(hasItem(DEFAULT_ASPEC_TRATIO.toString())));
    }

    @Test
    @Transactional
    public void getLibVideoObject() throws Exception {
        // Initialize the database
        libVideoObjectRepository.saveAndFlush(libVideoObject);

        // Get the libVideoObject
        restLibVideoObjectMockMvc.perform(get("/api/lib-video-objects/{id}", libVideoObject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libVideoObject.getId().intValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.intValue()))
            .andExpect(jsonPath("$.biTrate").value(DEFAULT_BI_TRATE))
            .andExpect(jsonPath("$.codec").value(DEFAULT_CODEC.toString()))
            .andExpect(jsonPath("$.quality").value(DEFAULT_QUALITY.toString()))
            .andExpect(jsonPath("$.aspecTratio").value(DEFAULT_ASPEC_TRATIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibVideoObject() throws Exception {
        // Get the libVideoObject
        restLibVideoObjectMockMvc.perform(get("/api/lib-video-objects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibVideoObject() throws Exception {
        // Initialize the database
        libVideoObjectRepository.saveAndFlush(libVideoObject);
        int databaseSizeBeforeUpdate = libVideoObjectRepository.findAll().size();

        // Update the libVideoObject
        LibVideoObject updatedLibVideoObject = libVideoObjectRepository.findOne(libVideoObject.getId());
        updatedLibVideoObject
                .width(UPDATED_WIDTH)
                .height(UPDATED_HEIGHT)
                .length(UPDATED_LENGTH)
                .biTrate(UPDATED_BI_TRATE)
                .codec(UPDATED_CODEC)
                .quality(UPDATED_QUALITY)
                .aspecTratio(UPDATED_ASPEC_TRATIO);
        LibVideoObjectDTO libVideoObjectDTO = libVideoObjectMapper.libVideoObjectToLibVideoObjectDTO(updatedLibVideoObject);

        restLibVideoObjectMockMvc.perform(put("/api/lib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libVideoObjectDTO)))
            .andExpect(status().isOk());

        // Validate the LibVideoObject in the database
        List<LibVideoObject> libVideoObjectList = libVideoObjectRepository.findAll();
        assertThat(libVideoObjectList).hasSize(databaseSizeBeforeUpdate);
        LibVideoObject testLibVideoObject = libVideoObjectList.get(libVideoObjectList.size() - 1);
        assertThat(testLibVideoObject.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testLibVideoObject.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testLibVideoObject.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testLibVideoObject.getBiTrate()).isEqualTo(UPDATED_BI_TRATE);
        assertThat(testLibVideoObject.getCodec()).isEqualTo(UPDATED_CODEC);
        assertThat(testLibVideoObject.getQuality()).isEqualTo(UPDATED_QUALITY);
        assertThat(testLibVideoObject.getAspecTratio()).isEqualTo(UPDATED_ASPEC_TRATIO);
    }

    @Test
    @Transactional
    public void updateNonExistingLibVideoObject() throws Exception {
        int databaseSizeBeforeUpdate = libVideoObjectRepository.findAll().size();

        // Create the LibVideoObject
        LibVideoObjectDTO libVideoObjectDTO = libVideoObjectMapper.libVideoObjectToLibVideoObjectDTO(libVideoObject);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibVideoObjectMockMvc.perform(put("/api/lib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libVideoObjectDTO)))
            .andExpect(status().isCreated());

        // Validate the LibVideoObject in the database
        List<LibVideoObject> libVideoObjectList = libVideoObjectRepository.findAll();
        assertThat(libVideoObjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibVideoObject() throws Exception {
        // Initialize the database
        libVideoObjectRepository.saveAndFlush(libVideoObject);
        int databaseSizeBeforeDelete = libVideoObjectRepository.findAll().size();

        // Get the libVideoObject
        restLibVideoObjectMockMvc.perform(delete("/api/lib-video-objects/{id}", libVideoObject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibVideoObject> libVideoObjectList = libVideoObjectRepository.findAll();
        assertThat(libVideoObjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibVideoObject.class);
    }
}
