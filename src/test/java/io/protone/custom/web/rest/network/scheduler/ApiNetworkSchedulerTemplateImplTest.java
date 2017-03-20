package io.protone.custom.web.rest.network.scheduler;

import io.protone.ProtoneApp;
import io.protone.domain.SchPlaylist;
import io.protone.domain.SchTemplate;
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
public class ApiNetworkSchedulerTemplateImplTest {
    @Autowired
    private ApiNetworkSchedulerTemplateImpl apiNetworkSchedulerTemplate;

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

        this.restCfgMarkerConfigurationMockMvc = MockMvcBuilders.standaloneSetup(apiNetworkSchedulerTemplate)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    public static SchTemplate createEntity(EntityManager em) {
        SchTemplate schTemplate = new SchTemplate();

        return schTemplate;
    }
    @Test
    public void updateSchedulerTemplatesUsingPUT() throws Exception {

    }

    @Test
    public void creatSchedulerTemplatesUsingPOST() throws Exception {

    }

    @Test
    public void getAllSchedulerTemplatesUsingGET() throws Exception {

    }

    @Test
    public void getSchedulerTemplateUsingGET() throws Exception {

    }

    @Test
    public void deleteSchedulerTemplateUsingDELETE() throws Exception {

    }

}
