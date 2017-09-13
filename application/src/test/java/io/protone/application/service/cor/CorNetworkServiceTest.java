package io.protone.application.service.cor;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.s3.exceptions.S3Exception;
import io.protone.core.s3.exceptions.UploadException;
import io.protone.core.s3.exceptions.UrlGenerationResourceException;
import io.protone.core.service.CorImageItemService;
import io.protone.core.service.CorNetworkService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.List;

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
public class CorNetworkServiceTest {


    private static final String TEST_NAME = "tet";
    @Autowired
    private CorNetworkService corNetworkService;
    @Autowired
    private CorImageItemService corImageItemService;

    @Mock
    private S3Client s3Client;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    @Before
    public void initPojos() throws UploadException, S3Exception, UrlGenerationResourceException {
        MockitoAnnotations.initMocks(this);
        doNothing().when(s3Client).upload(anyString(), anyString(), anyString(), anyObject(), anyString());
        when(s3Client.getCover(anyString(),anyString(), anyString())).thenReturn("test");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        ReflectionTestUtils.setField(corImageItemService, "s3Client", s3Client);
        ReflectionTestUtils.setField(corNetworkService, "corImageItemService", corImageItemService);

        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
    }

    @Test
    public void findAllNetworks() throws Exception {
        List<CorNetwork> corNetworkList = corNetworkService.findAllNetworks();
        assertNotNull(corNetworkList);
        assertFalse(corNetworkList.isEmpty());
        assertEquals(2, corNetworkList.size());

    }

    @Test
    public void findNetwork() throws Exception {
        CorNetwork local = corNetworkService.findNetwork(corNetwork.getShortcut());
        assertNotNull(local);
        assertEquals(corNetwork.getId(), local.getId());

    }


    @Test
    public void save() throws Exception {
        factory = new PodamFactoryImpl();
        CorNetwork local = factory.manufacturePojo(CorNetwork.class);
        local.setId(null);
        local = corNetworkService.save(corNetwork);

        assertNotNull(local.getId());
        assertNotNull(local.getCreatedBy());

    }

    @Test
    public void deleteNetwork() throws Exception {
        corNetworkService.deleteNetwork(corNetwork.getShortcut());
        CorNetwork local = corNetworkService.findNetwork(corNetwork.getShortcut());
        assertNull(local);

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoNetworksWithSameShortName() {

        /// /when
        CorNetwork corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork.setShortcut(TEST_NAME);

        CorNetwork corNetwork1 = factory.manufacturePojo(CorNetwork.class);
        corNetwork1.setId(null);
        corNetwork1.setShortcut(TEST_NAME);

        corNetwork = corNetworkService.save(corNetwork);
        corNetwork1 = corNetworkService.save(corNetwork1);

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoNetworksWithSameName() {

        /// /when
        CorNetwork corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork.setName(TEST_NAME);

        CorNetwork corNetwork1 = factory.manufacturePojo(CorNetwork.class);
        corNetwork1.setId(null);
        corNetwork1.setName(TEST_NAME);

        corNetwork = corNetworkService.save(corNetwork);
        corNetwork1 = corNetworkService.save(corNetwork1);

    }

    @Test
    public void shouldSaveCorNetworkWithImage() throws Exception {
        MockMultipartFile logo = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        factory = new PodamFactoryImpl();
        CorNetwork local = factory.manufacturePojo(CorNetwork.class);
        local.setId(null);
        local = corNetworkService.save(corNetwork, logo);

        assertNotNull(local.getId());
        assertNotNull(local.getCreatedBy());
        assertNotNull(local.getCorImageItem());


    }
}
