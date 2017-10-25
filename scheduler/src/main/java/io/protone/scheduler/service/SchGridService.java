package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.mapper.SchGridMapper;
import io.protone.scheduler.repository.SchGridRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;


@Service
public class SchGridService {
    private final Logger log = LoggerFactory.getLogger(SchGridService.class);
    @Inject
    private SchGridRepository schGridRepository;

    @Inject
    private SchGridClockTemplateService schGridClockTemplateService;

    @Inject
    private SchGridMapper schGridMapper;

    @Transactional
    public SchGrid saveGrid(SchGrid schGrid) {
        SchGrid beforeSave;
        beforeSave = schGridRepository.saveAndFlush(schGrid);
        beforeSave.setClocks(Sets.newHashSet());
        beforeSave.clocks(schGridClockTemplateService.saveGridClockConfiguration(schGrid.getClocks(), beforeSave));
        return schGridRepository.saveAndFlush(beforeSave);
    }

    @Transactional(readOnly = true)
    public Slice<SchGrid> findSchGridsForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pageable) {
        return schGridRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pageable);
    }

    @Transactional(readOnly = true)
    public Slice<SchGrid> findAllDefaultGrids(String networkShortcut, String channelShortcut, Pageable pageable) {
        return schGridRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDefaultGrid(networkShortcut, channelShortcut, true, pageable);
    }

    @Transactional(readOnly = true)
    public Slice<SchGrid> findAllGridsByCategoryNaem(String networkShortcut, String channelShortcut, String categoryName, Pageable pageable) {
        return schGridRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndGridCategory_Name(networkShortcut, channelShortcut, categoryName, pageable);
    }

    @Transactional(readOnly = true)
    public SchGridDTO findSchGridForNetworkAndChannelAndShortNameDTO(String networkShortcut, String channelShortcut, String shortName) {
        return schGridMapper.DB2DTO(schGridRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName));
    }

    @Transactional(readOnly = true)
    public SchGrid findSchGridForNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        return schGridRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
    }

    @Transactional
    public void deleteSchGridByNetworkAndChannelAndShortNAme(String networkShortcut, String channelShortcut, String shortName) {
        schGridRepository.deleteByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
    }
    @Transactional(readOnly = true)
    public SchGrid findOneByNetworkShortcutAndChannelShortcutAndDefaultGridAndDayOfWeek(String networkShortcut, String channelShortcut, Boolean defaultGrid, CorDayOfWeekEnum corDayOfWeekEnum) {
        return this.schGridRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDefaultGridAndDayOfWeek(networkShortcut, channelShortcut, defaultGrid, corDayOfWeekEnum);
    }
}