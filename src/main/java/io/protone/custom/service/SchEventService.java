package io.protone.custom.service;

import io.protone.custom.service.dto.SchEventPT;
import io.protone.custom.service.mapper.CustomSchTemplateMapper;
import io.protone.custom.utils.BlockUtils;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.SchTemplate;
import io.protone.repository.custom.CustomCorChannelRepository;
import io.protone.repository.custom.CustomSchTemplateRepository;
import io.protone.service.cor.CorChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SchEventService {

    private final Logger log = LoggerFactory.getLogger(SchEventService.class);

    @Inject
    private CorNetworkService networkService;

    @Inject
    private CorChannelService channelService;

    @Inject
    private CustomSchTemplateRepository templateRepository;

    @Inject
    private CustomSchTemplateMapper templateMapper;

    @Inject
    private CustomCorChannelRepository channelRepository;

    @Inject
    private SchBlockService blockService;

    @Inject
    private BlockUtils blockUtils;

    public SchEventPT randomTemplate(String networkShortcut, String channelShortcut, String templateName) {
        CorChannel channelDB = channelService.findChannel(networkShortcut, channelShortcut);

        return new SchEventPT()
          //  .channelId(channelDB.getId())
            .name(templateName);
    }

    public SchEventPT getTemplateByChannelAndShortcut(String networkShortcut, String channelShortcut, String templateName) {
        SchEventPT result = null;
        CorChannel channelDB = channelService.findChannel(networkShortcut, channelShortcut);
        Optional<SchTemplate> optionalTemplateDB = templateRepository.findByChannelAndName(channelDB, templateName);

        if (optionalTemplateDB.isPresent()) {
            result = templateMapper.DBToDTO(optionalTemplateDB.get());
       }

        return result;
    }

    public List<SchEventPT> getTemplates(String networkShortcut, String channelShortcut) {
        CorChannel channelDB = channelService.findChannel(networkShortcut, channelShortcut);
        List<SchTemplate> resultsDB = templateRepository.findByChannel(channelDB);
        return templateMapper.DBsToDTOs(resultsDB);
    }

    public List<SchEventPT> getTemplates(String networkShortcut) {
        List<SchEventPT> results = new ArrayList<>();
        CorNetwork networkDB = networkService.findNetwork(networkShortcut);
        if (networkDB == null)
            return results;

        List<CorChannel> channels = channelRepository.findAllByNetwork(networkDB);

        for (CorChannel channel : channels) {
            List<SchTemplate> templates = templateRepository.findByChannel(channel);
            results.addAll(templateMapper.DBsToDTOs(templates));
        }
        return results;
    }

    @Transactional
    public void deleteTemplate(String networkShortcut, String date) {
        List<SchEventPT> templatesToDelete = getTemplates(networkShortcut, date);
        templateRepository.delete(templateMapper.DTOsToDBs(templatesToDelete));
    }

    @Transactional
    public SchEventPT createOrUpdateTemplate(String networkShortcut, SchEventPT template) {
        return templateMapper.DBToDTO(templateRepository.saveAndFlush(templateMapper.DTOToDB(template)));
    }

    @Transactional
    public SchEventPT createOrUpdateTemplate(String networkShortcut, String channelShortcut, SchEventPT template) {
        return setTemplate(networkShortcut, channelShortcut, template);
    }

    private SchEventPT setTemplate(String networkShortcut, String channelShortcut, SchEventPT template) {

        CorChannel channelDB = channelService.findChannel(networkShortcut, channelShortcut);
        if (channelDB == null)
            return null;
        SchEventPT result = saveTemplate(template);

        return result;
    }

    private SchEventPT saveTemplate(SchEventPT template) {
        SchTemplate templateDB = templateMapper.DTOToDB(template);
        templateDB = templateRepository.saveAndFlush(templateDB);
        SchEventPT result = templateMapper.DBToDTO(templateDB);
       return result;
    }

    @Transactional
    public void deleteTemplate(String networkShortcut, String channelShortcut, String shortName) {
        SchEventPT toDelete = getTemplateByChannelAndShortcut(networkShortcut, channelShortcut, shortName);
        templateRepository.delete(templateMapper.DTOToDB(toDelete));
    }
}
