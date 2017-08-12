package io.protone.application.service.traffic;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
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
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

    private CorNetwork corNetwork;


    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
    }

    @Test
    public void shouldGetAllPrice() throws Exception {
        //when
        TraPrice traPrice = factory.manufacturePojo(TraPrice.class);
        traPrice.setBasePrice(new BigDecimal(1L));
        traPrice.setPriceFirstPostion(new BigDecimal(1L));
        traPrice.setPriceLastPostion(new BigDecimal(1L));

        traPrice.setNetwork(corNetwork);
        traPrice = traPriceRepository.save(traPrice);

        //then
        List<TraPrice> fetchedEntity = traPriceService.getAllPrice(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(traPrice.getId(), fetchedEntity.get(0).getId());
        assertEquals(traPrice.getName(), fetchedEntity.get(0).getName());
        assertEquals(traPrice.getNetwork(), fetchedEntity.get(0).getNetwork());
        assertNotNull(fetchedEntity.get(0).getLenghtMultiplier());
        assertNotNull(fetchedEntity.get(0).getLenghtMultiplier().keySet());
        assertNotEquals(0, fetchedEntity.get(0).getLenghtMultiplier().keySet().size());
        assertNotNull(fetchedEntity.get(0).getLenghtMultiplier().keySet().stream().findAny().get());

    }

    @Test
    public void shouldSavePrice() throws Exception {
        //when
        TraPrice traPrice = factory.manufacturePojo(TraPrice.class);
        traPrice.setBasePrice(new BigDecimal(1L));
        traPrice.setPriceFirstPostion(new BigDecimal(1L));
        traPrice.setPriceLastPostion(new BigDecimal(1L));

        traPrice.setNetwork(corNetwork);

        //then
        TraPrice fetchedEntity = traPriceService.savePrice(traPrice);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertNotNull(traPrice.getName());
        assertEquals(traPrice.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeletePrice() throws Exception {
        //when
        TraPrice traPrice = factory.manufacturePojo(TraPrice.class);
        traPrice.setBasePrice(new BigDecimal(1L));
        traPrice.setPriceFirstPostion(new BigDecimal(1L));
        traPrice.setPriceLastPostion(new BigDecimal(1L));

        traPrice.setNetwork(corNetwork);
        traPrice = traPriceRepository.save(traPrice);
        //then
        traPriceService.deletePrice(traPrice.getId(), corNetwork.getShortcut());
        TraPrice fetchedEntity = traPriceService.getPrice(traPrice.getId(), corNetwork.getShortcut());

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

        traPrice.setNetwork(corNetwork);
        traPrice = traPriceRepository.save(traPrice);

        //then
        TraPrice fetchedEntity = traPriceService.getPrice(traPrice.getId(), corNetwork.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(traPrice.getId(), fetchedEntity.getId());
        assertEquals(traPrice.getName(), fetchedEntity.getName());
        assertEquals(traPrice.getNetwork(), fetchedEntity.getNetwork());
    }

}