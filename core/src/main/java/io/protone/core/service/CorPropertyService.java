package io.protone.core.service;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import io.protone.core.domain.*;
import io.protone.core.repository.CorPropertyKeyRepository;
import io.protone.core.repository.CorPropertyValueRepository;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;

import static io.protone.core.constans.ServiceConstants.NO_DATA;


/**
 * Created by lukaszozimek on 29/05/2017.
 */
@Service
public class CorPropertyService {
    private final Logger log = LoggerFactory.getLogger(CorPropertyService.class);

    @Inject
    private CorPropertyValueRepository corPropertyValueRepository;

    @Inject
    private CorPropertyKeyRepository corPropertyKeyRepository;

    @Transactional
    public CorPropertyValue saveCorProperty(String metadataName, CorItem libMediaItem, Metadata metadata, CorChannel channel) {
        CorPropertyKey corPropertyKey;
        if (Strings.isNullOrEmpty(metadataName)) {
            corPropertyKey = new CorPropertyKey().key(NO_DATA).channel(channel);
        } else {
            corPropertyKey = new CorPropertyKey().key(metadataName).channel(channel);
        }

        log.debug("Persisting CorKey: {}", corPropertyKey);
        corPropertyKey = corPropertyKeyRepository.saveAndFlush(corPropertyKey);
        log.debug("Persisting CorValues: {}", metadata.get(metadataName));
        return corPropertyValueRepository.saveAndFlush(new CorPropertyValue().value(metadata.get(metadataName)).libItemPropertyValue(libMediaItem).propertyKey(corPropertyKey));

    }

    @Transactional
    public CorPropertyValue saveCorProperty(CorPropertyValue corPropertyValue) {
        log.debug("Persisting CorValues: {}", corPropertyValue);
        CorPropertyKey corPropertyKey = corPropertyKeyRepository.saveAndFlush(corPropertyValue.getPropertyKey());
        corPropertyValue.setPropertyKey(corPropertyKey);
        return corPropertyValueRepository.saveAndFlush(corPropertyValue);

    }

    @Transactional
    public void deleteProperties(Set<CorPropertyValue> propertyValues) {
        if (propertyValues != null && !propertyValues.isEmpty()) {
            propertyValues.stream().forEach(propertyValue -> {
                corPropertyValueRepository.delete(propertyValue.libItemPropertyValue(null));
            });
            corPropertyValueRepository.flush();
        }
    }

    @Transactional
    public void detachProperties(Set<CorPropertyValue> propertyValues) {
        if (propertyValues != null && !propertyValues.isEmpty()) {
            propertyValues.stream().forEach(propertyValue -> {
                corPropertyValueRepository.save(propertyValue.libItemPropertyValue(null));
            });
            corPropertyValueRepository.flush();
        }
    }
}
