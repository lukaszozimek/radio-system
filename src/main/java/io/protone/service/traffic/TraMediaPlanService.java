package io.protone.service.traffic;

import io.protone.config.s3.S3Client;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraAdvertisement;
import io.protone.domain.TraPlaylist;
import io.protone.service.library.LibItemService;
import io.protone.service.traffic.mediaplan.ExcelMediaPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by lukaszozimek on 08/06/2017.
 */

@Service
public class TraMediaPlanService {
    private final Logger log = LoggerFactory.getLogger(TraMediaPlanService.class);

    @Autowired
    private ExcelMediaPlan excelMediaPlan;

    @Autowired
    private S3Client s3Client;

    @Autowired
    private TraPlaylistService traPlaylistService;

    @Autowired
    private LibItemService libItemService;

    public void saveMediaPlan(MultipartFile[] multipartFiles, TraAdvertisement traAdvertisement, CorNetwork corNetwork, CorChannel corChannel) throws IOException, SAXException {
        String fileUUID = UUID.randomUUID().toString();
        for (MultipartFile multipartFile : multipartFiles) {
            ByteArrayInputStream bais = new ByteArrayInputStream(multipartFile.getBytes());
            List<TraPlaylist> parseMediaPlanPlaylists = excelMediaPlan.parseMediaPlan(bais, traAdvertisement, corNetwork, corChannel);
           // libItemService.upload(multipartFile, corNetwork.getShortcut(),);
        }

    }

}
