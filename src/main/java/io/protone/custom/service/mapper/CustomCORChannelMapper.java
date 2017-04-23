package io.protone.custom.service.mapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.protone.custom.service.dto.CoreChannelPT;

import io.protone.domain.CorChannel;

import io.protone.domain.CorNetwork;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-03-24T14:04:06+0100",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"

)

@Component

public class CustomCORChannelMapper {

    @Inject
    private CustomCorUserMapperExt corUserMapperExt;

    public CoreChannelPT cORChannelToCORChannelDTO(CorChannel cORChannel) {

        if (cORChannel == null) {

            return null;
        }

        CoreChannelPT coreChannelPT = new CoreChannelPT();


        coreChannelPT.setId(cORChannel.getId());

        coreChannelPT.setShortcut(cORChannel.getShortcut());

        coreChannelPT.setName(cORChannel.getName());

        coreChannelPT.setDescription(cORChannel.getDescription());
        return coreChannelPT;
    }


    public List<CoreChannelPT> cORChannelsToCORChannelDTOs(List<CorChannel> cORChannels) {

        if (cORChannels == null) {

            return null;
        }

        List<CoreChannelPT> list = new ArrayList<CoreChannelPT>();

        for (CorChannel corChannel : cORChannels) {

            list.add(cORChannelToCORChannelDTO(corChannel));
        }

        return list;
    }

    public CorChannel cORChannelDTOToCORChannel(CoreChannelPT cORChannelDTO) {

        if (cORChannelDTO == null) {

            return null;
        }

        CorChannel corChannel = new CorChannel();

        corChannel.setId(cORChannelDTO.getId());

        corChannel.setShortcut(cORChannelDTO.getShortcut());

        corChannel.setName(cORChannelDTO.getName());

        corChannel.setDescription(cORChannelDTO.getDescription());

        return corChannel;
    }

    public List<CorChannel> cORChannelDTOsToCORChannels(List<CoreChannelPT> cORChannelDTOs) {

        if (cORChannelDTOs == null) {

            return null;
        }

        List<CorChannel> list = new ArrayList<CorChannel>();

        for (CoreChannelPT coreChannelPT : cORChannelDTOs) {

            list.add(cORChannelDTOToCORChannel(coreChannelPT));
        }

        return list;
    }

    private Long cORChannelNetworkId(CorChannel corChannel) {

        if (corChannel == null) {

            return null;
        }

        CorNetwork network = corChannel.getNetwork();

        if (network == null) {

            return null;
        }

        Long id = network.getId();

        if (id == null) {

            return null;
        }

        return id;
    }

    private CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}

