package io.protone.custom.service;

import io.protone.domain.CorContact;
import io.protone.domain.CorPerson;
import io.protone.repository.custom.CustomCorContactRepository;
import io.protone.repository.custom.CustomCorPersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 29.04.2017.
 */
@Service
public class CorPersonService {
    private final Logger log = LoggerFactory.getLogger(CorPersonService.class);

    @Inject
    private CustomCorContactRepository corContactRepository;

    @Inject
    private CustomCorPersonRepository personRepository;

    public CorPerson savePerson(CorPerson corPerson) {
        log.debug("Persisting CorPerson: {}", corPerson);
        CorPerson person = personRepository.save(corPerson);
        if (corPerson.getContacts() != null) {
            log.debug("Persisting CorContact: {}", corPerson.getContacts());
            List<CorContact> corContact = corContactRepository.save(corPerson.getContacts());
            log.debug("Persisting CorPerson Contacts");
            corContact.stream().forEach(corContact1 -> corContactRepository.save(corContact1.person(person)));
            person.contacts(corContact.stream().collect(Collectors.toSet()));
        }
        return person;
    }

}
