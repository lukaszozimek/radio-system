package io.protone.custom.web.rest.network.library;

import io.protone.ProtoneApp;
import io.protone.custom.web.rest.network.library.impl.ApiNetworkLibraryItemImpl;
import io.protone.domain.LibMediaItem;
import io.protone.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class ApiNetworkLibraryItemImplTest {
    @Autowired
    private ApiNetworkLibraryItemImpl apiNetworkLibraryItem;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCfgMarkerConfigurationMockMvc;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.restCfgMarkerConfigurationMockMvc = MockMvcBuilders.standaloneSetup(apiNetworkLibraryItem)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    public static LibMediaItem createEntity(EntityManager em) {
        LibMediaItem libMediaItem = new LibMediaItem();

        return libMediaItem;
    }
    @Test
    public void updateItemByNetworShortcutAndLibraryPrefixUsingPUT() throws Exception {

    }

    @Test
    public void getAllItemsByNetworShortcutAndLibraryPrefixUsingGET() throws Exception {

    }

    @Test
    public void uploadItemsByNetworShortcutAndLibraryPrefix() throws Exception {

    }

    @Test
    public void streamItemByNetworShortcutAndLibrarUsingGET() throws Exception {

    }

    @Test
    public void getItemByNetworShortcutAndLibrarUsingGET() throws Exception {

    }

    @Test
    public void deleteItemByNetworShortcutAndLibrarUsingDELETE() throws Exception {

    }

}
