package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.service.dto.CORNetworkDTO;

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

public class CORNetworkMapperImpl implements CORNetworkMapper {

    @Override

    public CORNetworkDTO cORNetworkToCORNetworkDTO(CORNetwork cORNetwork) {

        if ( cORNetwork == null ) {

            return null;
        }

        CORNetworkDTO cORNetworkDTO = new CORNetworkDTO();

        cORNetworkDTO.setId( cORNetwork.getId() );

        cORNetworkDTO.setShortcut( cORNetwork.getShortcut() );

        cORNetworkDTO.setName( cORNetwork.getName() );

        cORNetworkDTO.setDescription( cORNetwork.getDescription() );

        return cORNetworkDTO;
    }

    @Override

    public List<CORNetworkDTO> cORNetworksToCORNetworkDTOs(List<CORNetwork> cORNetworks) {

        if ( cORNetworks == null ) {

            return null;
        }

        List<CORNetworkDTO> list = new ArrayList<CORNetworkDTO>();

        for ( CORNetwork cORNetwork : cORNetworks ) {

            list.add( cORNetworkToCORNetworkDTO( cORNetwork ) );
        }

        return list;
    }

    @Override

    public CORNetwork cORNetworkDTOToCORNetwork(CORNetworkDTO cORNetworkDTO) {

        if ( cORNetworkDTO == null ) {

            return null;
        }

        CORNetwork cORNetwork = new CORNetwork();

        cORNetwork.setId( cORNetworkDTO.getId() );

        cORNetwork.setShortcut( cORNetworkDTO.getShortcut() );

        cORNetwork.setName( cORNetworkDTO.getName() );

        cORNetwork.setDescription( cORNetworkDTO.getDescription() );

        return cORNetwork;
    }

    @Override

    public List<CORNetwork> cORNetworkDTOsToCORNetworks(List<CORNetworkDTO> cORNetworkDTOs) {

        if ( cORNetworkDTOs == null ) {

            return null;
        }

        List<CORNetwork> list = new ArrayList<CORNetwork>();

        for ( CORNetworkDTO cORNetworkDTO : cORNetworkDTOs ) {

            list.add( cORNetworkDTOToCORNetwork( cORNetworkDTO ) );
        }

        return list;
    }
}

