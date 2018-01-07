package io.protone.application.web.api.library;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.library.impl.LibraryMediaResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorImageItemRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorImageItemService;
import io.protone.library.api.dto.LibMediaLibraryDTO;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.mapper.LibLibraryMediaMapper;
import io.protone.library.repository.LibLibraryRepository;
import io.protone.library.service.LibLibraryMediaService;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.protone.application.util.TestConstans.*;
import static io.protone.core.constans.MinioFoldersConstants.MEDIA_ITEM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 02/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibraryMediaResourceImplTest {
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
    private LibLibraryRepository libLibraryRepository;

    @Autowired
    private LibLibraryMediaMapper libLibraryMediaMapper;

    @Autowired
    private LibLibraryMediaService libLibraryMediaService;

    @Mock
    private S3Client s3Client;

    @Autowired
    private CorChannelService corChannelService;

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

    @Autowired
    private CorImageItemRepository corImageItemRepository;

    private MockMvc restLibLibraryMockMvc;

    private LibMediaLibrary libMediaLibrary;

    private CorOrganization corOrganization;

    private CorChannel corChannel;

    private CorImageItem corImageItem;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibMediaLibrary createEntity(EntityManager em) {
        LibMediaLibrary libMediaLibrary = new LibMediaLibrary()
                .prefix(DEFAULT_PREFIX)
                .shortcut(DEFAULT_SHORTCUT)
                .name(DEFAULT_NAME)
                .counter(DEFAULT_COUNTER)
                .description(DEFAULT_DESCRIPTION);
        return libMediaLibrary;
    }

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        LibraryMediaResourceImpl libLibraryResource = new LibraryMediaResourceImpl();
        corImageItem = new CorImageItem().publicUrl(PUBLIC_URL_STRING).name("test");
        corImageItemRepository.saveAndFlush(corImageItem);
        when(corImageItemService.saveImageItem(any(), anyObject())).thenReturn(corImageItem);
        ReflectionTestUtils.setField(libLibraryMediaService, "corImageItemService", corImageItemService);
        ReflectionTestUtils.setField(libLibraryMediaService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libLibraryResource, "libLibraryMediaService", libLibraryMediaService);
        ReflectionTestUtils.setField(libLibraryResource, "libLibraryMediaMapper", libLibraryMediaMapper);
        ReflectionTestUtils.setField(libLibraryResource, "corChannelService", corChannelService);

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

        when(s3Client.makeBucket(anyString(), anyString())).thenReturn(corChannel.getShortcut() + MEDIA_ITEM + "testBucket");
        this.restLibLibraryMockMvc = MockMvcBuilders.standaloneSetup(libLibraryResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        libMediaLibrary = createEntity(em).channel(corChannel);
    }

    @Test
    @Transactional
    public void createLibLibrary() throws Exception {
        int databaseSizeBeforeCreate = libLibraryRepository.findAll().size();

        // Create the LibMediaLibrary
        LibMediaLibraryDTO libMediaLibraryDTO = libLibraryMediaMapper.DB2DTO(libMediaLibrary.channel(corChannel));
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libraryDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(libMediaLibraryDTO));

        restLibLibraryMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media", corOrganization.getShortcut(), corChannel.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isCreated());

        // Validate the LibMediaLibrary in the database
        List<LibMediaLibrary> libMediaLibraryList = libLibraryRepository.findAll();
        assertThat(libMediaLibraryList).hasSize(databaseSizeBeforeCreate + 1);
        LibMediaLibrary testLibMediaLibrary = libMediaLibraryList.get(libMediaLibraryList.size() - 1);
        assertThat(testLibMediaLibrary.getPrefix()).isEqualTo(DEFAULT_PREFIX);
        assertThat(testLibMediaLibrary.getShortcut()).isEqualTo(DEFAULT_SHORTCUT);
        assertThat(testLibMediaLibrary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibMediaLibrary.getCounter()).isEqualTo(DEFAULT_COUNTER);
        assertThat(testLibMediaLibrary.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLibLibraryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libLibraryRepository.findAll().size();

        // Create the LibMediaLibrary with an existing ID
        LibMediaLibrary existingLibMediaLibrary = new LibMediaLibrary();
        existingLibMediaLibrary.setId(1L);
        LibMediaLibraryDTO existingLibMediaLibraryDTO = libLibraryMediaMapper.DB2DTO(existingLibMediaLibrary);
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libraryDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(existingLibMediaLibraryDTO));

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibLibraryMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media", corOrganization.getShortcut(), corChannel.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibMediaLibrary> libMediaLibraryList = libLibraryRepository.findAll();
        assertThat(libMediaLibraryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrefixIsRequired() throws Exception {
        int databaseSizeBeforeTest = libLibraryRepository.findAll().size();
        // set the field null
        libMediaLibrary.setPrefix(null);

        // Create the LibMediaLibrary, which fails.
        LibMediaLibraryDTO libMediaLibraryDTO = libLibraryMediaMapper.DB2DTO(libMediaLibrary);
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libraryDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(libMediaLibraryDTO));

        restLibLibraryMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media", corOrganization.getShortcut(), corChannel.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isBadRequest());

        List<LibMediaLibrary> libMediaLibraryList = libLibraryRepository.findAll();
        assertThat(libMediaLibraryList).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void checkShortcutIsRequired() throws Exception {
        int databaseSizeBeforeTest = libLibraryRepository.findAll().size();
        // set the field null
        libMediaLibrary.setShortcut(null);

        // Create the LibMediaLibrary, which fails.
        LibMediaLibraryDTO libMediaLibraryDTO = libLibraryMediaMapper.DB2DTO(libMediaLibrary);
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libraryDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(libMediaLibraryDTO));

        restLibLibraryMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media", corOrganization.getShortcut(), corChannel.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isBadRequest());

        List<LibMediaLibrary> libMediaLibraryList = libLibraryRepository.findAll();
        assertThat(libMediaLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libLibraryRepository.findAll().size();
        // set the field null
        libMediaLibrary.setName(null);

        // Create the LibMediaLibrary, which fails.
        LibMediaLibraryDTO libMediaLibraryDTO = libLibraryMediaMapper.DB2DTO(libMediaLibrary);
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libraryDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(libMediaLibraryDTO));

        restLibLibraryMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media", corOrganization.getShortcut(), corChannel.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isBadRequest());

        List<LibMediaLibrary> libMediaLibraryList = libLibraryRepository.findAll();
        assertThat(libMediaLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCounterIsRequired() throws Exception {
        int databaseSizeBeforeTest = libLibraryRepository.findAll().size();
        // set the field null
        libMediaLibrary.setCounter(null);

        // Create the LibMediaLibrary, which fails.
        LibMediaLibraryDTO libMediaLibraryDTO = libLibraryMediaMapper.DB2DTO(libMediaLibrary);
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libraryDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(libMediaLibraryDTO));

        restLibLibraryMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media", corOrganization.getShortcut(), corChannel.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isBadRequest());

        List<LibMediaLibrary> libMediaLibraryList = libLibraryRepository.findAll();
        assertThat(libMediaLibraryList).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void getAllLibLibraries() throws Exception {
        // Initialize the database
        when(corImageItemService.saveImageItem(any(), anyObject())).thenReturn(null);
        libLibraryRepository.saveAndFlush(libMediaLibrary.channel(corChannel));

        // Get all the libLibraryList
        restLibLibraryMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(libMediaLibrary.getId().intValue())))
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
        when(corImageItemService.saveImageItem(any(), anyObject())).thenReturn(null);
        libLibraryRepository.saveAndFlush(libMediaLibrary.channel(corChannel).shortcut("123"));

        // Get the libMediaLibrary
        restLibLibraryMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media/{libraryPrefix}", corOrganization.getShortcut(), corChannel.getShortcut(), "123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(libMediaLibrary.getId().intValue()))
                .andExpect(jsonPath("$.prefix").value(DEFAULT_PREFIX.toString()))
                .andExpect(jsonPath("$.shortcut").value("123"))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.counter").value(DEFAULT_COUNTER.intValue()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllLibLibrariesWithImages() throws Exception {
        // Initialize the database

        libLibraryRepository.saveAndFlush(libMediaLibrary.channel(corChannel).mainImage(corImageItem));

        // Get all the libLibraryList
        restLibLibraryMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(libMediaLibrary.getId().intValue())))
                .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX.toString())))
                .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER.intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].publicUrl").value(hasItem(PUBLIC_URL_STRING.toString())));
    }

    @Test
    @Transactional
    public void getLibLibraryWithImage() throws Exception {
        // Initialize the database
        libLibraryRepository.saveAndFlush(libMediaLibrary.channel(corChannel).shortcut("123").mainImage(corImageItem));

        // Get the libMediaLibrary
        restLibLibraryMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media/{libraryPrefix}", corOrganization.getShortcut(), corChannel.getShortcut(), "123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(libMediaLibrary.getId().intValue()))
                .andExpect(jsonPath("$.prefix").value(DEFAULT_PREFIX.toString()))
                .andExpect(jsonPath("$.shortcut").value("123"))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.counter").value(DEFAULT_COUNTER.intValue()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.publicUrl").value(PUBLIC_URL_STRING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibLibrary() throws Exception {
        // Get the libMediaLibrary
        restLibLibraryMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media/{libraryPrefix}", corOrganization.getShortcut(), corChannel.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibLibrary() throws Exception {
        // Initialize the database
        libLibraryRepository.saveAndFlush(libMediaLibrary.channel(corChannel).shortcut("Tst"));
        int databaseSizeBeforeUpdate = libLibraryRepository.findAll().size();

        // Update the libMediaLibrary
        LibMediaLibrary updatedLibMediaLibrary = libLibraryRepository.findOne(libMediaLibrary.getId());
        updatedLibMediaLibrary
                .prefix(UPDATED_PREFIX)
                .shortcut(UPDATED_SHORTCUT)
                .name(UPDATED_NAME)
                .counter(UPDATED_COUNTER)
                .description(UPDATED_DESCRIPTION);
        LibMediaLibraryDTO libMediaLibraryDTO = libLibraryMediaMapper.DB2DTO((updatedLibMediaLibrary));

        restLibLibraryMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libMediaLibraryDTO)))
                .andExpect(status().isOk());

        // Validate the LibMediaLibrary in the database
        List<LibMediaLibrary> libMediaLibraryList = libLibraryRepository.findAll();
        assertThat(libMediaLibraryList).hasSize(databaseSizeBeforeUpdate);
        LibMediaLibrary testLibMediaLibrary = libMediaLibraryList.get(libMediaLibraryList.size() - 1);
        assertThat(testLibMediaLibrary.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testLibMediaLibrary.getShortcut()).isEqualTo(UPDATED_SHORTCUT);
        assertThat(testLibMediaLibrary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibMediaLibrary.getCounter()).isEqualTo(UPDATED_COUNTER);
        assertThat(testLibMediaLibrary.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateLibLibraryWithImage() throws Exception {
        // Initialize the database
        libLibraryRepository.saveAndFlush(libMediaLibrary.channel(corChannel).shortcut("Tst"));
        int databaseSizeBeforeUpdate = libLibraryRepository.findAll().size();

        // Update the libMediaLibrary
        LibMediaLibrary updatedLibMediaLibrary = libLibraryRepository.findOne(libMediaLibrary.getId());
        updatedLibMediaLibrary
                .prefix(UPDATED_PREFIX)
                .shortcut(UPDATED_SHORTCUT)
                .name(UPDATED_NAME)
                .counter(UPDATED_COUNTER)
                .description(UPDATED_DESCRIPTION);
        LibMediaLibraryDTO libMediaLibraryDTO = libLibraryMediaMapper.DB2DTO((updatedLibMediaLibrary));
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libraryDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(libMediaLibraryDTO));

        restLibLibraryMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media/{libraryPrefix}", corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isOk());

        // Validate the LibMediaLibrary in the database
        List<LibMediaLibrary> libMediaLibraryList = libLibraryRepository.findAll();
        assertThat(libMediaLibraryList).hasSize(databaseSizeBeforeUpdate);
        LibMediaLibrary testLibMediaLibrary = libMediaLibraryList.get(libMediaLibraryList.size() - 1);
        assertThat(testLibMediaLibrary.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testLibMediaLibrary.getShortcut()).isEqualTo(UPDATED_SHORTCUT);
        assertThat(testLibMediaLibrary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibMediaLibrary.getCounter()).isEqualTo(UPDATED_COUNTER);
        assertThat(testLibMediaLibrary.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLibLibrary() throws Exception {
        int databaseSizeBeforeUpdate = libLibraryRepository.findAll().size();

        // Create the LibMediaLibrary
        LibMediaLibraryDTO libMediaLibraryDTO = libLibraryMediaMapper.DB2DTO(libMediaLibrary.channel(corChannel));

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibLibraryMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libMediaLibraryDTO)))
                .andExpect(status().isCreated());

        // Validate the LibMediaLibrary in the database
        List<LibMediaLibrary> libMediaLibraryList = libLibraryRepository.findAll();
        assertThat(libMediaLibraryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteLibLibrary() throws Exception {
        // Initialize the database
        libLibraryRepository.saveAndFlush(libMediaLibrary.channel(corChannel).shortcut("Tet"));
        int databaseSizeBeforeDelete = libLibraryRepository.findAll().size();

        // Get the libMediaLibrary
        restLibLibraryMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media/{libraryPrefix}", corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LibMediaLibrary> libMediaLibraryList = libLibraryRepository.findAll();
        assertThat(libMediaLibraryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibMediaLibrary.class);
    }

}
