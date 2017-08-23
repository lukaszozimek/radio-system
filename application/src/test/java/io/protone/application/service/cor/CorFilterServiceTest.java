package io.protone.application.service.cor;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorFilter;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.core.domain.enumeration.CorEntityTypeEnum;
import io.protone.core.repository.CorFilterRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.service.CorFilterService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import java.util.List;

import static io.protone.core.domain.enumeration.CorEntityTypeEnum.Channel;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 20.07.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CorFilterServiceTest {

    @Autowired
    private CorFilterService corFilterService;
    @Autowired
    private CorFilterRepository corFilterRepository;

    @Autowired
    private CorNetworkRepository networkRepository;

    private PodamFactory factory;

    private CorNetwork corNetwork;


    @Before
    public void setUp() throws Exception {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = networkRepository.saveAndFlush(corNetwork);

    }

    @Test
    public void shouldSaveUser() throws Exception {
        CorFilter corFilter = new CorFilter().name("test").value("test").type(Channel).network(corNetwork);
        CorFilter entity = corFilterService.save(corFilter);
        CorFilter fetchedEntity = corFilterService.findOne(entity.getId(), Channel, corNetwork.getShortcut());
        assertEquals(entity.getId(), fetchedEntity.getId());
        assertEquals(entity.getName(), fetchedEntity.getName());
        assertEquals(entity.getType(), fetchedEntity.getType());
        assertEquals(entity.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldFindFilter() throws Exception {
        CorUser corUser = new CorUser();
        corUser.setId(3L);
        CorFilter corFilter = new CorFilter().name("test").value("test").type(Channel).network(corNetwork).user(corUser);
        CorFilter entity = corFilterRepository.saveAndFlush(corFilter);
        CorFilter fetchedEntity = corFilterService.findOne(entity.getId(), Channel, corNetwork.getShortcut());
        assertEquals(entity.getId(), fetchedEntity.getId());
        assertEquals(entity.getName(), fetchedEntity.getName());
        assertEquals(entity.getType(), fetchedEntity.getType());
        assertEquals(entity.getNetwork(), fetchedEntity.getNetwork());

    }

    @Test
    public void findAllFindFilter() throws Exception {
        CorUser corUser = new CorUser();
        corUser.setId(3L);
        CorFilter corFilter = new CorFilter().name("test").value("test").type(Channel).network(corNetwork).user(corUser);
        CorFilter entity = corFilterRepository.saveAndFlush(corFilter);

        Slice<CorFilter> fetchedEntity = corFilterService.findAll(corNetwork.getShortcut(), Channel, new PageRequest(0, 10));

        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(entity.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(entity.getName(), fetchedEntity.getContent().get(0).getName());
        assertEquals(entity.getType(), fetchedEntity.getContent().get(0).getType());
        assertEquals(entity.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void delete() throws Exception {
        CorUser corUser = new CorUser();
        corUser.setId(3L);
        CorFilter corFilter = new CorFilter().name("test").value("test").type(Channel).network(corNetwork).user(corUser);
        CorFilter entity = corFilterRepository.saveAndFlush(corFilter);

        corFilterService.delete(entity.getId(), corNetwork.getShortcut());

        CorFilter fetchedEntity = corFilterService.findOne(entity.getId(), Channel, corNetwork.getShortcut());
        assertNull(fetchedEntity);
    }
}