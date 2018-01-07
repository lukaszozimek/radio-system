package io.protone.application.service.library;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorImageItemService;
import io.protone.library.domain.LibAlbum;
import io.protone.library.domain.LibArtist;
import io.protone.library.repository.LibAlbumRepository;
import io.protone.library.repository.LibArtistRepository;
import io.protone.library.service.LibAlbumService;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import static io.protone.application.util.TestConstans.*;
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
public class LibAlbumServiceTest {


    @Autowired
    private LibAlbumService libAlbumService;

    @Autowired
    private LibAlbumRepository libAlbumRepository;

    @Autowired
    private LibArtistRepository libArtistRepository;

    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private CorImageItemService corImageItemService;

    private CorOrganization corOrganization;

    private CorChannel corChannel;

    private PodamFactory factory;

    @Mock
    private S3Client s3Client;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        MockitoAnnotations.initMocks(this);
        doNothing().when(s3Client).upload(anyString(), anyString(), anyString(), anyObject(), anyString());
        when(s3Client.getCover(anyString(), anyString(), anyString())).thenReturn("test");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        ReflectionTestUtils.setField(corImageItemService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libAlbumService, "corImageItemService", corImageItemService);
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        corChannel.setOrganization(corOrganization);

    }

    @Test
    public void shouldFindAlbum() throws Exception {
        LibArtist libArtist = factory.manufacturePojo(LibArtist.class).name("test").channel(corChannel);

        libArtist = libArtistRepository.saveAndFlush(libArtist);
        LibAlbum album = factory.manufacturePojo(LibAlbum.class).name("test").channel(corChannel).artist(libArtist);
        album.setId(null);
        album.setArtist(libArtist);
        album = libAlbumRepository.save(album);

        //then
        LibAlbum exisiting = libAlbumService.findOrSaveOne("test", libArtist.getName(), corChannel);

        //assert
        assertNotNull(exisiting);
        assertEquals(album.getId(), exisiting.getId());
        assertNotNull(album.getCreatedBy());
    }


    @Test
    public void shouldSaveDefaultAlbum() throws Exception {
        int size = libArtistRepository.findAll().size();
        LibArtist libArtist = factory.manufacturePojo(LibArtist.class).name("test").channel(corChannel);
        libArtist = libArtistRepository.saveAndFlush(libArtist);

        LibAlbum newOne = libAlbumService.findOrSaveOne("test", libArtist.getName(), corChannel);


        int afterAdd = libArtistRepository.findAll().size();

        assertNotNull(newOne);
        assertNotNull(newOne.getId());
        assertEquals(corChannel.getId(), newOne.getChannel().getId());
        assertEquals(size + 1, afterAdd);
    }

    @Test
    public void shouldSaveAlbumWithImage() throws Exception {
        MockMultipartFile logo = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));

        LibArtist libArtist = factory.manufacturePojo(LibArtist.class).name("test").channel(corChannel);
        libArtist = libArtistRepository.saveAndFlush(libArtist);

        LibAlbum libAlbum = factory.manufacturePojo(LibAlbum.class);
        libAlbum.setArtist(libArtist);
        libAlbum.channel(corChannel);
        LibAlbum newOne = libAlbumService.save(libAlbum, logo, null);
        assertNotNull(newOne.getMainImage());
    }

    @Test
    public void shouldSaveAlbumWithImageAndBokklett() throws Exception {
        MockMultipartFile logo = new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg"));
        MockMultipartFile[] booklet = Arrays.array(new MockMultipartFile("logo", Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/avatar/cor/channel/logo.jpg")));

        LibArtist libArtist = factory.manufacturePojo(LibArtist.class).name("test").channel(corChannel);
        libArtist = libArtistRepository.saveAndFlush(libArtist);

        LibAlbum libAlbum = factory.manufacturePojo(LibAlbum.class);
        libAlbum.setArtist(libArtist);
        libAlbum.channel(corChannel);
        LibAlbum newOne = libAlbumService.save(libAlbum, logo, booklet);
        assertNotNull(newOne.getMainImage());
        assertNotNull(newOne.getCover());
        assertEquals(1, newOne.getCover().size());
    }

    @Test
    public void shouldGetAllAlbum() throws Exception {
        //when
        LibAlbum libAlbum = factory.manufacturePojo(LibAlbum.class);
        libAlbum.setChannel(corChannel);
        libAlbum = libAlbumRepository.save(libAlbum);

        //then
        Slice<LibAlbum> fetchedEntity = libAlbumService.findAlbums(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(libAlbum.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(libAlbum.getName(), fetchedEntity.getContent().get(0).getName());
        assertEquals(libAlbum.getDescription(), fetchedEntity.getContent().get(0).getDescription());
        assertEquals(libAlbum.getChannel(), fetchedEntity.getContent().get(0).getChannel());


    }

    @Test
    public void shouldSaveAlbum() throws Exception {
        //when
        LibAlbum libAlbum = factory.manufacturePojo(LibAlbum.class);

        libAlbum.setChannel(corChannel);

        //then
        LibAlbum fetchedEntity = libAlbumService.saveOrUpdate(libAlbum);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertNotNull(libAlbum.getName());
        assertNotNull(libAlbum.getDescription());
        assertEquals(libAlbum.getChannel(), fetchedEntity.getChannel());
    }

    @Test
    public void shouldDeleteAlbum() throws Exception {
        //when
        LibAlbum libAlbum = factory.manufacturePojo(LibAlbum.class);

        libAlbum.setChannel(corChannel);
        libAlbum = libAlbumRepository.save(libAlbum);
        //then
        libAlbumService.deleteAlbum(libAlbum.getId(), corOrganization.getShortcut(), corChannel.getShortcut());
        LibAlbum fetchedEntity = libAlbumService.findAlbum(corOrganization.getShortcut(), corChannel.getShortcut(), libAlbum.getId());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetAlbum() throws Exception {
        //when
        LibAlbum libAlbum = factory.manufacturePojo(LibAlbum.class);
        libAlbum.setChannel(corChannel);
        libAlbum = libAlbumRepository.save(libAlbum);

        //then
        LibAlbum fetchedEntity = libAlbumService.findAlbum(corOrganization.getShortcut(), corChannel.getShortcut(), libAlbum.getId());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(libAlbum.getId(), fetchedEntity.getId());
        assertEquals(libAlbum.getName(), fetchedEntity.getName());
        assertEquals(libAlbum.getDescription(), fetchedEntity.getDescription());
        assertEquals(libAlbum.getChannel(), fetchedEntity.getChannel());
    }
}
