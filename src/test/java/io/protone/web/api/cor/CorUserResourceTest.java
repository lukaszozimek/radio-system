package io.protone.web.api.cor;

import com.google.common.collect.Sets;
import io.protone.ProtoneApp;
import io.protone.service.cor.CorUserService;
import io.protone.service.cor.CorMailService;
import io.protone.domain.CorUser;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.cor.CorUserRepository;
import io.protone.web.api.cor.impl.CorUserResourceImpl;
import io.protone.web.rest.mapper.CorNetworkMapper;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
        user.setImageurl("http://placehold.it/50x50");
        user.setLangkey("en");
        user.setNetworks(Sets.newLinkedHashSet());
        em.persist(user);
        em.flush();
        return user;
    }

    @Before
    public void setup() {
        CorUserResourceImpl userResource = new CorUserResourceImpl(userRepository, corUserService, mailService, corNetworkRepository, corNetworkMapper);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
    }

    @Test
    public void emptyTest(){

    }
}
