package io.protone.custom.service;

import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.service.mapper.CustomCRMAccountMapper;
import io.protone.custom.service.mapper.CustomTRAAdvertismentMapper;
import io.protone.custom.service.mapper.CustomTRAIndustryMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TRAAdvertismentService {

    @Inject
    private CustomTRAAdvertismentMapper traAdvertismentMapper;

    @Inject
    private TRAAdvertisementRepository traAdvertisementRepository;

    @Inject
    private CustomTRAIndustryMapper customTRAIndustryMapper;

    @Inject
    private CustomCRMAccountMapper customCRMAccountMapper;

    @Inject
    private LIBMediaItemRepository libMediaItemRepository;

    @Inject
    private TRAIndustryRepository traIndustryRepository;

    @Inject
    private CRMAccountRepository crmAccountRepository;

    public List<TraAdvertisementPT> getAllAdvertisement(CORNetwork corNetwork) {
        return traAdvertisementRepository.findByNetwork(corNetwork).stream().map(traAdvertisement -> traAdvertismentMapper.transformEntityToDTO(traAdvertisement)).collect(toList());
    }

    public TraAdvertisementPT saveAdvertisement(TraAdvertisementPT traAdvertisementPT, CORNetwork corNetwork) {
        TRAAdvertisement traAdvertisement = traAdvertismentMapper.transformDTOToEntity(traAdvertisementPT, corNetwork);
        traAdvertisementRepository.save(traAdvertisement);
        return traAdvertismentMapper.transformEntityToDTO(traAdvertisement);
    }

    public void deleteAdvertisement(Long idx, CORNetwork corNetwork) {
        TRAAdvertisement traAdvertisement = traAdvertisementRepository.findOne(idx);
        libMediaItemRepository.delete(traAdvertisement.getLibitem());
        traAdvertisementRepository.delete(traAdvertisement);
    }

    public TraAdvertisementPT getAdvertisement(Long id, CORNetwork corNetwork) {
        TRAAdvertisement traAdvertisement = traAdvertisementRepository.findOne(id);
        return traAdvertismentMapper.transformEntityToDTO(traAdvertisement);
    }


    public List<TraAdvertisementPT> getCustomerAdvertisments(String shortcut, CORNetwork corNetwork) {
        return null;
    }


}
