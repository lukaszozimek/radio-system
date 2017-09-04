package io.protone.application.web.api.library;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.library.impl.LibraryFileResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorNetwork;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorImageItemService;
import io.protone.core.service.CorNetworkService;
import io.protone.library.api.dto.LibFileLibraryDTO;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.mapper.LibFileLibraryMapper;
import io.protone.library.repository.LibFileLibraryRepository;
import io.protone.library.service.LibFileLibraryService;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.protone.core.constans.MinioFoldersConstants.MEDIA_ITEM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 02/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibraryFileResourceImplTest {
    private static final String DEFAULT_PREFIX = "A";
    private static final String UPDATED_PREFIX = "B";

    private static final String DEFAULT_SHORTCUT = "AAA";
    private static final String UPDATED_SHORTCUT = "BBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_COUNTER = 1L;
    private static final Long UPDATED_COUNTER = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
    private static final String PUBLIC_URL_STRING = "test";
    @Autowired
    private LibFileLibraryRepository libFileLibraryRepository;

    @Autowired
    private LibFileLibraryMapper libFileLibraryMapper;

    @Autowired
    private LibFileLibraryService libFileLibraryService;

    @Mock
    private S3Client s3Client;

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

    @Mock
    private CorImageItemService corImageItemService;

    private MockMvc restLibLibraryMockMvc;

    private LibFileLibrary libLibrary;

    private CorNetwork corNetwork;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibFileLibrary createEntity(EntityManager em) {
        LibFileLibrary libLibrary = new LibFileLibrary()
                .prefix(DEFAULT_PREFIX)
                .shortcut(DEFAULT_SHORTCUT)
                .name(DEFAULT_NAME)
                .counter(DEFAULT_COUNTER)
                .description(DEFAULT_DESCRIPTION);
        return libLibrary;
    }

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        LibraryFileResourceImpl libLibraryResource = new LibraryFileResourceImpl();
        ReflectionTestUtils.setField(libFileLibraryService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libLibraryResource, "libFileLibraryService", libFileLibraryService);
        ReflectionTestUtils.setField(libLibraryResource, "libFileLibraryMapper", libFileLibraryMapper);
        ReflectionTestUtils.setField(libLibraryResource, "corNetworkService", corNetworkService);
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        when(s3Client.makeBucket(anyString(), anyString())).thenReturn(corNetwork.getShortcut() + MEDIA_ITEM + "testBucket");
        this.restLibLibraryMockMvc = MockMvcBuilders.standaloneSetup(libLibraryResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        libLibrary = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createLibLibrary() throws Exception {
        int databaseSizeBeforeCreate = libFileLibraryRepository.findAll().size();

        // Create the LibMediaLibrary
        LibFileLibraryDTO libLibraryDTO = libFileLibraryMapper.DB2DTO(libLibrary);

        restLibLibraryMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/network/{networkShortcut}/library/file", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
                .andExpect(status().isCreated());

        // Validate the LibMediaLibrary in the database
        List<LibFileLibrary> libLibraryList = libFileLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeCreate + 1);
        LibFileLibrary testLibLibrary = libLibraryList.get(libLibraryList.size() - 1);
        assertThat(testLibLibrary.getPrefix()).isEqualTo(DEFAULT_PREFIX);
        assertThat(testLibLibrary.getShortcut()).isEqualTo(DEFAULT_SHORTCUT);
        assertThat(testLibLibrary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibLibrary.getCounter()).isEqualTo(DEFAULT_COUNTER);
        assertThat(testLibLibrary.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLibLibraryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libFileLibraryRepository.findAll().size();

        // Create the LibMediaLibrary with an existing ID
        LibFileLibrary existingLibLibrary = new LibFileLibrary();
        existingLibLibrary.setId(1L);
        LibFileLibraryDTO existingLibLibraryDTO = libFileLibraryMapper.DB2DTO(existingLibLibrary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibLibraryMockMvc.perform(post("/api/v1/network/{networkShortcut}/library/file", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingLibLibraryDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibFileLibrary> libLibraryList = libFileLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrefixIsRequired() throws Exception {
        int databaseSizeBeforeTest = libFileLibraryRepository.findAll().size();
        // set the field null
        libLibrary.setPrefix(null);

        // Create the LibMediaLibrary, which fails.
        LibFileLibraryDTO libLibraryDTO = libFileLibraryMapper.DB2DTO(libLibrary);

        restLibLibraryMockMvc.perform(post("/api/v1/network/{networkShortcut}/library/file", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
                .andExpect(status().isBadRequest());

        List<LibFileLibrary> libLibraryList = libFileLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void checkShortcutIsRequired() throws Exception {
        int databaseSizeBeforeTest = libFileLibraryRepository.findAll().size();
        // set the field null
        libLibrary.setShortcut(null);

        // Create the LibMediaLibrary, which fails.
        LibFileLibraryDTO libLibraryDTO = libFileLibraryMapper.DB2DTO(libLibrary);

        restLibLibraryMockMvc.perform(post("/api/v1/network/{networkShortcut}/library/file", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
                .andExpect(status().isBadRequest());

        List<LibFileLibrary> libLibraryList = libFileLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libFileLibraryRepository.findAll().size();
        // set the field null
        libLibrary.setName(null);

        // Create the LibMediaLibrary, which fails.
        LibFileLibraryDTO libLibraryDTO = libFileLibraryMapper.DB2DTO(libLibrary);

        restLibLibraryMockMvc.perform(post("/api/v1/network/{networkShortcut}/library/file", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
                .andExpect(status().isBadRequest());

        List<LibFileLibrary> libLibraryList = libFileLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCounterIsRequired() throws Exception {
        int databaseSizeBeforeTest = libFileLibraryRepository.findAll().size();
        // set the field null
        libLibrary.setCounter(null);

        // Create the LibMediaLibrary, which fails.
        LibFileLibraryDTO libLibraryDTO = libFileLibraryMapper.DB2DTO(libLibrary);

        restLibLibraryMockMvc.perform(post("/api/v1/network/{networkShortcut}/library/file", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
                .andExpect(status().isBadRequest());

        List<LibFileLibrary> libLibraryList = libFileLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void getAllLibLibraries() throws Exception {
        // Initialize the database
        when(corImageItemService.saveImageItem(any())).thenReturn(null);
        libFileLibraryRepository.saveAndFlush(libLibrary.network(corNetwork));

        // Get all the libLibraryList
        restLibLibraryMockMvc.perform(get("/api/v1/network/{networkShortcut}/library/file?sort=id,desc", corNetwork.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(libLibrary.getId().intValue())))
                .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX.toString())))
                .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER.intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLibLibrary() throws Exception {
        // Initialize the database
        when(corImageItemService.saveImageItem(any())).thenReturn(null);
        libFileLibraryRepository.saveAndFlush(libLibrary.network(corNetwork).shortcut("123"));

        // Get the libMediaLibrary
        restLibLibraryMockMvc.perform(get("/api/v1/network/{networkShortcut}/library/file/{libraryPrefix}", corNetwork.getShortcut(), "123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(libLibrary.getId().intValue()))
                .andExpect(jsonPath("$.prefix").value(DEFAULT_PREFIX.toString()))
                .andExpect(jsonPath("$.shortcut").value("123"))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.counter").value(DEFAULT_COUNTER.intValue()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibLibrary() throws Exception {
        // Get the libMediaLibrary
        restLibLibraryMockMvc.perform(get("/api/v1/network/{networkShortcut}/library/file/{libraryPrefix}", corNetwork.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibLibrary() throws Exception {
        // Initialize the database
        libFileLibraryRepository.saveAndFlush(libLibrary.network(corNetwork).shortcut("Tst"));
        int databaseSizeBeforeUpdate = libFileLibraryRepository.findAll().size();

        // Update the libMediaLibrary
        LibFileLibrary updatedLibLibrary = libFileLibraryRepository.findOne(libLibrary.getId());
        updatedLibLibrary
                .prefix(UPDATED_PREFIX)
                .shortcut(UPDATED_SHORTCUT)
                .name(UPDATED_NAME)
                .counter(UPDATED_COUNTER)
                .description(UPDATED_DESCRIPTION);
        LibFileLibraryDTO libLibraryDTO = libFileLibraryMapper.DB2DTO((updatedLibLibrary));

        restLibLibraryMockMvc.perform(put("/api/v1/network/{networkShortcut}/library/file", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
                .andExpect(status().isOk());

        // Validate the LibMediaLibrary in the database
        List<LibFileLibrary> libLibraryList = libFileLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeUpdate);
        LibFileLibrary testLibLibrary = libLibraryList.get(libLibraryList.size() - 1);
        assertThat(testLibLibrary.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testLibLibrary.getShortcut()).isEqualTo(UPDATED_SHORTCUT);
        assertThat(testLibLibrary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibLibrary.getCounter()).isEqualTo(UPDATED_COUNTER);
        assertThat(testLibLibrary.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void updateNonExistingLibLibrary() throws Exception {
        int databaseSizeBeforeUpdate = libFileLibraryRepository.findAll().size();

        // Create the LibMediaLibrary
        LibFileLibraryDTO libLibraryDTO = libFileLibraryMapper.DB2DTO(libLibrary);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibLibraryMockMvc.perform(put("/api/v1/network/{networkShortcut}/library/file", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
                .andExpect(status().isCreated());

        // Validate the LibMediaLibrary in the database
        List<LibFileLibrary> libLibraryList = libFileLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibLibrary() throws Exception {
        // Initialize the database
        libFileLibraryRepository.saveAndFlush(libLibrary.network(corNetwork).shortcut("Tet"));
        int databaseSizeBeforeDelete = libFileLibraryRepository.findAll().size();

        // Get the libMediaLibrary
        restLibLibraryMockMvc.perform(delete("/api/v1/network/{networkShortcut}/library/file/{libraryPrefix}", corNetwork.getShortcut(), libLibrary.getShortcut())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LibFileLibrary> libLibraryList = libFileLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibMediaLibrary.class);
    }

}
