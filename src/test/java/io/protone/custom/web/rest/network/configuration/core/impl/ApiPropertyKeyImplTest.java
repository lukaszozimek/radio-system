package io.protone.custom.web.rest.network.configuration.core.impl;

import io.protone.ProtoneApp;
import io.protone.domain.CorPropertyKey;
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
public class ApiPropertyKeyImplTest {
    @Autowired
    private ApiPropertyKeyImpl apiPropertyKey;

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

        this.restCfgMarkerConfigurationMockMvc = MockMvcBuilders.standaloneSetup(apiPropertyKey)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    public static CorPropertyKey createEntity(EntityManager em) {
        CorPropertyKey corPropertyKey = new CorPropertyKey();

        return corPropertyKey;
    }
    @Test
    public void getPropertyKeyUsingGET() throws Exception {

    }

    @Test
    public void deletePropertyKeyUsingDELETE() throws Exception {

    }

    @Test
    public void getAllPropertyKeysUsingGET() throws Exception {

    }

    @Test
    public void updatePropertyKeyUsingPUT() throws Exception {

    }

    @Test
    public void createPropertyKeyUsingPOST() throws Exception {

    }

}
