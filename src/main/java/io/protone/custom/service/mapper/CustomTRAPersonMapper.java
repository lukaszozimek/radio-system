package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreContactPT;
import io.protone.custom.service.dto.TraCustomerPersonPT;
import io.protone.domain.CorContact;
import io.protone.domain.CorPerson;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Service
public class CustomTRAPersonMapper {
    @Inject
    private CustomCorContactMapper corContactMapper;

    public Map<CorPerson, List<CorContact>> createMapPersonToContact(List<TraCustomerPersonPT> traCustomerPersonPT) {
        Map<CorPerson, List<CorContact>> corPersonListMap = new HashMap<>();
        traCustomerPersonPT.stream().forEach(traCustomerPerson -> {
            corPersonListMap.put(createPersonEntity(traCustomerPerson), createContactEntities(traCustomerPerson));
        });
        return corPersonListMap;
    }

    public TraCustomerPersonPT createDTOObject(CorPerson person) {

        return new TraCustomerPersonPT().id(person.getId()).
            lastName(person.getLastName())
            .firstName(person.getFirstName())
            .description(person.getDescription())
            .contacts(corContactMapper.cORContactsToCorContactDTOs(person.getPersonContacts()));
    }


    public CorPerson createPersonEntity(TraCustomerPersonPT personPT) {
        CorPerson corPerson = new CorPerson();
        corPerson.setId(personPT.getId());
        return corPerson.lastName(personPT.getLastName())
            .firstName(personPT.getFirstName())
            .description(personPT.getDescription())
            .personContacts(personPT.getContacts().stream().map(coreContactPT -> corContactMapper.cORContactDTOToCorContact(coreContactPT)).collect(Collectors.toSet()));
    }

    private List<CorContact> createContactEntities(TraCustomerPersonPT personPT) {
        return corContactMapper.cORContactDTOsToCorContacts(personPT.getContacts());
    }
}
