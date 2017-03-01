package io.protone.custom.web.rest.network.configuration.crm.impl;

import io.protone.ProtoneApp;
import io.protone.domain.CrmLeadSource;
import io.protone.domain.CrmLeadStatus;
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
public class ApiConfigurationCrmDictionaryLeadStatusImplTest {
    @Autowired
    private ApiConfigurationCrmDictionaryLeadStatusImpl apiConfigurationCrmDictionaryLeadStatus;

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

        this.restCfgMarkerConfigurationMockMvc = MockMvcBuilders.standaloneSetup(apiConfigurationCrmDictionaryLeadStatus)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    public static CrmLeadStatus createEntity(EntityManager em) {
        CrmLeadStatus crmLeadStatus = new CrmLeadStatus();

        return crmLeadStatus;
    }
    @Test
    public void deleteLeadStatusUsingDELETE() throws Exception {

    }

    @Test
    public void getAllLeadStatusUsingGET() throws Exception {

    }

    @Test
    public void getLeadStatusUsingGET() throws Exception {

    }

    @Test
    public void updateleadStatusUsingPUT() throws Exception {

    }

    @Test
    public void createLeadStatusUsingPOST() throws Exception {

    }

}
