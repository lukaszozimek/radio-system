package io.protone.application.service.traffic;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.traffic.domain.TraMediaPlanTemplate;
import io.protone.traffic.repository.TraMediaPlanTemplateRepository;
import io.protone.traffic.service.TraMediaPlanTemplateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static io.protone.application.util.TestConstans.*;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 31/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraMediaPlanTemplateServiceTest {

    @Autowired
    private TraMediaPlanTemplateService traMediaPlanTemplateService;
    @Autowired
    private TraMediaPlanTemplateRepository traMediaPlanTemplateRepository;

    @Autowired
    private CorNetworkRepository networkRepository;

    private PodamFactory factory;

    private CorChannel corChannel;

    private CorOrganization corOrganization;


    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

    }

    @Test
    public void shouldSaveUser() throws Exception {
        TraMediaPlanTemplate traMediaPlanTemplate = factory.manufacturePojo(TraMediaPlanTemplate.class);
        TraMediaPlanTemplate entity = traMediaPlanTemplateService.saveMediaPlanTemplate(traMediaPlanTemplate.channel(corChannel));
        TraMediaPlanTemplate fetchedEntity = traMediaPlanTemplateService.findMediaPlanTemplate(entity.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        assertEquals(entity.getId(), fetchedEntity.getId());
        assertEquals(entity.getName(), fetchedEntity.getName());
        assertEquals(entity.getBlockStartCell(), fetchedEntity.getBlockStartCell());
        assertEquals(entity.getBlockEndCell(), fetchedEntity.getBlockEndCell());
        assertEquals(entity.getBlockHourSeparator(), fetchedEntity.getBlockHourSeparator());
        assertEquals(entity.getPlaylistDateStartColumn(), fetchedEntity.getPlaylistDateStartColumn());
        assertEquals(entity.getBlockStartColumn(), fetchedEntity.getBlockStartColumn());
        assertEquals(entity.getPlaylistDateEndColumn(), fetchedEntity.getPlaylistDateEndColumn());
        assertEquals(entity.getPlaylistFirsValueCell(), fetchedEntity.getPlaylistFirsValueCell());
        assertEquals(entity.getPlaylistDatePattern(), fetchedEntity.getPlaylistDatePattern());
        assertEquals(entity.getSheetIndexOfMediaPlan(), fetchedEntity.getSheetIndexOfMediaPlan());
        assertEquals(entity.getFirstEmissionValueCell(), fetchedEntity.getFirstEmissionValueCell());
        assertEquals(entity.getLastEmissionValueCell(), fetchedEntity.getLastEmissionValueCell());
        assertEquals(entity.getName(), fetchedEntity.getName());
        assertEquals(entity.getChannel(), fetchedEntity.getChannel());
    }

    @Test
    public void shouldFindFilter() throws Exception {
        TraMediaPlanTemplate traMediaPlanTemplate = factory.manufacturePojo(TraMediaPlanTemplate.class);

        TraMediaPlanTemplate entity = traMediaPlanTemplateRepository.saveAndFlush(traMediaPlanTemplate.channel(corChannel));
        TraMediaPlanTemplate fetchedEntity = traMediaPlanTemplateService.findMediaPlanTemplate(entity.getId(), corOrganization.getShortcut(), corChannel.getShortcut());
        assertEquals(entity.getId(), fetchedEntity.getId());
        assertEquals(entity.getName(), fetchedEntity.getName());
        assertEquals(entity.getBlockStartCell(), fetchedEntity.getBlockStartCell());
        assertEquals(entity.getBlockEndCell(), fetchedEntity.getBlockEndCell());
        assertEquals(entity.getBlockHourSeparator(), fetchedEntity.getBlockHourSeparator());
        assertEquals(entity.getPlaylistDateStartColumn(), fetchedEntity.getPlaylistDateStartColumn());
        assertEquals(entity.getBlockStartColumn(), fetchedEntity.getBlockStartColumn());
        assertEquals(entity.getPlaylistDateEndColumn(), fetchedEntity.getPlaylistDateEndColumn());
        assertEquals(entity.getPlaylistFirsValueCell(), fetchedEntity.getPlaylistFirsValueCell());
        assertEquals(entity.getPlaylistDatePattern(), fetchedEntity.getPlaylistDatePattern());
        assertEquals(entity.getSheetIndexOfMediaPlan(), fetchedEntity.getSheetIndexOfMediaPlan());
        assertEquals(entity.getFirstEmissionValueCell(), fetchedEntity.getFirstEmissionValueCell());
        assertEquals(entity.getLastEmissionValueCell(), fetchedEntity.getLastEmissionValueCell());
        assertEquals(entity.getName(), fetchedEntity.getName());
        assertEquals(entity.getChannel(), fetchedEntity.getChannel());

    }

    @Test
    public void findAllFindFilter() throws Exception {
        TraMediaPlanTemplate traMediaPlanTemplate = factory.manufacturePojo(TraMediaPlanTemplate.class);

        TraMediaPlanTemplate entity = traMediaPlanTemplateRepository.saveAndFlush(traMediaPlanTemplate.channel(corChannel));

        Slice<TraMediaPlanTemplate> fetchedEntity = traMediaPlanTemplateService.findAllMediaPlanTemplates(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        assertNotNull(fetchedEntity.getContent());
    }

    @Test
    public void delete() throws Exception {
        TraMediaPlanTemplate traMediaPlanTemplate = factory.manufacturePojo(TraMediaPlanTemplate.class);
        TraMediaPlanTemplate entity = traMediaPlanTemplateRepository.saveAndFlush(traMediaPlanTemplate.channel(corChannel));
        traMediaPlanTemplateService.deleteMediaPlanTemplate(entity.getId(), corOrganization.getShortcut(), corChannel.getShortcut());
        TraMediaPlanTemplate fetchedEntity = traMediaPlanTemplateService.findMediaPlanTemplate(entity.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        assertNull(fetchedEntity);
    }
}