package io.protone.application.web.api.cor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.protone.application.ProtoneApp;
import io.protone.application.security.jwt.TokenProvider;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.impl.CorChannelResourceImpl;
import io.protone.application.web.api.cor.impl.CorNetworkResourceImpl;
import io.protone.application.web.api.cor.impl.CorUserJWTController;
import io.protone.application.web.api.library.impl.LibraryResourceImpl;
import io.protone.application.web.rest.JWTToken;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.application.web.rest.vm.LoginVM;
import io.protone.core.api.dto.CorNetworkDTO;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorChannelMapper;
import io.protone.core.mapper.CorNetworkMapper;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorImageItemService;
import io.protone.core.service.CorNetworkService;
import io.protone.library.mapper.LibLibraryMapper;
import io.protone.library.service.LibLibraryService;
import org.junit.Assert;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by lukaszozimek on 23/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@ActiveProfiles("security")
public class CorAuditTest {

    private MockMvc restLibLibraryMockMvc;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private LibLibraryService libLibraryService;

    @Autowired
    private LibLibraryMapper libLibraryMapper;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private CorChannelService channelService;

    @Autowired
    private CorChannelMapper corChannelMapper;

    @Autowired
    private CorNetworkMapper corNetworkMapper;
    @Autowired
    private CorNetworkRepository corNetworkRepository;

    private EntityManager em;
    @Mock
    private CorImageItemService corImageItemService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LibraryResourceImpl libLibraryResource = new LibraryResourceImpl();
        ReflectionTestUtils.setField(libLibraryResource, "libLibraryService", libLibraryService);
        ReflectionTestUtils.setField(libLibraryResource, "libLibraryMapper", libLibraryMapper);
        ReflectionTestUtils.setField(libLibraryResource, "corNetworkService", corNetworkService);

        CorUserJWTController corUserJWTController = new CorUserJWTController(tokenProvider, authenticationManager);

        CorNetworkResourceImpl corNetworkResource = new CorNetworkResourceImpl();
        ReflectionTestUtils.setField(corNetworkService, "corImageItemService", corImageItemService);
        ReflectionTestUtils.setField(corNetworkResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(corNetworkResource, "corNetworkMapper", corNetworkMapper);

        CorChannelResourceImpl corChannelResource = new CorChannelResourceImpl(channelService, corChannelMapper, corNetworkService);

        this.restLibLibraryMockMvc = MockMvcBuilders.standaloneSetup(libLibraryResource, corUserJWTController, corNetworkResource, corChannelResource)
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }


    @Test
    public void shouldCreateCorNetworksAndChangeAuditLog() throws Exception {
        when(corImageItemService.getValidLinkToResource(anyObject())).thenReturn(null);
        when(corImageItemService.saveImageItem(anyObject())).thenReturn(null);
        //when
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername("admin");
        loginVM.setPassword("admin");


        String jwtToken = restLibLibraryMockMvc.perform(post("/api/v1/user/authenticate").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loginVM))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JWTToken token = mapper.readValue(jwtToken, JWTToken.class);

        CorNetworkDTO corNetworkDTO = corNetworkMapper.DB2DTO(CorNetworkResourceIntTest.createEntity(em).shortcut("pep").name("tts"));

        MockMultipartFile emptyFile = new MockMultipartFile("logo", new byte[0]);
        MockMultipartFile jsonFile = new MockMultipartFile("network", "",
                "application/json", TestUtil.convertObjectToJsonBytes(corNetworkDTO));
        // pass channel to filter
        String corNetworkDTOWithId = restLibLibraryMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/network")
                .file(emptyFile)
                .file(jsonFile)
                .header("Authorization", "Bearer " + token.getIdToken()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn().getResponse().getContentAsString();
        CorNetworkDTO networkDTO = mapper.readValue(corNetworkDTOWithId, CorNetworkDTO.class);
        networkDTO.name("KKKK");

        LoginVM loginVMUser = new LoginVM();
        loginVMUser.setUsername("user");
        loginVMUser.setPassword("user");
        String jwtTokenUser = restLibLibraryMockMvc.perform(post("/api/v1/user/authenticate").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loginVMUser))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JWTToken tokenUser = mapper.readValue(jwtTokenUser, JWTToken.class);

        Thread.sleep(10000);

        String corNetworkDTOWithId1 = restLibLibraryMockMvc.perform(put("/api/v1/network")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(networkDTO))
                .header("Authorization", "Bearer " + tokenUser.getIdToken()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        CorNetwork corNetwork = corNetworkRepository.findOne(networkDTO.getId());

        Assert.assertEquals(corNetwork.getLastModifiedBy().getLogin(), "user");
        corNetworkRepository.delete(corNetwork);
    }
}
