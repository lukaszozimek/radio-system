package io.protone.service.traffic;

import io.protone.domain.*;
import io.protone.repository.traffic.TraMediaPlanRepository;
import io.protone.service.library.LibItemService;
import io.protone.service.traffic.mediaplan.TraExcelMediaXlsPlan;
import io.protone.service.traffic.mediaplan.descriptor.TraMediaPlanDescriptor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.persistence.EntityNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
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
    private TraExcelMediaXlsPlan traExcelMediaXlsPlan;

    @Autowired
    private TraMediaPlanPlaylistService traPlaylistService;

    @Autowired
    private LibItemService libItemService;

    @Transactional
    public TraMediaPlan saveMediaPlan(MultipartFile multipartFile, TraMediaPlanDescriptor traMediaPlanDescriptor, CorNetwork corNetwork, CorChannel corChannel) throws IOException, SAXException, TikaException, InvalidFormatException {
        TraMediaPlan mediaPlan = new TraMediaPlan();
        ByteArrayInputStream bais = new ByteArrayInputStream(multipartFile.getBytes());
        LibMediaItem libMediaItem = libItemService.upload(corNetwork.getShortcut(), MEDIA_PLAN_LIBRARY_SHORTCUT, multipartFile);
        Set<TraMediaPlanPlaylist> parseMediaPlanPlaylists = traExcelMediaXlsPlan.parseMediaPlan(bais, traMediaPlanDescriptor, corNetwork, corChannel);
        mediaPlan.mediaItem(libMediaItem).network(corNetwork).channel(corChannel).account(traMediaPlanDescriptor.getTraAdvertisement().getCustomer()).name(multipartFile.getOriginalFilename()).playlists(parseMediaPlanPlaylists);
        mediaPlan = traMediaPlanRepository.saveAndFlush(mediaPlan);
        TraMediaPlan finalMediaPlan = mediaPlan;
        parseMediaPlanPlaylists = traPlaylistService.savePlaylist(parseMediaPlanPlaylists.stream().map(traMediaPlanPlaylist -> traMediaPlanPlaylist.mediaPlan(finalMediaPlan)).collect(Collectors.toSet()));
        return traMediaPlanRepository.saveAndFlush(mediaPlan.playlists(parseMediaPlanPlaylists));

    }

    @Transactional
    public TraMediaPlan updateMediaPlan(TraMediaPlan traMediaPlan) {
        traMediaPlan.setPlaylists(traPlaylistService.savePlaylist(traMediaPlan.getPlaylists()));
        return traMediaPlanRepository.saveAndFlush(traMediaPlan);
    }

    @Transactional
    public List<TraMediaPlan> getMediaPlans(String corNetwork, String corChannel, Pageable pageable) {
        return traMediaPlanRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(corNetwork, corChannel, pageable);
    }

    @Transactional
    public void deleteMediaPlan(Long id, String corNetwork, String corChannel) {
        TraMediaPlan traMediaPlan = traMediaPlanRepository.findByIdAndNetwork_ShortcutAndChannel_Shortcut(id, corNetwork, corChannel);
        if (traMediaPlan == null) {
            throw new EntityNotFoundException();
        }
        libItemService.deleteItem(traMediaPlan.getMediaItem());
        traPlaylistService.deletePlaylist(traMediaPlan.getPlaylists());
        traMediaPlanRepository.delete(traMediaPlan);

    }

    @Transactional
    public TraMediaPlan getMediaPlan(Long id, String corNetwork, String corChannel) {
        return traMediaPlanRepository.findByIdAndNetwork_ShortcutAndChannel_Shortcut(id, corNetwork, corChannel);
    }

    @Transactional
    public List<TraMediaPlan> getCustomerMediaPlan(String customerShortcut, String corNetwork, String corChannel, Pageable pageable) {
        return traMediaPlanRepository.findAllByAccount_ShortNameAndNetwork_ShortcutAndChannel_Shortcut(customerShortcut, corNetwork, corChannel, pageable);
    }


}
