package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.CoreUserPT;
import io.protone.custom.service.dto.LibPersonPT;
import io.protone.domain.*;
import io.protone.web.rest.dto.thin.CoreUserThinDTO;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

@Mapper(componentModel = "spring", uses = {CorNetworkMapper.class, CorChannelMapper.class})
public interface CorUserMapper {

    @Mapping(source = "networks", target = "network")
    @Mapping(source = "channels", target = "channel")
    CoreUserPT DB2DTO(CorUser user);

    List<CoreUserPT> DBs2DTOs(List<CorUser> users);

    CorUser DTO2DB(CoreUserPT userPT, @Context CorChannel corChannel, @Context CorNetwork corNetwork);

    default List<CorUser> DTOs2DBs(List<CoreUserPT> userPTs, @Context CorChannel corChannel, @Context CorNetwork corNetwork) {
        List<CorUser> corPeople = new ArrayList<>();
        if (userPTs.isEmpty() || userPTs == null) {
            return null;
        }
        for (CoreUserPT dto : userPTs) {
            corPeople.add(DTO2DB(dto, corChannel, corNetwork));
        }
        return corPeople;
    }

    @AfterMapping
    default void coreUserPTToCorUserAfterMapping(CoreUserPT dto, @MappingTarget CorUser entity, @Context CorChannel corChannel, @Context CorNetwork corNetwork) {
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
