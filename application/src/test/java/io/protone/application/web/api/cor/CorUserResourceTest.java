package io.protone.application.web.api.cor;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.impl.CorUserResourceImpl;
import io.protone.core.domain.CorUser;
import io.protone.core.mapper.CorNetworkMapper;
import io.protone.core.mapper.CorUserMapper;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.repository.CorUserRepository;
import io.protone.core.service.CorMailService;
import io.protone.core.service.CorUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;

/**
 * Created by lukaszozimek on 05.05.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorUserResourceTest {

    @Autowired
    private CorUserRepository userRepository;

    @Autowired
    private CorMailService mailService;

    @Autowired
    private CorUserService corUserService;

    @Autowired
    private CorNetworkMapper corNetworkMapper;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CorUserMapper corUserMapper;

    private MockMvc restUserMockMvc;

    /**
     * Create a User.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which has a required relationship to the User entity.
     */
    public static CorUser createEntity(EntityManager em) {
        CorUser user = new CorUser();
        user.setLogin("test");
        user.setPasswordhash(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("test@test.com");
        user.setFirstname("test");
        user.setLastname("test");
        user.setLangkey("en");
        user.setNetworks(Sets.newLinkedHashSet());
        em.persist(user);
        em.flush();
        return user;
    }

    @Before
    public void setup() {
        CorUserResourceImpl userResource = new CorUserResourceImpl(userRepository, corUserService, mailService, corNetworkRepository, corNetworkMapper, corUserMapper);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
    }

    @Test
    public void emptyTest() {

    }
}
