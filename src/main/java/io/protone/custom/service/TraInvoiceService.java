package io.protone.custom.service;

import io.protone.web.rest.mapper.TraInvoiceMapper;
import io.protone.repository.custom.CustomTraInvoiceRepository;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraInvoice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Inject
    private TraInvoiceMapper customTraInvoiceMapper;

    public List<TraInvoicePT> getAllInvoice(CorNetwork corNetwork) {
        return traInvoiceRepository.findByNetwork(corNetwork).stream().map(traInvoicePTS -> customTraInvoiceMapper.DB2DTO(traInvoicePTS)).collect(toList());
    }

    public TraInvoicePT saveInvoice(TraInvoicePT traInvoicePT, CorNetwork corNetwork) {
        TraInvoice invoice = customTraInvoiceMapper.DTO2DB(traInvoicePT);
        invoice.setNetwork(corNetwork);
        log.debug("Persisting TraInvoice: {}", invoice);
        invoice = traInvoiceRepository.save(invoice);
        return customTraInvoiceMapper.DB2DTO(invoice);
    }

    public void deleteInvoice(Long id, CorNetwork corNetwork) {

        traInvoiceRepository.delete(id);
    }

    public TraInvoicePT getInvoice(Long id, CorNetwork corNetwork) {
        TraInvoice traInvoice = traInvoiceRepository.findOne(id);
        return customTraInvoiceMapper.DB2DTO(traInvoice);
    }

    public List<TraInvoicePT> getCustomerInvoice(String customerShortcut, CorNetwork corNetwork) {
        return null;
    }

}
