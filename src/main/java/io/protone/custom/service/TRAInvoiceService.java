package io.protone.custom.service;

import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.service.mapper.CustomTRAInvoiceMapper;
import io.protone.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TRAInvoiceService {

    @Inject
    private TRAInvoiceRepository traInvoiceRepository;

    @Inject
    private CustomTRAInvoiceMapper customTRAInvoiceMapper;


    public List<TraInvoicePT> getAllInvoice(CorNetwork corNetwork) {
        return traInvoiceRepository.findByNetwork(corNetwork).stream().map(traInvoicePTS -> customTRAInvoiceMapper.createDTOFromEnity(traInvoicePTS)).collect(toList());
    }

    public TraInvoicePT saveInvoice(TraInvoicePT traInvoicePT, CorNetwork corNetwork) {
        TRAInvoice invoice = customTRAInvoiceMapper.createEntityFromDTO(traInvoicePT, corNetwork);
        invoice = traInvoiceRepository.save(invoice);
        return customTRAInvoiceMapper.createDTOFromEnity(invoice);
    }

    public void deleteInvoice(Long id, CorNetwork corNetwork) {
        traInvoiceRepository.delete(id);
    }

    public TraInvoicePT getInvoice(Long id, CorNetwork corNetwork) {
        TRAInvoice traInvoice = traInvoiceRepository.findOne(id);
        return customTRAInvoiceMapper.createDTOFromEnity(traInvoice);
    }


    public List<TraInvoicePT> getCustomerInvoice(String customerShortcut, CorNetwork corNetwork) {
        return null;
    }

}
