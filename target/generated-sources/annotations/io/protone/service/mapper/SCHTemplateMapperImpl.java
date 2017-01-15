package io.protone.service.mapper;

import io.protone.domain.CORChannel;

import io.protone.domain.SCHTemplate;

import io.protone.service.dto.SCHTemplateDTO;

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

public class SCHTemplateMapperImpl implements SCHTemplateMapper {

    @Override

    public SCHTemplateDTO sCHTemplateToSCHTemplateDTO(SCHTemplate sCHTemplate) {

        if ( sCHTemplate == null ) {

            return null;
        }

        SCHTemplateDTO sCHTemplateDTO = new SCHTemplateDTO();

        sCHTemplateDTO.setParentTemplateId( sCHTemplateParentTemplateId( sCHTemplate ) );

        sCHTemplateDTO.setChannelId( sCHTemplateChannelId( sCHTemplate ) );

        sCHTemplateDTO.setId( sCHTemplate.getId() );

        sCHTemplateDTO.setSeq( sCHTemplate.getSeq() );

        sCHTemplateDTO.setName( sCHTemplate.getName() );

        sCHTemplateDTO.setType( sCHTemplate.getType() );

        sCHTemplateDTO.setStartTime( sCHTemplate.getStartTime() );

        sCHTemplateDTO.setStartType( sCHTemplate.getStartType() );

        sCHTemplateDTO.setRelativeDelay( sCHTemplate.getRelativeDelay() );

        sCHTemplateDTO.setEndTime( sCHTemplate.getEndTime() );

        sCHTemplateDTO.setLength( sCHTemplate.getLength() );

        sCHTemplateDTO.setDimYear( sCHTemplate.getDimYear() );

        sCHTemplateDTO.setDimMonth( sCHTemplate.getDimMonth() );

        sCHTemplateDTO.setDimDay( sCHTemplate.getDimDay() );

        sCHTemplateDTO.setDimHour( sCHTemplate.getDimHour() );

        sCHTemplateDTO.setDimMinute( sCHTemplate.getDimMinute() );

        sCHTemplateDTO.setDimSecond( sCHTemplate.getDimSecond() );

        return sCHTemplateDTO;
    }

    @Override

    public List<SCHTemplateDTO> sCHTemplatesToSCHTemplateDTOs(List<SCHTemplate> sCHTemplates) {

        if ( sCHTemplates == null ) {

            return null;
        }

        List<SCHTemplateDTO> list = new ArrayList<SCHTemplateDTO>();

        for ( SCHTemplate sCHTemplate : sCHTemplates ) {

            list.add( sCHTemplateToSCHTemplateDTO( sCHTemplate ) );
        }

        return list;
    }

    @Override

    public SCHTemplate sCHTemplateDTOToSCHTemplate(SCHTemplateDTO sCHTemplateDTO) {

        if ( sCHTemplateDTO == null ) {

            return null;
        }

        SCHTemplate sCHTemplate = new SCHTemplate();

        sCHTemplate.setChannel( cORChannelFromId( sCHTemplateDTO.getChannelId() ) );

        sCHTemplate.setParentTemplate( sCHTemplateFromId( sCHTemplateDTO.getParentTemplateId() ) );

        sCHTemplate.setId( sCHTemplateDTO.getId() );

        sCHTemplate.setSeq( sCHTemplateDTO.getSeq() );

        sCHTemplate.setName( sCHTemplateDTO.getName() );

        sCHTemplate.setType( sCHTemplateDTO.getType() );

        sCHTemplate.setStartTime( sCHTemplateDTO.getStartTime() );

        sCHTemplate.setStartType( sCHTemplateDTO.getStartType() );

        sCHTemplate.setRelativeDelay( sCHTemplateDTO.getRelativeDelay() );

        sCHTemplate.setEndTime( sCHTemplateDTO.getEndTime() );

        sCHTemplate.setLength( sCHTemplateDTO.getLength() );

        sCHTemplate.setDimYear( sCHTemplateDTO.getDimYear() );

        sCHTemplate.setDimMonth( sCHTemplateDTO.getDimMonth() );

        sCHTemplate.setDimDay( sCHTemplateDTO.getDimDay() );

        sCHTemplate.setDimHour( sCHTemplateDTO.getDimHour() );

        sCHTemplate.setDimMinute( sCHTemplateDTO.getDimMinute() );

        sCHTemplate.setDimSecond( sCHTemplateDTO.getDimSecond() );

        return sCHTemplate;
    }

    @Override

    public List<SCHTemplate> sCHTemplateDTOsToSCHTemplates(List<SCHTemplateDTO> sCHTemplateDTOs) {

        if ( sCHTemplateDTOs == null ) {

            return null;
        }

        List<SCHTemplate> list = new ArrayList<SCHTemplate>();

        for ( SCHTemplateDTO sCHTemplateDTO : sCHTemplateDTOs ) {

            list.add( sCHTemplateDTOToSCHTemplate( sCHTemplateDTO ) );
        }

        return list;
    }

    private Long sCHTemplateParentTemplateId(SCHTemplate sCHTemplate) {

        if ( sCHTemplate == null ) {

            return null;
        }

        SCHTemplate parentTemplate = sCHTemplate.getParentTemplate();

        if ( parentTemplate == null ) {

            return null;
        }

        Long id = parentTemplate.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long sCHTemplateChannelId(SCHTemplate sCHTemplate) {

        if ( sCHTemplate == null ) {

            return null;
        }

        CORChannel channel = sCHTemplate.getChannel();

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

