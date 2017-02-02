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
    private CORAssociationRepository corAssociationRepository;

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
        List<TraAdvertisementPT> traAdvertisementPTList = new ArrayList<>();
        List<TRAAdvertisement> traAdvertisementList = traAdvertisementRepository.findByNetwork(corNetwork);
        traAdvertisementList.stream().forEach(traAdvertisement -> traAdvertisementPTList.add(getTraAdvertisment(traAdvertisement, corNetwork)));
        return traAdvertisementPTList;
    }

    public TraAdvertisementPT saveAdvertisement(TraAdvertisementPT traAdvertisementPT, CORNetwork corNetwork) {
        TRAAdvertisement traAdvertisement = traAdvertismentMapper.transformDTOToEntity(traAdvertisementPT);
           return traAdvertismentMapper.transformEntityToDTO(traAdvertisement);
    }

    public void deleteAdvertisement(Long idx, CORNetwork corNetwork) {

    }

    public TraAdvertisementPT getAdvertisement(Long id, CORNetwork corNetwork) {
        TRAAdvertisement traAdvertisement = traAdvertisementRepository.findOne(id);
        return getTraAdvertisment(traAdvertisement, corNetwork);
    }

    public TraAdvertisementPT update(TraAdvertisementPT traAdvertisementPT, CORNetwork corNetwork) {
        deleteAdvertisement(traAdvertisementPT.getId(), corNetwork);
        return saveAdvertisement(traAdvertisementPT, corNetwork);
    }

    public List<TraAdvertisementPT> getAdverismentWithoutSelection(List<Long> listID, CORNetwork corNetwork) {
        List<TRAAdvertisement> traAdvertisement = traAdvertisementRepository.findAll(listID);
        return traAdvertisement.stream().map(traAdvertisement1 -> getTraAdvertisment(traAdvertisement1, corNetwork)).collect(toList());
    }

    public List<TraAdvertisementPT> getCustomerAdvertisments(String shortcut, CORNetwork corNetwork) {
     return null;
    }

    public TraAdvertisementPT getTraAdvertisment(TRAAdvertisement traAdvertisement, CORNetwork corNetwork) {
    return traAdvertismentMapper.transformEntityToDTO(traAdvertisement);
    }
}
