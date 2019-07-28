package io.protone.application.service.traffic;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
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
import java.util.List;

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

    private CorNetwork corNetwork;


    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = networkRepository.saveAndFlush(corNetwork);
    }

    @Test
    public void shouldSaveUser() throws Exception {
        TraMediaPlanTemplate traMediaPlanTemplate = factory.manufacturePojo(TraMediaPlanTemplate.class);
        TraMediaPlanTemplate entity = traMediaPlanTemplateService.saveMediaPlanTemplate(traMediaPlanTemplate.network(corNetwork));
        TraMediaPlanTemplate fetchedEntity = traMediaPlanTemplateService.findMediaPlanTemplate(entity.getId(), corNetwork.getShortcut());

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
        assertEquals(entity.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldFindFilter() throws Exception {
        TraMediaPlanTemplate traMediaPlanTemplate = factory.manufacturePojo(TraMediaPlanTemplate.class);

        TraMediaPlanTemplate entity = traMediaPlanTemplateRepository.saveAndFlush(traMediaPlanTemplate.network(corNetwork));
        TraMediaPlanTemplate fetchedEntity = traMediaPlanTemplateService.findMediaPlanTemplate(entity.getId(), corNetwork.getShortcut());
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
        assertEquals(entity.getNetwork(), fetchedEntity.getNetwork());

    }

    @Test
    public void findAllFindFilter() throws Exception {
        TraMediaPlanTemplate traMediaPlanTemplate = factory.manufacturePojo(TraMediaPlanTemplate.class);

        TraMediaPlanTemplate entity = traMediaPlanTemplateRepository.saveAndFlush(traMediaPlanTemplate.network(corNetwork));

        Slice<TraMediaPlanTemplate> fetchedEntity = traMediaPlanTemplateService.findAllMediaPlanTemplates(corNetwork.getShortcut(), new PageRequest(0, 10));

        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(entity.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(entity.getName(), fetchedEntity.getContent().get(0).getName());
        assertEquals(entity.getBlockStartCell(), fetchedEntity.getContent().get(0).getBlockStartCell());
        assertEquals(entity.getBlockEndCell(), fetchedEntity.getContent().get(0).getBlockEndCell());
        assertEquals(entity.getBlockHourSeparator(), fetchedEntity.getContent().get(0).getBlockHourSeparator());
        assertEquals(entity.getPlaylistDateStartColumn(), fetchedEntity.getContent().get(0).getPlaylistDateStartColumn());
        assertEquals(entity.getBlockStartColumn(), fetchedEntity.getContent().get(0).getBlockStartColumn());
        assertEquals(entity.getPlaylistDateEndColumn(), fetchedEntity.getContent().get(0).getPlaylistDateEndColumn());
        assertEquals(entity.getPlaylistFirsValueCell(), fetchedEntity.getContent().get(0).getPlaylistFirsValueCell());
        assertEquals(entity.getPlaylistDatePattern(), fetchedEntity.getContent().get(0).getPlaylistDatePattern());
        assertEquals(entity.getSheetIndexOfMediaPlan(), fetchedEntity.getContent().get(0).getSheetIndexOfMediaPlan());
        assertEquals(entity.getFirstEmissionValueCell(), fetchedEntity.getContent().get(0).getFirstEmissionValueCell());
        assertEquals(entity.getLastEmissionValueCell(), fetchedEntity.getContent().get(0).getLastEmissionValueCell());
        assertEquals(entity.getName(), fetchedEntity.getContent().get(0).getName());
        assertEquals(entity.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void delete() throws Exception {
        TraMediaPlanTemplate traMediaPlanTemplate = factory.manufacturePojo(TraMediaPlanTemplate.class);
        TraMediaPlanTemplate entity = traMediaPlanTemplateRepository.saveAndFlush(traMediaPlanTemplate.network(corNetwork));
        traMediaPlanTemplateService.deleteMediaPlanTemplate(entity.getId(), corNetwork.getShortcut());
        TraMediaPlanTemplate fetchedEntity = traMediaPlanTemplateService.findMediaPlanTemplate(entity.getId(), corNetwork.getShortcut());

        assertNull(fetchedEntity);
    }
}