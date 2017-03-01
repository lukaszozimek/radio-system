package io.protone.custom.web.rest.network.configuration.traffic.impl;

import io.protone.ProtoneApp;
import io.protone.domain.CorTax;
import io.protone.domain.TraIndustry;
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
public class ApiConfigurationTrafficDictionaryTaxImplTest {
    @Autowired
    private ApiConfigurationTrafficDictionaryTaxImpl apiConfigurationTrafficDictionaryTax;

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

        this.restCfgMarkerConfigurationMockMvc = MockMvcBuilders.standaloneSetup(apiConfigurationTrafficDictionaryTax)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    public static CorTax createEntity(EntityManager em) {
        CorTax corTax = new CorTax();

        return corTax;
    }

    @Test
    public void getAllTaxesUsingGET() throws Exception {

    }

    @Test
    public void getTaxUsingGET() throws Exception {

    }

    @Test
    public void updateTaxUsingPUT() throws Exception {

    }

    @Test
    public void createTaxUsingPOST() throws Exception {

    }

    @Test
    public void deleteTaxUsingDELETE() throws Exception {

    }

}
