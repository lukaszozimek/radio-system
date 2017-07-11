package io.protone.core.service;

import io.protone.core.domain.CorContact;
import io.protone.core.domain.CorPerson;
import io.protone.core.repository.CorPersonRepository;
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
    private CorContactService corContactService;

    @Inject
    private CorPersonRepository personRepository;

    public CorPerson savePerson(CorPerson corPerson) {
        log.debug("Persisting CorPerson: {}", corPerson);
        CorPerson person = personRepository.save(corPerson);
        if (corPerson.getContacts() != null) {
            List<CorContact> corContact = corContactService.saveCorContact(corPerson.getContacts());
            log.debug("Persisting CorPerson Contacts");
            corContact.stream().forEach(corContact1 -> corContactService.saveCorContact(corContact1.person(person)));
            person.contacts(corContact.stream().collect(Collectors.toSet()));
        }
        return person;
    }

}
