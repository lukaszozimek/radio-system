package io.protone.application.service.library;

import com.google.common.collect.Sets;
import io.protone.core.domain.CorAuthority;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.repository.CorUserRepository;
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

import static io.protone.core.security.AuthoritiesConstants.ADMIN;
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
    protected CorNetwork corNetwork;
    protected LibMediaLibrary libMediaLibrary;
    protected PodamFactory factory;
    protected Metadata metadata;
    protected ParseContext pcontext;
    protected Parser parser;
    protected BodyContentHandler handler;
    @Autowired
    private CorUserRepository corUserRepository;
    private CorUser corUser;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
        libMediaLibrary = factory.manufacturePojo(LibMediaLibrary.class);
        libMediaLibrary.setNetwork(corNetwork);
        libMediaLibrary = libLibraryRepository.saveAndFlush(libMediaLibrary);
        corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setChannels(null);
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser = corUserRepository.saveAndFlush(corUser);
        doNothing().when(s3Client).delete(anyString(), anyString(), anyObject());
        doNothing().when(s3Client).upload(anyString(), anyString(), anyString(), anyObject(), anyString());

        when(corUserService.getUserWithAuthoritiesByLogin(anyString())).thenReturn(Optional.of(corUser));

        parser = new AutoDetectParser();
        handler = new BodyContentHandler();
        metadata = new Metadata();
        pcontext = new ParseContext();
    }

}
