package io.protone.service.mapper;

import io.protone.domain.TRACampaign;

import io.protone.domain.TRACustomer;

import io.protone.domain.TRAOrder;

import io.protone.service.dto.TRAOrderDTO;

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

public class TRAOrderMapperImpl implements TRAOrderMapper {

    @Override

    public TRAOrderDTO tRAOrderToTRAOrderDTO(TRAOrder tRAOrder) {

        if ( tRAOrder == null ) {

            return null;
        }

        TRAOrderDTO tRAOrderDTO = new TRAOrderDTO();

        tRAOrderDTO.setCustomerId( tRAOrderCustomerId( tRAOrder ) );

        tRAOrderDTO.setCampaignId( tRAOrderCampaignId( tRAOrder ) );

        tRAOrderDTO.setId( tRAOrder.getId() );

        tRAOrderDTO.setName( tRAOrder.getName() );

        tRAOrderDTO.setStartDate( tRAOrder.getStartDate() );

        tRAOrderDTO.setEndDate( tRAOrder.getEndDate() );

        tRAOrderDTO.setCalculatedPrize( tRAOrder.getCalculatedPrize() );

        return tRAOrderDTO;
    }

    @Override

    public List<TRAOrderDTO> tRAOrdersToTRAOrderDTOs(List<TRAOrder> tRAOrders) {

        if ( tRAOrders == null ) {

            return null;
        }

        List<TRAOrderDTO> list = new ArrayList<TRAOrderDTO>();

        for ( TRAOrder tRAOrder : tRAOrders ) {

            list.add( tRAOrderToTRAOrderDTO( tRAOrder ) );
        }

        return list;
    }

    @Override

    public TRAOrder tRAOrderDTOToTRAOrder(TRAOrderDTO tRAOrderDTO) {

        if ( tRAOrderDTO == null ) {

            return null;
        }

        TRAOrder tRAOrder = new TRAOrder();

        tRAOrder.setCampaign( tRACampaignFromId( tRAOrderDTO.getCampaignId() ) );

        tRAOrder.setCustomer( tRACustomerFromId( tRAOrderDTO.getCustomerId() ) );

        tRAOrder.setId( tRAOrderDTO.getId() );

        tRAOrder.setName( tRAOrderDTO.getName() );

        tRAOrder.setStartDate( tRAOrderDTO.getStartDate() );

        tRAOrder.setEndDate( tRAOrderDTO.getEndDate() );

        tRAOrder.setCalculatedPrize( tRAOrderDTO.getCalculatedPrize() );

        return tRAOrder;
    }

    @Override

    public List<TRAOrder> tRAOrderDTOsToTRAOrders(List<TRAOrderDTO> tRAOrderDTOs) {

        if ( tRAOrderDTOs == null ) {

            return null;
        }

        List<TRAOrder> list = new ArrayList<TRAOrder>();

        for ( TRAOrderDTO tRAOrderDTO : tRAOrderDTOs ) {

            list.add( tRAOrderDTOToTRAOrder( tRAOrderDTO ) );
        }

        return list;
    }

    private Long tRAOrderCustomerId(TRAOrder tRAOrder) {

        if ( tRAOrder == null ) {

            return null;
        }

        TRACustomer customer = tRAOrder.getCustomer();

        if ( customer == null ) {

            return null;
        }

        Long id = customer.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long tRAOrderCampaignId(TRAOrder tRAOrder) {

        if ( tRAOrder == null ) {

            return null;
        }

        TRACampaign campaign = tRAOrder.getCampaign();

        if ( campaign == null ) {

            return null;
        }

        Long id = campaign.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

