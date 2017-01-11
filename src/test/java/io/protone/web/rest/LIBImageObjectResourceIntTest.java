package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LIBImageObject;
import io.protone.repository.LIBImageObjectRepository;
import io.protone.service.dto.LIBImageObjectDTO;
import io.protone.service.mapper.LIBImageObjectMapper;

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

import io.protone.domain.enumeration.LIBImageSizeEnum;
/**
 * Test class for the LIBImageObjectResource REST controller.
 *
 * @see LIBImageObjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LIBImageObjectResourceIntTest {

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final LIBImageSizeEnum DEFAULT_IMAGE_SIZE = LIBImageSizeEnum.IS_ORIGINAL;
    private static final LIBImageSizeEnum UPDATED_IMAGE_SIZE = LIBImageSizeEnum.IS_LARGE;

    @Inject
    private LIBImageObjectRepository lIBImageObjectRepository;

    @Inject
    private LIBImageObjectMapper lIBImageObjectMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLIBImageObjectMockMvc;

    private LIBImageObject lIBImageObject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LIBImageObjectResource lIBImageObjectResource = new LIBImageObjectResource();
        ReflectionTestUtils.setField(lIBImageObjectResource, "lIBImageObjectRepository", lIBImageObjectRepository);
        ReflectionTestUtils.setField(lIBImageObjectResource, "lIBImageObjectMapper", lIBImageObjectMapper);
        this.restLIBImageObjectMockMvc = MockMvcBuilders.standaloneSetup(lIBImageObjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LIBImageObject createEntity(EntityManager em) {
        LIBImageObject lIBImageObject = new LIBImageObject()
                .width(DEFAULT_WIDTH)
                .height(DEFAULT_HEIGHT)
                .imageSize(DEFAULT_IMAGE_SIZE);
        return lIBImageObject;
    }

    @Before
    public void initTest() {
        lIBImageObject = createEntity(em);
    }

    @Test
    @Transactional
    public void createLIBImageObject() throws Exception {
        int databaseSizeBeforeCreate = lIBImageObjectRepository.findAll().size();

        // Create the LIBImageObject
        LIBImageObjectDTO lIBImageObjectDTO = lIBImageObjectMapper.lIBImageObjectToLIBImageObjectDTO(lIBImageObject);

        restLIBImageObjectMockMvc.perform(post("/api/l-ib-image-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBImageObjectDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBImageObject in the database
        List<LIBImageObject> lIBImageObjectList = lIBImageObjectRepository.findAll();
        assertThat(lIBImageObjectList).hasSize(databaseSizeBeforeCreate + 1);
        LIBImageObject testLIBImageObject = lIBImageObjectList.get(lIBImageObjectList.size() - 1);
        assertThat(testLIBImageObject.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testLIBImageObject.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testLIBImageObject.getImageSize()).isEqualTo(DEFAULT_IMAGE_SIZE);
    }

    @Test
    @Transactional
    public void createLIBImageObjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lIBImageObjectRepository.findAll().size();

        // Create the LIBImageObject with an existing ID
        LIBImageObject existingLIBImageObject = new LIBImageObject();
        existingLIBImageObject.setId(1L);
        LIBImageObjectDTO existingLIBImageObjectDTO = lIBImageObjectMapper.lIBImageObjectToLIBImageObjectDTO(existingLIBImageObject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLIBImageObjectMockMvc.perform(post("/api/l-ib-image-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLIBImageObjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LIBImageObject> lIBImageObjectList = lIBImageObjectRepository.findAll();
        assertThat(lIBImageObjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkWidthIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBImageObjectRepository.findAll().size();
        // set the field null
        lIBImageObject.setWidth(null);

        // Create the LIBImageObject, which fails.
        LIBImageObjectDTO lIBImageObjectDTO = lIBImageObjectMapper.lIBImageObjectToLIBImageObjectDTO(lIBImageObject);

        restLIBImageObjectMockMvc.perform(post("/api/l-ib-image-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBImageObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LIBImageObject> lIBImageObjectList = lIBImageObjectRepository.findAll();
        assertThat(lIBImageObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBImageObjectRepository.findAll().size();
        // set the field null
        lIBImageObject.setHeight(null);

        // Create the LIBImageObject, which fails.
        LIBImageObjectDTO lIBImageObjectDTO = lIBImageObjectMapper.lIBImageObjectToLIBImageObjectDTO(lIBImageObject);

        restLIBImageObjectMockMvc.perform(post("/api/l-ib-image-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBImageObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LIBImageObject> lIBImageObjectList = lIBImageObjectRepository.findAll();
        assertThat(lIBImageObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBImageObjectRepository.findAll().size();
        // set the field null
        lIBImageObject.setImageSize(null);

        // Create the LIBImageObject, which fails.
        LIBImageObjectDTO lIBImageObjectDTO = lIBImageObjectMapper.lIBImageObjectToLIBImageObjectDTO(lIBImageObject);

        restLIBImageObjectMockMvc.perform(post("/api/l-ib-image-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBImageObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LIBImageObject> lIBImageObjectList = lIBImageObjectRepository.findAll();
        assertThat(lIBImageObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLIBImageObjects() throws Exception {
        // Initialize the database
        lIBImageObjectRepository.saveAndFlush(lIBImageObject);

        // Get all the lIBImageObjectList
        restLIBImageObjectMockMvc.perform(get("/api/l-ib-image-objects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lIBImageObject.getId().intValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].imageSize").value(hasItem(DEFAULT_IMAGE_SIZE.toString())));
    }

    @Test
    @Transactional
    public void getLIBImageObject() throws Exception {
        // Initialize the database
        lIBImageObjectRepository.saveAndFlush(lIBImageObject);

        // Get the lIBImageObject
        restLIBImageObjectMockMvc.perform(get("/api/l-ib-image-objects/{id}", lIBImageObject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lIBImageObject.getId().intValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.imageSize").value(DEFAULT_IMAGE_SIZE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLIBImageObject() throws Exception {
        // Get the lIBImageObject
        restLIBImageObjectMockMvc.perform(get("/api/l-ib-image-objects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLIBImageObject() throws Exception {
        // Initialize the database
        lIBImageObjectRepository.saveAndFlush(lIBImageObject);
        int databaseSizeBeforeUpdate = lIBImageObjectRepository.findAll().size();

        // Update the lIBImageObject
        LIBImageObject updatedLIBImageObject = lIBImageObjectRepository.findOne(lIBImageObject.getId());
        updatedLIBImageObject
                .width(UPDATED_WIDTH)
                .height(UPDATED_HEIGHT)
                .imageSize(UPDATED_IMAGE_SIZE);
        LIBImageObjectDTO lIBImageObjectDTO = lIBImageObjectMapper.lIBImageObjectToLIBImageObjectDTO(updatedLIBImageObject);

        restLIBImageObjectMockMvc.perform(put("/api/l-ib-image-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBImageObjectDTO)))
            .andExpect(status().isOk());

        // Validate the LIBImageObject in the database
        List<LIBImageObject> lIBImageObjectList = lIBImageObjectRepository.findAll();
        assertThat(lIBImageObjectList).hasSize(databaseSizeBeforeUpdate);
        LIBImageObject testLIBImageObject = lIBImageObjectList.get(lIBImageObjectList.size() - 1);
        assertThat(testLIBImageObject.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testLIBImageObject.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testLIBImageObject.getImageSize()).isEqualTo(UPDATED_IMAGE_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingLIBImageObject() throws Exception {
        int databaseSizeBeforeUpdate = lIBImageObjectRepository.findAll().size();

        // Create the LIBImageObject
        LIBImageObjectDTO lIBImageObjectDTO = lIBImageObjectMapper.lIBImageObjectToLIBImageObjectDTO(lIBImageObject);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLIBImageObjectMockMvc.perform(put("/api/l-ib-image-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBImageObjectDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBImageObject in the database
        List<LIBImageObject> lIBImageObjectList = lIBImageObjectRepository.findAll();
        assertThat(lIBImageObjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLIBImageObject() throws Exception {
        // Initialize the database
        lIBImageObjectRepository.saveAndFlush(lIBImageObject);
        int databaseSizeBeforeDelete = lIBImageObjectRepository.findAll().size();

        // Get the lIBImageObject
        restLIBImageObjectMockMvc.perform(delete("/api/l-ib-image-objects/{id}", lIBImageObject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LIBImageObject> lIBImageObjectList = lIBImageObjectRepository.findAll();
        assertThat(lIBImageObjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
