package io.protone.application.service.cor;

import io.jsonwebtoken.lang.Assert;
import io.protone.application.ProtoneApp;
import io.protone.core.service.CorImageItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * Created by lukaszozimek on 06/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CorImageItemServiceTest {

    @Autowired
    private CorImageItemService corImageItemService;

    @Test
    public void saveImageItems() throws Exception {
        Assert.notNull(null);
    }

    @Test
    public void saveImageItem() throws Exception {
        Assert.notNull(null);
    }

    @Test
    public void getValidLinkToResource() throws Exception {
        Assert.notNull(null);
    }
}