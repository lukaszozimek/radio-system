package io.protone.custom.service;

import io.protone.ProtoneApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchTemplateServiceTest {
    @Inject
    private SchTemplateService templateService;

    @Test
    public void getTemplate() throws Exception {

    }

    @Test
    public void getTemplates() throws Exception {

    }

    @Test
    public void getTemplates1() throws Exception {

    }

    @Test
    public void deleteTemplate() throws Exception {

    }

    @Test
    public void createOrUpdateTemplate() throws Exception {

    }

    @Test
    public void createOrUpdateTemplate1() throws Exception {

    }

    @Test
    public void deleteTemplate1() throws Exception {

    }

}
