package io.protone.application.web.api.cor;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.impl.CorChannelResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.api.dto.CorChannelDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorOrganization;
import io.protone.core.mapper.CorChannelMapper;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorImageItemRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorImageItemService;
import io.protone.core.service.CorOrganizationService;
import org.apache.tika.exception.TikaException;
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
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

import static io.protone.application.util.TestConstans.TEST_ORGANIZATION_ID;
import static io.protone.application.util.TestConstans.TEST_ORGANIZATION_SHORTCUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 01/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorChannelResourceTest {

    private static final String PUBLIC_URL_STRING = "test";

    private static final String DEFAULT_SHORTCUT = "AAA";
    private static final String UPDATED_SHORTCUT = "BBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CorChannelMapper corChannelMapper;

    @Autowired
    private CorChannelService corChannelService;

    @Autowired
    private CorOrganizationService corOrganizationService;

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

    private MockMvc restCorChannelMockMvc;

    private CorChannel corChannel;

    private CorOrganization corOrganization;

    private CorImageItem corImageItem;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorChannel createEntity(EntityManager em) {
        CorChannel corChannel = new CorChannel()
                .shortcut(DEFAULT_SHORTCUT)
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return corChannel;
    }


    @Before
    public void setup() throws IOException, TikaException, SAXException {
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel()
                .shortcut(DEFAULT_SHORTCUT)
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        corChannel.setOrganization(corOrganization);
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(corChannelService, "corImageItemService", corImageItemService);
        CorChannelResourceImpl corChannelResource = new CorChannelResourceImpl(corChannelService, corChannelMapper, corOrganizationService);
     //   corChannelService.deleteChannel(corOrganization.getShortcut(), corChannel.getShortcut());
        corImageItem = corImageItemRepository.saveAndFlush(new CorImageItem().publicUrl(PUBLIC_URL_STRING).name("test").organization(corOrganization));
        this.restCorChannelMockMvc = MockMvcBuilders.standaloneSetup(corChannelResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corChannel = createEntity(em).organization(corOrganization);
    }

    @Test
    public void createCorChannel() throws Exception {

        when(corImageItemService.saveImageItem(anyObject(), anyObject())).thenReturn(corImageItem);

        int databaseSizeBeforeCreate = corChannelRepository.findAll().size();

        // Create the CorChannel
        CorChannelDTO corChannelDTO = corChannelMapper.DB2DTO(corChannel);
        MockMultipartFile emptyFile = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        MockMultipartFile jsonFile = new MockMultipartFile("channelDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(corChannelDTO));


        restCorChannelMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel", corOrganization.getShortcut())
                .file(emptyFile)
                .file(jsonFile)).andExpect(status().isCreated());

        // Validate the CorChannel in the database
        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeCreate + 1);
        CorChannel testCorChannel = corChannelList.get(corChannelList.size() - 1);
        assertThat(testCorChannel.getShortcut()).isEqualTo(DEFAULT_SHORTCUT);
        assertThat(testCorChannel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorChannel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createCorChannelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corChannelRepository.findAll().size();

        // Create the CorChannel with an existing ID
        CorChannel existingCorChannel = new CorChannel();
        existingCorChannel.setId(1L);
        CorChannelDTO existingCorChannelDTO = corChannelMapper.DB2DTO(existingCorChannel);

        MockMultipartFile emptyFile = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        MockMultipartFile jsonFile = new MockMultipartFile("channelDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(existingCorChannelDTO));


        restCorChannelMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel", corOrganization.getShortcut())
                .file(emptyFile)
                .file(jsonFile)).andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkShortcutIsRequired() throws Exception {
        int databaseSizeBeforeTest = corChannelRepository.findAll().size();
        // set the field null
        corChannel.setShortcut(null);


        // Create the CorChannel, which fails.
        CorChannelDTO corChannelDTO = corChannelMapper.DB2DTO(corChannel);
        MockMultipartFile emptyFile = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        MockMultipartFile jsonFile = new MockMultipartFile("channelDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(corChannelDTO));


        restCorChannelMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel", corOrganization.getShortcut())
                .file(emptyFile)
                .file(jsonFile)).andExpect(status().isBadRequest());

        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNameIsRequired() throws Exception {

        int databaseSizeBeforeTest = corChannelRepository.findAll().size();
        // set the field null
        corChannel.setName(null);

        // Create the CorChannel, which fails.
        CorChannelDTO corChannelDTO = corChannelMapper.DB2DTO(corChannel);
        MockMultipartFile emptyFile = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        MockMultipartFile jsonFile = new MockMultipartFile("channelDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(corChannelDTO));


        restCorChannelMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel", corOrganization.getShortcut())
                .file(emptyFile)
                .file(jsonFile)).andExpect(status().isBadRequest());

        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorChannels() throws Exception {
        // Initialize the database
        when(corImageItemService.getValidLinkToResource(anyObject())).thenReturn(null);
        corChannelService.deleteChannel(corOrganization.getShortcut(), corChannel.getShortcut());
        corChannelRepository.saveAndFlush(corChannel.organization(corOrganization));

        // Get all the corChannelList
        restCorChannelMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel?sort=id,desc", corOrganization.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(corChannel.getId().intValue())))
                .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getAllCorChannelsWithImage() throws Exception {
        // Initialize the database
        corChannelService.deleteChannel(corOrganization.getShortcut(), corChannel.getShortcut());
        corChannelRepository.saveAndFlush(corChannel.logo(corImageItem).organization(corOrganization));

        // Get all the corChannelList
        restCorChannelMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel?sort=id,desc", corOrganization.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(corChannel.getId().intValue())))
                .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].publicUrl").value(hasItem(PUBLIC_URL_STRING.toString())));
    }

    @Test
    public void getCorChannel() throws Exception {
        // Initialize the database
        when(corImageItemService.getValidLinkToResource(anyObject())).thenReturn(null);
        corChannelService.deleteChannel(corOrganization.getShortcut(), corChannel.getShortcut());
        corChannelRepository.saveAndFlush(corChannel.organization(corOrganization));

        // Get the corChannel
        restCorChannelMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}", corOrganization.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(corChannel.getId().intValue()))
                .andExpect(jsonPath("$.shortcut").value(DEFAULT_SHORTCUT.toString()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getCorChannelWithImageItem() throws Exception {
        // Initialize the database
        when(corImageItemService.getValidLinkToResource(any())).thenReturn(new CorImageItem().publicUrl(PUBLIC_URL_STRING));
        corChannelService.deleteChannel(corOrganization.getShortcut(), corChannel.getShortcut());
        corChannelRepository.saveAndFlush(corChannel.logo(corImageItem).organization(corOrganization));

        // Get the corChannel
        restCorChannelMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}", corOrganization.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(corChannel.getId().intValue()))
                .andExpect(jsonPath("$.shortcut").value(DEFAULT_SHORTCUT.toString()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.publicUrl").value(PUBLIC_URL_STRING.toString()));

    }

    @Test
    public void getNonExistingCorChannel() throws Exception {
        // Get the corChannel
        restCorChannelMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}", corOrganization.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorChannel() throws Exception {

        // Initialize the database

        corChannelRepository.deleteByShortcutAndOrganization_Shortcut(UPDATED_SHORTCUT, corOrganization.getShortcut());
        corChannelRepository.saveAndFlush(corChannel.organization(corOrganization));
        int databaseSizeBeforeUpdate = corChannelRepository.findAll().size();

        // Update the corChannel
        CorChannel updatedCorChannel = corChannelRepository.findOne(corChannel.getId());
        updatedCorChannel
                .shortcut(UPDATED_SHORTCUT)
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CorChannelDTO corChannelDTO = corChannelMapper.DB2DTO(updatedCorChannel);
        restCorChannelMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel", corOrganization.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(corChannelDTO)))
                .andExpect(status().isOk());

        // Validate the CorChannel in the database
        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeUpdate);
        CorChannel testCorChannel = corChannelList.get(corChannelList.size() - 1);
        assertThat(testCorChannel.getShortcut()).isEqualTo(UPDATED_SHORTCUT);
        assertThat(testCorChannel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorChannel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateCorChannelWithImage() throws Exception {

        // Initialize the database
        when(corImageItemService.saveImageItem(anyObject(), anyObject())).thenReturn(corImageItem);
        corChannelService.deleteChannel(corOrganization.getShortcut(), corChannel.getShortcut());
        corChannelRepository.saveAndFlush(corChannel.organization(corOrganization));
        int databaseSizeBeforeUpdate = corChannelRepository.findAll().size();

        // Update the corChannel
        CorChannel updatedCorChannel = corChannelRepository.findOne(corChannel.getId());
        updatedCorChannel
                .shortcut(UPDATED_SHORTCUT)
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CorChannelDTO corChannelDTO = corChannelMapper.DB2DTO(updatedCorChannel);

        MockMultipartFile emptyFile = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        MockMultipartFile jsonFile = new MockMultipartFile("channelDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(corChannelDTO));


        restCorChannelMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}", corOrganization.getShortcut(), corChannel.getShortcut())
                .file(emptyFile)
                .file(jsonFile))
                .andExpect(status().isOk());

        // Validate the CorChannel in the database
        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeUpdate);
        CorChannel testCorChannel = corChannelList.get(corChannelList.size() - 1);
        assertThat(testCorChannel.getShortcut()).isEqualTo(UPDATED_SHORTCUT);
        assertThat(testCorChannel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorChannel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingCorChannel() throws Exception {
        corChannelService.deleteChannel(corOrganization.getShortcut(), corChannel.getShortcut());
        int databaseSizeBeforeUpdate = corChannelRepository.findAll().size();

        // Create the CorChannel
        CorChannelDTO corChannelDTO = corChannelMapper.DB2DTO(corChannel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorChannelMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel", corOrganization.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(corChannelDTO)))
                .andExpect(status().isCreated());

        // Validate the CorChannel in the database
        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteCorChannel() throws Exception {
        // Initialize the database
        corChannelService.deleteChannel(corOrganization.getShortcut(), corChannel.getShortcut());
        CorChannel localcorChannel = corChannelRepository.saveAndFlush(corChannel.shortcut("ops").organization(corOrganization));
        int databaseSizeBeforeDelete = corChannelRepository.findAll().size();

        // Get the corChannel
        restCorChannelMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}", corOrganization.getShortcut(), localcorChannel.getShortcut())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorChannel.class);
    }

}
