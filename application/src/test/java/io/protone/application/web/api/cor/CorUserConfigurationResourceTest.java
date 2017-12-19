package io.protone.application.web.api.cor;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.impl.CorUserConfigurationResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.api.dto.CorUserDTO;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.core.mapper.CorUserMapper;
import io.protone.core.repository.CorImageItemRepository;
import io.protone.core.repository.CorUserRepository;
import io.protone.core.service.CorImageItemService;
import io.protone.core.service.CorMailService;
import io.protone.core.service.CorNetworkService;
import io.protone.core.service.CorUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.util.Sets;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @Mock
    private CorImageItemService corImageItemService;

    @Autowired
    private CorImageItemRepository corImageItemRepository;

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
        network.shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        CorUser user = new CorUser();
        user.setLogin("test");
        user.setPasswordhash(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("test@test.com");
        user.setFirstname("test");
        user.setLastname("test");
        user.setLangkey("en");
        user.setNetworks(Sets.newLinkedHashSet(network));
        return user;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(corUserService, "corImageItemService", corImageItemService);
        CorUserConfigurationResourceImpl corUserConfigurationResource = new CorUserConfigurationResourceImpl(corUserRepository, corMailService,
                corUserService, corNetworkService, corUserMapper);


        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
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
        when(corImageItemService.saveImageItem(anyObject())).thenReturn(null);
        User principal = new User("admin", "", Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
        SecurityContextImpl impl = new SecurityContextImpl();
        impl.setAuthentication(new UsernamePasswordAuthenticationToken(principal,
                "", Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))));
        SecurityContextHolder.setContext(impl);

        int databaseSizeBeforeCreate = corUserRepository.findAll().size();

        // Create the CorDictionary
        CorUserDTO corDictionaryDTO = corUserMapper.DB2DTO(corUser.networks(Sets.newLinkedHashSet(corNetwork)));
        MockMultipartFile emptyFile = new MockMultipartFile("avatar", new byte[0]);
        MockMultipartFile jsonFile = new MockMultipartFile("corUserDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(corDictionaryDTO));

        restCorUserMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/configuration/user", corNetwork.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
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

        int databaseSizeBeforeCreate = corUserRepository.findAll().size();

        // Create the CorDictionary with an existing ID
        CorUser existingCorDictionary = new CorUser();
        existingCorDictionary.setId(1L);
        CorUserDTO existingCorDictionaryDTO = corUserMapper.DB2DTO(existingCorDictionary.networks(Sets.newLinkedHashSet(corNetwork)));
        MockMultipartFile emptyFile = new MockMultipartFile("avatar", new byte[0]);
        MockMultipartFile jsonFile = new MockMultipartFile("corUserDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(existingCorDictionaryDTO));

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorUserMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/configuration/user", corNetwork.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorUser> corDictionaryList = corUserRepository.findAll();
        assertThat(corDictionaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorUsers() throws Exception {
        // Initialize the database
        corUserRepository.saveAndFlush(corUser.networks(Sets.newLinkedHashSet(corNetwork)));

        // Get all the corDictionaryList
        restCorUserMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/configuration/user?sort=id,desc", corNetwork.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(corUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())));
    }

    @Test
    @Transactional
    public void getCorUser() throws Exception {

        // Initialize the database
        corUserRepository.saveAndFlush(corUser.networks(Sets.newLinkedHashSet(corNetwork)));

        // Get the corUser
        restCorUserMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/configuration/user/{login}", corNetwork.getShortcut(), corUser.getLogin()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(corUser.getId().intValue()))
                .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorUser() throws Exception {
        // Get the corUser
        restCorUserMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/configuration/user/{login}", corNetwork.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorUser() throws Exception {

        // Initialize the database
        corUserRepository.saveAndFlush(corUser.networks(Sets.newLinkedHashSet(corNetwork)));
        int databaseSizeBeforeUpdate = corUserRepository.findAll().size();

        // Update the corUser
        CorUser corUser1 = corUserRepository.findOne(corUser.getId());
        corUser1
                .login(UPDATED_LOGIN)
                .passwordhash(UPDATED_PASSWORD_HASH);
        CorUserDTO corDictionaryDTO = corUserMapper.DB2DTO(corUser1);

        restCorUserMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/configuration/user", corNetwork.getShortcut())
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

        int databaseSizeBeforeTest = corUserRepository.findAll().size();
        // set the field null
        corUser.setLogin(null);

        // Create the LibMediaLibrary, which fails.
        CorUserDTO corDictionaryDTO = corUserMapper.DB2DTO(corUser.networks(Sets.newLinkedHashSet(corNetwork)));
        MockMultipartFile emptyFile = new MockMultipartFile("avatar", new byte[0]);
        MockMultipartFile jsonFile = new MockMultipartFile("corUserDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(corDictionaryDTO));

        restCorUserMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/configuration/user", corNetwork.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
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
        restCorUserMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/configuration/user", corNetwork.getShortcut())
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
        restCorUserMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/configuration/user/{login}", corNetwork.getShortcut(), corUser.getLogin())
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
