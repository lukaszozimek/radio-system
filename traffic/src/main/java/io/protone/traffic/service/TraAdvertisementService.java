package io.protone.traffic.service;


import io.protone.crm.domain.CrmAccount;
import io.protone.library.service.LibItemService;
import io.protone.traffic.domain.TraAdvertisement;
import io.protone.traffic.repository.TraAdvertisementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
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
    private LibItemService libItemService;


    public List<TraAdvertisement> getAllAdvertisement(String corNetwork, Pageable pageable) {
        return traAdvertisementRepository.findAllByNetwork_Shortcut(corNetwork, pageable);
    }

    public TraAdvertisement saveAdvertisement(TraAdvertisement traAdvertisement) {
        log.debug("Persisting TraAdvertisement: {}", traAdvertisement);
        TraAdvertisement traAdvertisement1 = traAdvertisementRepository.saveAndFlush(traAdvertisement);
        return traAdvertisementRepository.findByIdAndNetwork_Shortcut(traAdvertisement1.getId(), traAdvertisement1.getNetwork().getShortcut());
    }

    public void deleteAdvertisement(Long id, String corNetwork) {
        TraAdvertisement traAdvertisement = traAdvertisementRepository.findByIdAndNetwork_Shortcut(id, corNetwork);
        traAdvertisementRepository.delete(traAdvertisement);
        if (traAdvertisement.getMediaItem() != null) {
            traAdvertisement.getMediaItem().stream().forEach(libMediaItem -> {

                libItemService.deleteItem(libMediaItem);
            });
        }
    }

    public void deleteCustomerAdvertisement(CrmAccount customer, String corNetwork) {
        List<TraAdvertisement> traAdvertisements = traAdvertisementRepository.findByCustomer_ShortNameAndNetwork_Shortcut(customer.getShortName(), corNetwork);
        if (traAdvertisements != null) {
            traAdvertisements.stream().forEach(traAdvertisement -> {
                traAdvertisementRepository.delete(traAdvertisement);
                if (traAdvertisement.getMediaItem() != null) {
                    traAdvertisement.getMediaItem().stream().forEach(libMediaItem -> {

                        libItemService.deleteItem(libMediaItem);
                    });
                }
            });
        }
    }

    public TraAdvertisement getAdvertisement(Long id, String corNetwork) {
        return traAdvertisementRepository.findByIdAndNetwork_Shortcut(id, corNetwork);
    }


    public List<TraAdvertisement> getCustomerAdvertisements(String shortcut, String corNetwork, Pageable pageable) {
        return traAdvertisementRepository.findByCustomer_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }


}
