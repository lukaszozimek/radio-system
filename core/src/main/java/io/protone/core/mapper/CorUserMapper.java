package io.protone.core.mapper;

import io.protone.core.api.dto.CorUserDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.protone.core.domain.CorAuthority;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

@Mapper(componentModel = "spring", uses = {CorNetworkMapper.class, CorChannelMapper.class})
public interface CorUserMapper {

    @Mapping(source = "networks", target = "network")
    @Mapping(source = "channels", target = "channel")
    @Mapping(source = "corImageItem.publicUrl",target = "imageurl")
    CorUserDTO DB2DTO(CorUser user);

    List<CorUserDTO> DBs2DTOs(List<CorUser> users);

    CorUser DTO2DB(CorUserDTO userPT, @Context CorChannel corChannel, @Context CorNetwork corNetwork);

    default List<CorUser> DTOs2DBs(List<CorUserDTO> userPTs, @Context CorChannel corChannel, @Context CorNetwork corNetwork) {
        List<CorUser> corPeople = new ArrayList<>();
        if (userPTs.isEmpty() || userPTs == null) {
            return null;
        }
        for (CorUserDTO dto : userPTs) {
            corPeople.add(DTO2DB(dto, corChannel, corNetwork));
        }
        return corPeople;
    }

    @AfterMapping
    default void coreUserPTToCorUserAfterMapping(CorUserDTO dto, @MappingTarget CorUser entity, @Context CorChannel corChannel, @Context CorNetwork corNetwork) {
        entity.addNetwork(corNetwork);
        entity.addChannel(corChannel);
    }

    CorUser corUserFromCoreUserThinPT(CoreUserThinDTO coreUserThinDTO);

    CoreUserThinDTO coreUserThinPTFromCorUser(CorUser coreUserThinPT);

    default CorAuthority corAuthorityFromString(String authorinty) {

        if (isNullOrEmpty(authorinty)) {
            return null;
        }
        return new CorAuthority().name(authorinty);
    }

    default String stringFromCorAuthority(CorAuthority authorinty) {

        if (authorinty == null) {
            return null;
        }
        return authorinty.getName();
    }


}
