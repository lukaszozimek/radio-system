package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LIBVideoObject;
import io.protone.repository.LIBVideoObjectRepository;
import io.protone.service.dto.LIBVideoObjectDTO;
import io.protone.service.mapper.LIBVideoObjectMapper;

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

import io.protone.domain.enumeration.LIBVideoQualityEnum;
import io.protone.domain.enumeration.LIBAspectRatioEnum;
/**
 * Test class for the LIBVideoObjectResource REST controller.
 *
 * @see LIBVideoObjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LIBVideoObjectResourceIntTest {

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Long DEFAULT_LENGTH = 1L;
    private static final Long UPDATED_LENGTH = 2L;

    private static final Integer DEFAULT_BITRATE = 1;
    private static final Integer UPDATED_BITRATE = 2;

    private static final String DEFAULT_CODEC = "AAAAAAAAAA";
    private static final String UPDATED_CODEC = "BBBBBBBBBB";

    private static final LIBVideoQualityEnum DEFAULT_QUALITY = LIBVideoQualityEnum.VQ_2160P;
    private static final LIBVideoQualityEnum UPDATED_QUALITY = LIBVideoQualityEnum.VQ_1440P;

    private static final LIBAspectRatioEnum DEFAULT_ASPECT_RATIO = LIBAspectRatioEnum.AR_4BY3;
    private static final LIBAspectRatioEnum UPDATED_ASPECT_RATIO = LIBAspectRatioEnum.AR_16BY9;

    @Inject
    private LIBVideoObjectRepository lIBVideoObjectRepository;

    @Inject
    private LIBVideoObjectMapper lIBVideoObjectMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLIBVideoObjectMockMvc;

    private LIBVideoObject lIBVideoObject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LIBVideoObjectResource lIBVideoObjectResource = new LIBVideoObjectResource();
        ReflectionTestUtils.setField(lIBVideoObjectResource, "lIBVideoObjectRepository", lIBVideoObjectRepository);
        ReflectionTestUtils.setField(lIBVideoObjectResource, "lIBVideoObjectMapper", lIBVideoObjectMapper);
        this.restLIBVideoObjectMockMvc = MockMvcBuilders.standaloneSetup(lIBVideoObjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LIBVideoObject createEntity(EntityManager em) {
        LIBVideoObject lIBVideoObject = new LIBVideoObject()
                .width(DEFAULT_WIDTH)
                .height(DEFAULT_HEIGHT)
                .length(DEFAULT_LENGTH)
                .bitrate(DEFAULT_BITRATE)
                .codec(DEFAULT_CODEC)
                .quality(DEFAULT_QUALITY)
                .aspectRatio(DEFAULT_ASPECT_RATIO);
        return lIBVideoObject;
    }

    @Before
    public void initTest() {
        lIBVideoObject = createEntity(em);
    }

    @Test
    @Transactional
    public void createLIBVideoObject() throws Exception {
        int databaseSizeBeforeCreate = lIBVideoObjectRepository.findAll().size();

        // Create the LIBVideoObject
        LIBVideoObjectDTO lIBVideoObjectDTO = lIBVideoObjectMapper.lIBVideoObjectToLIBVideoObjectDTO(lIBVideoObject);

        restLIBVideoObjectMockMvc.perform(post("/api/l-ib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBVideoObjectDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBVideoObject in the database
        List<LIBVideoObject> lIBVideoObjectList = lIBVideoObjectRepository.findAll();
        assertThat(lIBVideoObjectList).hasSize(databaseSizeBeforeCreate + 1);
        LIBVideoObject testLIBVideoObject = lIBVideoObjectList.get(lIBVideoObjectList.size() - 1);
        assertThat(testLIBVideoObject.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testLIBVideoObject.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testLIBVideoObject.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testLIBVideoObject.getBitrate()).isEqualTo(DEFAULT_BITRATE);
        assertThat(testLIBVideoObject.getCodec()).isEqualTo(DEFAULT_CODEC);
        assertThat(testLIBVideoObject.getQuality()).isEqualTo(DEFAULT_QUALITY);
        assertThat(testLIBVideoObject.getAspectRatio()).isEqualTo(DEFAULT_ASPECT_RATIO);
    }

    @Test
    @Transactional
    public void createLIBVideoObjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lIBVideoObjectRepository.findAll().size();

        // Create the LIBVideoObject with an existing ID
        LIBVideoObject existingLIBVideoObject = new LIBVideoObject();
        existingLIBVideoObject.setId(1L);
        LIBVideoObjectDTO existingLIBVideoObjectDTO = lIBVideoObjectMapper.lIBVideoObjectToLIBVideoObjectDTO(existingLIBVideoObject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLIBVideoObjectMockMvc.perform(post("/api/l-ib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLIBVideoObjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LIBVideoObject> lIBVideoObjectList = lIBVideoObjectRepository.findAll();
        assertThat(lIBVideoObjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkWidthIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBVideoObjectRepository.findAll().size();
        // set the field null
        lIBVideoObject.setWidth(null);

        // Create the LIBVideoObject, which fails.
        LIBVideoObjectDTO lIBVideoObjectDTO = lIBVideoObjectMapper.lIBVideoObjectToLIBVideoObjectDTO(lIBVideoObject);

        restLIBVideoObjectMockMvc.perform(post("/api/l-ib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBVideoObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LIBVideoObject> lIBVideoObjectList = lIBVideoObjectRepository.findAll();
        assertThat(lIBVideoObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBVideoObjectRepository.findAll().size();
        // set the field null
        lIBVideoObject.setHeight(null);

        // Create the LIBVideoObject, which fails.
        LIBVideoObjectDTO lIBVideoObjectDTO = lIBVideoObjectMapper.lIBVideoObjectToLIBVideoObjectDTO(lIBVideoObject);

        restLIBVideoObjectMockMvc.perform(post("/api/l-ib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBVideoObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LIBVideoObject> lIBVideoObjectList = lIBVideoObjectRepository.findAll();
        assertThat(lIBVideoObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBVideoObjectRepository.findAll().size();
        // set the field null
        lIBVideoObject.setLength(null);

        // Create the LIBVideoObject, which fails.
        LIBVideoObjectDTO lIBVideoObjectDTO = lIBVideoObjectMapper.lIBVideoObjectToLIBVideoObjectDTO(lIBVideoObject);

        restLIBVideoObjectMockMvc.perform(post("/api/l-ib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBVideoObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LIBVideoObject> lIBVideoObjectList = lIBVideoObjectRepository.findAll();
        assertThat(lIBVideoObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBitrateIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBVideoObjectRepository.findAll().size();
        // set the field null
        lIBVideoObject.setBitrate(null);

        // Create the LIBVideoObject, which fails.
        LIBVideoObjectDTO lIBVideoObjectDTO = lIBVideoObjectMapper.lIBVideoObjectToLIBVideoObjectDTO(lIBVideoObject);

        restLIBVideoObjectMockMvc.perform(post("/api/l-ib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBVideoObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LIBVideoObject> lIBVideoObjectList = lIBVideoObjectRepository.findAll();
        assertThat(lIBVideoObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodecIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBVideoObjectRepository.findAll().size();
        // set the field null
        lIBVideoObject.setCodec(null);

        // Create the LIBVideoObject, which fails.
        LIBVideoObjectDTO lIBVideoObjectDTO = lIBVideoObjectMapper.lIBVideoObjectToLIBVideoObjectDTO(lIBVideoObject);

        restLIBVideoObjectMockMvc.perform(post("/api/l-ib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBVideoObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LIBVideoObject> lIBVideoObjectList = lIBVideoObjectRepository.findAll();
        assertThat(lIBVideoObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLIBVideoObjects() throws Exception {
        // Initialize the database
        lIBVideoObjectRepository.saveAndFlush(lIBVideoObject);

        // Get all the lIBVideoObjectList
        restLIBVideoObjectMockMvc.perform(get("/api/l-ib-video-objects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lIBVideoObject.getId().intValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].bitrate").value(hasItem(DEFAULT_BITRATE)))
            .andExpect(jsonPath("$.[*].codec").value(hasItem(DEFAULT_CODEC.toString())))
            .andExpect(jsonPath("$.[*].quality").value(hasItem(DEFAULT_QUALITY.toString())))
            .andExpect(jsonPath("$.[*].aspectRatio").value(hasItem(DEFAULT_ASPECT_RATIO.toString())));
    }

    @Test
    @Transactional
    public void getLIBVideoObject() throws Exception {
        // Initialize the database
        lIBVideoObjectRepository.saveAndFlush(lIBVideoObject);

        // Get the lIBVideoObject
        restLIBVideoObjectMockMvc.perform(get("/api/l-ib-video-objects/{id}", lIBVideoObject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lIBVideoObject.getId().intValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.intValue()))
            .andExpect(jsonPath("$.bitrate").value(DEFAULT_BITRATE))
            .andExpect(jsonPath("$.codec").value(DEFAULT_CODEC.toString()))
            .andExpect(jsonPath("$.quality").value(DEFAULT_QUALITY.toString()))
            .andExpect(jsonPath("$.aspectRatio").value(DEFAULT_ASPECT_RATIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLIBVideoObject() throws Exception {
        // Get the lIBVideoObject
        restLIBVideoObjectMockMvc.perform(get("/api/l-ib-video-objects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLIBVideoObject() throws Exception {
        // Initialize the database
        lIBVideoObjectRepository.saveAndFlush(lIBVideoObject);
        int databaseSizeBeforeUpdate = lIBVideoObjectRepository.findAll().size();

        // Update the lIBVideoObject
        LIBVideoObject updatedLIBVideoObject = lIBVideoObjectRepository.findOne(lIBVideoObject.getId());
        updatedLIBVideoObject
                .width(UPDATED_WIDTH)
                .height(UPDATED_HEIGHT)
                .length(UPDATED_LENGTH)
                .bitrate(UPDATED_BITRATE)
                .codec(UPDATED_CODEC)
                .quality(UPDATED_QUALITY)
                .aspectRatio(UPDATED_ASPECT_RATIO);
        LIBVideoObjectDTO lIBVideoObjectDTO = lIBVideoObjectMapper.lIBVideoObjectToLIBVideoObjectDTO(updatedLIBVideoObject);

        restLIBVideoObjectMockMvc.perform(put("/api/l-ib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBVideoObjectDTO)))
            .andExpect(status().isOk());

        // Validate the LIBVideoObject in the database
        List<LIBVideoObject> lIBVideoObjectList = lIBVideoObjectRepository.findAll();
        assertThat(lIBVideoObjectList).hasSize(databaseSizeBeforeUpdate);
        LIBVideoObject testLIBVideoObject = lIBVideoObjectList.get(lIBVideoObjectList.size() - 1);
        assertThat(testLIBVideoObject.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testLIBVideoObject.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testLIBVideoObject.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testLIBVideoObject.getBitrate()).isEqualTo(UPDATED_BITRATE);
        assertThat(testLIBVideoObject.getCodec()).isEqualTo(UPDATED_CODEC);
        assertThat(testLIBVideoObject.getQuality()).isEqualTo(UPDATED_QUALITY);
        assertThat(testLIBVideoObject.getAspectRatio()).isEqualTo(UPDATED_ASPECT_RATIO);
    }

    @Test
    @Transactional
    public void updateNonExistingLIBVideoObject() throws Exception {
        int databaseSizeBeforeUpdate = lIBVideoObjectRepository.findAll().size();

        // Create the LIBVideoObject
        LIBVideoObjectDTO lIBVideoObjectDTO = lIBVideoObjectMapper.lIBVideoObjectToLIBVideoObjectDTO(lIBVideoObject);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLIBVideoObjectMockMvc.perform(put("/api/l-ib-video-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBVideoObjectDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBVideoObject in the database
        List<LIBVideoObject> lIBVideoObjectList = lIBVideoObjectRepository.findAll();
        assertThat(lIBVideoObjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLIBVideoObject() throws Exception {
        // Initialize the database
        lIBVideoObjectRepository.saveAndFlush(lIBVideoObject);
        int databaseSizeBeforeDelete = lIBVideoObjectRepository.findAll().size();

        // Get the lIBVideoObject
        restLIBVideoObjectMockMvc.perform(delete("/api/l-ib-video-objects/{id}", lIBVideoObject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LIBVideoObject> lIBVideoObjectList = lIBVideoObjectRepository.findAll();
        assertThat(lIBVideoObjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
