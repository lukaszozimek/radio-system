package io.protone.core.service;

import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.s3.exceptions.CreateBucketException;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

import static io.protone.core.constans.MinioFoldersConstants.FILE;
import static io.protone.core.service.CorImageItemService.PUBLIC_CONTENT;

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

    public List<CorNetwork> findAllNetworks() {
        return corNetworkRepository.findAll();
    }

    public CorNetwork findNetwork(String shortcut) {
        CorNetwork corNetwork = corNetworkRepository.findOneByShortcut(shortcut);
        return corNetwork;
    }

    @Transactional
    public void deleteNetwork(String shortcut) {
        corNetworkRepository.deleteByShortcut(shortcut);
    }

    public CorNetwork save(CorNetwork network) {
        log.debug("Persisting CorNetwork: {}", network);
        return corNetworkRepository.saveAndFlush(network);
    }

    public CorNetwork update(CorNetwork network) {
        log.debug("Persisting CorNetwork: {}", network);
        return corNetworkRepository.saveAndFlush(network);
    }

    public void createPublicBucketForThisNetwork(CorNetwork network) {
        log.debug("Persisting CorNetwork: {}", network);
        try {
            s3Client.makeBucketPublicBucket(network.getShortcut(), FILE + PUBLIC_CONTENT);
        } catch (CreateBucketException e) {
            log.error(e.getLocalizedMessage());
        }

    }

    public CorNetwork save(CorNetwork corNetwork, MultipartFile logo) throws IOException, TikaException, SAXException {
        log.debug("Persisting CorNetwork: {}", corNetwork);
        CorImageItem corImageItem = corImageItemService.saveImageItem(logo);
        return corNetworkRepository.saveAndFlush(corNetwork.image(corImageItem));
    }
}
