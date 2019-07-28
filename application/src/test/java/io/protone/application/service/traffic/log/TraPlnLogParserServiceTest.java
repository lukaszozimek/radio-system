package io.protone.application.service.traffic.log;

import io.protone.application.ProtoneApp;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.traffic.api.dto.TraEmissionLogDescriptorDTO;
import io.protone.traffic.api.dto.thin.TraOrderThinDTO;
import io.protone.traffic.domain.TraLogEmission;
import io.protone.traffic.service.log.pln.TraPlnLogParserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 11/12/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraPlnLogParserServiceTest {

    @Autowired
    private TraPlnLogParserService traPlnLogParserService;

    @Test
    public void shouldParsePlnLogSample1() throws IOException {
        final int NUMBER_OF_EMISSIONS = 47;
        //when
        MockMultipartFile pln = new MockMultipartFile("pln", Thread.currentThread().getContextClassLoader().getResourceAsStream("traffic/pln/Sample8.pln"));
        TraEmissionLogDescriptorDTO descriptorDTO = new TraEmissionLogDescriptorDTO().libMediaItemThinDTO(new LibMediaItemThinDTO().id(1L)).order(new TraOrderThinDTO().id(1L));

        //then
        List<TraLogEmission> result = traPlnLogParserService.parse(descriptorDTO, pln);
        //assert
        assertNotNull(result);
        assertEquals(result.size(), NUMBER_OF_EMISSIONS);
    }

    @Test
    public void shouldParsePlnLogSample2() throws IOException {
        final int NUMBER_OF_EMISSIONS = 56;
        //when
        MockMultipartFile pln = new MockMultipartFile("pln", Thread.currentThread().getContextClassLoader().getResourceAsStream("traffic/pln/Sample9.pln"));
        TraEmissionLogDescriptorDTO descriptorDTO = new TraEmissionLogDescriptorDTO().libMediaItemThinDTO(new LibMediaItemThinDTO().id(1L)).order(new TraOrderThinDTO().id(1L));

        //then
        List<TraLogEmission> result = traPlnLogParserService.parse(descriptorDTO, pln);

        //assert
        assertNotNull(result);
        assertEquals(result.size(), NUMBER_OF_EMISSIONS);

    }

    @Test
    public void shouldParsePlnLogSample3() throws IOException {
        final int NUMBER_OF_EMISSIONS = 20;
        //when
        MockMultipartFile pln = new MockMultipartFile("pln", Thread.currentThread().getContextClassLoader().getResourceAsStream("traffic/pln/Sample.pln"));
        TraEmissionLogDescriptorDTO descriptorDTO = new TraEmissionLogDescriptorDTO().libMediaItemThinDTO(new LibMediaItemThinDTO().id(1L)).order(new TraOrderThinDTO().id(1L));
        //then
        List<TraLogEmission> result = traPlnLogParserService.parse(descriptorDTO, pln);

        //assert
        assertNotNull(result);
        assertEquals(result.size(), NUMBER_OF_EMISSIONS);
    }

    @Test
    public void shouldParsePlnLogSample4() throws IOException {
        final int NUMBER_OF_EMISSIONS = 20;
        //when
        MockMultipartFile pln = new MockMultipartFile("pln", Thread.currentThread().getContextClassLoader().getResourceAsStream("traffic/pln/Sample1.pln"));
        TraEmissionLogDescriptorDTO descriptorDTO = new TraEmissionLogDescriptorDTO().libMediaItemThinDTO(new LibMediaItemThinDTO().id(1L)).order(new TraOrderThinDTO().id(1L));


        //then
        List<TraLogEmission> result = traPlnLogParserService.parse(descriptorDTO, pln);

        //assert
        assertNotNull(result);
        assertEquals(result.size(), NUMBER_OF_EMISSIONS);
    }

    @Test
    public void shouldParsePlnLogSample5() throws IOException {
        final int NUMBER_OF_EMISSIONS = 20;
        //when
        MockMultipartFile pln = new MockMultipartFile("pln", Thread.currentThread().getContextClassLoader().getResourceAsStream("traffic/pln/Sample2.pln"));
        TraEmissionLogDescriptorDTO descriptorDTO = new TraEmissionLogDescriptorDTO().libMediaItemThinDTO(new LibMediaItemThinDTO().id(1L)).order(new TraOrderThinDTO().id(1L));


        //then
        List<TraLogEmission> result = traPlnLogParserService.parse(descriptorDTO, pln);
        //assert
        assertNotNull(result);
        assertEquals(result.size(), NUMBER_OF_EMISSIONS);
    }

    @Test
    public void shouldParsePlnLogSample6() throws IOException {
        final int NUMBER_OF_EMISSIONS = 38;
        //when
        MockMultipartFile pln = new MockMultipartFile("pln", Thread.currentThread().getContextClassLoader().getResourceAsStream("traffic/pln/Sample3.pln"));
        TraEmissionLogDescriptorDTO descriptorDTO = new TraEmissionLogDescriptorDTO().libMediaItemThinDTO(new LibMediaItemThinDTO().id(1L)).order(new TraOrderThinDTO().id(1L));


        //then
        List<TraLogEmission> result = traPlnLogParserService.parse(descriptorDTO, pln);

        //assert
        assertNotNull(result);
        assertEquals(result.size(), NUMBER_OF_EMISSIONS);
    }

    @Test
    public void shouldParsePlnLogSample7() throws IOException {
        final int NUMBER_OF_EMISSIONS = 109;
        //when
        MockMultipartFile pln = new MockMultipartFile("pln", Thread.currentThread().getContextClassLoader().getResourceAsStream("traffic/pln/Sample4.pln"));
        TraEmissionLogDescriptorDTO descriptorDTO = new TraEmissionLogDescriptorDTO().libMediaItemThinDTO(new LibMediaItemThinDTO().id(1L)).order(new TraOrderThinDTO().id(1L));


        //then
        List<TraLogEmission> result = traPlnLogParserService.parse(descriptorDTO, pln);

        //assert
        assertNotNull(result);
        assertEquals(result.size(), NUMBER_OF_EMISSIONS);
    }

    @Test
    public void shouldParsePlnLogSample8() throws IOException {
        final int NUMBER_OF_EMISSIONS = 38;
        //when
        MockMultipartFile pln = new MockMultipartFile("pln", Thread.currentThread().getContextClassLoader().getResourceAsStream("traffic/pln/Sample5.pln"));
        TraEmissionLogDescriptorDTO descriptorDTO = new TraEmissionLogDescriptorDTO().libMediaItemThinDTO(new LibMediaItemThinDTO().id(1L)).order(new TraOrderThinDTO().id(1L));

        //then
        List<TraLogEmission> result = traPlnLogParserService.parse(descriptorDTO, pln);

        //assert
        assertNotNull(result);
        assertEquals(result.size(), NUMBER_OF_EMISSIONS);
    }

    @Test
    public void shouldParsePlnLogSample9() throws IOException {
        final int NUMBER_OF_EMISSIONS = 109;
        //when
        MockMultipartFile pln = new MockMultipartFile("pln", Thread.currentThread().getContextClassLoader().getResourceAsStream("traffic/pln/Sample6.pln"));
        TraEmissionLogDescriptorDTO descriptorDTO = new TraEmissionLogDescriptorDTO().libMediaItemThinDTO(new LibMediaItemThinDTO().id(1L)).order(new TraOrderThinDTO().id(1L));

        //then
        List<TraLogEmission> result = traPlnLogParserService.parse(descriptorDTO, pln);

        //assert
        assertNotNull(result);
        assertEquals(result.size(), NUMBER_OF_EMISSIONS);
    }

    @Test
    public void shouldParsePlnLogSample10() throws IOException {
        final int NUMBER_OF_EMISSIONS = 47;
        //when
        MockMultipartFile pln = new MockMultipartFile("pln", Thread.currentThread().getContextClassLoader().getResourceAsStream("traffic/pln/Sample7.pln"));
        TraEmissionLogDescriptorDTO descriptorDTO = new TraEmissionLogDescriptorDTO().libMediaItemThinDTO(new LibMediaItemThinDTO().id(1L)).order(new TraOrderThinDTO().id(1L));

        //then
        List<TraLogEmission> result = traPlnLogParserService.parse(descriptorDTO, pln);

        //assert
        assertNotNull(result);
        assertEquals(result.size(), NUMBER_OF_EMISSIONS);

    }
}
