package io.protone.custom.service.mapper;

import com.google.common.collect.Lists;
import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CoreUserPT;
import io.protone.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CustomCorUserMapperExtTest {
    @Autowired
    private CustomCorUserMapperExt customCorUserMapperExt;

    private CoreUserPT mockUserDto = null;

    private User mockUser = null;

    private List<User> mockUsers = null;

    private List<CoreUserPT> mockUsersPT = null;

    @Before
    public void initialize() {
        mockUserDto = new CoreUserPT();
        mockUserDto.setId((long) 1);
        mockUserDto.setActivated(true);
        mockUserDto.setAuthorities(new ArrayList<>());
        mockUserDto.setEmail("test");
        mockUserDto.setLogin("test");
        mockUserDto.setLangKey("test");
        mockUserDto.setFirstName("test");
        mockUserDto.setLastName("test");
        mockUser = new User();
        mockUser.setId((long) 1);
        mockUser.setActivated(true);
        mockUser.setAuthorities(new HashSet<>());
        mockUser.setEmail("test");
        mockUser.setLogin("test");
        mockUser.setLangKey("test");
        mockUser.setFirstName("test");
        mockUser.setLastName("test");
        mockUser.setActivationKey("");

    }

    @Test
    public void userToCoreUserPT() throws Exception {
        //then
        CoreUserPT result = customCorUserMapperExt.userToCoreUserPT(mockUser);
        //assert
        assertEquals(false, checkFiledsNotNull(result));

    }

    @Test
    public void usersToCoreUserPTs() throws Exception {
        //when
        mockUsers = Lists.newArrayList(mockUser);
        //then
        List<CoreUserPT> result = customCorUserMapperExt.usersToCoreUserPTs(mockUsers);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void coreUserPTToUser() throws Exception {
        //TODO :Refacotr necessary

    }

    @Test
    public void coreUserPTsToUsers() throws Exception {
        //TODO :Refacotr necessary

    }

}
