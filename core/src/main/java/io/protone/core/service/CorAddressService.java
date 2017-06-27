package io.protone.core.service;

import io.protone.core.repository.CorAddressRepository;
import io.protone.domain.CorAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 15/06/2017.
 */
@Service
public class CorAddressService {

    private final Logger log = LoggerFactory.getLogger(CorAddressService.class);

    @Inject
    private CorAddressRepository corAddressRepository;

    @Transactional
    public CorAddress saveCoreAdress(CorAddress corAddress) {
        log.debug("Persisting CorAddress: {}", corAddress);
        return corAddressRepository.saveAndFlush(corAddress);
    }
}
