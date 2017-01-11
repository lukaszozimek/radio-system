package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LIBCloudObject;
import io.protone.repository.LIBCloudObjectRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.protone.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.protone.domain.enumeration.LIBObjectTypeEnum;
/**
 * Test class for the LIBCloudObjectResource REST controller.
 *
 * @see LIBCloudObjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LIBCloudObjectResourceIntTest {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final LIBObjectTypeEnum DEFAULT_OBJECT_TYPE = LIBObjectTypeEnum.OT_IMAGE;
    private static final LIBObjectTypeEnum UPDATED_OBJECT_TYPE = LIBObjectTypeEnum.OT_AUDIO;

    private static final String DEFAULT_ORIGINAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_SIZE = 1L;
    private static final Long UPDATED_SIZE = 2L;

    private static final Boolean DEFAULT_ORIGINAL = false;
    private static final Boolean UPDATED_ORIGINAL = true;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private LIBCloudObjectRepository lIBCloudObjectRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLIBCloudObjectMockMvc;

    private LIBCloudObject lIBCloudObject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LIBCloudObjectResource lIBCloudObjectResource = new LIBCloudObjectResource();
        ReflectionTestUtils.setField(lIBCloudObjectResource, "lIBCloudObjectRepository", lIBCloudObjectRepository);
        this.restLIBCloudObjectMockMvc = MockMvcBuilders.standaloneSetup(lIBCloudObjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LIBCloudObject createEntity(EntityManager em) {
        LIBCloudObject lIBCloudObject = new LIBCloudObject()
                .uuid(DEFAULT_UUID)
                .objectType(DEFAULT_OBJECT_TYPE)
                .originalName(DEFAULT_ORIGINAL_NAME)
                .contentType(DEFAULT_CONTENT_TYPE)
                .size(DEFAULT_SIZE)
                .original(DEFAULT_ORIGINAL)
                .hash(DEFAULT_HASH)
                .createDate(DEFAULT_CREATE_DATE);
        return lIBCloudObject;
    }

    @Before
    public void initTest() {
        lIBCloudObject = createEntity(em);
    }

    @Test
    @Transactional
    public void createLIBCloudObject() throws Exception {
        int databaseSizeBeforeCreate = lIBCloudObjectRepository.findAll().size();

        // Create the LIBCloudObject

        restLIBCloudObjectMockMvc.perform(post("/api/l-ib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBCloudObject)))
            .andExpect(status().isCreated());

        // Validate the LIBCloudObject in the database
        List<LIBCloudObject> lIBCloudObjectList = lIBCloudObjectRepository.findAll();
        assertThat(lIBCloudObjectList).hasSize(databaseSizeBeforeCreate + 1);
        LIBCloudObject testLIBCloudObject = lIBCloudObjectList.get(lIBCloudObjectList.size() - 1);
        assertThat(testLIBCloudObject.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testLIBCloudObject.getObjectType()).isEqualTo(DEFAULT_OBJECT_TYPE);
        assertThat(testLIBCloudObject.getOriginalName()).isEqualTo(DEFAULT_ORIGINAL_NAME);
        assertThat(testLIBCloudObject.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testLIBCloudObject.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testLIBCloudObject.isOriginal()).isEqualTo(DEFAULT_ORIGINAL);
        assertThat(testLIBCloudObject.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testLIBCloudObject.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createLIBCloudObjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lIBCloudObjectRepository.findAll().size();

        // Create the LIBCloudObject with an existing ID
        LIBCloudObject existingLIBCloudObject = new LIBCloudObject();
        existingLIBCloudObject.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLIBCloudObjectMockMvc.perform(post("/api/l-ib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLIBCloudObject)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LIBCloudObject> lIBCloudObjectList = lIBCloudObjectRepository.findAll();
        assertThat(lIBCloudObjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBCloudObjectRepository.findAll().size();
        // set the field null
        lIBCloudObject.setUuid(null);

        // Create the LIBCloudObject, which fails.

        restLIBCloudObjectMockMvc.perform(post("/api/l-ib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBCloudObject)))
            .andExpect(status().isBadRequest());

        List<LIBCloudObject> lIBCloudObjectList = lIBCloudObjectRepository.findAll();
        assertThat(lIBCloudObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOriginalNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBCloudObjectRepository.findAll().size();
        // set the field null
        lIBCloudObject.setOriginalName(null);

        // Create the LIBCloudObject, which fails.

        restLIBCloudObjectMockMvc.perform(post("/api/l-ib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBCloudObject)))
            .andExpect(status().isBadRequest());

        List<LIBCloudObject> lIBCloudObjectList = lIBCloudObjectRepository.findAll();
        assertThat(lIBCloudObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBCloudObjectRepository.findAll().size();
        // set the field null
        lIBCloudObject.setContentType(null);

        // Create the LIBCloudObject, which fails.

        restLIBCloudObjectMockMvc.perform(post("/api/l-ib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBCloudObject)))
            .andExpect(status().isBadRequest());

        List<LIBCloudObject> lIBCloudObjectList = lIBCloudObjectRepository.findAll();
        assertThat(lIBCloudObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBCloudObjectRepository.findAll().size();
        // set the field null
        lIBCloudObject.setSize(null);

        // Create the LIBCloudObject, which fails.

        restLIBCloudObjectMockMvc.perform(post("/api/l-ib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBCloudObject)))
            .andExpect(status().isBadRequest());

        List<LIBCloudObject> lIBCloudObjectList = lIBCloudObjectRepository.findAll();
        assertThat(lIBCloudObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBCloudObjectRepository.findAll().size();
        // set the field null
        lIBCloudObject.setHash(null);

        // Create the LIBCloudObject, which fails.

        restLIBCloudObjectMockMvc.perform(post("/api/l-ib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBCloudObject)))
            .andExpect(status().isBadRequest());

        List<LIBCloudObject> lIBCloudObjectList = lIBCloudObjectRepository.findAll();
        assertThat(lIBCloudObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLIBCloudObjects() throws Exception {
        // Initialize the database
        lIBCloudObjectRepository.saveAndFlush(lIBCloudObject);

        // Get all the lIBCloudObjectList
        restLIBCloudObjectMockMvc.perform(get("/api/l-ib-cloud-objects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lIBCloudObject.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].objectType").value(hasItem(DEFAULT_OBJECT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].originalName").value(hasItem(DEFAULT_ORIGINAL_NAME.toString())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].original").value(hasItem(DEFAULT_ORIGINAL.booleanValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))));
    }

    @Test
    @Transactional
    public void getLIBCloudObject() throws Exception {
        // Initialize the database
        lIBCloudObjectRepository.saveAndFlush(lIBCloudObject);

        // Get the lIBCloudObject
        restLIBCloudObjectMockMvc.perform(get("/api/l-ib-cloud-objects/{id}", lIBCloudObject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lIBCloudObject.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.objectType").value(DEFAULT_OBJECT_TYPE.toString()))
            .andExpect(jsonPath("$.originalName").value(DEFAULT_ORIGINAL_NAME.toString()))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.intValue()))
            .andExpect(jsonPath("$.original").value(DEFAULT_ORIGINAL.booleanValue()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH.toString()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingLIBCloudObject() throws Exception {
        // Get the lIBCloudObject
        restLIBCloudObjectMockMvc.perform(get("/api/l-ib-cloud-objects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLIBCloudObject() throws Exception {
        // Initialize the database
        lIBCloudObjectRepository.saveAndFlush(lIBCloudObject);
        int databaseSizeBeforeUpdate = lIBCloudObjectRepository.findAll().size();

        // Update the lIBCloudObject
        LIBCloudObject updatedLIBCloudObject = lIBCloudObjectRepository.findOne(lIBCloudObject.getId());
        updatedLIBCloudObject
                .uuid(UPDATED_UUID)
                .objectType(UPDATED_OBJECT_TYPE)
                .originalName(UPDATED_ORIGINAL_NAME)
                .contentType(UPDATED_CONTENT_TYPE)
                .size(UPDATED_SIZE)
                .original(UPDATED_ORIGINAL)
                .hash(UPDATED_HASH)
                .createDate(UPDATED_CREATE_DATE);

        restLIBCloudObjectMockMvc.perform(put("/api/l-ib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLIBCloudObject)))
            .andExpect(status().isOk());

        // Validate the LIBCloudObject in the database
        List<LIBCloudObject> lIBCloudObjectList = lIBCloudObjectRepository.findAll();
        assertThat(lIBCloudObjectList).hasSize(databaseSizeBeforeUpdate);
        LIBCloudObject testLIBCloudObject = lIBCloudObjectList.get(lIBCloudObjectList.size() - 1);
        assertThat(testLIBCloudObject.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testLIBCloudObject.getObjectType()).isEqualTo(UPDATED_OBJECT_TYPE);
        assertThat(testLIBCloudObject.getOriginalName()).isEqualTo(UPDATED_ORIGINAL_NAME);
        assertThat(testLIBCloudObject.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testLIBCloudObject.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testLIBCloudObject.isOriginal()).isEqualTo(UPDATED_ORIGINAL);
        assertThat(testLIBCloudObject.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testLIBCloudObject.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingLIBCloudObject() throws Exception {
        int databaseSizeBeforeUpdate = lIBCloudObjectRepository.findAll().size();

        // Create the LIBCloudObject

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLIBCloudObjectMockMvc.perform(put("/api/l-ib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBCloudObject)))
            .andExpect(status().isCreated());

        // Validate the LIBCloudObject in the database
        List<LIBCloudObject> lIBCloudObjectList = lIBCloudObjectRepository.findAll();
        assertThat(lIBCloudObjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLIBCloudObject() throws Exception {
        // Initialize the database
        lIBCloudObjectRepository.saveAndFlush(lIBCloudObject);
        int databaseSizeBeforeDelete = lIBCloudObjectRepository.findAll().size();

        // Get the lIBCloudObject
        restLIBCloudObjectMockMvc.perform(delete("/api/l-ib-cloud-objects/{id}", lIBCloudObject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LIBCloudObject> lIBCloudObjectList = lIBCloudObjectRepository.findAll();
        assertThat(lIBCloudObjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
