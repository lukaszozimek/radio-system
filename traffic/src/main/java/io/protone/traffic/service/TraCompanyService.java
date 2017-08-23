package io.protone.traffic.service;


import io.protone.core.service.CorAddressService;
import io.protone.traffic.domain.TraCompany;
import io.protone.traffic.repository.TraCompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TraCompanyService {

    private final Logger log = LoggerFactory.getLogger(TraCompanyService.class);

    @Inject
    private TraCompanyRepository traCompanyRepository;

    @Inject
    private CorAddressService corAddressService;


    public Slice<TraCompany> getAllCompany(String corNetwork, Pageable pageable) {
        return traCompanyRepository.findSliceByNetwork_Shortcut(corNetwork, pageable);
    }

    public TraCompany getCompany(Long id, String corNetwork) {
        return traCompanyRepository.findOneByIdAndNetwork_Shortcut(id, corNetwork);
    }


    public TraCompany saveCompany(TraCompany traCompany) {
        log.debug("Persisting TraCompany: {}", traCompany);
        if(traCompany.getAddres() !=null){
            traCompany.setAddres(corAddressService.saveCoreAdress(traCompany.getAddres()));
        }
        return traCompanyRepository.save(traCompany);
    }

    public void deleteCompany(Long id, String corNetwork) {
        traCompanyRepository.deleteByIdAndNetwork_Shortcut(id, corNetwork);
    }


}
