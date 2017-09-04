package io.protone.application.web.api.library;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.library.impl.LibLabelResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorImageItemRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorImageItemService;
import io.protone.core.service.CorNetworkService;
import io.protone.library.api.dto.LibLabelDTO;
import io.protone.library.domain.LibLabel;
import io.protone.library.mapper.LibLabelMapper;
import io.protone.library.repository.LibLabelRepository;
import io.protone.library.service.LibLabelService;
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
public class LibLabelResourceImplTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
    private static final String PUBLIC_URL_STRING = "test";
    @Autowired
    private LibLabelRepository libLabelRepository;

    @Autowired
    private LibLabelMapper libLabelMapper;

    @Autowired
    private LibLabelService libLabelService;

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

    @Autowired
    private CorImageItemRepository corImageItemRepository;

    private MockMvc restLibLabelMockMvc;

    private LibLabel libLibrary;

    private CorNetwork corNetwork;

    private CorImageItem corImageItem;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibLabel createEntity(EntityManager em) {
        LibLabel libLibrary = new LibLabel()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return libLibrary;
    }

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        LibLabelResourceImpl libLibraryResource = new LibLabelResourceImpl();
        corImageItem = new CorImageItem().publicUrl(PUBLIC_URL_STRING).name("test").network(corNetwork);
        corImageItemRepository.saveAndFlush(corImageItem);
        when(corImageItemService.saveImageItem(any())).thenReturn(corImageItem);
        ReflectionTestUtils.setField(libLibraryResource, "libLabelService", libLabelService);
        ReflectionTestUtils.setField(libLibraryResource, "libLabelMapper", libLabelMapper);
        ReflectionTestUtils.setField(libLibraryResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        when(s3Client.makeBucket(anyString(), anyString())).thenReturn(corNetwork.getShortcut() + MEDIA_ITEM + "testBucket");
        this.restLibLabelMockMvc = MockMvcBuilders.standaloneSetup(libLibraryResource)
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
    public void createLibLabel() throws Exception {
        int databaseSizeBeforeCreate = libLabelRepository.findAll().size();

        // Create the LibLabel
        LibLabelDTO libLibraryDTO = libLabelMapper.DB2DTO(libLibrary);


        restLibLabelMockMvc.perform(post("/api/v1/network/{networkShortcut}/library/label", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)));

        // Validate the LibLabel in the database
        List<LibLabel> libLibraryList = libLabelRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeCreate + 1);
        LibLabel testLibLabel = libLibraryList.get(libLibraryList.size() - 1);
        assertThat(testLibLabel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibLabel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLibLabelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libLabelRepository.findAll().size();

        // Create the LibLabel with an existing ID
        LibLabel existingLibLabel = new LibLabel();
        existingLibLabel.setId(1L);
        LibLabelDTO existingLibLabelDTO = libLabelMapper.DB2DTO(existingLibLabel);
        restLibLabelMockMvc.perform(post("/api/v1/network/{networkShortcut}/library/label", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingLibLabelDTO)))
                .andExpect(status().isBadRequest());


        // Validate the Alice in the database
        List<LibLabel> libLibraryList = libLabelRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libLabelRepository.findAll().size();
        // set the field null
        libLibrary.setName(null);

        // Create the LibLabel, which fails.
        LibLabelDTO libLibraryDTO = libLabelMapper.DB2DTO(libLibrary);

        restLibLabelMockMvc.perform(post("/api/v1/network/{networkShortcut}/library/label", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
                .andExpect(status().isBadRequest());


        List<LibLabel> libLibraryList = libLabelRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void getAllLibLibraries() throws Exception {
        // Initialize the database
        when(corImageItemService.saveImageItem(any())).thenReturn(null);
        libLabelRepository.saveAndFlush(libLibrary.network(corNetwork));

        // Get all the libLibraryList
        restLibLabelMockMvc.perform(get("/api/v1/network/{networkShortcut}/library/label?sort=id,desc", corNetwork.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(libLibrary.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLibLabel() throws Exception {
        // Initialize the database
        when(corImageItemService.saveImageItem(any())).thenReturn(null);
        libLabelRepository.saveAndFlush(libLibrary.network(corNetwork));

        // Get the libMediaLibrary
        restLibLabelMockMvc.perform(get("/api/v1/network/{networkShortcut}/library/label/{id}", corNetwork.getShortcut(), libLibrary.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(libLibrary.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }


    @Test
    @Transactional
    public void getNonExistingLibLabel() throws Exception {
        // Get the libMediaLibrary
        restLibLabelMockMvc.perform(get("/api/v1/network/{networkShortcut}/library/{id}", corNetwork.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibLabel() throws Exception {
        // Initialize the database
        libLabelRepository.saveAndFlush(libLibrary.network(corNetwork));
        int databaseSizeBeforeUpdate = libLabelRepository.findAll().size();

        // Update the libMediaLibrary
        LibLabel updatedLibLabel = libLabelRepository.findOne(libLibrary.getId());
        updatedLibLabel
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        LibLabelDTO libLibraryDTO = libLabelMapper.DB2DTO((updatedLibLabel));

        restLibLabelMockMvc.perform(put("/api/v1/network/{networkShortcut}/library/label", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
                .andExpect(status().isOk());

        // Validate the LibLabel in the database
        List<LibLabel> libLibraryList = libLabelRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeUpdate);
        LibLabel testLibLabel = libLibraryList.get(libLibraryList.size() - 1);
        assertThat(testLibLabel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibLabel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void updateNonExistingLibLabel() throws Exception {
        int databaseSizeBeforeUpdate = libLabelRepository.findAll().size();

        // Create the LibLabel
        LibLabelDTO libLibraryDTO = libLabelMapper.DB2DTO(libLibrary);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibLabelMockMvc.perform(put("/api/v1/network/{networkShortcut}/library/label", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
                .andExpect(status().isCreated());

        // Validate the LibLabel in the database
        List<LibLabel> libLibraryList = libLabelRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibLabel() throws Exception {
        // Initialize the database
        libLabelRepository.saveAndFlush(libLibrary.network(corNetwork));
        int databaseSizeBeforeDelete = libLabelRepository.findAll().size();

        // Get the libMediaLibrary
        restLibLabelMockMvc.perform(delete("/api/v1/network/{networkShortcut}/library/label/{id}", corNetwork.getShortcut(), libLibrary.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LibLabel> libLibraryList = libLabelRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibLabel.class);
    }

}
