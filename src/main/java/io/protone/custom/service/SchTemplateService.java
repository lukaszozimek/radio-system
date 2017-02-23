package io.protone.custom.service;

import io.protone.custom.service.dto.SchTemplatePT;
import io.protone.custom.service.mapper.CustomSchTemplateMapper;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.SchTemplate;
import io.protone.repository.custom.CustomCorChannelRepository;
import io.protone.repository.custom.CustomSchTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SchTemplateService {

    @Inject
    CorNetworkService networkService;

    @Inject
    CorChannelService channelService;

    @Inject
    CustomSchTemplateRepository templateRepository;

    @Inject
    CustomSchTemplateMapper templateMapper;

    @Inject
    CustomCorChannelRepository channelRepository;


    public SchTemplatePT getTemplate(String networkShortcut, String channelShortcut, String templateName) {
        CorChannel channelDB = channelService.findChannelByNetworkShortcutAndChannelShortcut(networkShortcut, channelShortcut);
        Optional<SchTemplate> templateDB = templateRepository.findByChannelAndName(channelDB, templateName);
        return templateMapper.DBToDTO(templateDB.get());
    }

    public List<SchTemplatePT> getTemplates(String networkShortcut, String channelShortcut) {
        CorChannel channelDB = channelService.findChannelByNetworkShortcutAndChannelShortcut(networkShortcut, channelShortcut);
        List<SchTemplate> resultsDB = templateRepository.findByChannel(channelDB);
        return templateMapper.DBsToDTOs(resultsDB);
    }

    public List<SchTemplatePT> getTemplates(String networkShortcut) {
        List<SchTemplatePT> results = new ArrayList<>();
        CorNetwork networkDB = networkService.findNetwork(networkShortcut);
        if (networkDB == null)
            return results;

        List<CorChannel> channels = channelRepository.findAllByNetwork(networkDB);

        for (CorChannel channel: channels) {
            List<SchTemplate> templates = templateRepository.findByChannel(channel);
            results.addAll(templateMapper.DBsToDTOs(templates));
        }
        return results;
    }

    @Transactional
    public void deleteTemplate(String networkShortcut, String date) {
        List<SchTemplatePT> templatesToDelete = getTemplates(networkShortcut, date);
        templateRepository.delete(templateMapper.DTOsToDBs(templatesToDelete));
    }

    @Transactional
    public SchTemplatePT createOrUpdateTemplate(String networkShortcut, SchTemplatePT template) {
        return templateMapper.DBToDTO(templateRepository.saveAndFlush(templateMapper.DTOToDB(template)));
    }

    @Transactional
    public SchTemplatePT createOrUpdateTemplate(String networkShortcut, String channelShortcut, SchTemplatePT template) {
        CorChannel channel = channelService.findChannelByNetworkShortcutAndChannelShortcut(networkShortcut, channelShortcut);
        template.setChannelId(channel.getId());
        return templateMapper.DBToDTO(templateRepository.saveAndFlush(templateMapper.DTOToDB(template)));
    }

    @Transactional
    public void deleteTemplate(String networkShortcut, String channelShortcut, String shortName) {
        SchTemplatePT toDelete = getTemplate(networkShortcut, channelShortcut, shortName);
        templateRepository.delete(templateMapper.DTOToDB(toDelete));
    }
}
