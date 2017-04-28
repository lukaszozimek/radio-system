package io.protone.service.mapper;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import io.protone.custom.service.dto.CoreUserPT;
import io.protone.custom.service.dto.TraCustomerPersonPT;
import io.protone.custom.service.dto.thin.CoreUserThinPT;
import io.protone.domain.CorAuthority;
import io.protone.domain.CorPerson;
import io.protone.domain.CorUser;
import io.protone.repository.custom.CustomCorUserRepository;
import io.protone.service.mapper.CorChannelMapper;
import io.protone.service.mapper.CorNetworkMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Mapper(componentModel = "spring", uses = {CorNetworkMapper.class, CorChannelMapper.class})
public interface CorUserMapper {

    CoreUserPT DB2DTO(CorUser user);

    List<CoreUserPT> DBs2DTOs(List<CorUser> users);

    CorUser DTO2DB(CoreUserPT userPT);

    List<CorUser> DTOs2DBs(List<CoreUserPT> userPTs);

    CorUser corUserFromCoreUserThinPT(CoreUserThinPT coreUserThinPT);

    CoreUserThinPT coreUserThinPTFromCorUser(CorUser coreUserThinPT);

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
