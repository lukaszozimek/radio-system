package io.protone.service.traffic;

import io.protone.domain.TraCampaign;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.traffic.TraCampaignRepository;
import io.protone.service.crm.CrmCustomerService;
import io.protone.web.rest.mapper.TraCampaignMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TraCampaignService {

    private final Logger log = LoggerFactory.getLogger(TraCampaignService.class);

    @Inject
    private TraCampaignRepository traCampaignRepository;

    @Inject
    private CrmAccountRepository crmAccountRepository;

    @Inject
    private TraOrderService traOrderService;

    public List<TraCampaign> getAllCampaign(String corNetwork, Pageable pageable) {
        return traCampaignRepository.findAllByNetwork_Shortcut(corNetwork, pageable);
    }

    public TraCampaign saveCampaign(TraCampaign campaign) {
        if (!campaign.getOrders().isEmpty() && campaign.getOrders() != null) {
            log.debug("Persisting TraOrders Associated with Tra Campaign: {}", campaign);
            campaign.setOrders(campaign.getOrders().stream().map(traOrder -> traOrderService.saveOrder(traOrder)).collect(Collectors.toSet()));
        }
        log.debug("Persisting TraCampaign: {}", campaign);
        campaign = traCampaignRepository.saveAndFlush(campaign);
        return campaign;
    }

    public void deleteCampaign(String shortcut, String corNetwork) {
        traCampaignRepository.deleteByNameAndNetwork_Shortcut(shortcut, corNetwork);
    }


    public TraCampaign getCampaign(String shortcut, String corNetwork) {
        return traCampaignRepository.findByNameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public List<TraCampaign> getCustomerCampaing(String shortcut, String corNetwork, Pageable pageable) {
        return traCampaignRepository.findByCustomer_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

}
