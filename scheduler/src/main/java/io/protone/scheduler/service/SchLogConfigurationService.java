package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.service.LibFileLibraryService;
import io.protone.scheduler.domain.SchLogConfiguration;
import io.protone.scheduler.repository.SchLogConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;


@Service
public class SchLogConfigurationService {
    private final Logger log = LoggerFactory.getLogger(SchLogConfigurationService.class);
    @Inject
    private SchLogConfigurationRepository schLogConfigurationRepository;

    @Inject
    private SchLogColumnService schLogColumnService;

    @Inject
    private LibFileLibraryService libFileLibraryService;


    @Transactional
    public SchLogConfiguration saveSchLogConfiguration(SchLogConfiguration schLogConfiguration) throws CreateBucketException {
        if (schLogConfiguration != null) {
            if (schLogConfiguration.getId() == null) {
                this.libFileLibraryService.createOrUpdateLibrary(new LibFileLibrary()
                        .addChannel(schLogConfiguration.getChannel())
                        .network(schLogConfiguration.getNetwork())
                        .shortcut(schLogConfiguration.getExtension().toLowerCase())
                        .name(schLogConfiguration.getExtension()).prefix(schLogConfiguration.getExtension().substring(1)));
            }
            schLogConfiguration.setExtension(schLogConfiguration.getExtension().toLowerCase());
            return schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Slice<SchLogConfiguration> findSchLogConfigurationForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pageable) {
        return schLogConfigurationRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pageable);
    }

    public SchLogConfiguration findOneSchlogConfigurationByNetworkAndChannelAndExtension(String networkShortcut, String channelShortcut, String extension) {
        return this.schLogConfigurationRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndExtension(networkShortcut, channelShortcut, extension);
    }

    @Transactional(readOnly = true)
    public SchLogConfiguration findSchLogConfigurationForNetworkAndChannelAndId(String networkShortcut, String channelShortcut, Long id) {
        return schLogConfigurationRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndId(networkShortcut, channelShortcut, id);
    }

    @Transactional
    public void deleteSchLogConfigurationByNetworkAndChannelAndId(String networkShortcut, String channelShortcut, Long id) {
        SchLogConfiguration schLogConfiguration = schLogConfigurationRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndId(networkShortcut, channelShortcut, id);
        delete(schLogConfiguration);
    }

    @Transactional
    public void deleteSchLogConfigurationByNetworkAndChannelAndId(String networkShortcut, String channelShortcut, String extension) {
        SchLogConfiguration schLogConfiguration = schLogConfigurationRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndExtension(networkShortcut, channelShortcut, extension);
        delete(schLogConfiguration);
    }

    private void delete(SchLogConfiguration schLogConfiguration) {
        if (schLogConfiguration != null) {
            schLogColumnService.deleteColumns(schLogConfiguration.getLogColumns());
            schLogConfiguration.columns(Sets.newHashSet());
            SchLogConfiguration schLogConfiguration1 = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
            schLogConfigurationRepository.delete(schLogConfiguration1);

        }
    }
}
