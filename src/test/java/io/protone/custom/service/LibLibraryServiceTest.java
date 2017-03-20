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
public class LibLibraryServiceTest {
    @Inject
    private LibLibraryService libLibraryService;

    @Test
    public void findLibrary() throws Exception {

    }

    @Test
    public void findLibrary1() throws Exception {

    }

    @Test
    public void deleteLibrary() throws Exception {

    }

    @Test
    public void createOrUpdateLibrary() throws Exception {

    }

}
