package io.protone.application.web.api.language;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.language.impl.LaPQLResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.api.dto.thin.CorFilterThinDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.enumeration.CorEntityTypeEnum;
import io.protone.core.mapper.CorFilterMapper;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.language.service.pql.LaPQLMappingService;
import io.protone.language.service.pql.LaPQLService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static io.protone.application.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by lukaszozimek on 02/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LaPQLResourceImplTest {

    @Autowired
    private LaPQLService pqlService;

    @Autowired
    private CorFilterMapper corFilterMapper;

    @Autowired
    private LaPQLMappingService pqlMappingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCorFilterMockMvc;

    private CorNetwork corNetwork;
    private String SAMPLE_NET = "random";
    @Autowired
    private CorNetworkRepository corNetworkRepository;
    @Autowired
    private CorChannelRepository corChannelRepository;
    private String SAMPLE_SHORTCUT = "ran";
    private String SAMPLE = "randomRadnomistinc";


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LaPQLResourceImpl laPQLResource = new LaPQLResourceImpl();

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);

        ReflectionTestUtils.setField(laPQLResource, "pqlService", pqlService);
        ReflectionTestUtils.setField(laPQLResource, "corFilterMapper", corFilterMapper);

        ReflectionTestUtils.setField(laPQLResource, "pqlMappingService", pqlMappingService);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restCorFilterMockMvc = MockMvcBuilders.standaloneSetup(laPQLResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Test
    public void shouldReturnCorChannels() throws Exception {
        CorNetwork corNetwork = new CorNetwork().shortcut(SAMPLE_NET).name(SAMPLE_NET);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
        CorChannel corChannel = new CorChannel().shortcut(SAMPLE_SHORTCUT).name(SAMPLE).network(corNetwork);
        corChannelRepository.saveAndFlush(corChannel);
        CorFilterThinDTO corFilterThinDTO = new CorFilterThinDTO().type(CorEntityTypeEnum.Channel).value("Core Channel AND name='randomRadnomistinc'");

        // Get the crmTaskComment
        restCorFilterMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/language/pql/filter", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(corFilterThinDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").value(hasItem(corChannel.getId().intValue())))
                .andExpect(jsonPath("$.[*].shortcut").value(hasItem(corChannel.getShortcut().toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(corChannel.getName().toString())));

    }
}
