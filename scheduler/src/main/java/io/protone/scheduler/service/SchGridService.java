package io.protone.scheduler.service;

import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.domain.SchDiscriminators;
import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.mapper.SchGridMapper;
import io.protone.scheduler.repository.SchEventTemplateEvnetTemplateRepostiory;
import io.protone.scheduler.repository.SchGridRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static java.util.stream.Collectors.toList;


@Service
public class SchGridService {
    private final Logger log = LoggerFactory.getLogger(SchGridService.class);
    @Inject
    private SchGridRepository schGridRepository;

    @Inject
    private SchGridMapper schGridMapper;

    @Inject
    private SchEventTemplateEvnetTemplateRepostiory schEventTemplateEvnetTemplateRepostiory;

    @Transactional
    public SchGridDTO saveGrid(SchGrid schGrid) {
        if (schGrid.getId() != null) {
            schEventTemplateEvnetTemplateRepostiory.deleteAllByPk_ParentTemplate_Id(schGrid.getId());
        }
        schGrid.setSchEventTemplates(schGrid.getSchEventTemplates().stream().map(schEventTemplateEvnetTemplate -> {
            schEventTemplateEvnetTemplate.parent(schGrid).child(schEventTemplateEvnetTemplate.getChild());
            return schEventTemplateEvnetTemplateRepostiory.saveAndFlush(schEventTemplateEvnetTemplate);
        }).collect(toList()));
        schGridRepository.saveAndFlush(schGrid);
        return findSchGridForNetworkAndChannelAndShortNameDTO(schGrid.getNetwork().getShortcut(), schGrid.getChannel().getShortcut(), schGrid.getShortName());
    }

    @Transactional(readOnly = true)
    public Slice<SchGrid> findSchGridsForNetworkAndChannel(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return schGridRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndType(organizationShortcut, channelShortcut, SchDiscriminators.GRID_TEMPLATE, pageable);
    }

    @Transactional(readOnly = true)
    public Slice<SchGrid> findAllDefaultGrids(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return schGridRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDefaultGridAndType(organizationShortcut, channelShortcut, true, SchDiscriminators.GRID_TEMPLATE, pageable);
    }

    @Transactional(readOnly = true)
    public Slice<SchGrid> findAllGridsByCategoryNaem(String organizationShortcut, String channelShortcut, String categoryName, Pageable pageable) {
        return schGridRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndGridCategory_NameAndType(organizationShortcut, channelShortcut, categoryName, SchDiscriminators.GRID_TEMPLATE, pageable);
    }

    @Transactional(readOnly = true)
    public SchGridDTO findSchGridForNetworkAndChannelAndShortNameDTO(String organizationShortcut, String channelShortcut, String shortName) {
        return schGridMapper.DB2DTO(schGridRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortNameAndType(organizationShortcut, channelShortcut, shortName, SchDiscriminators.GRID_TEMPLATE));
    }

    @Transactional(readOnly = true)
    public SchGrid findSchGridForNetworkAndChannelAndShortName(String organizationShortcut, String channelShortcut, String shortName) {
        return schGridRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortNameAndType(organizationShortcut, channelShortcut, shortName, SchDiscriminators.GRID_TEMPLATE);
    }

    @Transactional
    public void deleteSchGridByNetworkAndChannelAndShortNAme(String organizationShortcut, String channelShortcut, String shortName) {
        schGridRepository.deleteByNetwork_ShortcutAndChannel_ShortcutAndShortNameAndType(organizationShortcut, channelShortcut, shortName, SchDiscriminators.GRID_TEMPLATE);
    }

    @Transactional(readOnly = true)
    public SchGrid findOneByorganizationShortcutAndChannelShortcutAndDefaultGridAndDayOfWeek(String organizationShortcut, String channelShortcut, Boolean defaultGrid, CorDayOfWeekEnum corDayOfWeekEnum) {
        return this.schGridRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDefaultGridAndDayOfWeekAndType(organizationShortcut, channelShortcut, defaultGrid, corDayOfWeekEnum, SchDiscriminators.GRID_TEMPLATE);
    }
}