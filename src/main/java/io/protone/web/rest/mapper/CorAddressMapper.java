package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.CoreAddressPT;
import io.protone.domain.CorAddress;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorAddressMapper {
    CoreAddressPT DB2DTO(CorAddress cORAddress);

    List<CoreAddressPT> DBs2DTOs(List<CorAddress> cORAddresses);

    CorAddress DTO2DB(CoreAddressPT cORAddressDTO);

    List<CorAddress> DTOs2DBs(List<CoreAddressPT> cORAddressDTOs);

}
