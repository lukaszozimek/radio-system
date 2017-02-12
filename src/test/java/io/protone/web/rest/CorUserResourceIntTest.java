package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorUser;
import io.protone.repository.CorUserRepository;
import io.protone.service.dto.CorUserDTO;
import io.protone.service.mapper.CorUserMapper;

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

/**
 * Test class for the CorUserResource REST controller.
 *
 * @see CorUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorUserResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CorUserRepository corUserRepository;

    @Autowired
    private CorUserMapper corUserMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorUserMockMvc;

    private CorUser corUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorUserResource corUserResource = new CorUserResource(corUserRepository, corUserMapper);
        this.restCorUserMockMvc = MockMvcBuilders.standaloneSetup(corUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorUser createEntity(EntityManager em) {
        CorUser corUser = new CorUser()
                .name(DEFAULT_NAME);
        return corUser;
    }

    @Before
    public void initTest() {
        corUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorUser() throws Exception {
        int databaseSizeBeforeCreate = corUserRepository.findAll().size();

        // Create the CorUser
        CorUserDTO corUserDTO = corUserMapper.corUserToCorUserDTO(corUser);

        restCorUserMockMvc.perform(post("/api/cor-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corUserDTO)))
            .andExpect(status().isCreated());

        // Validate the CorUser in the database
        List<CorUser> corUserList = corUserRepository.findAll();
        assertThat(corUserList).hasSize(databaseSizeBeforeCreate + 1);
        CorUser testCorUser = corUserList.get(corUserList.size() - 1);
        assertThat(testCorUser.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCorUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corUserRepository.findAll().size();

        // Create the CorUser with an existing ID
        CorUser existingCorUser = new CorUser();
        existingCorUser.setId(1L);
        CorUserDTO existingCorUserDTO = corUserMapper.corUserToCorUserDTO(existingCorUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorUserMockMvc.perform(post("/api/cor-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorUser> corUserList = corUserRepository.findAll();
        assertThat(corUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorUsers() throws Exception {
        // Initialize the database
        corUserRepository.saveAndFlush(corUser);

        // Get all the corUserList
        restCorUserMockMvc.perform(get("/api/cor-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCorUser() throws Exception {
        // Initialize the database
        corUserRepository.saveAndFlush(corUser);

        // Get the corUser
        restCorUserMockMvc.perform(get("/api/cor-users/{id}", corUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorUser() throws Exception {
        // Get the corUser
        restCorUserMockMvc.perform(get("/api/cor-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorUser() throws Exception {
        // Initialize the database
        corUserRepository.saveAndFlush(corUser);
        int databaseSizeBeforeUpdate = corUserRepository.findAll().size();

        // Update the corUser
        CorUser updatedCorUser = corUserRepository.findOne(corUser.getId());
        updatedCorUser
                .name(UPDATED_NAME);
        CorUserDTO corUserDTO = corUserMapper.corUserToCorUserDTO(updatedCorUser);

        restCorUserMockMvc.perform(put("/api/cor-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corUserDTO)))
            .andExpect(status().isOk());

        // Validate the CorUser in the database
        List<CorUser> corUserList = corUserRepository.findAll();
        assertThat(corUserList).hasSize(databaseSizeBeforeUpdate);
        CorUser testCorUser = corUserList.get(corUserList.size() - 1);
        assertThat(testCorUser.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCorUser() throws Exception {
        int databaseSizeBeforeUpdate = corUserRepository.findAll().size();

        // Create the CorUser
        CorUserDTO corUserDTO = corUserMapper.corUserToCorUserDTO(corUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorUserMockMvc.perform(put("/api/cor-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corUserDTO)))
            .andExpect(status().isCreated());

        // Validate the CorUser in the database
        List<CorUser> corUserList = corUserRepository.findAll();
        assertThat(corUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorUser() throws Exception {
        // Initialize the database
        corUserRepository.saveAndFlush(corUser);
        int databaseSizeBeforeDelete = corUserRepository.findAll().size();

        // Get the corUser
        restCorUserMockMvc.perform(delete("/api/cor-users/{id}", corUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorUser> corUserList = corUserRepository.findAll();
        assertThat(corUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorUser.class);
    }
}
