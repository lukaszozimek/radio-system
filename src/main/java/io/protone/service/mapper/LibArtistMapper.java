package io.protone.service.mapper;

import io.protone.custom.service.dto.LibArtistPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibArtist;
import io.protone.domain.enumeration.LibArtistTypeEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity LibArtist and its DTO LibArtistPT.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibArtistMapper {

    LibArtistPT DB2DTO(LibArtist db);

    List<LibArtistPT> DBs2DTOs(List<LibArtist> dbs);

    LibArtist DTO2DB(LibArtistPT dto);

    List<LibArtist> DTOs2DBs(List<LibArtistPT> dtos);


    default LibArtistPT.TypeEnum mapLibArtistPT_TypeEnum(LibArtistTypeEnum value) {

        if (value.compareTo(LibArtistTypeEnum.AT_BAND) == 0)
            return LibArtistPT.TypeEnum.BAND;
        else if (value.compareTo(LibArtistTypeEnum.AT_CHOIR) == 0)
            return LibArtistPT.TypeEnum.CHOIR;
        else if (value.compareTo(LibArtistTypeEnum.AT_PERSON) == 0)
            return LibArtistPT.TypeEnum.PERSON;
        else
            return LibArtistPT.TypeEnum.OTHER;
    }

    default LibArtistTypeEnum mapLibArtistTypeEnum(LibArtistPT.TypeEnum value) {

        if (value.compareTo(LibArtistPT.TypeEnum.BAND) == 0)
            return LibArtistTypeEnum.AT_BAND;
        else if (value.compareTo(LibArtistPT.TypeEnum.CHOIR) == 0)
            return LibArtistTypeEnum.AT_CHOIR;
        else if (value.compareTo(LibArtistPT.TypeEnum.PERSON) == 0)
            return LibArtistTypeEnum.AT_PERSON;
        else
            return LibArtistTypeEnum.AT_OTHER;
    }

}
