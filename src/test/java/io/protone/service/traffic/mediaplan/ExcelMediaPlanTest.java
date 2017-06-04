package io.protone.service.traffic.mediaplan;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraAdvertisement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static io.protone.util.TestUtil.parseInputStream;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 04/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class ExcelMediaPlanTest {

    private InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_1.xls");

    @Autowired
    private ExcelMediaPlan excelMediaPlan;

    @Test
    public void saveMediaPlan() throws Exception {
        //   ReflectionTestUtils.setField(excelMediaPlan, "s3Client", s3Client);
        // ReflectionTestUtils.setField(excelMediaPlan, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        excelMediaPlan.saveMediaPlan(byteArrayInputStream, new CrmAccount(), "", new CorNetwork(), new TraAdvertisement().name("TESTOW REKLAMA"));
    }


}
