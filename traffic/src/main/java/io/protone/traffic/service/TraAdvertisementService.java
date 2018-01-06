package io.protone.traffic.service;


import io.protone.crm.domain.CrmAccount;
import io.protone.library.service.LibMediaItemService;
import io.protone.traffic.domain.TraAdvertisement;
import io.protone.traffic.repository.TraAdvertisementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TraAdvertisementService {

    private final Logger log = LoggerFactory.getLogger(TraAdvertisementService.class);

    @Inject
    private TraAdvertisementRepository traAdvertisementRepository;

    @Inject
    private LibMediaItemService libMediaItemService;


    public Slice<TraAdvertisement> getAllAdvertisement(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return traAdvertisementRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pageable);
    }

    public TraAdvertisement saveAdvertisement(TraAdvertisement traAdvertisement) {
        log.debug("Persisting TraAdvertisement: {}", traAdvertisement);
        TraAdvertisement traAdvertisement1 = traAdvertisementRepository.saveAndFlush(traAdvertisement);
        return traAdvertisementRepository.findByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(traAdvertisement1.getId(), traAdvertisement1.getChannel().getOrganization().getShortcut(), traAdvertisement.getChannel().getShortcut());
    }

    public void deleteAdvertisement(Long id, String organizationShortcut, String channelShortcut) {
        TraAdvertisement traAdvertisement = traAdvertisementRepository.findByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
        traAdvertisementRepository.delete(traAdvertisement);
        if (traAdvertisement.getMediaItem() != null) {
            traAdvertisement.getMediaItem().stream().forEach(libMediaItem -> {

                libMediaItemService.deleteItem(libMediaItem);
            });
        }
    }

    public void deleteCustomerAdvertisement(CrmAccount customer, String organizationShortcut, String channelShortcut) {
        List<TraAdvertisement> traAdvertisements = traAdvertisementRepository.findByCustomer_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(customer.getShortName(), organizationShortcut, channelShortcut);
        if (traAdvertisements != null) {
            traAdvertisements.stream().forEach(traAdvertisement -> {
                traAdvertisementRepository.delete(traAdvertisement);
                if (traAdvertisement.getMediaItem() != null) {
                    traAdvertisement.getMediaItem().stream().forEach(libMediaItem -> {

                        libMediaItemService.deleteItem(libMediaItem);
                    });
                }
            });
        }
    }

    public TraAdvertisement getAdvertisement(Long id, String organizationShortcut, String channelShortcut) {
        return traAdvertisementRepository.findByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
    }


    public List<TraAdvertisement> getCustomerAdvertisements(String shortcut, String organizationShortcut, String channelShortcut, Pageable pageable) {
        return traAdvertisementRepository.findByCustomer_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut, pageable);
    }


}
