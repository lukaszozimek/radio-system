package io.protone.service.mapper;

import io.protone.domain.LIBMediaItem;

import io.protone.domain.SCHBlock;

import io.protone.domain.SCHEmission;

import io.protone.domain.SCHTemplate;

import io.protone.service.dto.SCHEmissionDTO;

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

public class SCHEmissionMapperImpl implements SCHEmissionMapper {

    @Override

    public SCHEmissionDTO sCHEmissionToSCHEmissionDTO(SCHEmission sCHEmission) {

        if ( sCHEmission == null ) {

            return null;
        }

        SCHEmissionDTO sCHEmissionDTO = new SCHEmissionDTO();

        sCHEmissionDTO.setBlockId( sCHEmissionBlockId( sCHEmission ) );

        sCHEmissionDTO.setMediaItemId( sCHEmissionMediaItemId( sCHEmission ) );

        sCHEmissionDTO.setTemplateId( sCHEmissionTemplateId( sCHEmission ) );

        sCHEmissionDTO.setId( sCHEmission.getId() );

        sCHEmissionDTO.setSeq( sCHEmission.getSeq() );

        sCHEmissionDTO.setStartTime( sCHEmission.getStartTime() );

        sCHEmissionDTO.setStartType( sCHEmission.getStartType() );

        sCHEmissionDTO.setRelativeDelay( sCHEmission.getRelativeDelay() );

        sCHEmissionDTO.setEndTime( sCHEmission.getEndTime() );

        sCHEmissionDTO.setLength( sCHEmission.getLength() );

        sCHEmissionDTO.setFinished( sCHEmission.isFinished() );

        return sCHEmissionDTO;
    }

    @Override

    public List<SCHEmissionDTO> sCHEmissionsToSCHEmissionDTOs(List<SCHEmission> sCHEmissions) {

        if ( sCHEmissions == null ) {

            return null;
        }

        List<SCHEmissionDTO> list = new ArrayList<SCHEmissionDTO>();

        for ( SCHEmission sCHEmission : sCHEmissions ) {

            list.add( sCHEmissionToSCHEmissionDTO( sCHEmission ) );
        }

        return list;
    }

    @Override

    public SCHEmission sCHEmissionDTOToSCHEmission(SCHEmissionDTO sCHEmissionDTO) {

        if ( sCHEmissionDTO == null ) {

            return null;
        }

        SCHEmission sCHEmission = new SCHEmission();

        sCHEmission.setTemplate( sCHTemplateFromId( sCHEmissionDTO.getTemplateId() ) );

        sCHEmission.setMediaItem( lIBMediaItemFromId( sCHEmissionDTO.getMediaItemId() ) );

        sCHEmission.setBlock( sCHBlockFromId( sCHEmissionDTO.getBlockId() ) );

        sCHEmission.setId( sCHEmissionDTO.getId() );

        sCHEmission.setSeq( sCHEmissionDTO.getSeq() );

        sCHEmission.setStartTime( sCHEmissionDTO.getStartTime() );

        sCHEmission.setStartType( sCHEmissionDTO.getStartType() );

        sCHEmission.setRelativeDelay( sCHEmissionDTO.getRelativeDelay() );

        sCHEmission.setEndTime( sCHEmissionDTO.getEndTime() );

        sCHEmission.setLength( sCHEmissionDTO.getLength() );

        sCHEmission.setFinished( sCHEmissionDTO.getFinished() );

        return sCHEmission;
    }

    @Override

    public List<SCHEmission> sCHEmissionDTOsToSCHEmissions(List<SCHEmissionDTO> sCHEmissionDTOs) {

        if ( sCHEmissionDTOs == null ) {

            return null;
        }

        List<SCHEmission> list = new ArrayList<SCHEmission>();

        for ( SCHEmissionDTO sCHEmissionDTO : sCHEmissionDTOs ) {

            list.add( sCHEmissionDTOToSCHEmission( sCHEmissionDTO ) );
        }

        return list;
    }

    private Long sCHEmissionBlockId(SCHEmission sCHEmission) {

        if ( sCHEmission == null ) {

            return null;
        }

        SCHBlock block = sCHEmission.getBlock();

        if ( block == null ) {

            return null;
        }

        Long id = block.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long sCHEmissionMediaItemId(SCHEmission sCHEmission) {

        if ( sCHEmission == null ) {

            return null;
        }

        LIBMediaItem mediaItem = sCHEmission.getMediaItem();

        if ( mediaItem == null ) {

            return null;
        }

        Long id = mediaItem.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long sCHEmissionTemplateId(SCHEmission sCHEmission) {

        if ( sCHEmission == null ) {

            return null;
        }

        SCHTemplate template = sCHEmission.getTemplate();

        if ( template == null ) {

            return null;
        }

        Long id = template.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

