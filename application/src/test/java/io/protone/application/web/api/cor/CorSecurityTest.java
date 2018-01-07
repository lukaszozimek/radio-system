package io.protone.application.web.api.cor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwsHeader;
import io.protone.application.ProtoneApp;
import io.protone.application.security.jwt.TokenProvider;
import io.protone.application.util.TestConstans;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.impl.CorChannelResourceImpl;
import io.protone.application.web.api.cor.impl.CorNetworkResourceImpl;
import io.protone.application.web.api.cor.impl.CorUserJWTController;
import io.protone.application.web.api.library.impl.LibraryMediaResourceImpl;
import io.protone.application.web.rest.JWTToken;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.application.web.rest.vm.LoginVM;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.mapper.CorChannelMapper;
import io.protone.core.mapper.CorNetworkMapper;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorOrganizationService;
import io.protone.library.mapper.LibLibraryMediaMapper;
import io.protone.library.service.LibLibraryMediaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedHashMap;

import static io.protone.application.security.jwt.TokenProvider.ORGANIZATION;
import static io.protone.application.util.TestConstans.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by lukaszozimek on 23/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@ActiveProfiles("security")
public class CorSecurityTest {

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
    private LibLibraryMediaService libLibraryMediaService;

    @Autowired
    private LibLibraryMediaMapper libLibraryMediaMapper;

    @Autowired
    private CorOrganizationService corOrganizationService;


    @Autowired
    private CorChannelService corChannelService;

    @Autowired
    private CorChannelMapper corChannelMapper;

    @Autowired
    private CorNetworkMapper corNetworkMapper;

    protected CorOrganization corOrganization;

    protected CorChannel corChannel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LibraryMediaResourceImpl libLibraryResource = new LibraryMediaResourceImpl();
        ReflectionTestUtils.setField(libLibraryResource, "libLibraryMediaService", libLibraryMediaService);
        ReflectionTestUtils.setField(libLibraryResource, "libLibraryMediaMapper", libLibraryMediaMapper);
        ReflectionTestUtils.setField(libLibraryResource, "corChannelService", corChannelService);

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        corChannel.setOrganization(corOrganization);

        CorUserJWTController corUserJWTController = new CorUserJWTController(tokenProvider, authenticationManager);

        CorNetworkResourceImpl corNetworkResource = new CorNetworkResourceImpl();
        ReflectionTestUtils.setField(corNetworkResource, "corOrganizationService", corOrganizationService);
        ReflectionTestUtils.setField(corNetworkResource, "corNetworkMapper", corNetworkMapper);

        CorChannelResourceImpl corChannelResource = new CorChannelResourceImpl(corChannelService, corChannelMapper, corOrganizationService);

        this.restLibLibraryMockMvc = MockMvcBuilders.standaloneSetup(libLibraryResource, corUserJWTController, corNetworkResource, corChannelResource)
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Test
    public void shouldLoginAndGetLibrariesInChannel() throws Exception {
        //when
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername("admin");
        loginVM.setPassword("admin");

        String jwtToken = restLibLibraryMockMvc.perform(post("/api/v1/user/authenticate").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loginVM))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JWTToken token = mapper.readValue(jwtToken, JWTToken.class);

        JwsHeader jwsHeader = tokenProvider.getUserAuthorizationAccess(token.getIdToken());
        LinkedHashMap<String, String> corNetworkMap = (LinkedHashMap<String, String>) jwsHeader.get(ORGANIZATION);


        // Get the libMediaLibrary
        restLibLibraryMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media", corNetworkMap.get("shortcut"), TestConstans.TEST_CHANNEL_SHORTCUT)
                .header("Authorization", "Bearer " + token.getIdToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void shouldHandleCheckingChannelSimple() throws Exception {
        //when
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername("admin");
        loginVM.setPassword("admin");

        String jwtToken = restLibLibraryMockMvc.perform(post("/api/v1/user/authenticate").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loginVM))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JWTToken token = mapper.readValue(jwtToken, JWTToken.class);


        // pass organization to filter
        restLibLibraryMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media", "organization", TestConstans.TEST_CHANNEL_SHORTCUT)
                .header("Authorization", "Bearer " + token.getIdToken()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldHandleCheckingChannelComplex() throws Exception {
        //when
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername("admin");
        loginVM.setPassword("admin");


        String jwtToken = restLibLibraryMockMvc.perform(post("/api/v1/user/authenticate").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loginVM))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JWTToken token = mapper.readValue(jwtToken, JWTToken.class);

        JwsHeader jwsHeader = tokenProvider.getUserAuthorizationAccess(token.getIdToken());
        LinkedHashMap<String, String> corNetworkMap = (LinkedHashMap<String, String>) jwsHeader.get(ORGANIZATION);


        // pass organization to filter
        restLibLibraryMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/library/media/{libraryPrefix}", corNetworkMap.get("shortcut"), TEST_CHANNEL_SHORTCUT, "Wybitna")
                .header("Authorization", "Bearer " + token.getIdToken()))
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldGetCorChannels() throws Exception {
        //when
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername("admin");
        loginVM.setPassword("admin");


        String jwtToken = restLibLibraryMockMvc.perform(post("/api/v1/user/authenticate").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loginVM))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JWTToken token = mapper.readValue(jwtToken, JWTToken.class);

        JwsHeader jwsHeader = tokenProvider.getUserAuthorizationAccess(token.getIdToken());
        LinkedHashMap<String, String> corNetworkMap = (LinkedHashMap<String, String>) jwsHeader.get(ORGANIZATION);


        // pass organization to filter
        restLibLibraryMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel", corOrganization.getShortcut())
                .header("Authorization", "Bearer " + token.getIdToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void shouldGetCorNetworks() throws Exception {
        //when
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername("admin");
        loginVM.setPassword("admin");


        String jwtToken = restLibLibraryMockMvc.perform(post("/api/v1/user/authenticate").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loginVM))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JWTToken token = mapper.readValue(jwtToken, JWTToken.class);

        // pass organization to filter
        restLibLibraryMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel", corOrganization.getShortcut())
                .header("Authorization", "Bearer " + token.getIdToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }
}
