package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.CRMLeadSource;

import io.protone.service.dto.CRMLeadSourceDTO;

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

public class CRMLeadSourceMapperImpl implements CRMLeadSourceMapper {

    @Override

    public CRMLeadSourceDTO cRMLeadSourceToCRMLeadSourceDTO(CRMLeadSource cRMLeadSource) {

        if ( cRMLeadSource == null ) {

            return null;
        }

        CRMLeadSourceDTO cRMLeadSourceDTO = new CRMLeadSourceDTO();

        cRMLeadSourceDTO.setNetworkId( cRMLeadSourceNetworkId( cRMLeadSource ) );

        cRMLeadSourceDTO.setId( cRMLeadSource.getId() );

        cRMLeadSourceDTO.setName( cRMLeadSource.getName() );

        return cRMLeadSourceDTO;
    }

    @Override

    public List<CRMLeadSourceDTO> cRMLeadSourcesToCRMLeadSourceDTOs(List<CRMLeadSource> cRMLeadSources) {

        if ( cRMLeadSources == null ) {

            return null;
        }

        List<CRMLeadSourceDTO> list = new ArrayList<CRMLeadSourceDTO>();

        for ( CRMLeadSource cRMLeadSource : cRMLeadSources ) {

            list.add( cRMLeadSourceToCRMLeadSourceDTO( cRMLeadSource ) );
        }

        return list;
    }

    @Override

    public CRMLeadSource cRMLeadSourceDTOToCRMLeadSource(CRMLeadSourceDTO cRMLeadSourceDTO) {

        if ( cRMLeadSourceDTO == null ) {

            return null;
        }

        CRMLeadSource cRMLeadSource = new CRMLeadSource();

        cRMLeadSource.setNetwork( cORNetworkFromId( cRMLeadSourceDTO.getNetworkId() ) );

        cRMLeadSource.setId( cRMLeadSourceDTO.getId() );

        cRMLeadSource.setName( cRMLeadSourceDTO.getName() );

        return cRMLeadSource;
    }

    @Override

    public List<CRMLeadSource> cRMLeadSourceDTOsToCRMLeadSources(List<CRMLeadSourceDTO> cRMLeadSourceDTOs) {

        if ( cRMLeadSourceDTOs == null ) {

            return null;
        }

        List<CRMLeadSource> list = new ArrayList<CRMLeadSource>();

        for ( CRMLeadSourceDTO cRMLeadSourceDTO : cRMLeadSourceDTOs ) {

            list.add( cRMLeadSourceDTOToCRMLeadSource( cRMLeadSourceDTO ) );
        }

        return list;
    }

    private Long cRMLeadSourceNetworkId(CRMLeadSource cRMLeadSource) {

        if ( cRMLeadSource == null ) {

            return null;
        }

        CORNetwork network = cRMLeadSource.getNetwork();

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

