package io.protone.custom.service;

import io.protone.domain.CorChannel;
import io.protone.repository.CorChannelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 16.01.2017.
 */
@Service
public class ChannelService {

    @Inject
    private CorChannelRepository ccorChannelRepository;

    public List<CorChannel> findAllChannel() {
        return ccorChannelRepository.findAll();
    }

    public CorChannel findChannel(String shortcut) {
        return ccorChannelRepository.findOneByPrefix(shortcut);
    }

    @Transactional
    public void deleteChannel(String shortcut) {
        ccorChannelRepository.deleteByPrefix(shortcut);
    }

    public CorChannel save(CorChannel network) {
        return ccorChannelRepository.save(network);
    }
}
