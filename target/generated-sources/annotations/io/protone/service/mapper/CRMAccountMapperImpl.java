package io.protone.service.mapper;

import io.protone.domain.CORNetwork;

import io.protone.domain.CRMAccount;

import io.protone.service.dto.CRMAccountDTO;

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

public class CRMAccountMapperImpl implements CRMAccountMapper {

    @Override

    public CRMAccountDTO cRMAccountToCRMAccountDTO(CRMAccount cRMAccount) {

        if ( cRMAccount == null ) {

            return null;
        }

        CRMAccountDTO cRMAccountDTO = new CRMAccountDTO();

        cRMAccountDTO.setNetworkId( cRMAccountNetworkId( cRMAccount ) );

        cRMAccountDTO.setId( cRMAccount.getId() );

        cRMAccountDTO.setShortName( cRMAccount.getShortName() );

        cRMAccountDTO.setExternalId1( cRMAccount.getExternalId1() );

        cRMAccountDTO.setExternalId2( cRMAccount.getExternalId2() );

        cRMAccountDTO.setName( cRMAccount.getName() );

        cRMAccountDTO.setPaymentDelay( cRMAccount.getPaymentDelay() );

        cRMAccountDTO.setVatNumber( cRMAccount.getVatNumber() );

        return cRMAccountDTO;
    }

    @Override

    public List<CRMAccountDTO> cRMAccountsToCRMAccountDTOs(List<CRMAccount> cRMAccounts) {

        if ( cRMAccounts == null ) {

            return null;
        }

        List<CRMAccountDTO> list = new ArrayList<CRMAccountDTO>();

        for ( CRMAccount cRMAccount : cRMAccounts ) {

            list.add( cRMAccountToCRMAccountDTO( cRMAccount ) );
        }

        return list;
    }

    @Override

    public CRMAccount cRMAccountDTOToCRMAccount(CRMAccountDTO cRMAccountDTO) {

        if ( cRMAccountDTO == null ) {

            return null;
        }

        CRMAccount cRMAccount = new CRMAccount();

        cRMAccount.setNetwork( cORNetworkFromId( cRMAccountDTO.getNetworkId() ) );

        cRMAccount.setId( cRMAccountDTO.getId() );

        cRMAccount.setShortName( cRMAccountDTO.getShortName() );

        cRMAccount.setExternalId1( cRMAccountDTO.getExternalId1() );

        cRMAccount.setExternalId2( cRMAccountDTO.getExternalId2() );

        cRMAccount.setName( cRMAccountDTO.getName() );

        cRMAccount.setPaymentDelay( cRMAccountDTO.getPaymentDelay() );

        cRMAccount.setVatNumber( cRMAccountDTO.getVatNumber() );

        return cRMAccount;
    }

    @Override

    public List<CRMAccount> cRMAccountDTOsToCRMAccounts(List<CRMAccountDTO> cRMAccountDTOs) {

        if ( cRMAccountDTOs == null ) {

            return null;
        }

        List<CRMAccount> list = new ArrayList<CRMAccount>();

        for ( CRMAccountDTO cRMAccountDTO : cRMAccountDTOs ) {

            list.add( cRMAccountDTOToCRMAccount( cRMAccountDTO ) );
        }

        return list;
    }

    private Long cRMAccountNetworkId(CRMAccount cRMAccount) {

        if ( cRMAccount == null ) {

            return null;
        }

        CORNetwork network = cRMAccount.getNetwork();

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

