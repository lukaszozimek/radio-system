package io.protone.custom.web.rest.network.configuration.core.user;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CoreUserPT;
import io.protone.custom.web.rest.network.TestUtil;
import io.protone.domain.CorDictionary;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorUser;
import io.protone.repository.cor.CorUserRepository;
import io.protone.security.AuthoritiesConstants;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.CorUserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.util.Sets;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.protone.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by lukaszozimek on 05.05.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorUserConfigurationResourceTest {
    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD_HASH = "BBBBBBBBBB";

    private static final String TEST_MODULE = "crm";

    private static final String TEST_TYPE = "crmStage";
    @Autowired
    private CorUserRepository corUserRepository;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private CorUserMapper corUserMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorUserMockMvc;

    private CorUser corUser;

    private CorNetwork corNetwork;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CorUserConfigurationResourceTest corDictionaryResource = new CorUserConfigurationResourceTest();

        ReflectionTestUtils.setField(corDictionaryResource, "corUserRepository", corUserRepository);
        ReflectionTestUtils.setField(corDictionaryResource, "corUserMapper", corUserMapper);
        ReflectionTestUtils.setField(corDictionaryResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restCorUserMockMvc = MockMvcBuilders.standaloneSetup(corDictionaryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorUser createEntity(EntityManager em) {
        CorNetwork network = new CorNetwork();
        network.setId(1L);
        network.shortcut(TEST_NETWORK);
        CorUser user = new CorUser();
        user.setLogin("test");
        user.setPasswordhash(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("test@test.com");
        user.setFirstname("test");
        user.setLastname("test");
        user.setImageurl("http://placehold.it/50x50");
        user.setLangkey("en");
        user.setNetworks(Sets.newLinkedHashSet(network));
        em.persist(user);
        em.flush();
        return user;
    }

    @Before
    public void initTest() {
        corUser = createEntity(em).networks(Sets.newLinkedHashSet(corNetwork));
    }

    @Test
    @Transactional
    public void createCorDictionary() throws Exception {
        int databaseSizeBeforeCreate = corUserRepository.findAll().size();

        // Create the CorDictionary
        CoreUserPT corDictionaryDTO = corUserMapper.DB2DTO(corUser);

        restCorUserMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/user", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corDictionaryDTO)))
            .andExpect(status().isCreated());

        // Validate the CorDictionary in the database
        List<CorUser> corDictionaryLallst = corUserRepository.findAll();
        assertThat(corDictionaryLallst).hasSize(databaseSizeBeforeCreate + 1);
        CorUser testCorDictionary = corDictionaryLallst.get(corDictionaryLallst.size() - 1);
        assertThat(testCorDictionary.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testCorDictionary.getPasswordhash()).isEqualTo(DEFAULT_PASSWORD_HASH);
        assertThat(testCorDictionary.getEmail()).isEqualTo(TEST_TYPE);
        assertThat(testCorDictionary.getAuthorities().stream().findFirst().get().getName()).isEqualTo(AuthoritiesConstants.ADMIN);

    }

    @Test
    @Transactional
    public void createCorDictionaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corUserRepository.findAll().size();

        // Create the CorDictionary with an existing ID
        CorUser existingCorDictionary = new CorUser();
        existingCorDictionary.setId(1L);
        CoreUserPT existingCorDictionaryDTO = corUserMapper.DB2DTO(existingCorDictionary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorUserMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/user", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorDictionaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorUser> corDictionaryList = corUserRepository.findAll();
        assertThat(corDictionaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorDictionaries() throws Exception {
        // Initialize the database
        corUserRepository.saveAndFlush(corUser.networks(Sets.newLinkedHashSet(corNetwork)));

        // Get all the corDictionaryList
        restCorUserMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/user?sort=id,desc", corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_PASSWORD_HASH.toString())));
    }

    @Test
    @Transactional
    public void getCorDictionary() throws Exception {
        // Initialize the database
        corUserRepository.saveAndFlush(corUser.networks(Sets.newLinkedHashSet(corNetwork)));
/*
        // Get the corUser
        restCorUserMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/user/{login}", corNetwork.getShortcut(), corUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_PASSWORD_HASH.toString()))
            .andExpect(jsonPath("$.seqNumber").value(DEFAULT_SEQ_NUMBER.intValue()));*/
    }

    @Test
    @Transactional
    public void getNonExistingCorDictionary() throws Exception {
        // Get the corUser
        restCorUserMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/user/{login}", corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorDictionary() throws Exception {
        // Initialize the database
        corUserRepository.saveAndFlush(corUser.networks(Sets.newLinkedHashSet(corNetwork)));
        int databaseSizeBeforeUpdate = corUserRepository.findAll().size();
/*
        // Update the corUser
        CorUser updatedCorDictionary = corUserRepository.findOne(corUser.getId());
        updatedCorDictionary
            .name(UPDATED_LOGIN)
            .description(UPDATED_PASSWORD_HASH)
            .seqNumber(UPDATED_SEQ_NUMBER);
        CorDictionaryDTO corDictionaryDTO = corUserMapper.DB2DTO(updatedCorDictionary);

        restCorUserMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/user", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corDictionaryDTO)))
            .andExpect(status().isOk());

        // Validate the CorDictionary in the database
        List<CorUser> corUsers = corUserRepository.findAll();
        assertThat(corUsers).hasSize(databaseSizeBeforeUpdate);
        CorUser corUser = corUsers.get(corUsers.size() - 1);
  */
        /*
        assertThat(corUser.getName()).isEqualTo(UPDATED_LOGIN);
        assertThat(corUser.getDescription()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(corUser.getCorDictionaryType()).isEqualTo(TEST_TYPE);
        assertThat(corUser.getSeqNumber()).isEqualTo(UPDATED_SEQ_NUMBER);
        assertThat(corUser.getCorModule()).isEqualTo(TEST_MODULE);*/
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = corUserRepository.findAll().size();
        // set the field null
        corUser.setLogin(null);
/*
        // Create the LibLibrary, which fails.
        CorDictionaryDTO corDictionaryDTO = corUserMapper.DB2DTO(corUser);

        restCorUserMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/user", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corDictionaryDTO)))
            .andExpect(status().isBadRequest());

        List<CorUser> libLibraryList = corUserRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
  */
    }

    @Test
    @Transactional
    public void updateNonExistingCorDictionary() throws Exception {
        int databaseSizeBeforeUpdate = corUserRepository.findAll().size();

        // Create the CorDictionary
        CoreUserPT corDictionaryDTO = corUserMapper.DB2DTO(corUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorUserMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/user", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corDictionaryDTO)))
            .andExpect(status().isCreated());

        // Validate the CorDictionary in the database
        List<CorUser> corDictionaryList = corUserRepository.findAll();
        assertThat(corDictionaryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorDictionary() throws Exception {
        // Initialize the database
        corUserRepository.saveAndFlush(corUser.networks(Sets.newLinkedHashSet(corNetwork)));
        int databaseSizeBeforeDelete = corUserRepository.findAll().size();

        // Get the corUser
        restCorUserMockMvc.perform(delete("/api/v1/network/{networkShortcut}/configuration/user/{login}", corNetwork.getShortcut(), corUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorUser> corDictionaryList = corUserRepository.findAll();
        assertThat(corDictionaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorDictionary.class);
    }
}
