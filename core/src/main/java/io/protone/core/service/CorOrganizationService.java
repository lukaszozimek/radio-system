package io.protone.core.service;

import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.repository.CorOrganizationRepository;
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
public class CorOrganizationService {
    private final Logger log = LoggerFactory.getLogger(CorOrganizationService.class);

    @Inject
    private CorOrganizationRepository corOrganizationRepository;


    public CorOrganization findOrganization(String shortcut) {
        CorOrganization organization = corOrganizationRepository.findOneByShortcut(shortcut);
        return organization;
    }

    public CorOrganization save(CorOrganization organization) {
        return corOrganizationRepository.saveAndFlush(organization);
    }
}
