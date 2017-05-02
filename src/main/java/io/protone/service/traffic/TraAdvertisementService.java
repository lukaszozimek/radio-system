package io.protone.service.traffic;

import io.protone.custom.service.LibItemService;
import io.protone.repository.traffic.TraAdvertisementRepository;
import io.protone.web.rest.mapper.CrmAccountMapper;
import io.protone.domain.TraAdvertisement;
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
    private CrmAccountMapper customCrmAccountMapper;

    @Inject
    private LibItemService libItemService;


    public List<TraAdvertisement> getAllAdvertisement(String corNetwork, Pageable pageable) {
        return traAdvertisementRepository.findAllByNetwork_Shortcut(corNetwork, pageable);
    }

    public TraAdvertisement saveAdvertisement(TraAdvertisement traAdvertisement) {
        log.debug("Persisting TraAdvertisement: {}", traAdvertisement);
        return traAdvertisementRepository.saveAndFlush(traAdvertisement);
    }

    public void deleteAdvertisement(Long id, String corNetwork) {
        TraAdvertisement traAdvertisement = traAdvertisementRepository.findByIdAndNetwork_Shortcut(id, corNetwork);
        if (traAdvertisement.getMediaItem() != null) {
            libItemService.deleteItem(traAdvertisement.getMediaItem());
        }
        traAdvertisementRepository.delete(traAdvertisement);
    }

    public TraAdvertisement getAdvertisement(Long id, String corNetwork) {
        return traAdvertisementRepository.findByIdAndNetwork_Shortcut(id, corNetwork);
    }


    public List<TraAdvertisement> getCustomerAdvertisements(String shortcut, String corNetwork, Pageable pageable) {
        return traAdvertisementRepository.findByCustomer_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }


}