package io.protone.application.web.api.cor;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.impl.CorFilterResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.api.dto.CorFilterDTO;
import io.protone.core.domain.CorFilter;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.core.mapper.CorFilterMapper;
import io.protone.core.repository.CorFilterRepository;
import io.protone.core.service.CorFilterService;
import io.protone.core.service.CorNetworkService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.protone.application.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static io.protone.core.domain.enumeration.CorEntityTypeEnum.Channel;
import static io.protone.core.domain.enumeration.CorEntityTypeEnum.Contact;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 20.07.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorFilterResourceImplTest {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";


    @Autowired
    private CorFilterRepository corFilterRepository;
    @Autowired
    private CorFilterService corFilterService;

    @Autowired
    private CorFilterMapper corFilterMapper;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorFilterMockMvc;

    private CorFilter corFilter;

    private CorNetwork corNetwork;
    private CorUser corUser;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorFilter createEntity(EntityManager em) {
        CorFilter corFilter = new CorFilter()
                .name(DEFAULT_NAME)
                .value(DEFAULT_VALUE)
                .type(Channel);

        return corFilter;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CorFilterResourceImpl CorFilterResource = new CorFilterResourceImpl();

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);

        ReflectionTestUtils.setField(CorFilterResource, "corFilterService", corFilterService);
        ReflectionTestUtils.setField(CorFilterResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(CorFilterResource, "corFilterMapper", corFilterMapper);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);
        corUser = new CorUser();
        corUser.setId(3L);

        this.restCorFilterMockMvc = MockMvcBuilders.standaloneSetup(CorFilterResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corFilter = createEntity(em).network(corNetwork).user(corUser);
    }

    @Test
    @Transactional
    public void createCorFilter() throws Exception {
        int databaseSizeBeforeCreate = corFilterRepository.findAll().size();

        // Create the CorFilter
        CorFilterDTO CorFilterDTO = corFilterMapper.DB2DTO(corFilter);

        restCorFilterMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/configuration/core/filter", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(CorFilterDTO)))
                .andExpect(status().isCreated());


        // Validate the CorFilter in the database
        List<CorFilter> CorFilterList = corFilterRepository.findAll();
        assertThat(CorFilterList).hasSize(databaseSizeBeforeCreate + 1);
        CorFilter testCorFilter = CorFilterList.get(CorFilterList.size() - 1);
        assertThat(testCorFilter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorFilter.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCorFilter.getType()).isEqualTo(Channel);
    }

    @Test
    @Transactional
    public void createCorFilterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corFilterRepository.findAll().size();

        // Create the CorFilter with an existing ID
        CorFilter existingCorFilter = new CorFilter();
        existingCorFilter.setId(1L);
        CorFilterDTO existingCorFilterDTO = corFilterMapper.DB2DTO(existingCorFilter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorFilterMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/configuration/core/filter", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingCorFilterDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorFilter> CorFilterList = corFilterRepository.findAll();
        assertThat(CorFilterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = corFilterRepository.findAll().size();
        // set the field null
        corFilter.setName(null);

        // Create the CfgMarkerConfiguration, which fails.
        CorFilterDTO cfgMarkerConfigurationDTO = corFilterMapper.DB2DTO(corFilter);

        restCorFilterMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/configuration/core/filter", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
                .andExpect(status().isBadRequest());

        List<CorFilter> corCurrencies = corFilterRepository.findAll();
        assertThat(corCurrencies).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void getAllCorCurrencies() throws Exception {
        // Initialize the database
        corFilterRepository.saveAndFlush(corFilter.network(corNetwork).user(corUser));

        // Get all the CorFilterList
        restCorFilterMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/configuration/core/filter/{type}?sort=id,desc", corNetwork.getShortcut(), Channel.name()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(corFilter.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(Channel.name().toString())));
    }

    @Test
    @Transactional
    public void getCorFilter() throws Exception {
        // Initialize the database
        corFilterRepository.saveAndFlush(corFilter.network(corNetwork).user(corUser));

        // Get the corFilter
        restCorFilterMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/configuration/core/filter/{type}/{id}", corNetwork.getShortcut(), Channel.name(), corFilter.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(corFilter.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
                .andExpect(jsonPath("$.type").value(Channel.name().toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorFilter() throws Exception {
        // Get the corFilter
        restCorFilterMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/configuration/core/filter/{type}/{id}", corNetwork.getShortcut(), Channel.name(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorFilter() throws Exception {
        // Initialize the database
        corFilterRepository.saveAndFlush(corFilter.network(corNetwork).user(corUser));
        int databaseSizeBeforeUpdate = corFilterRepository.findAll().size();

        // Update the corFilter
        CorFilter updatedCorFilter = corFilterRepository.findOne(corFilter.getId());
        updatedCorFilter
                .name(UPDATED_NAME)
                .value(UPDATED_VALUE)
                .type(Contact);
        CorFilterDTO CorFilterDTO = corFilterMapper.DB2DTO(updatedCorFilter);
        restCorFilterMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/configuration/core/filter", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(CorFilterDTO)))
                .andExpect(status().isOk());

        // Validate the CorFilter in the database
        List<CorFilter> CorFilterList = corFilterRepository.findAll();
        assertThat(CorFilterList).hasSize(databaseSizeBeforeUpdate);
        CorFilter testCorFilter = CorFilterList.get(CorFilterList.size() - 1);
        assertThat(testCorFilter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorFilter.getType()).isEqualTo(Contact);
        assertThat(testCorFilter.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingCorFilter() throws Exception {
        int databaseSizeBeforeUpdate = corFilterRepository.findAll().size();

        // Create the CorFilter
        CorFilterDTO CorFilterDTO = corFilterMapper.DB2DTO(corFilter);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorFilterMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/configuration/core/filter", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(CorFilterDTO)))
                .andExpect(status().isCreated());

        // Validate the CorFilter in the database
        List<CorFilter> CorFilterList = corFilterRepository.findAll();
        assertThat(CorFilterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorFilter() throws Exception {
        // Initialize the database
        corFilterRepository.saveAndFlush(corFilter.network(corNetwork).user(corUser));
        int databaseSizeBeforeDelete = corFilterRepository.findAll().size();

        // Get the corFilter
        restCorFilterMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/configuration/core/filter/{type}/{id}", corNetwork.getShortcut(), Channel.name(), corFilter.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CorFilter> CorFilterList = corFilterRepository.findAll();
        assertThat(CorFilterList).hasSize(databaseSizeBeforeDelete - 1);
    }

 
}