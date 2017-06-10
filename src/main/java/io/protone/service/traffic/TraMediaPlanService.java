package io.protone.service.traffic;

import io.protone.config.s3.S3Client;
import io.protone.domain.*;
import io.protone.repository.traffic.TraMediaPlanPlaylistRepository;
import io.protone.repository.traffic.TraMediaPlanRepository;
import io.protone.service.library.LibItemService;
import io.protone.service.traffic.mediaplan.ExcelMediaPlan;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 08/06/2017.
 */

@Service
public class TraMediaPlanService {
    private final Logger log = LoggerFactory.getLogger(TraMediaPlanService.class);

    private static final String MEDIA_PLAN_LIBRARY_SHORTCUT = "mpl";

    @Autowired
    private TraMediaPlanRepository traMediaPlanRepository;
    @Autowired
    private TraMediaPlanPlaylistRepository traMediaPlanPlaylistRepository;

    @Autowired
    private ExcelMediaPlan excelMediaPlan;

    @Autowired
    private S3Client s3Client;

    @Autowired
    private TraPlaylistService traPlaylistService;

    @Autowired
    private LibItemService libItemService;

    @Transactional
    public void saveMediaPlan(MultipartFile multipartFile, TraAdvertisement traAdvertisement, CorNetwork corNetwork, CorChannel corChannel) throws IOException, SAXException, TikaException {
        TraMediaPlan mediaPlan = new TraMediaPlan();
        String fileUUID = UUID.randomUUID().toString();
        ByteArrayInputStream bais = new ByteArrayInputStream(multipartFile.getBytes());
        Set<TraMediaPlanPlaylist> parseMediaPlanPlaylists = excelMediaPlan.parseMediaPlan(bais, traAdvertisement, corNetwork, corChannel);
        LibMediaItem libMediaItem = libItemService.upload(corNetwork.getShortcut(), MEDIA_PLAN_LIBRARY_SHORTCUT, multipartFile);
        parseMediaPlanPlaylists = traMediaPlanPlaylistRepository.save(parseMediaPlanPlaylists).stream().collect(Collectors.toSet());
        mediaPlan.mediaItem(libMediaItem).network(corNetwork).channel(corChannel).account(traAdvertisement.getCustomer()).name(multipartFile.getOriginalFilename()).playlists(parseMediaPlanPlaylists);
        mediaPlan = traMediaPlanRepository.saveAndFlush(mediaPlan);

    }

}
