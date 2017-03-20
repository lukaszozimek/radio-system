package io.protone.custom.service.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.ProtoneApp;
import io.protone.custom.service.dto.*;
import io.protone.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProtoneApp.class)
public class CustomTRAAdvertismentMapperTest {
    @Autowired
    private CustomTRAAdvertismentMapper customTRAAdvertismentMapper;
    private CorNetwork mockCorNetwork = null;
    private TraAdvertisement mockTraAdvertisment = null;
    private TraAdvertisementPT mockTraAdvertismentsPT = null;

  //  @Before
    public void initialize() {
        mockCorNetwork = new CorNetwork();
        mockTraAdvertisment = new TraAdvertisement();
        mockTraAdvertisment.setId((long) 1);
        mockTraAdvertisment.name("test")
            .description("test")
            .mediaItem(new LibMediaItem())
            .libitem(new LibMediaItem())
            .industry(new TraIndustry())
            .type(new TraAdvertismentType())
            .customer(new CrmAccount())
            .network(mockCorNetwork);
        mockTraAdvertismentsPT = new TraAdvertisementPT();
        mockTraAdvertismentsPT.id((long) 1)
            .description("test")
            .industryId(new ConfIndustryPT())
            .customerId(new TraCustomerPT())
            .mediaItemId(new LibItemPT())
            .name("test");
    }

 //   @Test
    public void transformDTOToEntity() throws Exception {
        //then
        TraAdvertisement result = customTRAAdvertismentMapper.transformDTOToEntity(mockTraAdvertismentsPT, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

//    @Test
    public void transformEntityToDTO() throws Exception {
        //then
        TraAdvertisementPT result = customTRAAdvertismentMapper.transformEntityToDTO(mockTraAdvertisment);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
