package io.protone.scheduler.service;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.library.service.LibFileItemService;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.domain.SchLogConfiguration;
import io.protone.scheduler.repository.SchLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class SchLogService {
    private final Logger log = LoggerFactory.getLogger(SchLogService.class);
    @Inject
    private SchLogRepository schLogRepository;

    @Inject
    private SchLogConfigurationService schLogConfigurationService;

    @Inject
    private LibFileItemService libFileItemService;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;


    @Transactional
    public List<SchLog> saveSchLog(MultipartFile[] multipartFile, CorNetwork networkShortcut, CorChannel channelShortcut) {

        if (multipartFile != null) {
            return Arrays.asList(multipartFile).stream().map(multipartFile1 -> {
                String extension = multipartFile1.getOriginalFilename().split("\\.")[1].toLowerCase();
                SchLogConfiguration schLogConfiguration = schLogConfigurationService.findOneSchlogConfigurationByNetworkAndChannelAndExtension(networkShortcut.getShortcut(), channelShortcut.getShortcut(), extension);
                if (schLogConfiguration != null) {
                    SchLog logtoSave = new SchLog();
                    logtoSave.network(networkShortcut);
                    logtoSave.channel(channelShortcut);
                    logtoSave.setSchLogConfiguration(schLogConfiguration);
                    logtoSave.setDate(LocalDate.parse(multipartFile1.getOriginalFilename().split("\\.")[0], DateTimeFormatter.ofPattern(schLogConfiguration.getPattern())));
                    try {
                        logtoSave.setLibFileItem(libFileItemService.uploadFileItem(networkShortcut.getShortcut(), extension.toLowerCase(), multipartFile1));
                    } catch (IOException e) {
                        log.error("There was a problem with storing Item in Storage");
                    }
                    return schLogRepository.saveAndFlush(logtoSave);

                }
                throw new BadRequestException("There is no configuration for this Log");
            }).collect(toList());
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Slice<SchLog> findSchLogForNetworkAndChannelExtension(String networkShortcut, String channelShortcut, String extension, Pageable pageable) {
        return schLogRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndSchLogConfiguration_Extension(networkShortcut, channelShortcut, extension, pageable);
    }

    public SchLog findSchLogForNetworkAndChannelAndDateAndExtension(String networkShortcut, String channelShortcut, LocalDate date, String extension) {
        return schLogRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDateAndSchLogConfiguration_Extension(networkShortcut, channelShortcut, date, extension);
    }

    @Transactional
    public void deleteSchLogByNetworkAndChannelAndDateAndExtension(String networkShortcut, String channelShortcut, LocalDate date, String extension) {
        SchLog schLog = schLogRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDateAndSchLogConfiguration_Extension(networkShortcut, channelShortcut, date, extension);
        this.libFileItemService.deleteFile(schLog.getLibFileItem());
        this.schLogRepository.delete(schLog);
    }


}
