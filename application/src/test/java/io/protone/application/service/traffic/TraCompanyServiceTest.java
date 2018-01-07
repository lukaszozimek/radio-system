package io.protone.application.service.traffic;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
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
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static io.protone.application.util.TestConstans.*;
import static org.junit.Assert.*;

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
    public void shouldGetAllCompany() throws Exception {
        //when
        TraCompany traCompany = factory.manufacturePojo(TraCompany.class);
        traCompany.setChannel(corChannel);
        traCompany = traCompanyRepository.save(traCompany);

        //then
        Slice<TraCompany> fetchedEntity = traCompanyService.getAllCompany(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());

    }

    @Test
    public void shouldSaveCompany() throws Exception {
        //when
        TraCompany traCompany = factory.manufacturePojo(TraCompany.class);

        traCompany.setChannel(corChannel);

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
        assertEquals(traCompany.getChannel(), fetchedEntity.getChannel());
    }

    @Test
    public void shouldDeleteCompany() throws Exception {
        //when
        TraCompany traCompany = factory.manufacturePojo(TraCompany.class);

        traCompany.setChannel(corChannel);
        traCompany = traCompanyRepository.save(traCompany);
        //then
        traCompanyService.deleteCompany(traCompany.getId(), corOrganization.getShortcut(), corChannel.getShortcut());
        TraCompany fetchedEntity = traCompanyService.getCompany(traCompany.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetCompany() throws Exception {
        //when
        TraCompany traCompany = factory.manufacturePojo(TraCompany.class);
        traCompany.setChannel(corChannel);
        traCompany = traCompanyRepository.save(traCompany);

        //then
        TraCompany fetchedEntity = traCompanyService.getCompany(traCompany.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(traCompany.getId(), fetchedEntity.getId());
        assertEquals(traCompany.getName(), fetchedEntity.getName());
        assertEquals(traCompany.getDescription(), fetchedEntity.getDescription());
        assertEquals(traCompany.getTaxId1(), fetchedEntity.getTaxId1());
        assertEquals(traCompany.getTaxId2(), fetchedEntity.getTaxId2());
        assertEquals(traCompany.getChannel(), fetchedEntity.getChannel());
    }


}