package io.protone.service.traffic;

import io.protone.config.s3.S3Client;
import io.protone.config.s3.exceptions.S3Exception;
import io.protone.config.s3.exceptions.UploadException;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraAdvertisement;
import io.protone.domain.TraPlaylist;
import io.protone.service.traffic.mediaplan.ExcelMediaPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void saveMediaPlan(ByteArrayInputStream bais, TraAdvertisement traAdvertisement, CorNetwork corNetwork, CorChannel corChannel) throws IOException, SAXException {
        String fileUUID = UUID.randomUUID().toString();
        try {
            log.debug("Uploading File to Storage: {} ", fileUUID);
            s3Client.uploadMediaPlan(fileUUID, bais, "excel");
            List<TraPlaylist> parseMediaPlanPlaylists = excelMediaPlan.parseMediaPlan(bais, traAdvertisement, corNetwork, corChannel);

        } catch (UploadException e) {
            e.printStackTrace();
        } catch (S3Exception e) {
            e.printStackTrace();
        }
    }

}
