package io.protone.service.library.file.impl;

import com.google.common.collect.Sets;
import io.protone.ProtoneApp;
import io.protone.config.s3.S3Client;
import io.protone.domain.CorAuthority;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorUser;
import io.protone.domain.LibLibrary;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.cor.CorUserRepository;
import io.protone.repository.library.LibLibraryRepository;
import io.protone.service.cor.CorUserService;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static io.protone.security.AuthoritiesConstants.ADMIN;
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

    @Autowired
    private CorUserRepository corUserRepository;
    @Mock
    protected S3Client s3Client;

    @Mock
    protected CorUserService corUserService;

    private CorUser corUser;

    protected CorNetwork corNetwork;

    protected LibLibrary libLibrary;

    protected PodamFactory factory;

    protected Metadata metadata;

    protected ParseContext pcontext;

    protected Parser parser;

    protected BodyContentHandler handler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
        libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setNetwork(corNetwork);
        libLibrary = libLibraryRepository.saveAndFlush(libLibrary);
        corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setChannels(null);
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser = corUserRepository.saveAndFlush(corUser);
        doNothing().when(s3Client).delete(anyObject());
        doNothing().when(s3Client).upload(anyString(), anyObject(), anyString());

        when(corUserService.getUserWithAuthoritiesByLogin(anyString())).thenReturn(Optional.of(corUser));

        parser = new AutoDetectParser();
        handler = new BodyContentHandler();
        metadata = new Metadata();
        pcontext = new ParseContext();
    }

}
