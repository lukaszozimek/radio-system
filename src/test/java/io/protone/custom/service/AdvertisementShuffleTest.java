package io.protone.custom.service;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.SchBlockPT;
import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.custom.service.mapper.CustomLibMediaItemMapper;
import io.protone.domain.enumeration.SchBlockTypeEnum;
import io.protone.domain.enumeration.SchStartTypeEnum;
import io.protone.repository.custom.CustomSchBlockRepository;
import io.protone.repository.custom.CustomSchEmissionRepository;
import io.protone.repository.custom.CustomTraAdvertisementRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class AdvertisementShuffleTest {

    @Inject
    private CustomSchBlockRepository customSchBlockRepository;

    @Inject
    private CustomSchEmissionRepository schEmissionRepository;

    @Inject
    private CustomTraAdvertisementRepository customTraAdvertisementRepository;
    @Inject
    private CustomLibMediaItemMapper customLibMediaItemMapper;

    @Inject
    private AdvertisementShuffle advertisementShuffle;

    @Before
    public void initMock() {
        List<SchBlockPT> results = sampleDay();

    }

    @Test
    public void schuffleCommercials() throws Exception {

    }

    public List<SchBlockPT> sampleDay() {
        List<SchBlockPT> results = new ArrayList<>();
        for (int h = 0; h < 24; h++)
            results.add(sampleHour(true));
        return results;
    }

    private SchBlockPT sampleHour(boolean full) {

        SchBlockPT result = new SchBlockPT();
        result.setType(SchBlockTypeEnum.BT_HOUR);
        if (full)
            for (int i = 0; i < 4; i++) {
                result.addBlock(sampleCommercial(3));
                result.addBlock(sampleMusic(4));
            }
        else {
            result.addBlock(sampleCommercial(3));
            result.addBlock(sampleMusic(3));
        }

        return result;
    }

    private SchBlockPT sampleCommercial(int numOfElements) {
        SchBlockPT result = new SchBlockPT()
            .type(SchBlockTypeEnum.BT_COMMERCIAL)
            .name("SAMPLE_" + SchBlockTypeEnum.BT_COMMERCIAL)
            .startType(SchStartTypeEnum.ST_ABSOLUTE)
            .relativeDelay(0L);
        //intro:
        result.addEmission(sampleEmission(1500L));
        //commercials:
        for (int c = 0; c < numOfElements; c++)
            result.addEmission(sampleEmission(30000L));
        //outro:
        result.addEmission(sampleEmission(1500L));
        return result;
    }

    private SchBlockPT sampleMusic(int numOfElements) {
        SchBlockPT result = new SchBlockPT()
            .type(SchBlockTypeEnum.BT_PROGRAM)
            .name("SAMPLE_" + SchBlockTypeEnum.BT_PROGRAM)
            .startType(SchStartTypeEnum.ST_ABSOLUTE)
            .relativeDelay(0L);
        for (int i = 0; i < numOfElements; i++)
            result.addEmission(sampleEmission(240000L));
        return result;
    }

    private SchEmissionPT sampleEmission(Long length) {
        SchEmissionPT emission = new SchEmissionPT();
        emission.length(length);
        return emission;
    }
}
