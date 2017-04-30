package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfMusicLogPT;
import io.protone.custom.service.dto.CoreAddressPT;
import io.protone.domain.CfgExternalSystemLog;
import io.protone.domain.CorAddress;
import io.protone.domain.CorNetwork;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorAddressMapper {
    CoreAddressPT DB2DTO(CorAddress cORAddress);

    List<CoreAddressPT> DBs2DTOs(List<CorAddress> cORAddresses);

    CorAddress DTO2DB(CoreAddressPT cORAddressDTO, @Context CorNetwork corNetwork);

    default List<CorAddress> DTOs2DBs(List<CoreAddressPT> coreAddressPTS, CorNetwork networkId) {
        List<CorAddress> corAddresses = new ArrayList<>();
        if (coreAddressPTS.isEmpty() || coreAddressPTS == null) {
            return null;
        }
        for (CoreAddressPT dto : coreAddressPTS) {
            corAddresses.add(DTO2DB(dto, networkId));
        }
        return corAddresses;
    }

    @AfterMapping
    default void coreAddressPTToCorAddressAfterMapping(CoreAddressPT dto, @MappingTarget CorAddress entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
