package io.protone.custom.service;

import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.service.mapper.CustomTraInvoiceMapper;
import io.protone.domain.*;
import io.protone.repository.TraInvoiceRepository;
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
public class TraInvoiceService {

    @Inject
    private TraInvoiceRepository traInvoiceRepository;

    @Inject
    private CustomTraInvoiceMapper customTraInvoiceMapper;


    public List<TraInvoicePT> getAllInvoice(CorNetwork corNetwork) {
        return traInvoiceRepository.findByNetwork(corNetwork).stream().map(traInvoicePTS -> customTraInvoiceMapper.createDTOFromEnity(traInvoicePTS)).collect(toList());
    }

    public TraInvoicePT saveInvoice(TraInvoicePT traInvoicePT, CorNetwork corNetwork) {
        TraInvoice invoice = customTraInvoiceMapper.createEntityFromDTO(traInvoicePT, corNetwork);
        invoice = traInvoiceRepository.save(invoice);
        return customTraInvoiceMapper.createDTOFromEnity(invoice);
    }

    public void deleteInvoice(Long id, CorNetwork corNetwork) {
        traInvoiceRepository.delete(id);
    }

    public TraInvoicePT getInvoice(Long id, CorNetwork corNetwork) {
        TraInvoice traInvoice = traInvoiceRepository.findOne(id);
        return customTraInvoiceMapper.createDTOFromEnity(traInvoice);
    }


    public List<TraInvoicePT> getCustomerInvoice(String customerShortcut, CorNetwork corNetwork) {
        return null;
    }

}
