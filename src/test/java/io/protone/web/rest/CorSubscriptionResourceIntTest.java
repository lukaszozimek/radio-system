package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorSubscription;
import io.protone.repository.CorSubscriptionRepository;
import io.protone.service.dto.CorSubscriptionDTO;
import io.protone.service.mapper.CorSubscriptionMapper;

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

import io.protone.domain.enumeration.CorSubscriptionTypeEnum;
/**
 * Test class for the CorSubscriptionResource REST controller.
 *
 * @see CorSubscriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorSubscriptionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final CorSubscriptionTypeEnum DEFAULT_SUBSCRIPTION = CorSubscriptionTypeEnum.ST_CRM;
    private static final CorSubscriptionTypeEnum UPDATED_SUBSCRIPTION = CorSubscriptionTypeEnum.ST_TRAFFIC;

    @Autowired
    private CorSubscriptionRepository corSubscriptionRepository;

    @Autowired
    private CorSubscriptionMapper corSubscriptionMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorSubscriptionMockMvc;

    private CorSubscription corSubscription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorSubscriptionResource corSubscriptionResource = new CorSubscriptionResource(corSubscriptionRepository, corSubscriptionMapper);
        this.restCorSubscriptionMockMvc = MockMvcBuilders.standaloneSetup(corSubscriptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorSubscription createEntity(EntityManager em) {
        CorSubscription corSubscription = new CorSubscription()
                .name(DEFAULT_NAME)
                .price(DEFAULT_PRICE)
                .subscription(DEFAULT_SUBSCRIPTION);
        return corSubscription;
    }

    @Before
    public void initTest() {
        corSubscription = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorSubscription() throws Exception {
        int databaseSizeBeforeCreate = corSubscriptionRepository.findAll().size();

        // Create the CorSubscription
        CorSubscriptionDTO corSubscriptionDTO = corSubscriptionMapper.corSubscriptionToCorSubscriptionDTO(corSubscription);

        restCorSubscriptionMockMvc.perform(post("/api/cor-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corSubscriptionDTO)))
            .andExpect(status().isCreated());

        // Validate the CorSubscription in the database
        List<CorSubscription> corSubscriptionList = corSubscriptionRepository.findAll();
        assertThat(corSubscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        CorSubscription testCorSubscription = corSubscriptionList.get(corSubscriptionList.size() - 1);
        assertThat(testCorSubscription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorSubscription.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testCorSubscription.getSubscription()).isEqualTo(DEFAULT_SUBSCRIPTION);
    }

    @Test
    @Transactional
    public void createCorSubscriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corSubscriptionRepository.findAll().size();

        // Create the CorSubscription with an existing ID
        CorSubscription existingCorSubscription = new CorSubscription();
        existingCorSubscription.setId(1L);
        CorSubscriptionDTO existingCorSubscriptionDTO = corSubscriptionMapper.corSubscriptionToCorSubscriptionDTO(existingCorSubscription);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorSubscriptionMockMvc.perform(post("/api/cor-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorSubscription> corSubscriptionList = corSubscriptionRepository.findAll();
        assertThat(corSubscriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorSubscriptions() throws Exception {
        // Initialize the database
        corSubscriptionRepository.saveAndFlush(corSubscription);

        // Get all the corSubscriptionList
        restCorSubscriptionMockMvc.perform(get("/api/cor-subscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].subscription").value(hasItem(DEFAULT_SUBSCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCorSubscription() throws Exception {
        // Initialize the database
        corSubscriptionRepository.saveAndFlush(corSubscription);

        // Get the corSubscription
        restCorSubscriptionMockMvc.perform(get("/api/cor-subscriptions/{id}", corSubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corSubscription.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.subscription").value(DEFAULT_SUBSCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorSubscription() throws Exception {
        // Get the corSubscription
        restCorSubscriptionMockMvc.perform(get("/api/cor-subscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorSubscription() throws Exception {
        // Initialize the database
        corSubscriptionRepository.saveAndFlush(corSubscription);
        int databaseSizeBeforeUpdate = corSubscriptionRepository.findAll().size();

        // Update the corSubscription
        CorSubscription updatedCorSubscription = corSubscriptionRepository.findOne(corSubscription.getId());
        updatedCorSubscription
                .name(UPDATED_NAME)
                .price(UPDATED_PRICE)
                .subscription(UPDATED_SUBSCRIPTION);
        CorSubscriptionDTO corSubscriptionDTO = corSubscriptionMapper.corSubscriptionToCorSubscriptionDTO(updatedCorSubscription);

        restCorSubscriptionMockMvc.perform(put("/api/cor-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corSubscriptionDTO)))
            .andExpect(status().isOk());

        // Validate the CorSubscription in the database
        List<CorSubscription> corSubscriptionList = corSubscriptionRepository.findAll();
        assertThat(corSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        CorSubscription testCorSubscription = corSubscriptionList.get(corSubscriptionList.size() - 1);
        assertThat(testCorSubscription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorSubscription.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testCorSubscription.getSubscription()).isEqualTo(UPDATED_SUBSCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCorSubscription() throws Exception {
        int databaseSizeBeforeUpdate = corSubscriptionRepository.findAll().size();

        // Create the CorSubscription
        CorSubscriptionDTO corSubscriptionDTO = corSubscriptionMapper.corSubscriptionToCorSubscriptionDTO(corSubscription);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorSubscriptionMockMvc.perform(put("/api/cor-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corSubscriptionDTO)))
            .andExpect(status().isCreated());

        // Validate the CorSubscription in the database
        List<CorSubscription> corSubscriptionList = corSubscriptionRepository.findAll();
        assertThat(corSubscriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorSubscription() throws Exception {
        // Initialize the database
        corSubscriptionRepository.saveAndFlush(corSubscription);
        int databaseSizeBeforeDelete = corSubscriptionRepository.findAll().size();

        // Get the corSubscription
        restCorSubscriptionMockMvc.perform(delete("/api/cor-subscriptions/{id}", corSubscription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorSubscription> corSubscriptionList = corSubscriptionRepository.findAll();
        assertThat(corSubscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorSubscription.class);
    }
}
