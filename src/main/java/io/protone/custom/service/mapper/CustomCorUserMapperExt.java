
package io.protone.custom.service.mapper;

import com.google.common.collect.Sets;
import io.protone.custom.service.dto.CoreUserPT;
import io.protone.domain.CorUser;
import io.protone.repository.custom.CustomCorUserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-01-26T21:11:21+0100",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_71 (Oracle Corporation)"

)

@Component

public class CustomCorUserMapperExt {

    @Inject
    private CustomCorUserRepository customCorUserRepository;
    @Inject
    private CustomCorNetworkMapper customCorNetworkMapper;
    @Inject
    private CustomCORChannelMapper customCORChannelMapper;

    public CoreUserPT userToCoreUserPT(CorUser user) {

        if (user == null) {

            return null;
        }

        CoreUserPT coreUserPT = new CoreUserPT();

        coreUserPT.setActivated(user.isActivated());

        coreUserPT.setEmail(user.getEmail());

        coreUserPT.setFirstName(user.getFirstname());

        coreUserPT.setLangKey(user.getLangkey());

        coreUserPT.setLastName(user.getLastname());

        coreUserPT.setLogin(user.getLogin());

        coreUserPT.setId(user.getId());
        if (user.getAuthorities() != null) {
            coreUserPT.setAuthorities(user.getAuthorities().stream().map(corAuthority -> corAuthority.getName()).collect(toList()));
        }
        if (!user.getChannels().isEmpty()) {
            coreUserPT.setChannel(user.getChannels().stream().map(customCORChannelMapper::cORChannelToCORChannelDTO).collect(toList()));

        }
        if (!user.getNetworks().isEmpty()) {
            coreUserPT.network(customCorNetworkMapper.cORNetworkToCorNetworkDTO(user.getNetworks().stream().findFirst().get()));
        }
        return coreUserPT;
    }

    public List<CoreUserPT> usersToCoreUserPTs(List<CorUser> users) {

        if (users == null) {

            return null;
        }

        List<CoreUserPT> list = new ArrayList<CoreUserPT>();

        for (CorUser user : users) {

            list.add(userToCoreUserPT(user));
        }

        return list;
    }

    public CorUser coreUserPTToUser(CoreUserPT userPT) {

        if (userPT == null) {

            return null;
        }

        CorUser user = customCorUserRepository.findOneByLogin(userPT.getLogin()).get();
        user.setFirstname(userPT.getFirstName());
        user.setLastname(userPT.getLastName());
        user.setEmail(userPT.getEmail());
        user.setNetworks(Sets.newHashSet(customCorNetworkMapper.cORNetworkDTOToCorNetwork(userPT.getNetwork())));
        user.channels(userPT.getChannel().stream().map(customCORChannelMapper::cORChannelDTOToCORChannel).collect(toSet()));
        if (userPT.getActivated() != null) {

            user.setActivated(userPT.getActivated());
        }

        user.setLangkey(userPT.getLangKey());

        return user;
    }

    public List<CorUser> coreUserPTsToUsers(List<CoreUserPT> userPTs) {

        if (userPTs == null) {

            return null;
        }

        List<CorUser> list = new ArrayList<CorUser>();

        for (CoreUserPT coreUserPT : userPTs) {

            list.add(coreUserPTToUser(coreUserPT));
        }

        return list;
    }
}
