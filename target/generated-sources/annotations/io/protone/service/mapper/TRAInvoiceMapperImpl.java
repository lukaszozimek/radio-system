package io.protone.service.mapper;

import io.protone.domain.TRAInvoice;

import io.protone.domain.TRAOrder;

import io.protone.service.dto.TRAInvoiceDTO;

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

public class TRAInvoiceMapperImpl implements TRAInvoiceMapper {

    @Override

    public TRAInvoiceDTO tRAInvoiceToTRAInvoiceDTO(TRAInvoice tRAInvoice) {

        if ( tRAInvoice == null ) {

            return null;
        }

        TRAInvoiceDTO tRAInvoiceDTO = new TRAInvoiceDTO();

        tRAInvoiceDTO.setOrderId( tRAInvoiceOrderId( tRAInvoice ) );

        tRAInvoiceDTO.setId( tRAInvoice.getId() );

        tRAInvoiceDTO.setPaid( tRAInvoice.isPaid() );

        tRAInvoiceDTO.setPrice( tRAInvoice.getPrice() );

        tRAInvoiceDTO.setPaymentDay( tRAInvoice.getPaymentDay() );

        return tRAInvoiceDTO;
    }

    @Override

    public List<TRAInvoiceDTO> tRAInvoicesToTRAInvoiceDTOs(List<TRAInvoice> tRAInvoices) {

        if ( tRAInvoices == null ) {

            return null;
        }

        List<TRAInvoiceDTO> list = new ArrayList<TRAInvoiceDTO>();

        for ( TRAInvoice tRAInvoice : tRAInvoices ) {

            list.add( tRAInvoiceToTRAInvoiceDTO( tRAInvoice ) );
        }

        return list;
    }

    @Override

    public TRAInvoice tRAInvoiceDTOToTRAInvoice(TRAInvoiceDTO tRAInvoiceDTO) {

        if ( tRAInvoiceDTO == null ) {

            return null;
        }

        TRAInvoice tRAInvoice = new TRAInvoice();

        tRAInvoice.setOrder( tRAOrderFromId( tRAInvoiceDTO.getOrderId() ) );

        tRAInvoice.setId( tRAInvoiceDTO.getId() );

        tRAInvoice.setPaid( tRAInvoiceDTO.getPaid() );

        tRAInvoice.setPrice( tRAInvoiceDTO.getPrice() );

        tRAInvoice.setPaymentDay( tRAInvoiceDTO.getPaymentDay() );

        return tRAInvoice;
    }

    @Override

    public List<TRAInvoice> tRAInvoiceDTOsToTRAInvoices(List<TRAInvoiceDTO> tRAInvoiceDTOs) {

        if ( tRAInvoiceDTOs == null ) {

            return null;
        }

        List<TRAInvoice> list = new ArrayList<TRAInvoice>();

        for ( TRAInvoiceDTO tRAInvoiceDTO : tRAInvoiceDTOs ) {

            list.add( tRAInvoiceDTOToTRAInvoice( tRAInvoiceDTO ) );
        }

        return list;
    }

    private Long tRAInvoiceOrderId(TRAInvoice tRAInvoice) {

        if ( tRAInvoice == null ) {

            return null;
        }

        TRAOrder order = tRAInvoice.getOrder();

        if ( order == null ) {

            return null;
        }

        Long id = order.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

