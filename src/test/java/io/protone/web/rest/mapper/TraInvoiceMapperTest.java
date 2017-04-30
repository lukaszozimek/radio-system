
import io.protone.ProtoneApp;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.mapper.TraInvoiceMapper;
import io.protone.domain.TraInvoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 28.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraInvoiceMapperTest {
    @Autowired
    private TraInvoiceMapper customTraInvoiceMapper;

    private TraInvoice traInvoice;

    private TraInvoicePT traInvoicePT;

    private List<TraInvoicePT> traInvoicePTS = new ArrayList<>();

    private List<TraInvoice> traInvoices = new ArrayList<>();
    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traInvoice = factory.manufacturePojo(TraInvoice.class);
        traInvoices.add(traInvoice);
        traInvoicePT = factory.manufacturePojo(TraInvoicePT.class);
        traInvoicePTS.add(traInvoicePT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        TraInvoicePT dto = customTraInvoiceMapper.DB2DTO(traInvoice);

        assertNotNull(dto.getOrders());
        assertNotNull(dto.getCustomerId());
        assertNotNull(dto.getStatusId());
        assertNotNull(dto.getId());
        assertNotNull(dto.getPaid());
        assertNotNull(dto.getPrice());
        assertNotNull(dto.getPaymentDay());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraInvoicePT> dtos = customTraInvoiceMapper.DBs2DTOs(traInvoices);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getOrders());
            assertNotNull(dto.getCustomerId());
            assertNotNull(dto.getStatusId());
            assertNotNull(dto.getId());
            assertNotNull(dto.getPaid());
            assertNotNull(dto.getPrice());
            assertNotNull(dto.getPaymentDay());


        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraInvoice> entities = customTraInvoiceMapper.DTOs2DBs(traInvoicePTS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getCustomer());
            assertNotNull(entity.getOrders());
            assertNotNull(entity.getStatus());
            assertNotNull(entity.getId());
            assertNotNull(entity.isPaid());
            assertNotNull(entity.getPrice());
            assertNotNull(entity.getPaymentDay());

            assertNotNull(entity.getNetwork());

        });
    }

    @Test
    public void DTO2DB() throws Exception {
        TraInvoice entity = customTraInvoiceMapper.DTO2DB(traInvoicePT, corNetwork);


        assertNotNull(entity.getCustomer());
        assertNotNull(entity.getOrders());
        assertNotNull(entity.getStatus());
        assertNotNull(entity.getId());
        assertNotNull(entity.isPaid());
        assertNotNull(entity.getPrice());
        assertNotNull(entity.getPaymentDay());

        assertNotNull(entity.getNetwork());
    }
}
