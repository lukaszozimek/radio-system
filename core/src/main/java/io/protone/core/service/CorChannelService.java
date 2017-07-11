package io.protone.core.service;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorImageItem;
import io.protone.core.repository.CorChannelRepository;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    public List<CorChannel> findAllChannel(String network, Pageable pageable) {
        return channelRepository.findAllByNetwork_Shortcut(network, pageable).stream().map(corChannel -> corChannel.logo(corImageItemService.getValidLinkToResource(corChannel.getCorImageItem()))).collect(toList());

    }

    public CorChannel findChannel(String networkShortcut, String channelShortcut) {
        CorChannel channel = channelRepository.findOneByNetwork_ShortcutAndShortcut(networkShortcut, channelShortcut);
        return channel;
    }

    public CorChannel findChannelWithPublicLogo(String networkShortcut, String channelShortcut) {
        CorChannel channel = channelRepository.findOneByNetwork_ShortcutAndShortcut(networkShortcut, channelShortcut);
        if (channel != null) {
            channel.logo(corImageItemService.getValidLinkToResource(channel.getCorImageItem()));
            return channel;
        }
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
