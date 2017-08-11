package io.protone.application.service.traffic;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.traffic.domain.TraCompany;
import io.protone.traffic.repository.TraCompanyRepository;
import io.protone.traffic.service.TraCompanyService;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 11/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraCompanyServiceTest {
    @Autowired
    private TraCompanyService traCompanyService;

    @Autowired
    private TraCompanyRepository traCompanyRepository;

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
    public void shouldGetAllCompany() throws Exception {
        //when
        TraCompany traCompany = factory.manufacturePojo(TraCompany.class);
        traCompany.setNetwork(corNetwork);
        traCompany = traCompanyRepository.save(traCompany);

        //then
        List<TraCompany> fetchedEntity = traCompanyService.getAllCompany(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(traCompany.getId(), fetchedEntity.get(0).getId());
        assertEquals(traCompany.getName(), fetchedEntity.get(0).getName());
        assertEquals(traCompany.getDescription(), fetchedEntity.get(0).getDescription());
        assertEquals(traCompany.getTaxId1(), fetchedEntity.get(0).getTaxId1());
        assertEquals(traCompany.getTaxId2(), fetchedEntity.get(0).getTaxId2());
        assertEquals(traCompany.getNetwork(), fetchedEntity.get(0).getNetwork());


    }

    @Test
    public void shouldSaveCompany() throws Exception {
        //when
        TraCompany traCompany = factory.manufacturePojo(TraCompany.class);

        traCompany.setNetwork(corNetwork);

        //then
        TraCompany fetchedEntity = traCompanyService.saveCompany(traCompany);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertNotNull(traCompany.getName());
        assertNotNull(traCompany.getDescription());
        assertNotNull(traCompany.getTaxId1());
        assertNotNull(traCompany.getTaxId2());
        assertEquals(traCompany.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteCompany() throws Exception {
        //when
        TraCompany traCompany = factory.manufacturePojo(TraCompany.class);

        traCompany.setNetwork(corNetwork);
        traCompany = traCompanyRepository.save(traCompany);
        //then
        traCompanyService.deleteCompany(traCompany.getId(), corNetwork.getShortcut());
        TraCompany fetchedEntity = traCompanyService.getCompany(traCompany.getId(), corNetwork.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetCompany() throws Exception {
        //when
        TraCompany traCompany = factory.manufacturePojo(TraCompany.class);
        traCompany.setNetwork(corNetwork);
        traCompany = traCompanyRepository.save(traCompany);

        //then
        TraCompany fetchedEntity = traCompanyService.getCompany(traCompany.getId(), corNetwork.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(traCompany.getId(), fetchedEntity.getId());
        assertEquals(traCompany.getName(), fetchedEntity.getName());
        assertEquals(traCompany.getDescription(), fetchedEntity.getDescription());
        assertEquals(traCompany.getTaxId1(), fetchedEntity.getTaxId1());
        assertEquals(traCompany.getTaxId2(), fetchedEntity.getTaxId2());
        assertEquals(traCompany.getNetwork(), fetchedEntity.getNetwork());
    }


}