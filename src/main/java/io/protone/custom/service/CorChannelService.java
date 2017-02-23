package io.protone.custom.service;

import io.protone.domain.CorNetwork;
import io.protone.repository.custom.CustomCorChannelRepository;
import io.protone.domain.CorChannel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 16.01.2017.
 */
@Service
public class CorChannelService {

    @Inject
    private CustomCorChannelRepository channelRepository;

    @Inject
    private CorNetworkService networkService;

    public List<CorChannel> findAllChannel() {
        return channelRepository.findAll();
    }

    public CorChannel findChannel(String networkShortcut, String channelShortcut) {
        return findChannelByNetworkShortcutAndChannelShortcut(networkShortcut, channelShortcut);
    }

    @Transactional
    public void deleteChannel(String networkShortcut, String channelShortcut) {
        channelRepository.delete(findChannelByNetworkShortcutAndChannelShortcut(networkShortcut, channelShortcut));
    }

    public CorChannel save(CorChannel network) {
        return channelRepository.save(network);
    }

    public CorChannel findChannelByNetworkShortcutAndChannelShortcut(String networkShortcut, String channelShortcut) {
        CorNetwork networkDB = networkService.findNetwork(networkShortcut);
        if (networkDB == null)
            return null;

        CorChannel channel = channelRepository.findOneByNetworkAndShortcut(networkDB, channelShortcut);
             return channel;
    }
}
