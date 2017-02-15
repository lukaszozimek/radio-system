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
        SchTemplatePT result = null;

        CorNetwork networkDB = networkService.findNetwork(networkShortcut);
        if (networkDB == null)
            return result;

        CorChannel channelDB = channelService.findChannel(channelShortcut);
        if ((channelDB == null) || (channelDB.getNetwork().getId().compareTo(networkDB.getId()) != 0))
            return result;

        Optional<SchTemplate> templateDB = templateRepository.findByChannelAndName(channelDB, templateName);

        return templateMapper.DBToDTO(templateDB.get());
    }

    public SchTemplatePT setTemplate(String networkShortcut, String channelShortcut, SchTemplatePT template) {
        SchTemplatePT result = null;
        return template;
    }

    public List<SchTemplatePT> getTemplates(String networkShortcut, String channelShortcut) {
        List<SchTemplatePT> results = new ArrayList<>();

        CorNetwork networkDB = networkService.findNetwork(networkShortcut);
        if (networkDB == null)
            return results;

        CorChannel channelDB = channelService.findChannel(channelShortcut);
        if ((channelDB == null) || (channelDB.getNetwork().getId().compareTo(networkDB.getId()) != 0))
            return results;

        List<SchTemplate> resultsDB = templateRepository.findByChannel(channelDB);

        return templateMapper.DBsToDTOs(resultsDB);
    }

    public List<SchTemplatePT> getPlaylists(String networkShortcut) {
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
    public void deleteTemplate(String networkShortcut, String channelShortcut, String date) {
        SchTemplatePT templateToDelete = getTemplate(networkShortcut, channelShortcut, date);
        templateRepository.delete(templateMapper.DTOToDB(templateToDelete));
    }
}
