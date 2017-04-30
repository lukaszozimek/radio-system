package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CoreUserPT;
import io.protone.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorUserMapperTest {
    private static final String TEST_AUTHORIRT = "Test";
    @Autowired
    private CorUserMapper customCorUserMapperExt;

    private CorUser corUser;

    private CoreUserPT coreUserPT;

    private List<CoreUserPT> coreUserPTS = new ArrayList<>();

    private List<CorUser> corUsers = new ArrayList<>();
    private CorNetwork corNetwork;
    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corUser = factory.manufacturePojo(CorUser.class);
        corUser.addChannel(factory.manufacturePojo(CorChannel.class));
        corUser.addNetwork(factory.manufacturePojo(CorNetwork.class));

        corUsers.add(corUser);
        coreUserPT = factory.manufacturePojo(CoreUserPT.class);
        coreUserPTS.add(coreUserPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corChannel = factory.manufacturePojo(CorChannel.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        CoreUserPT dto = customCorUserMapperExt.DB2DTO(corUser);

        assertNotNull(dto.getActivated());
        assertNotNull(dto.getAuthorities());
        assertNotNull(dto.getEmail());
        assertNotNull(dto.getImageurl());
        assertNotNull(dto.getLogin());
        assertNotNull(dto.getId());

        assertNotNull(dto.getChannel());
        assertNotNull(dto.getNetwork());
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CoreUserPT> dtos = customCorUserMapperExt.DBs2DTOs(corUsers);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getActivated());
            assertNotNull(dto.getAuthorities());
            assertNotNull(dto.getEmail());
            assertNotNull(dto.getImageurl());
            assertNotNull(dto.getLogin());
            assertNotNull(dto.getId());

            assertNotNull(dto.getChannel());
            assertNotNull(dto.getNetwork());

        });
    }


    @Test
    public void DTO2DB() throws Exception {
        CorUser entity = customCorUserMapperExt.DTO2DB(coreUserPT, corChannel, corNetwork);

        assertNotNull(entity.getId());
        assertNotNull(entity.getLogin());
        assertNotNull(entity.getEmail());
        assertNotNull(entity.getImageurl());
        assertNotNull(entity.isActivated());
        assertNotNull(entity.getAuthorities());

        assertNotNull(entity.getNetworks());
        assertNotNull(entity.getChannels());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorUser> entities = customCorUserMapperExt.DTOs2DBs(coreUserPTS, corChannel, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getId());
            assertNotNull(entity.getLogin());
            assertNotNull(entity.getEmail());
            assertNotNull(entity.getImageurl());
            assertNotNull(entity.isActivated());
            assertNotNull(entity.getAuthorities());

            assertNotNull(entity.getNetworks());
            assertNotNull(entity.getChannels());
        });
    }

    @Test
    public void corAuthorityFromString() throws Exception {
        CorAuthority corAuthority = customCorUserMapperExt.corAuthorityFromString(TEST_AUTHORIRT);
        assertNotNull(corAuthority);
        assertEquals(corAuthority.getName(), TEST_AUTHORIRT);
    }

    @Test
    public void stringFromCorAuthority() throws Exception {

        String authority = customCorUserMapperExt.stringFromCorAuthority(new CorAuthority().name(TEST_AUTHORIRT));
        assertNotNull(authority);
        assertEquals(authority, TEST_AUTHORIRT);
    }

}
