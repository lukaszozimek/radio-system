package io.protone.custom.service;

import io.protone.custom.service.dto.SchTemplatePT;
import io.protone.custom.service.mapper.CustomSchTemplateMapper;
import io.protone.custom.utils.BlockUtils;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.SchTemplate;
import io.protone.repository.custom.CustomCorChannelRepository;
import io.protone.repository.custom.CustomSchTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SchTemplateService {

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

    public SchTemplatePT randomTemplate(String networkShortcut, String channelShortcut, String templateName) {
        CorChannel channelDB = channelService.findChannelByNetworkShortcutAndChannelShortcut(networkShortcut, channelShortcut);

        return new SchTemplatePT()
            .channelId(channelDB.getId())
            .name(templateName)
            .blocks(blockUtils.sampleTemplate());
    }

    public SchTemplatePT getTemplateByChannelAndShortcut(String networkShortcut, String channelShortcut, String templateName) {
        SchTemplatePT result = null;
        CorChannel channelDB = channelService.findChannelByNetworkShortcutAndChannelShortcut(networkShortcut, channelShortcut);
        Optional<SchTemplate> optionalTemplateDB = templateRepository.findByChannelAndName(channelDB, templateName);

        if (optionalTemplateDB.isPresent()) {
            result = templateMapper.DBToDTO(optionalTemplateDB.get());
            result.blocks(new ArrayList<>());
            result.blocks(blockService.getBlocks(null, result, null));
        }

        return result;
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

        for (CorChannel channel : channels) {
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
        return setTemplate(networkShortcut, channelShortcut, template);
    }

    private SchTemplatePT setTemplate(String networkShortcut, String channelShortcut, SchTemplatePT template) {

        CorChannel channelDB = channelService.findChannel(networkShortcut, channelShortcut);
        if (channelDB == null)
            return null;

        SchTemplatePT result = saveTemplate(template);

        return result;
    }

    private SchTemplatePT saveTemplate(SchTemplatePT template) {
        SchTemplate templateDB = templateMapper.DTOToDB(template);
        templateDB = templateRepository.saveAndFlush(templateDB);
        SchTemplatePT result = templateMapper.DBToDTO(templateDB);
        result.blocks(blockService.setBlocks(template.getBlocks(), null, result));
        return result;
    }

    @Transactional
    public void deleteTemplate(String networkShortcut, String channelShortcut, String shortName) {
        SchTemplatePT toDelete = getTemplateByChannelAndShortcut(networkShortcut, channelShortcut, shortName);
        templateRepository.delete(templateMapper.DTOToDB(toDelete));
    }
}
