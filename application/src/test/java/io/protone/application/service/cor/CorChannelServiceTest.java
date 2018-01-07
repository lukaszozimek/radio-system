package io.protone.application.service.cor;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorOrganizationRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorImageItemService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 28.04.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CorChannelServiceTest {

    private static final String TEST_NAME = "Tes";
    private static final String TEST_SH = "xxksk";

    @Autowired
    private CorChannelService corChannelService;

    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private CorOrganizationRepository corOrganizationRepository;

    @Autowired
    private CorImageItemService corImageItemService;

    @Mock
    private S3Client s3Client;


    private CorChannel corChannel;

    private CorOrganization corOrganization;

    private PodamFactory factory;

    @Before
    public void initPojos() throws Exception {
        MockitoAnnotations.initMocks(this);
        doNothing().when(s3Client).upload(anyString(), anyString(), anyString(), anyObject(), anyString());
        when(s3Client.getCover(anyString(), anyString(), anyString())).thenReturn("test");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        ReflectionTestUtils.setField(corImageItemService, "s3Client", s3Client);
        ReflectionTestUtils.setField(corChannelService, "corImageItemService", corImageItemService);
        factory = new PodamFactoryImpl();
        corChannel = factory.manufacturePojo(CorChannel.class);
        corChannel.setId(null);

        corOrganization = factory.manufacturePojo(CorOrganization.class);
        corOrganization.setId(null);
        corOrganization = corOrganizationRepository.saveAndFlush(corOrganization);

        corChannel.setOrganization(corOrganization);
        corChannel = corChannelRepository.saveAndFlush(corChannel);
    }

    @Test
    public void findAllChannel() throws Exception {
        Slice<CorChannel> corChannels = corChannelService.findAllChannel(corOrganization.getShortcut(), new PageRequest(0, 10));
        assertNotNull(corChannels.getContent());
        assertEquals(corChannels.getContent().size(), 1);
        assertEquals(corChannels.getContent().get(0).getId(), corChannel.getId());

        assertNotNull(corChannels.getContent().get(0).getCreatedBy());
    }

    @Test
    public void findChannel() throws Exception {
        CorChannel fetechedEntity = corChannelService.findChannel(corOrganization.getShortcut(), corChannel.getShortcut());
        assertEquals(fetechedEntity.getDescription(), corChannel.getDescription());
        assertEquals(fetechedEntity.getId(), corChannel.getId());
        assertEquals(fetechedEntity.getName(), corChannel.getName());
        assertEquals(fetechedEntity.getOrganization().getId(), corChannel.getOrganization().getId());

    }


    @Test
    public void save() throws Exception {
        CorChannel localChannel = new CorChannel();
        localChannel.setShortcut("Txp");
        localChannel.setName("testTestowy12412");
        localChannel.setOrganization(corOrganization);
        localChannel = corChannelService.save(localChannel);
        assertNotNull(localChannel);
        assertNotNull(localChannel.getId());
        assertNotNull(localChannel.getCreatedBy());

    }

    @Test
    public void deleteChannel() throws Exception {
        corChannelService.deleteChannel(corOrganization.getShortcut(), corChannel.getShortcut());
        CorChannel fetechedEntity = corChannelService.findChannel(corOrganization.getShortcut(), corChannel.getShortcut());

        assertNull(fetechedEntity);
    }
//
//    @Test(expected = DataIntegrityViolationException.class)
//    public void shouldNotSaveTwoCorChannelWithSameShortNameInOneOrganization() {
//
//        /// /when
//        CorChannel corChannel = factory.manufacturePojo(CorChannel.class);
//        corChannel.setId(null);
//        corChannel.setShortcut(TEST_NAME);
//        corChannel.setOrganization(corOrganization);
//        CorChannel corChannel1 = factory.manufacturePojo(CorChannel.class);
//        corChannel1.setId(null);
//        corChannel1.setShortcut(TEST_NAME);
//        corChannel1.setOrganization(corOrganization);
//
//        corChannel = corChannelService.save(corChannel);
//        corChannel1 = corChannelService.save(corChannel1);
//
//    }

    @Test
    public void shouldSaveTwoCorChannelWithSameNameInDifferentNetwork() {
        //given
        CorOrganization secondOrganization = factory.manufacturePojo(CorOrganization.class);
        secondOrganization.setId(null);
        secondOrganization = corOrganizationRepository.save(secondOrganization);

        /// /when
        CorChannel corChannel = factory.manufacturePojo(CorChannel.class);
        corChannel.setId(null);
        corChannel.setShortcut(TEST_NAME);
        corChannel.setOrganization(corOrganization);
        CorChannel corChannel1 = factory.manufacturePojo(CorChannel.class);
        corChannel1.setId(null);
        corChannel1.setShortcut(TEST_NAME);
        corChannel1.setOrganization(secondOrganization);

        corChannel = corChannelService.save(corChannel);
        corChannel1 = corChannelService.save(corChannel1);


    }

    @Ignore
    public void shouldSaveCorChannelWithImage() throws Exception {
        MockMultipartFile logo = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        CorChannel localChannel = factory.manufacturePojo(CorChannel.class);
        localChannel.setShortcut(TEST_SH);
        localChannel.setOrganization(corOrganization);
        localChannel = corChannelService.save(localChannel, logo);
        assertNotNull(localChannel);
        assertNotNull(localChannel.getId());
        assertNotNull(localChannel.getCreatedBy());
        assertNotNull(localChannel.getCorImageItem());

    }

}
