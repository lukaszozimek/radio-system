package io.protone.application.service.library;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.domain.CorUser;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorUserService;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.repository.LibLibraryRepository;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Optional;

import static io.protone.application.util.TestConstans.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 30/05/2017.
 */
public class LibFileServiceBaseTest {

    protected static final long SAMPLE_FILE_SIZE = 50000;

    protected static final String SAMPLE_FILE_NAME = "test";

    @Autowired
    protected LibLibraryRepository libLibraryRepository;

    @Autowired
    protected CorNetworkRepository corNetworkRepository;

    @Mock
    protected S3Client s3Client;

    @Mock
    protected CorUserService corUserService;

    protected CorOrganization corOrganization;

    protected CorChannel corChannel;

    protected LibMediaLibrary libMediaLibrary;

    protected PodamFactory factory;

    protected Metadata metadata;

    protected ParseContext pcontext;

    protected Parser parser;

    protected BodyContentHandler handler;

    private CorUser corUser;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        MockitoAnnotations.initMocks(this);
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        corChannel.setOrganization(corOrganization);

        libMediaLibrary = libLibraryRepository.findOne(2L);

        corUser = new CorUser();
        corUser.setId(3L);
        corUser.setLogin("admin");
        corUser.setOrganization(corOrganization);

        doNothing().when(s3Client).delete(anyString(), anyString(), anyObject());
        doNothing().when(s3Client).upload(anyString(), anyString(), anyString(), anyObject(), anyString());

        when(corUserService.getUserWithAuthoritiesByLogin(anyString())).thenReturn(Optional.of(corUser));

        parser = new AutoDetectParser();
        handler = new BodyContentHandler();
        metadata = new Metadata();
        pcontext = new ParseContext();
    }

}
