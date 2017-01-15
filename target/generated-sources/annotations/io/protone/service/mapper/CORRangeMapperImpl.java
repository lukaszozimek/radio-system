package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.CORRange;

import io.protone.service.dto.CORRangeDTO;

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

public class CORRangeMapperImpl implements CORRangeMapper {

    @Override

    public CORRangeDTO cORRangeToCORRangeDTO(CORRange cORRange) {

        if ( cORRange == null ) {

            return null;
        }

        CORRangeDTO cORRangeDTO = new CORRangeDTO();

        cORRangeDTO.setNetworkId( cORRangeNetworkId( cORRange ) );

        cORRangeDTO.setId( cORRange.getId() );

        cORRangeDTO.setName( cORRange.getName() );

        return cORRangeDTO;
    }

    @Override

    public List<CORRangeDTO> cORRangesToCORRangeDTOs(List<CORRange> cORRanges) {

        if ( cORRanges == null ) {

            return null;
        }

        List<CORRangeDTO> list = new ArrayList<CORRangeDTO>();

        for ( CORRange cORRange : cORRanges ) {

            list.add( cORRangeToCORRangeDTO( cORRange ) );
        }

        return list;
    }

    @Override

    public CORRange cORRangeDTOToCORRange(CORRangeDTO cORRangeDTO) {

        if ( cORRangeDTO == null ) {

            return null;
        }

        CORRange cORRange = new CORRange();

        cORRange.setNetwork( cORNetworkFromId( cORRangeDTO.getNetworkId() ) );

        cORRange.setId( cORRangeDTO.getId() );

        cORRange.setName( cORRangeDTO.getName() );

        return cORRange;
    }

    @Override

    public List<CORRange> cORRangeDTOsToCORRanges(List<CORRangeDTO> cORRangeDTOs) {

        if ( cORRangeDTOs == null ) {

            return null;
        }

        List<CORRange> list = new ArrayList<CORRange>();

        for ( CORRangeDTO cORRangeDTO : cORRangeDTOs ) {

            list.add( cORRangeDTOToCORRange( cORRangeDTO ) );
        }

        return list;
    }

    private Long cORRangeNetworkId(CORRange cORRange) {

        if ( cORRange == null ) {

            return null;
        }

        CORNetwork network = cORRange.getNetwork();

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

