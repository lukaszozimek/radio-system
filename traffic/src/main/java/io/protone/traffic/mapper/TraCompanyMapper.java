package io.protone.traffic.mapper;


import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.mapper.CorAddressMapper;
import io.protone.traffic.api.dto.TraCompanyDTO;
import io.protone.traffic.domain.TraCompany;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity TraCompany and its DTO TraCompanyDTO.
 */
@Mapper(componentModel = "spring", uses = {CorAddressMapper.class})
public interface TraCompanyMapper {

    TraCompanyDTO DB2DTO(TraCompany traCompany);

    List<TraCompanyDTO> DBs2DTOs(List<TraCompany> traCompanies);

    TraCompany DTO2DB(TraCompanyDTO traCompanyDTO, @Context CorChannel channel);

    default List<TraCompany> DTOs2DBs(List<TraCompanyDTO> traCompanyDTOS, @Context CorChannel channel) {
        List<TraCompany> traCompanies = new ArrayList<>();
        if (traCompanyDTOS.isEmpty() || traCompanyDTOS == null) {
            return null;
        }
        for (TraCompanyDTO dto : traCompanyDTOS) {
            traCompanies.add(DTO2DB(dto, channel));
        }
        return traCompanies;
    }

    @AfterMapping
    default void traCompanyDTOToTraCompanyAfterMapping(TraCompanyDTO dto, @MappingTarget TraCompany entity, @Context CorChannel channel) {
        entity.setChannel(channel);
    }
}
