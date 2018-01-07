package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorOrganization;
import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.domain.SchDiscriminators;
import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.repository.SchGridRepository;
import io.protone.scheduler.service.SchGridService;
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
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchGridServiceTest {
    private static final String GRID_TEST_CATEGORY = "Zapasowe";
    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchGridService schGridService;

    @Autowired
    private SchGridRepository schGridRepository;

    private CorChannel corChannel;

    private CorOrganization corOrganization;


    @Before
    public void setUp() throws Exception {

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        corChannel.setOrganization(corOrganization);

    }

    @Test
    public void shouldGetGrids() throws Exception {
        //when
        SchGrid schGrid = factory.manufacturePojo(SchGrid.class);
        schGrid.setChannel(corChannel);
        schGrid = schGridRepository.save(schGrid);

        //then
        Slice<SchGrid> fetchedEntity = schGridService.findSchGridsForNetworkAndChannel(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schGrid.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(schGrid.getChannel(), fetchedEntity.getContent().get(0).getChannel());

    }

    @Test
    public void shouldGetDefaultGrids() throws Exception {
        //when
        SchGrid schGrid = factory.manufacturePojo(SchGrid.class);
        schGrid.setChannel(corChannel);


        schGrid = schGridRepository.save(schGrid);

        //then
        Slice<SchGrid> fetchedEntity = schGridService.findAllDefaultGrids(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schGrid.getId(), fetchedEntity.getContent().get(0).getId());
    }

    @Test
    public void shouldGetGridGroupedByType() throws Exception {
        //when
        SchGrid schGrid = factory.manufacturePojo(SchGrid.class);
        schGrid.setChannel(corChannel);
        CorDictionary corDictionary = new CorDictionary();
        corDictionary.setId(51L);
        schGrid.setGridCategory(corDictionary);
        schGrid = schGridRepository.save(schGrid);

        //then
        Slice<SchGrid> fetchedEntity = schGridService.findAllGridsByCategoryNaem(corOrganization.getShortcut(), corChannel.getShortcut(), GRID_TEST_CATEGORY, new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schGrid.getId(), fetchedEntity.getContent().get(0).getId());
    }

    @Test
    public void shouldSaveGrid() throws Exception {
        //when
        SchGrid schGrid = factory.manufacturePojo(SchGrid.class);
        schGrid.setChannel(corChannel);
        //then
        SchGridDTO fetchedEntity = schGridService.saveGrid(schGrid);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
    }

    @Test
    public void shouldDeleteGrid() throws Exception {
        //when
        SchGrid schGrid = factory.manufacturePojo(SchGrid.class);
        schGrid.setChannel(corChannel);
        schGrid = schGridRepository.saveAndFlush(schGrid);
        //then
        schGridService.deleteSchGridByNetworkAndChannelAndShortNAme(corOrganization.getShortcut(), corChannel.getShortcut(), schGrid.getShortName());
        SchGrid fetchedEntity = schGridRepository.findOneByChannel_Organization_ShortcutAndChannel_ShortcutAndShortNameAndType(corOrganization.getShortcut(), corChannel.getShortcut(), schGrid.getShortName(), SchDiscriminators.GRID_TEMPLATE);

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetGrid() throws Exception {
        //when
        SchGrid schGrid = factory.manufacturePojo(SchGrid.class);
        schGrid.setChannel(corChannel);
        schGrid = schGridRepository.save(schGrid);

        //then
        SchGrid fetchedEntity = schGridService.findSchGridForNetworkAndChannelAndShortName(corOrganization.getShortcut(), corChannel.getShortcut(), schGrid.getShortName());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(schGrid.getId(), fetchedEntity.getId());

    }


}