package io.protone.service.mapper;

import io.protone.domain.TRACustomer;

import io.protone.domain.TRADiscount;

import io.protone.service.dto.TRADiscountDTO;

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

public class TRADiscountMapperImpl implements TRADiscountMapper {

    @Override

    public TRADiscountDTO tRADiscountToTRADiscountDTO(TRADiscount tRADiscount) {

        if ( tRADiscount == null ) {

            return null;
        }

        TRADiscountDTO tRADiscountDTO = new TRADiscountDTO();

        tRADiscountDTO.setCustomerId( tRADiscountCustomerId( tRADiscount ) );

        tRADiscountDTO.setId( tRADiscount.getId() );

        tRADiscountDTO.setValidFrom( tRADiscount.getValidFrom() );

        tRADiscountDTO.setValidTo( tRADiscount.getValidTo() );

        tRADiscountDTO.setDiscount( tRADiscount.getDiscount() );

        return tRADiscountDTO;
    }

    @Override

    public List<TRADiscountDTO> tRADiscountsToTRADiscountDTOs(List<TRADiscount> tRADiscounts) {

        if ( tRADiscounts == null ) {

            return null;
        }

        List<TRADiscountDTO> list = new ArrayList<TRADiscountDTO>();

        for ( TRADiscount tRADiscount : tRADiscounts ) {

            list.add( tRADiscountToTRADiscountDTO( tRADiscount ) );
        }

        return list;
    }

    @Override

    public TRADiscount tRADiscountDTOToTRADiscount(TRADiscountDTO tRADiscountDTO) {

        if ( tRADiscountDTO == null ) {

            return null;
        }

        TRADiscount tRADiscount = new TRADiscount();

        tRADiscount.setCustomer( tRACustomerFromId( tRADiscountDTO.getCustomerId() ) );

        tRADiscount.setId( tRADiscountDTO.getId() );

        tRADiscount.setValidFrom( tRADiscountDTO.getValidFrom() );

        tRADiscount.setValidTo( tRADiscountDTO.getValidTo() );

        tRADiscount.setDiscount( tRADiscountDTO.getDiscount() );

        return tRADiscount;
    }

    @Override

    public List<TRADiscount> tRADiscountDTOsToTRADiscounts(List<TRADiscountDTO> tRADiscountDTOs) {

        if ( tRADiscountDTOs == null ) {

            return null;
        }

        List<TRADiscount> list = new ArrayList<TRADiscount>();

        for ( TRADiscountDTO tRADiscountDTO : tRADiscountDTOs ) {

            list.add( tRADiscountDTOToTRADiscount( tRADiscountDTO ) );
        }

        return list;
    }

    private Long tRADiscountCustomerId(TRADiscount tRADiscount) {

        if ( tRADiscount == null ) {

            return null;
        }

        TRACustomer customer = tRADiscount.getCustomer();

        if ( customer == null ) {

            return null;
        }

        Long id = customer.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

