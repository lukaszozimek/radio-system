package io.protone.application.service.language;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorAddress;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorPerson;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorImageItemService;
import io.protone.crm.domain.CrmContact;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.repostiory.CrmContactRepository;
import io.protone.crm.service.CrmContactService;
import io.protone.language.service.LaPQLService;
import org.apache.tika.exception.TikaException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.xml.sax.SAXException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 29.04.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LaPQLServiceTest {

    @Autowired
    private LaPQLService pqlService;

    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void shouldReturnListCorObjectRequestedInQuery(){

    }

    @Test
    public void shouldReturnListCrmObjectRequestedInQuery(){

    }
    @Test
    public void shouldReturnListTrafficObjectRequestedInQuery(){

    }

    @Test
    public void shouldReturnListLibraryObjectRequestedInQuery(){

    }

    @Test
    public void shouldReturnListWithLimitationObjectRequestedInQuery(){

    }

    @Test
    public void shouldReturnListWithLimitationAndGroupedByObjectRequestedInQuery(){

    }



}
