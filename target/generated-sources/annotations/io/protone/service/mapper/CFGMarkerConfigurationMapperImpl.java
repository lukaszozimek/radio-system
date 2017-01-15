package io.protone.service.mapper;

import io.protone.domain.CFGMarkerConfiguration;

import io.protone.domain.CORNetwork;

import io.protone.service.dto.CFGMarkerConfigurationDTO;

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

public class CFGMarkerConfigurationMapperImpl implements CFGMarkerConfigurationMapper {

    @Override

    public CFGMarkerConfigurationDTO cFGMarkerConfigurationToCFGMarkerConfigurationDTO(CFGMarkerConfiguration cFGMarkerConfiguration) {

        if ( cFGMarkerConfiguration == null ) {

            return null;
        }

        CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO = new CFGMarkerConfigurationDTO();

        cFGMarkerConfigurationDTO.setNetworkId( cFGMarkerConfigurationNetworkId( cFGMarkerConfiguration ) );

        cFGMarkerConfigurationDTO.setId( cFGMarkerConfiguration.getId() );

        cFGMarkerConfigurationDTO.setName( cFGMarkerConfiguration.getName() );

        cFGMarkerConfigurationDTO.setDisplayName( cFGMarkerConfiguration.getDisplayName() );

        cFGMarkerConfigurationDTO.setColor( cFGMarkerConfiguration.getColor() );

        cFGMarkerConfigurationDTO.setKeyboardShortcut( cFGMarkerConfiguration.getKeyboardShortcut() );

        cFGMarkerConfigurationDTO.setType( cFGMarkerConfiguration.getType() );

        return cFGMarkerConfigurationDTO;
    }

    @Override

    public List<CFGMarkerConfigurationDTO> cFGMarkerConfigurationsToCFGMarkerConfigurationDTOs(List<CFGMarkerConfiguration> cFGMarkerConfigurations) {

        if ( cFGMarkerConfigurations == null ) {

            return null;
        }

        List<CFGMarkerConfigurationDTO> list = new ArrayList<CFGMarkerConfigurationDTO>();

        for ( CFGMarkerConfiguration cFGMarkerConfiguration : cFGMarkerConfigurations ) {

            list.add( cFGMarkerConfigurationToCFGMarkerConfigurationDTO( cFGMarkerConfiguration ) );
        }

        return list;
    }

    @Override

    public CFGMarkerConfiguration cFGMarkerConfigurationDTOToCFGMarkerConfiguration(CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO) {

        if ( cFGMarkerConfigurationDTO == null ) {

            return null;
        }

        CFGMarkerConfiguration cFGMarkerConfiguration = new CFGMarkerConfiguration();

        cFGMarkerConfiguration.setNetwork( cORNetworkFromId( cFGMarkerConfigurationDTO.getNetworkId() ) );

        cFGMarkerConfiguration.setId( cFGMarkerConfigurationDTO.getId() );

        cFGMarkerConfiguration.setName( cFGMarkerConfigurationDTO.getName() );

        cFGMarkerConfiguration.setDisplayName( cFGMarkerConfigurationDTO.getDisplayName() );

        cFGMarkerConfiguration.setColor( cFGMarkerConfigurationDTO.getColor() );

        cFGMarkerConfiguration.setKeyboardShortcut( cFGMarkerConfigurationDTO.getKeyboardShortcut() );

        cFGMarkerConfiguration.setType( cFGMarkerConfigurationDTO.getType() );

        return cFGMarkerConfiguration;
    }

    @Override

    public List<CFGMarkerConfiguration> cFGMarkerConfigurationDTOsToCFGMarkerConfigurations(List<CFGMarkerConfigurationDTO> cFGMarkerConfigurationDTOs) {

        if ( cFGMarkerConfigurationDTOs == null ) {

            return null;
        }

        List<CFGMarkerConfiguration> list = new ArrayList<CFGMarkerConfiguration>();

        for ( CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO : cFGMarkerConfigurationDTOs ) {

            list.add( cFGMarkerConfigurationDTOToCFGMarkerConfiguration( cFGMarkerConfigurationDTO ) );
        }

        return list;
    }

    private Long cFGMarkerConfigurationNetworkId(CFGMarkerConfiguration cFGMarkerConfiguration) {

        if ( cFGMarkerConfiguration == null ) {

            return null;
        }

        CORNetwork network = cFGMarkerConfiguration.getNetwork();

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

