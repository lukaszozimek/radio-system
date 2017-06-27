package io.protone.web.api.library;

import com.google.common.collect.Sets;
import io.protone.ProtoneApp;
import io.protone.application.config.s3.S3Client;
import io.protone.application.config.s3.exceptions.DeleteException;
import io.protone.application.config.s3.exceptions.S3Exception;
import io.protone.application.config.s3.exceptions.UploadException;
import io.protone.core.repository.cor.CorUserRepository;
import io.protone.domain.enumeration.LibItemStateEnum;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.cor.CorUserService;
import io.protone.service.library.LibItemService;
import io.protone.util.TestUtil;
import io.protone.web.api.library.impl.LibMediaItemResourceImpl;
import io.protone.web.rest.dto.library.LibMediaItemDTO;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.LibItemMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import service.file.LibFileService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static io.protone.application.security.AuthoritiesConstants.ADMIN;
import static io.protone.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 05.05.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibMediaItemResourceTest {

    private static final String DEFAULT_IDX = "AAAAAAAAAA";
    private static final String UPDATED_IDX = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LibItemTypeEnum DEFAULT_ITEM_TYPE = LibItemTypeEnum.IT_AUDIO;
    private static final LibItemTypeEnum UPDATED_ITEM_TYPE = LibItemTypeEnum.IT_VIDEO;

    private static final Double DEFAULT_LENGTH = 1D;
    private static final Double UPDATED_LENGTH = 2D;

    private static final LibItemStateEnum DEFAULT_STATE = LibItemStateEnum.IS_NEW;
    private static final LibItemStateEnum UPDATED_STATE = LibItemStateEnum.IS_POSTPROCESS;

    private static final String DEFAULT_COMMAND = "AAAAAAAAAA";
    private static final String UPDATED_COMMAND = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
    @Mock
    protected S3Client s3Client;
    @Mock
    protected CorUserService corUserService;
    @Inject
    private LibItemService itemService;
    @Inject
    private LibItemMapper libMediaItemMapper;
    @Inject
    private LibMediaItemRepository libMediaItemRepository;
    @Inject
    private CorNetworkService corNetworkService;
    @Autowired
    @Qualifier("libAudioFileService")
    private LibFileService audioFileService;
    @Autowired
    @Qualifier("libVideoFileService")
    private LibFileService videoFileService;
    @Autowired
    @Qualifier("libImageFileService")
    private LibFileService imageFileService;
    @Autowired
    @Qualifier("libDocumentFileService")
    private LibFileService libDocumentFileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;
    @Autowired
    private CorUserRepository corUserRepository;
    @Autowired
    private EntityManager em;

    private MockMvc restLibMediaItemMockMvc;

    private LibMediaItem libMediaItem;

    private CorNetwork corNetwork;

    private LibLibrary libLibrary;

    private PodamFactory factory;
    private CorUser corUser;


    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibMediaItem createEntity(EntityManager em) {
        LibMediaItem libMediaItem = new LibMediaItem()
            .idx(DEFAULT_IDX)
            .name(DEFAULT_NAME)
            .itemType(DEFAULT_ITEM_TYPE)
            .length(DEFAULT_LENGTH)
            .state(DEFAULT_STATE)
            .command(DEFAULT_COMMAND)
            .description(DEFAULT_DESCRIPTION);
        return libMediaItem;
    }

    @Before
    public void setup() throws S3Exception, DeleteException, UploadException {
        MockitoAnnotations.initMocks(this);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);
        libLibrary = new LibLibrary().shortcut("tes").network(corNetwork);
        libLibrary.setId(1L);
        factory = new PodamFactoryImpl();
        corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setChannels(null);
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser = corUserRepository.saveAndFlush(corUser);
        doNothing().when(s3Client).delete(anyObject());
        doNothing().when(s3Client).upload(anyString(), anyObject(), anyString());
        when(corUserService.getUserWithAuthoritiesByLogin(anyString())).thenReturn(Optional.of(corUser));
        LibMediaItemResourceImpl libMediaItemResource = new LibMediaItemResourceImpl();

        ReflectionTestUtils.setField(audioFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(audioFileService, "corUserService", corUserService);

        ReflectionTestUtils.setField(videoFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(videoFileService, "corUserService", corUserService);

        ReflectionTestUtils.setField(imageFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(imageFileService, "corUserService", corUserService);

        ReflectionTestUtils.setField(libDocumentFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libDocumentFileService, "corUserService", corUserService);

        ReflectionTestUtils.setField(itemService, "audioFileService", audioFileService);
        ReflectionTestUtils.setField(itemService, "videoFileService", videoFileService);
        ReflectionTestUtils.setField(itemService, "imageFileService", imageFileService);
        ReflectionTestUtils.setField(itemService, "libDocumentFileService", libDocumentFileService);


        ReflectionTestUtils.setField(libMediaItemResource, "libItemService", itemService);
        ReflectionTestUtils.setField(libMediaItemResource, "libMediaItemMapper", libMediaItemMapper);
        ReflectionTestUtils.setField(libMediaItemResource, "corNetworkService", corNetworkService);

        this.restLibMediaItemMockMvc = MockMvcBuilders.standaloneSetup(libMediaItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        libMediaItem = createEntity(em);
    }


    @Test
    @Transactional
    public void checkIdxIsRequired() throws Exception {
        int databaseSizeBeforeTest = libMediaItemRepository.findAll().size();
        // set the field null
        libMediaItem.setIdx(null);

        // Create the LibMediaItem, which fails.
        LibMediaItemDTO libMediaItemDTO = libMediaItemMapper.DB2DTO(libMediaItem.library(libLibrary));

        restLibMediaItemMockMvc.perform(put("/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item", corNetwork.getShortcut(), libLibrary.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMediaItemDTO)))
            .andExpect(status().isBadRequest());

        List<LibMediaItem> libMediaItemList = libMediaItemRepository.findAll();
        assertThat(libMediaItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libMediaItemRepository.findAll().size();
        // set the field null
        libMediaItem.setName(null);

        // Create the LibMediaItem, which fails.
        LibMediaItemDTO libMediaItemDTO = libMediaItemMapper.DB2DTO(libMediaItem);

        restLibMediaItemMockMvc.perform(put("/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item", corNetwork.getShortcut(), libLibrary.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMediaItemDTO)))
            .andExpect(status().isBadRequest());

        List<LibMediaItem> libMediaItemList = libMediaItemRepository.findAll();
        assertThat(libMediaItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibMediaItems() throws Exception {
        // Initialize the database
        libMediaItemRepository.saveAndFlush(libMediaItem.library(libLibrary).network(corNetwork));

        // Get all the libMediaItemList
        restLibMediaItemMockMvc.perform(get("/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item?sort=id,desc", corNetwork.getShortcut(), libLibrary.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libMediaItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].idx").value(hasItem(DEFAULT_IDX.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].command").value(hasItem(DEFAULT_COMMAND.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLibMediaItem() throws Exception {
        // Initialize the database
        libMediaItemRepository.saveAndFlush(libMediaItem.library(libLibrary).network(corNetwork));

        // Get the libMediaItem
        restLibMediaItemMockMvc.perform(get("/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item/{id}", corNetwork.getShortcut(), libLibrary.getShortcut(), libMediaItem.getIdx()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libMediaItem.getId().intValue()))
            .andExpect(jsonPath("$.idx").value(DEFAULT_IDX.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.itemType").value(DEFAULT_ITEM_TYPE.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.command").value(DEFAULT_COMMAND.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibMediaItem() throws Exception {
        // Get the libMediaItem
        restLibMediaItemMockMvc.perform(get("/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item/{id}", corNetwork.getShortcut(), libLibrary.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibMediaItem() throws Exception {
        // Initialize the database
        libMediaItemRepository.saveAndFlush(libMediaItem.library(libLibrary).network(corNetwork));
        int databaseSizeBeforeUpdate = libMediaItemRepository.findAll().size();

        // Update the libMediaItem
        LibMediaItem updatedLibMediaItem = libMediaItemRepository.findOne(libMediaItem.getId());
        updatedLibMediaItem
            .idx(UPDATED_IDX)
            .name(UPDATED_NAME)
            .itemType(UPDATED_ITEM_TYPE)
            .length(UPDATED_LENGTH)
            .state(UPDATED_STATE)
            .command(UPDATED_COMMAND)
            .description(UPDATED_DESCRIPTION);
        LibMediaItemDTO libMediaItemDTO = libMediaItemMapper.DB2DTO(updatedLibMediaItem);

        restLibMediaItemMockMvc.perform(put("/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item", corNetwork.getShortcut(), libLibrary.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMediaItemDTO)))
            .andExpect(status().isOk());

        // Validate the LibMediaItem in the database
        List<LibMediaItem> libMediaItemList = libMediaItemRepository.findAll();
        assertThat(libMediaItemList).hasSize(databaseSizeBeforeUpdate);
        LibMediaItem testLibMediaItem = libMediaItemList.get(libMediaItemList.size() - 1);
        assertThat(testLibMediaItem.getIdx()).isEqualTo(UPDATED_IDX);
        assertThat(testLibMediaItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibMediaItem.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testLibMediaItem.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testLibMediaItem.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testLibMediaItem.getCommand()).isEqualTo(UPDATED_COMMAND);
        assertThat(testLibMediaItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLibMediaItem() throws Exception {
        int databaseSizeBeforeUpdate = libMediaItemRepository.findAll().size();

        // Create the LibMediaItem
        LibMediaItemDTO libMediaItemDTO = libMediaItemMapper.DB2DTO(libMediaItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibMediaItemMockMvc.perform(put("/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item", corNetwork.getShortcut(), libLibrary.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMediaItemDTO)))
            .andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    public void deleteLibMediaItem() throws Exception {
        // Initialize the database
        doNothing().when(s3Client).delete(anyObject());
        libMediaItemRepository.saveAndFlush(libMediaItem.library(libLibrary).network(corNetwork));
        int databaseSizeBeforeDelete = libMediaItemRepository.findAll().size();
        doNothing().when(s3Client).delete(anyObject());
        // Get the libMediaItem
        restLibMediaItemMockMvc.perform(delete("/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item/{id}", corNetwork.getShortcut(), libLibrary.getShortcut(), libMediaItem.getIdx())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibMediaItem> libMediaItemList = libMediaItemRepository.findAll();
        assertThat(libMediaItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void shouldUploadMediaItemVideo() throws Exception {
        MockMultipartFile firstFile = new MockMultipartFile("files", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/video/sample-video.mp4"));
        restLibMediaItemMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item", corNetwork.getShortcut(), libLibrary.getShortcut())
            .file(firstFile)).andExpect(status().is(200))
            .andExpect(jsonPath("$[*].itemType").value(LibItemTypeEnum.IT_VIDEO.toString()));
    }


    @Test
    @Transactional
    public void shouldUploadMediaItemAudio() throws Exception {
        MockMultipartFile firstFile = new MockMultipartFile("files", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/audio/sample_mp3.mp3"));
        restLibMediaItemMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item", corNetwork.getShortcut(), libLibrary.getShortcut())
            .file(firstFile)).andExpect(status().is(200))
            .andExpect(jsonPath("$[*].itemType").value(LibItemTypeEnum.IT_AUDIO.toString()));
    }

    @Test
    @Transactional
    public void shouldUploadMediaItemImage() throws Exception {
        MockMultipartFile firstFile = new MockMultipartFile("files", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/image/sample_image.png"));
        restLibMediaItemMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item", corNetwork.getShortcut(), libLibrary.getShortcut())
            .file(firstFile)).andExpect(status().is(200))
            .andExpect(jsonPath("$[*].itemType").value(LibItemTypeEnum.IT_IMAGE.toString()));
    }
    @Test
    @Transactional
    public void shouldUploadMediaItemDocument() throws Exception {
        MockMultipartFile firstFile = new MockMultipartFile("files", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/document/sample_document.xls"));
        restLibMediaItemMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/network/{networkShortcut}/library/{libraryPrefix}/item", corNetwork.getShortcut(), libLibrary.getShortcut())
            .file(firstFile)).andExpect(status().is(200))
            .andExpect(jsonPath("$[*].itemType").value(LibItemTypeEnum.IT_DOCUMENT.toString()));
    }



    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibMediaItem.class);
    }

}
