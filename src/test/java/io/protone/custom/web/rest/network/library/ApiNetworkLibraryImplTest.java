package io.protone.custom.web.rest.network.library;

import io.protone.ProtoneApp;
import io.protone.custom.web.rest.network.library.impl.ApiNetworkLibraryImpl;
import io.protone.domain.LibLibrary;
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
public class ApiNetworkLibraryImplTest {
    @Autowired
    private ApiNetworkLibraryImpl apiNetworkLibrary;

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

        this.restCfgMarkerConfigurationMockMvc = MockMvcBuilders.standaloneSetup(apiNetworkLibrary)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    public static LibLibrary createEntity(EntityManager em) {
        LibLibrary libLibrary = new LibLibrary();

        return libLibrary;
    }
    @Test
    public void updateLibraryUsingPUT() throws Exception {

    }

    @Test
    public void getAllLibrariesUsingGET() throws Exception {

    }

    @Test
    public void createLibraryUsingPOST() throws Exception {

    }

    @Test
    public void deleteLibraryUsingDELETE() throws Exception {

    }

    @Test
    public void getLibraryUsingGET() throws Exception {

    }

}
