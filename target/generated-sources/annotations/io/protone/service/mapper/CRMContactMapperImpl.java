package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.CRMContact;

import io.protone.service.dto.CRMContactDTO;

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

public class CRMContactMapperImpl implements CRMContactMapper {

    @Override

    public CRMContactDTO cRMContactToCRMContactDTO(CRMContact cRMContact) {

        if ( cRMContact == null ) {

            return null;
        }

        CRMContactDTO cRMContactDTO = new CRMContactDTO();

        cRMContactDTO.setNetworkId( cRMContactNetworkId( cRMContact ) );

        cRMContactDTO.setId( cRMContact.getId() );

        cRMContactDTO.setShortName( cRMContact.getShortName() );

        cRMContactDTO.setExternalId1( cRMContact.getExternalId1() );

        cRMContactDTO.setExternalId2( cRMContact.getExternalId2() );

        cRMContactDTO.setName( cRMContact.getName() );

        cRMContactDTO.setPaymentDelay( cRMContact.getPaymentDelay() );

        cRMContactDTO.setVatNumber( cRMContact.getVatNumber() );

        return cRMContactDTO;
    }

    @Override

    public List<CRMContactDTO> cRMContactsToCRMContactDTOs(List<CRMContact> cRMContacts) {

        if ( cRMContacts == null ) {

            return null;
        }

        List<CRMContactDTO> list = new ArrayList<CRMContactDTO>();

        for ( CRMContact cRMContact : cRMContacts ) {

            list.add( cRMContactToCRMContactDTO( cRMContact ) );
        }

        return list;
    }

    @Override

    public CRMContact cRMContactDTOToCRMContact(CRMContactDTO cRMContactDTO) {

        if ( cRMContactDTO == null ) {

            return null;
        }

        CRMContact cRMContact = new CRMContact();

        cRMContact.setNetwork( cORNetworkFromId( cRMContactDTO.getNetworkId() ) );

        cRMContact.setId( cRMContactDTO.getId() );

        cRMContact.setShortName( cRMContactDTO.getShortName() );

        cRMContact.setExternalId1( cRMContactDTO.getExternalId1() );

        cRMContact.setExternalId2( cRMContactDTO.getExternalId2() );

        cRMContact.setName( cRMContactDTO.getName() );

        cRMContact.setPaymentDelay( cRMContactDTO.getPaymentDelay() );

        cRMContact.setVatNumber( cRMContactDTO.getVatNumber() );

        return cRMContact;
    }

    @Override

    public List<CRMContact> cRMContactDTOsToCRMContacts(List<CRMContactDTO> cRMContactDTOs) {

        if ( cRMContactDTOs == null ) {

            return null;
        }

        List<CRMContact> list = new ArrayList<CRMContact>();

        for ( CRMContactDTO cRMContactDTO : cRMContactDTOs ) {

            list.add( cRMContactDTOToCRMContact( cRMContactDTO ) );
        }

        return list;
    }

    private Long cRMContactNetworkId(CRMContact cRMContact) {

        if ( cRMContact == null ) {

            return null;
        }

        CORNetwork network = cRMContact.getNetwork();

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

