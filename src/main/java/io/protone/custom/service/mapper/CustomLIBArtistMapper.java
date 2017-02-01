package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibArtistPT;
import io.protone.custom.service.dto.LibraryPT;
import io.protone.domain.CORNetwork;
import io.protone.domain.LIBArtist;
import io.protone.domain.enumeration.LIBArtistTypeEnum;
import io.protone.service.dto.LIBArtistDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity LIBArtist and its DTO LibArtistPT.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomLIBArtistMapper {

    @Mapping(source = "network.id", target = "networkId")
    LibArtistPT DB2DTO(LIBArtist db);

    List<LibArtistPT> DBs2DTOs(List<LIBArtist> dbs);

    @Mapping(source = "networkId", target = "network")
    LIBArtist DTO2DB(LibArtistPT dto);

    List<LIBArtist> DTOs2DBs(List<LibArtistPT> dtos);

    default CORNetwork mapCORNetwork(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }

    default LibArtistPT.TypeEnum mapLibArtistPT_TypeEnum(LIBArtistTypeEnum value) {
        return LibArtistPT.TypeEnum.valueOf(value.toString());
    }

    default LIBArtistTypeEnum mapLIBArtistTypeEnum(LibArtistPT.TypeEnum value) {
        return LIBArtistTypeEnum.valueOf(value.toString());
    }

}
