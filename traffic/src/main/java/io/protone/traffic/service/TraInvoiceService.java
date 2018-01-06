package io.protone.traffic.service;


import io.protone.crm.domain.CrmAccount;
import io.protone.traffic.domain.TraInvoice;
import io.protone.traffic.repository.TraInvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TraInvoiceService {

    private final Logger log = LoggerFactory.getLogger(TraInvoiceService.class);

    @Inject
    private TraInvoiceRepository traInvoiceRepository;
    @Inject
    private TraOrderService traOrderService;


    public Slice<TraInvoice> getAllInvoice(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return traInvoiceRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pageable);
    }

    public TraInvoice saveInvoice(TraInvoice traInvoice) {
        log.debug("Persisting TraInvoice: {}", traInvoice);
        TraInvoice savedInvoice = traInvoiceRepository.saveAndFlush(traInvoice);
        if (traInvoice.getOrders() != null && !traInvoice.getOrders().isEmpty()) {
            savedInvoice.setOrders(traInvoice.getOrders().stream().map(traOrder -> traOrderService.saveOrder(traOrder.invoice(savedInvoice))).collect(toSet()));
        }
        return getInvoice(savedInvoice.getId(), savedInvoice.getChannel().getOrganization().getShortcut(), savedInvoice.getChannel().getShortcut());
    }

    public void deleteInvoice(Long id, String organizationShortcut, String channelShortcut) {
        TraInvoice traInvoice = getInvoice(id, organizationShortcut, channelShortcut);
        if (traInvoice != null) {
            if (traInvoice.getOrders() != null && !traInvoice.getOrders().isEmpty()) {
                traInvoice.setOrders(traInvoice.getOrders().stream().map(traOrder -> traOrderService.saveOrder(traOrder.invoice(null).calculatedPrize(null))).collect(toSet()));
            }
            traInvoiceRepository.delete(traInvoice);
        }
    }

    public void deleteByCustomerInvoice(CrmAccount crmAccount, String organizationShortcut, String channelShortcut) {
        traInvoiceRepository.deleteByCustomerAndChannel_Organization_ShortcutAndChannel_Shortcut(crmAccount, organizationShortcut, channelShortcut);
    }

    public TraInvoice getInvoice(Long id, String organizationShortcut, String channelShortcut) {
        return traInvoiceRepository.findByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
    }

    public Slice<TraInvoice> getCustomerInvoice(String customerShortcut, String organizationShortcut, String channelShortcut, Pageable pageable) {
        return traInvoiceRepository.findSliceByCustomer_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(customerShortcut, organizationShortcut, channelShortcut, pageable);
    }

}
