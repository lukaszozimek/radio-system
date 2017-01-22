package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomTRAInvoiceMapper {

    @Inject
    private CustomTRAOrderMapper customTRAOrderMapper;

    public TRAInvoice createEntityFromDTO(TraInvoicePT traInvoicePT) {
        TRAInvoice traInvoice = new TRAInvoice();
        traInvoice.setId(traInvoice.getId());
        return traInvoice.paid(traInvoice.isPaid()).paymentDay(traInvoice.getPaymentDay());
    }

    public TraInvoicePT createDTOFromEnity(TRAInvoice traInvoicePT, List<TraOrderPT> traOrderPTList, TraCustomerPT customerPT) {
        return new TraInvoicePT().id(traInvoicePT.getId())
            .order(traOrderPTList)
            .paid(traInvoicePT.isPaid())
            .paymentDay(traInvoicePT.getPaymentDay())
            .customerId(customerPT);
    }

    public List<CORAssociation> createListOrderAssociation(TRAInvoice traInvoice, List<TRAOrder> traOrderList) {
        List<CORAssociation> corAssociationList = new ArrayList<>();
        traOrderList.stream().forEach(traOrder -> corAssociationList.add(createOrderAssociation(traInvoice, traOrder)));
        return corAssociationList;
    }

    public CORAssociation createOrderAssociation(TRAInvoice traInvoice, TRAOrder traOrder) {
        return new CORAssociation().name("ORDER SCHEDULED EMISSION")
            .sourceId(traInvoice.getId())
            .sourceClass(TRAInvoice.class.getName())
            .targetId(traOrder.getId())
            .targetClass(TRAOrder.class.getName());
    }

    public CORAssociation createCrmAccountAssociation(TRAInvoice traInvoice, CRMAccount crmAccount) {
        return new CORAssociation().name("ORDER customer")
            .sourceId(traInvoice.getId())
            .sourceClass(TRAInvoice.class.getName())
            .targetId(crmAccount.getId())
            .targetClass(CRMAccount.class.getName());
    }

}
