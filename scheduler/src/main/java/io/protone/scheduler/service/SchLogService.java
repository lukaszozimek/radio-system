package io.protone.scheduler.service;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public List<SchLog> saveEvent(MultipartFile[] multipartFile, String networkShortcut, String channelShortcut) {

        if (multipartFile != null) {
            return Arrays.asList(multipartFile).stream().map(multipartFile1 -> {

                SchLogConfiguration schLogConfiguration = schLogConfigurationService.findOneByrNetworkAndChannelAndExtension(networkShortcut, channelShortcut, multipartFile1.getOriginalFilename().split("\\.")[0]);
                if (schLogConfiguration != null) {
                    SchLog logtoSave = new SchLog();
                    logtoSave.network(corNetworkService.findNetwork(networkShortcut));
                    logtoSave.channel(corChannelService.findChannel(networkShortcut, channelShortcut));
                    logtoSave.setSchLogConfiguration(schLogConfiguration);
                    Pattern pattern = Pattern.compile(schLogConfiguration.getPattern());
                    Matcher matcher = pattern.matcher(multipartFile1.getOriginalFilename());
                    if (matcher.find()) {
                        logtoSave.setDate(LocalDate.parse(matcher.group(1), DateTimeFormatter.ofPattern(pattern.toString())));
                    } else {
                        new BadRequestException("Log doesn't Match to Pattern in configuration");
                    }
                    System.out.print(matcher.find());
                    try {
                        logtoSave.setLibFileItem(libFileItemService.uploadFileItem(networkShortcut, channelShortcut, multipartFile1));
                    } catch (IOException e) {
                        log.error("There was a problem with storing Item in Storage");
                    }
                    return schLogRepository.saveAndFlush(logtoSave);

                }
                return null;
            }).collect(toList());
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Slice<SchLog> findSchLogForNetworkAndChannelExtension(String networkShortcut, String channelShortcut, String extension, Pageable pageable) {
        return schLogRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndSchLogConfiguration_Extension(networkShortcut, channelShortcut, extension, pageable);
    }

    @Transactional(readOnly = true)
    public SchLog findSchLogForNetworkAndChannelAndDateAndExtension(String networkShortcut, String channelShortcut, LocalDate date, String extension) {
        return schLogRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDateAndSchLogConfiguration_Extension(networkShortcut, channelShortcut, date, extension);
    }

    @Transactional
    public void deleteSchLogByNetworkAndChannelAndDateAndExtension(String networkShortcut, String channelShortcut, LocalDate date, String extension) {
        schLogRepository.deleteByNetwork_ShortcutAndChannel_ShortcutAndDateAndSchLogConfiguration_Extension(networkShortcut, channelShortcut, date, extension);
    }



}
