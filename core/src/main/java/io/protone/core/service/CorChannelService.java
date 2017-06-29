package io.protone.core.service;

import io.protone.core.domain.CorChannel;
import io.protone.core.repository.CorChannelRepository;
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
    private CorChannelRepository channelRepository;

    public List<CorChannel> findAllChannel(String network, Pageable pageable) {
        return channelRepository.findAllByNetwork_Shortcut(network, pageable);
    }

    public CorChannel findChannel(String networkShortcut, String channelShortcut) {
        CorChannel channel = channelRepository.findOneByNetwork_ShortcutAndShortcut(networkShortcut, channelShortcut);
        return channel;
    }

    public void deleteChannel(String networkShortcut, String channelShortcut) {
        channelRepository.deleteByShortcutAndNetwork_Shortcut(channelShortcut, networkShortcut);
    }

    public CorChannel save(CorChannel channel) {
        log.debug("Persisting CorChannel: {}", channel);
        return channelRepository.saveAndFlush(channel);
    }



}
