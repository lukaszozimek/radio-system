package io.protone.application.web.api.library;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.library.impl.LibArtistResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorImageItemRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorImageItemService;
import io.protone.library.api.dto.LibArtistDTO;
import io.protone.library.domain.LibArtist;
import io.protone.library.mapper.LibArtistMapper;
import io.protone.library.repository.LibArtistRepository;
import io.protone.library.service.LibArtistService;
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
public class LibArtistResourceImplTest {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
    private static final String PUBLIC_URL_STRING = "test";
    @Autowired
    private LibArtistRepository libArtistRepository;

    @Autowired
    private LibArtistMapper libArtistMapper;

    @Autowired
    private LibArtistService libArtistService;

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

    private MockMvc restLibArtistMockMvc;

    private LibArtist libArtist;

    private CorOrganization corOrganization;

    private CorChannel corChannel;
    
    private CorImageItem corImageItem;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibArtist createEntity(EntityManager em) {
        LibArtist libLibrary = new LibArtist()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return libLibrary;
    }

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        LibArtistResourceImpl libLibraryResource = new LibArtistResourceImpl();
        corImageItem = new CorImageItem().publicUrl(PUBLIC_URL_STRING).name("test");
        corImageItemRepository.saveAndFlush(corImageItem);
        when(corImageItemService.saveImageItem(any(), anyObject())).thenReturn(corImageItem);
        ReflectionTestUtils.setField(corImageItemService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libArtistService, "corImageItemService", corImageItemService);
        ReflectionTestUtils.setField(libLibraryResource, "libArtistService", libArtistService);
        ReflectionTestUtils.setField(libLibraryResource, "libArtistMapper", libArtistMapper);
        ReflectionTestUtils.setField(libLibraryResource, "corChannelService", corChannelService);

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

        when(s3Client.makeBucket(anyString(), anyString())).thenReturn(corChannel.getShortcut() + MEDIA_ITEM + "testBucket");
        this.restLibArtistMockMvc = MockMvcBuilders.standaloneSetup(libLibraryResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        libArtist = createEntity(em).channel(corChannel);
    }

    @Test
    @Transactional
    public void createLibArtist() throws Exception {
        int databaseSizeBeforeCreate = libArtistRepository.findAll().size();

        // Create the LibArtist
        LibArtistDTO libLibraryDTO = libArtistMapper.DB2DTO(libArtist);
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libArtistDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(libLibraryDTO));

        restLibArtistMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/artist", corOrganization.getShortcut(), corChannel.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isCreated());

        // Validate the LibArtist in the database
        List<LibArtist> libLibraryList = libArtistRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeCreate + 1);
        LibArtist testLibArtist = libLibraryList.get(libLibraryList.size() - 1);
        assertThat(testLibArtist.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibArtist.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLibArtistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libArtistRepository.findAll().size();

        // Create the LibArtist with an existing ID
        LibArtist existingLibArtist = new LibArtist();
        existingLibArtist.setId(1L);
        LibArtistDTO existingLibArtistDTO = libArtistMapper.DB2DTO(existingLibArtist);
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libArtistDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(existingLibArtistDTO));

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibArtistMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/artist", corOrganization.getShortcut(), corChannel.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibArtist> libLibraryList = libArtistRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libArtistRepository.findAll().size();
        // set the field null
        libArtist.setName(null);

        // Create the LibArtist, which fails.
        LibArtistDTO libLibraryDTO = libArtistMapper.DB2DTO(libArtist);
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libArtistDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(libLibraryDTO));

        restLibArtistMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/artist", corOrganization.getShortcut(), corChannel.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isBadRequest());

        List<LibArtist> libLibraryList = libArtistRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void getAllLibLibraries() throws Exception {
        // Initialize the database
        when(corImageItemService.saveImageItem(any(), anyObject())).thenReturn(null);
        libArtistRepository.saveAndFlush(libArtist.channel(corChannel));

        // Get all the libLibraryList
        restLibArtistMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/artist?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(libArtist.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLibArtist() throws Exception {
        // Initialize the database
        when(corImageItemService.saveImageItem(any(), anyObject())).thenReturn(null);
        libArtistRepository.saveAndFlush(libArtist.channel(corChannel));

        // Get the libArtist
        restLibArtistMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/artist/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), libArtist.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(libArtist.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllLibLibrariesWithImages() throws Exception {
        // Initialize the database

        libArtistRepository.saveAndFlush(libArtist.channel(corChannel).mainImage(corImageItem));

        // Get all the libLibraryList
        restLibArtistMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/artist?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(libArtist.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].publicUrl").value(hasItem(PUBLIC_URL_STRING.toString())));
    }

    @Test
    @Transactional
    public void getLibArtistWithImage() throws Exception {
        // Initialize the database
        libArtistRepository.saveAndFlush(libArtist.channel(corChannel).mainImage(corImageItem));

        // Get the libArtist
        restLibArtistMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/artist/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), libArtist.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(libArtist.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.publicUrl").value(PUBLIC_URL_STRING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibArtist() throws Exception {
        // Get the libArtist
        restLibArtistMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/artist/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibArtist() throws Exception {
        // Initialize the database
        libArtistRepository.saveAndFlush(libArtist.channel(corChannel));
        int databaseSizeBeforeUpdate = libArtistRepository.findAll().size();

        // Update the libArtist
        LibArtist updatedLibArtist = libArtistRepository.findOne(libArtist.getId());
        updatedLibArtist
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        LibArtistDTO libLibraryDTO = libArtistMapper.DB2DTO((updatedLibArtist));

        restLibArtistMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/artist", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
                .andExpect(status().isOk());

        // Validate the LibArtist in the database
        List<LibArtist> libLibraryList = libArtistRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeUpdate);
        LibArtist testLibArtist = libLibraryList.get(libLibraryList.size() - 1);
        assertThat(testLibArtist.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibArtist.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateLibArtistWithImage() throws Exception {
        // Initialize the database
        libArtistRepository.saveAndFlush(libArtist.channel(corChannel));
        int databaseSizeBeforeUpdate = libArtistRepository.findAll().size();

        // Update the libArtist
        LibArtist updatedLibArtist = libArtistRepository.findOne(libArtist.getId());
        updatedLibArtist
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        LibArtistDTO libLibraryDTO = libArtistMapper.DB2DTO((updatedLibArtist));
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libArtistDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(libLibraryDTO));

        restLibArtistMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/artist/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), libArtist.getId())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isOk());

        // Validate the LibArtist in the database
        List<LibArtist> libLibraryList = libArtistRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeUpdate);
        LibArtist testLibArtist = libLibraryList.get(libLibraryList.size() - 1);
        assertThat(testLibArtist.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibArtist.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLibArtist() throws Exception {
        int databaseSizeBeforeUpdate = libArtistRepository.findAll().size();

        // Create the LibArtist
        LibArtistDTO libLibraryDTO = libArtistMapper.DB2DTO(libArtist);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibArtistMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/artist", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
                .andExpect(status().isCreated());

        // Validate the LibArtist in the database
        List<LibArtist> libLibraryList = libArtistRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibArtist() throws Exception {
        // Initialize the database
        libArtistRepository.saveAndFlush(libArtist.channel(corChannel));
        int databaseSizeBeforeDelete = libArtistRepository.findAll().size();

        // Get the libArtist
        restLibArtistMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/artist/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), libArtist.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LibArtist> libLibraryList = libArtistRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibArtist.class);
    }

}
