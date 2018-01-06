package io.protone.core.mapper;

import io.protone.core.api.dto.CorUserDTO;
import io.protone.core.api.dto.thin.CoreUserThinDTO;
import io.protone.core.domain.*;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

@Mapper(componentModel = "spring", uses = {CorNetworkMapper.class, CorChannelMapper.class, CorOrganizationMapper.class})
public interface CorUserMapper {

    @Mapping(source = "organization", target = "organizationDTO")
    @Mapping(source = "channels", target = "channel")
    @Mapping(source = "corImageItem.publicUrl", target = "imageurl")
    CorUserDTO DB2DTO(CorUser user);

    List<CorUserDTO> DBs2DTOs(List<CorUser> users);

    CorUser DTO2DB(CorUserDTO userPT, @Context CorChannel corChannel, @Context CorOrganization corNetwork);

    default List<CorUser> DTOs2DBs(List<CorUserDTO> userPTs, @Context CorChannel corChannel, @Context CorOrganization organization) {
        List<CorUser> corPeople = new ArrayList<>();
        if (userPTs.isEmpty() || userPTs == null) {
            return null;
        }
        for (CorUserDTO dto : userPTs) {
            corPeople.add(DTO2DB(dto, corChannel, organization));
        }
        return corPeople;
    }

    @AfterMapping
    default void coreUserPTToCorUserAfterMapping(CorUserDTO dto, @MappingTarget CorUser entity, @Context CorChannel corChannel, @Context CorOrganization organization) {
        entity.setOrganization(organization);
        entity.addChannel(corChannel);
    }

    CorUser corUserFromCoreUserThinPT(CoreUserThinDTO coreUserThinDTO);

    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
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
