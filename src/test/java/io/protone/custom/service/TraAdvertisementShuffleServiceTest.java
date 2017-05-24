package io.protone.custom.service;

import io.protone.ProtoneApp;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProtoneApp.class)
//@Transactional
public class TraAdvertisementShuffleServiceTest {/*
    @Mock
    private CustomSchBlockRepository customSchBlockRepository;

    @Mock
    private CustomSchEmissionRepository schEmissionRepository;

    @Mock
    private TraAdvertisementRepository customTraAdvertisementRepository;

    @Mock
    private CustomLibMediaItemMapper customLibMediaItemMapper;

    @InjectMocks
    private TraAdvertisementShuffleService advertisementShuffle;

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
        when(customLibMediaItemMapper.DTO2DB(any())).thenReturn(new LibMediaItem());
    }

    @Test
    public void shuffleCommercials() throws Exception {
        //when
        TraShuffleAdvertisementDTO tarShuffleAdvertisementPT = new TraShuffleAdvertisementDTO();
        tarShuffleAdvertisementPT.setFrom(ZonedDateTime.now().minusHours(4));
        tarShuffleAdvertisementPT.setTo(ZonedDateTime.now().plusDays(4));
        tarShuffleAdvertisementPT.setNumber(2);
        tarShuffleAdvertisementPT.setTraAdvertisementDTO(new TraAdvertisementDTO());
        tarShuffleAdvertisementPT.getTraAdvertisementDTO().setId((long) 1);
        tarShuffleAdvertisementPT.getTraAdvertisementDTO().setMediaItemId(new LibMediaItemThinDTO());

        advertisementShuffle.shuffleCommercials(tarShuffleAdvertisementPT);
    }

    @Test
    public void shuffleCommercialsLessThanAvailableBlocks() throws Exception {
        //given
        TraShuffleAdvertisementDTO tarShuffleAdvertisementPT = new TraShuffleAdvertisementDTO();
        tarShuffleAdvertisementPT.setFrom(ZonedDateTime.now().minusHours(4));
        tarShuffleAdvertisementPT.setTo(ZonedDateTime.now().plusDays(4));
        tarShuffleAdvertisementPT.setNumber(3);
        tarShuffleAdvertisementPT.setTraAdvertisementDTO(new TraAdvertisementDTO());
        tarShuffleAdvertisementPT.getTraAdvertisementDTO().setId((long) 1);
        tarShuffleAdvertisementPT.getTraAdvertisementDTO().setMediaItemId(new LibMediaItemThinDTO());
        //then
        advertisementShuffle.shuffleCommercials(tarShuffleAdvertisementPT);
    }

    @Test
    public void shuffleCommercialsIfOneExistInBlock() throws Exception {
        ///when
        SchEmission schEmission = new SchEmission();
        schEmission.setMediaItem(new LibMediaItem().idx("1").length(2.0));
        schEmission.setBlock(new SchBlock().scheduledLength(3L));
        SchEmission schEmission1 = new SchEmission();
        schEmission1.block(new SchBlock().scheduledLength(3L));
        schEmission1.setMediaItem(new LibMediaItem().idx("2").length(2.0));
        when(schEmissionRepository.findByBlock(any())).thenReturn(Lists.newArrayList(schEmission, schEmission1));
        when(customLibMediaItemMapper.DTO2DB(any())).thenReturn(new LibMediaItem().idx("1").length(2.0));
        //given
        TraShuffleAdvertisementDTO tarShuffleAdvertisementPT = new TraShuffleAdvertisementDTO();
        tarShuffleAdvertisementPT.setFrom(ZonedDateTime.now().minusHours(4));
        tarShuffleAdvertisementPT.setTo(ZonedDateTime.now().plusDays(4));
        tarShuffleAdvertisementPT.setNumber(3);
        tarShuffleAdvertisementPT.setTraAdvertisementDTO(new TraAdvertisementDTO());
        tarShuffleAdvertisementPT.getTraAdvertisementDTO().setId((long) 1);
        tarShuffleAdvertisementPT.getTraAdvertisementDTO().setMediaItemId(new LibMediaItemThinDTO());
        tarShuffleAdvertisementPT.getTraAdvertisementDTO().getMediaItemId().setIdx("1");
        //then
        advertisementShuffle.shuffleCommercials(tarShuffleAdvertisementPT);
    }
*/
}
