package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibLabelPT;
import io.protone.domain.CORNetwork;
import io.protone.domain.LIBLabel;
import io.protone.service.dto.LIBLabelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity LIBLabel and its DTO LIBLabelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomLIBLabelMapperExt {

    @Mapping(source = "network.id", target = "networkId")
    LibLabelPT DB2DTO(LIBLabel lIBLabel);

    List<LibLabelPT> DBs2DTOs(List<LIBLabel> lIBLabels);

    @Mapping(source = "networkId", target = "network")
    LIBLabel DTO2DB(LibLabelPT dto);

    List<LIBLabel> DTOs2DBs(List<LibLabelPT> dtos);

    default CORNetwork mapCORNetwork(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
