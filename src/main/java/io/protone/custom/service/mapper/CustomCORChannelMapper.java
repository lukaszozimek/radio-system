package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreChannelPT;
import io.protone.domain.CorChannel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface CustomCORChannelMapper {
    CoreChannelPT DB2DTO(CorChannel cORAddress);

    List<CoreChannelPT> DBs2DTOs(List<CorChannel> cORAddresses);

    CorChannel DTO2DB(CoreChannelPT cORAddressDTO);

    List<CorChannel> DTOs2DBs(List<CoreChannelPT> cORAddressDTOs);

}
