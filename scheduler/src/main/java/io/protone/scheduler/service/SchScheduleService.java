package io.protone.scheduler.service;

import io.protone.scheduler.api.dto.SchScheduleDTO;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.mapper.SchScheduleMapper;
import io.protone.scheduler.repository.SchBlockSchBlockRepository;
import io.protone.scheduler.repository.SchScheduleRepository;
import io.protone.scheduler.service.time.SchScheduleDTOTimeCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class SchScheduleService {
    private final Logger log = LoggerFactory.getLogger(SchScheduleService.class);
    @Inject
    private SchScheduleRepository schScheduleRepository;

    @Inject
    private SchPlaylistService schPlaylistService;

    @Inject
    private SchClockService schClockService;
    @Inject
    private SchScheduleDTOTimeCalculatorService schScheduleDTOTimeCalculatorService;
    @Inject
    private SchScheduleMapper schScheduleMapper;
    @Inject
    private SchBlockSchBlockRepository schBlockSchBlockRepository;

    @Transactional
    public SchSchedule saveSchedule(SchSchedule schSchedule) {
        if (schSchedule.getId() != null) {
            schBlockSchBlockRepository.deleteAllByPk_ParentTemplate_Id(schSchedule.getId());
        }
        schScheduleRepository.saveAndFlush(schSchedule);
        schSchedule.setBlocks(schSchedule.getBlocks().stream().map(schEventTemplateEvnetTemplate -> {
            schEventTemplateEvnetTemplate.sequence(schEventTemplateEvnetTemplate.getSequence()).parent(schSchedule).child(schClockService.saveClock((SchClock) schEventTemplateEvnetTemplate.getChild()));
            return schBlockSchBlockRepository.saveAndFlush(schEventTemplateEvnetTemplate);
        }).collect(toList()));

        return schSchedule;

    }

    @Transactional(readOnly = true)
    public Slice<SchSchedule> findSchSchedulesForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pageable) {
        return schScheduleRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pageable);
    }

    @Transactional(readOnly = true)
    public SchScheduleDTO findSchScheduleForNetworkAndChannelAndDate(String networkShortcut, String channelShortcut, LocalDate date) {
        SchSchedule schSchedule = schScheduleRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(networkShortcut, channelShortcut, date);

        if (schSchedule != null) {
            return schScheduleDTOTimeCalculatorService.calculateTimeInSchPlaylistDTO(schScheduleMapper.DB2DTO(schSchedule));
        }
        return null;
    }

    @Transactional(readOnly = true)
    public SchSchedule findSchScheduleEntityForNetworkAndChannelAndDate(String networkShortcut, String channelShortcut, LocalDate date) {
        return schScheduleRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(networkShortcut, channelShortcut, date);

    }

    @Transactional
    public void deleteSchScheduleByNetworkAndChannelAndDate(String networkShortcut, String channelShortcut, LocalDate date) {
        SchSchedule schSchedule = findSchScheduleEntityForNetworkAndChannelAndDate(networkShortcut, channelShortcut, date);

        if (schSchedule != null) {
            schSchedule.getBlocks().stream().forEach(schBlockSchBlock -> {
                schClockService.deleteByScheduleId(schBlockSchBlock.getChild().getId());
            });

            schScheduleRepository.delete(schSchedule);
            schPlaylistService.deleteSchPlaylistByNetworkAndChannelAndDate(networkShortcut, channelShortcut, date);
        }
    }

    public List<SchSchedule> findSchSchedulesForNetworkAndChannelBetweenDates(String networkShortcut, String channelShortcut, LocalDate from, LocalDate to) {
        return schScheduleRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndDateBetween(networkShortcut, channelShortcut, from, to);
    }
}