package io.protone.traffic.service;


import io.protone.traffic.domain.TraCompany;
import io.protone.traffic.repository.TraCompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TraCompanyService {

    private final Logger log = LoggerFactory.getLogger(TraCompanyService.class);

    @Inject
    private TraCompanyRepository traCompanyRepository;


    public List<TraCompany> getAllCompany(String corNetwork, Pageable pageable) {
        return traCompanyRepository.findAllByNetwork_Shortcut(corNetwork, pageable);
    }

    public TraCompany getCompany(Long id, String corNetwork) {
        return traCompanyRepository.findOneByIdAndNetwork_Shortcut(id, corNetwork);
    }


    public TraCompany saveCompany(TraCompany traInvoice) {
        log.debug("Persisting TraCompany: {}", traInvoice);
        return traCompanyRepository.save(traInvoice);
    }

    public void deleteCompany(Long id, String corNetwork) {
        traCompanyRepository.deleteByIdAndNetwork_Shortcut(id, corNetwork);
    }


}
