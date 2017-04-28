package io.protone.service.mapper;

import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.domain.CrmTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CorUserMapper.class})
public interface CrmTaskMapper {

    CrmTaskMapper INSTANCE = Mappers.getMapper(CrmTaskMapper.class);

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "assignedTo", target = "assignedTo")
    CrmTaskPT DB2DTO(CrmTask cORAddress);

    List<CrmTaskPT> DBs2DTOs(Set<CrmTask> cORAddresses);

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "assignedTo", target = "assignedTo")
    CrmTask DTO2DB(CrmTaskPT cORAddressDTO);

    Set<CrmTask> DTOs2DBs(List<CrmTaskPT> cORAddressDTOs);


}

