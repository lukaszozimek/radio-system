package io.protone.service.mapper;

import io.protone.domain.CORAddress;

import io.protone.domain.CORNetwork;

import io.protone.service.dto.CORAddressDTO;

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

public class CORAddressMapperImpl implements CORAddressMapper {

    @Override

    public CORAddressDTO cORAddressToCORAddressDTO(CORAddress cORAddress) {

        if ( cORAddress == null ) {

            return null;
        }

        CORAddressDTO cORAddressDTO = new CORAddressDTO();

        cORAddressDTO.setNetworkId( cORAddressNetworkId( cORAddress ) );

        cORAddressDTO.setId( cORAddress.getId() );

        cORAddressDTO.setStreet( cORAddress.getStreet() );

        cORAddressDTO.setNumber( cORAddress.getNumber() );

        cORAddressDTO.setPostalCode( cORAddress.getPostalCode() );

        cORAddressDTO.setCity( cORAddress.getCity() );

        cORAddressDTO.setCountry( cORAddress.getCountry() );

        return cORAddressDTO;
    }

    @Override

    public List<CORAddressDTO> cORAddressesToCORAddressDTOs(List<CORAddress> cORAddresses) {

        if ( cORAddresses == null ) {

            return null;
        }

        List<CORAddressDTO> list = new ArrayList<CORAddressDTO>();

        for ( CORAddress cORAddress : cORAddresses ) {

            list.add( cORAddressToCORAddressDTO( cORAddress ) );
        }

        return list;
    }

    @Override

    public CORAddress cORAddressDTOToCORAddress(CORAddressDTO cORAddressDTO) {

        if ( cORAddressDTO == null ) {

            return null;
        }

        CORAddress cORAddress = new CORAddress();

        cORAddress.setNetwork( cORNetworkFromId( cORAddressDTO.getNetworkId() ) );

        cORAddress.setId( cORAddressDTO.getId() );

        cORAddress.setStreet( cORAddressDTO.getStreet() );

        cORAddress.setNumber( cORAddressDTO.getNumber() );

        cORAddress.setPostalCode( cORAddressDTO.getPostalCode() );

        cORAddress.setCity( cORAddressDTO.getCity() );

        cORAddress.setCountry( cORAddressDTO.getCountry() );

        return cORAddress;
    }

    @Override

    public List<CORAddress> cORAddressDTOsToCORAddresses(List<CORAddressDTO> cORAddressDTOs) {

        if ( cORAddressDTOs == null ) {

            return null;
        }

        List<CORAddress> list = new ArrayList<CORAddress>();

        for ( CORAddressDTO cORAddressDTO : cORAddressDTOs ) {

            list.add( cORAddressDTOToCORAddress( cORAddressDTO ) );
        }

        return list;
    }

    private Long cORAddressNetworkId(CORAddress cORAddress) {

        if ( cORAddress == null ) {

            return null;
        }

        CORNetwork network = cORAddress.getNetwork();

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

