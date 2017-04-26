package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.TraCustomerPersonPT;
import io.protone.domain.CorContact;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPerson;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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

    @Inject
    private CustomCorNetworkMapper corNetworkMapper;


    public TraCustomerPersonPT createDTOObject(CorPerson person) {

        return new TraCustomerPersonPT().id(person.getId()).
            lastName(person.getLastName())
            .firstName(person.getFirstName())
            .description(person.getDescription())
            .contacts(corContactMapper.cORContactsToCorContactDTOs(person.getContacts()));
    }


    public CorPerson createPersonEntity(TraCustomerPersonPT personPT, CorNetwork network) {
        CorPerson corPerson = new CorPerson();
        corPerson.setId(personPT.getId());
        return corPerson.lastName(personPT.getLastName())
            .firstName(personPT.getFirstName())
            .description(personPT.getDescription())
            .contacts(personPT.getContacts().stream().map(coreContactPT -> corContactMapper.cORContactDTOToCorContact(coreContactPT.network(network.getId()))).collect(Collectors.toSet()))
            .network(network);
    }

    private List<CorContact> createContactEntities(TraCustomerPersonPT personPT) {
        return corContactMapper.cORContactDTOsToCorContacts(personPT.getContacts());
    }
}
