package io.protone.custom.service;

import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.mapper.CustomCRMAccountMapper;
import io.protone.custom.service.mapper.CustomSCHEmissionMapper;
import io.protone.custom.service.mapper.CustomTRACampaignMapper;
import io.protone.domain.CORAssociation;
import io.protone.domain.CRMAccount;
import io.protone.domain.SCHEmission;
import io.protone.domain.TRACampaign;
import io.protone.repository.CORAssociationRepository;
import io.protone.repository.CRMAccountRepository;
import io.protone.repository.SCHEmissionRepository;
import io.protone.repository.TRACampaignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TRACampaignService {

    @Inject
    private CustomTRACampaignMapper customTRACampaignMapper;

    @Inject
    private CORAssociationRepository corAssociationRepositor;

    @Inject
    private TRACampaignRepository traCampaignRepository;

    @Inject
    private CustomSCHEmissionMapper customSCHEmissionMapper;

    @Inject
    private CustomCRMAccountMapper customCRMAccountMapper;

    @Inject
    private CRMAccountRepository crmAccountRepository;

    @Inject
    private SCHEmissionRepository schEmissionRepository;
    @Inject
    private TRACustomerService traCustomerService;

    public List<TraCampaignPT> getAllCampaign() {
        return traCampaignRepository.findAll().stream().map(this::getCampaign).collect(toList());
    }

    public TraCampaignPT saveCampaign(TraCampaignPT campaignPT) {
        TRACampaign traCampaign = customTRACampaignMapper.transfromDTOToEntity(campaignPT);
        traCampaign = traCampaignRepository.save(traCampaign);
        List<SCHEmission> schEmissionList = customSCHEmissionMapper.createListEmissionFromListDTO(campaignPT.getEmission());
        CRMAccount crmAccount = customCRMAccountMapper.createCrmAcountEntity(campaignPT.getCustomerId());
        corAssociationRepositor.save(customTRACampaignMapper.createCrmAccountAssociation(traCampaign, crmAccount));
        corAssociationRepositor.save(customTRACampaignMapper.createListSCHEmissionAssociation(traCampaign, schEmissionList));
        return customTRACampaignMapper.transfromEntitytoDTO(traCampaign, customSCHEmissionMapper.createDTOFromListEntites(new HashMap<>()), traCustomerService.getCustomer(crmAccount));
    }

    public void deleteCampaign(String shortcut) {

    }

    public TraCampaignPT getCampaign(TRACampaign traCampaign) {
        List<CORAssociation> corCRMAssociationList = corAssociationRepositor.findBySourceIdAndTargetClass(traCampaign.getId(), CRMAccount.class.getName());
        List<CORAssociation> corSCHEmissionAssociationList = corAssociationRepositor.findBySourceIdAndTargetClass(traCampaign.getId(), SCHEmission.class.getName());
        CRMAccount crmAccount = crmAccountRepository.findOne(corCRMAssociationList.get(0).getTargetId());
        List<SCHEmission> schEmissionList = schEmissionRepository.findAll(corSCHEmissionAssociationList.stream().map(CORAssociation::getTargetId).collect(toList()));
        return customTRACampaignMapper.transfromEntitytoDTO(traCampaign, customSCHEmissionMapper.createDTOFromListEntites(new HashMap<>()), traCustomerService.getCustomer(crmAccount));
    }

    public TraCampaignPT getCampaign(String shortcut) {
        return getCampaign(traCampaignRepository.findByName(shortcut));
    }

    public List<TraCampaignPT> getCustomerCampaing(String shortcut) {
        return null;
    }

}
