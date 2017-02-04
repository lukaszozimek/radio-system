package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreContactPT;
import io.protone.custom.service.dto.TraCustomerPersonPT;
import io.protone.domain.CORContact;
import io.protone.domain.CORPerson;
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
    private CustomCORContactMapper corContactMapper;

    public Map<CORPerson, List<CORContact>> createMapPersonToContact(List<TraCustomerPersonPT> traCustomerPersonPT) {
        Map<CORPerson, List<CORContact>> corPersonListMap = new HashMap<>();
        traCustomerPersonPT.stream().forEach(traCustomerPerson -> {
            corPersonListMap.put(createPersonEntity(traCustomerPerson), createContactEntities(traCustomerPerson));
        });
        return corPersonListMap;
    }

    public TraCustomerPersonPT createDTOObject(CORPerson person) {

        return new TraCustomerPersonPT().id(person.getId()).
            lastName(person.getLastName())
            .firstName(person.getFirstName())
            .description(person.getDescription())
            .contacts(corContactMapper.cORContactsToCORContactDTOs(person.getPersonContacts()));
    }


    public CORPerson createPersonEntity(TraCustomerPersonPT personPT) {
        CORPerson corPerson = new CORPerson();
        corPerson.setId(personPT.getId());
        return corPerson.lastName(personPT.getLastName())
            .firstName(personPT.getFirstName())
            .description(personPT.getDescription())
            .personContacts(personPT.getContacts().stream().map(coreContactPT -> corContactMapper.cORContactDTOToCORContact(coreContactPT)).collect(Collectors.toSet()));
    }

    private List<CORContact> createContactEntities(TraCustomerPersonPT personPT) {
        return corContactMapper.cORContactDTOsToCORContacts(personPT.getContacts());
    }
}
