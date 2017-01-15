package io.protone.service.mapper;

import io.protone.domain.TRAEmissionOrder;

import io.protone.service.dto.TRAEmissionOrderDTO;

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

public class TRAEmissionOrderMapperImpl implements TRAEmissionOrderMapper {

    @Override

    public TRAEmissionOrderDTO tRAEmissionOrderToTRAEmissionOrderDTO(TRAEmissionOrder tRAEmissionOrder) {

        if ( tRAEmissionOrder == null ) {

            return null;
        }

        TRAEmissionOrderDTO tRAEmissionOrderDTO = new TRAEmissionOrderDTO();

        tRAEmissionOrderDTO.setId( tRAEmissionOrder.getId() );

        tRAEmissionOrderDTO.setCalculatedPrice( tRAEmissionOrder.getCalculatedPrice() );

        return tRAEmissionOrderDTO;
    }

    @Override

    public List<TRAEmissionOrderDTO> tRAEmissionOrdersToTRAEmissionOrderDTOs(List<TRAEmissionOrder> tRAEmissionOrders) {

        if ( tRAEmissionOrders == null ) {

            return null;
        }

        List<TRAEmissionOrderDTO> list = new ArrayList<TRAEmissionOrderDTO>();

        for ( TRAEmissionOrder tRAEmissionOrder : tRAEmissionOrders ) {

            list.add( tRAEmissionOrderToTRAEmissionOrderDTO( tRAEmissionOrder ) );
        }

        return list;
    }

    @Override

    public TRAEmissionOrder tRAEmissionOrderDTOToTRAEmissionOrder(TRAEmissionOrderDTO tRAEmissionOrderDTO) {

        if ( tRAEmissionOrderDTO == null ) {

            return null;
        }

        TRAEmissionOrder tRAEmissionOrder = new TRAEmissionOrder();

        tRAEmissionOrder.setId( tRAEmissionOrderDTO.getId() );

        tRAEmissionOrder.setCalculatedPrice( tRAEmissionOrderDTO.getCalculatedPrice() );

        return tRAEmissionOrder;
    }

    @Override

    public List<TRAEmissionOrder> tRAEmissionOrderDTOsToTRAEmissionOrders(List<TRAEmissionOrderDTO> tRAEmissionOrderDTOs) {

        if ( tRAEmissionOrderDTOs == null ) {

            return null;
        }

        List<TRAEmissionOrder> list = new ArrayList<TRAEmissionOrder>();

        for ( TRAEmissionOrderDTO tRAEmissionOrderDTO : tRAEmissionOrderDTOs ) {

            list.add( tRAEmissionOrderDTOToTRAEmissionOrder( tRAEmissionOrderDTO ) );
        }

        return list;
    }
}

