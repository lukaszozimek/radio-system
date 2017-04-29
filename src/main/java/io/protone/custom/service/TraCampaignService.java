package io.protone.custom.service;

import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.web.rest.mapper.TraCampaignMapper;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraCampaign;
import io.protone.repository.TraOrderRepository;
import io.protone.repository.custom.CustomCrmAccountRepositoryEx;
import io.protone.repository.custom.CustomTraCampaignRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TraCampaignService {

    private final Logger log = LoggerFactory.getLogger(TraCampaignService.class);

    @Inject
    private TraCampaignMapper customTRACampaignMapper;

    @Inject
    private CustomTraCampaignRepository traCampaignRepository;

    @Inject
    private CustomCrmAccountRepositoryEx crmAccountRepository;

    @Inject
    private TraCustomerService traCustomerService;

    @Inject
    private TraOrderRepository traOrderRepository;

    public List<TraCampaignPT> getAllCampaign(CorNetwork corNetwork) {
        return traCampaignRepository.findByNetwork(corNetwork).stream().map(campaign -> getCampaign(campaign, corNetwork)).collect(toList());
    }

    public TraCampaignPT saveCampaign(TraCampaignPT campaignPT, CorNetwork corNetwork) {
        TraCampaign traCampaign = customTRACampaignMapper.DTO2DB(campaignPT);
        traCampaign.network(corNetwork);
        if (!traCampaign.getOrders().isEmpty() && traCampaign.getOrders() != null) {
            traCampaign.setOrders(traCampaign.getOrders().stream().map(traOrder -> traOrderRepository.saveAndFlush(traOrder)).collect(Collectors.toSet()));
        }
        log.debug("Persisting TraCampaign: {}", traCampaign);
        traCampaign = traCampaignRepository.saveAndFlush(traCampaign);

        return customTRACampaignMapper.DB2DTO(traCampaign);
    }

    public void deleteCampaign(String shortcut, CorNetwork corNetwork) {
        traCampaignRepository.deleteByNameAndNetwork(shortcut, corNetwork);
    }

    public TraCampaignPT getCampaign(TraCampaign traCampaign, CorNetwork corNetwork) {
        return customTRACampaignMapper.DB2DTO(traCampaign);
    }

    public TraCampaignPT getCampaign(String shortcut, CorNetwork corNetwork) {
        return getCampaign(traCampaignRepository.findByNameAndNetwork(shortcut, corNetwork), corNetwork);
    }

    public List<TraCampaignPT> getCampaign(List<Long> idx, CorNetwork corNetwork) {
        return traCampaignRepository.findAll(idx).stream().map(campaign -> getCampaign(campaign, corNetwork)).collect(toList());
    }

    public List<TraCampaignPT> getCustomerCampaing(String shortcut, CorNetwork corNetwork) {
        CrmAccount crmAccount = crmAccountRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork.getShortcut());
        return traCampaignRepository.findByCustomerAndNetwork(crmAccount, corNetwork).stream().map(traCampaign -> customTRACampaignMapper.DB2DTO(traCampaign)).collect(toList());
    }

}
