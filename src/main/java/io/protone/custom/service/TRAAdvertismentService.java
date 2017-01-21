package io.protone.custom.service;

import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.service.dto.TraCustomerAdvertismentsPT;
import io.protone.custom.service.mapper.CustomTRAAdvertismentMapper;
import io.protone.repository.CORAssociationRepository;
import io.protone.repository.TRAAdvertisementRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
public class TRAAdvertismentService {

    @Inject
    private CustomTRAAdvertismentMapper traAdvertismentMapper;

    @Inject
    private TRAAdvertisementRepository traAdvertisementRepository;

    @Inject
    private CORAssociationRepository corAssociationRepository;

    public List<TraAdvertisementPT> getAllAdvertisement() {

        return null;
    }

    public TraAdvertisementPT saveAdvertisement(TraAdvertisementPT traAdvertisementPT) {

        return null;
    }

    public void deleteAdvertisement(Long idx) {

    }

    public TraAdvertisementPT getAdvertisement(String shortcut) {
        return null;
    }

    public List<TraAdvertisementPT> getCustomerAdvertisments(String shortcut) {
        return null;
    }

}
