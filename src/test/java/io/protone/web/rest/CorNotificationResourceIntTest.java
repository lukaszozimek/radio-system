package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorNotification;
import io.protone.repository.CorNotificationRepository;
import io.protone.service.dto.CorNotificationDTO;
import io.protone.service.mapper.CorNotificationMapper;

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
 * Test class for the CorNotificationResource REST controller.
 *
 * @see CorNotificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorNotificationResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CorNotificationRepository corNotificationRepository;

    @Autowired
    private CorNotificationMapper corNotificationMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorNotificationMockMvc;

    private CorNotification corNotification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorNotificationResource corNotificationResource = new CorNotificationResource(corNotificationRepository, corNotificationMapper);
        this.restCorNotificationMockMvc = MockMvcBuilders.standaloneSetup(corNotificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorNotification createEntity(EntityManager em) {
        CorNotification corNotification = new CorNotification()
                .description(DEFAULT_DESCRIPTION);
        return corNotification;
    }

    @Before
    public void initTest() {
        corNotification = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorNotification() throws Exception {
        int databaseSizeBeforeCreate = corNotificationRepository.findAll().size();

        // Create the CorNotification
        CorNotificationDTO corNotificationDTO = corNotificationMapper.corNotificationToCorNotificationDTO(corNotification);

        restCorNotificationMockMvc.perform(post("/api/cor-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corNotificationDTO)))
            .andExpect(status().isCreated());

        // Validate the CorNotification in the database
        List<CorNotification> corNotificationList = corNotificationRepository.findAll();
        assertThat(corNotificationList).hasSize(databaseSizeBeforeCreate + 1);
        CorNotification testCorNotification = corNotificationList.get(corNotificationList.size() - 1);
        assertThat(testCorNotification.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCorNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corNotificationRepository.findAll().size();

        // Create the CorNotification with an existing ID
        CorNotification existingCorNotification = new CorNotification();
        existingCorNotification.setId(1L);
        CorNotificationDTO existingCorNotificationDTO = corNotificationMapper.corNotificationToCorNotificationDTO(existingCorNotification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorNotificationMockMvc.perform(post("/api/cor-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorNotificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorNotification> corNotificationList = corNotificationRepository.findAll();
        assertThat(corNotificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorNotifications() throws Exception {
        // Initialize the database
        corNotificationRepository.saveAndFlush(corNotification);

        // Get all the corNotificationList
        restCorNotificationMockMvc.perform(get("/api/cor-notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCorNotification() throws Exception {
        // Initialize the database
        corNotificationRepository.saveAndFlush(corNotification);

        // Get the corNotification
        restCorNotificationMockMvc.perform(get("/api/cor-notifications/{id}", corNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corNotification.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorNotification() throws Exception {
        // Get the corNotification
        restCorNotificationMockMvc.perform(get("/api/cor-notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorNotification() throws Exception {
        // Initialize the database
        corNotificationRepository.saveAndFlush(corNotification);
        int databaseSizeBeforeUpdate = corNotificationRepository.findAll().size();

        // Update the corNotification
        CorNotification updatedCorNotification = corNotificationRepository.findOne(corNotification.getId());
        updatedCorNotification
                .description(UPDATED_DESCRIPTION);
        CorNotificationDTO corNotificationDTO = corNotificationMapper.corNotificationToCorNotificationDTO(updatedCorNotification);

        restCorNotificationMockMvc.perform(put("/api/cor-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corNotificationDTO)))
            .andExpect(status().isOk());

        // Validate the CorNotification in the database
        List<CorNotification> corNotificationList = corNotificationRepository.findAll();
        assertThat(corNotificationList).hasSize(databaseSizeBeforeUpdate);
        CorNotification testCorNotification = corNotificationList.get(corNotificationList.size() - 1);
        assertThat(testCorNotification.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCorNotification() throws Exception {
        int databaseSizeBeforeUpdate = corNotificationRepository.findAll().size();

        // Create the CorNotification
        CorNotificationDTO corNotificationDTO = corNotificationMapper.corNotificationToCorNotificationDTO(corNotification);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorNotificationMockMvc.perform(put("/api/cor-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corNotificationDTO)))
            .andExpect(status().isCreated());

        // Validate the CorNotification in the database
        List<CorNotification> corNotificationList = corNotificationRepository.findAll();
        assertThat(corNotificationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorNotification() throws Exception {
        // Initialize the database
        corNotificationRepository.saveAndFlush(corNotification);
        int databaseSizeBeforeDelete = corNotificationRepository.findAll().size();

        // Get the corNotification
        restCorNotificationMockMvc.perform(delete("/api/cor-notifications/{id}", corNotification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorNotification> corNotificationList = corNotificationRepository.findAll();
        assertThat(corNotificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorNotification.class);
    }
}
