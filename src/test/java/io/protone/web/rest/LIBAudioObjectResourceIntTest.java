package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LIBAudioObject;
import io.protone.repository.LIBAudioObjectRepository;
import io.protone.service.dto.LIBAudioObjectDTO;
import io.protone.service.mapper.LIBAudioObjectMapper;

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

import io.protone.domain.enumeration.LIBAudioQualityEnum;
/**
 * Test class for the LIBAudioObjectResource REST controller.
 *
 * @see LIBAudioObjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LIBAudioObjectResourceIntTest {

    private static final Long DEFAULT_LENGTH = 1L;
    private static final Long UPDATED_LENGTH = 2L;

    private static final Integer DEFAULT_BITRATE = 1;
    private static final Integer UPDATED_BITRATE = 2;

    private static final String DEFAULT_CODEC = "AAAAAAAAAA";
    private static final String UPDATED_CODEC = "BBBBBBBBBB";

    private static final LIBAudioQualityEnum DEFAULT_QUALITY = LIBAudioQualityEnum.AQ_ORIGINAL;
    private static final LIBAudioQualityEnum UPDATED_QUALITY = LIBAudioQualityEnum.AQ_LOSSLESS;

    @Inject
    private LIBAudioObjectRepository lIBAudioObjectRepository;

    @Inject
    private LIBAudioObjectMapper lIBAudioObjectMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLIBAudioObjectMockMvc;

    private LIBAudioObject lIBAudioObject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LIBAudioObjectResource lIBAudioObjectResource = new LIBAudioObjectResource();
        ReflectionTestUtils.setField(lIBAudioObjectResource, "lIBAudioObjectRepository", lIBAudioObjectRepository);
        ReflectionTestUtils.setField(lIBAudioObjectResource, "lIBAudioObjectMapper", lIBAudioObjectMapper);
        this.restLIBAudioObjectMockMvc = MockMvcBuilders.standaloneSetup(lIBAudioObjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LIBAudioObject createEntity(EntityManager em) {
        LIBAudioObject lIBAudioObject = new LIBAudioObject()
                .length(DEFAULT_LENGTH)
                .bitrate(DEFAULT_BITRATE)
                .codec(DEFAULT_CODEC)
                .quality(DEFAULT_QUALITY);
        return lIBAudioObject;
    }

    @Before
    public void initTest() {
        lIBAudioObject = createEntity(em);
    }

    @Test
    @Transactional
    public void createLIBAudioObject() throws Exception {
        int databaseSizeBeforeCreate = lIBAudioObjectRepository.findAll().size();

        // Create the LIBAudioObject
        LIBAudioObjectDTO lIBAudioObjectDTO = lIBAudioObjectMapper.lIBAudioObjectToLIBAudioObjectDTO(lIBAudioObject);

        restLIBAudioObjectMockMvc.perform(post("/api/l-ib-audio-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBAudioObjectDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBAudioObject in the database
        List<LIBAudioObject> lIBAudioObjectList = lIBAudioObjectRepository.findAll();
        assertThat(lIBAudioObjectList).hasSize(databaseSizeBeforeCreate + 1);
        LIBAudioObject testLIBAudioObject = lIBAudioObjectList.get(lIBAudioObjectList.size() - 1);
        assertThat(testLIBAudioObject.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testLIBAudioObject.getBitrate()).isEqualTo(DEFAULT_BITRATE);
        assertThat(testLIBAudioObject.getCodec()).isEqualTo(DEFAULT_CODEC);
        assertThat(testLIBAudioObject.getQuality()).isEqualTo(DEFAULT_QUALITY);
    }

    @Test
    @Transactional
    public void createLIBAudioObjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lIBAudioObjectRepository.findAll().size();

        // Create the LIBAudioObject with an existing ID
        LIBAudioObject existingLIBAudioObject = new LIBAudioObject();
        existingLIBAudioObject.setId(1L);
        LIBAudioObjectDTO existingLIBAudioObjectDTO = lIBAudioObjectMapper.lIBAudioObjectToLIBAudioObjectDTO(existingLIBAudioObject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLIBAudioObjectMockMvc.perform(post("/api/l-ib-audio-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLIBAudioObjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LIBAudioObject> lIBAudioObjectList = lIBAudioObjectRepository.findAll();
        assertThat(lIBAudioObjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBAudioObjectRepository.findAll().size();
        // set the field null
        lIBAudioObject.setLength(null);

        // Create the LIBAudioObject, which fails.
        LIBAudioObjectDTO lIBAudioObjectDTO = lIBAudioObjectMapper.lIBAudioObjectToLIBAudioObjectDTO(lIBAudioObject);

        restLIBAudioObjectMockMvc.perform(post("/api/l-ib-audio-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBAudioObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LIBAudioObject> lIBAudioObjectList = lIBAudioObjectRepository.findAll();
        assertThat(lIBAudioObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBitrateIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBAudioObjectRepository.findAll().size();
        // set the field null
        lIBAudioObject.setBitrate(null);

        // Create the LIBAudioObject, which fails.
        LIBAudioObjectDTO lIBAudioObjectDTO = lIBAudioObjectMapper.lIBAudioObjectToLIBAudioObjectDTO(lIBAudioObject);

        restLIBAudioObjectMockMvc.perform(post("/api/l-ib-audio-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBAudioObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LIBAudioObject> lIBAudioObjectList = lIBAudioObjectRepository.findAll();
        assertThat(lIBAudioObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodecIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBAudioObjectRepository.findAll().size();
        // set the field null
        lIBAudioObject.setCodec(null);

        // Create the LIBAudioObject, which fails.
        LIBAudioObjectDTO lIBAudioObjectDTO = lIBAudioObjectMapper.lIBAudioObjectToLIBAudioObjectDTO(lIBAudioObject);

        restLIBAudioObjectMockMvc.perform(post("/api/l-ib-audio-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBAudioObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LIBAudioObject> lIBAudioObjectList = lIBAudioObjectRepository.findAll();
        assertThat(lIBAudioObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLIBAudioObjects() throws Exception {
        // Initialize the database
        lIBAudioObjectRepository.saveAndFlush(lIBAudioObject);

        // Get all the lIBAudioObjectList
        restLIBAudioObjectMockMvc.perform(get("/api/l-ib-audio-objects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lIBAudioObject.getId().intValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].bitrate").value(hasItem(DEFAULT_BITRATE)))
            .andExpect(jsonPath("$.[*].codec").value(hasItem(DEFAULT_CODEC.toString())))
            .andExpect(jsonPath("$.[*].quality").value(hasItem(DEFAULT_QUALITY.toString())));
    }

    @Test
    @Transactional
    public void getLIBAudioObject() throws Exception {
        // Initialize the database
        lIBAudioObjectRepository.saveAndFlush(lIBAudioObject);

        // Get the lIBAudioObject
        restLIBAudioObjectMockMvc.perform(get("/api/l-ib-audio-objects/{id}", lIBAudioObject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lIBAudioObject.getId().intValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.intValue()))
            .andExpect(jsonPath("$.bitrate").value(DEFAULT_BITRATE))
            .andExpect(jsonPath("$.codec").value(DEFAULT_CODEC.toString()))
            .andExpect(jsonPath("$.quality").value(DEFAULT_QUALITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLIBAudioObject() throws Exception {
        // Get the lIBAudioObject
        restLIBAudioObjectMockMvc.perform(get("/api/l-ib-audio-objects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLIBAudioObject() throws Exception {
        // Initialize the database
        lIBAudioObjectRepository.saveAndFlush(lIBAudioObject);
        int databaseSizeBeforeUpdate = lIBAudioObjectRepository.findAll().size();

        // Update the lIBAudioObject
        LIBAudioObject updatedLIBAudioObject = lIBAudioObjectRepository.findOne(lIBAudioObject.getId());
        updatedLIBAudioObject
                .length(UPDATED_LENGTH)
                .bitrate(UPDATED_BITRATE)
                .codec(UPDATED_CODEC)
                .quality(UPDATED_QUALITY);
        LIBAudioObjectDTO lIBAudioObjectDTO = lIBAudioObjectMapper.lIBAudioObjectToLIBAudioObjectDTO(updatedLIBAudioObject);

        restLIBAudioObjectMockMvc.perform(put("/api/l-ib-audio-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBAudioObjectDTO)))
            .andExpect(status().isOk());

        // Validate the LIBAudioObject in the database
        List<LIBAudioObject> lIBAudioObjectList = lIBAudioObjectRepository.findAll();
        assertThat(lIBAudioObjectList).hasSize(databaseSizeBeforeUpdate);
        LIBAudioObject testLIBAudioObject = lIBAudioObjectList.get(lIBAudioObjectList.size() - 1);
        assertThat(testLIBAudioObject.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testLIBAudioObject.getBitrate()).isEqualTo(UPDATED_BITRATE);
        assertThat(testLIBAudioObject.getCodec()).isEqualTo(UPDATED_CODEC);
        assertThat(testLIBAudioObject.getQuality()).isEqualTo(UPDATED_QUALITY);
    }

    @Test
    @Transactional
    public void updateNonExistingLIBAudioObject() throws Exception {
        int databaseSizeBeforeUpdate = lIBAudioObjectRepository.findAll().size();

        // Create the LIBAudioObject
        LIBAudioObjectDTO lIBAudioObjectDTO = lIBAudioObjectMapper.lIBAudioObjectToLIBAudioObjectDTO(lIBAudioObject);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLIBAudioObjectMockMvc.perform(put("/api/l-ib-audio-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBAudioObjectDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBAudioObject in the database
        List<LIBAudioObject> lIBAudioObjectList = lIBAudioObjectRepository.findAll();
        assertThat(lIBAudioObjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLIBAudioObject() throws Exception {
        // Initialize the database
        lIBAudioObjectRepository.saveAndFlush(lIBAudioObject);
        int databaseSizeBeforeDelete = lIBAudioObjectRepository.findAll().size();

        // Get the lIBAudioObject
        restLIBAudioObjectMockMvc.perform(delete("/api/l-ib-audio-objects/{id}", lIBAudioObject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LIBAudioObject> lIBAudioObjectList = lIBAudioObjectRepository.findAll();
        assertThat(lIBAudioObjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
