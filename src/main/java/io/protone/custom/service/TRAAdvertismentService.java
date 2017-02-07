package io.protone.custom.service;

import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.service.mapper.CustomCrmAccountMapper;
import io.protone.custom.service.mapper.CustomTRAAdvertismentMapper;
import io.protone.custom.service.mapper.CustomTraIndustryMapper;
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
    private TraAdvertisementRepository traAdvertisementRepository;

    @Inject
    private CustomTraIndustryMapper customTraIndustryMapper;

    @Inject
    private CustomCrmAccountMapper customCrmAccountMapper;

    @Inject
    private LibMediaItemRepository libMediaItemRepository;

    @Inject
    private TraIndustryRepository traIndustryRepository;

    @Inject
    private CrmAccountRepository crmAccountRepository;

    public List<TraAdvertisementPT> getAllAdvertisement(CorNetwork corNetwork) {
        return traAdvertisementRepository.findByNetwork(corNetwork).stream().map(traAdvertisement -> traAdvertismentMapper.transformEntityToDTO(traAdvertisement)).collect(toList());
    }

    public TraAdvertisementPT saveAdvertisement(TraAdvertisementPT traAdvertisementPT, CorNetwork corNetwork) {
        TraAdvertisement traAdvertisement = traAdvertismentMapper.transformDTOToEntity(traAdvertisementPT, corNetwork);
        traAdvertisementRepository.save(traAdvertisement);
        return traAdvertismentMapper.transformEntityToDTO(traAdvertisement);
    }

    public void deleteAdvertisement(Long idx, CorNetwork corNetwork) {
        TraAdvertisement traAdvertisement = traAdvertisementRepository.findOne(idx);
        libMediaItemRepository.delete(traAdvertisement.getLibitem());
        traAdvertisementRepository.delete(traAdvertisement);
    }

    public TraAdvertisementPT getAdvertisement(Long id, CorNetwork corNetwork) {
        TraAdvertisement traAdvertisement = traAdvertisementRepository.findOne(id);
        return traAdvertismentMapper.transformEntityToDTO(traAdvertisement);
    }


    public List<TraAdvertisementPT> getCustomerAdvertisments(String shortcut, CorNetwork corNetwork) {
        return null;
    }


}
