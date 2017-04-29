package io.protone.custom.service;

import io.protone.web.rest.mapper.TraInvoiceMapper;
import io.protone.repository.custom.CustomTraInvoiceRepository;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraInvoice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TraInvoiceService {

    private final Logger log = LoggerFactory.getLogger(TraInvoiceService.class);

    @Inject
    private CustomTraInvoiceRepository traInvoiceRepository;


    public List<TraInvoice> getAllInvoice(String corNetwork, Pageable pageable) {
        return traInvoiceRepository.findAllByNetwork_Shortcut(corNetwork, pageable);
    }

    public TraInvoice saveInvoice(TraInvoice traInvoice) {
        log.debug("Persisting TraInvoice: {}", traInvoice);
        return traInvoiceRepository.save(traInvoice);
    }

    public void deleteInvoice(Long id, String corNetwork) {
        traInvoiceRepository.deleteByIdAndNetwork_Shortcut(id, corNetwork);
    }

    public TraInvoice getInvoice(Long id, String corNetwork) {
        return traInvoiceRepository.findByIdAndNetwork_Shortcut(id, corNetwork);
    }

    public List<TraInvoice> getCustomerInvoice(String customerShortcut, String corNetwork, Pageable pageable) {
        return traInvoiceRepository.findAllByCustomer_ShortAndNetwork_Shortcut(customerShortcut, corNetwork, pageable);
    }

}
