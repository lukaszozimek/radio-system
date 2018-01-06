package io.protone.core.mapper;


import io.protone.core.api.dto.CorFilterDTO;
import io.protone.core.api.dto.thin.CorFilterThinDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorFilter;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {})
public interface CorFilterMapper {

    CorFilterDTO DB2DTO(CorFilter cORAddress);

    List<CorFilterDTO> DBs2DTOs(List<CorFilter> corFilters);

    List<CorFilterDTO> DBs2DTOs(Set<CorFilter> corFilters);

    CorFilter DTO2DB(CorFilterThinDTO cORAddressDTO);

    CorFilter DTO2DB(CorFilterDTO corFilterDTO, @Context CorChannel corNetwork);

    default List<CorFilter> DTOs2DBs(List<CorFilterDTO> corFilterDTOS, CorChannel corNetwork) {
        List<CorFilter> corAddresses = new ArrayList<>();
        if (corFilterDTOS.isEmpty() || corFilterDTOS == null) {
            return null;
        }
        for (CorFilterDTO dto : corFilterDTOS) {
            corAddresses.add(DTO2DB(dto, corNetwork));
        }
        return corAddresses;
    }

    default Set<CorFilter> DTOs2DBsSet(List<CorFilterDTO> corFilterDTOS, CorChannel corNetwork) {
        Set<CorFilter> corAddresses = new HashSet<>();
        if (corFilterDTOS.isEmpty() || corFilterDTOS == null) {
            return null;
        }
        for (CorFilterDTO dto : corFilterDTOS) {
            corAddresses.add(DTO2DB(dto, corNetwork));
        }
        return corAddresses;
    }

    @AfterMapping
    default void coreFilterDTOToCorFilterAfterMapping(CorFilterDTO dto, @MappingTarget CorFilter entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }
}
