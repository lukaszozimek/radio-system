package io.protone.library.mapper;


import io.protone.core.domain.CorNetwork;
import io.protone.library.api.dto.LibArtistDTO;
import io.protone.library.domain.LibArtist;
import io.protone.library.domain.enumeration.LibArtistTypeEnum;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity LibArtist and its DTO LibArtistDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibArtistMapper {

    LibArtistDTO DB2DTO(LibArtist db);

    List<LibArtistDTO> DBs2DTOs(List<LibArtist> dbs);

    LibArtist DTO2DB(LibArtistDTO dto, @Context CorNetwork network);

    default List<LibArtist> DTOs2DBs(List<LibArtistDTO> dtos, @Context CorNetwork network) {
        List<LibArtist> corPeople = new ArrayList<>();
        if (dtos.isEmpty() || dtos == null) {
            return null;
        }
        for (LibArtistDTO dto : dtos) {
            corPeople.add(DTO2DB(dto, network));
        }
        return corPeople;
    }

    @AfterMapping
    default void libArtistPTToLibArtistAfterMapping(LibArtistDTO dto, @MappingTarget LibArtist entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }


    default LibArtistDTO.TypeEnum mapLibArtistPT_TypeEnum(LibArtistTypeEnum value) {

        if (value.compareTo(LibArtistTypeEnum.AT_BAND) == 0)
            return LibArtistDTO.TypeEnum.BAND;
        else if (value.compareTo(LibArtistTypeEnum.AT_CHOIR) == 0)
            return LibArtistDTO.TypeEnum.CHOIR;
        else if (value.compareTo(LibArtistTypeEnum.AT_PERSON) == 0)
            return LibArtistDTO.TypeEnum.PERSON;
        else
            return LibArtistDTO.TypeEnum.OTHER;
    }

    default LibArtistTypeEnum mapLibArtistTypeEnum(LibArtistDTO.TypeEnum value) {

        if (value.compareTo(LibArtistDTO.TypeEnum.BAND) == 0)
            return LibArtistTypeEnum.AT_BAND;
        else if (value.compareTo(LibArtistDTO.TypeEnum.CHOIR) == 0)
            return LibArtistTypeEnum.AT_CHOIR;
        else if (value.compareTo(LibArtistDTO.TypeEnum.PERSON) == 0)
            return LibArtistTypeEnum.AT_PERSON;
        else
            return LibArtistTypeEnum.AT_OTHER;
    }

}
