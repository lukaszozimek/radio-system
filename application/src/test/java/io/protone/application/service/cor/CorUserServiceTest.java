package io.protone.application.service.cor;

import com.google.common.collect.Sets;
import io.jsonwebtoken.lang.Assert;
import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorUserDTO;
import io.protone.core.domain.CorAuthority;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.core.mapper.CorNetworkMapper;
import io.protone.core.mapper.CorUserMapper;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.repository.CorUserRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorImageItemService;
import io.protone.core.service.CorUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static io.protone.core.security.AuthoritiesConstants.ADMIN;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 01.07.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CorUserServiceTest {

    private static final String ACTIVATION_KEY = "test";
    private static final String RESET_KEY = "reset";
    private static final String TEST_PASSWORD = "TEST_PASSS";
    private static final String SAMPLE_LOGIN = "sampleLogin";

    @Autowired
    private CorUserService corUserService;

    @Autowired
    private CorNetworkRepository networkRepository;

    @Autowired
    private CorUserRepository corUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CorNetworkMapper corNetworkMapper;

    @Autowired
    private CorImageItemService corImageItemService;

    @Autowired
    private CorUserMapper corUserMapper;

    private CorNetwork corNetwork;

    private PodamFactory factory;
    @Mock
    private S3Client s3Client;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        doNothing().when(s3Client).upload(anyString(), anyString(), anyObject(), anyString());
        when(s3Client.getCover(anyString(), anyString())).thenReturn("test");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = networkRepository.saveAndFlush(corNetwork);

    }

    @Test
    public void activateRegistration() throws Exception {
        //when
        CorUser corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser.setActivated(false);
        corUser.setChannels(null);
        corUser.setActivationkey(ACTIVATION_KEY);
        corUser = corUserRepository.saveAndFlush(corUser);

        //then
        Optional<CorUser> activatedUser = corUserService.activateRegistration(ACTIVATION_KEY);

        //assert
        assertNotNull(activatedUser);
        assertTrue(activatedUser.isPresent());
        assertTrue(activatedUser.get().isActivated());
        assertNull(activatedUser.get().getActivationkey());
    }

    @Test
    public void completePasswordReset() throws Exception {
        //when
        CorUser corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser.setActivated(true);
        corUser.setChannels(null);
        corUser.setResetkey(RESET_KEY);
        corUser.setResetdate(ZonedDateTime.now());
        corUser.setActivationkey(ACTIVATION_KEY);
        corUser = corUserRepository.saveAndFlush(corUser);

        //then
        corUserService.completePasswordReset(TEST_PASSWORD, RESET_KEY);
        Optional<CorUser> corUser1 = corUserRepository.findOneByLogin(corUser.getLogin());

        //assert
        assertTrue(corUser1.isPresent());
        assertNull(corUser1.get().getResetkey());
        assertNull(corUser1.get().getResetdate());

    }

    @Test
    public void requestPasswordReset() throws Exception {
        //when
        CorUser corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser.setActivated(true);
        corUser.setChannels(null);
        corUser = corUserRepository.saveAndFlush(corUser);

        Optional<CorUser> corUser1 = corUserService.requestPasswordReset(corUser.getEmail());


        assertTrue(corUser1.isPresent());
        assertNotNull(corUser1.get().getResetkey());
        assertNotNull(corUser1.get().getResetdate());

    }

    @Test
    public void createUser() throws Exception {
        CorUser corUser = factory.manufacturePojo(CorUser.class);
        corUser.setChannels(null);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));

        CorUserDTO corUserDTO = corUserMapper.DB2DTO(corUser);
        MockMultipartFile logo = new MockMultipartFile("logo", "logo.jpg", "", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        corUserService.createUser(corUserDTO, logo);
        Optional<CorUser> featechEnitity = corUserRepository.findOneByLogin(corUserDTO.getLogin());
        assertTrue(featechEnitity.isPresent());
    }

    @Test
    public void createUser1() throws Exception {
        corUserService.createUser(SAMPLE_LOGIN, TEST_PASSWORD, "test", "test", "test@test.com", "en-US", corNetworkMapper.DB2DTO(corNetwork));
        Optional<CorUser> corUser = corUserRepository.findOneByLogin(SAMPLE_LOGIN);
        assertTrue(corUser.isPresent());
        assertEquals("test@test.com", corUser.get().getEmail());
    }


    @Test
    public void deleteUser() throws Exception {
        //when
        CorUser corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser.setActivated(false);
        corUser.setChannels(null);
        corUser.setActivationkey(ACTIVATION_KEY);
        corUser = corUserRepository.saveAndFlush(corUser);

        //then
        corUserService.deleteUser(corUser.getLogin());

        //assert
        Optional<CorUser> corUser1 = corUserRepository.findOneByLogin(corUser.getLogin());
        Assert.isTrue(!corUser1.isPresent());
    }

    @Test
    public void changePassword() throws Exception {
        CorUser corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser.setActivated(true);
        corUser.setChannels(null);
        corUser.setResetkey(RESET_KEY);
        corUser.setResetdate(ZonedDateTime.now());
        corUser.setActivationkey(ACTIVATION_KEY);
        corUser = corUserRepository.saveAndFlush(corUser);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(corUser.getLogin(), corUser.getPasswordhash()));
        SecurityContextHolder.setContext(securityContext);
        //then
        corUserService.changePassword(TEST_PASSWORD);
        CorUser corUser1 = corUserRepository.findOne(corUser.getId());

        //assert
        assertNotNull(corUser1);
    }

    @Test
    public void getAllManagedUsers() throws Exception {
        CorUser corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser.setActivated(true);
        corUser.setChannels(null);
        corUser.setResetkey(RESET_KEY);
        corUser.setResetdate(ZonedDateTime.now());
        corUser.setActivationkey(ACTIVATION_KEY);
        corUser = corUserRepository.saveAndFlush(corUser);

        //then
        List<CorUser> corUsersList = corUserService.getAllManagedUsers(corNetwork, new PageRequest(0, 10));

        //assert
        Assert.notNull(corUsersList);
        assertFalse(corUsersList.isEmpty());


    }

    @Test
    public void getUserWithAuthoritiesByLogin() throws Exception {
        //when
        CorUser corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser.setActivated(true);
        corUser.setChannels(null);
        corUser.setResetkey(RESET_KEY);
        corUser.setResetdate(ZonedDateTime.now());
        corUser.setActivationkey(ACTIVATION_KEY);
        corUser = corUserRepository.saveAndFlush(corUser);

        //then
        Optional<CorUser> corUser1 = corUserService.getUserWithAuthoritiesByLogin(corUser.getLogin());

        //assert
        assertTrue(corUser1.isPresent());

    }

    @Test
    public void getUserWithAuthorities() throws Exception {
        //when
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        CorUser corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser.setActivated(false);
        corUser.setChannels(null);
        corUser.setResetkey(RESET_KEY);
        corUser.setResetdate(ZonedDateTime.now());
        corUser.setActivationkey(ACTIVATION_KEY);
        corUser = corUserRepository.saveAndFlush(corUser);

        //then
        CorUser corUser1 = corUserService.getUserWithAuthorities();

        //assert
        assertNotNull(corUser1);

    }

    @Test
    public void getUserWithAuthorities1() throws Exception {
        //when
        CorUser corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser.setActivated(false);
        corUser.setChannels(null);
        corUser.setResetkey(RESET_KEY);
        corUser.setResetdate(ZonedDateTime.now());
        corUser.setActivationkey(ACTIVATION_KEY);
        corUser = corUserRepository.saveAndFlush(corUser);

        //then
        CorUser fetchedCorUser = corUserService.getUserWithAuthorities(corUser.getId());

        //assert
        assertNotNull(fetchedCorUser);

    }

    @Test
    public void removeNotActivatedUsers() throws Exception {
        //when
        CorUser corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser.setActivated(false);
        corUser.setChannels(null);
        corUser.setResetkey(RESET_KEY);
        corUser.setCreatedDate(ZonedDateTime.now().minusDays(3));
        corUser.setActivationkey(ACTIVATION_KEY);
        corUser = corUserRepository.saveAndFlush(corUser);

        //then
        corUserService.removeNotActivatedUsers();
        CorUser corUser1 = corUserRepository.findOne(corUser.getId());


    }

    @Test
    public void getUserWithAuthoritiesByLoginAndNetwork() throws Exception {
        //when
        CorUser corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser.setActivated(true);
        corUser.setChannels(null);
        corUser.setResetkey(RESET_KEY);
        corUser.setResetdate(ZonedDateTime.now());
        corUser.setActivationkey(ACTIVATION_KEY);
        corUser = corUserRepository.saveAndFlush(corUser);

        //then
        Optional<CorUser> fetchedCorUser = corUserService.getUserWithAuthoritiesByLoginAndNetwork(corUser.getLogin(), corNetwork);

        assertNotNull(fetchedCorUser);
        assertTrue(fetchedCorUser.isPresent());
    }

}