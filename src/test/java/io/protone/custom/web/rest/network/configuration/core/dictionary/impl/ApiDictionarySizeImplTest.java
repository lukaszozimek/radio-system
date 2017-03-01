package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.ProtoneApp;
import io.protone.domain.CorSize;
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
public class ApiDictionarySizeImplTest {
    @Autowired
    private ApiDictionarySizeImpl apiDictionarySize;

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

        this.restCfgMarkerConfigurationMockMvc = MockMvcBuilders.standaloneSetup(apiDictionarySize)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    public static CorSize createEntity(EntityManager em) {
        CorSize corSize = new CorSize();

        return corSize;
    }
    @Test
    public void createSizeUsingPOST() throws Exception {

    }

    @Test
    public void deleteSizeUsingDELETE() throws Exception {

    }

    @Test
    public void getAllSizeUsingGET() throws Exception {

    }

    @Test
    public void getSizeUsingGET() throws Exception {

    }

    @Test
    public void updateSizeUsingPUT() throws Exception {

    }

}
