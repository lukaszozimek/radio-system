package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.LibLabelPT;
import io.protone.domain.LibLabel;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity LibLabel and its DTO LibLabelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibLabelMapper {

    LibLabelPT DB2DTO(LibLabel lIBLabel);

    List<LibLabelPT> DBs2DTOs(List<LibLabel> lIBLabels);

    LibLabel DTO2DB(LibLabelPT dto);

    List<LibLabel> DTOs2DBs(List<LibLabelPT> dtos);

}
