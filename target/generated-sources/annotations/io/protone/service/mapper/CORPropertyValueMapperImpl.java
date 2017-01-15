package io.protone.service.mapper;

import io.protone.domain.CORPropertyKey;

import io.protone.domain.CORPropertyValue;

import io.protone.service.dto.CORPropertyValueDTO;

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

public class CORPropertyValueMapperImpl implements CORPropertyValueMapper {

    @Override

    public CORPropertyValueDTO cORPropertyValueToCORPropertyValueDTO(CORPropertyValue cORPropertyValue) {

        if ( cORPropertyValue == null ) {

            return null;
        }

        CORPropertyValueDTO cORPropertyValueDTO = new CORPropertyValueDTO();

        cORPropertyValueDTO.setPropertyKeyId( cORPropertyValuePropertyKeyId( cORPropertyValue ) );

        cORPropertyValueDTO.setId( cORPropertyValue.getId() );

        cORPropertyValueDTO.setValue( cORPropertyValue.getValue() );

        return cORPropertyValueDTO;
    }

    @Override

    public List<CORPropertyValueDTO> cORPropertyValuesToCORPropertyValueDTOs(List<CORPropertyValue> cORPropertyValues) {

        if ( cORPropertyValues == null ) {

            return null;
        }

        List<CORPropertyValueDTO> list = new ArrayList<CORPropertyValueDTO>();

        for ( CORPropertyValue cORPropertyValue : cORPropertyValues ) {

            list.add( cORPropertyValueToCORPropertyValueDTO( cORPropertyValue ) );
        }

        return list;
    }

    @Override

    public CORPropertyValue cORPropertyValueDTOToCORPropertyValue(CORPropertyValueDTO cORPropertyValueDTO) {

        if ( cORPropertyValueDTO == null ) {

            return null;
        }

        CORPropertyValue cORPropertyValue = new CORPropertyValue();

        cORPropertyValue.setPropertyKey( cORPropertyKeyFromId( cORPropertyValueDTO.getPropertyKeyId() ) );

        cORPropertyValue.setId( cORPropertyValueDTO.getId() );

        cORPropertyValue.setValue( cORPropertyValueDTO.getValue() );

        return cORPropertyValue;
    }

    @Override

    public List<CORPropertyValue> cORPropertyValueDTOsToCORPropertyValues(List<CORPropertyValueDTO> cORPropertyValueDTOs) {

        if ( cORPropertyValueDTOs == null ) {

            return null;
        }

        List<CORPropertyValue> list = new ArrayList<CORPropertyValue>();

        for ( CORPropertyValueDTO cORPropertyValueDTO : cORPropertyValueDTOs ) {

            list.add( cORPropertyValueDTOToCORPropertyValue( cORPropertyValueDTO ) );
        }

        return list;
    }

    private Long cORPropertyValuePropertyKeyId(CORPropertyValue cORPropertyValue) {

        if ( cORPropertyValue == null ) {

            return null;
        }

        CORPropertyKey propertyKey = cORPropertyValue.getPropertyKey();

        if ( propertyKey == null ) {

            return null;
        }

        Long id = propertyKey.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

