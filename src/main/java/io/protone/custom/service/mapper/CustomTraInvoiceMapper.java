package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraInvoice;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.stream.Collectors;

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
    @Inject
    private CustomCorDictionaryMapper corDictionaryMapper;

    public TraInvoice createEntityFromDTO(TraInvoicePT traInvoicePT, CorNetwork corNetwork) {
        if (traInvoicePT == null || corNetwork == null) {
            return new TraInvoice();
        }
        TraInvoice traInvoice = new TraInvoice();
        traInvoice.setId(traInvoicePT.getId());
        return traInvoice.paid(traInvoicePT.getPaid())
            .paymentDay(traInvoicePT.getPaymentDay())
            .price(traInvoicePT.getPrice())
            .customer(crmAccountMapper.createCrmAcountEntity(traInvoicePT.getCustomerPT(), corNetwork))
            .orders(traInvoicePT.getOrder().stream().map(traOrderPT -> traOrderMapper.trasnformDTOtoEntity(traOrderPT, corNetwork)).collect(Collectors.toSet()))
            .network(corNetwork)
            .status(corDictionaryMapper.corDictionaryDTOToCorDictionary(traInvoicePT.getTraStatus()));
    }

    public TraInvoicePT createDTOFromEnity(TraInvoice traInvoicePT) {
        if (traInvoicePT == null) {
            return new TraInvoicePT();
        }
        return new TraInvoicePT().id(traInvoicePT.getId())
            .order(customTraOrderMapper.transfromEntitesToDTO(traInvoicePT.getOrders()))
            .paid(traInvoicePT.isPaid())
            .paymentDay(traInvoicePT.getPaymentDay())
            .customerId(crmAccountMapper.createCustomerTrafficDTO(traInvoicePT.getCustomer()))
            .traStatus(corDictionaryMapper.corDictionaryToCorDictionaryDTO(traInvoicePT.getStatus()))
            .price(traInvoicePT.getPrice());
    }


}
