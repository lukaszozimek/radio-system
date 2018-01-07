package io.protone.application.service.traffic;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.traffic.domain.TraPrice;
import io.protone.traffic.repository.TraPriceRepository;
import io.protone.traffic.service.TraPriceService;
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
import java.math.BigDecimal;

import static io.protone.application.util.TestConstans.*;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 12.08.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraPriceServiceTest {
    @Autowired
    private TraPriceService traPriceService;

    @Autowired
    private TraPriceRepository traPriceRepository;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    private CorChannel corChannel;

    private CorOrganization corOrganization;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

    }

    @Test
    public void shouldGetAllPrice() throws Exception {
        //when
        TraPrice traPrice = factory.manufacturePojo(TraPrice.class);
        traPrice.setBasePrice(new BigDecimal(1L));
        traPrice.setPriceFirstPostion(new BigDecimal(1L));
        traPrice.setPriceLastPostion(new BigDecimal(1L));

        traPrice.setChannel(corChannel);
        traPrice = traPriceRepository.save(traPrice);

        //then
        Slice<TraPrice> fetchedEntity = traPriceService.getAllPrice(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
    }

    @Test
    public void shouldSavePrice() throws Exception {
        //when
        TraPrice traPrice = factory.manufacturePojo(TraPrice.class);
        traPrice.setBasePrice(new BigDecimal(1L));
        traPrice.setPriceFirstPostion(new BigDecimal(1L));
        traPrice.setPriceLastPostion(new BigDecimal(1L));

        traPrice.setChannel(corChannel);

        //then
        TraPrice fetchedEntity = traPriceService.savePrice(traPrice);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertNotNull(traPrice.getName());
        assertEquals(traPrice.getChannel(), fetchedEntity.getChannel());
    }

    @Test
    public void shouldDeletePrice() throws Exception {
        //when
        TraPrice traPrice = factory.manufacturePojo(TraPrice.class);
        traPrice.setBasePrice(new BigDecimal(1L));
        traPrice.setPriceFirstPostion(new BigDecimal(1L));
        traPrice.setPriceLastPostion(new BigDecimal(1L));

        traPrice.setChannel(corChannel);
        traPrice = traPriceRepository.save(traPrice);
        //then
        traPriceService.deletePrice(traPrice.getId(), corOrganization.getShortcut(), corChannel.getShortcut());
        TraPrice fetchedEntity = traPriceService.getPrice(traPrice.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetPrice() throws Exception {
        //when
        TraPrice traPrice = factory.manufacturePojo(TraPrice.class);
        traPrice.setBasePrice(new BigDecimal(1L));
        traPrice.setPriceFirstPostion(new BigDecimal(1L));
        traPrice.setPriceLastPostion(new BigDecimal(1L));

        traPrice.setChannel(corChannel);
        traPrice = traPriceRepository.save(traPrice);

        //then
        TraPrice fetchedEntity = traPriceService.getPrice(traPrice.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(traPrice.getId(), fetchedEntity.getId());
        assertEquals(traPrice.getName(), fetchedEntity.getName());
        assertEquals(traPrice.getChannel(), fetchedEntity.getChannel());
    }

}