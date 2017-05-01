package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.ConfPersonPT;
import io.protone.custom.web.rest.network.TestUtil;
import io.protone.domain.CorPerson;
import io.protone.repository.cor.CorPersonRepository;
import io.protone.web.rest.mapper.CorPersonMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
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
 * Created by lukaszozimek on 01.05.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ApiDictionaryPeopleImplTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private CorPersonRepository corPersonRepository;

    @Inject
    private CorPersonMapper corPersonMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCorPersonMockMvc;

    private CorPerson corPerson;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorPerson createEntity(EntityManager em) {
        CorPerson corPerson = new CorPerson()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .description(DEFAULT_DESCRIPTION);
        return corPerson;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApiDictionaryPeopleImpl corPersonResource = new ApiDictionaryPeopleImpl();
        ReflectionTestUtils.setField(corPersonResource, "corPersonRepository", corPersonRepository);
        ReflectionTestUtils.setField(corPersonResource, "corPersonMapper", corPersonMapper);
        this.restCorPersonMockMvc = MockMvcBuilders.standaloneSetup(corPersonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorPerson() throws Exception {
        int databaseSizeBeforeCreate = corPersonRepository.findAll().size();

        // Create the CorPerson
        ConfPersonPT corPersonDTO = corPersonMapper.DB2DTO(corPerson);

        restCorPersonMockMvc.perform(post("/api/cor-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the CorPerson in the database
        List<CorPerson> corPersonList = corPersonRepository.findAll();
        assertThat(corPersonList).hasSize(databaseSizeBeforeCreate + 1);
        CorPerson testCorPerson = corPersonList.get(corPersonList.size() - 1);
        assertThat(testCorPerson.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCorPerson.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCorPerson.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCorPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corPersonRepository.findAll().size();

        // Create the CorPerson with an existing ID
        CorPerson existingCorPerson = new CorPerson();
        existingCorPerson.setId(1L);
        ConfPersonPT existingCorPersonDTO = corPersonMapper.DB2DTO(existingCorPerson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorPersonMockMvc.perform(post("/api/cor-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorPerson> corPersonList = corPersonRepository.findAll();
        assertThat(corPersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = corPersonRepository.findAll().size();
        // set the field null
        corPerson.setFirstName(null);

        // Create the CorPerson, which fails.
        ConfPersonPT corPersonDTO = corPersonMapper.DB2DTO(corPerson);

        restCorPersonMockMvc.perform(post("/api/cor-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corPersonDTO)))
            .andExpect(status().isBadRequest());

        List<CorPerson> corPersonList = corPersonRepository.findAll();
        assertThat(corPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = corPersonRepository.findAll().size();
        // set the field null
        corPerson.setLastName(null);

        // Create the CorPerson, which fails.
        ConfPersonPT corPersonDTO = corPersonMapper.DB2DTO(corPerson);

        restCorPersonMockMvc.perform(post("/api/cor-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corPersonDTO)))
            .andExpect(status().isBadRequest());

        List<CorPerson> corPersonList = corPersonRepository.findAll();
        assertThat(corPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorPeople() throws Exception {
        // Initialize the database
        corPersonRepository.saveAndFlush(corPerson);

        // Get all the corPersonList
        restCorPersonMockMvc.perform(get("/api/cor-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCorPerson() throws Exception {
        // Initialize the database
        corPersonRepository.saveAndFlush(corPerson);

        // Get the corPerson
        restCorPersonMockMvc.perform(get("/api/cor-people/{id}", corPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corPerson.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorPerson() throws Exception {
        // Get the corPerson
        restCorPersonMockMvc.perform(get("/api/cor-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorPerson() throws Exception {
        // Initialize the database
        corPersonRepository.saveAndFlush(corPerson);
        int databaseSizeBeforeUpdate = corPersonRepository.findAll().size();

        // Update the corPerson
        CorPerson updatedCorPerson = corPersonRepository.findOne(corPerson.getId());
        updatedCorPerson
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .description(UPDATED_DESCRIPTION);
        ConfPersonPT corPersonDTO = corPersonMapper.DB2DTO(updatedCorPerson);

        restCorPersonMockMvc.perform(put("/api/cor-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corPersonDTO)))
            .andExpect(status().isOk());

        // Validate the CorPerson in the database
        List<CorPerson> corPersonList = corPersonRepository.findAll();
        assertThat(corPersonList).hasSize(databaseSizeBeforeUpdate);
        CorPerson testCorPerson = corPersonList.get(corPersonList.size() - 1);
        assertThat(testCorPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCorPerson.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCorPerson.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCorPerson() throws Exception {
        int databaseSizeBeforeUpdate = corPersonRepository.findAll().size();

        // Create the CorPerson
        ConfPersonPT corPersonDTO = corPersonMapper.DB2DTO(corPerson);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorPersonMockMvc.perform(put("/api/cor-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the CorPerson in the database
        List<CorPerson> corPersonList = corPersonRepository.findAll();
        assertThat(corPersonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorPerson() throws Exception {
        // Initialize the database
        corPersonRepository.saveAndFlush(corPerson);
        int databaseSizeBeforeDelete = corPersonRepository.findAll().size();

        // Get the corPerson
        restCorPersonMockMvc.perform(delete("/api/cor-people/{id}", corPerson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorPerson> corPersonList = corPersonRepository.findAll();
        assertThat(corPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
