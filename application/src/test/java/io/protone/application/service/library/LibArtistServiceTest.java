package io.protone.application.service.library;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorImageItemService;
import io.protone.library.domain.LibArtist;
import io.protone.library.repository.LibArtistRepository;
import io.protone.library.service.LibArtistService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 30/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LibArtistServiceTest {


    private static final String TEST_NAME = "TEST";
    @Autowired
    private LibArtistService libArtistService;

    @Autowired
    private LibArtistRepository libArtistRepository;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CorImageItemService corImageItemService;

    @Mock
    private S3Client s3Client;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        doNothing().when(s3Client).upload(anyString(),anyString(), anyString(), anyObject(), anyString());
        when(s3Client.getCover(anyString(),anyString(), anyString())).thenReturn("test");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        ReflectionTestUtils.setField(corImageItemService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libArtistService, "corImageItemService", corImageItemService);
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);

    }

    @Test
    public void shouldFindOne() throws Exception {
        //when
        LibArtist libArtist = factory.manufacturePojo(LibArtist.class).name("test").network(corNetwork);
        libArtist.setId(null);
        libArtist = libArtistRepository.save(libArtist);

        //then
        LibArtist exisiting = libArtistService.findOrSaveOne("test", corNetwork);

        //assert
        assertNotNull(exisiting);
        assertEquals(libArtist.getId(), exisiting.getId());
        assertNotNull(libArtist.getCreatedBy());

    }

    @Test
    public void shouldSaveNewOne() throws Exception {
        int size = libArtistRepository.findAll().size();

        LibArtist newOne = libArtistService.findOrSaveOne("test", corNetwork);


        int afterAdd = libArtistRepository.findAll().size();

        assertNotNull(newOne);
        assertNotNull(newOne.getId());
        assertEquals(corNetwork.getId(), newOne.getNetwork().getId());
        assertEquals(size + 1, afterAdd);
    }

    @Test
    public void shouldNotCreateAnyWithOutName() throws Exception {
        int size = libArtistRepository.findAll().size();

        LibArtist newOne = libArtistService.findOrSaveOne(null, corNetwork);


        int afterAdd = libArtistRepository.findAll().size();

        assertNull(newOne);
        assertEquals(size, afterAdd);
    }

    @Test
    public void shouldNotCreateAnyNetwork() throws Exception {
        int size = libArtistRepository.findAll().size();

        LibArtist newOne = libArtistService.findOrSaveOne("test", null);


        int afterAdd = libArtistRepository.findAll().size();

        assertNull(newOne);
        assertEquals(size, afterAdd);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoArtistWithSameShortNameInOneNetwork() {

        /// /when
        LibArtist libArtist = factory.manufacturePojo(LibArtist.class);
        libArtist.setId(null);
        libArtist.name(TEST_NAME);
        libArtist.setNetwork(corNetwork);
        LibArtist libArtist1 = factory.manufacturePojo(LibArtist.class);
        libArtist1.setId(null);
        libArtist1.setName(TEST_NAME);
        libArtist1.setNetwork(corNetwork);

        libArtist = libArtistRepository.saveAndFlush(libArtist);
        libArtist1 = libArtistRepository.saveAndFlush(libArtist1);


    }

    @Test
    public void shouldSaveTwoArtistWithSameNameInDifferentNetwork() {
        //given
        CorNetwork corNetworkSecond = factory.manufacturePojo(CorNetwork.class);
        corNetworkSecond.setId(null);
        corNetworkSecond = corNetworkRepository.save(corNetworkSecond);

        /// /when
        LibArtist libArtist = factory.manufacturePojo(LibArtist.class);
        libArtist.setId(null);
        libArtist.name(TEST_NAME);
        libArtist.setNetwork(corNetwork);
        LibArtist libArtist1 = factory.manufacturePojo(LibArtist.class);
        libArtist1.setId(null);
        libArtist1.setName(TEST_NAME);
        libArtist1.setNetwork(corNetworkSecond);

        libArtist = libArtistRepository.saveAndFlush(libArtist);
        libArtist1 = libArtistRepository.saveAndFlush(libArtist1);


    }

    @Test
    public void shouldSaveArtistWithImage() throws Exception {
        MockMultipartFile logo = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));

        LibArtist libArtist = factory.manufacturePojo(LibArtist.class);
        libArtist.network(corNetwork);
        LibArtist newOne = libArtistService.save(libArtist, logo);
        assertNotNull(newOne.getMainImage());
    }

    @Test
    public void shouldGetAllCompany() throws Exception {
        //when
        LibArtist libArtist = factory.manufacturePojo(LibArtist.class);
        libArtist.setNetwork(corNetwork);
        libArtist = libArtistRepository.save(libArtist);

        //then
        Slice<LibArtist> fetchedEntity = libArtistService.findArtists(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(libArtist.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(libArtist.getName(), fetchedEntity.getContent().get(0).getName());
        assertEquals(libArtist.getDescription(), fetchedEntity.getContent().get(0).getDescription());
        assertEquals(libArtist.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());


    }

    @Test
    public void shouldSaveCompany() throws Exception {
        //when
        LibArtist libArtist = factory.manufacturePojo(LibArtist.class);

        libArtist.setNetwork(corNetwork);

        //then
        LibArtist fetchedEntity = libArtistService.createOrUpdateArtist(libArtist);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertNotNull(libArtist.getName());
        assertNotNull(libArtist.getDescription());
        assertEquals(libArtist.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteCompany() throws Exception {
        //when
        LibArtist libArtist = factory.manufacturePojo(LibArtist.class);

        libArtist.setNetwork(corNetwork);
        libArtist = libArtistRepository.save(libArtist);
        //then
        libArtistService.deleteArtist(libArtist.getId(), corNetwork.getShortcut());
        LibArtist fetchedEntity = libArtistService.findArtist(corNetwork.getShortcut(), libArtist.getId());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetCompany() throws Exception {
        //when
        LibArtist libArtist = factory.manufacturePojo(LibArtist.class);
        libArtist.setNetwork(corNetwork);
        libArtist = libArtistRepository.save(libArtist);

        //then
        LibArtist fetchedEntity = libArtistService.findArtist(corNetwork.getShortcut(), libArtist.getId());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(libArtist.getId(), fetchedEntity.getId());
        assertEquals(libArtist.getName(), fetchedEntity.getName());
        assertEquals(libArtist.getDescription(), fetchedEntity.getDescription());
        assertEquals(libArtist.getNetwork(), fetchedEntity.getNetwork());
    }
}
