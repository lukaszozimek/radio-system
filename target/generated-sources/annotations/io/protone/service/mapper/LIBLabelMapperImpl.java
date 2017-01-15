package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.LIBLabel;

import io.protone.service.dto.LIBLabelDTO;

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

public class LIBLabelMapperImpl implements LIBLabelMapper {

    @Override

    public LIBLabelDTO lIBLabelToLIBLabelDTO(LIBLabel lIBLabel) {

        if ( lIBLabel == null ) {

            return null;
        }

        LIBLabelDTO lIBLabelDTO = new LIBLabelDTO();

        lIBLabelDTO.setNetworkId( lIBLabelNetworkId( lIBLabel ) );

        lIBLabelDTO.setId( lIBLabel.getId() );

        lIBLabelDTO.setName( lIBLabel.getName() );

        lIBLabelDTO.setDescription( lIBLabel.getDescription() );

        return lIBLabelDTO;
    }

    @Override

    public List<LIBLabelDTO> lIBLabelsToLIBLabelDTOs(List<LIBLabel> lIBLabels) {

        if ( lIBLabels == null ) {

            return null;
        }

        List<LIBLabelDTO> list = new ArrayList<LIBLabelDTO>();

        for ( LIBLabel lIBLabel : lIBLabels ) {

            list.add( lIBLabelToLIBLabelDTO( lIBLabel ) );
        }

        return list;
    }

    @Override

    public LIBLabel lIBLabelDTOToLIBLabel(LIBLabelDTO lIBLabelDTO) {

        if ( lIBLabelDTO == null ) {

            return null;
        }

        LIBLabel lIBLabel = new LIBLabel();

        lIBLabel.setNetwork( cORNetworkFromId( lIBLabelDTO.getNetworkId() ) );

        lIBLabel.setId( lIBLabelDTO.getId() );

        lIBLabel.setName( lIBLabelDTO.getName() );

        lIBLabel.setDescription( lIBLabelDTO.getDescription() );

        return lIBLabel;
    }

    @Override

    public List<LIBLabel> lIBLabelDTOsToLIBLabels(List<LIBLabelDTO> lIBLabelDTOs) {

        if ( lIBLabelDTOs == null ) {

            return null;
        }

        List<LIBLabel> list = new ArrayList<LIBLabel>();

        for ( LIBLabelDTO lIBLabelDTO : lIBLabelDTOs ) {

            list.add( lIBLabelDTOToLIBLabel( lIBLabelDTO ) );
        }

        return list;
    }

    private Long lIBLabelNetworkId(LIBLabel lIBLabel) {

        if ( lIBLabel == null ) {

            return null;
        }

        CORNetwork network = lIBLabel.getNetwork();

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

