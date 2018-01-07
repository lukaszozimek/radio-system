package io.protone.application.web.api.library;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.library.impl.LibAlbumResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorImageItemRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorImageItemService;
import io.protone.library.api.dto.LibAlbumDTO;
import io.protone.library.domain.LibAlbum;
import io.protone.library.domain.enumeration.LibAlbumTypeEnum;
import io.protone.library.mapper.LibAlbumMapper;
import io.protone.library.repository.LibAlbumRepository;
import io.protone.library.service.LibAlbumService;
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
public class LibAlbumResourceImplTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
    private static final String PUBLIC_URL_STRING = "test";
    @Autowired
    private LibAlbumRepository libAlbumRepository;

    @Autowired
    private LibAlbumMapper libAlbumMapper;

    @Autowired
    private LibAlbumService libAlbumService;

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

    private MockMvc restLibAlbumMockMvc;

    private LibAlbum libAlbum;

    private CorOrganization corOrganization;

    private CorChannel corChannel;

    private CorImageItem corImageItem;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibAlbum createEntity(EntityManager em) {
        LibAlbum libAlbum = new LibAlbum()
                .name(DEFAULT_NAME)
                .albumType(LibAlbumTypeEnum.AT_ALBUM)
                .description(DEFAULT_DESCRIPTION);
        return libAlbum;
    }

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        LibAlbumResourceImpl libLibraryResource = new LibAlbumResourceImpl();
        corImageItem = new CorImageItem().publicUrl(PUBLIC_URL_STRING).name("test");
        corImageItemRepository.saveAndFlush(corImageItem);
        when(corImageItemService.saveImageItem(any(), anyObject())).thenReturn(corImageItem);
        ReflectionTestUtils.setField(libAlbumService, "corImageItemService", corImageItemService);
        ReflectionTestUtils.setField(corImageItemService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libLibraryResource, "libAlbumService", libAlbumService);
        ReflectionTestUtils.setField(libLibraryResource, "libAlbumMapper", libAlbumMapper);
        ReflectionTestUtils.setField(libLibraryResource, "corChannelService", corChannelService);

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

        when(s3Client.makeBucket(anyString(), anyString())).thenReturn(corChannel.getShortcut() + MEDIA_ITEM + "testBucket");
        this.restLibAlbumMockMvc = MockMvcBuilders.standaloneSetup(libLibraryResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        libAlbum = createEntity(em).channel(corChannel);
    }

    @Test
    @Transactional
    public void createLibAlbum() throws Exception {
        int databaseSizeBeforeCreate = libAlbumRepository.findAll().size();

        // Create the LibAlbum
        LibAlbumDTO libLibraryDTO = libAlbumMapper.DB2DTO(libAlbum);
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libAlbumDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(libLibraryDTO));

        restLibAlbumMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/album", corOrganization.getShortcut(), corChannel.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isCreated());

        // Validate the LibAlbum in the database
        List<LibAlbum> libLibraryList = libAlbumRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeCreate + 1);
        LibAlbum testLibAlbum = libLibraryList.get(libLibraryList.size() - 1);
        assertThat(testLibAlbum.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibAlbum.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLibAlbumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libAlbumRepository.findAll().size();

        // Create the LibAlbum with an existing ID
        LibAlbum existingLibAlbum = new LibAlbum();
        existingLibAlbum.setId(1L);
        LibAlbumDTO existingLibAlbumDTO = libAlbumMapper.DB2DTO(existingLibAlbum);
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libAlbumDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(existingLibAlbumDTO));

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibAlbumMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/album", corOrganization.getShortcut(), corChannel.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibAlbum> libLibraryList = libAlbumRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libAlbumRepository.findAll().size();
        // set the field null
        libAlbum.setName(null);

        // Create the LibAlbum, which fails.
        LibAlbumDTO libLibraryDTO = libAlbumMapper.DB2DTO(libAlbum);
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libAlbumDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(libLibraryDTO));

        restLibAlbumMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/album", corOrganization.getShortcut(), corChannel.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isBadRequest());

        List<LibAlbum> libLibraryList = libAlbumRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void getAllLibLibraries() throws Exception {
        // Initialize the database
        when(corImageItemService.saveImageItem(any(), anyObject())).thenReturn(null);
        libAlbumRepository.saveAndFlush(libAlbum.channel(corChannel));

        // Get all the libLibraryList
        restLibAlbumMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/album?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(libAlbum.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLibAlbum() throws Exception {
        // Initialize the database
        when(corImageItemService.saveImageItem(any(), anyObject())).thenReturn(null);
        libAlbumRepository.saveAndFlush(libAlbum.channel(corChannel));

        // Get the libAlbum
        restLibAlbumMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/album/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), libAlbum.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(libAlbum.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllLibLibrariesWithImages() throws Exception {
        // Initialize the database

        libAlbumRepository.saveAndFlush(libAlbum.channel(corChannel).mainImage(corImageItem));

        // Get all the libLibraryList
        restLibAlbumMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/album?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(libAlbum.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].publicUrl").value(hasItem(PUBLIC_URL_STRING.toString())));
    }

    @Test
    @Transactional
    public void getLibAlbumWithImage() throws Exception {
        // Initialize the database
        libAlbumRepository.saveAndFlush(libAlbum.channel(corChannel).mainImage(corImageItem));

        // Get the libAlbum
        restLibAlbumMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/album/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), libAlbum.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(libAlbum.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.publicUrl").value(PUBLIC_URL_STRING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibAlbum() throws Exception {
        // Get the libAlbum
        restLibAlbumMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/album/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibAlbum() throws Exception {
        // Initialize the database
        libAlbumRepository.saveAndFlush(libAlbum.channel(corChannel));
        int databaseSizeBeforeUpdate = libAlbumRepository.findAll().size();

        // Update the libAlbum
        LibAlbum updatedLibAlbum = libAlbumRepository.findOne(libAlbum.getId());
        updatedLibAlbum
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        LibAlbumDTO libLibraryDTO = libAlbumMapper.DB2DTO((updatedLibAlbum));

        restLibAlbumMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/album", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
                .andExpect(status().isOk());

        // Validate the LibAlbum in the database
        List<LibAlbum> libLibraryList = libAlbumRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeUpdate);
        LibAlbum testLibAlbum = libLibraryList.get(libLibraryList.size() - 1);
        assertThat(testLibAlbum.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibAlbum.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateLibAlbumWithImage() throws Exception {
        // Initialize the database
        libAlbumRepository.saveAndFlush(libAlbum.channel(corChannel));
        int databaseSizeBeforeUpdate = libAlbumRepository.findAll().size();

        // Update the libAlbum
        LibAlbum updatedLibAlbum = libAlbumRepository.findOne(libAlbum.getId());
        updatedLibAlbum
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        LibAlbumDTO libLibraryDTO = libAlbumMapper.DB2DTO((updatedLibAlbum));
        MockMultipartFile emptyFile = new MockMultipartFile("cover", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/crm/customer/logo.png"));
        MockMultipartFile jsonFile = new MockMultipartFile("libAlbumDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(libLibraryDTO));

        restLibAlbumMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/album/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), libAlbum.getId())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isOk());

        // Validate the LibAlbum in the database
        List<LibAlbum> libLibraryList = libAlbumRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeUpdate);
        LibAlbum testLibAlbum = libLibraryList.get(libLibraryList.size() - 1);
        assertThat(testLibAlbum.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibAlbum.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLibAlbum() throws Exception {
        int databaseSizeBeforeUpdate = libAlbumRepository.findAll().size();

        // Create the LibAlbum
        LibAlbumDTO libLibraryDTO = libAlbumMapper.DB2DTO(libAlbum);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibAlbumMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/album", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
                .andExpect(status().isCreated());

        // Validate the LibAlbum in the database
        List<LibAlbum> libLibraryList = libAlbumRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibAlbum() throws Exception {
        // Initialize the database
        libAlbumRepository.saveAndFlush(libAlbum.channel(corChannel));
        int databaseSizeBeforeDelete = libAlbumRepository.findAll().size();

        // Get the libAlbum
        restLibAlbumMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/album/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), libAlbum.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LibAlbum> libLibraryList = libAlbumRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibAlbum.class);
    }

}
