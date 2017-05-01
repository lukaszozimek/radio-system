package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.LibArtistPT;
import io.protone.custom.service.dto.LibPersonPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPerson;
import io.protone.domain.LibArtist;
import io.protone.domain.enumeration.LibArtistTypeEnum;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity LibArtist and its DTO LibArtistPT.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibArtistMapper {

    LibArtistPT DB2DTO(LibArtist db);

    List<LibArtistPT> DBs2DTOs(List<LibArtist> dbs);

    LibArtist DTO2DB(LibArtistPT dto, @Context CorNetwork network);

    default List<LibArtist> DTOs2DBs(List<LibArtistPT> dtos, @Context CorNetwork network) {
        List<LibArtist> corPeople = new ArrayList<>();
        if (dtos.isEmpty() || dtos == null) {
            return null;
        }
        for (LibArtistPT dto : dtos) {
            corPeople.add(DTO2DB(dto, network));
        }
        return corPeople;
    }

    @AfterMapping
    default void libArtistPTToLibArtistAfterMapping(LibArtistPT dto, @MappingTarget LibArtist entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }


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
