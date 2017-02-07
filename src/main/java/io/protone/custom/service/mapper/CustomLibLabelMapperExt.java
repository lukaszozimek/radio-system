package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibLabelPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibLabel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity LibLabel and its DTO LibLabelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomLibLabelMapperExt {

    @Mapping(source = "network.id", target = "networkId")
    LibLabelPT DB2DTO(LibLabel lIBLabel);

    List<LibLabelPT> DBs2DTOs(List<LibLabel> lIBLabels);

    @Mapping(source = "networkId", target = "network")
    LibLabel DTO2DB(LibLabelPT dto);

    List<LibLabel> DTOs2DBs(List<LibLabelPT> dtos);

    default CorNetwork mapCorNetwork(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
