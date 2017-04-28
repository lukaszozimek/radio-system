package io.protone.service.mapper;

import io.protone.custom.service.dto.CoreUserPT;
import io.protone.service.dto.thin.CoreUserThinDTO;
import io.protone.domain.CorAuthority;
import io.protone.domain.CorUser;
import org.mapstruct.Mapper;

import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

@Mapper(componentModel = "spring", uses = {CorNetworkMapper.class, CorChannelMapper.class})
public interface CorUserMapper {

    CoreUserPT DB2DTO(CorUser user);

    List<CoreUserPT> DBs2DTOs(List<CorUser> users);

    CorUser DTO2DB(CoreUserPT userPT);

    List<CorUser> DTOs2DBs(List<CoreUserPT> userPTs);

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
