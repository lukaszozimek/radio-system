package io.protone.scheduler.service;

import io.protone.scheduler.api.dto.SchEventTemplateDTO;
import io.protone.scheduler.domain.SchDiscriminators;
import io.protone.scheduler.domain.SchEventTemplate;
import io.protone.scheduler.mapper.SchEventTemplateMapper;
import io.protone.scheduler.repository.SchEventTemplateEvnetTemplateRepostiory;
import io.protone.scheduler.repository.SchEventTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@Service
public class SchEventTemplateService {

    private final Logger log = LoggerFactory.getLogger(SchEventTemplateService.class);
    @Inject
    private SchEventTemplateRepository eventRepository;

    @Inject
    private SchEventTemplateEvnetTemplateRepostiory schEventTemplateEvnetTemplateRepostiory;

    @Inject
    private SchEventTemplateMapper schEventTemplateMapper;

    @Transactional(readOnly = true)
    public Slice<SchEventTemplate> findSchEventTemplatesForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pagable) {
        return eventRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndInstanceAndType(networkShortcut, channelShortcut, false, SchDiscriminators.EVENT_TEMPLATE, pagable);
    }

    @Transactional(readOnly = true)
    public Slice<SchEventTemplate> findAllEventsByCategoryName(String networkShortcut, String channelShortcut, String categoryName, Pageable pageable) {
        return eventRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndEventCategory_NameAndInstanceAndType(networkShortcut, channelShortcut, categoryName, false, SchDiscriminators.EVENT_TEMPLATE, pageable);
    }

    @Transactional
    public SchEventTemplate saveEventConfigurationInClockContext(SchEventTemplate schEventConfiguration) {
        if (schEventConfiguration.getInstance().equals(true)) {
            if (schEventConfiguration.getId() != null) {
                schEventConfiguration.setId(null);
                schEventConfiguration.getEmissions().stream().forEach(schEmissionTemplate -> {
                    schEmissionTemplate.setId(null);
                    schEmissionTemplate.getAttachments().stream().forEach(schAttachmentTemplate -> schAttachmentTemplate.setId(null));
                });
            }
            eventRepository.saveAndFlush(schEventConfiguration);
            if (schEventConfiguration.getSchEventTemplates() != null && !schEventConfiguration.getSchEventTemplates().isEmpty()) {
                schEventConfiguration.setSchEventTemplates(schEventConfiguration.getSchEventTemplates().stream().map(schEventTemplateEvnetTemplate -> {

                    if (schEventTemplateEvnetTemplate.getChild().getSchEventTemplates() != null && !schEventTemplateEvnetTemplate.getChild().getSchEventTemplates().isEmpty()) {
                        schEventTemplateEvnetTemplate.child(saveEventConfigurationInClockContext(schEventTemplateEvnetTemplate.getChild()));
                    }
                    if (schEventTemplateEvnetTemplate.getChild().getInstance().equals(true) && (schEventTemplateEvnetTemplate.getChild().getSchEventTemplates() == null || schEventTemplateEvnetTemplate.getChild().getSchEventTemplates().isEmpty())) {
                        if (schEventConfiguration.getId() != null) {
                            schEventConfiguration.setId(null);
                            schEventConfiguration.getEmissions().stream().forEach(schEmissionTemplate -> {
                                schEmissionTemplate.setId(null);
                                schEmissionTemplate.getAttachments().stream().forEach(schAttachmentTemplate -> schAttachmentTemplate.setId(null));
                            });
                        }
                        schEventTemplateEvnetTemplate.setChild(eventRepository.saveAndFlush(schEventTemplateEvnetTemplate.getChild()));
                        schEventTemplateEvnetTemplate.sequence(schEventTemplateEvnetTemplate.getSequence()).parent(schEventConfiguration).child(schEventTemplateEvnetTemplate.getChild());
                    } else {
                        schEventTemplateEvnetTemplate.sequence(schEventTemplateEvnetTemplate.getSequence()).parent(schEventConfiguration).child(schEventTemplateEvnetTemplate.getChild());
                    }
                    return schEventTemplateEvnetTemplateRepostiory.saveAndFlush(schEventTemplateEvnetTemplate);
                }).collect(Collectors.toList()));
            }
        }
        return schEventConfiguration;
    }

    @Transactional
    public SchEventTemplate saveEventConfiguration(SchEventTemplate schEventConfiguration) {
        eventRepository.saveAndFlush(schEventConfiguration);
        return schEventConfiguration;
    }

    @Transactional
    public void removeEvent(Long id) {
        if (id != null) {
            SchEventTemplate schEventTemplate = eventRepository.findOne(id);
            if (schEventTemplate.getInstance().equals(true)) {
                if (schEventTemplate.getSchEventTemplates() != null && !schEventTemplate.getSchEventTemplates().isEmpty()) {
                    schEventTemplate.getSchEventTemplates().stream().forEach(schEventTemplateEvnetTemplate -> {
                        if (schEventTemplateEvnetTemplate.getChild().getSchEventTemplates() != null && !schEventTemplateEvnetTemplate.getChild().getSchEventTemplates().isEmpty()) {
                            removeEvent(schEventTemplateEvnetTemplate.getChild().getId());
                        }
                        schEventTemplateEvnetTemplateRepostiory.delete(schEventTemplateEvnetTemplate);
                    });
                }
                eventRepository.delete(schEventTemplate);
            }
            eventRepository.flush();
            schEventTemplateEvnetTemplateRepostiory.flush();
        }
    }


    @Transactional
    public void deleteSchEventTemplateByNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        eventRepository.deleteByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
    }

    @Transactional(readOnly = true)
    public SchEventTemplate findSchEventTemplatesForNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        return eventRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortNameAndInstanceAndType(networkShortcut, channelShortcut, shortName, false, SchDiscriminators.EVENT_TEMPLATE);
    }

    @Transactional(readOnly = true)
    public SchEventTemplateDTO findSchEventTemplatesForNetworkAndChannelAndShortNameDTO(String networkShortcut, String channelShortcut, String shortName) {
        return schEventTemplateMapper.DB2DTO(eventRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortNameAndInstanceAndType(networkShortcut, channelShortcut, shortName, false, SchDiscriminators.EVENT_TEMPLATE));
    }
}
