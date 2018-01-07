package io.protone.application.service.traffic.log;

import io.protone.application.ProtoneApp;
import io.protone.application.service.traffic.base.TraPlaylistBasedTest;
import io.protone.traffic.api.dto.TraEmissionLogDescriptorDTO;
import io.protone.traffic.service.log.TraLogDiff;
import io.protone.traffic.service.log.pln.TraPlnLogService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 11/12/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraPlnLogServiceTest extends TraPlaylistBasedTest {

    @Inject
    private TraPlnLogService traPlnLogParserService;

    @Before
    public void setup() throws InterruptedException {
        this.traEmissionRepository.deleteAllInBatch();
        this.traEmissionRepository.flush();
        this.trablockRepository.deleteAllInBatch();
        this.trablockRepository.flush();
        this.traPlaylistRepository.deleteAllInBatch();
        this.traPlaylistRepository.flush();
        this.trablockConfigurationRepository.deleteAllInBatch();
        this.trablockConfigurationRepository.flush();

        MockitoAnnotations.initMocks(this);
        buildMustHavePojos();
        traPlaylists = new ArrayList<>();
        mediaItemList = buildMediaItems();
        advertisements = buildAdvertisments();
        buildBlockConfiguration();

    }

    @Test
    public void shouldTransformPLNSample1() throws IOException {
        //when
        MockMultipartFile pln = new MockMultipartFile("pln", Thread.currentThread().getContextClassLoader().getResourceAsStream("traffic/pln/Sample8.pln"));
        TraEmissionLogDescriptorDTO descriptorDTO = new TraEmissionLogDescriptorDTO().libMediaItemThinDTO(libMediaItemToShuffleThinDTO).order(traOrderThinDTO);

        //then
        TraLogDiff traLogDiff = traPlnLogParserService.transform(descriptorDTO, pln, corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(traLogDiff);
    }


    @Test
    public void shouldTransformPLNSample2() throws IOException {
        //when
        MockMultipartFile pln = new MockMultipartFile("pln", Thread.currentThread().getContextClassLoader().getResourceAsStream("traffic/pln/Sample9.pln"));
        TraEmissionLogDescriptorDTO descriptorDTO = new TraEmissionLogDescriptorDTO().libMediaItemThinDTO(libMediaItemToShuffleThinDTO).order(traOrderThinDTO);

        //then
        TraLogDiff traLogDiff = traPlnLogParserService.transform(descriptorDTO, pln, corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(traLogDiff);
    }

    @Test
    public void shouldTransformPLNSample3() throws IOException {
        //when
        MockMultipartFile pln = new MockMultipartFile("pln", Thread.currentThread().getContextClassLoader().getResourceAsStream("traffic/pln/Sample.pln"));
        TraEmissionLogDescriptorDTO descriptorDTO = new TraEmissionLogDescriptorDTO().libMediaItemThinDTO(libMediaItemToShuffleThinDTO).order(traOrderThinDTO);

        //then
        TraLogDiff traLogDiff = traPlnLogParserService.transform(descriptorDTO, pln, corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(traLogDiff);
    }

    @Test
    public void shouldTransformPLNSample4() throws IOException {
        //when
        MockMultipartFile pln = new MockMultipartFile("pln", Thread.currentThread().getContextClassLoader().getResourceAsStream("traffic/pln/Sample1.pln"));
        TraEmissionLogDescriptorDTO descriptorDTO = new TraEmissionLogDescriptorDTO().libMediaItemThinDTO(libMediaItemToShuffleThinDTO).order(traOrderThinDTO);

        //then
        TraLogDiff traLogDiff = traPlnLogParserService.transform(descriptorDTO, pln, corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(traLogDiff);
    }

}
