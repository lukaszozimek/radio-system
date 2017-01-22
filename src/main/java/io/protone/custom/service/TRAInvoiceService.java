package io.protone.custom.service;

import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.service.mapper.CustomCRMAccountMapper;
import io.protone.custom.service.mapper.CustomTRAInvoiceMapper;
import io.protone.custom.service.mapper.CustomTRAOrderMapper;
import io.protone.domain.CORAssociation;
import io.protone.domain.CRMAccount;
import io.protone.domain.TRAInvoice;
import io.protone.domain.TRAOrder;
import io.protone.repository.CORAssociationRepository;
import io.protone.repository.CRMAccountRepository;
import io.protone.repository.TRAInvoiceRepository;
import io.protone.repository.TRAOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TRAInvoiceService {

    @Inject
    private CORAssociationRepository corAssociationRepository;

    @Inject
    private TRAInvoiceRepository traInvoiceRepository;

    @Inject
    private CustomTRAInvoiceMapper customTRAInvoiceMapper;

    @Inject
    private CustomCRMAccountMapper customCRMAccountMapper;

    @Inject
    private CustomTRAOrderMapper customTRAOrderMapper;

    @Inject
    private TRAOrderService traOrderService;

    @Inject
    private TRAOrderRepository traOrderRepository;

    @Inject
    private CRMAccountRepository crmAccountRepository;

    @Inject
    private TRACustomerService customerService;

    public List<TraInvoicePT> getAllInvoice() {
        List<TraInvoicePT> traInvoicePTS = new ArrayList<>();
        List<TRAInvoice> traInvoices = traInvoiceRepository.findAll();
        traInvoices.stream().forEach(traInvoice -> {
            traInvoicePTS.add(getInvoice(traInvoice));
        });
        return traInvoicePTS;
    }

    public TraInvoicePT saveInvoice(TraInvoicePT traInvoicePT) {
        TRAInvoice invoice = customTRAInvoiceMapper.createEntityFromDTO(traInvoicePT);
        CRMAccount crmAccount = customCRMAccountMapper.createCrmAcountEntity(traInvoicePT.getCustomerPT());
        List<TRAOrder> traOrders = customTRAOrderMapper.trasnformDTOtoEntity(traInvoicePT.getOrder());
        List<Long> traOrdersId = traOrders.stream().map(TRAOrder::getId).collect(toList());
        invoice = traInvoiceRepository.save(invoice);
        corAssociationRepository.save(customTRAInvoiceMapper.createCrmAccountAssociation(invoice, crmAccount));
        corAssociationRepository.save(customTRAInvoiceMapper.createListOrderAssociation(invoice, traOrders));
        return customTRAInvoiceMapper.createDTOFromEnity(invoice, traOrderService.getOrdersById(traOrdersId), customerService.getCustomer(crmAccount));
    }

    public void deleteInvoice(Long id) {
        List<CORAssociation> corAssociationList = new ArrayList<>();

    }

    public TraInvoicePT getInvoice(Long id) {
        TRAInvoice traInvoice = traInvoiceRepository.findOne(id);
        return getInvoice(traInvoice);
    }

    public TraInvoicePT getInvoice(TRAInvoice traInvoice) {
        List<CORAssociation> corAssociationCRMAccountList = corAssociationRepository.findBySourceIdAndTargetClass(traInvoice.getId(), CRMAccount.class.getName());
        List<CORAssociation> corAssociationOrderList = corAssociationRepository.findBySourceIdAndTargetClass(traInvoice.getId(), TRAOrder.class.getName());
        List<Long> ordersID = corAssociationOrderList.stream().map(CORAssociation::getTargetId).collect(toList());
        List<TRAOrder> orders = traOrderRepository.findAll(ordersID);
        CRMAccount crmAccount = crmAccountRepository.findOne(corAssociationCRMAccountList.get(0).getTargetId());
        return customTRAInvoiceMapper.createDTOFromEnity(traInvoice, traOrderService.getOrdersByEntitie(orders), customerService.getCustomer(crmAccount));
    }

}
