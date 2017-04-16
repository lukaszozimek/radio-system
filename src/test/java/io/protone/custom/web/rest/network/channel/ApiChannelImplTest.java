package io.protone.custom.web.rest.network.channel;

import io.protone.ProtoneApp;
import io.protone.custom.web.rest.network.channel.impl.ApiChannelImpl;
import io.protone.domain.CorChannel;
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
public class ApiChannelImplTest {
    @Autowired
    private ApiChannelImpl apiChannel;

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

        this.restCfgMarkerConfigurationMockMvc = MockMvcBuilders.standaloneSetup(apiChannel)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    public static CorChannel createEntity(EntityManager em) {
        CorChannel corChannel = new CorChannel();

        return corChannel;
    }

    @Test
    public void updateChannelUsingPUT() throws Exception {

    }

    @Test
    public void createChannelUsingPOST() throws Exception {

    }

    @Test
    public void getAllChannelsUsingGET() throws Exception {

    }

    @Test
    public void getChannelUsingGET() throws Exception {

    }

    @Test
    public void deleteChannelUsingDELETE() throws Exception {

    }

}
