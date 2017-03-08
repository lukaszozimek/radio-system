package io.protone.custom.service;

import com.google.common.collect.Lists;
import io.protone.ProtoneApp;
import io.protone.custom.service.dto.LibMediaItemPT;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.service.dto.TraShuffleAdvertisementPT;
import io.protone.custom.service.mapper.CustomLibMediaItemMapper;
import io.protone.domain.LibMediaItem;
import io.protone.domain.SchBlock;
import io.protone.domain.SchEmission;
import io.protone.domain.TraAdvertisement;
import io.protone.repository.custom.CustomSchBlockRepository;
import io.protone.repository.custom.CustomSchEmissionRepository;
import io.protone.repository.custom.CustomTraAdvertisementRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class AdvertisementShuffleTest {

    @Mock
    private CustomSchBlockRepository customSchBlockRepository;

    @Mock
    private CustomSchEmissionRepository schEmissionRepository;

    @Mock
    private CustomTraAdvertisementRepository customTraAdvertisementRepository;

    @Mock
    private CustomLibMediaItemMapper customLibMediaItemMapper;

    @InjectMocks
    private AdvertisementShuffle advertisementShuffle;

    @Before
    public void initMock() {
        MockitoAnnotations.initMocks(this);
        SchBlock schBlock = new SchBlock();
        SchBlock schBlock1 = new SchBlock();
        SchEmission schEmission = new SchEmission();
        schEmission.setMediaItem(new LibMediaItem().idx("3"));

        SchEmission schEmission1 = new SchEmission();
        schEmission1.setMediaItem(new LibMediaItem().idx("2"));
        when(customTraAdvertisementRepository.findOne((long) 1)).thenReturn(new TraAdvertisement());
        when(customSchBlockRepository.findByScheduledStartTimeBetweenAndType(any(ZonedDateTime.class), any(ZonedDateTime.class), any())).thenReturn(Lists.newArrayList(schBlock, schBlock1));
        when(schEmissionRepository.findByBlock(any())).thenReturn(Lists.newArrayList(schEmission, schEmission1));
        when(customLibMediaItemMapper.lIBMediaItemPTToLibMediaItem(any())).thenReturn(new LibMediaItem());
    }

    @Test
    public void shuffleCommercials() throws Exception {
        TraShuffleAdvertisementPT tarShuffleAdvertisementPT = new TraShuffleAdvertisementPT();
        tarShuffleAdvertisementPT.setFrom(ZonedDateTime.now().minusHours(4));
        tarShuffleAdvertisementPT.setTo(ZonedDateTime.now().plusDays(4));
        tarShuffleAdvertisementPT.setNumber(2);
        tarShuffleAdvertisementPT.setTraAdvertisementPT(new TraAdvertisementPT());
        tarShuffleAdvertisementPT.getTraAdvertisementPT().setId((long) 1);
        tarShuffleAdvertisementPT.getTraAdvertisementPT().setMediaItemId(new LibMediaItemPT());
        advertisementShuffle.shuffleCommercials(tarShuffleAdvertisementPT);
    }

    @Test
    public void shuffleCommercialsLessThanAvailableBlocks() throws Exception {
        TraShuffleAdvertisementPT tarShuffleAdvertisementPT = new TraShuffleAdvertisementPT();
        tarShuffleAdvertisementPT.setFrom(ZonedDateTime.now().minusHours(4));
        tarShuffleAdvertisementPT.setTo(ZonedDateTime.now().plusDays(4));
        tarShuffleAdvertisementPT.setNumber(3);
        tarShuffleAdvertisementPT.setTraAdvertisementPT(new TraAdvertisementPT());
        tarShuffleAdvertisementPT.getTraAdvertisementPT().setId((long) 1);
        tarShuffleAdvertisementPT.getTraAdvertisementPT().setMediaItemId(new LibMediaItemPT());
        advertisementShuffle.shuffleCommercials(tarShuffleAdvertisementPT);
    }

    @Test
    public void shuffleCommercialsIfOneExistInBlock() throws Exception {
        SchEmission schEmission = new SchEmission();
        schEmission.setMediaItem(new LibMediaItem().idx("1"));

        SchEmission schEmission1 = new SchEmission();
        schEmission1.setMediaItem(new LibMediaItem().idx("2"));
        when(schEmissionRepository.findByBlock(any())).thenReturn(Lists.newArrayList(schEmission, schEmission1));
        when(customLibMediaItemMapper.lIBMediaItemPTToLibMediaItem(any())).thenReturn(new LibMediaItem().idx("1"));
        TraShuffleAdvertisementPT tarShuffleAdvertisementPT = new TraShuffleAdvertisementPT();
        tarShuffleAdvertisementPT.setFrom(ZonedDateTime.now().minusHours(4));
        tarShuffleAdvertisementPT.setTo(ZonedDateTime.now().plusDays(4));
        tarShuffleAdvertisementPT.setNumber(3);
        tarShuffleAdvertisementPT.setTraAdvertisementPT(new TraAdvertisementPT());
        tarShuffleAdvertisementPT.getTraAdvertisementPT().setId((long) 1);
        tarShuffleAdvertisementPT.getTraAdvertisementPT().setMediaItemId(new LibMediaItemPT());
        tarShuffleAdvertisementPT.getTraAdvertisementPT().getMediaItemId().setIdx("1");
        advertisementShuffle.shuffleCommercials(tarShuffleAdvertisementPT);
    }

}
