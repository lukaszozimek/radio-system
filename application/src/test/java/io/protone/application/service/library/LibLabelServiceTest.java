package io.protone.application.service.library;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorOrganizationRepository;
import io.protone.library.domain.LibLabel;
import io.protone.library.repository.LibLabelRepository;
import io.protone.library.service.LibLabelService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.Optional;

import static io.protone.application.util.TestConstans.*;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 05/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LibLabelServiceTest {
    private static final String TEST_NAME = "TEST";
    @Autowired
    private LibLabelService libLabelService;

    @Autowired
    private LibLabelRepository libLabelRepository;

    @Autowired
    private CorOrganizationRepository corOrganizationRepository;

    @Autowired
    private CorChannelRepository corChannelRepository;

    private CorOrganization corOrganization;

    private CorChannel corChannel;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

    }

    @Test
    public void shoudlSaveLibLabel() throws Exception {
        LibLabel libLabel = factory.manufacturePojo(LibLabel.class).channel(corChannel);

        Optional<LibLabel> savedLibLabel = libLabelService.saveLibLabel(libLabel);

        assertNotNull(savedLibLabel.get());
        assertNotNull(savedLibLabel.get().getId());
        assertNotNull(savedLibLabel.get().getCreatedBy());
    }

    @Test
    public void shoudlNotSaveLibLabel() throws Exception {

        Optional<LibLabel> libLabel = libLabelService.saveLibLabel(null);

        assertFalse(libLabel.isPresent());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoArtistWithSameShortNameInOneNetwork() {

        /// /when
        LibLabel libArtist = factory.manufacturePojo(LibLabel.class);
        libArtist.setId(null);
        libArtist.name(TEST_NAME);
        libArtist.channel(corChannel);
        LibLabel libArtist1 = factory.manufacturePojo(LibLabel.class);
        libArtist1.setId(null);
        libArtist1.setName(TEST_NAME);
        libArtist1.channel(corChannel);

        libArtist = libLabelRepository.saveAndFlush(libArtist);
        libArtist1 = libLabelRepository.saveAndFlush(libArtist1);


    }

    @Test
    public void shouldSaveTwoArtistWithSameNameInDifferentNetwork() {
        //given
        CorOrganization corOrganizationkSecond = factory.manufacturePojo(CorOrganization.class);
        corOrganizationkSecond.setId(null);
        corOrganizationkSecond = corOrganizationRepository.saveAndFlush(corOrganizationkSecond);
        CorChannel corChannelSecond = factory.manufacturePojo(CorChannel.class);
        corChannelSecond.setId(null);
        corChannelSecond.setOrganization(corOrganizationkSecond);
        corChannelSecond = corChannelRepository.save(corChannelSecond);

        /// /when
        LibLabel libArtist = factory.manufacturePojo(LibLabel.class);
        libArtist.setId(null);
        libArtist.name(TEST_NAME);
        libArtist.channel(corChannel);
        LibLabel libArtist1 = factory.manufacturePojo(LibLabel.class);
        libArtist1.setId(null);
        libArtist1.setName(TEST_NAME);
        libArtist1.setChannel(corChannelSecond);

        libArtist = libLabelRepository.saveAndFlush(libArtist);
        libArtist1 = libLabelRepository.saveAndFlush(libArtist1);
    }

    @Test
    public void shouldGetAllLabel() throws Exception {
        //when
        LibLabel libLabel = factory.manufacturePojo(LibLabel.class);
        libLabel.setChannel(corChannel);
        libLabel = libLabelRepository.save(libLabel);

        //then
        Slice<LibLabel> fetchedEntity = libLabelService.findLabels(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(libLabel.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(libLabel.getName(), fetchedEntity.getContent().get(0).getName());
        assertEquals(libLabel.getDescription(), fetchedEntity.getContent().get(0).getDescription());
        assertEquals(libLabel.getChannel(), fetchedEntity.getContent().get(0).getChannel());


    }

    @Test
    public void shouldSaveLabel() throws Exception {
        //when
        LibLabel libLabel = factory.manufacturePojo(LibLabel.class);

        libLabel.setChannel(corChannel);

        //then
        Optional<LibLabel> fetchedEntity = libLabelService.saveLibLabel(libLabel);

        //assert
        assertNotNull(fetchedEntity.get());
        assertNotNull(fetchedEntity.get().getId());
        assertNotNull(fetchedEntity.get().getCreatedBy());
        assertNotNull(libLabel.getName());
        assertNotNull(libLabel.getDescription());
        assertEquals(libLabel.getChannel(), fetchedEntity.get().getChannel());
    }

    @Test
    public void shouldDeleteLabel() throws Exception {
        //when
        LibLabel libLabel = factory.manufacturePojo(LibLabel.class);

        libLabel.setChannel(corChannel);
        libLabel = libLabelRepository.save(libLabel);
        //then
        libLabelService.deleteLabel(libLabel.getId(), corOrganization.getShortcut(), corChannel.getShortcut());
        LibLabel fetchedEntity = libLabelService.findLabel(corOrganization.getShortcut(), corChannel.getShortcut(), libLabel.getId());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetLabel() throws Exception {
        //when
        LibLabel libLabel = factory.manufacturePojo(LibLabel.class);
        libLabel.setChannel(corChannel);
        libLabel = libLabelRepository.save(libLabel);

        //then
        LibLabel fetchedEntity = libLabelService.findLabel(corOrganization.getShortcut(), corChannel.getShortcut(), libLabel.getId());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(libLabel.getId(), fetchedEntity.getId());
        assertEquals(libLabel.getName(), fetchedEntity.getName());
        assertEquals(libLabel.getDescription(), fetchedEntity.getDescription());
        assertEquals(libLabel.getChannel(), fetchedEntity.getChannel());
    }
}
