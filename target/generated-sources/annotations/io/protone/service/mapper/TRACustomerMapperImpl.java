package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.TRACustomer;

import io.protone.domain.TRAIndustry;

import io.protone.domain.User;

import io.protone.service.dto.TRACustomerDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-01-15T01:15:41+0100",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_71 (Oracle Corporation)"

)

@Component

public class TRACustomerMapperImpl implements TRACustomerMapper {

    @Autowired

    private UserMapper userMapper;

    @Override

    public TRACustomerDTO tRACustomerToTRACustomerDTO(TRACustomer tRACustomer) {

        if ( tRACustomer == null ) {

            return null;
        }

        TRACustomerDTO tRACustomerDTO = new TRACustomerDTO();

        tRACustomerDTO.setAccountId( tRACustomerAccountId( tRACustomer ) );

        tRACustomerDTO.setIndustryId( tRACustomerIndustryId( tRACustomer ) );

        tRACustomerDTO.setNetworkId( tRACustomerNetworkId( tRACustomer ) );

        tRACustomerDTO.setId( tRACustomer.getId() );

        tRACustomerDTO.setName( tRACustomer.getName() );

        tRACustomerDTO.setSize( tRACustomer.getSize() );

        tRACustomerDTO.setRange( tRACustomer.getRange() );

        tRACustomerDTO.setArea( tRACustomer.getArea() );

        tRACustomerDTO.setVatNumber( tRACustomer.getVatNumber() );

        tRACustomerDTO.setIdNumber1( tRACustomer.getIdNumber1() );

        tRACustomerDTO.setIdNumber2( tRACustomer.getIdNumber2() );

        tRACustomerDTO.setPaymentDate( tRACustomer.getPaymentDate() );

        return tRACustomerDTO;
    }

    @Override

    public List<TRACustomerDTO> tRACustomersToTRACustomerDTOs(List<TRACustomer> tRACustomers) {

        if ( tRACustomers == null ) {

            return null;
        }

        List<TRACustomerDTO> list = new ArrayList<TRACustomerDTO>();

        for ( TRACustomer tRACustomer : tRACustomers ) {

            list.add( tRACustomerToTRACustomerDTO( tRACustomer ) );
        }

        return list;
    }

    @Override

    public TRACustomer tRACustomerDTOToTRACustomer(TRACustomerDTO tRACustomerDTO) {

        if ( tRACustomerDTO == null ) {

            return null;
        }

        TRACustomer tRACustomer = new TRACustomer();

        tRACustomer.setIndustry( tRAIndustryFromId( tRACustomerDTO.getIndustryId() ) );

        tRACustomer.setAccount( userMapper.userFromId( tRACustomerDTO.getAccountId() ) );

        tRACustomer.setNetwork( cORNetworkFromId( tRACustomerDTO.getNetworkId() ) );

        tRACustomer.setId( tRACustomerDTO.getId() );

        tRACustomer.setName( tRACustomerDTO.getName() );

        tRACustomer.setSize( tRACustomerDTO.getSize() );

        tRACustomer.setRange( tRACustomerDTO.getRange() );

        tRACustomer.setArea( tRACustomerDTO.getArea() );

        tRACustomer.setVatNumber( tRACustomerDTO.getVatNumber() );

        tRACustomer.setIdNumber1( tRACustomerDTO.getIdNumber1() );

        tRACustomer.setIdNumber2( tRACustomerDTO.getIdNumber2() );

        tRACustomer.setPaymentDate( tRACustomerDTO.getPaymentDate() );

        return tRACustomer;
    }

    @Override

    public List<TRACustomer> tRACustomerDTOsToTRACustomers(List<TRACustomerDTO> tRACustomerDTOs) {

        if ( tRACustomerDTOs == null ) {

            return null;
        }

        List<TRACustomer> list = new ArrayList<TRACustomer>();

        for ( TRACustomerDTO tRACustomerDTO : tRACustomerDTOs ) {

            list.add( tRACustomerDTOToTRACustomer( tRACustomerDTO ) );
        }

        return list;
    }

    private Long tRACustomerAccountId(TRACustomer tRACustomer) {

        if ( tRACustomer == null ) {

            return null;
        }

        User account = tRACustomer.getAccount();

        if ( account == null ) {

            return null;
        }

        Long id = account.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long tRACustomerIndustryId(TRACustomer tRACustomer) {

        if ( tRACustomer == null ) {

            return null;
        }

        TRAIndustry industry = tRACustomer.getIndustry();

        if ( industry == null ) {

            return null;
        }

        Long id = industry.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long tRACustomerNetworkId(TRACustomer tRACustomer) {

        if ( tRACustomer == null ) {

            return null;
        }

        CORNetwork network = tRACustomer.getNetwork();

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

