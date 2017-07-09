package io.protone.application.service.cor;

import io.jsonwebtoken.lang.Assert;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.service.CorUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

/**
 * Created by lukaszozimek on 01.07.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CorUserServiceTest {

    @Autowired
    private CorUserService corUserService;
    @Autowired
    private CorNetworkRepository networkRepository;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = networkRepository.saveAndFlush(corNetwork);

    }

    @Test
    public void activateRegistration() throws Exception {
        corUserService.activateRegistration("");
        Assert.notNull(null);
    }

    @Test
    public void completePasswordReset() throws Exception {
        corUserService.completePasswordReset("", "");

        Assert.notNull(null);
    }

    @Test
    public void requestPasswordReset() throws Exception {
        corUserService.requestPasswordReset("");

        Assert.notNull(null);
    }

    @Test
    public void createUser() throws Exception {

        // corUserService.createUser();
        Assert.notNull(null);
    }

    @Test
    public void createUser1() throws Exception {
        //  corUserService.createUser();

        Assert.notNull(null);
    }

    @Test
    public void updateUser() throws Exception {
        //   corUserService.updateUser();
        Assert.notNull(null);
    }

    @Test
    public void updateUser1() throws Exception {
        //    corUserService.updateUser();

        Assert.notNull(null);
    }

    @Test
    public void deleteUser() throws Exception {
        corUserService.deleteUser("");

        Assert.notNull(null);
    }

    @Test
    public void changePassword() throws Exception {
        corUserService.changePassword("");

        Assert.notNull(null);
    }

    @Test
    public void getAllManagedUsers() throws Exception {
        corUserService.getAllManagedUsers(corNetwork);

        Assert.notNull(null);
    }

    @Test
    public void getUserWithAuthoritiesByLogin() throws Exception {

        corUserService.getUserWithAuthoritiesByLogin("");
        Assert.notNull(null);
    }

    @Test
    public void getUserWithAuthorities() throws Exception {
        corUserService.getUserWithAuthorities();

        Assert.notNull(null);
    }

    @Test
    public void getUserWithAuthorities1() throws Exception {

        corUserService.getUserWithAuthorities();
        Assert.notNull(null);
    }

    @Test
    public void removeNotActivatedUsers() throws Exception {
        corUserService.removeNotActivatedUsers();

        Assert.notNull(null);
    }

    @Test
    public void getUserWithAuthoritiesByLoginAndNetwork() throws Exception {
        corUserService.getUserWithAuthoritiesByLoginAndNetwork("", corNetwork);

        Assert.notNull(null);
    }

}