package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.TraCustomerPersonPT;
import io.protone.domain.CorContact;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPerson;
import io.protone.service.mapper.CorContactMapper;
import io.protone.service.mapper.CorNetworkMapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Service
public class CustomTRAPersonMapper {

    @Inject
    private CorContactMapper corContactMapper;

    @Inject
    private CorNetworkMapper corNetworkMapper;


    public TraCustomerPersonPT createDTOObject(CorPerson person) {

        return new TraCustomerPersonPT().id(person.getId()).
            lastName(person.getLastName())
            .firstName(person.getFirstName())
            .description(person.getDescription())
            .contacts(corContactMapper.DBs2DTOs(person.getContacts()));
    }


    public CorPerson createPersonEntity(TraCustomerPersonPT personPT, CorNetwork network) {
        CorPerson corPerson = new CorPerson();
        corPerson.setId(personPT.getId());
        return corPerson.lastName(personPT.getLastName())
            .firstName(personPT.getFirstName())
            .description(personPT.getDescription())
            .contacts(personPT.getContacts().stream().map(coreContactPT -> {
                CorContact corContact = corContactMapper.DTO2DB(coreContactPT);
                corContact.network(network);
                return corContact;
            }).collect(toSet()))
            .network(network);
    }

    private List<CorContact> createContactEntities(TraCustomerPersonPT personPT) {
        return corContactMapper.DTOs2DBs(personPT.getContacts());
    }
}
