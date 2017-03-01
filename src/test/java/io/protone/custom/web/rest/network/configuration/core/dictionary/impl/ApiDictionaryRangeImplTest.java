package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.ProtoneApp;
import io.protone.domain.CorPerson;
import io.protone.domain.CorRange;
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
public class ApiDictionaryRangeImplTest {
    @Autowired
    private ApiDictionaryRangeImpl apiDictionaryRange;

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

        this.restCfgMarkerConfigurationMockMvc = MockMvcBuilders.standaloneSetup(apiDictionaryRange)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    public static CorRange createEntity(EntityManager em) {
        CorRange corRange = new CorRange();

        return corRange;
    }
    @Test
    public void createRangeUsingPOST() throws Exception {

    }

    @Test
    public void getAllRangeUsingGET() throws Exception {

    }

    @Test
    public void getRangeUsingGET() throws Exception {

    }

    @Test
    public void updateRangeUsingPUT() throws Exception {

    }

    @Test
    public void deleteRangeUsingDELETE() throws Exception {

    }

}
