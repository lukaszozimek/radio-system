package io.protone.service.mapper;

import io.protone.domain.CORContact;

import io.protone.domain.CORNetwork;

import io.protone.service.dto.CORContactDTO;

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

public class CORContactMapperImpl implements CORContactMapper {

    @Override

    public CORContactDTO cORContactToCORContactDTO(CORContact cORContact) {

        if ( cORContact == null ) {

            return null;
        }

        CORContactDTO cORContactDTO = new CORContactDTO();

        cORContactDTO.setNetworkId( cORContactNetworkId( cORContact ) );

        cORContactDTO.setId( cORContact.getId() );

        cORContactDTO.setContact( cORContact.getContact() );

        cORContactDTO.setContactType( cORContact.getContactType() );

        return cORContactDTO;
    }

    @Override

    public List<CORContactDTO> cORContactsToCORContactDTOs(List<CORContact> cORContacts) {

        if ( cORContacts == null ) {

            return null;
        }

        List<CORContactDTO> list = new ArrayList<CORContactDTO>();

        for ( CORContact cORContact : cORContacts ) {

            list.add( cORContactToCORContactDTO( cORContact ) );
        }

        return list;
    }

    @Override

    public CORContact cORContactDTOToCORContact(CORContactDTO cORContactDTO) {

        if ( cORContactDTO == null ) {

            return null;
        }

        CORContact cORContact = new CORContact();

        cORContact.setNetwork( cORNetworkFromId( cORContactDTO.getNetworkId() ) );

        cORContact.setId( cORContactDTO.getId() );

        cORContact.setContact( cORContactDTO.getContact() );

        cORContact.setContactType( cORContactDTO.getContactType() );

        return cORContact;
    }

    @Override

    public List<CORContact> cORContactDTOsToCORContacts(List<CORContactDTO> cORContactDTOs) {

        if ( cORContactDTOs == null ) {

            return null;
        }

        List<CORContact> list = new ArrayList<CORContact>();

        for ( CORContactDTO cORContactDTO : cORContactDTOs ) {

            list.add( cORContactDTOToCORContact( cORContactDTO ) );
        }

        return list;
    }

    private Long cORContactNetworkId(CORContact cORContact) {

        if ( cORContact == null ) {

            return null;
        }

        CORNetwork network = cORContact.getNetwork();

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

