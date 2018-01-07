package io.protone.core.service;

import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.s3.S3Client;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * Created by lukaszozimek on 16.01.2017.
 */
@Service
public class CorNetworkService {
    private final Logger log = LoggerFactory.getLogger(CorNetworkService.class);

    @Inject
    private CorNetworkRepository corNetworkRepository;

    @Inject
    private CorImageItemService corImageItemService;

    @Inject
    private S3Client s3Client;

    public List<CorNetwork> findAllNetworks(String organizationShortcut) {
        return corNetworkRepository.findAllByOrganization_Shortcut(organizationShortcut);
    }

    public CorNetwork findNetwork(String shortcut, String organizationShortcut) {
        CorNetwork corNetwork = corNetworkRepository.findOneByShortcutAndOrganization_Shortcut(shortcut, organizationShortcut);
        return corNetwork;
    }

    @Transactional
    public void deleteNetwork(String shortcut, String organizationShortcut) {
        corNetworkRepository.deleteByShortcutAndOrganization_Shortcut(shortcut, organizationShortcut);
    }

    public CorNetwork save(CorNetwork network) {
        log.debug("Persisting CorNetwork: {}", network);
        return corNetworkRepository.saveAndFlush(network);
    }

    public CorNetwork update(CorNetwork network) {
        log.debug("Persisting CorNetwork: {}", network);
        return corNetworkRepository.saveAndFlush(network);
    }

    public CorNetwork save(CorNetwork corNetwork, MultipartFile logo) throws IOException, TikaException, SAXException {
        log.debug("Persisting CorNetwork: {}", corNetwork);
        CorImageItem corImageItem = corImageItemService.saveImageItem(logo, corNetwork.getOrganization());
        return corNetworkRepository.saveAndFlush(corNetwork.image(corImageItem));
    }
}
