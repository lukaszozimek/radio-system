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
        traAdvertisement = traAdvertisementRepository.save(traAdvertisement);
        TRAIndustry industry = customTRAIndustryMapper.tRAIndustryDTOToTRAIndustry(traAdvertisementPT.getIndustryId());
        CRMAccount crmAccount = customCRMAccountMapper.createCrmAcountEntity(traAdvertisementPT.getCustomerId());
        LIBMediaItem libMediaItem = libMediaItemRepository.findOne(1L);
        corAssociationRepository.save(traAdvertismentMapper.createAdvertismentCrmAccountAssociation(traAdvertisement, crmAccount));
        corAssociationRepository.save(traAdvertismentMapper.createAdvertismentIndustryAssociation(traAdvertisement, industry));
        corAssociationRepository.save(traAdvertismentMapper.createAdvertismentMediaItemAssociation(traAdvertisement, libMediaItem));
        return traAdvertismentMapper.transformEntityToDTO(traAdvertisement, industry, crmAccount, new LibItemPT());
    }

    public void deleteAdvertisement(Long idx, CORNetwork corNetwork) {
        TRAAdvertisement traAdvertisement = traAdvertisementRepository.findOne(idx);
        corAssociationRepository.deleteBySourceIdAndTargetClassAndNetwork(traAdvertisement.getId(), TRAIndustry.class.getName(),corNetwork);
        corAssociationRepository.deleteBySourceIdAndTargetClassAndNetwork(traAdvertisement.getId(), CRMAccount.class.getName(),corNetwork);
        List<CORAssociation> advertismentLibItemList = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(traAdvertisement.getId(), LIBMediaItem.class.getName(),corNetwork);
        libMediaItemRepository.delete(advertismentLibItemList.get(0).getId());
        corAssociationRepository.delete(advertismentLibItemList);

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
        CRMAccount crmAccount = crmAccountRepository.findOneByShortNameAndNetwork(shortcut,corNetwork);
        List<CORAssociation> associations = corAssociationRepository.findByTargetIdAndSourceClassAndNetwork(crmAccount.getId(), TRAAdvertisement.class.getName(),corNetwork);
        return getAdverismentWithoutSelection(associations.stream().map(CORAssociation::getSourceId).collect(toList()), corNetwork);

    }

    public TraAdvertisementPT getTraAdvertisment(TRAAdvertisement traAdvertisement, CORNetwork corNetwork) {

        List<CORAssociation> advertismentIndustryList = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(traAdvertisement.getId(), TRAIndustry.class.getName(),corNetwork);
        List<CORAssociation> advertismentCustomerList = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(traAdvertisement.getId(), CRMAccount.class.getName(),corNetwork);
        List<CORAssociation> advertismentLibItemList = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(traAdvertisement.getId(), LIBMediaItem.class.getName(),corNetwork);
        TRAIndustry industry = traIndustryRepository.findOne(advertismentIndustryList.get(0).getTargetId());
        CRMAccount crmAccount = crmAccountRepository.findOne(advertismentCustomerList.get(0).getTargetId());
        LIBMediaItem libMediaItem = libMediaItemRepository.findOne(advertismentLibItemList.get(0).getId());
        return traAdvertismentMapper.transformEntityToDTO(traAdvertisement, industry, crmAccount, new LibItemPT());
    }
}
