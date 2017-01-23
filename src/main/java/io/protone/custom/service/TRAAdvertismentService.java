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

    public List<TraAdvertisementPT> getAllAdvertisement() {
        List<TraAdvertisementPT> traAdvertisementPTList = new ArrayList<>();
        List<TRAAdvertisement> traAdvertisementList = traAdvertisementRepository.findAll();
        traAdvertisementList.stream().forEach(traAdvertisement -> traAdvertisementPTList.add(getTraAdvertisment(traAdvertisement)));
        return traAdvertisementPTList;
    }

    public TraAdvertisementPT saveAdvertisement(TraAdvertisementPT traAdvertisementPT) {
        TRAAdvertisement traAdvertisement = traAdvertismentMapper.transformDTOToEntity(traAdvertisementPT);
        traAdvertisement = traAdvertisementRepository.save(traAdvertisement);
        TRAIndustry industry = customTRAIndustryMapper.tRAIndustryDTOToTRAIndustry(traAdvertisementPT.getIndustryId());
        CRMAccount crmAccount = customCRMAccountMapper.createCrmAcountEntity(traAdvertisementPT.getCustomerId());
        LIBMediaItem libMediaItem = libMediaItemRepository.findOne(1L);
        corAssociationRepository.save(traAdvertismentMapper.createAdvertismentCrmAccountAssociation(traAdvertisement, crmAccount));
        corAssociationRepository.save(traAdvertismentMapper.createAdvertismentIndustryAssociation(traAdvertisement, industry));
        corAssociationRepository.save(traAdvertismentMapper.createAdvertismentMediaItemAssociation(traAdvertisement, libMediaItem));
        return traAdvertismentMapper.transformEntityToDTO(traAdvertisement, industry, crmAccount, new LibItemPT());
    }

    public void deleteAdvertisement(Long idx) {
        TRAAdvertisement traAdvertisement = traAdvertisementRepository.findOne(idx);
        corAssociationRepository.deleteBySourceIdAndTargetClass(traAdvertisement.getId(), TRAIndustry.class.getName());
        corAssociationRepository.deleteBySourceIdAndTargetClass(traAdvertisement.getId(), CRMAccount.class.getName());
        List<CORAssociation> advertismentLibItemList = corAssociationRepository.findBySourceIdAndTargetClass(traAdvertisement.getId(), LIBMediaItem.class.getName());
        libMediaItemRepository.delete(advertismentLibItemList.get(0).getId());
        corAssociationRepository.delete(advertismentLibItemList);

    }

    public TraAdvertisementPT getAdvertisement(Long id) {
        TRAAdvertisement traAdvertisement = traAdvertisementRepository.findOne(id);
        return getTraAdvertisment(traAdvertisement);
    }

    public List<TraAdvertisementPT> getAdverismentWithoutSelection(List<Long> listID) {
        List<TRAAdvertisement> traAdvertisement = traAdvertisementRepository.findAll(listID);
        return traAdvertisement.stream().map(this::getTraAdvertisment).collect(toList());
    }

    public List<TraAdvertisementPT> getCustomerAdvertisments(String shortcut) {
        CRMAccount crmAccount = crmAccountRepository.findByShortName(shortcut);
        List<CORAssociation> associations = corAssociationRepository.findByTargetIdAndSourceClass(crmAccount.getId(), TRAAdvertisement.class.getName());
        return getAdverismentWithoutSelection(associations.stream().map(CORAssociation::getSourceId).collect(toList()));

    }

    public TraAdvertisementPT getTraAdvertisment(TRAAdvertisement traAdvertisement) {

        List<CORAssociation> advertismentIndustryList = corAssociationRepository.findBySourceIdAndTargetClass(traAdvertisement.getId(), TRAIndustry.class.getName());
        List<CORAssociation> advertismentCustomerList = corAssociationRepository.findBySourceIdAndTargetClass(traAdvertisement.getId(), CRMAccount.class.getName());
        List<CORAssociation> advertismentLibItemList = corAssociationRepository.findBySourceIdAndTargetClass(traAdvertisement.getId(), LIBMediaItem.class.getName());
        TRAIndustry industry = traIndustryRepository.findOne(advertismentIndustryList.get(0).getTargetId());
        CRMAccount crmAccount = crmAccountRepository.findOne(advertismentCustomerList.get(0).getTargetId());
        LIBMediaItem libMediaItem = libMediaItemRepository.findOne(advertismentLibItemList.get(0).getId());
        return traAdvertismentMapper.transformEntityToDTO(traAdvertisement, industry, crmAccount, new LibItemPT());
    }
}
