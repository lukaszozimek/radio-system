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
public class SchPlaylistServiceTest {
    @Inject
    private SchPlaylistService playlistService;

    @Test
    public void getPlaylist() throws Exception {

    }

    @Test
    public void setPlaylist() throws Exception {

    }

    @Test
    public void getPlaylists() throws Exception {

    }

    @Test
    public void getPlaylists1() throws Exception {

    }

    @Test
    public void deletePlaylist() throws Exception {

    }

    @Test
    public void createOrUpdatePlaylist() throws Exception {

    }

    @Test
    public void getPlaylist1() throws Exception {

    }

    @Test
    public void deletePlaylist1() throws Exception {

    }

    @Test
    public void createOrUpdatePlaylist1() throws Exception {

    }

}
