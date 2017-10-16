package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.repository.SchEmissionRepository;
import io.protone.scheduler.service.SchEmissionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.Set;

import static io.protone.application.service.scheduler.base.SchedulerBaseTest.LIBRARY_ID;
import static io.protone.application.service.scheduler.base.SchedulerBaseTest.buildEmissionForWithAttachment;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchEmissionServiceTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchEmissionService schEmissionService;

    @Autowired
    private SchEmissionRepository schEmissionRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    private LibMediaItem libMediaItem;

    private LibMediaLibrary libMediaLibrary;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    @Before
    public void setUp() throws Exception {
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
        libMediaLibrary = new LibMediaLibrary();
        libMediaLibrary.setId(LIBRARY_ID);
        libMediaItem = new LibMediaItem().name("test").library(libMediaLibrary).idx("test").length(40.0).network(corNetwork);
        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);
    }

    @Test
    public void shouldSaveSchEmission() throws Exception {
        //when
        SchEmission schEmission = factory.manufacturePojo(SchEmission.class);
        schEmission.setNetwork(corNetwork);
        schEmission.setChannel(corChannel);
        //then
        Set<SchEmission> fetchedEntity = schEmissionService.saveEmission(Sets.newSet(schEmission));

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.stream().findFirst().get().getId());
        assertEquals(schEmission.getNetwork(), fetchedEntity.stream().findFirst().get().getNetwork());
    }

    @Test
    public void shouldSaveSchEmissionWithAttachment() throws Exception {
        //when
        Set<SchEmission> schEmissions = Sets.newSet(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));

        //then
        Set<SchEmission> savedEmissions = schEmissionService.saveEmission(schEmissions);

        //assert
        assertNotNull(savedEmissions);
        assertNotNull(savedEmissions.stream().findFirst().get().getId());
        assertEquals(schEmissions.stream().findFirst().get().getNetwork(), savedEmissions.stream().findFirst().get().getNetwork());
        assertNotNull(savedEmissions.stream().findFirst().get().getAttachments());
        assertNotNull(savedEmissions.stream().findFirst().get().getAttachments().stream().findFirst().get().getId());
    }

    @Test
    public void shouldDeleteSchEmission() throws Exception {

        //when
        SchEmission schEmission = factory.manufacturePojo(SchEmission.class);
        schEmission.setNetwork(corNetwork);
        schEmission.setChannel(corChannel);
        schEmission = schEmissionRepository.saveAndFlush(schEmission);

        //then
        schEmissionService.deleteEmissions(Sets.newSet(schEmission));
        SchEmission fetchedEntity = schEmissionRepository.findOne(schEmission.getId());

        //assert
        assertNull(fetchedEntity);
    }

}