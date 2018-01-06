package io.protone.traffic.service;


import io.protone.crm.domain.CrmAccount;
import io.protone.crm.service.CrmCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 08/06/2017.
 */

@Service
public class TraCustomerService {
    private final Logger log = LoggerFactory.getLogger(TraCustomerService.class);

    @Inject
    private TraOrderService traOrderService;

    @Inject
    private TraAdvertisementService traAdvertisementService;

    @Inject
    private TraInvoiceService traInvoiceService;

    @Inject
    private TraCampaignService traCampaignService;

    @Inject
    private TraMediaPlanService traMediaPlanService;

    @Inject
    private CrmCustomerService crmCustomerService;

    @Transactional
    public void deleteCustomerByShortNameAndNetworkShortName(String customerShortName, String organizationShortcut, String channelShortcut) {
        CrmAccount crmAccount = crmCustomerService.getCustomer(customerShortName, organizationShortcut, channelShortcut);
        traOrderService.deleteCustomerOrders(crmAccount, organizationShortcut, channelShortcut);
        traAdvertisementService.deleteCustomerAdvertisement(crmAccount, organizationShortcut, channelShortcut);
        traMediaPlanService.deleteCustomerMediaPlan(crmAccount, organizationShortcut, channelShortcut);
        traCampaignService.deleteCampaignByCustomer(crmAccount, organizationShortcut, channelShortcut);
        traInvoiceService.deleteByCustomerInvoice(crmAccount, organizationShortcut, channelShortcut);
        crmCustomerService.deleteCustomer(customerShortName, organizationShortcut, channelShortcut);
    }


}
