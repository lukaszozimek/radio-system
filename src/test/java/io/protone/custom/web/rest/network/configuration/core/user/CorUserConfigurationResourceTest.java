package io.protone.custom.web.rest.network.configuration.core.user;

import io.protone.ProtoneApp;
import io.protone.custom.service.CorMailService;
import io.protone.custom.service.CorUserService;
import io.protone.custom.web.rest.network.configuration.core.user.impl.CorUserConfigurationResourceImpl;
import io.protone.security.SecurityUtils;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.dto.cor.CorUserDTO;
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
import org.hibernate.secure.spi.GrantedPermission;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static io.protone.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
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
    private static final String DEFAULT_LOGIN = "test";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD_HASH = "BBBBBBBBBB";

    @Autowired
    private CorUserRepository corUserRepository;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private CorUserMapper corUserMapper;

    @Autowired
    private CorUserService corUserService;
    @Autowired
    private CorMailService corMailService;

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
        return user;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CorUserConfigurationResourceImpl corUserConfigurationResource = new CorUserConfigurationResourceImpl(corUserRepository, corMailService,
            corUserService, corNetworkService);


        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restCorUserMockMvc = MockMvcBuilders.standaloneSetup(corUserConfigurationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corUser = createEntity(em).networks(Sets.newLinkedHashSet(corNetwork));
    }

    @Test
    @Transactional
    public void createCorUser() throws Exception {
        User principal = new User("admin", "", Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
        SecurityContextImpl impl = new SecurityContextImpl();
        impl.setAuthentication(new UsernamePasswordAuthenticationToken(principal,
            "", Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))));
        SecurityContextHolder.setContext(impl);

        int databaseSizeBeforeCreate = corUserRepository.findAll().size();

        // Create the CorDictionary
        CorUserDTO corDictionaryDTO = corUserMapper.DB2DTO(corUser.networks(Sets.newLinkedHashSet(corNetwork)));

        restCorUserMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/user", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corDictionaryDTO)))
            .andExpect(status().isCreated());

        // Validate the CorDictionary in the database
        List<CorUser> corDictionaryLallst = corUserRepository.findAll();
        assertThat(corDictionaryLallst).hasSize(databaseSizeBeforeCreate + 1);
        CorUser corUser = corDictionaryLallst.get(corDictionaryLallst.size() - 1);
        assertThat(corUser.getLogin()).isEqualTo(DEFAULT_LOGIN);

    }

    @Test
    @Transactional
    public void createCorUserWithExistingId() throws Exception {

        corUserRepository.deleteAll();
        int databaseSizeBeforeCreate = corUserRepository.findAll().size();

        // Create the CorDictionary with an existing ID
        CorUser existingCorDictionary = new CorUser();
        existingCorDictionary.setId(1L);
        CorUserDTO existingCorDictionaryDTO = corUserMapper.DB2DTO(existingCorDictionary.networks(Sets.newLinkedHashSet(corNetwork)));

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
    public void getAllCorUsers() throws Exception {
        // Initialize the database
        corUserRepository.deleteAll();
        corUserRepository.saveAndFlush(corUser.networks(Sets.newLinkedHashSet(corNetwork)));

        // Get all the corDictionaryList
        restCorUserMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/user?sort=id,desc", corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())));
    }

    @Test
    @Transactional
    public void getCorUser() throws Exception {

        corUserRepository.deleteAll();
        // Initialize the database
        corUserRepository.saveAndFlush(corUser.networks(Sets.newLinkedHashSet(corNetwork)));

        // Get the corUser
        restCorUserMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/user/{login}", corNetwork.getShortcut(), corUser.getLogin()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corUser.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorUser() throws Exception {
        // Get the corUser
        restCorUserMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/user/{login}", corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorUser() throws Exception {

        corUserRepository.deleteAll();
        // Initialize the database
        corUserRepository.saveAndFlush(corUser.networks(Sets.newLinkedHashSet(corNetwork)));
        int databaseSizeBeforeUpdate = corUserRepository.findAll().size();

        // Update the corUser
        CorUser corUser1 = corUserRepository.findOne(corUser.getId());
        corUser1
            .login(UPDATED_LOGIN)
            .passwordhash(UPDATED_PASSWORD_HASH);
        CorUserDTO corDictionaryDTO = corUserMapper.DB2DTO(corUser1);

        restCorUserMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/user", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corDictionaryDTO)))
            .andExpect(status().isOk());

        // Validate the CorDictionary in the database
        List<CorUser> corUsers = corUserRepository.findAll();
        assertThat(corUsers).hasSize(databaseSizeBeforeUpdate);
        CorUser corUser = corUsers.get(corUsers.size() - 1);

        assertThat(corUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(corUser.getPasswordhash()).isEqualTo(UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {

        corUserRepository.deleteAll();
        int databaseSizeBeforeTest = corUserRepository.findAll().size();
        // set the field null
        corUser.setLogin(null);

        // Create the LibLibrary, which fails.
        CorUserDTO corDictionaryDTO = corUserMapper.DB2DTO(corUser.networks(Sets.newLinkedHashSet(corNetwork)));

        restCorUserMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/user", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corDictionaryDTO)))
            .andExpect(status().isBadRequest());

        List<CorUser> libLibraryList = corUserRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void updateNonExistingCorUser() throws Exception {

        User principal = new User("admin", "", Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
        SecurityContextImpl impl = new SecurityContextImpl();
        impl.setAuthentication(new UsernamePasswordAuthenticationToken(principal,
            "", Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))));
        SecurityContextHolder.setContext(impl);

        int databaseSizeBeforeUpdate = corUserRepository.findAll().size();

        // Create the CorDictionary
        CorUserDTO corDictionaryDTO = corUserMapper.DB2DTO(corUser.networks(Sets.newLinkedHashSet(corNetwork)));
        corDictionaryDTO.setLogin("xxxxxx");
        corDictionaryDTO.setEmail("lfllflflflflf@com.pl");
        corDictionaryDTO.setId(null);
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

        corUserRepository.deleteAll();
        // Initialize the database
        corUserRepository.saveAndFlush(corUser.networks(Sets.newLinkedHashSet(corNetwork)));
        int databaseSizeBeforeDelete = corUserRepository.findAll().size();

        // Get the corUser
        restCorUserMockMvc.perform(delete("/api/v1/network/{networkShortcut}/configuration/user/{login}", corNetwork.getShortcut(), corUser.getLogin())
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
