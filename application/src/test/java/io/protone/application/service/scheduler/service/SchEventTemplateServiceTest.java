package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorOrganization;
import io.protone.scheduler.domain.SchDiscriminators;
import io.protone.scheduler.domain.SchEventTemplate;
import io.protone.scheduler.repository.SchEventTemplateRepository;
import io.protone.scheduler.service.SchEventTemplateService;
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

import static io.protone.application.util.TestConstans.*;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@org.springframework.transaction.annotation.Transactional
public class SchEventTemplateServiceTest {
    private static final String TEST_EVENT_CATEGORY = "News";

    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchEventTemplateService schEventTemplateService;

    @Autowired
    private SchEventTemplateRepository schEventTemplateRepository;


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
    public void shouldGetEventConfigurations() throws Exception {
        //when
        SchEventTemplate schEventConfiguration = factory.manufacturePojo(SchEventTemplate.class).instance(false);
        schEventConfiguration.setChannel(corChannel);
        schEventConfiguration = schEventTemplateRepository.save(schEventConfiguration);

        //then
        Slice<SchEventTemplate> fetchedEntity = schEventTemplateService.findSchEventTemplatesForNetworkAndChannel(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schEventConfiguration.getId(), fetchedEntity.getContent().get(0).getId());

    }


    @Test
    public void shouldGetEventConfigurationsGroupedByType() throws Exception {
        //when
        SchEventTemplate schEventConfiguration = factory.manufacturePojo(SchEventTemplate.class).instance(false);
        schEventConfiguration.setChannel(corChannel);
        CorDictionary corDictionary = new CorDictionary();
        corDictionary.setId(43L);
        schEventConfiguration.setEventCategory(corDictionary);
        schEventConfiguration = schEventTemplateRepository.save(schEventConfiguration);

        //then
        Slice<SchEventTemplate> fetchedEntity = schEventTemplateService.findAllEventsByCategoryName(corOrganization.getShortcut(), corChannel.getShortcut(), TEST_EVENT_CATEGORY, new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schEventConfiguration.getId(), fetchedEntity.getContent().get(0).getId());

    }

    @Test
    public void shouldSaveEventConfiguration() throws Exception {
        //when
        SchEventTemplate schEventConfiguration = factory.manufacturePojo(SchEventTemplate.class).instance(false);
        schEventConfiguration.setChannel(corChannel);
        //then
        SchEventTemplate fetchedEntity = schEventTemplateService.saveEventConfiguration(schEventConfiguration);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
    }

    @Test
    public void shouldDeleteEventConfiguration() throws Exception {
        //when
        SchEventTemplate schEventConfiguration = factory.manufacturePojo(SchEventTemplate.class).instance(false);
        schEventConfiguration.setChannel(corChannel);
        schEventConfiguration.setChannel(corChannel);
        schEventConfiguration = schEventTemplateRepository.saveAndFlush(schEventConfiguration);
        //then
        schEventTemplateService.deleteSchEventTemplateByNetworkAndChannelAndShortName(corOrganization.getShortcut(), corChannel.getShortcut(), schEventConfiguration.getShortName());
        SchEventTemplate fetchedEntity = schEventTemplateRepository.findOneByChannel_Organization_ShortcutAndChannel_ShortcutAndShortNameAndInstanceAndType(corOrganization.getShortcut(), corChannel.getShortcut(), schEventConfiguration.getShortName(), false, SchDiscriminators.EVENT_TEMPLATE);

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldDeleteEventConfigurationWithRecusriveStrategy() throws Exception {

    }


    @Test
    public void shouldGetEventConfiguration() throws Exception {
        //when
        SchEventTemplate schEventConfiguration = factory.manufacturePojo(SchEventTemplate.class).instance(false);
        schEventConfiguration.setChannel(corChannel);
        schEventConfiguration.setChannel(corChannel);
        schEventConfiguration = schEventTemplateRepository.save(schEventConfiguration);

        //then
        SchEventTemplate fetchedEntity = schEventTemplateService.findSchEventTemplatesForNetworkAndChannelAndShortName(corOrganization.getShortcut(), corChannel.getShortcut(), schEventConfiguration.getShortName());
        //assert
        assertNotNull(fetchedEntity);
        assertEquals(schEventConfiguration.getId(), fetchedEntity.getId());

        assertEquals(schEventConfiguration.getChannel(), fetchedEntity.getChannel());

    }

}