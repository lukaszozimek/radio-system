package io.protone.core.service;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import io.protone.core.repository.CorPropertyKeyRepository;
import io.protone.core.repository.CorPropertyValueRepository;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPropertyKey;
import io.protone.domain.CorPropertyValue;
import io.protone.domain.LibMediaItem;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import service.file.impl.LibAudioFileService;

import javax.inject.Inject;

import static io.protone.service.constans.ServiceConstants.NO_DATA;

/**
 * Created by lukaszozimek on 29/05/2017.
 */
@Service
public class CorPropertyService {
    private final Logger log = LoggerFactory.getLogger(LibAudioFileService.class);

    @Inject
    private CorPropertyValueRepository corPropertyValueRepository;

    @Inject
    private CorPropertyKeyRepository corPropertyKeyRepository;

    public CorPropertyValue saveCorProperty(String metadataName, LibMediaItem libMediaItem, Metadata metadata, CorNetwork corNetwork) {
        CorPropertyKey corPropertyKey;
        if (Strings.isNullOrEmpty(metadataName)) {
            corPropertyKey = new CorPropertyKey().key(NO_DATA).network(corNetwork);
        } else {
            corPropertyKey = new CorPropertyKey().key(metadataName).network(corNetwork);
        }

        log.debug("Persisting CorKey: {}", corPropertyKey);
        corPropertyKey = corPropertyKeyRepository.saveAndFlush(corPropertyKey);
        log.debug("Persisting CorValues: {}", metadata.get(metadataName));
        return corPropertyValueRepository.saveAndFlush(new CorPropertyValue().value(metadata.get(metadataName)).libItemPropertyValue(libMediaItem).propertyKey(corPropertyKey));

    }
}
