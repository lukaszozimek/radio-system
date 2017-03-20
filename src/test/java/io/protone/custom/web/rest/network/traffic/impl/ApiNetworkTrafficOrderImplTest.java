package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.ProtoneApp;
import io.protone.domain.TraInvoice;
import io.protone.domain.TraOrder;
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
public class ApiNetworkTrafficOrderImplTest {
    @Autowired
    private ApiNetworkTrafficOrderImpl apiNetworkTrafficOrder;

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

        this.restCfgMarkerConfigurationMockMvc = MockMvcBuilders.standaloneSetup(apiNetworkTrafficOrder)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    public static TraOrder createEntity(EntityManager em) {
        TraOrder traOrder = new TraOrder();

        return traOrder;
    }
    @Test
    public void updateAnOrderUsingPUT() throws Exception {

    }

    @Test
    public void createAnOrderUsingPOST() throws Exception {

    }

    @Test
    public void getAllAnOrdersUsingGET() throws Exception {

    }

    @Test
    public void getAnOrderUsingGET() throws Exception {

    }

    @Test
    public void deleteAnOrderUsingDELETE() throws Exception {

    }

    @Test
    public void notifyCustomerAboutUnpaidOrderUsingPOST() throws Exception {

    }

}
