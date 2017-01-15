package io.protone.service.mapper;

import io.protone.domain.CORChannel;

import io.protone.domain.SCHBlock;

import io.protone.domain.SCHTemplate;

import io.protone.service.dto.SCHBlockDTO;

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

public class SCHBlockMapperImpl implements SCHBlockMapper {

    @Override

    public SCHBlockDTO sCHBlockToSCHBlockDTO(SCHBlock sCHBlock) {

        if ( sCHBlock == null ) {

            return null;
        }

        SCHBlockDTO sCHBlockDTO = new SCHBlockDTO();

        sCHBlockDTO.setParentBlockId( sCHBlockParentBlockId( sCHBlock ) );

        sCHBlockDTO.setTemplateId( sCHBlockTemplateId( sCHBlock ) );

        sCHBlockDTO.setChannelId( sCHBlockChannelId( sCHBlock ) );

        sCHBlockDTO.setId( sCHBlock.getId() );

        sCHBlockDTO.setSeq( sCHBlock.getSeq() );

        sCHBlockDTO.setName( sCHBlock.getName() );

        sCHBlockDTO.setType( sCHBlock.getType() );

        sCHBlockDTO.setStartTime( sCHBlock.getStartTime() );

        sCHBlockDTO.setStartType( sCHBlock.getStartType() );

        sCHBlockDTO.setRelativeDelay( sCHBlock.getRelativeDelay() );

        sCHBlockDTO.setEndTime( sCHBlock.getEndTime() );

        sCHBlockDTO.setLength( sCHBlock.getLength() );

        sCHBlockDTO.setDimYear( sCHBlock.getDimYear() );

        sCHBlockDTO.setDimMonth( sCHBlock.getDimMonth() );

        sCHBlockDTO.setDimDay( sCHBlock.getDimDay() );

        sCHBlockDTO.setDimHour( sCHBlock.getDimHour() );

        sCHBlockDTO.setDimMinute( sCHBlock.getDimMinute() );

        sCHBlockDTO.setDimSecond( sCHBlock.getDimSecond() );

        return sCHBlockDTO;
    }

    @Override

    public List<SCHBlockDTO> sCHBlocksToSCHBlockDTOs(List<SCHBlock> sCHBlocks) {

        if ( sCHBlocks == null ) {

            return null;
        }

        List<SCHBlockDTO> list = new ArrayList<SCHBlockDTO>();

        for ( SCHBlock sCHBlock : sCHBlocks ) {

            list.add( sCHBlockToSCHBlockDTO( sCHBlock ) );
        }

        return list;
    }

    @Override

    public SCHBlock sCHBlockDTOToSCHBlock(SCHBlockDTO sCHBlockDTO) {

        if ( sCHBlockDTO == null ) {

            return null;
        }

        SCHBlock sCHBlock = new SCHBlock();

        sCHBlock.setTemplate( sCHTemplateFromId( sCHBlockDTO.getTemplateId() ) );

        sCHBlock.setChannel( cORChannelFromId( sCHBlockDTO.getChannelId() ) );

        sCHBlock.setParentBlock( sCHBlockFromId( sCHBlockDTO.getParentBlockId() ) );

        sCHBlock.setId( sCHBlockDTO.getId() );

        sCHBlock.setSeq( sCHBlockDTO.getSeq() );

        sCHBlock.setName( sCHBlockDTO.getName() );

        sCHBlock.setType( sCHBlockDTO.getType() );

        sCHBlock.setStartTime( sCHBlockDTO.getStartTime() );

        sCHBlock.setStartType( sCHBlockDTO.getStartType() );

        sCHBlock.setRelativeDelay( sCHBlockDTO.getRelativeDelay() );

        sCHBlock.setEndTime( sCHBlockDTO.getEndTime() );

        sCHBlock.setLength( sCHBlockDTO.getLength() );

        sCHBlock.setDimYear( sCHBlockDTO.getDimYear() );

        sCHBlock.setDimMonth( sCHBlockDTO.getDimMonth() );

        sCHBlock.setDimDay( sCHBlockDTO.getDimDay() );

        sCHBlock.setDimHour( sCHBlockDTO.getDimHour() );

        sCHBlock.setDimMinute( sCHBlockDTO.getDimMinute() );

        sCHBlock.setDimSecond( sCHBlockDTO.getDimSecond() );

        return sCHBlock;
    }

    @Override

    public List<SCHBlock> sCHBlockDTOsToSCHBlocks(List<SCHBlockDTO> sCHBlockDTOs) {

        if ( sCHBlockDTOs == null ) {

            return null;
        }

        List<SCHBlock> list = new ArrayList<SCHBlock>();

        for ( SCHBlockDTO sCHBlockDTO : sCHBlockDTOs ) {

            list.add( sCHBlockDTOToSCHBlock( sCHBlockDTO ) );
        }

        return list;
    }

    private Long sCHBlockParentBlockId(SCHBlock sCHBlock) {

        if ( sCHBlock == null ) {

            return null;
        }

        SCHBlock parentBlock = sCHBlock.getParentBlock();

        if ( parentBlock == null ) {

            return null;
        }

        Long id = parentBlock.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long sCHBlockTemplateId(SCHBlock sCHBlock) {

        if ( sCHBlock == null ) {

            return null;
        }

        SCHTemplate template = sCHBlock.getTemplate();

        if ( template == null ) {

            return null;
        }

        Long id = template.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long sCHBlockChannelId(SCHBlock sCHBlock) {

        if ( sCHBlock == null ) {

            return null;
        }

        CORChannel channel = sCHBlock.getChannel();

        if ( channel == null ) {

            return null;
        }

        Long id = channel.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

