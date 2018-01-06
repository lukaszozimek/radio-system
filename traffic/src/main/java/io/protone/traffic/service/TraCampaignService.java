package io.protone.traffic.service;


import io.protone.crm.domain.CrmAccount;
import io.protone.traffic.domain.TraCampaign;
import io.protone.traffic.repository.TraCampaignRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
    private TraOrderService traOrderService;

    public Slice<TraCampaign> getAllCampaign(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return traCampaignRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pageable);
    }

    public TraCampaign saveCampaign(TraCampaign campaign) {
        if (!campaign.getOrders().isEmpty() && campaign.getOrders() != null) {
            log.debug("Persisting TraOrders Associated with Tra Campaign: {}", campaign);
            campaign.setOrders(campaign.getOrders().stream().map(traOrder -> traOrderService.saveOrderLazy(traOrder)).collect(Collectors.toSet()));
        }
        log.debug("Persisting TraCampaign: {}", campaign);
        campaign = traCampaignRepository.saveAndFlush(campaign);
        return campaign;
    }

    public void deleteCampaign(String shortcut, String organizationShortcut, String channelShortcut) {
        traCampaignRepository.deleteByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
    }

    public void deleteCampaignByCustomer(CrmAccount crmAccount, String organizationShortcut, String channelShortcut) {
        traCampaignRepository.deleteByCustomerAndChannel_Organization_ShortcutAndChannel_Shortcut(crmAccount, organizationShortcut, channelShortcut);
    }

    public TraCampaign getCampaign(String shortcut, String organizationShortcut, String channelShortcut) {
        return traCampaignRepository.findByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
    }

    public Slice<TraCampaign> getCustomerCampaing(String shortcut, String organizationShortcut, String channelShortcut, Pageable pageable) {
        return traCampaignRepository.findSliceByCustomer_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut, pageable);
    }

}
