package io.protone.application.web.api.cor;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.impl.CorChannelResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.api.dto.CorChannelDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorChannelMapper;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorImageItemService;
import io.protone.core.service.CorNetworkService;
import io.protone.library.api.dto.LibMediaItemDTO;
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
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

import static io.protone.application.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasItem;
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
    private CorNetworkService networkService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Mock
    private CorImageItemService imageItemService;

    private MockMvc restCorChannelMockMvc;

    private CorChannel corChannel;

    private CorNetwork corNetwork;

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
        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(corChannelService, "corImageItemService", imageItemService);
        CorChannelResourceImpl corChannelResource = new CorChannelResourceImpl(corChannelService, corChannelMapper, networkService);
        this.restCorChannelMockMvc = MockMvcBuilders.standaloneSetup(corChannelResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corChannel = createEntity(em).network(corNetwork);
    }

    @Test
    public void createCorChannel() throws Exception {
        corChannelService.deleteChannel(corNetwork.getShortcut(), corChannel.getShortcut());
        CorImageItem corImageItem = new CorImageItem().name("test").network(corNetwork);
        corImageItem.setId(1L);
        when(imageItemService.saveImageItem(anyObject())).thenReturn(corImageItem);

        int databaseSizeBeforeCreate = corChannelRepository.findAll().size();

        // Create the CorChannel
        CorChannelDTO corChannelDTO = corChannelMapper.DB2DTO(corChannel);
        MockMultipartFile emptyFile = new MockMultipartFile("logo",  Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        MockMultipartFile jsonFile = new MockMultipartFile("channelDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(corChannelDTO));


        restCorChannelMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/network/{networkShortcut}/channel", corNetwork.getShortcut())
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

        MockMultipartFile emptyFile = new MockMultipartFile("logo",   Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        MockMultipartFile jsonFile = new MockMultipartFile("channelDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(existingCorChannelDTO));


        restCorChannelMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/network/{networkShortcut}/channel", corNetwork.getShortcut())
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
        MockMultipartFile emptyFile = new MockMultipartFile("logo",  Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        MockMultipartFile jsonFile = new MockMultipartFile("channelDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(corChannelDTO));


        restCorChannelMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/network/{networkShortcut}/channel", corNetwork.getShortcut())
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
        MockMultipartFile emptyFile = new MockMultipartFile("logo",   Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        MockMultipartFile jsonFile = new MockMultipartFile("channelDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(corChannelDTO));


        restCorChannelMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/network/{networkShortcut}/channel", corNetwork.getShortcut())
                .file(emptyFile)
                .file(jsonFile)).andExpect(status().isBadRequest());

        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllCorChannels() throws Exception {
        // Initialize the database
        corChannelService.deleteChannel(corNetwork.getShortcut(), corChannel.getShortcut());
        corChannelRepository.saveAndFlush(corChannel.network(corNetwork));

        // Get all the corChannelList
        restCorChannelMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel?sort=id,desc", corNetwork.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(corChannel.getId().intValue())))
                .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    public void getCorChannel() throws Exception {
        // Initialize the database
        corChannelService.deleteChannel(corNetwork.getShortcut(), corChannel.getShortcut());
        corChannelRepository.saveAndFlush(corChannel.network(corNetwork));

        // Get the corChannel
        restCorChannelMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}", corNetwork.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(corChannel.getId().intValue()))
                .andExpect(jsonPath("$.shortcut").value(DEFAULT_SHORTCUT.toString()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingCorChannel() throws Exception {
        // Get the corChannel
        restCorChannelMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}", corNetwork.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCorChannel() throws Exception {

        // Initialize the database

        corChannelService.deleteChannel(corNetwork.getShortcut(), corChannel.getShortcut());
        corChannelRepository.saveAndFlush(corChannel.network(corNetwork));
        int databaseSizeBeforeUpdate = corChannelRepository.findAll().size();

        // Update the corChannel
        CorChannel updatedCorChannel = corChannelRepository.findOne(corChannel.getId());
        updatedCorChannel
                .shortcut(UPDATED_SHORTCUT)
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CorChannelDTO corChannelDTO = corChannelMapper.DB2DTO(updatedCorChannel);

        restCorChannelMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel", corNetwork.getShortcut())
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

        corChannelService.deleteChannel(corNetwork.getShortcut(), corChannel.getShortcut());
        corChannelRepository.saveAndFlush(corChannel.network(corNetwork));
        int databaseSizeBeforeUpdate = corChannelRepository.findAll().size();

        // Update the corChannel
        CorChannel updatedCorChannel = corChannelRepository.findOne(corChannel.getId());
        updatedCorChannel
                .shortcut(UPDATED_SHORTCUT)
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CorChannelDTO corChannelDTO = corChannelMapper.DB2DTO(updatedCorChannel);

        MockMultipartFile emptyFile = new MockMultipartFile("logo",  Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        MockMultipartFile jsonFile = new MockMultipartFile("channelDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(corChannelDTO));


        restCorChannelMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/network/{networkShortcut}/channel/{channelShortcut}", corNetwork.getShortcut(), corChannel.getShortcut())
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
        corChannelService.deleteChannel(corNetwork.getShortcut(), corChannel.getShortcut());
        int databaseSizeBeforeUpdate = corChannelRepository.findAll().size();

        // Create the CorChannel
        CorChannelDTO corChannelDTO = corChannelMapper.DB2DTO(corChannel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorChannelMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel", corNetwork.getShortcut())
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
        corChannelService.deleteChannel(corNetwork.getShortcut(), corChannel.getShortcut());
        CorChannel localcorChannel = corChannelRepository.saveAndFlush(corChannel.shortcut("ops").network(corNetwork));
        int databaseSizeBeforeDelete = corChannelRepository.findAll().size();

        // Get the corChannel
        restCorChannelMockMvc.perform(delete("/api/v1/network/{networkShortcut}/channel/{channelShortcut}", corNetwork.getShortcut(), localcorChannel.getShortcut())
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
