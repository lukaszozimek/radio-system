package io.protone.traffic.service;


import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmAccount;
import io.protone.library.domain.LibFileItem;
import io.protone.library.service.LibFileItemService;
import io.protone.traffic.domain.TraMediaPlan;
import io.protone.traffic.repository.TraMediaPlanRepository;
import io.protone.traffic.service.mediaplan.TraExcelMediaParserXlsPlan;
import io.protone.traffic.service.mediaplan.descriptor.TraMediaPlanDescriptor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by lukaszozimek on 08/06/2017.
 */

@Service
public class TraMediaPlanService {
    private final Logger log = LoggerFactory.getLogger(TraMediaPlanService.class);

    private static final String MEDIA_PLAN_LIBRARY_SHORTCUT = "mpl";

    @Inject
    private TraMediaPlanRepository traMediaPlanRepository;

    @Inject
    private TraExcelMediaParserXlsPlan traExcelMediaXlsPlan;

    @Inject
    private TraMediaPlanPlaylistDateService traPlaylistService;

    @Inject
    private LibFileItemService libFileItemService;

    @Transactional
    public TraMediaPlan saveMediaPlan(MultipartFile multipartFile, TraMediaPlanDescriptor traMediaPlanDescriptor, CorNetwork corNetwork, CorChannel corChannel) throws IOException, SAXException, TikaException, InvalidFormatException {
        TraMediaPlan mediaPlan = new TraMediaPlan();
        ByteArrayInputStream bais = new ByteArrayInputStream(multipartFile.getBytes());
        LibFileItem libFileItem = libFileItemService.uploadFileItem(corNetwork.getShortcut(), MEDIA_PLAN_LIBRARY_SHORTCUT, multipartFile);
        mediaPlan.fileItem(libFileItem).network(corNetwork).channel(corChannel).account(traMediaPlanDescriptor.getOrder().getAdvertisment().getCustomer()).name(multipartFile.getOriginalFilename());
        mediaPlan = traMediaPlanRepository.saveAndFlush(mediaPlan);
        TraMediaPlan finalMediaPlan = mediaPlan;
        traExcelMediaXlsPlan.parseMediaPlan(bais, mediaPlan, traMediaPlanDescriptor, corNetwork, corChannel);
        return finalMediaPlan;

    }

    @Transactional
    public TraMediaPlan updateMediaPlan(TraMediaPlan traMediaPlan) {
        return traMediaPlanRepository.saveAndFlush(traMediaPlan);
    }

    @Transactional
    public Slice<TraMediaPlan> getMediaPlans(String corNetwork, String corChannel, Pageable pageable) {
        return traMediaPlanRepository.findSliceByNetwork_ShortcutAndChannel_Shortcut(corNetwork, corChannel, pageable);
    }

    @Transactional
    public void deleteMediaPlan(Long id, String corNetwork, String corChannel) {
        TraMediaPlan traMediaPlan = traMediaPlanRepository.findByIdAndNetwork_ShortcutAndChannel_Shortcut(id, corNetwork, corChannel);
        if (traMediaPlan == null) {
            throw new EntityNotFoundException();
        }
        libFileItemService.deleteFile(traMediaPlan.getLibFileItem());
        traMediaPlanRepository.delete(traMediaPlan);

    }

    @Transactional
    public void deleteCustomerMediaPlan(CrmAccount crmAccount, String corNetwork) {
        List<TraMediaPlan> traMediaPlans = traMediaPlanRepository.findAllByNetwork_ShortcutAndAccount(corNetwork, crmAccount);

        if (traMediaPlans != null) {
            traMediaPlans.stream().forEach(traMediaPlan -> {
                libFileItemService.deleteFile(traMediaPlan.getLibFileItem());
                traMediaPlanRepository.delete(traMediaPlan);
            });
        }
    }


    @Transactional
    public TraMediaPlan getMediaPlan(Long id, String corNetwork, String corChannel) {
        return traMediaPlanRepository.findByIdAndNetwork_ShortcutAndChannel_Shortcut(id, corNetwork, corChannel);
    }

    @Transactional
    public Slice<TraMediaPlan> getCustomerMediaPlan(String customerShortcut, String corNetwork, String corChannel, Pageable pageable) {
        return traMediaPlanRepository.findSliceByAccount_ShortNameAndNetwork_ShortcutAndChannel_Shortcut(customerShortcut, corNetwork, corChannel, pageable);
    }


}
