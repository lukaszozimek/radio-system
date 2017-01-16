package io.protone.custom.service;

import io.protone.domain.CORChannel;
import io.protone.repository.CCORChannelRepository;
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
    private CCORChannelRepository ccorChannelRepository;

    public List<CORChannel> findAllChannel() {
        return ccorChannelRepository.findAll();
    }

    public CORChannel findChannel(String shortcut) {
        return ccorChannelRepository.findByShortcut(shortcut);
    }

    @Transactional
    public void deleteChannel(String shortcut) {
        ccorChannelRepository.deleteByShortcut(shortcut);
    }

    public CORChannel save(CORChannel network) {
        return ccorChannelRepository.save(network);
    }
}
