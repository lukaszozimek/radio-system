package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CORPerson;
import io.protone.repository.CORPersonRepository;
import io.protone.service.dto.CORPersonDTO;
import io.protone.service.mapper.CORPersonMapper;

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

/**
 * Test class for the CORPersonResource REST controller.
 *
 * @see CORPersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CORPersonResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private CORPersonRepository cORPersonRepository;

    @Inject
    private CORPersonMapper cORPersonMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCORPersonMockMvc;

    private CORPerson cORPerson;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CORPersonResource cORPersonResource = new CORPersonResource();
        ReflectionTestUtils.setField(cORPersonResource, "cORPersonRepository", cORPersonRepository);
        ReflectionTestUtils.setField(cORPersonResource, "cORPersonMapper", cORPersonMapper);
        this.restCORPersonMockMvc = MockMvcBuilders.standaloneSetup(cORPersonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CORPerson createEntity(EntityManager em) {
        CORPerson cORPerson = new CORPerson()
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .description(DEFAULT_DESCRIPTION);
        return cORPerson;
    }

    @Before
    public void initTest() {
        cORPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createCORPerson() throws Exception {
        int databaseSizeBeforeCreate = cORPersonRepository.findAll().size();

        // Create the CORPerson
        CORPersonDTO cORPersonDTO = cORPersonMapper.cORPersonToCORPersonDTO(cORPerson);

        restCORPersonMockMvc.perform(post("/api/c-or-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the CORPerson in the database
        List<CORPerson> cORPersonList = cORPersonRepository.findAll();
        assertThat(cORPersonList).hasSize(databaseSizeBeforeCreate + 1);
        CORPerson testCORPerson = cORPersonList.get(cORPersonList.size() - 1);
        assertThat(testCORPerson.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCORPerson.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCORPerson.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCORPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cORPersonRepository.findAll().size();

        // Create the CORPerson with an existing ID
        CORPerson existingCORPerson = new CORPerson();
        existingCORPerson.setId(1L);
        CORPersonDTO existingCORPersonDTO = cORPersonMapper.cORPersonToCORPersonDTO(existingCORPerson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCORPersonMockMvc.perform(post("/api/c-or-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCORPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CORPerson> cORPersonList = cORPersonRepository.findAll();
        assertThat(cORPersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORPersonRepository.findAll().size();
        // set the field null
        cORPerson.setFirstName(null);

        // Create the CORPerson, which fails.
        CORPersonDTO cORPersonDTO = cORPersonMapper.cORPersonToCORPersonDTO(cORPerson);

        restCORPersonMockMvc.perform(post("/api/c-or-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORPersonDTO)))
            .andExpect(status().isBadRequest());

        List<CORPerson> cORPersonList = cORPersonRepository.findAll();
        assertThat(cORPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORPersonRepository.findAll().size();
        // set the field null
        cORPerson.setLastName(null);

        // Create the CORPerson, which fails.
        CORPersonDTO cORPersonDTO = cORPersonMapper.cORPersonToCORPersonDTO(cORPerson);

        restCORPersonMockMvc.perform(post("/api/c-or-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORPersonDTO)))
            .andExpect(status().isBadRequest());

        List<CORPerson> cORPersonList = cORPersonRepository.findAll();
        assertThat(cORPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCORPeople() throws Exception {
        // Initialize the database
        cORPersonRepository.saveAndFlush(cORPerson);

        // Get all the cORPersonList
        restCORPersonMockMvc.perform(get("/api/c-or-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cORPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCORPerson() throws Exception {
        // Initialize the database
        cORPersonRepository.saveAndFlush(cORPerson);

        // Get the cORPerson
        restCORPersonMockMvc.perform(get("/api/c-or-people/{id}", cORPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cORPerson.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCORPerson() throws Exception {
        // Get the cORPerson
        restCORPersonMockMvc.perform(get("/api/c-or-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCORPerson() throws Exception {
        // Initialize the database
        cORPersonRepository.saveAndFlush(cORPerson);
        int databaseSizeBeforeUpdate = cORPersonRepository.findAll().size();

        // Update the cORPerson
        CORPerson updatedCORPerson = cORPersonRepository.findOne(cORPerson.getId());
        updatedCORPerson
                .firstName(UPDATED_FIRST_NAME)
                .lastName(UPDATED_LAST_NAME)
                .description(UPDATED_DESCRIPTION);
        CORPersonDTO cORPersonDTO = cORPersonMapper.cORPersonToCORPersonDTO(updatedCORPerson);

        restCORPersonMockMvc.perform(put("/api/c-or-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORPersonDTO)))
            .andExpect(status().isOk());

        // Validate the CORPerson in the database
        List<CORPerson> cORPersonList = cORPersonRepository.findAll();
        assertThat(cORPersonList).hasSize(databaseSizeBeforeUpdate);
        CORPerson testCORPerson = cORPersonList.get(cORPersonList.size() - 1);
        assertThat(testCORPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCORPerson.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCORPerson.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCORPerson() throws Exception {
        int databaseSizeBeforeUpdate = cORPersonRepository.findAll().size();

        // Create the CORPerson
        CORPersonDTO cORPersonDTO = cORPersonMapper.cORPersonToCORPersonDTO(cORPerson);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCORPersonMockMvc.perform(put("/api/c-or-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the CORPerson in the database
        List<CORPerson> cORPersonList = cORPersonRepository.findAll();
        assertThat(cORPersonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCORPerson() throws Exception {
        // Initialize the database
        cORPersonRepository.saveAndFlush(cORPerson);
        int databaseSizeBeforeDelete = cORPersonRepository.findAll().size();

        // Get the cORPerson
        restCORPersonMockMvc.perform(delete("/api/c-or-people/{id}", cORPerson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CORPerson> cORPersonList = cORPersonRepository.findAll();
        assertThat(cORPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
