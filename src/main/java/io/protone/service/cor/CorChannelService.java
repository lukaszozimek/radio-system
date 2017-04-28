package io.protone.service.cor;

import io.protone.custom.service.CorNetworkService;
import io.protone.domain.CorNetwork;
import io.protone.repository.custom.CustomCorChannelRepository;
import io.protone.domain.CorChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 16.01.2017.
 */
@Service
@Transactional
public class CorChannelService {

    private final Logger log = LoggerFactory.getLogger(CorChannelService.class);

    @Inject
    private CustomCorChannelRepository channelRepository;

    @Inject
    private CorNetworkService networkService;

    public List<CorChannel> findAllChannel(CorNetwork network, Pageable pageable) {
        return channelRepository.findAllByNetwork(network, pageable);
    }

    public CorChannel findChannel(String networkShortcut, String channelShortcut) {
        return findChannelByNetworkShortcutAndChannelShortcut(networkShortcut, channelShortcut);
    }

    public void deleteChannel(String networkShortcut, String channelShortcut) {
        channelRepository.delete(findChannelByNetworkShortcutAndChannelShortcut(networkShortcut, channelShortcut));
    }

    public CorChannel save(CorChannel channel) {
        log.debug("Persisting CorChannel: {}", channel);
        return channelRepository.saveAndFlush(channel);
    }

    private CorChannel findChannelByNetworkShortcutAndChannelShortcut(String networkShortcut, String channelShortcut) {
        CorNetwork networkDB = networkService.findNetwork(networkShortcut);
        if (networkDB == null)
            return null;

        CorChannel channel = channelRepository.findOneByNetworkAndShortcut(networkDB, channelShortcut);
        return channel;
    }


}
