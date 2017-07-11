package io.protone.application.service.cor;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorImageItem;
import io.protone.core.repository.CorImageItemRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorImageItemService;
import org.assertj.core.util.Strings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;

/**
 * Created by lukaszozimek on 06/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CorImageItemServiceTest {

    @Autowired
    private CorImageItemService corImageItemService;

    @Autowired
    private CorImageItemRepository corImageItemRepository;
    @Mock
    private S3Client s3Client;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        doNothing().when(s3Client).upload(anyString(), anyString(), anyObject(), anyString());
        when(s3Client.getCover(anyString(), anyString())).thenReturn("test");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        ReflectionTestUtils.setField(corImageItemService, "s3Client", s3Client);
        factory = new PodamFactoryImpl();
    }

    @Test
    public void saveImageItems() throws Exception {
        int size = corImageItemRepository.findAll().size();

        MockMultipartFile logo = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        CorImageItem corImageItem = corImageItemService.saveImageItem(logo);
        int afterAdd = corImageItemRepository.findAll().size();
        notNull(corImageItem);
        assertEquals(size + 1, afterAdd);
    }

    @Test
    public void saveImageItem() throws Exception {
        int size = corImageItemRepository.findAll().size();

        MockMultipartFile logo = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        MockMultipartFile secondLogo = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        MockMultipartFile[] mockMultipartFiles = {logo, secondLogo};
        Set<CorImageItem> corImageItem = corImageItemService.saveImageItems(mockMultipartFiles);
        int afterAdd = corImageItemRepository.findAll().size();
        assertNotNull(corImageItem);
        notEmpty(corImageItem);
        assertEquals(size + 2, afterAdd);
    }

    @Test
    public void getValidLinkToResource() throws Exception {
        CorImageItem corImageItem = factory.manufacturePojo(CorImageItem.class);
        corImageItem.setPublicUrl(null);
        CorImageItem corImageItem1 = corImageItemService.getValidLinkToResource(corImageItem);

        assertNotNull(corImageItem1);
        assertTrue(!Strings.isNullOrEmpty(corImageItem1.getPublicUrl()));

    }
}