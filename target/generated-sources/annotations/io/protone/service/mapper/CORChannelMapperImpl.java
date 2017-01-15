package io.protone.service.mapper;

import io.protone.domain.CORChannel;

import io.protone.domain.CORNetwork;

import io.protone.service.dto.CORChannelDTO;

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

public class CORChannelMapperImpl implements CORChannelMapper {

    @Override

    public CORChannelDTO cORChannelToCORChannelDTO(CORChannel cORChannel) {

        if ( cORChannel == null ) {

            return null;
        }

        CORChannelDTO cORChannelDTO = new CORChannelDTO();

        cORChannelDTO.setNetworkId( cORChannelNetworkId( cORChannel ) );

        cORChannelDTO.setId( cORChannel.getId() );

        cORChannelDTO.setShortcut( cORChannel.getShortcut() );

        cORChannelDTO.setName( cORChannel.getName() );

        cORChannelDTO.setDescription( cORChannel.getDescription() );

        return cORChannelDTO;
    }

    @Override

    public List<CORChannelDTO> cORChannelsToCORChannelDTOs(List<CORChannel> cORChannels) {

        if ( cORChannels == null ) {

            return null;
        }

        List<CORChannelDTO> list = new ArrayList<CORChannelDTO>();

        for ( CORChannel cORChannel : cORChannels ) {

            list.add( cORChannelToCORChannelDTO( cORChannel ) );
        }

        return list;
    }

    @Override

    public CORChannel cORChannelDTOToCORChannel(CORChannelDTO cORChannelDTO) {

        if ( cORChannelDTO == null ) {

            return null;
        }

        CORChannel cORChannel = new CORChannel();

        cORChannel.setNetwork( cORNetworkFromId( cORChannelDTO.getNetworkId() ) );

        cORChannel.setId( cORChannelDTO.getId() );

        cORChannel.setShortcut( cORChannelDTO.getShortcut() );

        cORChannel.setName( cORChannelDTO.getName() );

        cORChannel.setDescription( cORChannelDTO.getDescription() );

        return cORChannel;
    }

    @Override

    public List<CORChannel> cORChannelDTOsToCORChannels(List<CORChannelDTO> cORChannelDTOs) {

        if ( cORChannelDTOs == null ) {

            return null;
        }

        List<CORChannel> list = new ArrayList<CORChannel>();

        for ( CORChannelDTO cORChannelDTO : cORChannelDTOs ) {

            list.add( cORChannelDTOToCORChannel( cORChannelDTO ) );
        }

        return list;
    }

    private Long cORChannelNetworkId(CORChannel cORChannel) {

        if ( cORChannel == null ) {

            return null;
        }

        CORNetwork network = cORChannel.getNetwork();

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

