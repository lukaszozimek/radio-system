package io.protone.traffic.service;


import io.protone.core.domain.CorChannel;
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
    private LibFileItemService libFileItemService;

    @Transactional
    public TraMediaPlan saveMediaPlan(MultipartFile multipartFile, TraMediaPlanDescriptor traMediaPlanDescriptor, CorChannel corChannel) throws IOException, SAXException, TikaException, InvalidFormatException {
        TraMediaPlan mediaPlan = new TraMediaPlan();
        ByteArrayInputStream bais = new ByteArrayInputStream(multipartFile.getBytes());
        LibFileItem libFileItem = libFileItemService.uploadFileItem(corChannel.getOrganization().getShortcut(), corChannel.getShortcut(), MEDIA_PLAN_LIBRARY_SHORTCUT, multipartFile);
        mediaPlan.fileItem(libFileItem).channel(corChannel).account(traMediaPlanDescriptor.getOrder().getAdvertisment().getCustomer()).name(multipartFile.getOriginalFilename());
        mediaPlan = traMediaPlanRepository.saveAndFlush(mediaPlan);
        TraMediaPlan finalMediaPlan = mediaPlan;
        traExcelMediaXlsPlan.parseMediaPlan(bais, mediaPlan, traMediaPlanDescriptor, corChannel);
        return finalMediaPlan;

    }

    @Transactional
    public TraMediaPlan updateMediaPlan(TraMediaPlan traMediaPlan) {
        return traMediaPlanRepository.saveAndFlush(traMediaPlan);
    }

    @Transactional
    public Slice<TraMediaPlan> getMediaPlans(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return traMediaPlanRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pageable);
    }

    @Transactional
    public void deleteMediaPlan(Long id, String organizationShortcut, String channelShortcut) {
        TraMediaPlan traMediaPlan = traMediaPlanRepository.findByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
        if (traMediaPlan == null) {
            throw new EntityNotFoundException();
        }
        libFileItemService.deleteFile(traMediaPlan.getLibFileItem());
        traMediaPlanRepository.delete(traMediaPlan);

    }

    @Transactional
    public void deleteCustomerMediaPlan(CrmAccount crmAccount, String organizationShortcut, String channelShortcut) {
        List<TraMediaPlan> traMediaPlans = traMediaPlanRepository.findAllByChannel_Organization_ShortcutAndChannel_ShortcutAndAccount(organizationShortcut, channelShortcut, crmAccount);

        if (traMediaPlans != null) {
            traMediaPlans.stream().forEach(traMediaPlan -> {
                libFileItemService.deleteFile(traMediaPlan.getLibFileItem());
                traMediaPlanRepository.delete(traMediaPlan);
            });
        }
    }


    @Transactional
    public TraMediaPlan getMediaPlan(Long id, String organizationShortcut, String channelShortcut) {
        return traMediaPlanRepository.findByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
    }

    @Transactional
    public Slice<TraMediaPlan> getCustomerMediaPlan(String customerShortcut, String organizationShortcut, String channelShortcut, Pageable pageable) {
        return traMediaPlanRepository.findSliceByAccount_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(customerShortcut, organizationShortcut, channelShortcut, pageable);
    }


}
