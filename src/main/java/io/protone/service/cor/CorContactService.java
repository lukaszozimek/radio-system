package io.protone.service.cor;

import io.protone.domain.CorContact;
import io.protone.repository.cor.CorContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Created by lukaszozimek on 15/06/2017.
 */
@Service
public class CorContactService {
    private final Logger log = LoggerFactory.getLogger(CorContactService.class);

    @Inject
    private CorContactRepository corContactRepository;

    @Transactional
    public List<CorContact> saveCorContact(Set<CorContact> contacts) {
        log.debug("Persisting CorContact: {}",contacts);

        return corContactRepository.save(contacts);
    }

    @Transactional
    public CorContact saveCorContact(CorContact corContact) {
        return corContactRepository.save(corContact);
    }
}
