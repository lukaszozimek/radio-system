package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TRACustomerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TRACustomer and its DTO TRACustomerDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface TRACustomerMapper {

    @Mapping(source = "industry.id", target = "industryId")
    @Mapping(source = "network.id", target = "networkId")
    @Mapping(source = "account.id", target = "accountId")
    TRACustomerDTO tRACustomerToTRACustomerDTO(TRACustomer tRACustomer);

    List<TRACustomerDTO> tRACustomersToTRACustomerDTOs(List<TRACustomer> tRACustomers);

    @Mapping(source = "industryId", target = "industry")
    @Mapping(source = "networkId", target = "network")
    @Mapping(source = "accountId", target = "account")
    TRACustomer tRACustomerDTOToTRACustomer(TRACustomerDTO tRACustomerDTO);

    List<TRACustomer> tRACustomerDTOsToTRACustomers(List<TRACustomerDTO> tRACustomerDTOs);

    default TRAIndustry tRAIndustryFromId(Long id) {
        if (id == null) {
            return null;
        }
        TRAIndustry tRAIndustry = new TRAIndustry();
        tRAIndustry.setId(id);
        return tRAIndustry;
    }

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
