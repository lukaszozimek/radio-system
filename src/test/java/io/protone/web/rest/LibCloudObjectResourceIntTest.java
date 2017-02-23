package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LibCloudObject;
import io.protone.repository.LibCloudObjectRepository;

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

import io.protone.domain.enumeration.LibObjectTypeEnum;
/**
 * Test class for the LibCloudObjectResource REST controller.
 *
 * @see LibCloudObjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibCloudObjectResourceIntTest {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final LibObjectTypeEnum DEFAULT_OBJECT_TYPE = LibObjectTypeEnum.OT_IMAGE;
    private static final LibObjectTypeEnum UPDATED_OBJECT_TYPE = LibObjectTypeEnum.OT_AUDIO;

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

    @Autowired
    private LibCloudObjectRepository libCloudObjectRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLibCloudObjectMockMvc;

    private LibCloudObject libCloudObject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LibCloudObjectResource libCloudObjectResource = new LibCloudObjectResource(libCloudObjectRepository);
        this.restLibCloudObjectMockMvc = MockMvcBuilders.standaloneSetup(libCloudObjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibCloudObject createEntity(EntityManager em) {
        LibCloudObject libCloudObject = new LibCloudObject()
                .uuid(DEFAULT_UUID)
                .objectType(DEFAULT_OBJECT_TYPE)
                .originalName(DEFAULT_ORIGINAL_NAME)
                .contentType(DEFAULT_CONTENT_TYPE)
                .size(DEFAULT_SIZE)
                .original(DEFAULT_ORIGINAL)
                .hash(DEFAULT_HASH)
                .createDate(DEFAULT_CREATE_DATE);
        return libCloudObject;
    }

    @Before
    public void initTest() {
        libCloudObject = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibCloudObject() throws Exception {
        int databaseSizeBeforeCreate = libCloudObjectRepository.findAll().size();

        // Create the LibCloudObject

        restLibCloudObjectMockMvc.perform(post("/api/lib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libCloudObject)))
            .andExpect(status().isCreated());

        // Validate the LibCloudObject in the database
        List<LibCloudObject> libCloudObjectList = libCloudObjectRepository.findAll();
        assertThat(libCloudObjectList).hasSize(databaseSizeBeforeCreate + 1);
        LibCloudObject testLibCloudObject = libCloudObjectList.get(libCloudObjectList.size() - 1);
        assertThat(testLibCloudObject.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testLibCloudObject.getObjectType()).isEqualTo(DEFAULT_OBJECT_TYPE);
        assertThat(testLibCloudObject.getOriginalName()).isEqualTo(DEFAULT_ORIGINAL_NAME);
        assertThat(testLibCloudObject.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testLibCloudObject.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testLibCloudObject.isOriginal()).isEqualTo(DEFAULT_ORIGINAL);
        assertThat(testLibCloudObject.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testLibCloudObject.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createLibCloudObjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libCloudObjectRepository.findAll().size();

        // Create the LibCloudObject with an existing ID
        LibCloudObject existingLibCloudObject = new LibCloudObject();
        existingLibCloudObject.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibCloudObjectMockMvc.perform(post("/api/lib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLibCloudObject)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibCloudObject> libCloudObjectList = libCloudObjectRepository.findAll();
        assertThat(libCloudObjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = libCloudObjectRepository.findAll().size();
        // set the field null
        libCloudObject.setUuid(null);

        // Create the LibCloudObject, which fails.

        restLibCloudObjectMockMvc.perform(post("/api/lib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libCloudObject)))
            .andExpect(status().isBadRequest());

        List<LibCloudObject> libCloudObjectList = libCloudObjectRepository.findAll();
        assertThat(libCloudObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOriginalNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libCloudObjectRepository.findAll().size();
        // set the field null
        libCloudObject.setOriginalName(null);

        // Create the LibCloudObject, which fails.

        restLibCloudObjectMockMvc.perform(post("/api/lib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libCloudObject)))
            .andExpect(status().isBadRequest());

        List<LibCloudObject> libCloudObjectList = libCloudObjectRepository.findAll();
        assertThat(libCloudObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = libCloudObjectRepository.findAll().size();
        // set the field null
        libCloudObject.setContentType(null);

        // Create the LibCloudObject, which fails.

        restLibCloudObjectMockMvc.perform(post("/api/lib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libCloudObject)))
            .andExpect(status().isBadRequest());

        List<LibCloudObject> libCloudObjectList = libCloudObjectRepository.findAll();
        assertThat(libCloudObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = libCloudObjectRepository.findAll().size();
        // set the field null
        libCloudObject.setSize(null);

        // Create the LibCloudObject, which fails.

        restLibCloudObjectMockMvc.perform(post("/api/lib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libCloudObject)))
            .andExpect(status().isBadRequest());

        List<LibCloudObject> libCloudObjectList = libCloudObjectRepository.findAll();
        assertThat(libCloudObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = libCloudObjectRepository.findAll().size();
        // set the field null
        libCloudObject.setHash(null);

        // Create the LibCloudObject, which fails.

        restLibCloudObjectMockMvc.perform(post("/api/lib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libCloudObject)))
            .andExpect(status().isBadRequest());

        List<LibCloudObject> libCloudObjectList = libCloudObjectRepository.findAll();
        assertThat(libCloudObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibCloudObjects() throws Exception {
        // Initialize the database
        libCloudObjectRepository.saveAndFlush(libCloudObject);

        // Get all the libCloudObjectList
        restLibCloudObjectMockMvc.perform(get("/api/lib-cloud-objects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libCloudObject.getId().intValue())))
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
    public void getLibCloudObject() throws Exception {
        // Initialize the database
        libCloudObjectRepository.saveAndFlush(libCloudObject);

        // Get the libCloudObject
        restLibCloudObjectMockMvc.perform(get("/api/lib-cloud-objects/{id}", libCloudObject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libCloudObject.getId().intValue()))
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
    public void getNonExistingLibCloudObject() throws Exception {
        // Get the libCloudObject
        restLibCloudObjectMockMvc.perform(get("/api/lib-cloud-objects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibCloudObject() throws Exception {
        // Initialize the database
        libCloudObjectRepository.saveAndFlush(libCloudObject);
        int databaseSizeBeforeUpdate = libCloudObjectRepository.findAll().size();

        // Update the libCloudObject
        LibCloudObject updatedLibCloudObject = libCloudObjectRepository.findOne(libCloudObject.getId());
        updatedLibCloudObject
                .uuid(UPDATED_UUID)
                .objectType(UPDATED_OBJECT_TYPE)
                .originalName(UPDATED_ORIGINAL_NAME)
                .contentType(UPDATED_CONTENT_TYPE)
                .size(UPDATED_SIZE)
                .original(UPDATED_ORIGINAL)
                .hash(UPDATED_HASH)
                .createDate(UPDATED_CREATE_DATE);

        restLibCloudObjectMockMvc.perform(put("/api/lib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLibCloudObject)))
            .andExpect(status().isOk());

        // Validate the LibCloudObject in the database
        List<LibCloudObject> libCloudObjectList = libCloudObjectRepository.findAll();
        assertThat(libCloudObjectList).hasSize(databaseSizeBeforeUpdate);
        LibCloudObject testLibCloudObject = libCloudObjectList.get(libCloudObjectList.size() - 1);
        assertThat(testLibCloudObject.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testLibCloudObject.getObjectType()).isEqualTo(UPDATED_OBJECT_TYPE);
        assertThat(testLibCloudObject.getOriginalName()).isEqualTo(UPDATED_ORIGINAL_NAME);
        assertThat(testLibCloudObject.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testLibCloudObject.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testLibCloudObject.isOriginal()).isEqualTo(UPDATED_ORIGINAL);
        assertThat(testLibCloudObject.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testLibCloudObject.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingLibCloudObject() throws Exception {
        int databaseSizeBeforeUpdate = libCloudObjectRepository.findAll().size();

        // Create the LibCloudObject

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibCloudObjectMockMvc.perform(put("/api/lib-cloud-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libCloudObject)))
            .andExpect(status().isCreated());

        // Validate the LibCloudObject in the database
        List<LibCloudObject> libCloudObjectList = libCloudObjectRepository.findAll();
        assertThat(libCloudObjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibCloudObject() throws Exception {
        // Initialize the database
        libCloudObjectRepository.saveAndFlush(libCloudObject);
        int databaseSizeBeforeDelete = libCloudObjectRepository.findAll().size();

        // Get the libCloudObject
        restLibCloudObjectMockMvc.perform(delete("/api/lib-cloud-objects/{id}", libCloudObject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibCloudObject> libCloudObjectList = libCloudObjectRepository.findAll();
        assertThat(libCloudObjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibCloudObject.class);
    }
}
