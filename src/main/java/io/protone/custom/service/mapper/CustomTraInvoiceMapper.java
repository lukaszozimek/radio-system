package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomTraInvoiceMapper {

    @Inject
    private CustomTraOrderMapper customTraOrderMapper;
    @Inject
    private CustomCrmAccountMapper crmAccountMapper;
    @Inject
    private CustomTraOrderMapper traOrderMapper;

    public TraInvoice createEntityFromDTO(TraInvoicePT traInvoicePT, CorNetwork corNetwork) {
        TraInvoice traInvoice = new TraInvoice();
        traInvoice.setId(traInvoice.getId());
        return traInvoice.paid(traInvoice.isPaid())
            .paymentDay(traInvoice.getPaymentDay())
            .customer(crmAccountMapper.createCrmAcountEntity(traInvoicePT.getCustomerPT(), corNetwork))
            .orders(traInvoicePT.getOrder().stream().map(traOrderPT -> traOrderMapper.trasnformDTOtoEntity(traOrderPT, corNetwork)).collect(Collectors.toSet()))
            .network(corNetwork)
            .status(traInvoicePT.getTraStatus());
    }

    public TraInvoicePT createDTOFromEnity(TraInvoice traInvoicePT) {
        return new TraInvoicePT().id(traInvoicePT.getId())
            .order(customTraOrderMapper.transfromEntitesToDTO(traInvoicePT.getOrders()))
            .paid(traInvoicePT.isPaid())
            .paymentDay(traInvoicePT.getPaymentDay())
            .customerId(crmAccountMapper.createCustomerTrafficDTO(traInvoicePT.getCustomer()))
            .traStatus(traInvoicePT.getStatus());
    }


}
