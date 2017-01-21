package io.protone.custom.service;

import io.protone.custom.service.dto.*;
import io.protone.custom.service.mapper.CustomTRACustomerMapper;
import io.protone.repository.CORAssociationRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
public class TRACustomerService {

    @Inject
    private CORAssociationRepository corAssociationRepository;

    @Inject
    private CustomTRACustomerMapper customTRACustomerMapper;

    public List<TraCustomerPT> getAllCustomers() {

        return null;
    }

    public TraCustomerPT saveCustomers() {

        return null;
    }

    public void deleteCustomer() {

    }

    public TraCustomerPT getCustomer(String shortcut) {
        return null;
    }

    public TraCustomerAdvertismentsPT getCustomerAdvertisments(String shortcut) {
        return null;
    }

    public TraCampaignPT getCustomerCampaing(String shortcut) {
        return null;
    }

    public TraOrderPT getCustomerOrders(String shortcut) {
        return null;
    }

    public TraInvoicePT getCustomerInvoice(String shortcut) {
        return null;
    }

}
