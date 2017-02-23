package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LibImageObject;
import io.protone.repository.LibImageObjectRepository;
import io.protone.service.dto.LibImageObjectDTO;
import io.protone.service.mapper.LibImageObjectMapper;

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

import io.protone.domain.enumeration.LibImageSizeEnum;
/**
 * Test class for the LibImageObjectResource REST controller.
 *
 * @see LibImageObjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibImageObjectResourceIntTest {

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final LibImageSizeEnum DEFAULT_IMAGE_SIZE = LibImageSizeEnum.IS_ORIGINAL;
    private static final LibImageSizeEnum UPDATED_IMAGE_SIZE = LibImageSizeEnum.IS_LARGE;

    @Autowired
    private LibImageObjectRepository libImageObjectRepository;

    @Autowired
    private LibImageObjectMapper libImageObjectMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLibImageObjectMockMvc;

    private LibImageObject libImageObject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LibImageObjectResource libImageObjectResource = new LibImageObjectResource(libImageObjectRepository, libImageObjectMapper);
        this.restLibImageObjectMockMvc = MockMvcBuilders.standaloneSetup(libImageObjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibImageObject createEntity(EntityManager em) {
        LibImageObject libImageObject = new LibImageObject()
                .width(DEFAULT_WIDTH)
                .height(DEFAULT_HEIGHT)
                .imageSize(DEFAULT_IMAGE_SIZE);
        return libImageObject;
    }

    @Before
    public void initTest() {
        libImageObject = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibImageObject() throws Exception {
        int databaseSizeBeforeCreate = libImageObjectRepository.findAll().size();

        // Create the LibImageObject
        LibImageObjectDTO libImageObjectDTO = libImageObjectMapper.libImageObjectToLibImageObjectDTO(libImageObject);

        restLibImageObjectMockMvc.perform(post("/api/lib-image-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libImageObjectDTO)))
            .andExpect(status().isCreated());

        // Validate the LibImageObject in the database
        List<LibImageObject> libImageObjectList = libImageObjectRepository.findAll();
        assertThat(libImageObjectList).hasSize(databaseSizeBeforeCreate + 1);
        LibImageObject testLibImageObject = libImageObjectList.get(libImageObjectList.size() - 1);
        assertThat(testLibImageObject.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testLibImageObject.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testLibImageObject.getImageSize()).isEqualTo(DEFAULT_IMAGE_SIZE);
    }

    @Test
    @Transactional
    public void createLibImageObjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libImageObjectRepository.findAll().size();

        // Create the LibImageObject with an existing ID
        LibImageObject existingLibImageObject = new LibImageObject();
        existingLibImageObject.setId(1L);
        LibImageObjectDTO existingLibImageObjectDTO = libImageObjectMapper.libImageObjectToLibImageObjectDTO(existingLibImageObject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibImageObjectMockMvc.perform(post("/api/lib-image-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLibImageObjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibImageObject> libImageObjectList = libImageObjectRepository.findAll();
        assertThat(libImageObjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkWidthIsRequired() throws Exception {
        int databaseSizeBeforeTest = libImageObjectRepository.findAll().size();
        // set the field null
        libImageObject.setWidth(null);

        // Create the LibImageObject, which fails.
        LibImageObjectDTO libImageObjectDTO = libImageObjectMapper.libImageObjectToLibImageObjectDTO(libImageObject);

        restLibImageObjectMockMvc.perform(post("/api/lib-image-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libImageObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LibImageObject> libImageObjectList = libImageObjectRepository.findAll();
        assertThat(libImageObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = libImageObjectRepository.findAll().size();
        // set the field null
        libImageObject.setHeight(null);

        // Create the LibImageObject, which fails.
        LibImageObjectDTO libImageObjectDTO = libImageObjectMapper.libImageObjectToLibImageObjectDTO(libImageObject);

        restLibImageObjectMockMvc.perform(post("/api/lib-image-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libImageObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LibImageObject> libImageObjectList = libImageObjectRepository.findAll();
        assertThat(libImageObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = libImageObjectRepository.findAll().size();
        // set the field null
        libImageObject.setImageSize(null);

        // Create the LibImageObject, which fails.
        LibImageObjectDTO libImageObjectDTO = libImageObjectMapper.libImageObjectToLibImageObjectDTO(libImageObject);

        restLibImageObjectMockMvc.perform(post("/api/lib-image-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libImageObjectDTO)))
            .andExpect(status().isBadRequest());

        List<LibImageObject> libImageObjectList = libImageObjectRepository.findAll();
        assertThat(libImageObjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibImageObjects() throws Exception {
        // Initialize the database
        libImageObjectRepository.saveAndFlush(libImageObject);

        // Get all the libImageObjectList
        restLibImageObjectMockMvc.perform(get("/api/lib-image-objects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libImageObject.getId().intValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].imageSize").value(hasItem(DEFAULT_IMAGE_SIZE.toString())));
    }

    @Test
    @Transactional
    public void getLibImageObject() throws Exception {
        // Initialize the database
        libImageObjectRepository.saveAndFlush(libImageObject);

        // Get the libImageObject
        restLibImageObjectMockMvc.perform(get("/api/lib-image-objects/{id}", libImageObject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libImageObject.getId().intValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.imageSize").value(DEFAULT_IMAGE_SIZE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibImageObject() throws Exception {
        // Get the libImageObject
        restLibImageObjectMockMvc.perform(get("/api/lib-image-objects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibImageObject() throws Exception {
        // Initialize the database
        libImageObjectRepository.saveAndFlush(libImageObject);
        int databaseSizeBeforeUpdate = libImageObjectRepository.findAll().size();

        // Update the libImageObject
        LibImageObject updatedLibImageObject = libImageObjectRepository.findOne(libImageObject.getId());
        updatedLibImageObject
                .width(UPDATED_WIDTH)
                .height(UPDATED_HEIGHT)
                .imageSize(UPDATED_IMAGE_SIZE);
        LibImageObjectDTO libImageObjectDTO = libImageObjectMapper.libImageObjectToLibImageObjectDTO(updatedLibImageObject);

        restLibImageObjectMockMvc.perform(put("/api/lib-image-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libImageObjectDTO)))
            .andExpect(status().isOk());

        // Validate the LibImageObject in the database
        List<LibImageObject> libImageObjectList = libImageObjectRepository.findAll();
        assertThat(libImageObjectList).hasSize(databaseSizeBeforeUpdate);
        LibImageObject testLibImageObject = libImageObjectList.get(libImageObjectList.size() - 1);
        assertThat(testLibImageObject.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testLibImageObject.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testLibImageObject.getImageSize()).isEqualTo(UPDATED_IMAGE_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingLibImageObject() throws Exception {
        int databaseSizeBeforeUpdate = libImageObjectRepository.findAll().size();

        // Create the LibImageObject
        LibImageObjectDTO libImageObjectDTO = libImageObjectMapper.libImageObjectToLibImageObjectDTO(libImageObject);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibImageObjectMockMvc.perform(put("/api/lib-image-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libImageObjectDTO)))
            .andExpect(status().isCreated());

        // Validate the LibImageObject in the database
        List<LibImageObject> libImageObjectList = libImageObjectRepository.findAll();
        assertThat(libImageObjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibImageObject() throws Exception {
        // Initialize the database
        libImageObjectRepository.saveAndFlush(libImageObject);
        int databaseSizeBeforeDelete = libImageObjectRepository.findAll().size();

        // Get the libImageObject
        restLibImageObjectMockMvc.perform(delete("/api/lib-image-objects/{id}", libImageObject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibImageObject> libImageObjectList = libImageObjectRepository.findAll();
        assertThat(libImageObjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibImageObject.class);
    }
}
