package io.protone.scheduler.service;

import io.protone.scheduler.api.dto.SchClockTemplateDTO;
import io.protone.scheduler.domain.SchClockTemplate;
import io.protone.scheduler.mapper.SchClockTemplateMapper;
import io.protone.scheduler.repository.SchClockTemplateRepository;
import io.protone.scheduler.repository.SchEventTemplateEvnetTemplateRepostiory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 30/08/2017.
 */

@Service
public class SchClockTemplateService {
    private final Logger log = LoggerFactory.getLogger(SchClockTemplateService.class);

    @Inject
    private SchClockTemplateRepository schClockTemplateRepository;


    @Inject
    private SchEventTemplateService schEventService;

    @Inject
    private SchEmissionTemplateService schEmissionTemplateService;
    @Inject
    private SchEventTemplateEvnetTemplateRepostiory schEventTemplateEvnetTemplateRepostiory;
    @Inject
    private SchClockTemplateMapper schClockTemplateMapper;

    @Transactional
    public SchClockTemplateDTO saveClockConfiguration(SchClockTemplate schClockTemplate) {
        schClockTemplate.getSchEventTemplates().stream().forEach(schEventTemplateEvnetTemplate -> {
            schEventService.removeEvent(schEventTemplateEvnetTemplate.getChild().getId());
        });
        schClockTemplate.setSchEventTemplates(schClockTemplate.getSchEventTemplates().stream().map(schEventTemplateEvnetTemplate -> {
            schEventTemplateEvnetTemplate.parent(schClockTemplate).child(schEventService.saveEventConfigurationInClockContext(schEventTemplateEvnetTemplate.getChild()));
            return schEventTemplateEvnetTemplateRepostiory.saveAndFlush(schEventTemplateEvnetTemplate);
        }).collect(toList()));
        schClockTemplateRepository.save(schClockTemplate);
        return findDTOSchClockConfigurationForNetworkAndChannelAndShortName(schClockTemplate.getNetwork().getShortcut(), schClockTemplate.getChannel().getShortcut(), schClockTemplate.getShortName());
    }

    @Transactional(readOnly = true)
    public Slice<SchClockTemplate> findSchClockConfigurationsForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pagable) {
        return schClockTemplateRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pagable);
    }

    @Transactional(readOnly = true)
    public Slice<SchClockTemplate> findAllClocksByCategoryName(String networkShortcut, String channelShortcut, String categoryName, Pageable pageable) {
        return schClockTemplateRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndClockCategory_Name(networkShortcut, channelShortcut, categoryName, pageable);
    }

    @Transactional(readOnly = true)
    public SchClockTemplate findSchClockConfigurationForNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        return schClockTemplateRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
    }

    @Transactional(readOnly = true)
    public SchClockTemplateDTO findDTOSchClockConfigurationForNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        return schClockTemplateMapper.DB2DTO(schClockTemplateRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName));
    }

    @Transactional
    public void deleteSchClockConfigurationByNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {

    }
}
