package io.protone.core.service;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorImageItem;
import io.protone.core.repository.CorChannelRepository;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by lukaszozimek on 16.01.2017.
 */
@Service
@Transactional
public class CorChannelService {

    private final Logger log = LoggerFactory.getLogger(CorChannelService.class);

    @Inject
    private CorChannelRepository channelRepository;

    @Inject
    private CorImageItemService corImageItemService;

    public Slice<CorChannel> findAllChannel(String network, Pageable pageable) {
        return channelRepository.findSliceByNetwork_Shortcut(network, pageable);
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

    public CorChannel save(CorChannel channel, MultipartFile logo) throws IOException, TikaException, SAXException {
        log.debug("Persisting CorChannel: {}", channel);
        CorImageItem corImageItem = corImageItemService.saveImageItem(logo);
        return channelRepository.saveAndFlush(channel.logo(corImageItem));
    }


}
