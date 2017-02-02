package io.protone.custom.service;

import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.mapper.CustomCRMAccountMapper;
import io.protone.custom.service.mapper.CustomSCHEmissionMapper;
import io.protone.custom.service.mapper.CustomTRACampaignMapper;
import io.protone.domain.*;
import io.protone.repository.CRMAccountRepository;
import io.protone.repository.SCHEmissionRepository;
import io.protone.repository.TRACampaignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

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

    public List<TraCampaignPT> getAllCampaign(CORNetwork corNetwork) {
        return traCampaignRepository.findByNetwork(corNetwork).stream().map(campaign -> getCampaign(campaign, corNetwork)).collect(toList());
    }

    public TraCampaignPT saveCampaign(TraCampaignPT campaignPT, CORNetwork corNetwork) {
        TRACampaign traCampaign = customTRACampaignMapper.transfromDTOToEntity(campaignPT);
        return customTRACampaignMapper.transfromEntitytoDTO(traCampaign);
    }

    public TraCampaignPT update(TraCampaignPT campaignPT, CORNetwork corNetwork) {
        deleteCampaign(campaignPT.getName(), corNetwork);
        return saveCampaign(campaignPT, corNetwork);
    }

    public void deleteCampaign(String shortcut, CORNetwork corNetwork) {


    }

    public TraCampaignPT getCampaign(TRACampaign traCampaign, CORNetwork corNetwork) {
        return customTRACampaignMapper.transfromEntitytoDTO(traCampaign);
    }

    public TraCampaignPT getCampaign(String shortcut, CORNetwork corNetwork) {
        return getCampaign(traCampaignRepository.findByNameAndNetwork(shortcut, corNetwork), corNetwork);
    }

    public List<TraCampaignPT> getCampaign(List<Long> idx, CORNetwork corNetwork) {
        return traCampaignRepository.findAll(idx).stream().map(campaign -> getCampaign(campaign, corNetwork)).collect(toList());
    }

    public List<TraCampaignPT> getCustomerCampaing(String shortcut, CORNetwork corNetwork) {
    return null;
    }

}
