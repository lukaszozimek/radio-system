package io.protone.custom.service;

import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.mapper.CustomCrmAccountMapper;
import io.protone.custom.service.mapper.CustomSchEmissionMapper;
import io.protone.custom.service.mapper.CustomTRACampaignMapper;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraCampaign;
import io.protone.repository.SchEmissionRepository;
import io.protone.repository.custom.CustomCrmAccountRepositoryEx;
import io.protone.repository.custom.CustomTraCampaignRepository;
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
public class TraCampaignService {

    @Inject
    private CustomTRACampaignMapper customTRACampaignMapper;

    @Inject
    private CustomTraCampaignRepository traCampaignRepository;

    @Inject
    private CustomSchEmissionMapper customSchEmissionMapper;

    @Inject
    private CustomCrmAccountMapper customCrmAccountMapper;

    @Inject
    private CustomCrmAccountRepositoryEx crmAccountRepository;

    @Inject
    private SchEmissionRepository schEmissionRepository;

    @Inject
    private TraCustomerService traCustomerService;

    public List<TraCampaignPT> getAllCampaign(CorNetwork corNetwork) {
        return traCampaignRepository.findByNetwork(corNetwork).stream().map(campaign -> getCampaign(campaign, corNetwork)).collect(toList());
    }

    public TraCampaignPT saveCampaign(TraCampaignPT campaignPT, CorNetwork corNetwork) {
        TraCampaign traCampaign = customTRACampaignMapper.transfromDTOToEntity(campaignPT, corNetwork);
        return customTRACampaignMapper.transfromEntitytoDTO(traCampaign);
    }

    public void deleteCampaign(String shortcut, CorNetwork corNetwork) {
        traCampaignRepository.deleteByNameAndNetwork(shortcut, corNetwork);
    }

    public TraCampaignPT getCampaign(TraCampaign traCampaign, CorNetwork corNetwork) {
        return customTRACampaignMapper.transfromEntitytoDTO(traCampaign);
    }

    public TraCampaignPT getCampaign(String shortcut, CorNetwork corNetwork) {
        return getCampaign(traCampaignRepository.findByNameAndNetwork(shortcut, corNetwork), corNetwork);
    }

    public List<TraCampaignPT> getCampaign(List<Long> idx, CorNetwork corNetwork) {
        return traCampaignRepository.findAll(idx).stream().map(campaign -> getCampaign(campaign, corNetwork)).collect(toList());
    }

    public List<TraCampaignPT> getCustomerCampaing(String shortcut, CorNetwork corNetwork) {
        CrmAccount crmAccount = crmAccountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return traCampaignRepository.findByCustomerAndNetwork(crmAccount, corNetwork).stream().map(traCampaign -> customTRACampaignMapper.transfromEntitytoDTO(traCampaign)).collect(toList());
    }

}
