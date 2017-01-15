package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.CORPerson;

import io.protone.service.dto.CORPersonDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-01-15T01:15:41+0100",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_71 (Oracle Corporation)"

)

@Component

public class CORPersonMapperImpl implements CORPersonMapper {

    @Override

    public CORPersonDTO cORPersonToCORPersonDTO(CORPerson cORPerson) {

        if ( cORPerson == null ) {

            return null;
        }

        CORPersonDTO cORPersonDTO = new CORPersonDTO();

        cORPersonDTO.setNetworkId( cORPersonNetworkId( cORPerson ) );

        cORPersonDTO.setId( cORPerson.getId() );

        cORPersonDTO.setFirstName( cORPerson.getFirstName() );

        cORPersonDTO.setLastName( cORPerson.getLastName() );

        cORPersonDTO.setDescription( cORPerson.getDescription() );

        return cORPersonDTO;
    }

    @Override

    public List<CORPersonDTO> cORPeopleToCORPersonDTOs(List<CORPerson> cORPeople) {

        if ( cORPeople == null ) {

            return null;
        }

        List<CORPersonDTO> list = new ArrayList<CORPersonDTO>();

        for ( CORPerson cORPerson : cORPeople ) {

            list.add( cORPersonToCORPersonDTO( cORPerson ) );
        }

        return list;
    }

    @Override

    public CORPerson cORPersonDTOToCORPerson(CORPersonDTO cORPersonDTO) {

        if ( cORPersonDTO == null ) {

            return null;
        }

        CORPerson cORPerson = new CORPerson();

        cORPerson.setNetwork( cORNetworkFromId( cORPersonDTO.getNetworkId() ) );

        cORPerson.setId( cORPersonDTO.getId() );

        cORPerson.setFirstName( cORPersonDTO.getFirstName() );

        cORPerson.setLastName( cORPersonDTO.getLastName() );

        cORPerson.setDescription( cORPersonDTO.getDescription() );

        return cORPerson;
    }

    @Override

    public List<CORPerson> cORPersonDTOsToCORPeople(List<CORPersonDTO> cORPersonDTOs) {

        if ( cORPersonDTOs == null ) {

            return null;
        }

        List<CORPerson> list = new ArrayList<CORPerson>();

        for ( CORPersonDTO cORPersonDTO : cORPersonDTOs ) {

            list.add( cORPersonDTOToCORPerson( cORPersonDTO ) );
        }

        return list;
    }

    private Long cORPersonNetworkId(CORPerson cORPerson) {

        if ( cORPerson == null ) {

            return null;
        }

        CORNetwork network = cORPerson.getNetwork();

        if ( network == null ) {

            return null;
        }

        Long id = network.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

