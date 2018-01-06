package io.protone.traffic.service;


import io.protone.traffic.domain.TraPrice;
import io.protone.traffic.repository.TraPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TraPriceService {

    private final Logger log = LoggerFactory.getLogger(TraPriceService.class);

    @Inject
    private TraPriceRepository traCompanyRepository;


    public Slice<TraPrice> getAllPrice(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return traCompanyRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pageable);
    }

    public TraPrice getPrice(Long id, String organizationShortcut, String channelShortcut) {
        return traCompanyRepository.findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
    }


    public TraPrice savePrice(TraPrice traInvoice) {
        log.debug("Persisting TraPrice: {}", traInvoice);
        return traCompanyRepository.save(traInvoice);
    }

    public void deletePrice(Long id, String organizationShortcut, String channelShortcut) {
        traCompanyRepository.deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
    }


}
